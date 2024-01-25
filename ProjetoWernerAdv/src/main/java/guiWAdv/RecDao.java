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


public class RecDao {
	
		private static final Logger logger = Logger.getLogger(RecDao.class.getName());
			
		public boolean recExist(String numerorecurso) throws SQLException {
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

		public int saveRec(Rec rec) throws SQLException {
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

		
		public List<Rec> obterTodosRecursos() throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<Rec> recursos = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT Idrec, numerorecurso, nomedocliente, processoorigem, tiporecurso, recorridoourecorrente, status, julgador, relator FROM rec";
	            statement = connection.prepareStatement(query);
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                Rec rec = new Rec();
	                rec.setIdrec(resultSet.getInt("Idrec"));
	                rec.setNumerorecurso(resultSet.getString("numerorecurso"));
	                rec.setNomedocliente(resultSet.getString("nomedocliente"));
	                rec.setProcessoorigem(resultSet.getString("processoorigem"));
	                rec.setTiporecurso(resultSet.getString("tiporecurso"));
	                rec.setRecorridoourecorrente(resultSet.getString("recorridoourecorrente"));
	                rec.setStatus(resultSet.getString("status"));
	                rec.setJulgador(resultSet.getString("julgador"));
	                rec.setRelator(resultSet.getString("relator"));
	                recursos.add(rec);
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

	        return recursos;
	    }

	    public List<String> obterNumerosRecursos() throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<String> numerosRecursos = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT numerorecurso FROM rec";
	            statement = connection.prepareStatement(query);
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                numerosRecursos.add(resultSet.getString("numerorecurso"));
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

	        return numerosRecursos;
	    }

