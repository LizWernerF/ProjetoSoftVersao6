package guiWAdv;

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

public class AndamentoRecDao {
    private static final Logger logger = Logger.getLogger(AndamentoRecDao.class.getName());

    public List<AndamentoRec> getAllAndamentoRecs() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<AndamentoRec> andamentoRecs = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            statement = connection.prepareStatement("SELECT * FROM andamento_rec");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AndamentoRec andamentoRec = new AndamentoRec();
                andamentoRec.setIdAndamento(resultSet.getInt("idAndamento"));
                andamentoRec.setIdRecurso(resultSet.getInt("idRecurso"));
                andamentoRec.setDataRecurso(resultSet.getString("dataRecurso"));
                andamentoRec.setDescricaoRecurso(resultSet.getString("descricaoRecurso"));
                andamentoRecs.add(andamentoRec);
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

        return andamentoRecs;
    }

    public AndamentoRec getAndamentoRecById(int idAndamento) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        AndamentoRec andamentoRec = null;

        try {
            connection = DataBase.getDBConnection();
            statement = connection.prepareStatement("SELECT * FROM andamento_rec WHERE idAndamento = ?");
            statement.setInt(1, idAndamento);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                andamentoRec = new AndamentoRec();
                andamentoRec.setIdAndamento(resultSet.getInt("idAndamento"));
                andamentoRec.setIdRecurso(resultSet.getInt("idRecurso"));
                andamentoRec.setDataRecurso(resultSet.getString("dataRecurso"));
                andamentoRec.setDescricaoRecurso(resultSet.getString("descricaoRecurso"));
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

        return andamentoRec;
    }

    public void saveAndamentoRec(AndamentoRec andamentoRec) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DataBase.getDBConnection();
            connection.setAutoCommit(false);
            String query = "INSERT INTO andamento_rec(idRecurso, dataRecurso, descricaoRecurso) VALUES(?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            statement.setInt(counter++, andamentoRec.getIdRecurso());
            statement.setString(counter++, andamentoRec.getDataRecurso());
            statement.setString(counter++, andamentoRec.getDescricaoRecurso());
            statement.executeUpdate();
            connection.commit();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                andamentoRec.setIdAndamento(resultSet.getInt(1));
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

    public List<String> getDatasEDescricoesPorIdRecurso(int idRecurso) throws SQLException {
        Connection connection = DataBase.getDBConnection();
        List<String> datasEDescricoesRec = new ArrayList<>();

        String sql = "SELECT dataRecurso, descricaoRecurso FROM andamento_rec WHERE idRecurso = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecurso);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dataRecurso = resultSet.getString("dataRecurso");
                String descricaoRecurso = resultSet.getString("descricaoRecurso");
                String dataEDescricaoRec = dataRecurso + ": " + descricaoRecurso;

                datasEDescricoesRec.add(dataEDescricaoRec);
            }
        }

        return datasEDescricoesRec;
    }

    public List<String> getDatasEDescricoesPorIdRecursoEditar(int idRecurso) throws SQLException {
        Connection connection = DataBase.getDBConnection();
        List<String> datasEDescricoesRecEdit = new ArrayList<>();

        String sql = "SELECT dataRecurso, descricaoRecurso FROM andamento_rec WHERE idRecurso = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecurso);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dataRecurso = resultSet.getString("dataRecurso");
                String descricaoRecurso = resultSet.getString("descricaoRecurso");
                String dataEDescricaoRec = dataRecurso + ": " + descricaoRecurso;

                datasEDescricoesRecEdit.add(dataEDescricaoRec);
            }
        }

        return datasEDescricoesRecEdit;
    }
    
    
    @SuppressWarnings("unused")
	private int buscarIdAndamentoPorDataDescricao(String dataEDescricaoRec) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int idAndamento = -1; // Valor padrão se não for encontrado

        try {
            connection = com.WernerADV.Software.DataBase.getDBConnection();
            
            // Consulta SQL para buscar o idHistorico com base em data e descricao
            String query = "SELECT idAndamento FROM andamento_rec WHERE CONCAT(dataRecurso, ': ', descricaoRecurso) = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, dataEDescricaoRec);
            
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	idAndamento = resultSet.getInt("idAndamento");
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

        return idAndamento;
    }
    
    
    public AndamentoRecDao obterAndamentoComBaseNoTexto(String textoSelecionado) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        AndamentoRecDao AndamentoRecDao = null;

        try {
            connection = com.WernerADV.Software.DataBase.getDBConnection();
            String query = "SELECT * FROM andamento_rec WHERE descricaoRecurso = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, textoSelecionado);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idAndamento = resultSet.getInt("idAndamento");
                int idRecurso = resultSet.getInt("idRecurso");
                String dataRecurso = resultSet.getString("dataRecurso");
                String descricaoRecurso = resultSet.getString("descricaoRecurso");

                AndamentoRecDao = new AndamentoRecDao();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
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

        return AndamentoRecDao;
    }
    
    public boolean inserirAndamentoRec(int idRec, String dataRecurso, String descricaoRecurso) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DataBase.getDBConnection();
            String query = "INSERT INTO andamento_rec (idRecurso, dataRecurso, descricaoRecurso) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idRec);
            statement.setString(2, dataRecurso);
            statement.setString(3, descricaoRecurso);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            return false;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    public List<String> obterAndamentosPorIdRec(int idRec) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> andamentos = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            String query = "SELECT dataRecurso, descricaoRecurso FROM andamento_rec WHERE idRecurso = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idRec);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dataRecurso = resultSet.getString("dataRecurso");
                String descricaoRecurso = resultSet.getString("descricaoRecurso");
                String andamento = dataRecurso + ": " + descricaoRecurso;
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

    public List<String> obterAndamentosPorIdRecEdit(int idRec) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> andamentos = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            String query = "SELECT dataRecurso, descricaoRecurso FROM andamento_rec WHERE idRecurso = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idRec);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dataRecurso = resultSet.getString("dataRecurso");
                String descricaoRecurso = resultSet.getString("descricaoRecurso");
                String andamento = dataRecurso + ": " + descricaoRecurso;
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
    
    
    public AndamentoRec obterHistoricoComBaseNaDataDescricao(String dataRecurso, String descricaoRecurso) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        AndamentoRec andamentoRec = new AndamentoRec(); // Crie um objeto vazio

        try {
            connection = com.WernerADV.Software.DataBase.getDBConnection();
            String query = "SELECT * FROM andamento_rec WHERE dataRecurso = ? AND descricaoRecurso = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, dataRecurso);
            statement.setString(2, descricaoRecurso);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	andamentoRec.setIdAndamento(resultSet.getInt("IdAndamento"));
            	andamentoRec.setIdRecurso(resultSet.getInt("idRecurso"));
            	andamentoRec.setDataRecurso(dataRecurso);
            	andamentoRec.setDescricaoRecurso(descricaoRecurso);
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

        return andamentoRec;
    }
    
    public boolean atualizarAndamentoRec(int IdAndamento, String novaDataRec, String novaDescricaoRec) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = com.WernerADV.Software.DataBase.getDBConnection();
            String query = "UPDATE andamento_rec SET dataRecurso = ?, descricaoRecurso = ? WHERE idAndamento = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, novaDataRec);
            statement.setString(2, novaDescricaoRec);
            statement.setInt(3, IdAndamento);

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

    public List<AndamentoRec> getHistoricoPorIdProcesso(int idRecurso) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<AndamentoRec> AndamentoRecurso = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            String query = "SELECT * FROM andamento_rec WHERE idRecurso = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idRecurso);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AndamentoRec AndamentoRec = new AndamentoRec();
                AndamentoRec.setIdAndamento(resultSet.getInt("idAndamento"));
                AndamentoRec.setIdRecurso(resultSet.getInt("idRecurso"));
                AndamentoRec.setDataRecurso(resultSet.getString("dataRecurso"));
                AndamentoRec.setDescricaoRecurso(resultSet.getString("descricaoRecurso"));
                AndamentoRec.add(AndamentoRec);
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

        return AndamentoRecurso;
    }
    
    
	
    
}

