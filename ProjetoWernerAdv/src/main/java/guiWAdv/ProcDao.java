package guiWAdv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.WernerADV.Software.DataBase;

public class ProcDao {

	private static final Logger logger = Logger.getLogger(ProcDao.class.getName());
		
	public boolean procExist(String numeroprocesso) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		List<Proc> procs = new ArrayList<>();
		
		
		try {
			connection = com.WernerADV.Software.DataBase.getDBConnection();
			connection.setAutoCommit(false);
			String query = "SELECT Idproc, numeroprocesso, nomecliente, comarca, vara, tema, valorcausa, valorhono, status, matricula, sistema FROM proc WHERE numeroprocesso = ?";
			statement = connection.prepareStatement(query);
			int counter = 1;
			statement.setString(counter++, numeroprocesso);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Proc proc = new Proc();
				proc.setIdproc(resultSet.getInt(1));
				proc.setnumeroprocesso(resultSet.getString(2));
				proc.setnomecliente(resultSet.getString(3));
				proc.setcomarca(resultSet.getString(4));
				proc.setvara(resultSet.getString(5));
				proc.settema(resultSet.getString(6));
				proc.setvalorcausa(resultSet.getString(7));
				proc.setvalorhono(resultSet.getString(8));
				proc.setstatus(resultSet.getString(9));
				proc.setMatricula(resultSet.getString(10));
				proc.setSistema(resultSet.getString(11));

				procs.add(proc);
			}

			return procs.isEmpty() ? false : true;
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

