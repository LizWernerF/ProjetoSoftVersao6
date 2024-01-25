package guiWAdv;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

		listResultadoListView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
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

		
		
		 listNomes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        try {
					onSelecionarCliente();
				} catch (SQLException e) {	
					e.printStackTrace();
				}
		    });

		 listProcNomes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        try {
		            onSelecionarProc();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    });
		 listProcs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			    try {
			        onSelecionarProcNumero();
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			});

		 preencherComboBoxComarcas();
		 
		 comboBuscaComarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			    if (newValue != null) {
			        try {
			            // Obtenha a "comarca" selecionada
			            String comarcaSelecionada = newValue.toString();

			            // Busque os "nomecliente" correspondentes à "comarca" selecionada
			            List<String> nomesClientes = obterNomesClientesPorComarca(comarcaSelecionada);

			            // Use um conjunto para remover duplicatas e ordenar em ordem alfabética
			            Set<String> nomesUnicosOrdenados = new TreeSet<>(nomesClientes);

			            // Atualize a ListView listUserComarcas com os resultados
			            ObservableList<String> items = FXCollections.observableArrayList(nomesUnicosOrdenados);
			            listUserComarcas.setItems(items);
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Trate a exceção conforme necessário
			        }
			    }
			});

		 listUserComarcas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			    if (newValue != null) {
			        try {
			            // Obtenha o nome do cliente selecionado
			            String nomeClienteSelecionado = newValue.toString();

			            // Obtenha a comarca atualmente selecionada na ComboBox
			            String comarcaSelecionada = (String) comboBuscaComarca.getSelectionModel().getSelectedItem();

			            // Obtenha os números do processo relacionados a esse cliente e comarca
			            List<String> numerosProcesso = procDao.obterNumerosProcessoPorNomeEComarca(nomeClienteSelecionado, comarcaSelecionada);

			            // Atualize a ListView listProcComarcas com os resultados
			            ObservableList<String> items = FXCollections.observableArrayList(numerosProcesso);
			            listProcComarcas.setItems(items);
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Trate a exceção conforme necessário
			        }
			    }
			});

		 listProcComarcas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			    if (newValue != null) {
			        try {
			            // Obtenha o número do processo selecionado
			            String numeroProcessoSelecionado = newValue.toString();

			            // Use o número do processo para buscar os detalhes do processo
			            Proc processoSelecionado = procDao.buscarProcessoPorNumero(numeroProcessoSelecionado);

			            // Preencha os campos com os detalhes do processo
			            if (processoSelecionado != null) {
			                txtNomeSelecionado2.setText(processoSelecionado.getnomecliente());
			                txtProcSelecionado2.setText(processoSelecionado.getnumeroprocesso());
			                labelComarca2.setText(processoSelecionado.getcomarca());
			                labelVara2.setText(processoSelecionado.getvara());
			                labelTema2.setText(processoSelecionado.gettema());
			                txtStatusComarca.setText(processoSelecionado.getstatus());

			                // Obtém o ID do processo
			                int idProcesso = processoSelecionado.getIdproc();

			                // Atualiza a exibição dos andamentos
			                exibirAndamentos2(idProcesso);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Trate a exceção conforme necessário
			        }
			    }
			});

		 preencherComboBoxComarcasVara();
		 
		 preencherComboBoxVara();
		 
		 comboBuscaVaraComarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        if (newValue != null) {
		            onComarcaVaraSelecionada();
		        }
		    });

		    // Adiciona um ouvinte à ComboBox de varas
		    comboBuscaVara.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        if (newValue != null) {
		            onVaraSelecionada();
		        }
		    });
		 
		
		    listNomesVara.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			    if (newValue != null) {
			        try {
			            // Obtenha o nome do cliente selecionado
			            String selectedNomeCliente = newValue.toString();

			            // Obtenha a comarca atualmente selecionada na ComboBox
			            String selectedComarca = (String) comboBuscaVaraComarca.getSelectionModel().getSelectedItem();
			            
			            String selectedVara = (String) comboBuscaVara.getSelectionModel().getSelectedItem();

			            // Obtenha os números do processo relacionados a esse cliente e comarca
			            List<String> numerosProcesso = procDao.obterNumerosProcessoPorNomeEComarcaEVara(selectedNomeCliente, selectedComarca, selectedVara);
				        
			            // Atualize a ListView listProcComarcas com os resultados
			            ObservableList<String> items = FXCollections.observableArrayList(numerosProcesso);
			            listProcNomesVara.setItems(items);
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Trate a exceção conforme necessário
			        }
			    }
			});
		 
		    listProcNomesVara.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        if (newValue != null) {
		            onSelecionarProcNumeroVara();
		        }
		    });
		 
		    preencherComboBoxTemas();
		 
		    comboBuscaTema.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        onTemaSelecionado();
		    });
		    
		    listNomesTema.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        onNomeClienteSelecionadoTema();
		    });
		    
		    listProcNomesTema.getSelectionModel().selectedItemProperty().addListener(
		            (observable, oldValue, newValue) -> onSelecionarProcNumeroTema());
		    
		    
		    
		    preencherComboBoxTemasStatus();
			
		    preencherComboBoxTemasStatus();
		    
		    comboBuscaStatusTema.getSelectionModel().selectedItemProperty().addListener(
		            (observable, oldValue, newValue) -> onTemaStatusSelecionado());
		
		    comboBuscaStatus.getSelectionModel().selectedItemProperty().addListener(
		            (observable, oldValue, newValue) -> onStatusSelecionado());
		    
		    
		    listNomesStatus.setOnMouseClicked(event -> {
		        String selectedNomeStatus = listNomesStatus.getSelectionModel().getSelectedItem();
		        if (selectedNomeStatus != null) {
		            // Separe o número do processo do restante do texto
		            String numeroProcesso = selectedNomeStatus.split(" - ")[1];

		            try {
		                // Chame o método para obter os detalhes do processo
		                ProcDao procDao = new ProcDao();
		                Proc processo = procDao.obterDetalhesPorNumeroProcesso(numeroProcesso);

		                // Atualize os campos na interface gráfica
		                txtNomeSelecionado41.setText(processo.getnomecliente());
		                txtProcSelecionado41.setText(processo.getnumeroprocesso());
		                labelComarca41.setText(processo.getcomarca());
		                labelVara41.setText(processo.getvara());
		                labelTema41.setText(processo.gettema());
		                txtStatusTema1.setText(processo.getvalorcausa());

		                // Preencha a TextArea com os movimentos do processo (adaptando conforme necessário)
		                HistoricoDao historicoDao = new HistoricoDao();
		                List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(processo.getIdproc());

		                taAndamentos41.clear();
		                for (String movimento : movimentos) {
		                    taAndamentos41.appendText(movimento + "\n");
		                }
		                taAndamentos41.setWrapText(true);

		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    });
		 
		 
		 
		 
		 
		 
		
		
		// FECHA INITIALIZE
	}

	
	
	public static void main(String[] args) {
		launch(args);
	}

	// TAB HOME E CONFIG GERAIS

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

		return proc;
	}

	// CADASTRO RECURSO
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

	public Rec createRecObject(String numerorecurso, String nomedocliente, String processoorigem, String tiporecurso,
			String recorridoourecorrente, String status, String julgador, String relator) {
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

		return rec;
	}

	// COMANDOS DE AÇÃO

	@FXML
	private Button btnLimparTelaRec;

	public static void Main(String[] args) {
		launch(args);
	}

	private static void launch(String[] args) {

	}

	// AÇÕES NÃO IDENTIFICADAS NO FXML

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
			// Chame o método do ProcDao para obter a lista de processos para o cliente
			// selecionado
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

				// Verifique se a linha foi dividida corretamente (deve haver pelo menos duas
				// partes)
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
					&& !StringPool.BLANK.equals(valorhono) && !StringPool.BLANK.equals(statusProc)
					&& !StringPool.BLANK.equals(Matricula)) {
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
					&& !StringPool.BLANK.equals(julgador) && !StringPool.BLANK.equals(relator)) {

				try {
					if

					(!recDao.recExist(numerorecurso)) {
						// Se o usuário não existir, faça o seguinte:
						Rec rec = this.createRecObject(numerorecurso, nomedocliente, processoorigem, tiporecurso,
								recorridoourecorrente, status, julgador, relator);
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

			// Inicialize o ComboBox clienteComboBox1 (supondo que você tenha um método de
			// inicialização)
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

			// Inicialize o ComboBox clienteComboBox2 (supondo que você tenha um método de
			// inicialização)
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

			// Inicialize o ComboBox clienteComboBox1 (supondo que você tenha um método de
			// inicialização)
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

			Tela05CalculosController calculosController = loader.getController();

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// BUSCAS GERAIS
	// BUSCA POR NOME
	@FXML
	private TextField txtBuscaNome;
	@FXML
	private Button btnBuscaNome;
	@FXML
	private ListView<String> listNomes;
	@FXML
	private ListView<String> listProcNomes;
	@FXML
	private TextField txtNomeSelecionado;
	@FXML
	private TextField txtProcSelecionado;
	@FXML
	private Label labelComarca;
	@FXML
	private Label labelVara;
	@FXML
	private Label labelTema;
	@FXML
	private TextArea taAndamentos;
	@FXML
	private TextField txtDataNovoAndamento;
	@FXML
	private TextField txtNovoAndamento;
	@FXML
	private Button btnNovoAndamento;
	@FXML
	private TextField txtStatusNome;
	
	//MÉTODOS
	@FXML
	private void buscarporNome() {
	    String textoBuscaCliente = txtBuscaNome.getText().trim();

	    try {
	        ProcDao procDao = new ProcDao();
	        List<String> nomesClientes = procDao.buscarNomesClientesPorTexto(textoBuscaCliente);

	        Set<String> nomesUnicos = new TreeSet<>(nomesClientes);

	        listNomes.getItems().setAll(new ArrayList<>(nomesUnicos));
	        // Define os resultados na ListView
	    } catch (SQLException e) {
	        // Lide com exceções de SQL aqui, se necessário
	        e.printStackTrace();
	    }
	}


	@FXML
	private void onSelecionarCliente() throws SQLException {
	  
	    String selectedCliente = (String) listNomes.getSelectionModel().getSelectedItem();

	    if (selectedCliente != null) {
	        try {
	            ProcDao procDao = new ProcDao();
	            List<String> numerosProcessos = procDao.buscarNumerosProcPorNomeCliente(selectedCliente);

	            if (!numerosProcessos.isEmpty()) {
	                txtNomeSelecionado.setText(selectedCliente);

	               
	                listProcNomes.getItems().setAll(numerosProcessos);
	            }
	        } catch (SQLException e) {
	            // Lide com exceções de SQL aqui, se necessário
	            e.printStackTrace();
	        }
	    }
	}
	
	private Proc getSelectedProc() {
	    String selectedProcNumber = listProcNomes.getSelectionModel().getSelectedItem();
	    try {
	        ProcDao procDao = new ProcDao();
	        return procDao.buscarProcessoPorNumero(selectedProcNumber);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null; // Lide com a exceção adequadamente na sua aplicação
	    }
	}

	@FXML
	private void onSelecionarProc() throws SQLException {
	    String selectedProc = (String) listProcNomes.getSelectionModel().getSelectedItem();

	    if (selectedProc != null) {
	        try {
	            ProcDao procDao = new ProcDao();
	            Proc proc = procDao.buscarProcessoPorNumero(selectedProc);

	            if (proc != null) {
	                // Preencha as TextFields e Labels com os detalhes do processo
	                txtNomeSelecionado.setText(proc.getnomecliente());
	                txtProcSelecionado.setText(proc.getnumeroprocesso());
	                labelComarca.setText(proc.getcomarca());
	                labelVara.setText(proc.getvara());
	                labelTema.setText(proc.gettema());
	                txtStatusNome.setText(proc.getstatus());

	                Proc selectedProcObject = getSelectedProc();
	                // Obtém o ID do processo
	                int idProcesso = proc.getIdproc();

	                // Atualiza a exibição dos andamentos
	                exibirAndamentos(idProcesso);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        // Lógica para lidar com nenhum item selecionado, se necessário
	    }
	}
	
	

	private void exibirAndamentos(int idProcesso) {
	    try {
	        HistoricoDao historicoDao = new HistoricoDao();
	        List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso1(idProcesso);

	        // Limpe o TextArea
	        taAndamentos.clear();

	        // Exiba os movimentos no TextArea
	        for (String movimento : movimentos) {
	            taAndamentos.appendText(movimento + "\n");
	        }
	        taAndamentos.setWrapText(true);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private void inserirMovimento() {
	    String descricao = txtNovoAndamento.getText();
	    String data = txtDataNovoAndamento.getText();

	    if (!descricao.isEmpty() && !data.isEmpty()) {
	        Historico historico = new Historico();
	        historico.setDescricao(descricao);
	        historico.setData(data);

	        // Obtenha o processo selecionado (você precisa implementar esse método)
	        Proc selectedProc = getSelectedProc();

	        if (selectedProc != null) {
	            try {
	                // Salve o histórico no banco de dados usando o HistoricoDao
	                HistoricoDao historicoDao = new HistoricoDao();
	                historicoDao.saveHistorico(historico, selectedProc.getIdproc()); // Passe o ID do processo

	                // Associe o histórico ao processo
	                selectedProc.adicionarHistorico(historico);

	                // Limpe os campos após a inserção
	                txtNovoAndamento.clear();
	                txtDataNovoAndamento.clear();

	                // Atualize a exibição do histórico
	                atualizarHistorico(selectedProc);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                // Lide com erros de SQL, se necessário
	            }
	        }
	    } else {
	        // Lide com o caso em que os campos estão vazios
	    }
	}
	
	private void atualizarHistorico(Proc selectedProc) {
	    if (selectedProc != null) {
	        try {
	            HistoricoDao historicoDao = new HistoricoDao();
	            List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(selectedProc.getIdproc());

	            // Limpe o TextArea
	            taAndamentos.clear();

	            // Exiba os movimentos no TextArea
	            for (String movimento : movimentos) {
	                taAndamentos.appendText(movimento + "\n");
	            }
	            taAndamentos.setWrapText(true);  // Certifique-se de que o WrapText está configurado corretamente
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Lide com erros de SQL, se necessário
	        }
	    }
	}

	
	

	// BUSCA POR PROCESSO
	@FXML
	private TextField txtBuscaProc;
	@FXML
	private Button btnBuscaProc;
	@FXML
	private ListView<String> listProcs;
	@FXML
	private TextField txtNomeSelecionado1;
	@FXML
	private TextField txtProcSelecionado1;
	@FXML
	private Label labelComarca1;
	@FXML
	private Label labelVara1;
	@FXML
	private Label labelTema1;
	@FXML
	private TextArea taAndamentos1;
	@FXML
	private TextField txtDataNovoAndamento1;
	@FXML
	private TextField txtNovoAndamento1;
	@FXML
	private Button btnNovoAndamento1;
	@FXML
	private TextField txtStatusProcesso;

	// MÉTODOS
	@FXML
	private void buscarPorNumero() {
	    String textoBuscaProc = txtBuscaProc.getText().trim();

	    try {
	        ProcDao procDao = new ProcDao();
	        List<String> numerosProcessos = procDao.buscarNumerosProcessoPorTexto(textoBuscaProc);

	        listProcs.getItems().setAll(new ArrayList<>(numerosProcessos));
	        // Define os resultados na ListView
	    } catch (SQLException e) {
	        // Lide com exceções de SQL aqui, se necessário
	        e.printStackTrace();
	    }
	}

	@FXML
	private void onSelecionarProcNumero() throws SQLException {
	    String selectedProcNumero = (String) listProcs.getSelectionModel().getSelectedItem();

	    if (selectedProcNumero != null) {
	        try {
	            ProcDao procDao = new ProcDao();
	            Proc proc = procDao.buscarProcessoPorNumero(selectedProcNumero);

	            if (proc != null) {
	                // Preencha as TextFields e Labels com os detalhes do processo
	                txtNomeSelecionado1.setText(proc.getnomecliente());
	                txtProcSelecionado1.setText(proc.getnumeroprocesso());
	                labelComarca1.setText(proc.getcomarca());
	                labelVara1.setText(proc.getvara());
	                labelTema1.setText(proc.gettema());
	                txtStatusProcesso.setText(proc.getstatus());

	                Proc selectedProcObject = getSelectedProcNumero();
	                // Obtém o ID do processo
	                int idProcesso = proc.getIdproc();

	                // Atualiza a exibição dos andamentos
	                exibirAndamentosNumero(idProcesso);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        // Lógica para lidar com nenhum item selecionado, se necessário
	    }
	}
	
	private void exibirAndamentosNumero(int idProcesso) {
	    try {
	        HistoricoDao historicoDao = new HistoricoDao();
	        List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso1(idProcesso);

	        // Limpe o TextArea
	        taAndamentos1.clear();

	        // Exiba os movimentos no TextArea
	        for (String movimento : movimentos) {
	            taAndamentos1.appendText(movimento + "\n");
	        }
	        taAndamentos1.setWrapText(true);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private Proc getSelectedProcNumero() {
	    String selectedProcNumber = listProcs.getSelectionModel().getSelectedItem();
	    try {
	        ProcDao procDao = new ProcDao();
	        return procDao.buscarProcessoPorNumero(selectedProcNumber);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null; // Lide com a exceção adequadamente na sua aplicação
	    }
	}

	@FXML
	private void inserirMovimentoNumero() {
	    String descricao = txtNovoAndamento1.getText();
	    String data = txtDataNovoAndamento1.getText();

	    if (!descricao.isEmpty() && !data.isEmpty()) {
	        Historico historico = new Historico();
	        historico.setDescricao(descricao);
	        historico.setData(data);

	        // Obtenha o processo selecionado (você precisa implementar esse método)
	        Proc selectedProc = getSelectedProcNumero();

	        if (selectedProc != null) {
	            try {
	                // Salve o histórico no banco de dados usando o HistoricoDao
	                HistoricoDao historicoDao = new HistoricoDao();
	                historicoDao.saveHistorico(historico, selectedProc.getIdproc()); // Passe o ID do processo

	                // Associe o histórico ao processo
	                selectedProc.adicionarHistorico(historico);

	                // Limpe os campos após a inserção
	                txtNovoAndamento1.clear();
	                txtDataNovoAndamento1.clear();

	                // Atualize a exibição do histórico
	                atualizarHistoricoNumero(selectedProc);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                // Lide com erros de SQL, se necessário
	            }
	        }
	    } else {
	        // Lide com o caso em que os campos estão vazios
	    }
	}

	private void atualizarHistoricoNumero(Proc selectedProc) {
	    if (selectedProc != null) {
	        try {
	            HistoricoDao historicoDao = new HistoricoDao();
	            List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(selectedProc.getIdproc());

	            // Limpe o TextArea
	            taAndamentos1.clear();

	            // Exiba os movimentos no TextArea
	            for (String movimento : movimentos) {
	                taAndamentos1.appendText(movimento + "\n");
	            }
	            taAndamentos1.setWrapText(true);  // Certifique-se de que o WrapText está configurado corretamente
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Lide com erros de SQL, se necessário
	        }
	    }
	}

	
	
	
	// BUSCA POR COMARCA
	@FXML
	private ComboBox<String> comboBuscaComarca;
	@FXML
	private ListView<String> listUserComarcas;
	@FXML
	private ListView<String> listProcComarcas;
	@FXML
	private TextField txtNomeSelecionado2;
	@FXML
	private TextField txtProcSelecionado2;
	@FXML
	private Label labelComarca2;
	@FXML
	private Label labelVara2;
	@FXML
	private Label labelTema2;
	@FXML
	private TextArea taAndamentos2;
	@FXML
	private TextField txtDataNovoAndamento2;
	@FXML
	private TextField txtNovoAndamento2;
	@FXML
	private Button btnNovoAndamento2;
	@FXML
	private TextField txtStatusComarca;
	
	
	private List<String> obterListaComarcas() throws SQLException {
	    ProcDao procDao = new ProcDao();
	    return procDao.obterComarcas();
	}

	private void preencherComboBoxComarcas() {
	    try {
	        List<String> comarcas = obterListaComarcas();
	        Collections.sort(comarcas);  // Ordena a lista em ordem alfabética
	        ObservableList<String> items = FXCollections.observableArrayList(comarcas);
	        comboBuscaComarca.setItems(items);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Trate a exceção conforme necessário
	    }
	}

	public List<String> obterNomesClientesPorComarca(String comarca) throws SQLException {
	    ProcDao procDao = new ProcDao();
	    return procDao.obterNomesClientesPorComarca(comarca);
	}

	@FXML
	private void onNomeClienteSelecionado() {
	    String selectedNomeCliente = (String) listUserComarcas.getSelectionModel().getSelectedItem();
	    String selectedComarca = (String) comboBuscaComarca.getSelectionModel().getSelectedItem();

	    if (selectedNomeCliente != null && selectedComarca != null) {
	        try {
	            List<String> numerosProcesso = procDao.obterNumerosProcessoPorNomeEComarca(selectedNomeCliente, selectedComarca);
	            listProcComarcas.getItems().setAll(new ArrayList<>(numerosProcesso));
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	        }
	    }
	}
	private void exibirAndamentos2(int idProcesso) {
	    try {
	        HistoricoDao historicoDao = new HistoricoDao();
	        List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso1(idProcesso);

	        // Limpe a TextArea
	        taAndamentos2.clear();

	        // Exiba os movimentos na TextArea
	        for (String movimento : movimentos) {
	            taAndamentos2.appendText(movimento + "\n");
	        }
	        taAndamentos2.setWrapText(true);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private void inserirMovimento2() {
	    String descricao = txtNovoAndamento2.getText();
	    String data = txtDataNovoAndamento2.getText();

	    if (!descricao.isEmpty() && !data.isEmpty()) {
	        Historico historico = new Historico();
	        historico.setDescricao(descricao);
	        historico.setData(data);

	        // Obtenha o processo selecionado (você precisa implementar esse método)
	        Proc selectedProc = getSelectedProc();

	        if (selectedProc != null) {
	            try {
	                // Salve o histórico no banco de dados usando o HistoricoDao
	                HistoricoDao historicoDao = new HistoricoDao();
	                historicoDao.saveHistorico(historico, selectedProc.getIdproc()); // Passe o ID do processo

	                // Associe o histórico ao processo
	                selectedProc.adicionarHistorico(historico);

	                // Limpe os campos após a inserção
	                txtNovoAndamento2.clear();
	                txtDataNovoAndamento2.clear();

	                // Atualize a exibição do histórico
	                atualizarHistorico2(selectedProc);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                // Lide com erros de SQL, se necessário
	            }
	        }
	    } else {
	        // Lide com o caso em que os campos estão vazios
	    }
	}

	private void atualizarHistorico2(Proc selectedProc) {
	    if (selectedProc != null) {
	        try {
	            HistoricoDao historicoDao = new HistoricoDao();
	            List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(selectedProc.getIdproc());

	            // Limpe a TextArea
	            taAndamentos2.clear();

	            // Exiba os movimentos na TextArea
	            for (String movimento : movimentos) {
	                taAndamentos2.appendText(movimento + "\n");
	            }
	            taAndamentos2.setWrapText(true);  // Certifique-se de que o WrapText está configurado corretamente
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Lide com erros de SQL, se necessário
	        }
	    }
	}


	

	// BUSCA POR VARA
	@FXML
	private ComboBox<String> comboBuscaVaraComarca;
	@FXML
	private ComboBox<String> comboBuscaVara;
	@FXML
	private ListView<String> listNomesVara;
	@FXML
	private ListView<String> listProcNomesVara;
	@FXML
	private TextField txtNomeSelecionado3;
	@FXML
	private TextField txtProcSelecionado3;
	@FXML
	private Label labelComarca3;
	@FXML
	private Label labelVara3;
	@FXML
	private Label labelTema3;
	@FXML
	private TextArea taAndamentos3;
	@FXML
	private TextField txtDataNovoAndamento3;
	@FXML
	private TextField txtNovoAndamento3;
	@FXML
	private Button btnNovoAndamento3;
	@FXML
	private TextField txtStatusVara;
	
	 public List<String> obterVarasPorComarca(String comarca) {
	        try {
	            return procDao.obterVarasPorComarca(comarca);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	            return new ArrayList<>();
	        }
	    }

	    public List<String> obterNomesPorVara(String vara) {
	        try {
	            return procDao.obterNomesPorVara(vara);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	            return new ArrayList<>();
	        }
	    }

	    public List<String> obterNumerosProcessoPorVara(String vara) {
	        try {
	            return procDao.obterNumerosProcessoPorVara(vara);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	            return new ArrayList<>();
	        }
	    }
	private void onComarcaVaraSelecionada() {
        String selectedComarca = comboBuscaVaraComarca.getValue();
        if (selectedComarca != null) {
            // Obtenha as varas correspondentes à comarca
			List<String> varas = obterVarasPorComarca(selectedComarca);

			// Preencha a ComboBox de varas
			ObservableList<String> items = FXCollections.observableArrayList(varas);
			comboBuscaVara.setItems(items);
        }
    }
	
	public List<String> obterNomesPorVaraeComarca(String vara, String comarca) {
	    try {
	        return procDao.obterNomesPorVaraeComarca(vara, comarca);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Trate a exceção conforme necessário
	        return new ArrayList<>();
	    }
	}

	private void onVaraSelecionada() {
	    String selectedVara = comboBuscaVara.getValue();
	    String selectedComarca = comboBuscaVaraComarca.getValue();
	    if (selectedVara != null && selectedComarca != null) {
	        // Obtenha os nomes e processos relacionados à vara e comarca selecionadas
	        List<String> nomes = obterNomesPorVaraeComarca(selectedVara, selectedComarca);

	        // Preencha as listas de nomes e processos
	        listNomesVara.setItems(FXCollections.observableArrayList(nomes));
	    }
	}
	
	
    private void preencherComboBoxComarcasVara() {
        try {
            // Obtenha a lista de comarcas
            List<String> comarcas = obterListaComarcas();
            Collections.sort(comarcas);  // Ordena a lista em ordem alfabética

            // Preenche a ComboBox de comarcas para varas
            ObservableList<String> items = FXCollections.observableArrayList(comarcas);
            comboBuscaVaraComarca.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        }
    }

    private List<String> obterListaVaras() throws SQLException {
	    ProcDao procDao = new ProcDao();
	    return procDao.obterVaras();
	}
    
    private void preencherComboBoxVara() {
        try {
            // Obtenha a lista de varas
            List<String> varas = obterListaVaras();
            Collections.sort(varas);  // Ordena a lista em ordem alfabética

            // Preenche a ComboBox de varas
            ObservableList<String> items = FXCollections.observableArrayList(varas);
            comboBuscaVara.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        }
    }
	
    @FXML
    private void onNomeClienteSelecionadoVara() {
        String selectedNomeCliente = (String) listNomesVara.getSelectionModel().getSelectedItem();
        String selectedVara = (String) comboBuscaVara.getSelectionModel().getSelectedItem();
        String selectedComarca = (String) comboBuscaVaraComarca.getSelectionModel().getSelectedItem();

        if (selectedNomeCliente != null && selectedComarca != null && selectedVara != null) {
            try {
                List<String> numerosProcesso = procDao.obterNumerosProcessoPorNomeEComarcaEVara(selectedNomeCliente, selectedComarca, selectedVara);
                listProcNomesVara.getItems().setAll(new ArrayList<>(numerosProcesso));
            } catch (SQLException e) {
                e.printStackTrace();
                // Trate a exceção conforme necessário
            }
        }
    }
	
    @FXML
    private void onSelecionarProcNumeroVara() {
        String selectedProcNumero = listProcNomesVara.getSelectionModel().getSelectedItem();

        if (selectedProcNumero != null) {
            try {
                ProcDao procDao = new ProcDao();
                Proc proc = procDao.buscarProcessoPorNumero(selectedProcNumero);

                if (proc != null) {
                    // Preencha as TextFields e Labels com os detalhes do processo
                    txtNomeSelecionado3.setText(proc.getnomecliente());
                    txtProcSelecionado3.setText(proc.getnumeroprocesso());
                    labelComarca3.setText(proc.getcomarca());
                    labelVara3.setText(proc.getvara());
                    labelTema3.setText(proc.gettema());
                    txtStatusVara.setText(proc.getstatus());

                    // Obtém o ID do processo
                    int idProcesso = proc.getIdproc();

                    // Atualiza a exibição dos andamentos
                    exibirAndamentosVara(idProcesso);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Lógica para lidar com nenhum item selecionado, se necessário
        }
    }
	
    private void exibirAndamentosVara(int idProcesso) {
        try {
            HistoricoDao historicoDao = new HistoricoDao();
            List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(idProcesso);

            // Limpe a TextArea
            taAndamentos3.clear();

            // Exiba os movimentos na TextArea
            for (String movimento : movimentos) {
                taAndamentos3.appendText(movimento + "\n");
            }
            taAndamentos3.setWrapText(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Proc getSelectedProcVara() {
	    String selectedProcNumber = listProcNomesVara.getSelectionModel().getSelectedItem();
	    try {
	        ProcDao procDao = new ProcDao();
	        return procDao.buscarProcessoPorNumero(selectedProcNumber);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null; // Lide com a exceção adequadamente na sua aplicação
	    }
	}

    @FXML
    private void inserirMovimentoVara() {
        String descricao = txtNovoAndamento3.getText();
        String data = txtDataNovoAndamento3.getText();

        if (!descricao.isEmpty() && !data.isEmpty()) {
            Historico historico = new Historico();
            historico.setDescricao(descricao);
            historico.setData(data);

            // Obtenha o processo selecionado na tab do BuscaVara
            Proc selectedProcVara = getSelectedProcVara();

            if (selectedProcVara != null) {
                try {
                    // Salve o histórico no banco de dados usando o HistoricoDao
                    HistoricoDao historicoDao = new HistoricoDao();
                    historicoDao.saveHistorico(historico, selectedProcVara.getIdproc()); // Passe o ID do processo

                    // Associe o histórico ao processo
                    selectedProcVara.adicionarHistorico(historico);

                    // Limpe os campos após a inserção
                    txtNovoAndamento3.clear();
                    txtDataNovoAndamento3.clear();

                    // Atualize a exibição do histórico
                    atualizarHistoricoVara(selectedProcVara);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Lide com erros de SQL, se necessário
                }
            }
        } else {
            // Lide com o caso em que os campos estão vazios
        }
    }

    private void atualizarHistoricoVara(Proc selectedProcVara) {
        if (selectedProcVara != null) {
            try {
                HistoricoDao historicoDao = new HistoricoDao();
                List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(selectedProcVara.getIdproc());

                // Limpe a TextArea
                taAndamentos3.clear();

                // Exiba os movimentos na TextArea
                for (String movimento : movimentos) {
                    taAndamentos3.appendText(movimento + "\n");
                }
                taAndamentos3.setWrapText(true);  // Certifique-se de que o WrapText está configurado corretamente
            } catch (SQLException e) {
                e.printStackTrace();
                // Lide com erros de SQL, se necessário
            }
        }
    }
	
	
	

	// BUSCA POR TEMA
	@FXML
	private ComboBox<String> comboBuscaTema;
	@FXML
	private ListView<String> listNomesTema;
	@FXML
	private ListView<String> listProcNomesTema;
	@FXML
	private TextField txtNomeSelecionado4;
	@FXML
	private TextField txtProcSelecionado4;
	@FXML
	private Label labelComarca4;
	@FXML
	private Label labelVara4;
	@FXML
	private Label labelTema4;
	@FXML
	private TextArea taAndamentos4;
	@FXML
	private TextField txtDataNovoAndamento4;
	@FXML
	private TextField txtNovoAndamento4;
	@FXML
	private Button btnNovoAndamento4;
	@FXML
	private TextField txtStatusTema;
	
	private void preencherComboBoxTemas() {
	    try {
	        ProcDao procDao = new ProcDao();
	        List<String> temas = procDao.obterTemas();
	        Collections.sort(temas);  // Ordena a lista em ordem alfabética

	        // Preenche a ComboBox de temas
	        ObservableList<String> items = FXCollections.observableArrayList(temas);
	        comboBuscaTema.setItems(items);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Trate a exceção conforme necessário
	    }
	}
	private void onTemaSelecionado() {
	    String selectedTema = comboBuscaTema.getValue();
	    if (selectedTema != null) {
	        try {
	            // Obtenha os nomecliente correspondentes ao tema selecionado
	            List<String> nomeClientes = procDao.obterNomesPorTema(selectedTema);

	            // Remova duplicatas e ordene em ordem alfabética
	            nomeClientes = nomeClientes.stream().distinct().sorted().collect(Collectors.toList());

	            // Preencha a ListView de nomecliente
	            listNomesTema.setItems(FXCollections.observableArrayList(nomeClientes));
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	        }
	    }
	}

	
	private void onNomeClienteSelecionadoTema() {
	    String selectedNomeCliente = listNomesTema.getSelectionModel().getSelectedItem();
	    String selectedTema = comboBuscaTema.getValue();

	    if (selectedNomeCliente != null && selectedTema != null) {
	        try {
	            // Obtenha os numeroprocesso correspondentes ao nomecliente e tema selecionados
	            List<String> numerosProcesso = procDao.obterNumerosProcessoPorNomeETema(selectedNomeCliente, selectedTema);

	            // Preencha a ListView de numeroprocesso
	            listProcNomesTema.setItems(FXCollections.observableArrayList(numerosProcesso));
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	        }
	    }
	}
	@FXML
	private void onSelecionarProcNumeroTema() {
	    String selectedProcNumero = listProcNomesTema.getSelectionModel().getSelectedItem();

	    if (selectedProcNumero != null) {
	        try {
	            ProcDao procDao = new ProcDao();
	            Proc proc = procDao.buscarProcessoPorNumero(selectedProcNumero);

	            if (proc != null) {
	                // Preencha as TextFields e Labels com os detalhes do processo
	                txtNomeSelecionado4.setText(proc.getnomecliente());
	                txtProcSelecionado4.setText(proc.getnumeroprocesso());
	                labelComarca4.setText(proc.getcomarca());
	                labelVara4.setText(proc.getvara());
	                labelTema4.setText(proc.gettema());
	                txtStatusTema.setText(proc.getstatus());

	                // Obtém o ID do processo
	                int idProcesso = proc.getIdproc();

	                // Atualiza a exibição dos andamentos
	                exibirAndamentosTema(idProcesso);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        // Lógica para lidar com nenhum item selecionado, se necessário
	    }
	}

	private void exibirAndamentosTema(int idProcesso) {
	    try {
	        HistoricoDao historicoDao = new HistoricoDao();
	        List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(idProcesso);

	        // Limpe a TextArea
	        taAndamentos4.clear();

	        // Exiba os movimentos na TextArea
	        for (String movimento : movimentos) {
	            taAndamentos4.appendText(movimento + "\n");
	        }
	        taAndamentos4.setWrapText(true);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private Proc getSelectedProcTema() {
	    String selectedProcNumber = listProcNomesTema.getSelectionModel().getSelectedItem();
	    try {
	        ProcDao procDao = new ProcDao();
	        return procDao.buscarProcessoPorNumero(selectedProcNumber);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null; // Lide com a exceção adequadamente na sua aplicação
	    }
	}
	@FXML
    private void inserirMovimentoTema() {
        String descricao = txtNovoAndamento4.getText();
        String data = txtDataNovoAndamento4.getText();

        if (!descricao.isEmpty() && !data.isEmpty()) {
            Historico historico = new Historico();
            historico.setDescricao(descricao);
            historico.setData(data);

            // Obtenha o processo selecionado na tab do BuscaVara
            Proc selectedProcTema = getSelectedProcTema();

            if (selectedProcTema != null) {
                try {
                    // Salve o histórico no banco de dados usando o HistoricoDao
                    HistoricoDao historicoDao = new HistoricoDao();
                    historicoDao.saveHistorico(historico, selectedProcTema.getIdproc()); // Passe o ID do processo

                    // Associe o histórico ao processo
                    selectedProcTema.adicionarHistorico(historico);

                    // Limpe os campos após a inserção
                    txtNovoAndamento4.clear();
                    txtDataNovoAndamento4.clear();

                    // Atualize a exibição do histórico
                    atualizarHistoricoTema(selectedProcTema);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Lide com erros de SQL, se necessário
                }
            }
        } else {
            // Lide com o caso em que os campos estão vazios
        }
    }

    private void atualizarHistoricoTema(Proc selectedProcTema) {
        if (selectedProcTema != null) {
            try {
                HistoricoDao historicoDao = new HistoricoDao();
                List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(selectedProcTema.getIdproc());

                // Limpe a TextArea
                taAndamentos4.clear();

                // Exiba os movimentos na TextArea
                for (String movimento : movimentos) {
                    taAndamentos4.appendText(movimento + "\n");
                }
                taAndamentos4.setWrapText(true);  // Certifique-se de que o WrapText está configurado corretamente
            } catch (SQLException e) {
                e.printStackTrace();
                // Lide com erros de SQL, se necessário
            }
        }
    }
    
    
	// BUSCA POR STATUS
	@FXML
	private ComboBox<String> comboBuscaStatusTema;
	@FXML
	private ComboBox<String> comboBuscaStatus;
	@FXML
	private ListView<String> listNomesStatus;
	@FXML
	private TextField txtNomeSelecionado41;
	@FXML
	private TextField txtProcSelecionado41;
	@FXML
	private Label labelComarca41;
	@FXML
	private Label labelVara41;
	@FXML
	private Label labelTema41;
	@FXML
	private TextArea taAndamentos41;
	@FXML
	private TextField txtDataNovoAndamento41;
	@FXML
	private TextField txtNovoAndamento41;
	@FXML
	private Button btnNovoAndamento41;
	@FXML
	private TextField txtStatusTema1;
	
	private void preencherComboBoxTemasStatus() {
	    try {
	        ProcDao procDao = new ProcDao();
	        List<String> temas = procDao.obterTemas();
	        Collections.sort(temas);  // Ordena a lista em ordem alfabética

	        // Preenche a ComboBox de temas
	        ObservableList<String> items = FXCollections.observableArrayList(temas);
	        comboBuscaStatusTema.setItems(items);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Trate a exceção conforme necessário
	    }
	}
	
	private void onTemaStatusSelecionado() {
	    String selectedTema = comboBuscaStatusTema.getValue();
	    if (selectedTema != null) {
	        try {
	            // Obtenha a lista de status para o tema selecionado
	            List<String> statusList = procDao.obterStatusPorTema(selectedTema);

	            // Preencha a ComboBox de status
	            ObservableList<String> statusItems = FXCollections.observableArrayList(statusList);
	            comboBuscaStatus.setItems(statusItems);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	        }
	    }
	}
	private void onStatusSelecionado() {
	    String selectedTema = comboBuscaStatusTema.getValue();
	    String selectedStatus = comboBuscaStatus.getValue();
	    if (selectedTema != null && selectedStatus != null) {
	        try {
	            // Obtenha a lista de nomecliente + " - " + numeroprocesso para o tema e status selecionados
	            List<String> nomesStatusList = procDao.obterNomesPorTemaEStatus(selectedTema, selectedStatus);

	            // Preencha a ListView de nomesStatus
	            ObservableList<String> nomesStatusItems = FXCollections.observableArrayList(nomesStatusList);
	            listNomesStatus.setItems(nomesStatusItems);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Trate a exceção conforme necessário
	        }
	    }
	}
	
	@FXML
    private void inserirMovimentoStatus() {
        String descricao = txtNovoAndamento41.getText();
        String data = txtDataNovoAndamento41.getText();

        if (!descricao.isEmpty() && !data.isEmpty()) {
            Historico historico = new Historico();
            historico.setDescricao(descricao);
            historico.setData(data);

            // Obtenha o processo selecionado na tab do BuscaVara
            Proc selectedProcTema = getSelectedProcTema();

            if (selectedProcTema != null) {
                try {
                    // Salve o histórico no banco de dados usando o HistoricoDao
                    HistoricoDao historicoDao = new HistoricoDao();
                    historicoDao.saveHistorico(historico, selectedProcTema.getIdproc()); // Passe o ID do processo

                    // Associe o histórico ao processo
                    selectedProcTema.adicionarHistorico(historico);

                    // Limpe os campos após a inserção
                    txtNovoAndamento41.clear();
                    txtDataNovoAndamento41.clear();

                    // Atualize a exibição do histórico
                    atualizarHistoricoStatus(selectedProcTema);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Lide com erros de SQL, se necessário
                }
            }
        } else {
            // Lide com o caso em que os campos estão vazios
        }
    }

    private void atualizarHistoricoStatus(Proc selectedProcTema) {
        if (selectedProcTema != null) {
            try {
                HistoricoDao historicoDao = new HistoricoDao();
                List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(selectedProcTema.getIdproc());

                // Limpe a TextArea
                taAndamentos41.clear();

                // Exiba os movimentos na TextArea
                for (String movimento : movimentos) {
                    taAndamentos41.appendText(movimento + "\n");
                }
                taAndamentos41.setWrapText(true);  // Certifique-se de que o WrapText está configurado corretamente
            } catch (SQLException e) {
                e.printStackTrace();
                // Lide com erros de SQL, se necessário
            }
        }
    }
	
	
	

}
