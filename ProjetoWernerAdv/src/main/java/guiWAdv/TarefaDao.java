package guiWAdv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.WernerADV.Software.DataBase;



public class TarefaDao {
	
	
	private static final Logger logger = Logger.getLogger(TarefaDao.class.getName());

    public List<Tarefa> getAllTarefas() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tarefa> tarefas = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            String query = "SELECT idTarefa, dataCriacao, descricao, responsavel, cliente, processo, dataLimite, status, concluida FROM tarefa";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setIdTarefa(resultSet.getInt("idTarefa"));
                tarefa.setDataCriacao(resultSet.getString("dataCriacao"));
                tarefa.setDescricao(resultSet.getString("descricao"));
                tarefa.setResponsavel(resultSet.getString("responsavel"));
                tarefa.setCliente(resultSet.getString("cliente"));
                tarefa.setProcesso(resultSet.getString("processo"));
                tarefa.setDataLimite(resultSet.getString("dataLimite"));
                tarefa.setStatus(resultSet.getString("status"));
                tarefa.setConcluida(resultSet.getBoolean("concluida"));
                tarefas.add(tarefa);
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
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

        return tarefas;
    }

    public void saveTarefa(Tarefa tarefa) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DataBase.getDBConnection();
            String query = "INSERT INTO tarefa(dataCriacao, descricao, responsavel, cliente, processo, dataLimite, status, concluida) " +
                           "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            // Definir os valores dos parâmetros
            int counter = 1;
            statement.setString(counter++, tarefa.getDataCriacao());
            statement.setString(counter++, tarefa.getDescricao());
            statement.setString(counter++, tarefa.getResponsavel());
            statement.setString(counter++, tarefa.getCliente());
            statement.setString(counter++, tarefa.getProcesso());
            statement.setString(counter++, tarefa.getDataLimite());
            statement.setString(counter++, tarefa.getStatus());
            statement.setBoolean(counter++, tarefa.isConcluida());
            
            // Executa a query
            statement.executeUpdate();

            // Recupera a chave gerada (ID da tarefa)
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                tarefa.setIdTarefa(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw exception; // Rethrow a exceção para ser tratada no nível superior
        } finally {
            // Fecha os recursos de forma segura
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

  
    public void atualizarTarefa(Tarefa tarefa) {
        String sql = "UPDATE tarefa SET descricao = ?, responsavel = ?, cliente = ?, processo = ?, dataLimite = ?, status = ? WHERE idTarefa = ?";

        try (Connection conn = DataBase.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Converte a dataLimite para o formato aaaa-mm-dd
            String dataLimiteFormatada = LocalDate.parse(tarefa.getDataLimite(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

            pstmt.setString(1, tarefa.getDescricao());
            pstmt.setString(2, tarefa.getResponsavel());
            pstmt.setString(3, tarefa.getCliente());
            pstmt.setString(4, tarefa.getProcesso());
            pstmt.setString(5, dataLimiteFormatada); // Insere a data no formato correto
            pstmt.setString(6, tarefa.getStatus());
            pstmt.setInt(7, tarefa.getIdTarefa()); // Usando o ID da tarefa para identificar qual tarefa atualizar

            int rowsAffected = pstmt.executeUpdate(); // Executa a atualização
            if (rowsAffected > 0) {
                System.out.println("Tarefa atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma tarefa encontrada com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Tarefa> getTarefasConcluidas() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tarefa> tarefasConcluidas = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            String query = "SELECT idTarefa, dataCriacao, descricao, responsavel, cliente, processo, dataLimite, status, concluida FROM tarefa WHERE concluida = true";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setIdTarefa(resultSet.getInt("idTarefa"));
                tarefa.setDataCriacao(resultSet.getString("dataCriacao"));
                tarefa.setDescricao(resultSet.getString("descricao"));
                tarefa.setResponsavel(resultSet.getString("responsavel"));
                tarefa.setCliente(resultSet.getString("cliente"));
                tarefa.setProcesso(resultSet.getString("processo"));
                tarefa.setDataLimite(resultSet.getString("dataLimite"));
                tarefa.setStatus(resultSet.getString("status"));
                tarefa.setConcluida(resultSet.getBoolean("concluida"));
                tarefasConcluidas.add(tarefa);
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
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

        return tarefasConcluidas;
    }
    
       
    public void atualizarTarefa(String descricao, String responsavel, String cliente, String processo, String dataLimite) {
        String sql = "UPDATE tarefa SET descricao = ?, responsavel = ?, cliente = ?, processo = ?, dataLimite = ? WHERE descricao = ? AND responsavel = ?";

        try (Connection conn = DriverManager.getConnection("sua_url_do_banco_de_dados", "seu_usuario", "sua_senha");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, descricao);
            pstmt.setString(2, responsavel);
            pstmt.setString(3, cliente);
            pstmt.setString(4, processo);
            pstmt.setString(5, dataLimite);
            pstmt.setString(6, descricao); // Para identificação
            pstmt.setString(7, responsavel); // Para identificação

            int rowsAffected = pstmt.executeUpdate(); // Executa a atualização
            if (rowsAffected > 0) {
                System.out.println("Tarefa atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma tarefa encontrada com os critérios fornecidos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int buscarIdTarefaPorDataDescricao(String dataCriacao, String descricao) throws SQLException {
        int idTarefa = -1;
        String sql = "SELECT idTarefa FROM tarefa WHERE dataCriacao = ? AND descricao = ?";

        // Converte a data para o formato yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dataCriacao, formatter); // Converte a string para LocalDate
        String dataCriacaoFormatada = localDate.toString(); // Converte para o formato yyyy-MM-dd

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

       

            stmt.setString(1, dataCriacaoFormatada); // Define a data formatada como parâmetro
            stmt.setString(2, descricao); // Define a descrição como parâmetro

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idTarefa = rs.getInt("idTarefa"); // Obtém o ID da tarefa
                    
                } else {
                  
                }
            }
        } catch (SQLException exception) {
            Logger.getLogger(TarefaDao.class.getName()).log(Level.SEVERE, null, exception);
        }

        return idTarefa;
    }

    public String getDataLimiteById(int idTarefa) throws SQLException {
        String dataLimite = null; // Inicializa a variável para armazenar a data limite
        String sql = "SELECT dataLimite FROM tarefa WHERE idTarefa = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idTarefa); // Define o ID da tarefa como parâmetro

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dataLimite = rs.getString("dataLimite"); // Obtém a data limite da tarefa
                }
            }
        } catch (SQLException exception) {
            Logger.getLogger(TarefaDao.class.getName()).log(Level.SEVERE, null, exception);
        }

        return dataLimite; // Retorna a data limite encontrada ou null se não houver
    }
    
    public void marcarComoFeito(int idTarefa) {
        String sql = "UPDATE tarefa SET concluida = true, status = 'concluída' WHERE idTarefa = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idTarefa); // Define o ID da tarefa como parâmetro
            int rowsAffected = pstmt.executeUpdate(); // Executa a atualização
            
            if (rowsAffected > 0) {
              
            } else {
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void marcarComoPendente(int idTarefa) {
        String sql = "UPDATE tarefa SET concluida = false, status = 'pendente' WHERE idTarefa = ?";

        try (Connection connection = DataBase.getDBConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idTarefa); // Define o ID da tarefa como parâmetro
            int rowsAffected = pstmt.executeUpdate(); // Executa a atualização

            if (rowsAffected > 0) {
                System.out.println("Tarefa marcada como pendente com sucesso.");
            } else {
                System.out.println("Erro: nenhuma tarefa foi atualizada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    }
