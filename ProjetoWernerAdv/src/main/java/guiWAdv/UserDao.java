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

public class UserDao implements IUserDao{

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

	public boolean userExists(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		List<User> users = new ArrayList<>();

		try {
			connection = com.WernerADV.Software.DataBase.getDBConnection();
			connection.setAutoCommit(false);
			String query = "SELECT id, nome, telefone, cpf, rg, nacionalidade, estadocivil, profissao, idfuncional, datanascimento, endereco, cidade, estado, cep, mat1, ref1, cargh1, datainicio1, cargo1, nivel1, trienio1, dataaposentadoria1,mat2, ref2, cargh2, datainicio2, cargo2, nivel2, trienio2, dataposentadoria2 FROM user WHERE nome = ?";
			statement = connection.prepareStatement(query);
			int counter = 1;
			statement.setString(counter++, username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt(1));
				user.setnome(resultSet.getString(2));
				user.settelefone(resultSet.getString(3));
				user.setcpf(resultSet.getString(4));
				user.setrg(resultSet.getString(5));
				user.setnacionalidade(resultSet.getString(6));
				user.setestadocivil(resultSet.getString(7));
				user.setprofissao(resultSet.getString(8));
				user.setidfuncional(resultSet.getString(9));
				user.setdatanascimento(resultSet.getString(10));
				user.setendereco(resultSet.getString(11));
				user.setcidade(resultSet.getString(12));
				user.setestado(resultSet.getString(13));
				user.setcep(resultSet.getString(14));
				user.setmat1(resultSet.getString(15));
				user.setref1(resultSet.getString(16));
				user.setcargh1(resultSet.getString(17));
				user.setdatainicio1(resultSet.getString(18));
				user.setcargo1(resultSet.getString(19));
				user.setnivel1(resultSet.getString(20));
				user.settrienio1(resultSet.getString(21));
				user.setdataaposentadoria1(resultSet.getString(22));
				user.setmat2(resultSet.getString(23));
				user.setref2(resultSet.getString(24));
				user.setcargh2(resultSet.getString(25));
				user.setdatainicio2(resultSet.getString(26));
				user.setcargo2(resultSet.getString(27));
				user.setnivel2(resultSet.getString(28));
				user.settrienio2(resultSet.getString(29));
				user.setdataaposentadoria2(resultSet.getString(30));
				users.add(user);
			}

			return users.isEmpty() ? false : true;
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

		return users.isEmpty() ? false : true;
	}

	public int saveUser(User user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = com.WernerADV.Software.DataBase.getDBConnection();
		
			String query = "INSERT INTO user(nome, telefone, cpf, rg, nacionalidade, estadocivil, profissao, idfuncional, datanascimento, endereco, cidade, estado, cep, mat1, ref1, cargh1, datainicio1, cargo1, nivel1, trienio1, dataaposentadoria1,mat2, ref2, cargh2, datainicio2, cargo2, nivel2, trienio2, dataposentadoria2) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			statement.setString(counter++, user.getnome());
			statement.setString(counter++, user.gettelefone());
			statement.setString(counter++, user.getcpf());
			statement.setString(counter++, user.getrg());
			statement.setString(counter++, user.getnacionalidade());
			statement.setString(counter++, user.getestadocivil());
			statement.setString(counter++, user.getprofissao());
			statement.setString(counter++, user.getidfuncional());
			statement.setString(counter++, user.getdatanascimento());
			statement.setString(counter++, user.getendereco());
			statement.setString(counter++, user.getcidade());
			statement.setString(counter++, user.getestado());
			statement.setString(counter++, user.getcep());
			statement.setString(counter++, user.getmat1());
			statement.setString(counter++, user.getref1());
			statement.setString(counter++, user.getcargh1());
			statement.setString(counter++, user.getdatainicio1());
			statement.setString(counter++, user.getcargo1());
			statement.setString(counter++, user.getnivel1());
			statement.setString(counter++, user.gettrienio1());
			statement.setString(counter++, user.getdataaposentadoria1());
			statement.setString(counter++, user.getmat2());
			statement.setString(counter++, user.getref2());
			statement.setString(counter++, user.getcargh2());
			statement.setString(counter++, user.getdatainicio2());
			statement.setString(counter++, user.getcargo2());
			statement.setString(counter++, user.getnivel2());
			statement.setString(counter++, user.gettrienio2());
			statement.setString(counter++, user.getdataaposentadoria2());			
			
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

		return 0;
	}
	// Outros métodos da classe...

