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

public class professorescolaDao {

	private static final Logger logger = Logger.getLogger(escolaNEDao.class.getName());

	public List<professorescola> getAllProfessorEscola() throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    List<professorescola> professorEscolas = new ArrayList<>();

	    try {
	        connection = DataBase.getDBConnection();
	        statement = connection.prepareStatement("SELECT * FROM professorescola");
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            professorescola professorEscola = new professorescola();
	            professorEscola.setIdprofessorescola(resultSet.getInt("idprofessorescola"));
	            professorEscola.setNome(resultSet.getString("nome"));
	            professorEscola.setIdescolasNE(resultSet.getInt("idescolasNE"));
	            professorEscola.setNomeEscolaNE(resultSet.getString("nomeEscolaNE"));
	            professorEscola.setNotaEscolaNE(resultSet.getString("notaEscolaNE"));
	            professorEscola.setMatricula(resultSet.getString("matricula"));
	            professorEscolas.add(professorEscola);
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

	    return professorEscolas;
	}

	public professorescola getProfessorEscolaById(int idEscolaNE) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    professorescola professorEscola = null;

	    try {
	        connection = DataBase.getDBConnection();
	        statement = connection.prepareStatement("SELECT * FROM professorescola WHERE idescolasNE = ?");
	        statement.setInt(1, idEscolaNE);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            professorEscola = new professorescola();
	            professorEscola.setIdprofessorescola(resultSet.getInt("idprofessorescola"));
	            professorEscola.setNome(resultSet.getString("nome"));
	            professorEscola.setIdescolasNE(resultSet.getInt("idescolasNE"));
	            professorEscola.setNomeEscolaNE(resultSet.getString("nomeEscolaNE"));
	            professorEscola.setNotaEscolaNE(resultSet.getString("notaEscolaNE"));
	            professorEscola.setMatricula(resultSet.getString("matricula"));
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

	    return professorEscola;
	}


	public void saveprofessorescola(professorescola professorEscola) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
	        connection = DataBase.getDBConnection();
	        connection.setAutoCommit(false);
	        String query = "INSERT INTO professorescola(nome, idescolasNE, nomeEscolaNE, notaEscolaNE, matricula) VALUES(?, ?, ?, ?, ?)";
	        statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        int counter = 1;
	        statement.setString(counter++, professorEscola.getNome());
	        statement.setInt(counter++, professorEscola.getIdescolasNE());
	        statement.setString(counter++, professorEscola.getNomeEscolaNE());
	        statement.setString(counter++, professorEscola.getNotaEscolaNE());
	        statement.setString(counter++, professorEscola.getMatricula());
	        statement.executeUpdate();
	        connection.commit();
	        ResultSet resultSet = statement.getGeneratedKeys();
	        if (resultSet.next()) {
	            professorEscola.setIdprofessorescola(resultSet.getInt(1));
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


	public professorescola buscarEscolaPorNomeMatricula(String nome, String matricula) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    professorescola professorescola = null;

	    try {
	        connection = DataBase.getDBConnection();
	        statement = connection.prepareStatement("SELECT * FROM professorescola WHERE nome = ? AND matricula = ?");
	        statement.setString(1, nome);
	        statement.setString(2, matricula);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	        	professorescola = new professorescola();
	        	professorescola.setIdprofessorescola(resultSet.getInt("idprofessorescola"));
	           	professorescola.setIdescolasNE(resultSet.getInt("idescolasNE"));
	        	professorescola.setNomeEscolaNE(resultSet.getString("nomeEscolaNE"));
	        	professorescola.setNotaEscolaNE(resultSet.getString("notaEscolaNE"));
	        	
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

	    return professorescola;
	}
	
	
	
	
	
	
	
}
