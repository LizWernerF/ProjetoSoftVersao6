package infoCalculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.WernerADV.Software.DataBase;

public class DbpisoDao {

	private static final Logger logger = Logger.getLogger(DbpisoDao.class.getName());

	// ... (códigos existentes)

	public double getValorProventoEstado(String nivel, String ano, String cargaHoraria) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		double valorProventoEstado = 0;

		try {
			connection = DataBase.getDBConnection();
			String query = "SELECT valorproventoestado FROM dbpiso WHERE nivel = ? AND ano = ? AND cargahoraria = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, nivel);
			statement.setString(2, ano);
			statement.setString(3, cargaHoraria);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				valorProventoEstado = resultSet.getDouble("valorproventoestado");
			}
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		} finally {
			// Fechar conexões e recursos
		}

		return valorProventoEstado;
	}

	public double getValorPisoNacional(String nivel, String ano, String cargaHoraria) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		double valorPisoNacional = 0;

		try {
			connection = DataBase.getDBConnection();
			String query = "SELECT valorpisonacional FROM dbpiso WHERE nivel = ? AND ano = ? AND cargahoraria = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, nivel);
			statement.setString(2, ano);
			statement.setString(3, cargaHoraria);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				valorPisoNacional = resultSet.getDouble("valorpisonacional");
			}
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		} finally {
			// Fechar conexões e recursos
		}

		return valorPisoNacional;
	}

	public List<Double> obterValoresAeB(String nivel, String cargaHoraria) throws SQLException {
		List<Double> valoresAeB = new ArrayList<>();

		for (int ano = 2017; ano <= 2022; ano++) {
			double valorA = getValorProventoEstado(nivel, String.valueOf(ano), cargaHoraria);
			double valorB = getValorPisoNacional(nivel, String.valueOf(ano), cargaHoraria);
			valoresAeB.add(valorA);
			valoresAeB.add(valorB);
		}

		return valoresAeB;
	}

	public List<LinhaPlanilha> obterValoresPlanilha(int cargaHoraria, LocalDate anoInicial, LocalDate anoFinal, int nivel,
			double percentual) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<LinhaPlanilha> result = new ArrayList<>();

		try {
			connection = DataBase.getDBConnection();
			String query = "select *, round(Y.diferenca_percentual + Y.diferenca, 2 ) as valor_final " + " from ("
					+ "	select *, round(X.vpn_percentual - X.vpe_percentual, 2 ) as diferenca_percentual " + "	from ("
					+ "		select *, round(valorpisonacional - valorproventoestado, 2 ) as diferenca, "
					+ "		valorproventoestado * ? as vpe_percentual, "
					+ "		valorpisonacional * ? as vpn_percentual " + "		from clienteswerneradv.dbpiso"
					+ "		where ano >= ?" + "		and ano <= ?" + "		and nivel = ?"
					+ "		and cargahoraria = ?" + "	) as X" + ") as Y";
			statement = connection.prepareStatement(query);
			statement.setDouble(1, percentual);
			statement.setDouble(2, percentual);
			statement.setDouble(3, anoInicial.getYear());
			statement.setDouble(4, anoFinal.getYear());
			statement.setDouble(5, nivel);
			statement.setDouble(6, cargaHoraria);

			rs = statement.executeQuery();

			while (rs.next()) {
				int ano = rs.getInt("ano");
				
				if(ano == anoInicial.getYear()) {
					for(Meses m : Meses.getMesesAfter(anoInicial.getMonth().getValue())) {
						result.add(new LinhaPlanilha(m.toString() + "/" + ano,ano, rs.getDouble("valorproventoestado"),
								rs.getDouble("valorpisonacional"), rs.getDouble("diferenca"), rs.getDouble("vpe_percentual"),
								rs.getDouble("vpn_percentual"), rs.getDouble("diferenca_percentual"),
								rs.getDouble("valor_final")));
					}
				}
				else if(ano == anoFinal.getYear()) {
					for(Meses m : Meses.getMesesUntil(anoFinal.getMonth().getValue())) {
						result.add(new LinhaPlanilha(m.toString() + "/" + ano,ano, rs.getDouble("valorproventoestado"),
								rs.getDouble("valorpisonacional"), rs.getDouble("diferenca"), rs.getDouble("vpe_percentual"),
								rs.getDouble("vpn_percentual"), rs.getDouble("diferenca_percentual"),
								rs.getDouble("valor_final")));
					}
				}
				else {
					for(Meses m : Meses.getAllMeses()) {
						result.add(new LinhaPlanilha(m.toString() + "/" + ano,ano, rs.getDouble("valorproventoestado"),
								rs.getDouble("valorpisonacional"), rs.getDouble("diferenca"), rs.getDouble("vpe_percentual"),
								rs.getDouble("vpn_percentual"), rs.getDouble("diferenca_percentual"),
								rs.getDouble("valor_final")));
					}
				}
			}
			
			

		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		} finally {
			// connection.close();
		}

		return result;
	}

	public class LinhaPlanilha {

		private int ano;
		private Double valorProventoEstado;
		private Double valorPisoNacional;
		private Double diferenca;
		private Double valorProventoEstadoPercentual;
		private Double valorPisoNacionalPercentual;
		private Double diferencaPercentual;
		private Double total;
		private String dataParcelas;

		public LinhaPlanilha(String dataParcelas, int ano, Double valorProventoEstado, Double valorPisoNacional, Double diferenca,
				Double valorProventoEstadoPercentual, Double valorPisoNacionalPercentual, Double diferencaPercentual,
				Double total) {
			super();
			this.dataParcelas = dataParcelas;
			this.ano = ano;
			this.valorProventoEstado = valorProventoEstado;
			this.valorPisoNacional = valorPisoNacional;
			this.diferenca = diferenca;
			this.valorProventoEstadoPercentual = valorProventoEstadoPercentual;
			this.valorPisoNacionalPercentual = valorPisoNacionalPercentual;
			this.diferencaPercentual = diferencaPercentual;
			this.total = total;
		}
		
		@Override
		public String toString() {
			return "dataParcelas: " + dataParcelas + 
					", valorProventoEstado: " + valorProventoEstado + 
					", valorPisoNacional: " + valorPisoNacional + 
					", diferenca: " + diferenca + 
					", valorProventoEstadoPercentual: " + valorProventoEstadoPercentual + 
					", valorPisoNacionalPercentual: " + valorPisoNacionalPercentual + 
					", diferencaPercentual: " + diferencaPercentual + 
					", total: " + total;
		}

		public int getAno() {
			return ano;
		}

		public void setAno(int ano) {
			this.ano = ano;
		}

		public Double getValorProventoEstado() {
			return valorProventoEstado;
		}

		public void setValorProventoEstado(Double valorProventoEstado) {
			this.valorProventoEstado = valorProventoEstado;
		}

		public Double getValorPisoNacional() {
			return valorPisoNacional;
		}

		public void setValorPisoNacional(Double valorPisoNacional) {
			this.valorPisoNacional = valorPisoNacional;
		}

		public Double getDiferenca() {
			return diferenca;
		}

		public void setDiferenca(Double diferenca) {
			this.diferenca = diferenca;
		}

		public Double getValorProventoEstadoPercentual() {
			return valorProventoEstadoPercentual;
		}

		public void setValorProventoEstadoPercentual(Double valorProventoEstadoPercentual) {
			this.valorProventoEstadoPercentual = valorProventoEstadoPercentual;
		}

		public Double getValorPisoNacionalPercentual() {
			return valorPisoNacionalPercentual;
		}

		public void setValorPisoNacionalPercentual(Double valorPisoNacionalPercentual) {
			this.valorPisoNacionalPercentual = valorPisoNacionalPercentual;
		}

		public Double getDiferencaPercentual() {
			return diferencaPercentual;
		}

		public void setDiferencaPercentual(Double diferencaPercentual) {
			this.diferencaPercentual = diferencaPercentual;
		}

		public Double getTotal() {
			return total;
		}

		public void setTotal(Double total) {
			this.total = total;
		}

		public String getDataParcelas() {
			return dataParcelas;
		}

		public void setDataParcelas(String dataParcelas) {
			this.dataParcelas = dataParcelas;
		}

	}

	private enum Meses {

		JAN(1), FEV(2), MAR(3), ABR(4), MAI(5), JUN(6), JUL(7), AGO(8), SET(9), OUT(10), NOV(11), DEC(12), DEC2(13);
		
		private Integer mes;
		
		Meses(Integer mes) {
			this.mes = mes;
		}

		public static List<Meses> getMesesUntil(Integer mes) {
			List<Meses> meses = new ArrayList<>();
			for (Meses m : Meses.values()) {
				if(m.getMes() <= mes) {
			      meses.add(m);
				}
			  }
			
			return meses;
		}
		
		public static List<Meses> getMesesAfter(Integer mes) {
			List<Meses> meses = new ArrayList<>();
			for (Meses m : Meses.values()) {
				if(m.getMes() >= mes) {
			      meses.add(m);
				}
			  }
			
			return meses;
		}
		
		public static List<Meses> getAllMeses() {
			return List.of(Meses.values());
		}
		
		public Integer getMes() {
			return mes;
		}
	}
}