	    public void inserirRecurso(Rec rec) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        try {
	            connection = DataBase.getDBConnection();
	            connection.setAutoCommit(false);
	            String query = "INSERT INTO rec(numerorecurso, nomedocliente, processoorigem, tiporecurso, recorridoourecorrente, status, julgador, relator) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	            statement = connection.prepareStatement(query);
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
	        } catch (SQLException exception) {
	            logger.log(Level.SEVERE, exception.getMessage());
	            if (connection != null) {
	                connection.rollback();
	            }
	        } finally {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        }
	    }

	    public void atualizarRecurso(Rec rec) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        try {
	            connection = DataBase.getDBConnection();
	            connection.setAutoCommit(false);
	            String query = "UPDATE rec SET numerorecurso = ?, processoorigem = ?, tiporecurso = ?, recorridoourecorrente = ?, status = ?, julgador = ?, relator = ? WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            int counter = 1;
	            statement.setString(counter++, rec.getNumerorecurso());
	            statement.setString(counter++, rec.getProcessoorigem());
	            statement.setString(counter++, rec.getTiporecurso());
	            statement.setString(counter++, rec.getRecorridoourecorrente());
	            statement.setString(counter++, rec.getStatus());
	            statement.setString(counter++, rec.getJulgador());
	            statement.setString(counter++, rec.getRelator());
	            statement.setString(counter++, rec.getNomedocliente());
	            statement.executeUpdate();
	            connection.commit();
	        } catch (SQLException exception) {
	            logger.log(Level.SEVERE, exception.getMessage());
	            if (connection != null) {
	                connection.rollback();
	            }
	        } finally {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        }
	    }

	    public void excluirRecurso(Rec rec) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        try {
	            connection = DataBase.getDBConnection();
	            connection.setAutoCommit(false);
	            String query = "DELETE FROM rec WHERE numerorecurso = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, rec.getNumerorecurso());
	            statement.executeUpdate();
	            connection.commit();
	        } catch (SQLException exception) {
	            logger.log(Level.SEVERE, exception.getMessage());
	            if (connection != null) {
	                connection.rollback();
	            }
	        } finally {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        }
	    }

	    public Rec buscarRecurso(Rec rec) throws SQLException {
	        Connection connection = DataBase.getDBConnection();
	        PreparedStatement statement = connection.prepareStatement("SELECT * FROM rec WHERE numerorecurso = ?");
	        statement.setString(1, rec.getNumerorecurso());
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            Rec recurso = new Rec();
	            recurso.setIdrec(resultSet.getInt("Idrec"));
	            recurso.setNumerorecurso(resultSet.getString("numerorecurso"));
	            recurso.setNomedocliente(resultSet.getString("nomedocliente"));
	            recurso.setProcessoorigem(resultSet.getString("processoorigem"));
	            recurso.setTiporecurso(resultSet.getString("tiporecurso"));
	            recurso.setRecorridoourecorrente(resultSet.getString("recorridoourecorrente"));
	            recurso.setStatus(resultSet.getString("status"));
	            recurso.setJulgador(resultSet.getString("julgador"));
	            recurso.setRelator(resultSet.getString("relator"));
	            return recurso;
	        } else {
	            return null;
	        }
	    }

	    public List<Rec> buscarRecursosPorNumerosIniciaisDoNumero(String numerosIniciais) throws SQLException {
	        Connection connection = DataBase.getDBConnection();
	        PreparedStatement statement = connection.prepareStatement("SELECT * FROM rec WHERE numerorecurso LIKE ?");
	        statement.setString(1, numerosIniciais + "%");
	        ResultSet resultSet = statement.executeQuery();

	        List<Rec> recursos = new ArrayList<>();

	        while (resultSet.next()) {
	            Rec recurso = new Rec();
	            recurso.setIdrec(resultSet.getInt("Idrec"));
	            recurso.setNumerorecurso(resultSet.getString("numerorecurso"));
	            // Configure os outros atributos do recurso conforme necessário
	            recursos.add(recurso);
	        }

	        return recursos;
	    }

	    
	    public List<Rec> buscarRecursosPorIniciaisNomedoCliente(String iniciaisNomedoCliente) throws SQLException {
	        Connection connection = DataBase.getDBConnection();
	        PreparedStatement statement = connection.prepareStatement("SELECT * FROM rec WHERE nomedocliente LIKE ?");
	        statement.setString(1, iniciaisNomedoCliente + "%");
	        ResultSet resultSet = statement.executeQuery();

	        List<Rec> recursos = new ArrayList<>();

	        while (resultSet.next()) {
	            Rec recurso = new Rec();
	            recurso.setIdrec(resultSet.getInt("Idrec"));
	            recurso.setNumerorecurso(resultSet.getString("numerorecurso"));
	            recurso.setNomedocliente(resultSet.getString("nomedocliente"));
	            // Configure os outros atributos do recurso conforme necessário
	            recursos.add(recurso);
	        }

	        return recursos;
	    }
	    
	    public List<String> buscarNomesClientesPorTexto(String textoBusca) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<String> nomesClientes = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT nomedocliente FROM rec WHERE nomedocliente LIKE ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, textoBusca + "%"); // Usamos o operador LIKE para buscar nomes que comecem com o texto inserido
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                nomesClientes.add(resultSet.getString("nomedocliente"));
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
	    
	    
	    public List<String> buscarRecPorNomeCliente(String nomeCliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<String> nomesClientes = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT nomedocliente FROM rec WHERE nomedocliente LIKE ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomeCliente + "%");
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                nomesClientes.add(resultSet.getString("nomedocliente"));
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

	        return nomesClientes;
	    }
	    
	    public Rec buscarRecPorNome(String nomeCliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        Rec rec = null;

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT * FROM rec WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomeCliente);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                rec = new Rec();
	                rec.setNomedocliente(resultSet.getString("nomedocliente"));
	                rec.setNumerorecurso(resultSet.getString("numerorecurso"));
	                rec.setProcessoorigem(resultSet.getString("processoorigem"));
	                rec.setTiporecurso(resultSet.getString("tiporecurso"));
	                rec.setRecorridoourecorrente(resultSet.getString("recorridoourecorrente"));
	                rec.setStatus(resultSet.getString("status"));
	                rec.setJulgador(resultSet.getString("julgador"));
	                rec.setRelator(resultSet.getString("relator"));
	              
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

	        return rec;
	    }
	    
	    public Rec buscarRecPorNomeEditar(String nomeCliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        Rec rec = null;

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT * FROM rec WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomeCliente);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                rec = new Rec();
	                rec.setNomedocliente(resultSet.getString("nomedocliente"));
	                rec.setNumerorecurso(resultSet.getString("numerorecurso"));
	                rec.setProcessoorigem(resultSet.getString("processoorigem"));
	                rec.setTiporecurso(resultSet.getString("tiporecurso"));
	                rec.setRecorridoourecorrente(resultSet.getString("recorridoourecorrente"));
	                rec.setStatus(resultSet.getString("status"));
	                rec.setJulgador(resultSet.getString("julgador"));
	                rec.setRelator(resultSet.getString("relator"));
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

	        return rec;
	    }
	    
	    public List<String> obterNomesClientesRec() throws SQLException {
		    Connection connection = DataBase.getDBConnection();
		    PreparedStatement statement = connection.prepareStatement("SELECT nomedocliente FROM rec");
		    ResultSet resultSet = statement.executeQuery();

		    List<String> nomesdosClientes = new ArrayList<>();

		    while (resultSet.next()) {
		    	nomesdosClientes.add(resultSet.getString("nomecliente"));
		    }

		    return nomesdosClientes;
		}
	    
	    public Rec buscarRecursoPorNumero(String numerorecurso) throws SQLException {
		    Connection connection = DataBase.getDBConnection();
		    PreparedStatement statement = connection.prepareStatement("SELECT * FROM rec WHERE numerorecurso = ?");
		    statement.setString(1, numerorecurso);
		    ResultSet resultSet = statement.executeQuery();

		    if (resultSet.next()) {
		        Rec recurso = new Rec();
		        recurso.setIdrec(resultSet.getInt("Idrec"));
		        recurso.setNumerorecurso(resultSet.getString("numerorecurso"));
		        recurso.setNomedocliente(resultSet.getString("nomedocliente"));
		        recurso.setProcessoorigem(resultSet.getString("processoorigem"));
		        recurso.setTiporecurso(resultSet.getString("tiporecurso"));
		        recurso.setRecorridoourecorrente(resultSet.getString("recorridoourecorrente"));
		        recurso.setStatus(resultSet.getString("status"));
		        recurso.setJulgador(resultSet.getString("julgador"));
		        recurso.setRelator(resultSet.getString("relator"));
		        return recurso;
		    } else {
		        return null;
		    }
		}


	    public int obterIdRecPorProcesso(String processoSelecionado) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT Idrec FROM rec WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, processoSelecionado);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                return resultSet.getInt("Idrec");
	            } else {
	                // Trate o caso em que o processo não foi encontrado
	                return -1; // Por exemplo, retorne -1 para indicar que o processo não foi encontrado
	            }
	        } catch (SQLException exception) {
	            logger.log(Level.SEVERE, exception.getMessage());
	            throw exception; // Relance a exceção para que possa ser tratada pelo chamador
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
	    }
	    public int obterIdRecPorNomeCliente(String nomeCliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        int idRec = -1; // Valor padrão se não encontrar um ID válido

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT Idrec FROM rec WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomeCliente);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                idRec = resultSet.getInt("Idrec");
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

	        return idRec;
	    }
	    
	    public int obterIdRecPorNomeClienteEdit(String nomeCliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        int idRec = -1; // Valor padrão se não encontrar um ID válido

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT Idrec FROM rec WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomeCliente);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                idRec = resultSet.getInt("Idrec");
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

	        return idRec;
	    }
	    
	    
	    public boolean UpdateRecurso(Rec rec) throws SQLException {
	    	Connection connection = null;
		    PreparedStatement statement = null;
		    
		    try {
		        connection = com.WernerADV.Software.DataBase.getDBConnection();
		        connection.setAutoCommit(false);
		        
	            String query = "UPDATE rec SET numerorecurso = ?, processoorigem = ?, tiporecurso = ?, recorridoourecorrente = ?, status = ?, julgador = ?, relator = ? WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            
	            statement.setString(1, rec.getNumerorecurso());
	            statement.setString(2, rec.getProcessoorigem());
	            statement.setString(3, rec.getTiporecurso());
	            statement.setString(4, rec.getRecorridoourecorrente());
	            statement.setString(5, rec.getStatus());
	            statement.setString(6, rec.getJulgador());
	            statement.setString(7, rec.getRelator());
	            statement.setString(8, rec.getNomedocliente());
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
	    
	    
	    public List<String> buscarNumerosRecursoPorNomeCliente(String nomedocliente) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        List<String> numerosRecursos = new ArrayList<>();

	        try {
	            connection = DataBase.getDBConnection();
	            String query = "SELECT numerorecurso FROM rec WHERE nomedocliente = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, nomedocliente);
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                numerosRecursos.add(resultSet.getString("numerorecurso"));
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

	        return numerosRecursos;
	    }

	    
	    
	    
	    public void moverParaRecArquivo(int idRec) throws SQLException {
	        Connection connection = null;
	        PreparedStatement moveStatement = null;
	        PreparedStatement deleteStatement = null;

	        try {
	            connection = com.WernerADV.Software.DataBase.getDBConnection();
	            connection.setAutoCommit(false);

	            // 1. Inserir na tabela recarquivo
	            String moveQuery = "INSERT INTO recarquivo(Idrec, numerorecurso, nomedocliente, processoorigem, tiporecurso, recorridoourecorrente, status, julgador, relator) SELECT Idrec, numerorecurso, nomedocliente, processoorigem, tiporecurso, recorridoourecorrente, status, julgador, relator FROM rec WHERE Idrec = ?";
	            moveStatement = connection.prepareStatement(moveQuery);
	            moveStatement.setInt(1, idRec);
	            moveStatement.executeUpdate();

	            // 2. Excluir da tabela rec
	            String deleteQuery = "DELETE FROM rec WHERE Idrec = ?";
	            deleteStatement = connection.prepareStatement(deleteQuery);
	            deleteStatement.setInt(1, idRec);
	            deleteStatement.executeUpdate();

	            connection.commit();
	        } catch (SQLException exception) {
	            if (connection != null) {
	                connection.rollback();
	            }
	            logger.log(Level.SEVERE, exception.getMessage());
	        } finally {
	            if (moveStatement != null) {
	                moveStatement.close();
	            }

	            if (deleteStatement != null) {
	                deleteStatement.close();
	            }

	            if (connection != null) {
	                connection.close();
	            }
	        }
	    }
	
	    
	    public int obterIdRecPorNumeroRecurso(String numeroRecurso) throws SQLException {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            connection = com.WernerADV.Software.DataBase.getDBConnection();
	            connection.setAutoCommit(false);

	            String query = "SELECT Idrec FROM rec WHERE numerorecurso = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, numeroRecurso);

	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                return resultSet.getInt("Idrec");
	            } else {
	                return -1; // Retorna -1 se não encontrar nenhum Idrec para o número do recurso
	            }
	        } catch (SQLException exception) {
	            exception.printStackTrace(); // Trate a exceção conforme necessário
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

	        return -1;
	    }
	    
	    
	    
	    
	    
	    
}
	    
	    
	    
	    
	


