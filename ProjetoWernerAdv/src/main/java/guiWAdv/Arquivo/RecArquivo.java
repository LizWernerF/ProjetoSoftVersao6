package guiWAdv.Arquivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import guiWAdv.Rec;
import guiWAdv.RecDao;

public class RecArquivo {
	private int Idrecarquivo;
	private int Idrec;
	
	private String numerorecurso;
	private String nomedocliente;
	private String processoorigem;
	private String tiporecurso;
    private String recorridoourecorrente;
    private String status;
    private String julgador;
    private String relator;
    
    public int getIdrecarquivo() {
		return Idrecarquivo;
	}

	public void setIdrecarquivo(int idrecarquivo) {
		Idrecarquivo = idrecarquivo;
	}
	public int getIdrec() {
		return Idrec;
	}

	public void setIdrec(int idrec) {
		Idrec = idrec;
	}

	public String getNumerorecurso() {
		return numerorecurso;
	}

	public void setNumerorecurso(String numerorecurso) {
		this.numerorecurso = numerorecurso;
	}

	public String getNomedocliente() {
		return nomedocliente;
	}

	public void setNomedocliente(String nomedocliente) {
		this.nomedocliente = nomedocliente;
	}

	public String getProcessoorigem() {
		return processoorigem;
	}

	public void setProcessoorigem(String processoorigem) {
		this.processoorigem = processoorigem;
	}

	public String getTiporecurso() {
		return tiporecurso;
	}

	public void setTiporecurso(String tiporecurso) {
		this.tiporecurso = tiporecurso;
	}

	public String getRecorridoourecorrente() {
		return recorridoourecorrente;
	}

	public void setRecorridoourecorrente(String recorridoourecorrente) {
		this.recorridoourecorrente = recorridoourecorrente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJulgador() {
		return julgador;
	}

	public void setJulgador(String julgador) {
		this.julgador = julgador;
	}

	public String getRelator() {
		return relator;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}

	
	@Override
	public String toString() {
		return "Rec [Idrec=" + Idrec + ", numerorecurso=" + numerorecurso + ", nomedocliente=" + nomedocliente
				+ ", processoorigem=" + processoorigem + ", tiporecurso=" + tiporecurso + ", recorridoourecorrente="
				+ recorridoourecorrente + ", status=" + status + ", julgador=" + julgador + ", relator=" + relator
				+ "]";
	}
	
	
	
	private static final Logger logger = Logger.getLogger(RecDao.class.getName());
	
	public boolean recArqExist(String numerorecurso) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		List<Rec> recs = new ArrayList<>();
		
		
		try {
			connection = com.WernerADV.Software.DataBase.getDBConnection();
			connection.setAutoCommit(false);
			String query = "SELECT Idrec, numerorecurso, nomedocliente, processoorigem, tiporecurso, recorridoourecorrente, status, julgador, relator FROM proc WHERE numerorecurso = ?";
			statement = connection.prepareStatement(query);
			int counter = 1;
			statement.setString(counter++, numerorecurso);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Rec rec = new Rec();
				rec.setIdrec(resultSet.getInt(1));
				rec.setNumerorecurso(resultSet.getString(2));
				rec.setNomedocliente(resultSet.getString(3));
				rec.setProcessoorigem(resultSet.getString(4));
				rec.setTiporecurso(resultSet.getString(5));
				rec.setRecorridoourecorrente(resultSet.getString(6));
				rec.setStatus(resultSet.getString(7));
				rec.setJulgador(resultSet.getString(8));
				rec.setRelator(resultSet.getString(9));

				recs.add(rec);
			}

			return recs.isEmpty() ? false : true;
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		} finally {
			if (null != statement) {
				statement.close();
			}

			if (null != connection) {
				connection.close();
			}
		}

		return recs.isEmpty() ? false : true;
	}

	public int saveRecArq(Rec rec) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = com.WernerADV.Software.DataBase.getDBConnection();
			connection.setAutoCommit(false);
			String query = "INSERT INTO rec(numerorecurso, nomedocliente, processoorigem, tiporecurso, recorridoourecorrente, status, julgador, relator) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			statement.setString(counter++, rec.getNumerorecurso());
			statement.setString(counter++, rec.getNomedocliente());
			statement.setString(counter++, rec.getProcessoorigem());
			statement.setString(counter++, rec.getTiporecurso());
			statement.setString(counter++, rec.getRecorridoourecorrente());
			statement.setString(counter++, rec.getStatus());
			statement.setString(counter++, rec.getJulgador());
			statement.setString(counter++, rec.getRelator());
			statement.executeUpdate();
			connection.commit();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
			if (null != connection) {
				connection.rollback();
			}

		}

		finally {
			if (null != resultSet) {
				resultSet.close();
			}

			if (null != statement) {
				statement.close();
			}

			if (null != connection) {
				connection.close();
			}
		}

		return 0;
	}

	
	
	
	
	
}
