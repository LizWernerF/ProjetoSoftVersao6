package infoCalculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.WernerADV.Software.DataBase;

public class CalculosDao {

	int idcalculos;
	String nome;
	String tema;
	String matricula;
	String valorPrincipal;
	String rioPrev;
	String valorFinal;
	
	private static final Logger logger = Logger.getLogger(escolaNEDao.class.getName());
	
	public void saveCalculos(Calculos calculos) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
	        connection = DataBase.getDBConnection();
	        connection.setAutoCommit(false);
	        String query = "INSERT INTO calculos(nome, tema, matricula, valorPrincipal, rioPrev, valorFinal) VALUES(?,?,?,?,?,?)";
	        statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        int counter = 1;
	        statement.setString(counter++, calculos.getNome());
	        statement.setString(counter++, calculos.getTema());
	        statement.setString(counter++, calculos.getMatricula());
	        statement.setString(counter++, calculos.getValorPrincipal());
	        statement.setString(counter++, calculos.getRioPrev());
	        statement.setString(counter++, calculos.getValorFinal());
	        statement.executeUpdate();
	        connection.commit();
	        ResultSet resultSet = statement.getGeneratedKeys();
	        if (resultSet.next()) {
	        	calculos.setIdcalculos(resultSet.getInt(1));
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
	
	
	public Calculos buscarCalculosPorTemaNomeMatricula(String tema, String nome, String matricula) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    Calculos calculos = null;

	    try {
	        connection = DataBase.getDBConnection();
	        statement = connection.prepareStatement("SELECT * FROM calculos WHERE tema = ? AND nome = ? AND matricula = ?");
	        statement.setString(1, tema);
	        statement.setString(2, nome);
	        statement.setString(3, matricula);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            calculos = new Calculos();
	            calculos.setIdcalculos(resultSet.getInt("idcalculos"));
	            calculos.setValorPrincipal(resultSet.getString("valorPrincipal"));
	            calculos.setRioPrev(resultSet.getString("rioPrev"));
	            calculos.setValorFinal(resultSet.getString("valorFinal"));
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

	    return calculos;
	}

	
	
}
