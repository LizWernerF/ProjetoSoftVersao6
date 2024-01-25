package infoCalculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.WernerADV.Software.DataBase;

public class Demonstrativo_interniveisDao {

	private static final Logger logger = Logger.getLogger(Demonstrativo_interniveisDao.class.getName());
	
	
	public List<Demonstrativo_interniveis> buscarPorDataENivel(String data, String nivel, String data1, String nivel1) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    List<Demonstrativo_interniveis> demonstrativos = new ArrayList<>();
	    
	    try {
	        connection = DataBase.getDBConnection();
	        String sql = "SELECT dataDif, nivel, diferenca, trienio, ROUND(diferenca + trienio, 2) as total " +
	                     "FROM clienteswerneradv.demonstrativo_interniveis t " +
	                     "WHERE (t.dataDif < ? AND t.nivel = ?) OR (t.dataDif >= ? AND t.nivel = ?) " +
	                     "ORDER BY 1";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, data);
	        statement.setString(2, nivel);
	        statement.setString(3, data1);
	        statement.setString(4, nivel1);
	        resultSet = statement.executeQuery();
	        
	        while (resultSet.next()) {
	            Demonstrativo_interniveis demonstrativo = new Demonstrativo_interniveis();
	            demonstrativo.setDataDif(resultSet.getString("dataDif"));
	            demonstrativo.setNivel(resultSet.getString("nivel"));
	            demonstrativo.setDiferenca(resultSet.getDouble("diferenca"));
	            demonstrativo.setTrienio(resultSet.getDouble("trienio"));
	            demonstrativos.add(demonstrativo);
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

	    
	    return demonstrativos;
	}

	
	
	
	
	
}