    public List<User> obterTodosClientes() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> clientes = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
            String query = "SELECT id, nome, telefone, cpf, rg, nacionalidade, estadocivil, profissao, idfuncional, datanascimento, endereco, cidade, estado, cep, mat1, ref1, cargh1, datainicio1, cargo1, nivel1, trienio1, dataaposentadoria1, mat2, ref2, cargh2, datainicio2, cargo2, nivel2, trienio2, dataposentadoria2 FROM user";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setnome(resultSet.getString("nome"));
                user.settelefone(resultSet.getString("telefone"));
                user.setcpf(resultSet.getString("cpf"));
                user.setrg(resultSet.getString("rg"));
                user.setnacionalidade(resultSet.getString("nacionalidade"));
                user.setestadocivil(resultSet.getString("estadocivil"));
                user.setprofissao(resultSet.getString("profissao"));
                user.setidfuncional(resultSet.getString("idfuncional"));
                user.setdatanascimento(resultSet.getString("datanascimento"));
                user.setendereco(resultSet.getString("endereco"));
                user.setcidade(resultSet.getString("cidade"));
                user.setestado(resultSet.getString("estado"));
                user.setcep(resultSet.getString("cep"));
                user.setmat1(resultSet.getString("mat1"));
                user.setref1(resultSet.getString("ref1"));
                user.setcargh1(resultSet.getString("cargh1"));
                user.setdatainicio1(resultSet.getString("datainicio1"));
                user.setcargo1(resultSet.getString("cargo1"));
                user.setnivel1(resultSet.getString("nivel1"));
                user.settrienio1(resultSet.getString("trienio1"));
                user.setdataaposentadoria1(resultSet.getString("dataaposentadoria1"));
                user.setmat2(resultSet.getString("mat2"));
                user.setref2(resultSet.getString("ref2"));
                user.setcargh2(resultSet.getString("cargh2"));
                user.setdatainicio2(resultSet.getString("datainicio2"));
                user.setcargo2(resultSet.getString("cargo2"));
                user.setnivel2(resultSet.getString("nivel2"));
                user.settrienio2(resultSet.getString("trienio2"));
                user.setdataaposentadoria2(resultSet.getString("dataaposentadoria2"));
                clientes.add(user);
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
            // Feche a conexão, o statement e o resultSet no bloco finally
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

