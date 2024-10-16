package guiWAdv;

import java.io.FileInputStream;
import java.util.HashSet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import infoCalculos.Calculos;
import infoCalculos.CalculosDao;
import infoCalculos.professorescola;
import infoCalculos.professorescolaDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Tela04GeradorDeDocumentoController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(com.WernerADV.Software.App.class.getName());
	private UserDao userDao = new UserDao();
	private ProcDao procDao = new ProcDao();
	@SuppressWarnings("unused")
	private RecDao recDao = new RecDao();

	@SuppressWarnings("unused")
	private void alert(String title, String message, AlertType alertType) {
		Platform.runLater(() -> {
			Alert alert = new Alert(alertType);
			alert.setTitle("Werner Advogados");
			alert.setHeaderText(title);
			alert.setContentText(message);
			alert.showAndWait();
		});
	}

	// NOMES CONTROLES

	// TABS e TABPANE
	@FXML
	private TabPane tabPaneGeradorDoc;

	public void setTabPane(TabPane tabPaneGeradorDoc) {
		this.tabPaneGeradorDoc = tabPaneGeradorDoc;
	}

	@FXML
	private Tab tabDocAdm;
	@FXML
	private Tab tabPetIniciais;
	@FXML
	private Tab tabPetIntercorrentes;

	// TAB DOCUMENTOS ADMINISTRATIVOS

	@FXML
	private TextField txtNomeDocAdm;
	@FXML
	private TextField txtNacionalidadeDocAdm;
	@FXML
	private TextField txtProfissaoDocAdm;
	@FXML
	private TextField txtEstadoCivilDocAdm;
	@FXML
	private TextField txtRGDocAdm;
	@FXML
	private TextField txtCPFDocAdm;
	@FXML
	private TextField txtIdFDocAdm;
	@FXML
	private TextField txtEnderecoDocAdm;
	@FXML
	private TextField txtCEPDocAdm;

	// TAB PETIÇÕES INICIAIS
	
	@FXML
	private ListView<String> listClientesPetInicial = new ListView<>();

	@FXML
	private TextField txtNomePetInicial;
	@FXML
	private TextField txtNacionalidadePetInicial;
	@FXML
	private TextField txtEstadoCivilPetInicial;
	@FXML
	private TextField txtProfissaoDPetInicial;
	@FXML
	private TextField txtRGPetInicial;
	@FXML
	private TextField txtCPFPetInicial;
	@FXML
	private TextField txtIdFPetInicial;
	@FXML
	private TextField txtEnderecoPetInicial;
	@FXML
	private TextField txtCEPPetInicial;
	@FXML
	private TextField txtMatPetInicial;
	@FXML
	private TextField txtRefPetInicial;
	@FXML
	private TextField txtCargoPetInicial;
	@FXML
	private TextField txtCargaHPetInicial;
	@FXML
	private TextField txtNivelPetInicial;
	@FXML
	private TextField txtDataInicioPetInicial;
	@FXML
	private TextField txtDataaposentadoriaPetInicial;
	@FXML
	private TextField txtDataNascimentoPetInicial;
	@FXML
	private TextField txtTrienioPetInicial;

	@FXML
	private Button btnTABPANECalculosInicial;

	// IR PARA CALCULOS
	@FXML
	private Tela05CalculosController calculosController;

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
			@SuppressWarnings("unused")
			Tela05CalculosController calculosController = loader.getController();

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// SUBTAB - NE
	@FXML
	private Button btnPuxaDataNE;
	@FXML
	private Button btnPuxaDataCalcNE;
	@FXML
	private TextField txtEscolaNE;
	@FXML
	private TextField txtNotaNE;
	@FXML
	private TextField txtValorBrutoNE;
	@FXML
	private TextField txtRioPrevNE;
	@FXML
	private TextField txtValorFinalNE;
	@FXML
	private ChoiceBox<String> choiceSalarioMinimo;

	@FXML
	private ChoiceBox<String> choiceTipoVaraNE;

	@FXML
	private Button btnGeraInicialNE;
	@FXML
	private Label labelTemaNE;

	@FXML
	private void buscarValoresPorTemaNomeMatriculaNE(ActionEvent event) {
		String temaSelecionado = labelTemaNE.getText();
		String nome = txtNomePetInicial.getText();
		String matricula = txtMatPetInicial.getText();

		if (temaSelecionado != null && !nome.isEmpty() && !matricula.isEmpty()) {
			try {
				CalculosDao calculosDao = new CalculosDao();
				Calculos calculos = calculosDao.buscarCalculosPorTemaNomeMatricula(temaSelecionado, nome, matricula);

				if (calculos != null) {
					txtValorBrutoNE.setText(calculos.getValorPrincipal());
					txtRioPrevNE.setText(calculos.getRioPrev());
					txtValorFinalNE.setText(calculos.getValorFinal());
				} else {
					// Trate o caso em que não foram encontrados valores
					exibirAlerta("Aviso", "Nenhum cálculo encontrado com os dados fornecidos.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// Trate os erros de banco de dados, se necessário
				exibirAlerta("Erro", "Ocorreu um erro ao buscar os valores: " + e.getMessage());
			}
		} else {
			// Trate o caso em que os campos não estão preenchidos corretamente
			exibirAlerta("Aviso", "Preencha todos os campos corretamente.");
		}
	}

	@FXML
	private void buscarEscolaPorNomeMatriculaNE(ActionEvent event) {
		String nome = txtNomePetInicial.getText();
		String matricula = txtMatPetInicial.getText();

		if (nome != null && !matricula.isEmpty()) {
			try {
				professorescolaDao professorescolaDao = new professorescolaDao();
				professorescola professorescola = professorescolaDao.buscarEscolaPorNomeMatricula(nome, matricula);

				if (professorescola != null) {
					txtEscolaNE.setText(professorescola.getNomeEscolaNE());
					txtNotaNE.setText(professorescola.getNotaEscolaNE());

				} else {
					// Trate o caso em que não foram encontrados valores
					exibirAlerta("Aviso", "Nenhuma escola encontrada com os dados fornecidos.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// Trate os erros de banco de dados, se necessário
				exibirAlerta("Erro", "Ocorreu um erro ao buscar a escola: " + e.getMessage());
			}
		} else {
			// Trate o caso em que os campos não estão preenchidos corretamente
			exibirAlerta("Aviso", "Preencha todos os campos corretamente.");
		}
	}

	// TAB PETIÇÕES INTERCORRENTES
	
	@FXML
	private ListView<String> listClientesPetInter;
	@FXML
	private TextField txtNomePetInter;
	@FXML
	private TextField txtMatPetInter;
	@FXML
	private TextField txtRefPetInter;
	@FXML
	private TextField txtCargoPetInter;
	@FXML
	private TextField txtCargaHPetInter;
	@FXML
	private TextField txtNivelPetInter;
	@FXML
	private TextField txtNumeroProcInter;
	@FXML
	private TextField txtTemaProcesso;
	@FXML
	private TextField txtVaraProcesso;
	@FXML
	private TextField txtComarcaProcesso;
	@FXML
	private Label labelDataInicio;

	// INITIALIZE
	public void initializeComboBox() {
		@SuppressWarnings("unused")
		UserDao userDao = new UserDao();
	}

	@FXML
	public void initialize() {

		// COMBOBOX - TAB Documentos administrativos
		try {

			// Obtém os nomes de clientes do UserDao
			List<String> nomesClientesUser = userDao.obterNomesClientes();

			// Obtém os nomes de clientes do ProcDao
			List<String> nomesClientesProc = procDao.obterNomesClientes();

			// Combine os nomes de clientes de ambas as fontes em uma única lista
			List<String> todosNomesClientes = new ArrayList<>(nomesClientesUser);
			todosNomesClientes.addAll(nomesClientesProc);

			// Remova duplicatas, se necessário
			todosNomesClientes = new ArrayList<>(new HashSet<>(todosNomesClientes));

			// Ordene os nomes dos clientes
			Collections.sort(todosNomesClientes);

			

		} catch (SQLException e) {
			e.printStackTrace();
			// Lide com exceções SQL, se necessário
		}

		
		// COMBOBOX - TAB Petição Inicial
		try {
			List<String> nomesClientesUser = userDao.obterNomesClientes();

			// Obtém os nomes de clientes do ProcDao
			List<String> nomesClientesProc = procDao.obterNomesClientes();

			// Combine os nomes de clientes de ambas as fontes em uma única lista
			List<String> todosNomesClientes = new ArrayList<>(nomesClientesUser);
			todosNomesClientes.addAll(nomesClientesProc);

			// Remova duplicatas, se necessário
			todosNomesClientes = new ArrayList<>(new HashSet<>(todosNomesClientes));

			// Ordene os nomes dos clientes
			Collections.sort(todosNomesClientes);

			
		} catch (SQLException e) {
			e.printStackTrace();

		}

		
		

		// COMBOBOX - TAB Petição Intercorrente
		try {
			// Obtém os nomes de clientes do UserDao
			List<String> nomesClientesUser = userDao.obterNomesClientes();

			// Obtém os nomes de clientes do ProcDao
			List<String> nomesClientesProc = procDao.obterNomesClientes();

			// Combine os nomes de clientes de ambas as fontes em uma única lista
			List<String> todosNomesClientes = new ArrayList<>(nomesClientesUser);
			todosNomesClientes.addAll(nomesClientesProc);

			// Remova duplicatas, se necessário
			todosNomesClientes = new ArrayList<>(new HashSet<>(todosNomesClientes));

			// Ordene os nomes dos clientes
			Collections.sort(todosNomesClientes);

			
		} catch (SQLException e) {
			e.printStackTrace();
			// Lide com exceções SQL, se necessário
		}

		

		setupListViewClickHandler();

		listClientesPetInter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        onSelecionarClientePetInter(newValue);
	       
	    });
		
		btnBuscaNomeTabIntercorrente.setOnAction(event -> {
            buscarPorNomeTabIntercorrente();
        });

		
    

		// INITIALIZE PARA GERAÇÃO DE DOCUMENTOS
		// TAB DOCUMENTOS ADMINISTRATIVOS

		List<String> nomesDocumentos = Arrays.asList("ProcuracaoInterniveis", "ProcuracaoNE", "ProcuracaoPiso",
				"ProcuracaoNEPiso", "ProcuracaoInterNEPiso", "ProcuracaoInterNE", "ProcuracaoInterPiso",
				 "ContratoIntNEPiso", "ContratoInt", 
				"ContratoPiso", "ContratoIntPiso", "ContratoIntNE", "ContratoNE", "ContratoNEPiso",
				 "DeclaracaoHipo");
		choiceBoxDocumento.getItems().addAll(nomesDocumentos);

		// TAB PETIÇÕES INICIAIS
		// NE
		List<String> salarioMinimo = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"

		);
		choiceSalarioMinimo.getItems().addAll(salarioMinimo);
		choiceSalarioMinimoInterniveis.getItems().addAll(salarioMinimo);
		choiceBoxSalarioMinimoPiso.getItems().addAll(salarioMinimo);

		List<String> tipoVara = Arrays.asList("Vara Cível", "Juizado Especial Fazendário", "Vara de Fazenda Pública"

		);
		choiceTipoVaraNE.getItems().addAll(tipoVara);
		choiceBoxVaraPiso.getItems().addAll(tipoVara);

		List<String> ativaInativa = Arrays.asList("Inativa", "Ativa", "Inativo", "Ativo"

		);
		choiceBoxAtivaInativa.getItems().addAll(ativaInativa);

		// TAB PETIÇÕES INTERCORRENTES
		// INTERNIVEIS
		List<String> nomesPetInter = Arrays.asList("Peticao_RPV_interniveis", "Peticao_RPV_interniveis_com_renuncia",
				"Resposta_Impugnacao_calculo_ERJ_Interniveis", "Peticao_Levantamento", "Peticao_concordar_calculoscontador_interniveis -", 
				"Peticao_discordar_calculoscontador_interniveis -", "Peticao_interniveis", "Peticao_Levantamento_2", "Peticao_Levantamento_suc",
				"Peticao_RPV_interniveis_Valores", "EDs_Honorarios_interniveis", "EDs_Honorarios_RPV_40_SM_interniveis", "Peticao_JGSUC_interniveis", "Peticao_JG_interniveis",
				"Peticao_GRERJ_reembolso_interniveis", "Peticao_JG_e_RPV_e_reembolso_interniveis", "Peticao_JG_e_RPV_interniveis",
				"Peticao_RPV_e_reembolso_interniveis"

		);
		choiceBoxPetInter.getItems().addAll(nomesPetInter);

		// NE
		List<String> nomesPetInterNE = Arrays.asList("PeticaoRespostaImpugnacaoERJNE", "EDsAvaliacaoAplicada",
				"PeticaoconcordandocalculoscontadorNE", "PeticaodescabimentoSuspensaoNE", "PeticaoProvasNE",
				"PeticaoSobreParametrosNE", "EDs_Sobre_Suspensao_NE", "Agravo_Parametro_NE", "CR_Agravo_NE",
				"CR_AInterno_Agravo_NE", "CR_EDs_Agravo_NE", "CR_RESP_NE", "Peticao_inf_Agravo_parametros_NE",
				"Peticao_levantamento_NE", "Peticao_valores_RPV_NE"

		);
		choiceBoxPetInterNE.getItems().addAll(nomesPetInterNE);

		// PISO
		List<String> nomesPetPiso = Arrays.asList("Replica_Piso", "Provas_Piso", "Alegacoes_Finais_Piso",
				"CR_Apelacao_Piso", "EDs_Sobre_Suspensao_Piso", "Agravo_suspensao_Piso", "Peticao_inf_Agravo_suspensao_Piso", "Apelacao_Piso"

		);
		choiceBoxPetPiso.getItems().addAll(nomesPetPiso);

		// CHOICEBOX CALCULOS
		// INTERNIVEIS
		List<String> valoresPorTema = Arrays.asList("Nova Escola", "Interniveis", "Piso"

		);
		choiceBoxValorPorTema.getItems().addAll(valoresPorTema);

		// CHOICEBOPETIÇÕES EM RECURSO
		List<String> nomesPetRecNE = Arrays.asList("XX", "XXX", "XXXX", "XXX");
		cbRecNE.getItems().addAll(nomesPetRecNE);

		List<String> nomesPetRecInter = Arrays.asList("XX", "XXX", "XXXX", "XXX");
		cbRecInter.getItems().addAll(nomesPetRecInter);

		List<String> nomesPetRecPiso = Arrays.asList("XX", "XXX", "XXXX", "XXX");
		cbRecPiso.getItems().addAll(nomesPetRecPiso);

		listClienteRec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				onSelecionarClienteRec();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		listRecursoRec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				onSelecionarRec();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		btnBuscaClientesDocAdm.setOnAction(event -> buscarporNome());

	    // Configurar a ação quando um cliente é selecionado
		listClientesDocAdm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onSelecionarClienteDocAdm();
        });
    
		btnBuscaNomeClientePetiInicial.setOnAction(event -> {
		    buscarPorNomePetiInicial();
		});
		
		listClientesPetInicial.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    onSelecionarClientePetInicial(); 
		   
		});

		// Ouvinte para a ListView de matrículas
		listMatriculasPetInicial.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    onSelecionarMatriculaPetInicial();
		  
		});
		
		
		// FECHA INITIALIZE
	}

	@FXML
	private TextField txtClientesDocAdmBusca;
	@FXML
	private Button btnBuscaClientesDocAdm;
	@FXML
	private ListView<String> listClientesDocAdm1;
	
	// TAB DOCUMENTOS ADMINISTRATIVOS
	// METODO PARA INFORMAÇÕES APARECEREM NAS TEXTFIELDS
	@FXML
	private void buscarporNome() {
	    String textoBuscaCliente = txtClientesDocAdmBusca.getText().trim();

	    try {
	        List<String> nomesClientes;

	        if (textoBuscaCliente.isEmpty()) {
	            // Buscar todos os nomes
	            UserDao userDao = new UserDao();
	            ProcDao procDao = new ProcDao();

	            List<String> nomesUser = userDao.buscarTodosNomes();
	            List<String> nomesProc = procDao.buscarTodosNomes();

	            // Adicionar nomes à lista
	            nomesClientes = new ArrayList<>(nomesUser);
	            nomesClientes.addAll(nomesProc);
	        } else {
	            // Buscar nomes que correspondem ao texto
	            UserDao userDao = new UserDao();
	            ProcDao procDao = new ProcDao();

	            // Usar os métodos existentes
	            User clienteUser = userDao.buscaClientePeloNomeTabEditar(textoBuscaCliente);
	            List<String> nomesProc = procDao.buscarNomesClientesPorTexto(textoBuscaCliente);

	            // Adicionar nomes à lista
	            nomesClientes = new ArrayList<>();
	            if (clienteUser != null) {
	                nomesClientes.add(clienteUser.getnome());
	            }
	            nomesClientes.addAll(nomesProc);
	        }

	        // Remover duplicatas e ordenar alfabeticamente
	        Set<String> nomesUnicos = new TreeSet<>(nomesClientes);
	        listClientesDocAdm.getItems().setAll(new ArrayList<>(nomesUnicos));
	        // Definir os resultados na ListView
	    } catch (SQLException e) {
	        // Lidar com exceções de SQL aqui, se necessário
	        e.printStackTrace();
	    }
	}


	@FXML
	private void onSelecionarClienteDocAdm() {
	    try {
	        // Obtenha o cliente selecionado
	        String DadosClienteSelecionado = listClientesDocAdm.getSelectionModel().getSelectedItem();
	        if (DadosClienteSelecionado != null) {
	            User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(DadosClienteSelecionado);

	            if (clienteSelecionado != null) {
	                // Se o cliente for encontrado, preencha as TextFields
	                txtNomeDocAdm.setText(clienteSelecionado.getnome());
	                txtNacionalidadeDocAdm.setText(clienteSelecionado.getnacionalidade());
	                txtEstadoCivilDocAdm.setText(clienteSelecionado.getestadocivil());
	                txtProfissaoDocAdm.setText(clienteSelecionado.getprofissao());
	                txtRGDocAdm.setText(clienteSelecionado.getrg());
	                txtCPFDocAdm.setText(clienteSelecionado.getcpf());
	                txtIdFDocAdm.setText(clienteSelecionado.getidfuncional());
	                txtEnderecoDocAdm.setText(clienteSelecionado.getendereco() + " " + clienteSelecionado.getcidade() + " "
	                        + clienteSelecionado.getestado());
	                txtCEPDocAdm.setText(clienteSelecionado.getcep());

	                // Adicione mat1 e mat2 à listClientesDocAdm1
	                ObservableList<String> items = FXCollections.observableArrayList();
	                if (clienteSelecionado.getmat1() != null) {
	                    items.add(clienteSelecionado.getmat1());
	                }
	                if (clienteSelecionado.getmat2() != null) {
	                    items.add(clienteSelecionado.getmat2());
	                }
	                listClientesDocAdm1.setItems(items);
	            }
	        } else {
	            // Limpe as TextFields se nenhum cliente for selecionado
	            txtNomeDocAdm.clear();
	            txtNacionalidadeDocAdm.clear();
	            txtEstadoCivilDocAdm.clear();
	            txtProfissaoDocAdm.clear();
	            txtRGDocAdm.clear();
	            txtCPFDocAdm.clear();
	            txtIdFDocAdm.clear();
	            txtEnderecoDocAdm.clear();
	            txtCEPDocAdm.clear();
	            listClientesDocAdm1.getItems().clear();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private Button btnLimparCamposDocAdm;
	@FXML
	private void limparCamposDocAdm() {
	    txtNomeDocAdm.clear();
	    txtNacionalidadeDocAdm.clear();
	    txtEstadoCivilDocAdm.clear();
	    txtProfissaoDocAdm.clear();
	    txtRGDocAdm.clear();
	    txtCPFDocAdm.clear();
	    txtIdFDocAdm.clear();
	    txtEnderecoDocAdm.clear();
	    txtCEPDocAdm.clear();
	    listClientesDocAdm1.getItems().clear();
	}
	@FXML
	private ListView<String> listClientesDocAdm = new ListView<>();

	
	// METODOS TAB PETIÇÕES INICIAIS
	
	@FXML
	private TextField txtBuscaNomeClientePetiInicial;
	@FXML
	private Button btnBuscaNomeClientePetiInicial;
	@FXML
	private ListView<String> listMatriculasPetInicial;
	
	
	@FXML
	private void buscarPorNomePetiInicial() {
	    String textoBuscaCliente = txtBuscaNomeClientePetiInicial.getText().trim();

	    try {
	        List<String> nomesClientes;

	        if (textoBuscaCliente.isEmpty()) {
	            // Buscar todos os nomes
	            UserDao userDao = new UserDao();
	            ProcDao procDao = new ProcDao();

	            List<String> nomesUser = userDao.buscarTodosNomes();
	            List<String> nomesProc = procDao.buscarTodosNomes();

	            // Adicionar nomes à lista
	            nomesClientes = new ArrayList<>(nomesUser);
	            nomesClientes.addAll(nomesProc);
	        } else {
	            // Buscar nomes que correspondem ao texto
	            UserDao userDao = new UserDao();
	            ProcDao procDao = new ProcDao();

	            // Usar os métodos existentes
	            User clienteUser = userDao.buscaClientePeloNomeTabEditar(textoBuscaCliente);
	            List<String> nomesProc = procDao.buscarNomesClientesPorTexto(textoBuscaCliente);

	            // Adicionar nomes à lista
	            nomesClientes = new ArrayList<>();
	            if (clienteUser != null) {
	                nomesClientes.add(clienteUser.getnome());
	            }
	            nomesClientes.addAll(nomesProc);
	        }

	        // Remover duplicatas e ordenar alfabeticamente
	        Set<String> nomesUnicos = new TreeSet<>(nomesClientes);
	        listClientesPetInicial.getItems().setAll(new ArrayList<>(nomesUnicos));
	        // Definir os resultados na ListView
	    } catch (SQLException e) {
	        // Lidar com exceções de SQL aqui, se necessário
	        e.printStackTrace();
	    }
	}

	@FXML
	private void onSelecionarClientePetInicial() {
	    try {
	        // Obtenha o cliente selecionado
	        String DadosClienteSelecionado = listClientesPetInicial.getSelectionModel().getSelectedItem();
	        if (DadosClienteSelecionado != null) {
	            User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(DadosClienteSelecionado);

	            if (clienteSelecionado != null) {
	         
	                // Se o cliente for encontrado, preencha as TextFields
	            	txtNomePetInicial.setText(clienteSelecionado.getnome());
					txtNacionalidadePetInicial.setText(clienteSelecionado.getnacionalidade());
					txtEstadoCivilPetInicial.setText(clienteSelecionado.getestadocivil());
					txtProfissaoDPetInicial.setText(clienteSelecionado.getprofissao());
					txtRGPetInicial.setText(clienteSelecionado.getrg());
					txtCPFPetInicial.setText(clienteSelecionado.getcpf());
					txtIdFPetInicial.setText(clienteSelecionado.getidfuncional());
					txtEnderecoPetInicial.setText(clienteSelecionado.getendereco() + " "
							+ clienteSelecionado.getcidade() + " " + clienteSelecionado.getestado());
					txtCEPPetInicial.setText(clienteSelecionado.getcep());
					txtDataNascimentoPetInicial.setText(clienteSelecionado.getdatanascimento());
					labelCidade.setText(clienteSelecionado.getcidade());

	                // Adicione mat1 e mat2 à listMatriculasPetInicial
	                ObservableList<String> items = FXCollections.observableArrayList();
	                if (clienteSelecionado.getmat1() != null) {
	                    items.add(clienteSelecionado.getmat1());
	                }
	                if (clienteSelecionado.getmat2() != null) {
	                    items.add(clienteSelecionado.getmat2());
	                }
	                listMatriculasPetInicial.setItems(items);
	            }
	        } else {
	            // Limpe as TextFields se nenhum cliente for selecionado
	        	txtNomePetInicial.clear();
				txtNacionalidadePetInicial.clear();
				txtEstadoCivilPetInicial.clear();
				txtProfissaoDPetInicial.clear();
				txtRGPetInicial.clear();
				txtCPFPetInicial.clear();
				txtIdFPetInicial.clear();
				txtEnderecoPetInicial.clear();
				txtCEPPetInicial.clear();
				txtDataNascimentoPetInicial.clear();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	@FXML
	private void onSelecionarMatriculaPetInicial() {
	    try {
	        // Obtenha a matrícula selecionada
	        String matriculaSelecionada = listMatriculasPetInicial.getSelectionModel().getSelectedItem();
	        if (matriculaSelecionada != null) {
	            User matSelecionada = userDao.obterUsuarioPelaMatricula(matriculaSelecionada);

	            if (matSelecionada != null) {
	                // Verifique se a matrícula selecionada é mat1 ou mat2
	                if (matriculaSelecionada.equals(matSelecionada.getmat1())) {
	                	
	                    // Preencha as TextFields com os detalhes de mat1
	                    txtMatPetInicial.setText(matSelecionada.getmat1());
	                    txtRefPetInicial.setText(matSelecionada.getref1());
	                    txtCargaHPetInicial.setText(matSelecionada.getcargh1());
	                    txtDataInicioPetInicial.setText(matSelecionada.getdatainicio1());
	                    txtCargoPetInicial.setText(matSelecionada.getcargo1());
	                    txtNivelPetInicial.setText(matSelecionada.getnivel1());
	                    txtTrienioPetInicial.setText(matSelecionada.gettrienio1());
	                    txtDataaposentadoriaPetInicial.setText(matSelecionada.getdataaposentadoria1());
	                } else if (matriculaSelecionada.equals(matSelecionada.getmat2())) {
	                	
	                    // Preencha as TextFields com os detalhes de mat2
	                    txtMatPetInicial.setText(matSelecionada.getmat2());
	                    txtRefPetInicial.setText(matSelecionada.getref2());
	                    txtCargaHPetInicial.setText(matSelecionada.getcargh2());
	                    txtDataInicioPetInicial.setText(matSelecionada.getdatainicio2());
	                    txtCargoPetInicial.setText(matSelecionada.getcargo2());
	                    txtNivelPetInicial.setText(matSelecionada.getnivel2());
	                    txtTrienioPetInicial.setText(matSelecionada.gettrienio2());
	                    txtDataaposentadoriaPetInicial.setText(matSelecionada.getdataaposentadoria2());
	                }
	            }
	        } else {
	            // Limpe as TextFields se nenhuma matrícula for selecionada
	            txtMatPetInicial.clear();
	            txtRefPetInicial.clear();
	            txtCargaHPetInicial.clear();
	            txtDataInicioPetInicial.clear();
	            txtCargoPetInicial.clear();
	            txtNivelPetInicial.clear();
	            txtTrienioPetInicial.clear();
	            txtDataaposentadoriaPetInicial.clear();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



	// TAB PETIÇÃO INTERCORRENTE

	@FXML
	private TextField txtBuscaNomeTabIntercorrente;
	@FXML
	private Button btnBuscaNomeTabIntercorrente;
	@FXML
	private ListView<String> listProcessoseMat;
	
	@FXML
    private void buscarPorNomeTabIntercorrente() {
        String textoBuscaCliente = txtBuscaNomeTabIntercorrente.getText().trim();

        try {
            List<String> nomesClientes;

            if (textoBuscaCliente.isEmpty()) {
                // Buscar todos os nomes
                UserDao userDao = new UserDao();
                ProcDao procDao = new ProcDao();

                List<String> nomesUser = userDao.buscarTodosNomes();
                List<String> nomesProc = procDao.buscarTodosNomes();

                // Adicionar nomes à lista
                nomesClientes = new ArrayList<>(nomesUser);
                nomesClientes.addAll(nomesProc);
            } else {
                // Buscar nomes que correspondem ao texto
                UserDao userDao = new UserDao();
                ProcDao procDao = new ProcDao();

                // Usar os métodos existentes
                User clienteUser = userDao.buscaClientePeloNomeTabEditar(textoBuscaCliente);
                List<String> nomesProc = procDao.buscarNomesClientesPorTexto(textoBuscaCliente);

                // Adicionar nomes à lista
                nomesClientes = new ArrayList<>();
                if (clienteUser != null) {
                    nomesClientes.add(clienteUser.getnome());
                }
                nomesClientes.addAll(nomesProc);
            }

            // Remover duplicatas e ordenar alfabeticamente
            Set<String> nomesUnicos = new TreeSet<>(nomesClientes);
            listClientesPetInter.getItems().setAll(new ArrayList<>(nomesUnicos));
        } catch (SQLException e) {
            // Lidar com exceções de SQL aqui, se necessário
            e.printStackTrace();
        }
    }
	private void onSelecionarClientePetInter(String nomeClienteSelecionado) {
	    if (nomeClienteSelecionado != null) {
		    // Atualize a ListView de processos e matrículas para o cliente selecionado
		    atualizarListViewComDadosClientePetIntercorrente(nomeClienteSelecionado);

		    // Atualize a TextField com o nome do cliente
		    txtNomePetInter.setText(nomeClienteSelecionado);
		
	   
		}
	}

	private void atualizarListViewComDadosClientePetIntercorrente(String nomeCliente) {
	    try {
	        // Chame o método do ProcDao para obter a lista de processos para o cliente selecionado
	        List<Proc> processos = procDao.obterNumerosProcessoseMatriculaPorNomeCliente(nomeCliente);

	        // Limpe a ListView
	        listProcessoseMat.getItems().clear();

	        // Percorra a lista de processos e adicione as informações à ListView
	        for (Proc processo : processos) {
	            String numeroProcesso = processo.getnumeroprocesso();
	            String matricula = processo.getMatricula();
	            String info = numeroProcesso + " - " + matricula;
	            listProcessoseMat.getItems().add(info);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Lide com exceções SQL, se necessário
	    }
	}


	private void setupListViewClickHandler() {
		listProcessoseMat.setOnMouseClicked(event -> {
			// Verifique se um item da ListView foi clicado
			Object itemSelecionado = listProcessoseMat.getSelectionModel().getSelectedItem();

			if (itemSelecionado != null && itemSelecionado instanceof String) {
				// Converta o item selecionado para uma String
				String linhaSelecionada = (String) itemSelecionado;

				// Divida a linha pelo caractere '-' para extrair a matrícula
				String[] partes = linhaSelecionada.split(" - ");

				// Verifique se a linha foi dividida corretamente (deve haver pelo menos duas
				// partes)
				if (partes.length >= 2) {
					// Remova espaços em branco em excesso e obtenha a matrícula
					String matricula = partes[1].trim();
					String numeroProcesso = partes[0].trim();

					// Defina a matrícula na txtMatPetInter
					txtMatPetInter.setText(matricula);
					txtNumeroProcInter.setText(numeroProcesso);
					try {
						UserDao userDao = new UserDao();
						boolean matriculaExiste = userDao.verificaMatricula(matricula);

						if (matriculaExiste) {
							// Matrícula existe na base de dados, agora preencha as TextFields
							User user = userDao.obterUsuarioPelaMatricula(matricula);
							if (user != null) {
								// Verifique qual matrícula corresponde e preencha as TextFields adequadamente
								if (matricula.equals(user.getmat1())) {
									// Matrícula corresponde a mat1
								
									
									txtRefPetInter.setText(user.getref1());
									txtCargaHPetInter.setText(user.getcargh1());
									txtCargoPetInter.setText(user.getcargo1());
									txtNivelPetInter.setText(user.getnivel1());
									labelDataInicio.setText(user.getdatainicio1());

								} else if (matricula.equals(user.getmat2())) {
									// Matrícula corresponde a mat2

									txtRefPetInter.setText(user.getref2());
									txtCargaHPetInter.setText(user.getcargh2());
									txtCargoPetInter.setText(user.getcargo2());
									txtNivelPetInter.setText(user.getnivel2());
									labelDataInicio.setText(user.getdatainicio2());
						
								}
							}
						} else {
							// Matrícula não existe na base de dados, faça o que for necessário aqui
							alert("Matrícula não encontrada", "A matrícula não foi encontrada na base de dados.", AlertType.WARNING);

						}
					} catch (SQLException e) {
						// Trate exceções de SQL aqui
					}
					// Obtenha o número do processo da txtNumeroProcInter
					@SuppressWarnings("unused")
					String numeroDoProcesso = txtNumeroProcInter.getText();

					try {
						ProcDao procDao = new ProcDao();
						String tema = procDao.obterTemaPorNumeroProcesso(numeroProcesso);

						if (tema != null) {
							// Configure o tema na txtTemaProcesso
							txtTemaProcesso.setText(tema);
						} else {
							// Não foi encontrado um tema para o número do processo
							// Trate conforme necessário
						}
					} catch (SQLException e) {
						// Trate exceções de SQL aqui
					}
					String numeroDoProcessoVara = txtNumeroProcInter.getText();
					try {
						ProcDao procDao = new ProcDao();
						String vara = procDao.obterVaraPorNumeroProcesso(numeroDoProcessoVara);
						if (vara != null) {
							txtVaraProcesso.setText(vara);
						} else {
						}
					} catch (SQLException e) {
					}
					String numeroDoProcessoComarca = txtNumeroProcInter.getText();
					try {
						ProcDao procDao = new ProcDao();
						String comarca = procDao.obterComarcaPorNumeroProcesso(numeroDoProcessoComarca);
						if (comarca != null) {
							txtComarcaProcesso.setText(comarca);
						} else {
						}
					} catch (SQLException e) {
					}
				}
			}
		});
	}

	@FXML
	private ChoiceBox<String> choiceBoxValorPorTema;
	@FXML
	private Button btnMostrarValores;
	@FXML
	private TextField txtValorPrincipal;
	@FXML
	private TextField txtRioPrev;
	@FXML
	private TextField txtValorFinal;

	@FXML
	private void buscarValoresPorTemaNomeMatricula(ActionEvent event) {
		String temaSelecionado = choiceBoxValorPorTema.getValue();
		String nome = txtNomePetInter.getText();
		String matricula = txtMatPetInter.getText();

		if (temaSelecionado != null && !nome.isEmpty() && !matricula.isEmpty()) {
			try {
				CalculosDao calculosDao = new CalculosDao();
				Calculos calculos = calculosDao.buscarCalculosPorTemaNomeMatricula(temaSelecionado, nome, matricula);

				if (calculos != null) {
					txtValorPrincipal.setText(calculos.getValorPrincipal());
					txtRioPrev.setText(calculos.getRioPrev());
					txtValorFinal.setText(calculos.getValorFinal());
				} else {
					// Trate o caso em que não foram encontrados valores
					exibirAlerta("Aviso", "Nenhum cálculo encontrado com os dados fornecidos.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// Trate os erros de banco de dados, se necessário
				exibirAlerta("Erro", "Ocorreu um erro ao buscar os valores: " + e.getMessage());
			}
		} else {
			// Trate o caso em que os campos não estão preenchidos corretamente
			exibirAlerta("Aviso", "Preencha todos os campos corretamente.");
		}
	}

	private void exibirAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}

	// CONTROLES PARA GERAÇÃO DE DOCUMENTOS
	// TAB Documentos Administrativos
	@FXML
	private ChoiceBox<String> choiceBoxDocumento;
	@FXML
	private Button btnGerarDocumentoSelecionado;
	private Map<String, String> documentosModelos = new HashMap<>();

	@FXML
	private void gerarDocumentoSelecionado() {
		String nomeDocumentoSelecionado = choiceBoxDocumento.getValue();

		if (nomeDocumentoSelecionado != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\documentos\\"
					+ nomeDocumentoSelecionado + ".docx";
			Map<String, String> dataMap = new HashMap<>();
			dataMap.put("${nome}", txtNomeDocAdm.getText());
			dataMap.put("NACIONALIDADE", txtNacionalidadeDocAdm.getText());
			dataMap.put("PROFISSAO", txtProfissaoDocAdm.getText());
			dataMap.put("ESTADOCIVIL", txtEstadoCivilDocAdm.getText());
			dataMap.put("IDENTIDADE", txtRGDocAdm.getText());
			dataMap.put("CPF", txtCPFDocAdm.getText());
			dataMap.put("IDFUNCIONAL", txtIdFDocAdm.getText());
			dataMap.put("ENDERECO", txtEnderecoDocAdm.getText());
			dataMap.put("${cep}", txtCEPDocAdm.getText());
			// Adicione outros campos de texto ao dataMap

			preencherDocumento(templatePath, dataMap, nomeDocumentoSelecionado);
		}
	}

	private void preencherDocumento(String nomeDocumento, Map<String, String> dataMap, String nomeArquivo) {
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(nomeDocumento));

			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				for (XWPFRun run : paragraph.getRuns()) {
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : dataMap.entrySet()) {
							text = text.replace(entry.getKey(), entry.getValue());
						}
						run.setText(text, 0);
					}
				}
			}

			salvarDocumentoGerado(doc, nomeArquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvarDocumentoGerado(XWPFDocument doc, String nomeDocumento) {
		String nome = txtNomePetInter.getText();
		String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\"
				+ nomeDocumento + " - " + nome + "_-.docx";
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			doc.write(out);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TAB PETIÇÕES INICIAIS

	// NE
	@FXML
	private Label labelCidade;

	@FXML
	private void gerarInicialNE() {
		String nomeDocumentoSelecionado = "Peticao_Inicial_Nova_Escola.docx";

		String valorBruto = txtValorBrutoNE.getText();
		String rioPrev = txtRioPrevNE.getText();
		String valorFinal = txtValorFinalNE.getText();
		String matricula = txtMatPetInicial.getText();
		String escola = txtEscolaNE.getText();
		String nota = txtNotaNE.getText();

		if (nomeDocumentoSelecionado != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\Iniciais\\"
					+ nomeDocumentoSelecionado;
			Map<String, String> dataMap = new HashMap<>();

			dataMap.put("${nome}", txtNomePetInicial.getText());
			dataMap.put("NACIONALIDADE", txtNacionalidadePetInicial.getText());
			dataMap.put("PROFISSAO", txtProfissaoDPetInicial.getText());
			dataMap.put("ESTADOCIVIL", txtEstadoCivilPetInicial.getText());
			dataMap.put("IDENTIDADE", txtRGPetInicial.getText());
			dataMap.put("CPF", txtCPFPetInicial.getText());
			dataMap.put("IDFUNCIONAL", txtIdFPetInicial.getText());
			dataMap.put("ENDERECO", txtEnderecoPetInicial.getText());
			dataMap.put("${cep}", txtCEPPetInicial.getText());
			dataMap.put("xxxxxxx", labelCidade.getText());
			dataMap.put("[VARA]", choiceTipoVaraNE.getValue());
			dataMap.put("S4L4RI0", choiceSalarioMinimo.getValue());
			dataMap.put("VALORBRUTO", valorBruto);
			dataMap.put("VALORRIOPREV", rioPrev);
			dataMap.put("VALORFINAL", valorFinal);
			dataMap.put("M4TRICUL4", matricula);
			dataMap.put("ESC0L4", escola);
			dataMap.put("N0T4", nota);

			// Adicione outros campos de texto ao dataMap

			preencherInicialNE(templatePath, dataMap, nomeDocumentoSelecionado);
		}
	}

	private void preencherInicialNE(String nomeDocumento, Map<String, String> dataMap, String nomeArquivo) {
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(nomeDocumento));

			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				for (XWPFRun run : paragraph.getRuns()) {
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : dataMap.entrySet()) {
							text = text.replace(entry.getKey(), entry.getValue());
						}
						run.setText(text, 0);
					}
				}
			}

			salvarInicialNE(doc, nomeArquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvarInicialNE(XWPFDocument doc, String nomeDocumento) {
		String nome = txtNomePetInicial.getText();
		String matricula = txtMatPetInicial.getText();
		String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\"
				+ nomeDocumento + " - " + nome + " - " + matricula + "_gerado.docx";
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			doc.write(out);
			exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		}
	}

	private void exibirAviso(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}

	// PETIÇÃO INICIAL INTERNIVEIS
	@FXML
	private TextField txtValorBrutoInterniveis;
	@FXML
	private TextField txtRioPrevInterniveis;
	@FXML
	private TextField txtValorFinalInterniveis;
	@FXML
	private ChoiceBox<String> choiceSalarioMinimoInterniveis;
	@FXML
	private TextArea txaInfoNivel;
	@FXML
	private Button btnValoresInterniveis;
	@FXML
	private Button btnInfoNivel;
	@FXML
	private Button btnGerarInicialInterniveis;

	public void calcularNiveisInterniveis(ActionEvent event) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String dataInicioStr = txtDataInicioPetInicial.getText();
		String referencia = txtRefPetInicial.getText();
		Date dataInicio;

		try {
			dataInicio = dateFormat.parse(dataInicioStr);
		} catch (ParseException e) {
			exibirAlerta("Erro", "Erro ao converter data de início.");
			return;
		}

		int nivelInicio;

		switch (referencia) {
		case "A":
			nivelInicio = 1;
			break;
		case "B":
			nivelInicio = 2;
			break;
		case "C":
			nivelInicio = 3;
			break;
		case "D":
			nivelInicio = 4;
			break;
		default:
			exibirAlerta("Erro", "Referência inválida.");
			return;
		}

		Date dataFim;

		try {
			dataFim = dateFormat.parse("01/04/2003");
		} catch (ParseException e) {
			exibirAlerta("Erro", "Erro ao converter data de fim.");
			return;
		}

		int nivel = nivelInicio;

		Date dataAtual = dataInicio;

		String resultado = ""; // Inicialize uma string para construir o resultado.

		resultado += "Nível: " + nivel + " (a partir de: " + dateFormat.format(dataInicio) + ")\n";

		while (dataAtual.before(dataFim)) {
			Date dataMudancaNivel = adicionarAnos(dataAtual, 5);
			if (dataMudancaNivel.after(dataFim)) {
				break;
			}

			nivel++;
			if (nivel <= nivelInicio + 5) {
				resultado += "Nível: " + nivel + " (a partir de: " + dateFormat.format(dataMudancaNivel) + ")\n";
			} else {
				break; // Se atingiu o nível máximo, saia do loop.
			}

			dataAtual = dataMudancaNivel;
		}

		txaInfoNivel.setText(resultado); // Defina o resultado na TextArea.
	}

	public static Date adicionarAnos(Date data, int anos) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(data);
		cal.add(java.util.Calendar.YEAR, anos);
		return cal.getTime();
	}

	@FXML
	private void gerarInicialInterniveis() {
		String nomeDocumentoSelecionado = "Peticao_Inicial_Interniveis.docx";

		String valorBruto = txtValorBrutoInterniveis.getText();
		String rioPrev = txtRioPrevInterniveis.getText();
		String valorFinal = txtValorFinalInterniveis.getText();
		String matricula = txtMatPetInicial.getText();
		String referencia = txtRefPetInicial.getText();
		String cargo = txtCargoPetInicial.getText();
		String nivelInterniveis = txaInfoNivel.getText();

		if (nomeDocumentoSelecionado != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\Iniciais\\"
					+ nomeDocumentoSelecionado;
			Map<String, String> dataMap = new HashMap<>();

			dataMap.put("${nome}", txtNomePetInicial.getText());
			dataMap.put("NACIONALIDADE", txtNacionalidadePetInicial.getText());
			dataMap.put("PROFISSAO", txtProfissaoDPetInicial.getText());
			dataMap.put("ESTADOCIVIL", txtEstadoCivilPetInicial.getText());
			dataMap.put("IDENTIDADE", txtRGPetInicial.getText());
			dataMap.put("CPF", txtCPFPetInicial.getText());
			dataMap.put("IDFUNCIONAL", txtIdFPetInicial.getText());
			dataMap.put("ENDERECO", txtEnderecoPetInicial.getText());
			dataMap.put("${cep}", txtCEPPetInicial.getText());
			dataMap.put("M4TRICUL4", matricula);
			dataMap.put("C4RG0", cargo);

			dataMap.put("S4L4RI0", choiceSalarioMinimoInterniveis.getValue());
			dataMap.put("3XPLIC4CAONIV3L", nivelInterniveis);
			dataMap.put("R3F3R3NCI4", referencia);

			dataMap.put("VALORBRUTO", valorBruto);
			dataMap.put("VALORRIOPREV", rioPrev);
			dataMap.put("VALORFINAL", valorFinal);

			// Adicione outros campos de texto ao dataMap

			preencherInicialInterniveis(templatePath, dataMap, nomeDocumentoSelecionado);
		}
	}

	private void preencherInicialInterniveis(String nomeDocumento, Map<String, String> dataMap, String nomeArquivo) {
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(nomeDocumento));

			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				for (XWPFRun run : paragraph.getRuns()) {
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : dataMap.entrySet()) {
							text = text.replace(entry.getKey(), entry.getValue());
						}
						run.setText(text, 0);
					}
				}
			}

			salvarInicialInterniveis(doc, nomeArquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvarInicialInterniveis(XWPFDocument doc, String nomeDocumento) {
		String nome = txtNomePetInicial.getText();
		String matricula = txtMatPetInicial.getText();
		String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\"
				+ nomeDocumento + " - " + nome + " - " + matricula + "_gerado.docx";
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			doc.write(out);
			exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		}
	}

	// INICIAL PISO
	@FXML
	private ChoiceBox<String> choiceBoxSalarioMinimoPiso;
	@FXML
	private ChoiceBox<String> choiceBoxVaraPiso;
	@FXML
	private ChoiceBox<String> choiceBoxAtivaInativa;
	@FXML
	private TextField txtValorHoje;
	@FXML
	private TextField txtTrienioHoje;
	@FXML
	private TextField txtPercTrienioHoje;
	@FXML
	private TextField txtValorPiso;
	@FXML
	private TextField txtDiferencaPiso;
	@FXML
	private TextField txtTrienioPiso;
	@FXML
	private TextField txtTotalPiso;
	@FXML
	private TextField txtDiferencaTotal;
	@FXML
	private Button btnValoresPiso;
	@FXML
	private Button btnGerarInicialPiso;

	@FXML
	private void gerarInicialPiso() {
		String nomeDocumentoSelecionado = "Peticao_Inicial_Piso_VC.docx";

		String matricula = txtMatPetInicial.getText();
		String referencia = txtRefPetInicial.getText();
		String cargo = txtCargoPetInicial.getText();
		String ativaInativa = choiceBoxAtivaInativa.getValue();
		String tipoVara = choiceBoxVaraPiso.getValue();
		String vencimentoPiso = txtValorPiso.getText();
		String vencimentoHoje = txtValorHoje.getText();
		String trienioHoje = txtTrienioHoje.getText();
		String percentualTrienio = txtPercTrienioHoje.getText();
		String trienioPiso = txtTrienioPiso.getText();
		double vencimentoPisoD = Double.parseDouble(txtValorPiso.getText());
		double vencimentoHojeD = Double.parseDouble(txtValorHoje.getText());
		double trienioHojeD = Double.parseDouble(txtTrienioHoje.getText());
		double trienioPisoD = Double.parseDouble(txtTrienioPiso.getText());

		double diferencaPisoHoje = (double) (vencimentoPisoD - vencimentoHojeD);
		double totalPisoMaisTrienioPiso = (double) (vencimentoPisoD + trienioPisoD);
		double diferencaTotal = (double) (vencimentoPisoD + trienioPisoD) - (vencimentoHojeD + trienioHojeD);

		if (nomeDocumentoSelecionado != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\Iniciais\\"
					+ nomeDocumentoSelecionado;
			Map<String, String> dataMap = new HashMap<>();

			dataMap.put("${nome}", txtNomePetInicial.getText());
			dataMap.put("NACIONALIDADE", txtNacionalidadePetInicial.getText());
			dataMap.put("PROFISSAO", txtProfissaoDPetInicial.getText());
			dataMap.put("ESTADOCIVIL", txtEstadoCivilPetInicial.getText());
			dataMap.put("IDENTIDADE", txtRGPetInicial.getText());
			dataMap.put("CPF", txtCPFPetInicial.getText());
			dataMap.put("IDFUNCIONAL", txtIdFPetInicial.getText());
			dataMap.put("ENDERECO", txtEnderecoPetInicial.getText());
			dataMap.put("${cep}", txtCEPPetInicial.getText());
			dataMap.put("M4TRICUL4", matricula);
			dataMap.put("C4RG0", cargo);
			dataMap.put("xxxxxxx", labelCidade.getText());
			dataMap.put("[VARA]", tipoVara);
			dataMap.put("R3F3R3NCI4", referencia);
			dataMap.put("NUM3R0FUNC", txtIdFPetInicial.getText());
			dataMap.put("N1V3L", txtNivelPetInicial.getText());
			dataMap.put("C4RG4H0R4RI4", txtCargaHPetInicial.getText());
			dataMap.put("S4L4RI0", choiceBoxSalarioMinimoPiso.getValue());
			dataMap.put("4TIV4OUIN4TIVA", ativaInativa);
			dataMap.put("V3NCIM3NTOB4S3", vencimentoPiso);
			dataMap.put("V3NCIM3NT0H0J3", vencimentoHoje);
			dataMap.put("TR13N10H0J3", trienioHoje);
			dataMap.put("P3RCH0J3", percentualTrienio);
			dataMap.put("D1F3RENP1S0", String.valueOf(diferencaPisoHoje));
			dataMap.put("TR13N1OB4S3", trienioPiso);
			dataMap.put("T0T4LB4S3", String.valueOf(totalPisoMaisTrienioPiso));
			dataMap.put("D1F3RENT0T4L", String.valueOf(diferencaTotal));

			System.out.println("Valores preenchidos: " + " V3NCIM3NTOB4S3: " + vencimentoPiso + "V3NCIM3NT0H0J3"
					+ vencimentoHoje + "TR13N10H0J3" + trienioHoje + "P3RCH0J3" + percentualTrienio + "D1F3RENP1S0"
					+ String.valueOf(diferencaPisoHoje) + "TR13N1OB4S3" + trienioPiso + "T0T4LB4S3"
					+ String.valueOf(totalPisoMaisTrienioPiso) + "D1F3RENT0T4L" + String.valueOf(diferencaTotal));

			// Adicione outros campos de texto ao dataMap

			preencherInicialPiso(templatePath, dataMap, nomeDocumentoSelecionado);
		}
	}

	private void preencherInicialPiso(String nomeDocumento, Map<String, String> dataMap, String nomeArquivo) {
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(nomeDocumento));

			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				for (XWPFRun run : paragraph.getRuns()) {
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : dataMap.entrySet()) {
							text = text.replace(entry.getKey(), entry.getValue());
						}
						run.setText(text, 0);
					}
				}
			}

			salvarInicialPiso(doc, nomeArquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvarInicialPiso(XWPFDocument doc, String nomeDocumento) {
		String nome = txtNomePetInicial.getText();
		String matricula = txtMatPetInicial.getText();
		String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\"
				+ nomeDocumento + " - " + nome + " - " + matricula + "_gerado.docx";
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			doc.write(out);
			exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		}
	}

	// TAB PETIÇÕES INTERCORRENTES
	// GERAR PETIÇÃO INTERCORRENTE INTERNIVEIS
	@FXML
	private ChoiceBox<String> choiceBoxPetInter;
	@FXML
	private Button btnGeraPetInt;
	private Map<String, String> nomesPetInter = new HashMap<>();

	@FXML
	private void gerarPetInterSelecionada() {

		String nome = txtNomePetInter.getText();
		String numeroprocesso = txtNumeroProcInter.getText();
		String cargoAutor = txtCargoPetInter.getText();
		String cargahorariaAutor = txtCargaHPetInter.getText();

		String referencia = txtRefPetInter.getText();
		String valorBrutoTexto = txtValorPrincipal.getText();
		double valorBrutoDouble = 0.0; // Inicializa com zero, caso ocorra um erro na conversão
		try {
			// Remove o "R$" e substitui a vírgula por ponto
			String valorNumerico = valorBrutoTexto.replace("R$", "") // Remove "R$"
					.replace(".", "") // Remove os pontos de milhar, se houver
					.replace(",", "."); // Substitui a vírgula por ponto
			valorBrutoDouble = Double.parseDouble(valorNumerico);
		} catch (NumberFormatException e) {
			// Lida com um valor inválido
			exibirAlerta("Erro", "Valor bruto inválido: " + valorBrutoTexto);
			return; // Saia do método se houver um erro
		}

		double valorSucumbencia = 0.10 * valorBrutoDouble; // 10% do valor bruto

		DecimalFormat df = new DecimalFormat("#0.00");
		// Formate o valor da sucumbência com duas casas decimais
		String valorSucumbenciaFormatado = "R$ " + df.format(valorSucumbencia);

		String rioPrev = txtRioPrev.getText();
		String valorFinal = txtValorFinal.getText();

		int nivelInicial = Integer.parseInt(txtNivelPetInter.getText());
		int nivel2 = (int) (nivelInicial + 1);
		int nivel3 = (int) (nivel2 + 1);
		int nivel4 = (int) (nivel3 + 1);
		int nivel5 = (int) (nivel4 + 1);
		String daatadeinicio = labelDataInicio.getText();

		LocalDate daatainicio = LocalDate.parse(daatadeinicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		// Calcula a data 5 anos depois da data inicial
		LocalDate data5AnosDepois = daatainicio.plusYears(5);
		// Calcula a data 10 anos depois da data inicial
		LocalDate data10AnosDepois = daatainicio.plusYears(10);
		// Calcula a data 15 anos depois da data inicial
		LocalDate data15AnosDepois = daatainicio.plusYears(15);

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String daatadeinicioFormatada = daatainicio.format(dateFormatter);
		String data5AnosDepoisFormatada = data5AnosDepois.format(dateFormatter);
		String data10AnosDepoisFormatada = data10AnosDepois.format(dateFormatter);
		String data15AnosDepoisFormatada = data15AnosDepois.format(dateFormatter);

		String nomePeticaoInter = choiceBoxPetInter.getValue();

		if (nomePeticaoInter != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\PetInterniveis\\"
					+ nomePeticaoInter + ".docx";
			Map<String, String> dataMap = new HashMap<>();
			dataMap.put("[NOME]", nome);
			dataMap.put("[PROCESSO]", numeroprocesso);
			dataMap.put("REFAUTOR", referencia);
			dataMap.put("NIVELAUTOR", String.valueOf(nivelInicial));
			dataMap.put("nimais5", String.valueOf(nivel2));
			dataMap.put("nimais10", String.valueOf(nivel3));
			dataMap.put("nimais15", String.valueOf(nivel4));
			dataMap.put("nimais20", String.valueOf(nivel5));
			dataMap.put("DATAINICIO", String.valueOf(daatadeinicioFormatada));
			dataMap.put("data5AnosDepois", String.valueOf(data5AnosDepoisFormatada));
			dataMap.put("data10AnosDepois", String.valueOf(data10AnosDepoisFormatada));
			dataMap.put("data15AnosDepois", String.valueOf(data15AnosDepoisFormatada));
			dataMap.put("SUCUMBENCIA", String.valueOf(valorSucumbenciaFormatado));
			dataMap.put("VALORBRUTO", String.valueOf(valorBrutoTexto));
			dataMap.put("VALORRIOPREV", String.valueOf(rioPrev));
			dataMap.put("VALORFINAL", String.valueOf(valorFinal));
			dataMap.put("#CH", cargahorariaAutor);
			dataMap.put("CARGOAUTOR", cargoAutor);

			preencherPetInter(templatePath, dataMap, nomePeticaoInter);

		}
	}

	private void preencherPetInter(String nomePeticaoInter, Map<String, String> dataMap, String nomeArquivoInter) {
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(nomePeticaoInter));

			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				for (XWPFRun run : paragraph.getRuns()) {
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : dataMap.entrySet()) {
							text = text.replace(entry.getKey(), entry.getValue());
						}
						run.setText(text, 0);
					}
				}
			}

			salvarPetInterGerado(doc, nomeArquivoInter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvarPetInterGerado(XWPFDocument doc, String nomePeticaoInter) {
		String nome = txtNomePetInter.getText();
		String numeroprocesso = txtNumeroProcInter.getText();
		String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\"
				+ nomePeticaoInter + " - " + nome + " - " + numeroprocesso + "_gerado.docx";
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			doc.write(out);
			exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		}
	}

	// GERAR PETIÇÃO INTERCORRENTE NE
	@FXML
	private Button btnGeraPetNE;
	@FXML
	private ChoiceBox<String> choiceBoxPetInterNE;
	private Map<String, String> nomesPetInterNE = new HashMap<>();

	@FXML
	private void gerarPetInterNESelecionada() {
		String nome = txtNomePetInter.getText();
		String numeroprocesso = txtNumeroProcInter.getText();
		String matricula = txtMatPetInter.getText();
		String referencia = txtRefPetInter.getText();
		String cargo = txtCargoPetInter.getText();
		String cargahoraria = txtCargaHPetInter.getText();
		String nivel = txtNivelPetInter.getText();
		String vara = txtVaraProcesso.getText();
		String comarca = txtComarcaProcesso.getText();

		String nomePeticaoNE = choiceBoxPetInterNE.getValue();

		if (nomePeticaoNE != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\PetNE\\"
					+ nomePeticaoNE + ".docx";
			Map<String, String> dataMap = new HashMap<>();
			dataMap.put("[NOME]", nome);
			dataMap.put("[PROCESSO]", numeroprocesso);
			dataMap.put("PROFISSAO", matricula);
			dataMap.put("ESTADOCIVIL", referencia);
			dataMap.put("IDENTIDADE", cargo);
			dataMap.put("CPF", cargahoraria);
			dataMap.put("IDFUNCIONAL", nivel);
			dataMap.put("[VARA]", vara);
			dataMap.put("xxxxxxx", comarca);

			preencherPetNE(templatePath, dataMap, nomePeticaoNE);
		}
	}

	private void preencherPetNE(String nomePeticaoNE, Map<String, String> dataMap, String nomeArquivoNE) {
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(nomePeticaoNE));

			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				for (XWPFRun run : paragraph.getRuns()) {
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : dataMap.entrySet()) {
							text = text.replace(entry.getKey(), entry.getValue());
						}
						run.setText(text, 0);
					}
				}
			}

			salvarPetNEGerado(doc, nomeArquivoNE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvarPetNEGerado(XWPFDocument doc, String nomePeticaoNE) {
		String nome = txtNomePetInter.getText();
		String numeroprocesso = txtNumeroProcInter.getText();
		String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\"
				+ nomePeticaoNE + " - " + nome + " - " + numeroprocesso + "_gerado.docx";
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			doc.write(out);
			exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		}
	}

	// GERAR PETIÇÃO INTERCORRENTE PISO
	@FXML
	private ChoiceBox<String> choiceBoxPetPiso;
	@FXML
	private Button btnGeraPetPiso;
	private Map<String, String> nomesPetPiso = new HashMap<>();

	@FXML
	private void gerarPetPisoSelecionada() {

		String nome = txtNomePetInter.getText();
		String numeroprocesso = txtNumeroProcInter.getText();
		String cargoAutor = txtCargoPetInter.getText();
		String cargahorariaAutor = txtCargaHPetInter.getText();
		String referencia = txtRefPetInter.getText();
		String comarca = txtComarcaProcesso.getText();
		String vara = txtVaraProcesso.getText();
		int nivelInicial = Integer.parseInt(txtNivelPetInter.getText());

		String nomePeticaoPiso = choiceBoxPetPiso.getValue();

		if (nomePeticaoPiso != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\PetPiso\\"
					+ nomePeticaoPiso + ".docx";
			Map<String, String> dataMap = new HashMap<>();
			dataMap.put("[NOME]", nome);
			dataMap.put("[PROCESSO]", numeroprocesso);
			dataMap.put("REFAUTOR", referencia);
			dataMap.put("NIVELAUTOR", String.valueOf(nivelInicial));
			dataMap.put("xxxxxxx", comarca);
			dataMap.put("[VARA]", vara);
			dataMap.put("#CH", cargahorariaAutor);
			dataMap.put("CARGOAUTOR", cargoAutor);

			preencherPetPiso(templatePath, dataMap, nomePeticaoPiso);
		}
	}

	private void preencherPetPiso(String nomePeticaoPiso, Map<String, String> dataMap, String nomeArquivoPiso) {
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(nomePeticaoPiso));

			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				for (XWPFRun run : paragraph.getRuns()) {
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : dataMap.entrySet()) {
							text = text.replace(entry.getKey(), entry.getValue());
						}
						run.setText(text, 0);
					}
				}
			}

			salvarPetPisoGerado(doc, nomeArquivoPiso);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvarPetPisoGerado(XWPFDocument doc, String nomePeticaoPiso) {
		String nome = txtNomePetInter.getText();
		String numeroprocesso = txtNumeroProcInter.getText();
		String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\"
				+ nomePeticaoPiso + " - " + nome + " - " + numeroprocesso + "_g.docx";
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			doc.write(out);
			exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		}
	}

	public Map<String, String> getNomesPetInterNE() {
		return nomesPetInterNE;
	}

	public void setNomesPetInterNE(Map<String, String> nomesPetInterNE) {
		this.nomesPetInterNE = nomesPetInterNE;
	}

	public Map<String, String> getNomesPetPiso() {
		return nomesPetPiso;
	}

	public void setNomesPetPiso(Map<String, String> nomesPetPiso) {
		this.nomesPetPiso = nomesPetPiso;
	}

	public Map<String, String> getNomesPetInter() {
		return nomesPetInter;
	}

	public void setNomesPetInter(Map<String, String> nomesPetInter) {
		this.nomesPetInter = nomesPetInter;
	}

	public Map<String, String> getDocumentosModelos() {
		return documentosModelos;
	}

	public void setDocumentosModelos(Map<String, String> documentosModelos) {
		this.documentosModelos = documentosModelos;
	}

	// TAB PETIÇÕES EM RECURSO
	@FXML
	private TextField txtBuscaNomeTabRec;
	@FXML
	private TextField txtNumeroRec;
	@FXML
	private TextField txtNomeClienteRec;
	@FXML
	private TextField txtProcOriginario;
	@FXML
	private TextField txtTipoRec;
	@FXML
	private TextField txtStatusRec;
	@FXML
	private TextField txtRecorridoRecorrente;
	@FXML
	private TextField txtCamara;
	@FXML
	private TextField txtRelator;
	@FXML
	private TextField txtTema;
	@FXML
	private Button btnBuscaClienteRec;
	@FXML
	private ChoiceBox<String> cbRecNE;
	@FXML
	private ChoiceBox<String> cbRecInter;
	@FXML
	private ChoiceBox<String> cbRecPiso;
	@FXML
	private Button btnGeraPetRecNE;
	@FXML
	private Button btnGeraPetRecInter;
	@FXML
	private Button btnGeraPetRecPiso;
	@FXML
	private ListView<String> listClienteRec;
	@FXML
	private ListView<String> listRecursoRec;

	// BUSCA CLIENTE E PREENCHIMENTO TEXTFIELDS

	@FXML
	private void buscarNomesClientesRec() {
		String textoBuscaClienteRec = txtBuscaNomeTabRec.getText().trim();

		try {
			RecDao recDao = new RecDao(); // Crie uma instância de RecDao
			List<String> nomesClientesRec = recDao.buscarNomesClientesPorTexto(textoBuscaClienteRec);

			// Usando um conjunto para garantir nomes únicos e ordená-los
			Set<String> nomesUnicos = new TreeSet<>(nomesClientesRec);

			listClienteRec.getItems().setAll(new ArrayList<>(nomesUnicos));
			// Define os resultados na ListView
		} catch (SQLException e) {
			// Lide com exceções de SQL aqui, se necessário
			e.printStackTrace();
		}
	}

	@FXML
	private void onSelecionarClienteRec() throws SQLException {
		// Obtém o item selecionado da lista
		String selectedCliente = (String) listClienteRec.getSelectionModel().getSelectedItem();

		if (selectedCliente != null) {
			try {
				RecDao recDao = new RecDao();
				List<String> numerosRecursos = recDao.buscarNumerosRecursoPorNomeCliente(selectedCliente);

				if (!numerosRecursos.isEmpty()) {
					txtNomeClienteRec.setText(selectedCliente);

					// Limpe a lista de recursos e adicione os números de recurso do cliente
					listRecursoRec.getItems().setAll(numerosRecursos);
				}
			} catch (SQLException e) {
				// Lide com exceções de SQL aqui, se necessário
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onSelecionarRec() throws SQLException {
		// Obtém o item selecionado da lista de recursos
		String selectedNumeroRec = (String) listRecursoRec.getSelectionModel().getSelectedItem();

		if (selectedNumeroRec != null) {
			try {
				RecDao recDao = new RecDao();
				Rec rec = recDao.buscarRecursoPorNumero(selectedNumeroRec);

				if (rec != null) {
					// Preencha as TextFields com os detalhes do recurso
					txtNumeroRec.setText(rec.getNumerorecurso());
					txtNomeClienteRec.setText(rec.getNomedocliente());
					txtProcOriginario.setText(rec.getProcessoorigem());
					txtTipoRec.setText(rec.getTiporecurso());
					txtStatusRec.setText(rec.getStatus());
					txtRecorridoRecorrente.setText(rec.getRecorridoourecorrente());
					txtCamara.setText(rec.getJulgador());
					txtRelator.setText(rec.getRelator());
				}
			} catch (SQLException e) {
				// Lide com exceções de SQL aqui, se necessário
				e.printStackTrace();
			}
		} else {
			// Lidar com o caso em que nenhum número de recurso foi selecionado
		}
	}

}