		return procs.isEmpty() ? false : true;
	}

	public int saveProc(Proc proc) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = com.WernerADV.Software.DataBase.getDBConnection();
			connection.setAutoCommit(false);
			String query = "INSERT INTO proc(numeroprocesso, nomecliente, comarca, vara, tema, valorcausa, valorhono, status, matricula, sistema) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			statement.setString(counter++, proc.getnumeroprocesso());
			statement.setString(counter++, proc.getnomecliente());
			statement.setString(counter++, proc.getcomarca());
			statement.setString(counter++, proc.getvara());
			statement.setString(counter++, proc.gettema());
			statement.setString(counter++, proc.getvalorcausa());
			statement.setString(counter++, proc.getvalorhono());
			statement.setString(counter++, proc.getstatus());
			statement.setString(counter++, proc.getMatricula());
			statement.setString(counter++, proc.getSistema());
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

	public boolean EditaProcesso(Proc proc) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
	        connection = com.WernerADV.Software.DataBase.getDBConnection();
	        connection.setAutoCommit(false);
	        
	        // Consulta SQL para atualizar os valores no banco de dados
	        String query = "UPDATE proc SET nomecliente=?, comarca=?, vara=?, tema=?, valorcausa=?, valorhono=?, status=?, Matricula=? WHERE numeroprocesso=?";
	        statement = connection.prepareStatement(query);
	        
	        // Preencha os valores dos parâmetros da consulta com base no objeto Proc fornecido
	        statement.setString(1, proc.getnomecliente());
	        statement.setString(2, proc.getcomarca());
	        statement.setString(3, proc.getvara());
	        statement.setString(4, proc.gettema());
	        statement.setString(5, proc.getvalorcausa());
	        statement.setString(6, proc.getvalorhono());
	        statement.setString(7, proc.getstatus());
	        statement.setString(8, proc.getMatricula());
	        statement.setString(9, proc.getnumeroprocesso());
	        
	        int rowsUpdated = statement.executeUpdate();
	        connection.commit();
	        
	        // Verifique se alguma linha foi atualizada no banco de dados
	        return rowsUpdated > 0;
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	        if (null != connection) {
	            connection.rollback();
	        }
	    } finally {
	        if (null != statement) {
	            statement.close();
	        }

	        if (null != connection) {
	            connection.close();
	        }
	    }

	    return false;
	}

	
	
	// Outros métodos da classe...

	public List<Proc> obterTodosProcessos() throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    List<Proc> processos = new ArrayList<>();

	    try {
	        connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
	        String query = "SELECT Idproc, numeroprocesso, nomecliente, comarca, vara, tema, valorcausa, valorhono, status, matricula, sistema FROM proc";
	        statement = connection.prepareStatement(query);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            Proc proc = new Proc();
	            proc.setIdproc(resultSet.getInt("Idproc"));
	            proc.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	            proc.setnomecliente(resultSet.getString("nomecliente"));
	            proc.setcomarca(resultSet.getString("comarca"));
	            proc.setvara(resultSet.getString("vara"));
	            proc.settema(resultSet.getString("tema"));
	            proc.setvalorcausa(resultSet.getString("valorcausa"));
	            proc.setvalorhono(resultSet.getString("valorhono"));
	            proc.setstatus(resultSet.getString("status"));
	            proc.setMatricula(resultSet.getString("matricula"));
	            proc.setSistema(resultSet.getString("sistema"));
	            processos.add(proc);
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

	    return processos;
	}

	public List<String> obterNumerosProcessos() throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    List<String> numerosProcessos = new ArrayList<>();

	    try {
	        connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
	        String query = "SELECT numeroprocesso FROM proc";
	        statement = connection.prepareStatement(query);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            numerosProcessos.add(resultSet.getString("numeroprocesso"));
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

	    return numerosProcessos;
	}

	public void inserirProcesso(Proc proc) throws SQLException {
	    // Implemente a inserção de um novo processo no banco de dados aqui
	}

	
	public void atualizarProcesso(Proc proc) throws SQLException {
	    // Implemente a atualização de um processo no banco de dados aqui
	}

	
	public void excluirProcesso(Proc proc) throws SQLException {
	    // Implemente a exclusão de um processo no banco de dados aqui
	}

	public Proc buscarProcesso(Proc proc) throws SQLException {
	    Connection connection = DataBase.getDBConnection();
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM proc WHERE numeroprocesso = ?");
	    statement.setString(1, proc.getnumeroprocesso());
	    ResultSet resultSet = statement.executeQuery();

	    if (resultSet.next()) {
	        Proc processo = new Proc();
	        processo.setIdproc(resultSet.getInt("Idproc"));
	        processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	        processo.setnomecliente(resultSet.getString("nomecliente"));
	        processo.setcomarca(resultSet.getString("comarca"));
	        processo.setvara(resultSet.getString("vara"));
	        processo.settema(resultSet.getString("tema"));
	        processo.setvalorcausa(resultSet.getString("valorcausa"));
	        processo.setvalorhono(resultSet.getString("valorhono"));
	        processo.setstatus(resultSet.getString("status"));
	        processo.setMatricula(resultSet.getString("matricula"));
	        processo.setSistema(resultSet.getString("sistema"));
	        return processo;
	    } else {
	        return null;
	    }
	}

	public List<Proc> buscarProcessosPorNumerosIniciaisDoNumero(String numerosIniciais) throws SQLException {
	    Connection connection = DataBase.getDBConnection();
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM proc WHERE numeroprocesso LIKE ?");
	    statement.setString(1, numerosIniciais + "%");
	    ResultSet resultSet = statement.executeQuery();

	    List<Proc> processos = new ArrayList<>();

	    while (resultSet.next()) {
	        Proc processo = new Proc();
	        processo.setIdproc(resultSet.getInt("Idproc"));
	        processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	        // Configure os outros atributos do processo conforme necessário
	        processos.add(processo);
	    }

	    return processos;
	}
	
	public List<String> buscarTodosNomes() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> nomes = new ArrayList<>();

        try {
            connection = DataBase.getDBConnection();
            String query = "SELECT nomecliente FROM proc";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                nomes.add(resultSet.getString("nomecliente"));
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }

        return nomes;
    }

	 public List<String> buscarNomesClientesPorTexto(String textoBusca) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<String> nomesClientes = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT nomecliente FROM proc WHERE nomecliente LIKE ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, textoBusca + "%"); 
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                nomesClientes.add(resultSet.getString("nomecliente"));
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
	 
	 public List<String> buscarNumerosProcPorNomeCliente(String nomedocliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<String> numerosProcessos = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT numeroprocesso FROM proc WHERE nomecliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomedocliente);
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	            	numerosProcessos.add(resultSet.getString("numeroprocesso"));
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

	        return numerosProcessos;
	    }
	
	public List<Proc> obterNumerosProcessosPorNomeCliente(String nomecliente) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Proc> processos = new ArrayList<>();

		try {
	        connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
	        String query = "SELECT * FROM proc WHERE nomecliente = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, nomecliente);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            Proc processo = new Proc();
	            processo.setIdproc(resultSet.getInt("Idproc"));
	            processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	            // Configure os outros atributos do processo conforme necessário
	            processos.add(processo);
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
	        // Feche a conexão, o statement e o resultSe
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

	    return processos;
	}
	
	public List<Proc> obterNumerosProcessoseMatriculaPorNomeCliente(String nomecliente) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Proc> processos = new ArrayList<>();

		try {
	        connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
	        String query = "SELECT * FROM proc WHERE nomecliente = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, nomecliente);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            Proc processo2 = new Proc();
	            processo2.setIdproc(resultSet.getInt("Idproc"));
	            processo2.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	            processo2.setMatricula(resultSet.getString("matricula"));
	            processo2.setnomecliente(resultSet.getString("nomecliente"));
	            // Configure os outros atributos do processo conforme necessário
	            processos.add(processo2);
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
	        // Feche a conexão, o statement e o resultSe
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

	    return processos;
	}
	
	public List<Proc> obterNumerosProcessosPorNomeCliente2(String nomecliente) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Proc> processos2 = new ArrayList<>();

		try {
	        connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
	        String query = "SELECT * FROM proc WHERE nomecliente = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, nomecliente);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            Proc processo2 = new Proc();
	            processo2.setIdproc(resultSet.getInt("Idproc"));
	            processo2.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	            // Configure os outros atributos do processo conforme necessário
	            processos2.add(processo2);
	        }
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	    } finally {
	        // Feche a conexão, o statement e o resultSe
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

	    return processos2;
	}
	
	
	public List<String> buscarProcessosPeloNomeCliente(String nomecliente) throws SQLException {
		Connection connection = DataBase.getDBConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT numeroprocesso FROM proc WHERE nomecliente LIKE ?");
		statement.setString(1, nomecliente + "%");
		ResultSet resultSet = statement.executeQuery();
		 List<String> numerosProcessos = new ArrayList<>();

		    while (resultSet.next()) {
		        numerosProcessos.add(resultSet.getString("numeroprocesso"));
		    }

		    return numerosProcessos;
		}
	
	public List<String> buscarProcessosPeloNomeCliente2(String nomecliente) throws SQLException {
		Connection connection = DataBase.getDBConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT numeroprocesso FROM proc WHERE nomecliente LIKE ?");
		statement.setString(1, nomecliente + "%");
		ResultSet resultSet = statement.executeQuery();
		 List<String> numerosProcessos2 = new ArrayList<>();

		    while (resultSet.next()) {
		        numerosProcessos2.add(resultSet.getString("numeroprocesso"));
		    }

		    return numerosProcessos2;
		}
	
	
	
	public List<String> obterNomesClientes() throws SQLException {
	    Connection connection = DataBase.getDBConnection();
	    PreparedStatement statement = connection.prepareStatement("SELECT nomecliente FROM proc");
	    ResultSet resultSet = statement.executeQuery();

	    List<String> nomesClientes = new ArrayList<>();

	    while (resultSet.next()) {
	        nomesClientes.add(resultSet.getString("nomecliente"));
	    }

	    return nomesClientes;
	}
	
	public List<String> obterNomesClientes2() throws SQLException {
	    Connection connection = DataBase.getDBConnection();
	    PreparedStatement statement = connection.prepareStatement("SELECT nomecliente FROM proc");
	    ResultSet resultSet = statement.executeQuery();

	    List<String> nomesClientes2 = new ArrayList<>();

	    while (resultSet.next()) {
	        nomesClientes2.add(resultSet.getString("nomecliente"));
	    }

	    return nomesClientes2;
	}
	
	public Proc buscarProcessoPorNumero(String numeroProcesso) throws SQLException {
	    Connection connection = DataBase.getDBConnection();
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM proc WHERE numeroprocesso = ?");
	    statement.setString(1, numeroProcesso);
	    ResultSet resultSet = statement.executeQuery();

	    if (resultSet.next()) {
	        Proc processo = new Proc();
	        processo.setIdproc(resultSet.getInt("Idproc"));
	        processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	        processo.setnomecliente(resultSet.getString("nomecliente"));
	        processo.setcomarca(resultSet.getString("comarca"));
	        processo.setvara(resultSet.getString("vara"));
	        processo.settema(resultSet.getString("tema"));
	        processo.setvalorcausa(resultSet.getString("valorcausa"));
	        processo.setvalorhono(resultSet.getString("valorhono"));
	        processo.setstatus(resultSet.getString("status"));
	        processo.setMatricula(resultSet.getString("matricula"));
	        processo.setSistema(resultSet.getString("sistema"));
	        return processo;
	    } else {
	        return null;
	    }
	}
	
	public Proc buscarProcessoPorNumero2(String numeroProcesso) throws SQLException {
	    Connection connection = DataBase.getDBConnection();
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM proc WHERE numeroprocesso = ?");
	    statement.setString(1, numeroProcesso);
	    ResultSet resultSet = statement.executeQuery();

	    if (resultSet.next()) {
	        Proc processo = new Proc();
	        processo.setIdproc(resultSet.getInt("Idproc"));
	        processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
	        processo.setnomecliente(resultSet.getString("nomecliente"));
	        processo.setcomarca(resultSet.getString("comarca"));
	        processo.setvara(resultSet.getString("vara"));
	        processo.settema(resultSet.getString("tema"));
	        processo.setvalorcausa(resultSet.getString("valorcausa"));
	        processo.setvalorhono(resultSet.getString("valorhono"));
	        processo.setstatus(resultSet.getString("status"));
	        processo.setMatricula(resultSet.getString("matricula"));
	        processo.setSistema(resultSet.getString("sistema"));
	        return processo;
	    } else {
	        return null;
	    }
	}
	
	
	public boolean updateProc(Proc proc) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
	        connection = com.WernerADV.Software.DataBase.getDBConnection();
	        connection.setAutoCommit(false);

	        // Consulta SQL para atualizar os valores no banco de dados, excluindo o nomecliente
	        String query = "UPDATE proc SET comarca=?, vara=?, tema=?, valorcausa=?, valorhono=?, status=?, matricula=? WHERE numeroprocesso=?";
	        statement = connection.prepareStatement(query);

	        // Preencha os valores dos parâmetros da consulta com base no objeto Proc fornecido
	        statement.setString(1, proc.getcomarca());
	        statement.setString(2, proc.getvara());
	        statement.setString(3, proc.gettema());
	        statement.setString(4, proc.getvalorcausa());
	        statement.setString(5, proc.getvalorhono());
	        statement.setString(6, proc.getstatus());
	        statement.setString(7, proc.getnumeroprocesso());
	        statement.setString(8, proc.getMatricula());

	        int rowsUpdated = statement.executeUpdate();
	        connection.commit();

	        // Verifique se alguma linha foi atualizada no banco de dados
	        return rowsUpdated > 0;
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	        if (null != connection) {
	            connection.rollback();
	        }
	        return false; // Retorne false em caso de exceção
	    } finally {
	        if (null != statement) {
	            statement.close();
	        }

	        if (null != connection) {
	            connection.close();
	        }
	    }
	}

	
	
	public boolean deleteProc(String numeroprocesso) throws SQLException {
	    Connection connection = null;
	    PreparedStatement statement = null;

	    try {
	        connection = com.WernerADV.Software.DataBase.getDBConnection();
	        connection.setAutoCommit(false);

	        // Consulta SQL para excluir o processo com base no número do processo
	        String query = "DELETE FROM proc WHERE numeroprocesso = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, numeroprocesso);

	        int rowsDeleted = statement.executeUpdate();
	        connection.commit();

	        // Verifique se alguma linha foi excluída no banco de dados
	        return rowsDeleted > 0;
	    } catch (SQLException exception) {
	        logger.log(Level.SEVERE, exception.getMessage());
	        if (null != connection) {
	            connection.rollback();
	        }
	    } finally {
	        if (null != statement) {
	            statement.close();
	        }

	        if (null != connection) {
	            connection.close();
	        }
	    }

	    return false;
	}
	
	
	  public int obterIdProcPorNomeClienteTabEditProc(String nomeClienteTabProcEdit) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        int Idproc = -1; // Valor padrão se não encontrar um ID válido

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT Idproc FROM proc WHERE nomecliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomeClienteTabProcEdit);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	            	Idproc = resultSet.getInt("Idproc");
	            }
	        } catch (SQLException exception) {
	            exception.printStackTrace();
	            // Lide com exceções de SQL, se necessário
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

	        return Idproc;
	    }
	    
	  public String obterTemaPorNumeroProcesso(String numeroProcesso) throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT tema FROM proc WHERE numeroprocesso = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, numeroProcesso);
		        resultSet = statement.executeQuery();

		        if (resultSet.next()) {
		            return resultSet.getString("tema");
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

		    return null; // Retorne null se o tema não for encontrado
		}
	
	  public String obterVaraPorNumeroProcesso(String numeroProcesso) throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT vara FROM proc WHERE numeroprocesso = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, numeroProcesso);
		        resultSet = statement.executeQuery();

		        if (resultSet.next()) {
		            return resultSet.getString("vara");
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

		    return null; // Retorne null se o tema não for encontrado
		}
	  public String obterComarcaPorNumeroProcesso(String numeroProcesso) throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT comarca FROM proc WHERE numeroprocesso = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, numeroProcesso);
		        resultSet = statement.executeQuery();

		        if (resultSet.next()) {
		            return resultSet.getString("comarca");
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

		    return null; // Retorne null se o tema não for encontrado
		}
	
	

	  
	  public List<Proc> obterProcsPorIniciaisDoNome(String iniciaisDoNome) throws SQLException {
		    List<Proc> processos = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT * FROM proc WHERE nomecliente LIKE ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, iniciaisDoNome + "%");
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            Proc processo = new Proc();
		            processo.setnomecliente(resultSet.getString("nomecliente"));
		            processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
		            // Defina outras propriedades, se necessário
		            processos.add(processo);
		        }
		    } finally {
		        // Certifique-se de fechar a conexão, o statement e o resultSet
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

		    return processos;
		}
	  public List<Proc> buscarProcessosPordoNomeCliente(String nomecliente) throws SQLException {
		    Connection connection = DataBase.getDBConnection();
		    PreparedStatement statement = connection.prepareStatement("SELECT * FROM proc WHERE nomecliente LIKE ?");
		    statement.setString(1, "%" + nomecliente + "%");
		    ResultSet resultSet = statement.executeQuery();

		    List<Proc> processos = new ArrayList<>();

		    while (resultSet.next()) {
		        Proc processo = new Proc();
		        processo.setIdproc(resultSet.getInt("Idproc"));
		        processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
		        processo.setnomecliente(resultSet.getString("nomecliente"));
		        processo.setcomarca(resultSet.getString("comarca"));
		        processo.setvara(resultSet.getString("vara"));
		        processo.settema(resultSet.getString("tema"));
		        processo.setvalorcausa(resultSet.getString("valorcausa"));
		        processo.setvalorhono(resultSet.getString("valorhono"));
		        processo.setstatus(resultSet.getString("status"));
		        processo.setMatricula(resultSet.getString("matricula"));
		        processos.add(processo);
		    }

		    return processos;
		}

		public List<Proc> buscarProcessosPorNumeroProcesso(String numeroprocesso) throws SQLException {
		    Connection connection = DataBase.getDBConnection();
		    PreparedStatement statement = connection.prepareStatement("SELECT * FROM proc WHERE numeroprocesso LIKE ?");
		    statement.setString(1, "%" + numeroprocesso + "%");
		    ResultSet resultSet = statement.executeQuery();

		    List<Proc> processos = new ArrayList<>();

		    while (resultSet.next()) {
		        Proc processo = new Proc();
		        processo.setIdproc(resultSet.getInt("Idproc"));
		        processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
		        processo.setnomecliente(resultSet.getString("nomecliente"));
		        processo.setcomarca(resultSet.getString("comarca"));
		        processo.setvara(resultSet.getString("vara"));
		        processo.settema(resultSet.getString("tema"));
		        processo.setvalorcausa(resultSet.getString("valorcausa"));
		        processo.setvalorhono(resultSet.getString("valorhono"));
		        processo.setstatus(resultSet.getString("status"));
		        processo.setMatricula(resultSet.getString("matricula"));
		        processos.add(processo);
		    }

		    return processos;
		}

		public List<String> obterComarcas() throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;
		    
		    Set<String> comarcasSet = new HashSet<>();

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT DISTINCT comarca FROM proc";
		        statement = connection.prepareStatement(query);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            comarcasSet.add(resultSet.getString("comarca"));
		        }
		    } finally {
		        // Certifique-se de fechar a conexão, o statement e o resultSet
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

		    return new ArrayList<>(comarcasSet);
		}

		public List<String> obterVaras() throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;
		    
		    Set<String> varasSet = new HashSet<>();

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT DISTINCT vara FROM proc";
		        statement = connection.prepareStatement(query);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		        	varasSet.add(resultSet.getString("vara"));
		        }
		    } finally {
		        // Certifique-se de fechar a conexão, o statement e o resultSet
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

		    return new ArrayList<>(varasSet);
		}
		
		public List<String> obterNomesClientesPorComarca(String comarca) throws SQLException {
		    List<String> nomesClientes = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT nomecliente FROM proc WHERE comarca = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, comarca);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            nomesClientes.add(resultSet.getString("nomecliente"));
		        }
		    } finally {
		        // Certifique-se de fechar a conexão, o statement e o resultSet
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

		public List<String>  obterNumerosProcessoPorNomeEComarcaEVara(String nomeCliente, String comarca, String vara) throws SQLException {
			List<String> numerosProcesso = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT numeroprocesso FROM proc WHERE nomecliente = ? AND comarca = ? AND vara = ?";

		        statement = connection.prepareStatement(query);
		        statement.setString(1, nomeCliente);
		        statement.setString(2, comarca);
		        statement.setString(3, vara);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            numerosProcesso.add(resultSet.getString("numeroprocesso"));
		        }
		    } finally {
		        // Certifique-se de fechar a conexão, o statement e o resultSet
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

		    return numerosProcesso;
		}
			
			public List<String> obterNumerosProcessoPorNomeEComarca(String nomeCliente, String comarca) throws SQLException {
		    List<String> numerosProcesso = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT numeroprocesso FROM proc WHERE nomecliente = ? AND comarca = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, nomeCliente);
		        statement.setString(2, comarca);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            numerosProcesso.add(resultSet.getString("numeroprocesso"));
		        }
		    } finally {
		        // Certifique-se de fechar a conexão, o statement e o resultSet
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

		    return numerosProcesso;
		}

			public List<String> obterNomesPorVaraeComarca(String vara, String comarca) throws SQLException {
			    List<String> nomes = new ArrayList<>();

			    try (Connection connection = DataBase.getDBConnection();
			         PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT nomecliente FROM proc WHERE vara = ? AND comarca = ?")) {

			        statement.setString(1, vara);
			        statement.setString(2, comarca);

			        try (ResultSet rs = statement.executeQuery()) {
			            while (rs.next()) {
			                nomes.add(rs.getString("nomecliente"));
			            }
			        }
			    }

			    return nomes;
			}


		public List<String> buscarNumerosProcessoPorTexto(String textoBusca) throws SQLException {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;
		    List<String> numerosProcesso = new ArrayList<>();

		    try {
		        connection = DataBase.getDBConnection();
		        String query = "SELECT numeroprocesso FROM proc WHERE numeroprocesso LIKE ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, textoBusca + "%");
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            numerosProcesso.add(resultSet.getString("numeroprocesso"));
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

		    return numerosProcesso;
		}
		
		  public List<String> obterVarasPorComarca(String comarca) throws SQLException {
		        List<String> varas = new ArrayList<>();

		        try (Connection connection = DataBase.getDBConnection();
		             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT vara FROM proc WHERE comarca = ?")) {

		            statement.setString(1, comarca);

		            try (ResultSet rs = statement.executeQuery()) {
		                while (rs.next()) {
		                    varas.add(rs.getString("vara"));
		                }
		            }
		        }

		        return varas;
		    }

		    public List<String> obterNomesPorVara(String vara) throws SQLException {
		        List<String> nomes = new ArrayList<>();

		        try (Connection connection = DataBase.getDBConnection();
		             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT nomecliente FROM proc WHERE vara = ?")) {

		            statement.setString(1, vara);

		            try (ResultSet rs = statement.executeQuery()) {
		                while (rs.next()) {
		                    nomes.add(rs.getString("nomecliente"));
		                }
		            }
		        }

		        return nomes;
		    }

		    public List<String> obterNumerosProcessoPorVara(String vara) throws SQLException {
		        List<String> numerosProcesso = new ArrayList<>();

		        try (Connection connection = DataBase.getDBConnection();
		             PreparedStatement statement = connection.prepareStatement("SELECT numeroprocesso FROM proc WHERE vara = ?")) {

		            statement.setString(1, vara);

		            try (ResultSet rs = statement.executeQuery()) {
		                while (rs.next()) {
		                    numerosProcesso.add(rs.getString("numeroprocesso"));
		                }
		            }
		        }

		        return numerosProcesso;
		    }
		
		    public List<String> obterTemas() throws SQLException {
		        List<String> temas = new ArrayList<>();
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;

		        try {
		            connection = DataBase.getDBConnection();
		            String query = "SELECT DISTINCT tema FROM proc";
		            statement = connection.prepareStatement(query);
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                temas.add(resultSet.getString("tema"));
		            }
		        } finally {
		            // Certifique-se de fechar a conexão, o statement e o resultSet
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

		        return temas;
		    }
		    
		    public List<String> obterNomesPorTema(String tema) throws SQLException {
		        List<String> nomes = new ArrayList<>();
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;

		        try {
		            connection = DataBase.getDBConnection();
		            String query = "SELECT nomecliente FROM proc WHERE tema = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, tema);
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                nomes.add(resultSet.getString("nomecliente"));
		            }
		        } finally {
		            closeResources(resultSet, statement, connection);
		        }

		        return nomes;
		    }

		    public List<String> obterNumerosProcessoPorNomeETema(String nomeCliente, String tema) throws SQLException {
		        List<String> numerosProcesso = new ArrayList<>();
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;

		        try {
		            connection = DataBase.getDBConnection();
		            String query = "SELECT numeroprocesso FROM proc WHERE nomecliente = ? AND tema = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, nomeCliente);
		            statement.setString(2, tema);
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                numerosProcesso.add(resultSet.getString("numeroprocesso"));
		            }
		        } finally {
		            closeResources(resultSet, statement, connection);
		        }

		        return numerosProcesso;
		    }

		    private void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
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
		    public List<String> obterStatusPorTema(String tema) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        List<String> statusList = new ArrayList<>();

		        try {
		            connection = DataBase.getDBConnection();
		            String query = "SELECT DISTINCT status FROM proc WHERE tema = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, tema);
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                statusList.add(resultSet.getString("status"));
		            }
		        } catch (SQLException exception) {
		            logger.log(Level.SEVERE, exception.getMessage());
		        } finally {
		            // Feche a conexão, o statement e o resultSet no bloco finally
		            closeResources(resultSet, statement, connection);
		        }

		        return statusList;
		    }
		    
		 // Dentro da classe ProcDao

		    public List<String> obterNomesPorTemaEStatus(String tema, String status) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        List<String> nomesStatusList = new ArrayList<>();

		        try {
		            connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
		            String query = "SELECT nomecliente, numeroprocesso FROM proc WHERE tema = ? AND status = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, tema);
		            statement.setString(2, status);
		            resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                String nomeCliente = resultSet.getString("nomecliente");
		                String numeroProcesso = resultSet.getString("numeroprocesso");
		                String nomeStatus = nomeCliente + " - " + numeroProcesso;
		                nomesStatusList.add(nomeStatus);
		            }
		        } catch (SQLException exception) {
		            logger.log(Level.SEVERE, exception.getMessage());
		        } finally {
		            // Feche a conexão, o statement e o resultSet no bloco finally
		            closeResources(resultSet, statement, connection);
		        }

		        return nomesStatusList;
		    }

		    public Proc obterDetalhesPorNumeroProcesso(String numeroProcesso) throws SQLException {
		        Connection connection = null;
		        PreparedStatement statement = null;
		        ResultSet resultSet = null;
		        Proc processo = null;

		        try {
		            connection = DataBase.getDBConnection(); // Certifique-se de que seu método de conexão está funcionando corretamente
		            String query = "SELECT * FROM proc WHERE numeroprocesso = ?";
		            statement = connection.prepareStatement(query);
		            statement.setString(1, numeroProcesso);
		            resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                processo = new Proc();
		                processo.setIdproc(resultSet.getInt("Idproc"));
		                processo.setnumeroprocesso(resultSet.getString("numeroprocesso"));
		                processo.setnomecliente(resultSet.getString("nomecliente"));
		                processo.setcomarca(resultSet.getString("comarca"));
		                processo.setvara(resultSet.getString("vara"));
		                processo.settema(resultSet.getString("tema"));
		                processo.setvalorcausa(resultSet.getString("valorcausa"));
		                processo.setvalorhono(resultSet.getString("valorhono"));
		                processo.setstatus(resultSet.getString("status"));
		                processo.setMatricula(resultSet.getString("matricula"));
		                processo.setvalorcausa(resultSet.getString("valorcausa"));
		            }
		        } catch (SQLException exception) {
		            logger.log(Level.SEVERE, exception.getMessage());
		        } finally {
		            // Feche a conexão, o statement e o resultSet no bloco finally
		            closeResources(resultSet, statement, connection);
		        }

		        return processo;
		    }
	

}