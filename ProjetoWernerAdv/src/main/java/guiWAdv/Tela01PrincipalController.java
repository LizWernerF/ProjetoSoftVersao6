package guiWAdv;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.WernerADV.Software.StringPool;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Tela01PrincipalController implements Initializable {

	private static final Logger logger = Logger.getLogger(com.WernerADV.Software.App.class.getName());
	private UserDao userDao = new UserDao();
	private ProcDao procDao = new ProcDao();
	private RecDao recDao = new RecDao();
	
	
	
	 @FXML
	 private Tela03ConsultaProcessoController consultaProcessoController;
	 @FXML
	 private Tela02ConsultaClienteController consultaClienteController;
	 @FXML
	 private Tela04GeradorDeDocumentoController geradorDeDocumentoController;
	 @FXML
	 private Tela05CalculosController calculosController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		listResultadoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null) {
		        try {
		        	onSelecionarClienteUserCadProc();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		});
		setupListViewClickHandler();
		
	
		
		txtProcesoOrigem = new TextField();
		@SuppressWarnings("unused")
		UserDao userDao = new UserDao();
	  
	
		
		// ADICIONANDO COMANDO AOS BOTÕES PARA ALTERNAR ENTRE AS TABS
		btnIniciar.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabHome);
		});
		btnCadastrar.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabCadastros);
		});
		btnConsultar.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabConsultar);
		});
		btnVoltar.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabIniciar);
		});
		btnNovocliente.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabCadastrosClientes);
		});
		btnNovoRecurso.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabCadastrosRecursos);
		});
		btnNovoprocesso.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabCadastrosProcessos);
		});
		btnVoltar2.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabHome);
		});
		btnVoltar21.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabHome);
		});
		btnVoltar3.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabCadastros);
		});
		btnVoltar31.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabCadastros);
		});
		btnVoltar32.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabCadastros);
		});
		
		btnVoltar34.setOnAction(event -> {
			tabPane.getSelectionModel().select(tabHome);
		});
		btnTabBuscas.setOnAction(event -> {
			tabPane.getSelectionModel().select(TabBuscas);
		});
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		//FECHA INITIALIZE
	}

	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	                                                                 //TAB HOME E CONFIG GERAIS
	
	@FXML 
	private Tab TabBuscas;
	@FXML
	private TabPane tabPane;                                                  
	@FXML
	private Button btnIniciar;
	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	@FXML
	private Tab tabHome;
	@FXML
	private Tab tabCadastros;
	@FXML
	private Tab tabIniciar;
	@FXML
	private Tab tabCadastrosClientes;
	@FXML
	private Tab tabCadastrosProcessos;
	@FXML
	private Tab tabCadastrosRecursos;
	@FXML
	private Tab tabConsultar;
	@FXML
	private Tab tabConsultaCliente;
	@FXML
	private Tab tabGerarDocumento;

                                                     // CRIAÇÃO DE BOTÕES PARA COMANDAR MUDANÇA DE TABS 
	@FXML
	private Button voltar;
	@FXML
	private Button btnVoltar;
	@FXML
	private Button btnVoltar2;
	@FXML
	private Button btnVoltar21;
	@FXML
	private Button btnVoltar31;
	@FXML
	private Button btnVoltar32;
	@FXML
	private Button btnVoltar33;
	@FXML
	private Button btnVoltar34;
	@FXML
	private Button btnNovocliente;
	@FXML
	private Button btnNovoRecurso;
	@FXML
	private Button btnCadastrarCliente;
	@FXML
	private Button btnNovoCadastroCliente;
	@FXML
	private Button btnNovoprocesso;
	@FXML
	private Button btnVoltar3;
	@FXML
	private Button btnConsultar;
	@FXML
	private Button btnCadastrar;
	@FXML
	private Button btnTabGerarDocumento;
	@FXML
	private Button btnMostrartodos;
	@FXML
	private Button btnLimparTela;
	@FXML
	private Button btnTabBuscas;

	// Controls TABCADASTROSCLIENTES - FORMULÁRIO DE CADASTRO DE CLIENTES -
	// VINCULADO À DATABASE
	// MYSQL
	@FXML
	private TextField txtID;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtTelefone;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtRg;
	@FXML
	private TextField txtNacionalidade;
	@FXML
	private TextField txtEstCivil;
	@FXML
	private TextField txtProfissao;
	@FXML
	private TextField txtIdFuncional;
	@FXML
	private TextField txtDatanscimento;
	@FXML
	private TextField txtEndereco;
	@FXML
	private TextField txtCidade;
	@FXML
	private TextField txtEstado;
	@FXML
	private TextField txtCep;
	@FXML
	private TextField txtMat1;
	@FXML
	private TextField BoxRef1;
	@FXML
	private TextField BoxCargH1;
	@FXML
	private TextField DataInicio1;
	@FXML
	private TextField txtCargo;
	@FXML
	private TextField BoxNivelatual1;
	@FXML
	private TextField txtTrienioatual1;
	@FXML
	private TextField txtdataaposentadoria1;
	@FXML
	private TextField txtMat2;
	@FXML
	private TextField BoxRef2;
	@FXML
	private TextField BoxCargh2;
	@FXML
	private TextField datainicio2;
	@FXML
	private TextField txtCargo2;
	@FXML
	private TextField BoxNivelatual2;
	@FXML
	private TextField txtTrienioatual2;
	@FXML
	private TextField txtDataaposentadoria2;

	private void alert(String title, String message, AlertType alertType) {
	    Platform.runLater(() -> {
	        Alert alert = new Alert(alertType);
	        alert.setTitle("Werner Advogados");
	        alert.setHeaderText(title);
	        alert.setContentText(message);

	        // Carregue o ícone da imagem
	        Image icon = new Image(getClass().getResourceAsStream("/guiWAdv/LOGO.png"));
	        ImageView imageView = new ImageView(icon);

	        // Configure o ícone do Alert
	        alert.setGraphic(imageView);

	        alert.showAndWait();
	    });
	}

	public User createUserObject(String nome, String telefone, String cpf, String rg, String nacionalidade,
			String estadocivil, String profissao, String idfuncional, String datanascimento, String endereco,
			String cidade, String estado, String cep, String mat1, String ref1, String cargh1, String datainicio1,
			String cargo1, String nivel1, String trienio1, String dataaposentadoria1, String mat2, String ref2,
			String cargh2, String dataInicio2, String cargo2, String nivel2, String trienio2,
			String dataaposentadoria2) {
		User user = new User();
		user.setnome(nome);
		user.settelefone(telefone);
		user.setcpf(cpf);
		user.setrg(rg);
		user.setnacionalidade(nacionalidade);
		user.setestadocivil(estadocivil);
		user.setprofissao(profissao);
		user.setidfuncional(idfuncional);
		user.setdatanascimento(datanascimento);
		user.setendereco(endereco);
		user.setcidade(cidade);
		user.setestado(estado);
		user.setcep(cep);
		user.setmat1(mat1);
		user.setref1(ref1);
		user.setcargh1(cargh1);
		user.setdatainicio1(datainicio1);
		user.setcargo1(cargo1);
		user.setnivel1(nivel1);
		user.settrienio1(trienio1);

		if (dataaposentadoria1 != null) {
			user.setdataaposentadoria1(dataaposentadoria1);
		}
		if (mat2 != null && !mat2.isEmpty()) {
			user.setmat2(mat2);
		}
		if (ref2 != null && !ref2.isEmpty()) {
			user.setref2(ref2);
		}
		if (cargh2 != null && !cargh2.isEmpty()) {
			user.setcargh2(cargh2);
		}
		if (dataInicio2 != null) {
			user.setdatainicio2(dataInicio2);
		}
		if (cargo2 != null && !cargo2.isEmpty()) {
			user.setcargo2(cargo2);
		}
		if (nivel2 != null && !nivel2.isEmpty()) {
			user.setnivel2(nivel2);
		}
		if (trienio2 != null && !trienio2.isEmpty()) {
			user.settrienio2(trienio2);
		}
		if (dataaposentadoria2 != null) {
			user.setdataaposentadoria2(dataaposentadoria2);
		}

		return user;
	}

	// COMBOBOX - COMANDOS E CONTROLE DAS COMBOBOX

		@FXML
		private ComboBox<User> comboClientes; // Componente ComboBox para selecionar cliente
		
		@FXML
		private ComboBox<String> clienteComboBox1;
		

		private ObservableList<User> listaClientes = FXCollections.observableArrayList();

		
		
		// Método de inicialização, pode ser chamado durante a inicialização do
		// controlador
		public void initialize() {
			
			// Configure o ComboBox para exibir os nomes dos clientes
			comboClientes.setItems(listaClientes);
			comboClientes.setCellFactory(cliente -> new ListCell<User>() {
				@Override
				protected void updateItem(User user, boolean empty) {
					super.updateItem(user, empty);
					if (empty || user == null) {
						setText(null);
					} else {
						setText(user.getnome());
					}
				}
			});

			// Force a ComboBox a atualizar sua exibição
			comboClientes.getSelectionModel().select(0);

			// Configure um evento de seleção para a ComboBox
			comboClientes.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Este código é executado quando um item é selecionado na ComboBox
					User selectedUser = comboClientes.getSelectionModel().getSelectedItem();
					if (selectedUser != null) {
					} else {
						if (listaClientes.isEmpty()) {
						} else {
						}
					}
				}
			});
		}

	
	// BOTÃO MOSTRAR TODOS OS CLIENTES - CRUD TELA 2.1

	@FXML
	private Button btnMostrarClientesTela;
	@FXML
	private TextArea txtTodosClientesTela;
	@FXML
	private Button btnBuscarClientes;
	@FXML
	private TextField buscarNome;
	@FXML
	private TextField txtBuscarCliente;
	@FXML
    private ListView<String> listResultadoListView;
	@FXML
	private Label labelMat1CadProc;
	@FXML
	private Label labelMat2CadProc;
	@FXML
	private Button btnMostrarTodosClientes;

	
	
												// Controls TABCADASTROPROCESSO - TELA DE CADASTRO DE PROCESSOS

	
	@FXML
	private Button btnCadastrarprocesso;
	@FXML
	private TextField tfIdCliente;
	@FXML
	private TextField txtStatusProc;
	@FXML
	private TextField txtNomeCliente;
	@FXML
	private TextField txtNumeroProcesso;
	@FXML
	private TextField txtComarca;
	@FXML
	private TextField txtVara;
	@FXML
	private TextField txtTema;
	@FXML
	private TextField txtValorCausa;
	@FXML
	private TextField txtValorHonorarios;
	@FXML
	private TextField txtMatriculaProc;
	@FXML
	private Label labelNomeClienteCadastroProc;
	
	
	public void alertproc(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public Proc createProcObject(String numeroProcesso, String nomecliente, String comarca, String vara, String tema,
		String valorcausa, String valorhono, String statusProc, String Matricula) {
		Proc proc = new Proc();
		proc.setnumeroprocesso(numeroProcesso);
		proc.setnomecliente(nomecliente);
		proc.settema(tema);

		
		if (comarca != null) {
			proc.setcomarca(comarca);
		}
		if (vara != null && !vara.isEmpty()) {
			proc.setvara(vara);
		}

		if (valorcausa != null && !valorcausa.isEmpty()) {
			proc.setvalorcausa(valorcausa);
		}
		if (valorhono != null && !valorhono.isEmpty()) {
			proc.setvalorhono(valorhono);
		}
		if (statusProc != null && !statusProc.isEmpty()) {
			proc.setstatus(statusProc);
		}
		if (Matricula != null && !Matricula.isEmpty()) {
			proc.setMatricula(Matricula);
		}

		return proc;}
	
														//CADASTRO RECURSO
	@FXML
    private ListView<String> listResultadoListViewCadRec;
	@FXML
	private Label labelNomeDoClienteCadRec;
	@FXML
	private Label labelNumeroProcCadRec;
	@FXML
    private TextArea resultadoTextArea3;
	@FXML
	private TextField buscarnumeroprocesso;
	@FXML
	private void onBuscar1Action(ActionEvent event) throws SQLException {
	
	    ProcDao procDao = new ProcDao();
	    String numerosIniciais = buscarnumeroprocesso.getText();
	    try {
	        List<Proc> procs = procDao.buscarProcessosPorNumerosIniciaisDoNumero(numerosIniciais);

	        if (!procs.isEmpty()) {
	            List<String> resultados = new ArrayList<>();
	            for (Proc processo : procs) {
	                resultados.add("Processo encontrado: " + processo.getnumeroprocesso());
	            }
	            exibirResultadosNaTextArea3(resultados);
	        } else {
	            System.out.println("Nenhum processo encontrado");
	            resultadoTextArea3.setText("Nenhum processo encontrado.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void exibirResultadosNaTextArea3(List<String> resultados) {
	    StringBuilder sb = new StringBuilder();
	    for (String resultado : resultados) {
	        sb.append(resultado).append("\n");
	    }
	    resultadoTextArea3.setText(sb.toString());
	
	}
	
										// AÇÃO DE CADASTRO NO BANCO DE DADOS - RECURSO
	
	@FXML
	private Button btnCadastrarrecurso;
	@FXML
	private TextField txtnumerorecurso;
	@FXML
	private TextField buscarNomeRec;
	@FXML
	private TextField txtProcesoOrigem;
	@FXML
	private TextField txtTiporecurso;
	@FXML
	private TextField txtRecorridorecorrente;
	@FXML
	private TextField txtStatus;
	@FXML
	private TextField txtJulgador;
	@FXML
	private TextField txtRelator;
	
	
	public void alertrec(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public Rec createRecObject(String numerorecurso, String nomedocliente, String processoorigem, String tiporecurso, String recorridoourecorrente,
			String status, String julgador, String relator) {
		Rec rec = new Rec();
		rec.setNumerorecurso(numerorecurso);
		rec.setNomedocliente(nomedocliente);
		rec.setProcessoorigem(processoorigem);
		rec.setTiporecurso(tiporecurso);
		rec.setRecorridoourecorrente(recorridoourecorrente);
		rec.setStatus(status);

		
		if (julgador != null) {
			rec.setJulgador(julgador);
		}
		if (relator != null && !relator.isEmpty()) {
			rec.setRelator(relator);
		}
				

		return rec;}
	
												// COMANDOS DE AÇÃO
	
	@FXML
	private Button btnLimparTelaRec;
	
	public static void Main(String[] args) {
		launch(args);
	}

	private static void launch(String[] args) {

	}
	
			//AÇÕES NÃO IDENTIFICADAS NO FXML
			
			// COMANDO DE LIMPEZA DE TELA
			@FXML
			public void onBtnLimparTelaCadastroCliente() {
			    // Limpe todos os campos de texto e labels
				txtNome.clear();
				txtTelefone.clear();
				txtCpf.clear();
				txtRg.clear();
				txtNacionalidade.clear();
				txtEstCivil.clear();
				txtProfissao.clear();
				txtIdFuncional.clear();
				txtDatanscimento.clear();
				txtEndereco.clear();
				txtCidade.clear();
				txtEstado.clear();
				txtCep.clear();
				txtMat1.clear();
				BoxRef1.clear();
				BoxCargH1.clear();
				DataInicio1.clear();
				txtCargo.clear();
				BoxNivelatual1.clear();
				txtTrienioatual1.clear();
				txtdataaposentadoria1.clear();
				txtMat2.clear();
				BoxRef2.clear();
				BoxCargh2.clear();
				datainicio2.clear();
				txtCargo2.clear();
				BoxNivelatual2.clear();
				txtTrienioatual2.clear();
				txtDataaposentadoria2.clear();
				
				
			}
			
			// AÇÃO DE CADASTRO NO BANCO DE DADOS - CLIENTE
			@FXML
			public void onBtnCadastrarClienteAction() {
				btnCadastrarCliente.setOnAction(actionEvent -> {
					String nome = txtNome.getText().trim();
					String telefone = txtTelefone.getText().trim();
					String cpf = txtCpf.getText().trim();
					String rg = txtRg.getText();
					String nacionalidade = txtNacionalidade.getText();
					String estadocivil = txtEstCivil.getText();
					String profissao = txtProfissao.getText();
					String idfuncional = txtIdFuncional.getText();
					String datanascimento = txtDatanscimento.getText();
					String endereco = txtEndereco.getText();
					String cidade = txtCidade.getText();
					String estado = txtEstado.getText();
					String cep = txtCep.getText();
					String mat1 = txtMat1.getText();
					String ref1 = BoxRef1.getText();
					String cargh1 = BoxCargH1.getText();
					String datainicio1 = DataInicio1.getText();
					String cargo1 = txtCargo.getText();
					String nivel1 = BoxNivelatual1.getText();
					String trienio1 = txtTrienioatual1.getText();
					String dataaposentadoria1 = txtdataaposentadoria1.getText();
					String mat2 = txtMat2.getText();
					String ref2 = BoxRef2.getText();
					String cargh2 = BoxCargh2.getText();
					String DataInicio2 = datainicio2.getText();
					String cargo2 = txtCargo2.getText();
					String nivel2 = BoxNivelatual2.getText();
					String trienio2 = txtTrienioatual2.getText();
					String dataaposentadoria2 = txtDataaposentadoria2.getText();

					if (!StringPool.BLANK.equals(nome) && !StringPool.BLANK.equals(telefone) && !StringPool.BLANK.equals(cpf)
							&& !StringPool.BLANK.equals(rg) && !StringPool.BLANK.equals(nacionalidade)
							&& !StringPool.BLANK.equals(estadocivil) && !StringPool.BLANK.equals(profissao)
							&& !StringPool.BLANK.equals(idfuncional) && !StringPool.BLANK.equals(datanascimento)
							&& !StringPool.BLANK.equals(endereco) && !StringPool.BLANK.equals(cidade)
							&& !StringPool.BLANK.equals(estado) && !StringPool.BLANK.equals(cep)
							&& !StringPool.BLANK.equals(mat1) && !StringPool.BLANK.equals(ref1)
							&& !StringPool.BLANK.equals(cargh1) && !StringPool.BLANK.equals(datainicio1)
							&& !StringPool.BLANK.equals(cargo1) && !StringPool.BLANK.equals(nivel1)
							&& !StringPool.BLANK.equals(trienio1)) {

						try {
							if (!userDao.userExists(nome)) {
								// Se o usuário não existir, faça o seguinte:
								User user = this.createUserObject(nome, telefone, cpf, rg, nacionalidade, estadocivil,
										profissao, idfuncional, datanascimento, endereco, cidade, estado, cep, mat1, ref1,
										cargh1, datainicio1, cargo1, nivel1, trienio1, dataaposentadoria1, mat2, ref2, cargh2,
										DataInicio2, cargo2, nivel2, trienio2, dataaposentadoria2);
								// Cria um objeto User com os valores dos campos de texto, exceto os que podem
								// estar em branco
								int userId = userDao.saveUser(user);
								// Salva o objeto User no banco de dados
								if (userId > 0) {
									this.alert("Save", "Successful!", AlertType.INFORMATION);
								} else {
									this.alert("Error", "Failed!", AlertType.ERROR);
								}
							} else {
								// Se o usuário já existir, faça o seguinte:
								this.alert("Error", "User already exists!", AlertType.ERROR);
							}
						} catch (Exception exception) {
							logger.log(Level.SEVERE, exception.getMessage());
						}
					} else {
						this.alert("Error", "Please complete fields!", AlertType.ERROR);
					}
				});
			}
			
			@FXML
			private void limparTelaCadastroProcesso() {
			    // Limpe todos os campos de texto e labels
				txtNumeroProcesso.clear();
				buscarNome.clear();
				listResultadoListView.getItems().clear();
				txtComarca.clear();
				txtVara.clear();
				txtTema.clear();
				txtValorCausa.clear();
				txtValorHonorarios.clear();
				txtStatusProc.clear();
				txtMatriculaProc.clear();
			    
			}
			 
			       
			@FXML
		    private void buscarNomesClientesCadProc() {
		        String textoBuscaProc = buscarNome.getText().trim();

		        if (!textoBuscaProc.isEmpty()) {
		        	
		            try {
		            	 List<String> nomesClientesProc = userDao.buscarNomesClientesPorTextoUser(textoBuscaProc);

		                if (nomesClientesProc.isEmpty()) {
		                	listResultadoListView.getItems().clear(); // Limpa a lista se não houver resultados
		                } else {
		                	listResultadoListView.getItems().setAll(nomesClientesProc); // Define os resultados na ListView
		                }
		            } catch (SQLException e) {
		                // Lide com exceções de SQL aqui, se necessário
		                e.printStackTrace();
		            }
		        } else {
		        	listResultadoListView.getItems().clear(); // Limpa a lista se o campo de texto estiver vazio
		        }
		    }
			@FXML
			private void onSelecionarClienteUserCadProc() throws SQLException {
			    // Obtém o item selecionado da lista
			    Object selectedValue = listResultadoListView.getSelectionModel().getSelectedItem();

			    // Verifica se o item selecionado é uma String
			    if (selectedValue != null && selectedValue instanceof String) {
			        String newValue = (String) selectedValue;

			        // Agora você pode chamar o método buscarRecPorNome com newValue
			        User user = userDao.buscarUserPorNome(newValue);

			       
			        labelNomeClienteCadastroProc.setText(user.getnome());
			        labelMat1CadProc.setText(user.getmat1());
			        labelMat2CadProc.setText(user.getmat2());
			   
			    } else {
			        // Lidar com o caso em que o valor selecionado não é uma String
			    }
			}
			
			
					
			@FXML
			private void buscarNomesClientesCadRec() {
			    String textoBuscaRec = buscarNomeRec.getText().trim();

			    if (textoBuscaRec.isEmpty()) {
			        // Se o campo de busca estiver vazio, obtenha todos os clientes
			        atualizarListViewComNomeProcCadRec("");
			    } else {
			        atualizarListViewComNomeProcCadRec(textoBuscaRec);
			    }
			}
			public void atualizarListViewComNomeProcCadRec(String nomeCliente) {
			    try {
			        // Chame o método do ProcDao para obter a lista de processos para o cliente selecionado
			        List<Proc> processos = procDao.obterProcsPorIniciaisDoNome(nomeCliente);
			        // Ordene a lista de processos por nome
			        Collections.sort(processos, (p1, p2) -> p1.getnomecliente().compareTo(p2.getnomecliente()));

			        // Limpe a ListView
			        listResultadoListViewCadRec.getItems().clear();

			        // Percorra a lista de processos e adicione as informações à ListView
			        for (Proc processo : processos) {
			            String nome = processo.getnomecliente();
			            String numeroProcesso = processo.getnumeroprocesso();

			            String info = nome + " - " + numeroProcesso;
			            listResultadoListViewCadRec.getItems().add(info);
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			        // Lide com exceções SQL, se necessário
			    }
			}
			private void setupListViewClickHandler() {
				listResultadoListViewCadRec.setOnMouseClicked(event -> {
			        // Verifique se um item da ListView foi clicado
			        Object itemSelecionado = listResultadoListViewCadRec.getSelectionModel().getSelectedItem();

			        if (itemSelecionado != null && itemSelecionado instanceof String) {
			            // Converta o item selecionado para uma String
			            String linhaSelecionada = (String) itemSelecionado;

			            // Divida a linha pelo caractere '-' para extrair o nome e o número do processo
			            String[] partes = linhaSelecionada.split(" - ");

			            // Verifique se a linha foi dividida corretamente (deve haver pelo menos duas partes)
			            if (partes.length >= 2) {
			                // Remova espaços em branco em excesso e obtenha o nome e o número do processo
			                String nome = partes[0].trim();
			                String numeroProcesso = partes[1].trim();

			                // Defina o nome e o número do processo nas Labels
			                labelNomeDoClienteCadRec.setText(nome);
			                labelNumeroProcCadRec.setText(numeroProcesso);
			            }
			        }
			    });
			}	
			
			
			// AÇÃO DE CADASTRO NO BANCO DE DADOS - PROCESSO

			@FXML
			public void onBtnCadastrarProcessoAction() {
				btnCadastrarprocesso.setOnAction(actionEvent -> {
					String numeroprocesso = txtNumeroProcesso.getText().trim();
					String nomecliente = labelNomeClienteCadastroProc.getText().trim();
					String comarca = txtComarca.getText().trim();
					String vara = txtVara.getText().trim();
					String tema = txtTema.getText();
					String valorcausa = txtValorCausa.getText();
					String valorhono = txtValorHonorarios.getText();
					String statusProc = txtStatusProc.getText();
					String Matricula = txtMatriculaProc.getText();

					if (!StringPool.BLANK.equals(numeroprocesso) && !StringPool.BLANK.equals(nomecliente)
							&& !StringPool.BLANK.equals(comarca) && !StringPool.BLANK.equals(vara)
							&& !StringPool.BLANK.equals(tema) && !StringPool.BLANK.equals(valorcausa)
							&& !StringPool.BLANK.equals(valorhono) && !StringPool.BLANK.equals(statusProc)&& !StringPool.BLANK.equals(Matricula)) {
						try {
							if 
							
							(!procDao.procExist(numeroprocesso)) {
								// Se o usuário não existir, faça o seguinte:
								Proc proc = this.createProcObject(numeroprocesso, nomecliente, comarca, vara, tema, valorcausa,
										valorhono, statusProc, Matricula);
								System.out.println(proc.getnumeroprocesso());
								ProcDao procDao = new ProcDao();
								// Cria um objeto Proc com os valores dos campos de texto, exceto os que podem
								// estar em branco
								int procIdproc = procDao.saveProc(proc);
								// Salva o objeto Proc no banco de dados

								if (procIdproc > 0) {
									 // Cadastro bem-sucedido, agora atualize a ComboBox no outro controlador
						            consultaProcessoController.atualizarComboBox(procDao.obterNumerosProcessos());
									this.alert("Save", "Successful!", AlertType.INFORMATION);
								} else {
									this.alert("Error", "Failed!", AlertType.ERROR);
								}
							} else {
								// Se o processo já existir, faça o seguinte:
								this.alert("Error", "Proc already exists!", AlertType.ERROR);
							}
						} catch (Exception exception) {
							logger.log(Level.SEVERE, exception.getMessage());
						}
					} else {
						this.alert("Error", "Please complete fields!", AlertType.ERROR);
					}
				});
			}
			
			@FXML
			private void limparTelaCadastroRecurso() {
			    // Limpe todos os campos de texto e labels
				txtnumerorecurso.clear();
				buscarNomeRec.clear();
				listResultadoListViewCadRec.getItems().clear();
				txtTiporecurso.clear();
				txtRecorridorecorrente.clear();
				txtStatus.clear();
				txtJulgador.clear();
				txtRelator.clear();
			    
			}
			@FXML
			public void onBuscarActionRec() {
			    String nomeCliente = buscarNomeRec.getText();

			    if (nomeCliente.isEmpty()) {
			        // Se o campo estiver em branco, obtenha todos os clientes
			        atualizarListViewComNomeProcCadRec("");
			    } else {
			        // Caso contrário, obtenha os clientes com base nas iniciais do nome
			        atualizarListViewComNomeProcCadRec(nomeCliente);
			    }
			}
			@FXML
			public void onBtnCadastrarRecursoAction() {
				btnCadastrarrecurso.setOnAction(actionEvent -> {
					String numerorecurso = txtnumerorecurso.getText().trim();
					String nomedocliente = labelNomeDoClienteCadRec.getText().trim();
					String processoorigem = labelNumeroProcCadRec.getText().trim();
					String tiporecurso = txtTiporecurso.getText().trim();
					String recorridoourecorrente = txtRecorridorecorrente.getText();
					String status = txtStatus.getText();
					String julgador = txtJulgador.getText();
					String relator = txtRelator.getText();

					if (!StringPool.BLANK.equals(numerorecurso) && !StringPool.BLANK.equals(nomedocliente)
							&& !StringPool.BLANK.equals(processoorigem) && !StringPool.BLANK.equals(tiporecurso)
							&& !StringPool.BLANK.equals(recorridoourecorrente) && !StringPool.BLANK.equals(status)
							&& !StringPool.BLANK.equals(julgador)&& !StringPool.BLANK.equals(relator)) {
				
						try {
							if 
							
							(!recDao.recExist(numerorecurso)) {
								// Se o usuário não existir, faça o seguinte:
								Rec rec = this.createRecObject(numerorecurso, nomedocliente, processoorigem, tiporecurso, recorridoourecorrente, status, julgador, relator);
								System.out.println(rec.getNumerorecurso());
								RecDao recDao = new RecDao();
								// Cria um objeto Proc com os valores dos campos de texto, exceto os que podem
								// estar em branco
								int recIdrec = recDao.saveRec(rec);
								// Salva o objeto Rec no banco de dados

								if (recIdrec > 0) {
									this.alert("Save", "Successful!", AlertType.INFORMATION);
								} else {
									this.alert("Error", "Failed!", AlertType.ERROR);
								}
							} else {
								// Se o recurso já existir, faça o seguinte:
								this.alert("Error", "Rec already exists!", AlertType.ERROR);
							}
						} catch (Exception exception) {
							logger.log(Level.SEVERE, exception.getMessage());
						}
					} else {
						this.alert("Error", "Please complete fields!", AlertType.ERROR);
					}
				});
			}
			
			@FXML
			private Button btnNovoprocesso1;

			public void btnNovoprocesso1() {
			    try {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiWAdv/TelaW3ConsultaProcesso.fxml"));
			        Parent root = loader.load();
			        Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.getIcons().add(new Image(getClass().getResourceAsStream("/guiWAdv/LOGO.png")));
		            stage.setTitle("Werner Advogados");
			        stage.setScene(scene);

			        // Obtém uma referência ao controlador do ConsultaProcessoController
			        Tela03ConsultaProcessoController consultaController = loader.getController();

			        // Inicialize o ComboBox clienteComboBox1 (supondo que você tenha um método de inicialização)
			        consultaController.initializeComboBox();

			        stage.show();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
			@FXML
			private Button btnNovocliente1;

			public void btnNovocliente1() {
			    try {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiWAdv/TelaW2ConsultaCliente.fxml"));
			        Parent root = loader.load();
			        Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.setScene(scene);
			        
			        stage.getIcons().add(new Image(getClass().getResourceAsStream("/guiWAdv/LOGO.png")));
		            stage.setTitle("Werner Advogados");
			        // Obtém uma referência ao controlador do ConsultaProcessoController
			        Tela02ConsultaClienteController consultaClienteController = loader.getController();

			        // Inicialize o ComboBox clienteComboBox2 (supondo que você tenha um método de inicialização)
			        consultaClienteController.initializeComboBox();

			        stage.show();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
			@FXML
			private Button btnTABPANEGerarDoc;

			public void btnTABPANEGerarDoc() {
			    try {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiWAdv/TelaW4GeradorDeDocumento.fxml"));
			        Parent root = loader.load();
			        Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.getIcons().add(new Image(getClass().getResourceAsStream("/guiWAdv/LOGO.png")));
		            stage.setTitle("Werner Advogados");
			        stage.setScene(scene);

			        // Obtém uma referência ao controlador do ConsultaProcessoController
			        Tela04GeradorDeDocumentoController GeradorDeDocumentoController = loader.getController();

			        // Inicialize o ComboBox clienteComboBox1 (supondo que você tenha um método de inicialização)
			        GeradorDeDocumentoController.initializeComboBox();

			        stage.show();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
			
			@FXML
			private Button btnTABPANECalculos;

			public void btnTABPANECalculos() {
			    try {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiWAdv/TelaW5Calculos.fxml"));
			        Parent root = loader.load();
			        Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.getIcons().add(new Image(getClass().getResourceAsStream("/guiWAdv/LOGO.png")));
		            stage.setTitle("Werner Advogados");
			        stage.setScene(scene);

			        // Obtém uma referência ao controlador do ConsultaProcessoController
			        Tela05CalculosController calculosController = loader.getController();

			        stage.show();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
	
	//BUSCAS GERAIS
			
			@FXML
			private Label labelNomeTabBuscaProcNome;
			@FXML
			private Label labelNumeroTabBuscaProcNome;
			@FXML
			private Label labelComarcaTabBuscaProcNome;
			@FXML
			private Label labelVaraTabBuscaProcNome;
			@FXML
			private Label labelTemaTabBuscaProcNome;
			@FXML
			private Label labelValorTabBuscaProcNome;
			@FXML
			private Label labelHonorariosTabBuscaProcNome;
			@FXML
			private Label labelAndamentoTabBuscaProcNome;
			@FXML
			private Label labelTemaTabBuscaProcNome1;
			@FXML
			private Button btnBuscarTabBuscaProcNome;
			@FXML
			private ListView<String> listResultadosNumerosTabBuscaProcNome;
			@FXML
			private TextArea txaResultadoTabBuscaProcNome;
			@FXML
			private TextField txtBuscaPorNomeTabBuscaProcNome;

			// MÉTODOS PARA BUSCAS GERAIS
			
			@FXML
			private void onbtnBuscarClienteTabBuscaProcNome(ActionEvent event) throws SQLException {
			    UserDao userDao = new UserDao();
			    String letrasIniciais = txtBuscaPorNomeTabBuscaProcNome.getText();
			    try {
			    List<User> clientes = userDao.buscaClientesPorLetrasIniciaisDoNome(letrasIniciais);

		        if (!clientes.isEmpty()) {
		            List<String> resultados = new ArrayList<>();
		            for (User cliente : clientes) {
		                resultados.add(cliente.getnome());
		            }
		            // Limpe a ListView antes de exibir os novos resultados
		            listResultadosNumerosTabBuscaProcNome.getItems().clear();
		            listResultadosNumerosTabBuscaProcNome.getItems().addAll(resultados);
		        } else {
		           
		            // Limpe a ListView se nenhum resultado for encontrado
		        	listResultadosNumerosTabBuscaProcNome.getItems().clear();
		        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			}
			
			
		
			
			
}
