package guiWAdv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.WernerADV.Software.DataBase;

public class HistoricoDao {
	

		private static final Logger logger = Logger.getLogger(HistoricoDao.class.getName());
			
		 public List<Historico> getAllHistoricos() throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        List<Historico> historicos = new ArrayList<>();

		        try {
		            connection = DataBase.getDBConnection();
		            statement = connection.prepareStatement("SELECT * FROM historico");
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                Historico historico = new Historico();
		                historico.setIdHistorico(resultSet.getInt("idHistorico"));
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(resultSet.getString("data"));
		                historico.setDescricao(resultSet.getString("descricao"));
		                historicos.add(historico);
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

		        return historicos;
		    }

		    public Historico getHistoricoById(int idHistorico) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        Historico historico = null;

		        try {
		            connection = DataBase.getDBConnection();
		            statement = connection.prepareStatement("SELECT * FROM historico WHERE idHistorico = ?");
		            statement.setInt(1, idHistorico);
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                historico = new Historico();
		                historico.setIdHistorico(resultSet.getInt("idHistorico"));
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(resultSet.getString("data"));
		                historico.setDescricao(resultSet.getString("descricao"));
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

		        return historico;
		    }

		    public void saveHistorico(Historico historico) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;

		        try {
		            connection = DataBase.getDBConnection();
		            connection.setAutoCommit(false);
		            String query = "INSERT INTO historico(idProcesso, data, descricao) VALUES(?, ?, ?)";
		            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		            int counter = 1;
		            statement.setInt(counter++, historico.getIdProcesso());
		            statement.setString(counter++, historico.getData());
		            statement.setString(counter++, historico.getDescricao());
		            statement.executeUpdate();
		            connection.commit();
		            ResultSet resultSet = statement.getGeneratedKeys();
		            if (resultSet.next()) {
		                historico.setIdHistorico(resultSet.getInt(1));
		            }
		        } catch (SQLException exception) {
		            logger.log(Level.SEVERE, exception.getMessage());
		            if (null != connection) {
		                connection.rollback();
		            }
		        } finally {
		            if (null != statement) {
		                statement.close();
		            } }  
		        }
		    
		    public void saveHistorico(Historico historico, int idProcesso) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;

		        try {
		            connection = DataBase.getDBConnection();
		            connection.setAutoCommit(false);
		            String query = "INSERT INTO historico(idProcesso, data, descricao) VALUES(?, ?, ?)";
		            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		            int counter = 1;
		            statement.setInt(counter++, idProcesso); // Aqui passamos o ID do processo
		            statement.setString(counter++, historico.getData());
		            statement.setString(counter++, historico.getDescricao());
		            statement.executeUpdate();
		            connection.commit();
		            ResultSet resultSet = statement.getGeneratedKeys();
		            if (resultSet.next()) {
		                historico.setIdHistorico(resultSet.getInt(1));
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
		    
		    public List<String> getDatasEDescricoesPorIdProcesso(int idProcesso) throws SQLException {
		        Connection connection = DataBase.getDBConnection(); // Use a mesma conexão do seu método obterNomesClientes
		        List<String> datasEDescricoes = new ArrayList<>();

		        // Execute uma consulta SQL para obter datas e descrições com base no ID do processo
		        String sql = "SELECT data, descricao FROM historico WHERE idProcesso = ?";
		        
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setInt(1, idProcesso);
		            ResultSet resultSet = statement.executeQuery();

		            // Itere sobre o resultado e adicione as datas e descrições à lista
		            while (resultSet.next()) {
		                String data = resultSet.getString("data");
		                String descricao = resultSet.getString("descricao");
		                String dataEDescricao = data + ": " + descricao;
		             
		                datasEDescricoes.add(dataEDescricao);
		            }
		        }

		        return datasEDescricoes;
		    }

		    public List<String> getDatasEDescricoesPorIdProcessoEditar(int idProcesso) throws SQLException {
		        Connection connection = DataBase.getDBConnection(); // Use a mesma conexão do seu método obterNomesClientes
		        List<String> datasEDescricoeseditar = new ArrayList<>();

		        // Execute uma consulta SQL para obter datas e descrições com base no ID do processo
		        String sql = "SELECT data, descricao, idHistorico FROM historico WHERE idProcesso = ?";
		        
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setInt(1, idProcesso);
		            ResultSet resultSet = statement.executeQuery();

		            // Itere sobre o resultado e adicione as datas e descrições à lista
		            while (resultSet.next()) {
		                String data = resultSet.getString("data");
		                String descricao = resultSet.getString("descricao");
		                int idHistorico = resultSet.getInt("idHistorico");
		                String dataEDescricao = data + ": " + descricao + ": " + idHistorico;
		                datasEDescricoeseditar.add(dataEDescricao);
		            }
		        }

		        return datasEDescricoeseditar;
		    }
		    
		    
		    public Historico buscarHistoricoPorDataDescricao(String data, String descricao) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;

		        try {
		            connection = DataBase.getDBConnection();
		            String query = "SELECT * FROM historico WHERE data = ? AND descricao = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, data);
		            statement.setString(2, descricao);
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                Historico historico = new Historico();
		                historico.setIdHistorico(resultSet.getInt("idHistorico"));
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(resultSet.getString("data"));
		                historico.setDescricao(resultSet.getString("descricao"));
		                return historico;
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

		        return null; // Retorna null se não encontrar nenhum histórico com os parâmetros fornecidos
		    }
		    
		    
		 // Método para buscar idHistorico com base em data e descricao
		    @SuppressWarnings("unused")
			private int buscarIdHistoricoPorDataDescricao(String dataEDescricao) {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        int idHistorico = -1; // Valor padrão se não for encontrado

		        try {
		            connection = com.WernerADV.Software.DataBase.getDBConnection();
		            
		            // Consulta SQL para buscar o idHistorico com base em data e descricao
		            String query = "SELECT idHistorico FROM historico WHERE CONCAT(data, ': ', descricao) = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, dataEDescricao);
		            
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                idHistorico = resultSet.getInt("idHistorico");
		            }
		        } catch (SQLException exception) {
		            logger.log(Level.SEVERE, exception.getMessage());
		        } finally {
		            // Feche as conexões e recursos
		            if (resultSet != null) {
		                try {
		                    resultSet.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (statement != null) {
		                try {
		                    statement.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (connection != null) {
		                try {
		                    connection.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		        }

		        return idHistorico;
		    }
		    
		 // Método para buscar um registro de histórico por idHistorico
		    @SuppressWarnings("unused")
			private Historico buscarHistoricoPorId(int idHistorico) {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        Historico historico = null;

		        try {
		            connection = com.WernerADV.Software.DataBase.getDBConnection();
		            
		            // Consulta SQL para buscar um registro de histórico com base no idHistorico
		            String query = "SELECT * FROM historico WHERE idHistorico = ?";
		            statement = connection.prepareStatement(query);
		            statement.setInt(1, idHistorico);
		            
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                historico = new Historico();
		                historico.setIdHistorico(resultSet.getInt("idHistorico"));
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(resultSet.getString("data"));
		                historico.setDescricao(resultSet.getString("descricao"));
		            }
		        } catch (SQLException exception) {
		            logger.log(Level.SEVERE, exception.getMessage());
		        } finally {
		            // Feche as conexões e recursos
		            if (resultSet != null) {
		                try {
		                    resultSet.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (statement != null) {
		                try {
		                    statement.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (connection != null) {
		                try {
		                    connection.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		        }

		        return historico;
		    }
		    
		    public Historico obterHistoricoComBaseNoTexto(String textoSelecionado) {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        Historico historico = null;

		        try {
		            connection = com.WernerADV.Software.DataBase.getDBConnection();
		            String query = "SELECT * FROM historico WHERE descricao = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, textoSelecionado);
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                resultSet.getInt("idHistorico");
		                resultSet.getInt("idProcesso");
		                resultSet.getString("data");
		                resultSet.getString("descricao");

		                historico = new Historico();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            // Certifique-se de fechar a conexão, o PreparedStatement e o ResultSet aqui
		            if (resultSet != null) {
		                try {
		                    resultSet.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (statement != null) {
		                try {
		                    statement.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (connection != null) {
		                try {
		                    connection.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		        }

		        return historico;
		    }
		    
		    public Historico obterHistoricoComBaseNaDataDescricao(String data, String descricao) {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        Historico historico = new Historico(); // Crie um objeto vazio

		        try {
		            connection = com.WernerADV.Software.DataBase.getDBConnection();
		            String query = "SELECT * FROM historico WHERE data = ? AND descricao = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, data);
		            statement.setString(2, descricao);
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                historico.setIdHistorico(resultSet.getInt("idHistorico"));
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(data);
		                historico.setDescricao(descricao);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            // Certifique-se de fechar a conexão, o PreparedStatement e o ResultSet aqui
		            if (resultSet != null) {
		                try {
		                    resultSet.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (statement != null) {
		                try {
		                    statement.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (connection != null) {
		                try {
		                    connection.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		        }

		        return historico;
		    }
		        
		    public Historico obterHistoricoComBaseNoId(int idHistorico) {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        Historico historico = new Historico(); // Crie um objeto vazio

		        try {
		            connection = com.WernerADV.Software.DataBase.getDBConnection();
		            String query = "SELECT * FROM historico WHERE idHistorico = ?";
		            statement = connection.prepareStatement(query);
		            statement.setInt(1, idHistorico);
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                historico.setIdHistorico(idHistorico); // Defina o idHistorico
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(resultSet.getString("data"));
		                historico.setDescricao(resultSet.getString("descricao"));
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            // Certifique-se de fechar a conexão, o PreparedStatement e o ResultSet aqui
		            if (resultSet != null) {
		                try {
		                    resultSet.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (statement != null) {
		                try {
		                    statement.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		            if (connection != null) {
		                try {
		                    connection.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
		        }

		        return historico;
		    }
		    
		        
		    public Historico obterHistoricoPorDescricao(String descricao) {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        Historico historico = null;

		        try {
		            connection = com.WernerADV.Software.DataBase.getDBConnection();

		            // Consulta SQL para buscar um registro de histórico com base na descrição
		            String query = "SELECT * FROM historico WHERE descricao = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, descricao);

		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                historico = new Historico();
		                historico.setIdHistorico(resultSet.getInt("idHistorico"));
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(resultSet.getString("data"));
		                historico.setDescricao(resultSet.getString("descricao"));
		            }
		        } catch (SQLException exception) {
		            logger.log(Level.SEVERE, exception.getMessage());
		        } finally {
		            // Feche as conexões e recursos
		            if (resultSet != null) {
		                try {
		                    resultSet.close();
		                } catch (SQLException e) {
		                    logger.log(Level.SEVERE, e.getMessage());
		                }
		            }
		            if (statement != null) {
		                try {
		                    statement.close();
		                } catch (SQLException e) {
		                    logger.log(Level.SEVERE, e.getMessage());
		                }
		            }
		            if (connection != null) {
		                try {
		                    connection.close();
		                } catch (SQLException e) {
		                    logger.log(Level.SEVERE, e.getMessage());
		                }
		            }
		        }

		        return historico;
		    }
		
		    
		    public boolean atualizarHistorico(int idHistorico, String novaData, String novaDescricao) {
		        Connection connection = null;
		        PreparedStatement statement = null;

		        try {
		            connection = com.WernerADV.Software.DataBase.getDBConnection();
		            String query = "UPDATE historico SET data = ?, descricao = ? WHERE idHistorico = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, novaData);
		            statement.setString(2, novaDescricao);
		            statement.setInt(3, idHistorico);

		            int rowsAffected = statement.executeUpdate();

		            // Verifique se a atualização foi bem-sucedida (verifique se alguma linha foi afetada)
		            return rowsAffected > 0;
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return false; // Tratamento de erros
		        } finally {
		            // Certifique-se de fechar a conexão e o PreparedStatement aqui
		            // ...
		        }
		    }

		    public List<Historico> getHistoricoPorIdProcesso(int idProcesso) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        List<Historico> historicos = new ArrayList<>();

		        try {
		            connection = DataBase.getDBConnection();
		            String query = "SELECT * FROM historico WHERE idProcesso = ?";
		            statement = connection.prepareStatement(query);
		            statement.setInt(1, idProcesso);
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                Historico historico = new Historico();
		                historico.setIdHistorico(resultSet.getInt("idHistorico"));
		                historico.setIdProcesso(resultSet.getInt("idProcesso"));
		                historico.setData(resultSet.getString("data"));
		                historico.setDescricao(resultSet.getString("descricao"));
		                historicos.add(historico);
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

		        return historicos;
		    }
		    
		    
		    public List<String> obterAndamentosPorIdProcEdit(int idProc) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        List<String> andamentos = new ArrayList<>();

		        try {
		            connection = DataBase.getDBConnection();
		            String query = "SELECT data, descricao FROM historico WHERE idHistorico = ?";
		            statement = connection.prepareStatement(query);
		            statement.setInt(1, idProc);
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                String data = resultSet.getString("data");
		                String descricao = resultSet.getString("descricao");
		                String andamento = data + ": " + descricao;
		                andamentos.add(andamento);
		            }
		        } catch (SQLException exception) {
		            // Lide com exceções de SQL aqui, se necessário
		            exception.printStackTrace();
		        } finally {
		            // Feche as conexões e recursos
		            if (resultSet != null) {
		                resultSet.close();
		            }
		            if (statement != null) {
		                statement.close();
		            }
		            if (connection != null) {
		                connection.close();
		            }
		        }

		        return andamentos;
		    }
		    
		    public List<String> getDatasEDescricoesPorIdProcesso1(int idProcesso) throws SQLException {
		    	 Connection connection = DataBase.getDBConnection(); // Use a mesma conexão do seu método obterNomesClientes
		         List<String> datasEDescricoes = new ArrayList<>();

		         // Execute uma consulta SQL para obter datas e descrições com base no ID do processo
		         String sql = "SELECT data, descricao FROM historico WHERE idProcesso = ?";
		         
		         try (PreparedStatement statement = connection.prepareStatement(sql)) {
		             statement.setInt(1, idProcesso);
		             ResultSet resultSet = statement.executeQuery();

		             // Criar uma lista para armazenar objetos de Andamento
		             List<Andamento> andamentos = new ArrayList<>();

		             // Iterar sobre o resultado e adicionar os andamentos à lista
		             while (resultSet.next()) {
		                 String dataString = resultSet.getString("data");
		                 String descricao = resultSet.getString("descricao");

		                 // Parse da data no formato "dd/MM/yyyy"
		                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		                 Date data = dateFormat.parse(dataString);

		                 // Crie um objeto de Andamento com data e descrição
		                 Andamento andamento = new Andamento(data, descricao);

		                 // Adicione o andamento à lista
		                 andamentos.add(andamento);
		             }

		             // Ordene a lista de andamentos pela data (do mais recente para o mais antigo)
		             Collections.sort(andamentos, Comparator.comparing(Andamento::getData).reversed());

		             // Converta a lista ordenada de volta para uma lista de strings para retornar
		             for (Andamento andamento : andamentos) {
		                 // Formatando a data de volta para o formato "dd/MM/yyyy"
		                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		                 String dataFormatada = dateFormat.format(andamento.getData());

		                 String dataEDescricao = dataFormatada + ": " + andamento.getDescricao();
		                 datasEDescricoes.add(dataEDescricao);
		             }
		         } catch (SQLException | ParseException exception) {
		             exception.printStackTrace();
		         }

		         return datasEDescricoes;
		     }

		     // Classe auxiliar para representar um Andamento
		     private static class Andamento {
		         private final Date data;
		         private final String descricao;

		         public Andamento(Date data, String descricao) {
		             this.data = data;
		             this.descricao = descricao;
		         }

		         public Date getData() {
		             return data;
		         }

		         public String getDescricao() {
		             return descricao;
		         }
		     }
		    
		    
}