        return clientes;
    }
    
    public List<String> obterNomesClientes() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> nomesClientes = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
            String query = "SELECT nome FROM user";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                nomesClientes.add(resultSet.getString("nome"));
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
            // Feche a conexão, o statement e o resultSet no bloco finally
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

        return nomesClientes;
    }
    
	public List<String> obterNomesClientes2() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> nomesClientes2 = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
            String query = "SELECT nome FROM user";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                nomesClientes2.add(resultSet.getString("nome"));
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
            // Feche a conexão, o statement e o resultSet no bloco finally
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

        return nomesClientes2;
    }

	@Override
	public void inserirCliente(User user) throws SQLException {
	
		
	}

	public void atualizaCliente(User user) throws SQLException {
	    
	}

	public boolean atualizarCliente(int idCliente, String novoNome, String novoTelefone, String novoCPF, String novoRG,
	        String novaNacionalidade, String novoEstadoCivil, String novaProfissao, String novoIdFuncional,
	        String novaDataNascimento, String novoEndereco, String novaCidade, String novoEstado, String novoCEP,
	        String novoMat1, String novoRef1, String novoCargah1, String novaDataInicio1, String novoCargo1, String novoNivel1,
	        String novoTrienio1, String novaDataAposentadoria1, String novoMat2, String novoRef2, String novoCargah2,
	        String novaDataInicio2, String novoCargo2, String novoNivel2, String novoTrienio2, String novaDataAposentadoria2) {
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
            connection = com.WernerADV.Software.DataBase.getDBConnection();
	        String query = "UPDATE user SET nome = ?, telefone = ?, cpf = ?, rg = ?, nacionalidade = ?, estadocivil = ?,"
	                + " profissao = ?, idfuncional = ?, datanascimento = ?, endereco = ?, cidade = ?, estado = ?, cep = ?,"
	                + " mat1 = ?, ref1 = ?, cargh1 = ?, datainicio1 = ?, cargo1 = ?, nivel1 = ?, trienio1 = ?,"
	                + " dataaposentadoria1 = ?, mat2 = ?, ref2 = ?, cargh2 = ?, datainicio2 = ?, cargo2 = ?, nivel2 = ?,"
	                + " trienio2 = ?, dataposentadoria2 = ? WHERE id = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, novoNome);
	        statement.setString(2, novoTelefone);
	        statement.setString(3, novoCPF);
	        statement.setString(4, novoRG);
	        statement.setString(5, novaNacionalidade);
	        statement.setString(6, novoEstadoCivil);
	        statement.setString(7, novaProfissao);
	        statement.setString(8, novoIdFuncional);
	        statement.setString(9, novaDataNascimento);
	        statement.setString(10, novoEndereco);
	        statement.setString(11, novaCidade);
	        statement.setString(12, novoEstado);
	        statement.setString(13, novoCEP);
	        statement.setString(14, novoMat1);
	        statement.setString(15, novoRef1);
	        statement.setString(16, novoCargah1);
	        statement.setString(17, novaDataInicio1);
	        statement.setString(18, novoCargo1);
	        statement.setString(19, novoNivel1);
	        statement.setString(20, novoTrienio1);
	        statement.setString(21, novaDataAposentadoria1);
	        statement.setString(22, novoMat2);
	        statement.setString(23, novoRef2);
	        statement.setString(24, novoCargah2);
	        statement.setString(25, novaDataInicio2);
	        statement.setString(26, novoCargo2);
	        statement.setString(27, novoNivel2);
	        statement.setString(28, novoTrienio2);
	        statement.setString(29, novaDataAposentadoria2);
	        statement.setInt(30, idCliente);

	        int rowsAffected = statement.executeUpdate();

	        // Verifique se a atualização foi bem-sucedida (verifique se alguma linha foi afetada)
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Tratamento de erros
	    } finally {
	        // Certifique-se de fechar a conexão e o PreparedStatement aqui
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	


	
	
	@Override
	public void excluiCliente(User user) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User buscaCliente(User user) throws SQLException {
		 Connection connection = DataBase.getDBConnection();
		 PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE nome = ?");
		 statement.setString(1, user.getnome());
		 ResultSet resultSet = statement.executeQuery();
		 if (resultSet.next()) {
			 User cliente = new User();
			 cliente.setId(resultSet.getInt("id"));
			    cliente.setnome(resultSet.getString("nome"));
			    cliente.settelefone(resultSet.getString("telefone"));
			    cliente.setcpf(resultSet.getString("cpf"));
			    cliente.setrg(resultSet.getString("rg"));
			    cliente.setnacionalidade(resultSet.getString("nacionalidade"));
			    cliente.setestadocivil(resultSet.getString("estadocivil"));
			    cliente.setprofissao(resultSet.getString("profissao"));
			    cliente.setidfuncional(resultSet.getString("idfuncional"));
			    cliente.setdatanascimento(resultSet.getString("datanascimento"));
			    cliente.setendereco(resultSet.getString("endereco"));
			    cliente.setcidade(resultSet.getString("cidade"));
			    cliente.setestado(resultSet.getString("estado"));
			    cliente.setcep(resultSet.getString("cep"));
			    cliente.setmat1(resultSet.getString("mat1"));
			    cliente.setref1(resultSet.getString("ref1"));
			    cliente.setcargh1(resultSet.getString("cargh1"));
			    cliente.setdatainicio1(resultSet.getString("datainicio1"));
			    cliente.setcargo1(resultSet.getString("cargo1"));
			    cliente.setnivel1(resultSet.getString("nivel1"));
			    cliente.settrienio1(resultSet.getString("trienio1"));
			    cliente.setdataaposentadoria1(resultSet.getString("dataaposentadoria1"));
			    cliente.setmat2(resultSet.getString("mat2"));
			    cliente.setref2(resultSet.getString("ref2"));
			    cliente.setcargh2(resultSet.getString("cargh2"));
			    cliente.setdatainicio2(resultSet.getString("datainicio2"));
			    cliente.setcargo2(resultSet.getString("cargo2"));
			    cliente.setnivel2(resultSet.getString("nivel2"));
			    cliente.settrienio2(resultSet.getString("trienio2"));
			    cliente.setdataaposentadoria2(resultSet.getString("dataposentadoria2"));
			    return cliente;

		  } else {
			  return null;
		  }
	}

	public List<User> buscaClientesPorLetrasIniciaisDoNome(String letrasIniciais) throws SQLException {
	    Connection connection = DataBase.getDBConnection();
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE nome LIKE ?");
	    statement.setString(1, letrasIniciais + "%");
	    ResultSet resultSet = statement.executeQuery();

	    List<User> clientes = new ArrayList<>();

	    while (resultSet.next()) {
	        User cliente = new User();
	        cliente.setId(resultSet.getInt("id"));
	        cliente.setnome(resultSet.getString("nome"));
	        // Configure os outros atributos do cliente conforme necessário
	        clientes.add(cliente);
	    }

	    return clientes;
	}
	
	@Override
	public List<User> buscaClientes() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User buscaClientePeloNome(String nomeCliente) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = DataBase.getDBConnection();
	        String query = "SELECT * FROM user WHERE nome = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, nomeCliente);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            User cliente = new User();
	            cliente.setId(resultSet.getInt("id"));
	            cliente.setnome(resultSet.getString("nome"));
	            cliente.settelefone(resultSet.getString("telefone"));
	            cliente.setcpf(resultSet.getString("cpf"));
	            cliente.setrg(resultSet.getString("rg"));
	            cliente.setnacionalidade(resultSet.getString("nacionalidade"));
	            cliente.setestadocivil(resultSet.getString("estadocivil"));
	            cliente.setprofissao(resultSet.getString("profissao"));
	            cliente.setidfuncional(resultSet.getString("idfuncional"));
	            cliente.setdatanascimento(resultSet.getString("datanascimento"));
	            cliente.setendereco(resultSet.getString("endereco"));
	            cliente.setcidade(resultSet.getString("cidade"));
	            cliente.setestado(resultSet.getString("estado"));
	            cliente.setcep(resultSet.getString("cep"));
	            cliente.setmat1(resultSet.getString("mat1"));
	            cliente.setref1(resultSet.getString("ref1"));
	            cliente.setcargh1(resultSet.getString("cargh1"));
	            cliente.setdatainicio1(resultSet.getString("datainicio1"));
	            cliente.setcargo1(resultSet.getString("cargo1"));
	            cliente.setnivel1(resultSet.getString("nivel1"));
	            cliente.settrienio1(resultSet.getString("trienio1"));
	            cliente.setdataaposentadoria1(resultSet.getString("dataaposentadoria1"));
	            cliente.setmat2(resultSet.getString("mat2"));
	            cliente.setref2(resultSet.getString("ref2"));
	            cliente.setcargh2(resultSet.getString("cargh2"));
	            cliente.setdatainicio2(resultSet.getString("datainicio2"));
	            cliente.setcargo2(resultSet.getString("cargo2"));
	            cliente.setnivel2(resultSet.getString("nivel2"));
	            cliente.settrienio2(resultSet.getString("trienio2"));
	            cliente.setdataaposentadoria2(resultSet.getString("dataposentadoria2"));
	            return cliente;
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
	        // Feche a conexão, o statement e o resultSet no bloco finally
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

	    return null;
	}
	
	 public List<String> buscarTodosNomes() throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<String> nomes = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT nome FROM user";
	            statement = connection.prepareStatement(query);
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                nomes.add(resultSet.getString("nome"));
	            }
	        } catch (SQLException exception) {
	            logger.log(Level.SEVERE, exception.getMessage());
	        } finally {
	        	 // Feche a conexão, o statement e o resultSet no bloco finally
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

		    return nomes;
		}
	
	public User buscaClientePeloNomeTabEditar(String nomeCliente) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = DataBase.getDBConnection();
	        String query = "SELECT * FROM user WHERE nome = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, nomeCliente);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            User cliente = new User();
	            cliente.setId(resultSet.getInt("id"));
	            cliente.setnome(resultSet.getString("nome"));
	            cliente.settelefone(resultSet.getString("telefone"));
	            cliente.setcpf(resultSet.getString("cpf"));
	            cliente.setrg(resultSet.getString("rg"));
	            cliente.setnacionalidade(resultSet.getString("nacionalidade"));
	            cliente.setestadocivil(resultSet.getString("estadocivil"));
	            cliente.setprofissao(resultSet.getString("profissao"));
	            cliente.setidfuncional(resultSet.getString("idfuncional"));
	            cliente.setdatanascimento(resultSet.getString("datanascimento"));
	            cliente.setendereco(resultSet.getString("endereco"));
	            cliente.setcidade(resultSet.getString("cidade"));
	            cliente.setestado(resultSet.getString("estado"));
	            cliente.setcep(resultSet.getString("cep"));
	            cliente.setmat1(resultSet.getString("mat1"));
	            cliente.setref1(resultSet.getString("ref1"));
	            cliente.setcargh1(resultSet.getString("cargh1"));
	            cliente.setdatainicio1(resultSet.getString("datainicio1"));
	            cliente.setcargo1(resultSet.getString("cargo1"));
	            cliente.setnivel1(resultSet.getString("nivel1"));
	            cliente.settrienio1(resultSet.getString("trienio1"));
	            cliente.setdataaposentadoria1(resultSet.getString("dataaposentadoria1"));
	            cliente.setmat2(resultSet.getString("mat2"));
	            cliente.setref2(resultSet.getString("ref2"));
	            cliente.setcargh2(resultSet.getString("cargh2"));
	            cliente.setdatainicio2(resultSet.getString("datainicio2"));
	            cliente.setcargo2(resultSet.getString("cargo2"));
	            cliente.setnivel2(resultSet.getString("nivel2"));
	            cliente.settrienio2(resultSet.getString("trienio2"));
	            cliente.setdataaposentadoria2(resultSet.getString("dataposentadoria2"));
	            return cliente;
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
	        // Feche a conexão, o statement e o resultSet no bloco finally
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

	    return null;
	}
	
	
	public boolean verificaMatricula(String matricula) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = DataBase.getDBConnection();
	        String query = "SELECT mat1, mat2 FROM user WHERE mat1 = ? OR mat2 = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, matricula);
	        statement.setString(2, matricula);
	        resultSet = statement.executeQuery();

	        return resultSet.next(); // Se houver correspondência em mat1 ou mat2, retorna true
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
	        // Feche a conexão, o statement e o resultSet no bloco finally
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

	    return false; // Se ocorrer um erro ou não houver correspondência, retorna false
	}

	public User obterUsuarioPelaMatricula(String matricula) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = DataBase.getDBConnection();
	        String query = "SELECT * FROM user WHERE mat1 = ? OR mat2 = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, matricula);
	        statement.setString(2, matricula);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            User user = new User();
	            user.setId(resultSet.getInt("id"));
	            user.setnome(resultSet.getString("nome"));
	            user.setmat1(resultSet.getString("mat1"));
	            user.setmat2(resultSet.getString("mat2"));
	            user.setref1(resultSet.getString("ref1"));
	            user.setref2(resultSet.getString("ref2"));
	            user.setcargh1(resultSet.getString("cargh1"));
	            user.setcargh2(resultSet.getString("cargh2"));
	            user.setcargo1(resultSet.getString("cargo1"));
	            user.setcargo2(resultSet.getString("cargo2"));
	            user.setnivel1(resultSet.getString("nivel1"));
	            user.setnivel2(resultSet.getString("nivel2"));
	            user.setdatainicio1(resultSet.getString("datainicio1"));
	            user.setdatainicio2(resultSet.getString("datainicio2"));
	            
	            // Preencha outros atributos conforme necessário

	            return user;
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
	        // Feche a conexão, o statement e o resultSet no bloco finally
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

	    return null;
	}

	public List<String> buscarNomesClientesPorTextoUser(String textoBusca) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> nomesClientes = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            String query = "SELECT nome FROM user WHERE nome LIKE ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, textoBusca + "%"); // Usamos o operador LIKE para buscar nomes que comecem com o texto inserido
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                nomesClientes.add(resultSet.getString("nome"));
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            // Lide com exceções de SQL aqui, se necessário
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

        return nomesClientes;
    }
	
	public List<String> buscarMatriculasPorNome(String nomeCliente) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    List<String> matriculas = new ArrayList<>();

	    try {
	        connection = DataBase.getDBConnection();
	        String query = "SELECT mat1, mat2 FROM user WHERE nome = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, nomeCliente);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            matriculas.add(resultSet.getString("mat1"));
	            matriculas.add(resultSet.getString("mat2"));
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

	    return matriculas;
	}
	
	
	  public User buscarUserPorNome(String nomeCliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        User user = null;

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT * FROM user WHERE nome = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomeCliente);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                user = new User();
	                
	                user.setId(resultSet.getInt("id"));
	                user.setnome(resultSet.getString("nome"));
	                user.settelefone(resultSet.getString("telefone"));
	                user.setcpf(resultSet.getString("cpf"));
	                user.setrg(resultSet.getString("rg"));
	                user.setnacionalidade(resultSet.getString("nacionalidade"));
	                user.setestadocivil(resultSet.getString("estadocivil"));
	                user.setprofissao(resultSet.getString("profissao"));
		            user.setidfuncional(resultSet.getString("idfuncional"));
		            user.setdatanascimento(resultSet.getString("datanascimento"));
		            user.setendereco(resultSet.getString("endereco"));
		            user.setcidade(resultSet.getString("cidade"));
		            user.setestado(resultSet.getString("estado"));
		            user.setcep(resultSet.getString("cep"));
		            user.setmat1(resultSet.getString("mat1"));
		            user.setref1(resultSet.getString("ref1"));
		            user.setcargh1(resultSet.getString("cargh1"));
		            user.setdatainicio1(resultSet.getString("datainicio1"));
		            user.setcargo1(resultSet.getString("cargo1"));
		            user.setnivel1(resultSet.getString("nivel1"));
		            user.settrienio1(resultSet.getString("trienio1"));
		            user.setdataaposentadoria1(resultSet.getString("dataaposentadoria1"));
		            user.setmat2(resultSet.getString("mat2"));
		            user.setref2(resultSet.getString("ref2"));
		            user.setcargh2(resultSet.getString("cargh2"));
		            user.setdatainicio2(resultSet.getString("datainicio2"));
		            user.setcargo2(resultSet.getString("cargo2"));
		            user.setnivel2(resultSet.getString("nivel2"));
		            user.settrienio2(resultSet.getString("trienio2"));
		            user.setdataaposentadoria2(resultSet.getString("dataposentadoria2"));
	                // Defina outros campos da classe Rec conforme necessário
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

	        return user;
	    }
	 
	  
	  
	  //MÉTODOS VOLTADOS PARA O AUTOINCREMENTO
	 
	  
	  public List<String> getAllNacionalidades() throws SQLException {
		    List<String> nacionalidades = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT DISTINCT nacionalidade FROM user";
		        statement = connection.prepareStatement(query);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            nacionalidades.add(resultSet.getString("nacionalidade"));
		        }
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

		    return nacionalidades;
		}

		public List<String> getAllEstadosCivis() throws SQLException {
		    List<String> estadosCivis = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT DISTINCT estadocivil FROM user";
		        statement = connection.prepareStatement(query);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            estadosCivis.add(resultSet.getString("estadocivil"));
		        }
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

		    return estadosCivis;
		}

		public String buscarReferenciaUsuario(String matricula) throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;
		    String referenciaUsuario = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT referencia FROM user WHERE matricula = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, matricula);
		        resultSet = statement.executeQuery();

		        if (resultSet.next()) {
		            referenciaUsuario = resultSet.getString("referencia");
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

		    return referenciaUsuario;
		}

		public String buscarDataInicial(String matricula) throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;
		    String dataInicial = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT data_inicial FROM user WHERE matricula = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, matricula);
		        resultSet = statement.executeQuery();

		        if (resultSet.next()) {
		            dataInicial = resultSet.getString("data_inicial");
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

		    return dataInicial;
		}

	  
	  
	  
		  
}