package infoCalculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.WernerADV.Software.DataBase;

public class escolaNEDao {
	
	
	private static final Logger logger = Logger.getLogger(escolaNEDao.class.getName());

	public List<escolaNE> getAllEscolasNE() throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    List<escolaNE> escolasNE = new ArrayList<>();

	    try {
	        connection = DataBase.getDBConnection();
	        statement = connection.prepareStatement("SELECT * FROM escolaNE");
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            escolaNE escola = new escolaNE();
	            escola.setIdescolasNE(resultSet.getInt("idescolasNE"));
	            escola.setNomeEscola(resultSet.getString("NomeEscola"));
	            escola.setNotaEscola(resultSet.getString("NotaEscola"));
	            escolasNE.add(escola);
	        }
	    } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
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

	    return escolasNE;
	}

	public escolaNE getEscolaNEById(int idEscolaNE) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    escolaNE escola = null;

	    try {
	        connection = DataBase.getDBConnection();
	        statement = connection.prepareStatement("SELECT * FROM escolaNE WHERE idescolasNE = ?");
	        statement.setInt(1, idEscolaNE);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            escola = new escolaNE();
	            escola.setIdescolasNE(resultSet.getInt("idescolasNE"));
	            escola.setNomeEscola(resultSet.getString("NomeEscola"));
	            escola.setNotaEscola(resultSet.getString("NotaEscola"));
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
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

	    return escola;
	}

	public void saveEscolaNE(escolaNE escola) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
	        connection = DataBase.getDBConnection();
	        connection.setAutoCommit(false);
	        String query = "INSERT INTO escolaNE(NomeEscola, NotaEscola) VALUES(?, ?)";
	        statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        int counter = 1;
	        statement.setString(counter++, escola.getNomeEscola());
	        statement.setString(counter++, escola.getNotaEscola());
	        statement.executeUpdate();
	        connection.commit();
	        ResultSet resultSet = statement.getGeneratedKeys();
	        if (resultSet.next()) {
	            escola.setIdescolasNE(resultSet.getInt(1));
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	        if (null != connection) {
	            connection.rollback();
	        }
	    } finally {
	        if (null != statement) {
	            statement.close();
	        }
	    }
	}

	public List<escolaNE> buscarEscolasNEPorNome(String nome) throws SQLException {
	    List<escolaNE> escolasNE = new ArrayList<>();
	    String sql = "SELECT * FROM escolaNE WHERE NomeEscola LIKE ?";
	    
	    try (Connection connection = DataBase.getDBConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, "%" + nome + "%"); // Adicione % para corresponder a qualquer parte do nome
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            escolaNE escola = new escolaNE();
	            escola.setIdescolasNE(resultSet.getInt("idescolasNE"));
	            escola.setNomeEscola(resultSet.getString("NomeEscola"));
	            escola.setNotaEscola(resultSet.getString("NotaEscola"));
	            escolasNE.add(escola);
	        }
	    }

	    return escolasNE;
	}

	public int buscarIdEscolaNE(String nomeEscola, String notaEscola) {
	    try {
	        List<escolaNE> escolasNE = getAllEscolasNE();
	        for (escolaNE escola : escolasNE) {
	            if (escola.getNomeEscola().equals(nomeEscola) && escola.getNotaEscola().equals(notaEscola)) {
	                return escola.getIdescolasNE();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // Valor padrão caso não encontre a correspondência
	}

	
	
}
