package guiWAdv;

import java.io.FileInputStream;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import infoCalculos.Calculos;
import infoCalculos.CalculosDao;
import infoCalculos.professorescola;
import infoCalculos.professorescolaDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Tela04GeradorDeDocumentoController {

	private static final Logger logger = Logger.getLogger(com.WernerADV.Software.App.class.getName());
	private UserDao userDao = new UserDao();
	private ProcDao procDao = new ProcDao();
	private RecDao recDao = new RecDao();
	

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
	private ComboBox<String> comboClientesDocAdm;
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
		private ComboBox<String> comboClientesPetInicial;
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
				        Tela05CalculosController calculosController = loader.getController();

				        stage.show();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				}
	
		
				                        //SUBTAB - NE
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
				
				
				
		
	                                       	//TAB PETIÇÕES INTERCORRENTES
		@FXML
		private ComboBox comboClientesPetInter;
		@FXML 
		private ListView listClientesPetInter;
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
		    
		    // Limpe a ComboBox antes de adicionar os nomes dos clientes
		    comboClientesDocAdm.getItems().clear();
		    
		    // Adicione os nomes dos clientes à ComboBox
		    comboClientesDocAdm.getItems().addAll(todosNomesClientes);
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		    // Lide com exceções SQL, se necessário
		}

		comboClientesDocAdm.setOnAction(event -> {
			String clienteSelecionado = comboClientesDocAdm.getSelectionModel().getSelectedItem();
			if (clienteSelecionado != null) {
				// Chame um método para atualizar as TextFields com os dados do cliente
				atualizarTextFieldscomDadosCliente(clienteSelecionado);

				// Chame um método para atualizar a ListView com o nome e as matrículas do
				// cliente
				atualizarListViewComDadosCliente(clienteSelecionado);
			}
		});
		// COMBOBOX - TAB Petição Inicial
		try {
			List<String> nomesClientes = procDao.obterNomesClientes();
			Collections.sort(nomesClientes);
			// Adicione os nomes dos clientes à ComboBox
			for (String nomeCliente : nomesClientes) {
				if (!comboClientesPetInicial.getItems().contains(nomeCliente)) {
					comboClientesPetInicial.getItems().add(nomeCliente);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		comboClientesPetInicial.setOnAction(event -> {
			String clienteSelecionado = comboClientesPetInicial.getSelectionModel().getSelectedItem();
			if (clienteSelecionado != null) {
				// Chame um método para atualizar as TextFields com os dados do cliente
				atualizarTextFieldscomDadosClientePetInicial(clienteSelecionado);

				// Chame um método para atualizar a ListView com o nome e as matrículas do
				// cliente
				atualizarListViewComDadosClientePetiInicial(clienteSelecionado);
			}
		});
		listClientesPetInicial.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    // Chame o método para preencher as TextFields com base na seleção
		    preencherTextFieldsComDadosClientePetInicial(newValue);
		});
		listClientesPetInicial.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null) {
		        String selectedItem = newValue.toString(); // Obtém o texto da linha selecionada

		        if (selectedItem.startsWith("Matrícula 1")) {
		            // Foi selecionada a Matrícula 1, você pode extrair os dados da matrícula 1 aqui
		         
		            // Extrair dados e preencher as TextFields aqui
		        } else if (selectedItem.startsWith("Matrícula 2")) {
		            // Foi selecionada a Matrícula 2, você pode extrair os dados da matrícula 2 aqui
		          
		            // Extrair dados e preencher as TextFields aqui
		        }
		    }
		});

		


		
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
		    
		    // Limpe a ComboBox antes de adicionar os nomes dos clientes
		    comboClientesPetInter.getItems().clear();
		    
		    // Adicione os nomes dos clientes à ComboBox
		    comboClientesPetInter.getItems().addAll(todosNomesClientes);
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		    // Lide com exceções SQL, se necessário
		}

		comboClientesPetInter.setOnAction(event -> {
		    Object selectedObject = comboClientesPetInter.getSelectionModel().getSelectedItem();
		    if (selectedObject != null) {
		        String clienteSelecionado = selectedObject.toString();
		        // Chame um método para atualizar a ListView com o Processo e matrícula do cliente
		        atualizarListViewComDadosClientePetIntercorrente(clienteSelecionado);
		    }
		});
		
	
		    setupListViewClickHandler();

		    
		
		
		
		
		
		
		
		
		                                // INITIALIZE PARA GERAÇÃO DE DOCUMENTOS
		                                    //TAB DOCUMENTOS ADMINISTRATIVOS

				
		List<String> nomesDocumentos = Arrays.asList(
			        "ProcuracaoInterniveis",
			        "ProcuracaoNE",
			        "ProcuracaoPiso",
			        "ProcuracaoNEPiso",
			        "ProcuracaoInterNEPiso",
			        "ProcuracaoInterNE",
			        "ProcuracaoInterPiso",
			        "ContratoIntNEPisoCom",
			        "ContratoIntNEPisoSem",
			        "ContratoInt",
			        "ContratoIntPisoCom",
			        "ContratoPisoCom",
			        "ContratoPisoSem",
			        "ContratoIntPisoSem",
			        "ContratoIntNE",
			        "ContratoNE",
			        "ContratoNEPisoSem",
			        "ContratoNEPisoCom",
			        "DeclaracaoHipo"
				);
		choiceBoxDocumento.getItems().addAll(nomesDocumentos);


		
		
		
		
		
		
		
		
		
		
		
		
	                                    	//TAB PETIÇÕES INICIAIS
		//NE
		List<String> salarioMinimo = Arrays.asList(
		        "1",
		        "2",
		        "3",
		        "4",
		        "5",
		        "6",
		        "7",
		        "8",
		        "9",
		        "10"
		        
			);
		choiceSalarioMinimo.getItems().addAll(salarioMinimo);
		choiceSalarioMinimoInterniveis.getItems().addAll(salarioMinimo);
		choiceBoxSalarioMinimoPiso.getItems().addAll(salarioMinimo);

		List<String> tipoVara = Arrays.asList(
		        "Vara Cível",
		        "Juizado Especial Fazendário",
		        "Vara de Fazenda Pública"
		        
		        
			);
		choiceTipoVaraNE.getItems().addAll(tipoVara);
		choiceBoxVaraPiso.getItems().addAll(tipoVara);
		
		List<String> ativaInativa = Arrays.asList(
				"Inativa",
				"Ativa",
		        "Inativo",
		        "Ativo"
		        
		        
			);
		choiceBoxAtivaInativa.getItems().addAll(ativaInativa);
		
		
		
		
		
		
		                                 //TAB PETIÇÕES INTERCORRENTES
		//INTERNIVEIS
		List<String> nomesPetInter = Arrays.asList(
		        "Peticao_RPV_interniveis",
		        "Peticao_RPV_interniveis_com_renuncia",
		        "Resposta_Impugnacao_calculo_ERJ_Interniveis",
		        "Peticao_Levantamento"
		        
			);
	choiceBoxPetInter.getItems().addAll(nomesPetInter);
		
		
		//NE
		List<String> nomesPetInterNE = Arrays.asList(
		        "PeticaoRespostaImpugnacaoERJNE",
		        "EDsAvaliacaoAplicada",
		        "PeticaoconcordandocalculoscontadorNE",
		        "PeticaodescabimentoSuspensaoNE",
		        "PeticaoProvasNE",
		        "PeticaoSobreParametrosNE"
		        
			);
	choiceBoxPetInterNE.getItems().addAll(nomesPetInterNE);
		
		
	
	
	//PISO
	List<String> nomesPetPiso = Arrays.asList(
	        "Réplica_Piso",
	        "teste",
	        "teste2",
	        "tste3",
	        "",
	        ""
	        
		);
choiceBoxPetPiso.getItems().addAll(nomesPetPiso);
		
	
	
	
	
	
	
	
	
	
	
	
	
	
										//CHOICEBOX CALCULOS
	//INTERNIVEIS
			List<String> valoresPorTema = Arrays.asList(
			        "Nova Escola",
			        "Interniveis",
			        "Piso"
			        
				);
		choiceBoxValorPorTema.getItems().addAll(valoresPorTema);
	
	
	
	
	
		
		
		
		
		// FECHA INITIALIZE
	}

	
	//TAB DOCUMENTOS ADMINISTRATIVOS
	//METODO PARA INFORMAÇÕES APARECEREM NAS TEXTFIELDS
	private void atualizarTextFieldscomDadosCliente(String DadosClienteSelecionado) {
		UserDao userDao = new UserDao();
		try {
			User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(DadosClienteSelecionado);

			User clienteSelecionadoAdm;

			if (clienteSelecionado != null) {
				// Se o processo for encontrado, atualize os campos TextField
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

			} else {
				// Limpe os campos Label se nenhum cliente for encontrado
				txtNomeDocAdm.clear();
				txtNacionalidadeDocAdm.clear();
				txtEstadoCivilDocAdm.clear();
				txtProfissaoDocAdm.clear();
				txtRGDocAdm.clear();
				txtCPFDocAdm.clear();
				txtIdFDocAdm.clear();
				txtEnderecoDocAdm.clear();
				txtCEPDocAdm.clear();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private ListView<String> listClientesDocAdm = new ListView<>();

	private void atualizarListViewComDadosCliente(String nomeCliente) {
		// Limpe a ListView
		listClientesDocAdm.getItems().clear();

		UserDao userDao = new UserDao();
		try {
			User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(nomeCliente);

			if (clienteSelecionado != null) {
				// Adicione o nome do cliente
			
				listClientesDocAdm.getItems().add("Nome: " + clienteSelecionado.getnome());

				// Verifique se mat1 não está vazio
				if (!clienteSelecionado.getmat1().isEmpty()) {
				
					listClientesDocAdm.getItems().add("Matrícula 1: " + clienteSelecionado.getmat1());
				}

				// Verifique se mat2 não está vazio ou é nulo

				if (clienteSelecionado.getmat2() != null && !clienteSelecionado.getmat2().isEmpty()) {
				
					listClientesDocAdm.getItems().add("Matrícula 2: " + clienteSelecionado.getmat2());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	//METODOS TAB PETIÇÕES INICIAIS
	private void atualizarListViewComDadosClientePetiInicial(String nomeCliente) {
		// Limpe a ListView
		listClientesPetInicial.getItems().clear();

		UserDao userDao = new UserDao();
		try {
			User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(nomeCliente);

			if (clienteSelecionado != null) {
				
				// Verifique se mat1 não está vazio
				if (!clienteSelecionado.getmat1().isEmpty()) {
				    listClientesPetInicial.getItems().add("Matrícula 1: " + clienteSelecionado.getmat1());
				}

				if (clienteSelecionado.getmat2() != null && !clienteSelecionado.getmat2().isEmpty()) {
				    listClientesPetInicial.getItems().add("Matrícula 2: " + clienteSelecionado.getmat2());
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//METODO PARA INFORMAÇÕES APARECEREM NAS TEXTFIELDS
		private void atualizarTextFieldscomDadosClientePetInicial (String DadosClienteSelecionadoInicial) {
			UserDao userDao = new UserDao();
			try {
				User clienteSelecionadoPI = userDao.buscaClientePeloNomeTabEditar(DadosClienteSelecionadoInicial);

				User clienteSelecionadoInicial;

				if (clienteSelecionadoPI != null) {
					// Se o processo for encontrado, atualize os campos TextField
					txtNomePetInicial.setText(clienteSelecionadoPI.getnome());
					txtNacionalidadePetInicial.setText(clienteSelecionadoPI.getnacionalidade());
					txtEstadoCivilPetInicial.setText(clienteSelecionadoPI.getestadocivil());
					txtProfissaoDPetInicial.setText(clienteSelecionadoPI.getprofissao());
					txtRGPetInicial.setText(clienteSelecionadoPI.getrg());
					txtCPFPetInicial.setText(clienteSelecionadoPI.getcpf());
					txtIdFPetInicial.setText(clienteSelecionadoPI.getidfuncional());
					txtEnderecoPetInicial.setText(clienteSelecionadoPI.getendereco() + " " + clienteSelecionadoPI.getcidade() + " "
							+ clienteSelecionadoPI.getestado());
					txtCEPPetInicial.setText(clienteSelecionadoPI.getcep());
					txtDataNascimentoPetInicial.setText(clienteSelecionadoPI.getdatanascimento());
					labelCidade.setText(clienteSelecionadoPI.getcidade());

				} else {
					// Limpe os campos Label se nenhum cliente for encontrado
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
		private void preencherTextFieldsComDadosClientePetInicial(String selecao) {
		    if (selecao != null) {
		        // Extrai a parte relevante da linha (por exemplo, "Matrícula 1" ou "Matrícula 2")
		        String parteRelevante = selecao.split(":")[0].trim();

		        UserDao userDao = new UserDao(); // Crie um objeto UserDao se já não existir

		        try {
		            User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(comboClientesPetInicial.getValue()); // Obtém o nome do cliente selecionado na ComboBox

		            if (clienteSelecionado != null) {
		                if (parteRelevante.equals("Matrícula 1")) {
		                 
		                    // Preencha as TextFields com os dados correspondentes a mat1
		                    txtMatPetInicial.setText(clienteSelecionado.getmat1());
		                    txtRefPetInicial.setText(clienteSelecionado.getref1());
		                    txtCargaHPetInicial.setText(clienteSelecionado.getcargh1());
		                    txtDataInicioPetInicial.setText(clienteSelecionado.getdatainicio1());
		                    txtCargoPetInicial.setText(clienteSelecionado.getcargo1());
		                    txtNivelPetInicial.setText(clienteSelecionado.getnivel1());
		                    txtTrienioPetInicial.setText(clienteSelecionado.gettrienio1());
		                    txtDataaposentadoriaPetInicial.setText(clienteSelecionado.getdataaposentadoria1());
		                } else if (parteRelevante.equals("Matrícula 2")) {
		               
		                    // Preencha as TextFields com os dados correspondentes a mat2
		                    txtMatPetInicial.setText(clienteSelecionado.getmat2());
		                    txtRefPetInicial.setText(clienteSelecionado.getref2());
		                    txtCargaHPetInicial.setText(clienteSelecionado.getcargh2());
		                    txtDataInicioPetInicial.setText(clienteSelecionado.getdatainicio2());
		                    txtCargoPetInicial.setText(clienteSelecionado.getcargo2());
		                    txtNivelPetInicial.setText(clienteSelecionado.getnivel2());
		                    txtTrienioPetInicial.setText(clienteSelecionado.gettrienio2());
		                    txtDataaposentadoriaPetInicial.setText(clienteSelecionado.getdataaposentadoria2());
		                }
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    } else {
		        // Limpe as TextFields se nenhum item for selecionado
		        txtMatPetInicial.clear();
		        txtRefPetInicial.clear();
		        txtCargaHPetInicial.clear();
		        txtDataInicioPetInicial.clear();
		        txtCargoPetInicial.clear();
		        txtNivelPetInicial.clear();
		        txtTrienioPetInicial.clear();
		        txtDataaposentadoriaPetInicial.clear();
		    }
		}


	
		//TAB PETIÇÃO INTERCORRENTE
		
		public void atualizarListViewComDadosClientePetIntercorrente(String nomeCliente) {
		    try {
		        // Chame o método do ProcDao para obter a lista de processos para o cliente selecionado
		        List<Proc> processos = procDao.obterNumerosProcessoseMatriculaPorNomeCliente(nomeCliente);

		        // Limpe a ListView
		        listClientesPetInter.getItems().clear();

		        // Percorra a lista de processos e adicione as informações à ListView
		        for (Proc processo : processos) {
		            String numeroProcesso = processo.getnumeroprocesso();
		            String matricula = processo.getMatricula();
		            String info = numeroProcesso + " - " + matricula;
		            listClientesPetInter.getItems().add(info);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Lide com exceções SQL, se necessário
		    }
		}

		private void setupListViewClickHandler() {
		    listClientesPetInter.setOnMouseClicked(event -> {
		        // Verifique se um item da ListView foi clicado
		        Object itemSelecionado = listClientesPetInter.getSelectionModel().getSelectedItem();

		        if (itemSelecionado != null && itemSelecionado instanceof String) {
		            // Converta o item selecionado para uma String
		            String linhaSelecionada = (String) itemSelecionado;

			        // Divida a linha pelo caractere '-' para extrair a matrícula
			        String[] partes = linhaSelecionada.split(" - ");

			        // Verifique se a linha foi dividida corretamente (deve haver pelo menos duas partes)
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
		                         
		                            	txtNomePetInter.setText(user.getnome());
		                                txtRefPetInter.setText(user.getref1());
		                                txtCargaHPetInter.setText(user.getcargh1());
		                                txtCargoPetInter.setText(user.getcargo1());
		                                txtNivelPetInter.setText(user.getnivel1());
		                                labelDataInicio.setText(user.getdatainicio1());
		                                
		                            } else if (matricula.equals(user.getmat2())) {
		                                // Matrícula corresponde a mat2
		                           
		                            	txtNomePetInter.setText(user.getnome());
		                                txtRefPetInter.setText(user.getref2());
		                                txtCargaHPetInter.setText(user.getcargh2());
		                                txtCargoPetInter.setText(user.getcargo2());
		                                txtNivelPetInter.setText(user.getnivel2());
		                                labelDataInicio.setText(user.getdatainicio2());
		                            }
		                        }
		                    } else {
		                        // Matrícula não existe na base de dados, faça o que for necessário aqui
		                    }
		                } catch (SQLException e) {
		                    // Trate exceções de SQL aqui
		                }
			         // Obtenha o número do processo da txtNumeroProcInter
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
	                                               //TAB Documentos Administrativos
		@FXML
		private ChoiceBox<String> choiceBoxDocumento;
		@FXML
		private Button btnGerarDocumentoSelecionado;
		private Map<String, String> documentosModelos = new HashMap<>();

		@FXML
		private void gerarDocumentoSelecionado() {
		    String nomeDocumentoSelecionado = choiceBoxDocumento.getValue();

		    if (nomeDocumentoSelecionado != null) {
		        String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\documentos\\" + nomeDocumentoSelecionado + ".docx";
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
		    String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\" + nomeDocumento + " - " + nome + "_gerado.docx";
		    try (FileOutputStream out = new FileOutputStream(outputPath)) {
		        doc.write(out);
		    
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

	   
	

		
		
		
		
	
		
	
	
	
	
	
	
													//TAB PETIÇÕES INICIAIS
	
															//NE
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
		        String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\Iniciais\\" + nomeDocumentoSelecionado;
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
		    String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\" + nomeDocumento + " - " + nome + " - " + matricula + "_gerado.docx";
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
		
		
		
											//PETIÇÃO INICIAL INTERNIVEIS
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
		        String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\Iniciais\\" + nomeDocumentoSelecionado;
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
		    String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\" + nomeDocumento + " - " + nome + " - " + matricula + "_gerado.docx";
		    try (FileOutputStream out = new FileOutputStream(outputPath)) {
		        doc.write(out);
		        exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		    } catch (Exception e) {
		        e.printStackTrace();
		        exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		    }
		}
		
		
		
		
												//INICIAL PISO
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
		    double vencimentoHojeD= Double.parseDouble(txtValorHoje.getText());
		    double trienioHojeD= Double.parseDouble(txtTrienioHoje.getText());
		    double trienioPisoD = Double.parseDouble(txtTrienioPiso.getText());
		    
		    double diferencaPisoHoje = (double)(vencimentoPisoD - vencimentoHojeD);
		    double totalPisoMaisTrienioPiso = (double) (vencimentoPisoD + trienioPisoD);
		    double diferencaTotal = (double)(vencimentoPisoD + trienioPisoD)-(vencimentoHojeD + trienioHojeD);
		   
		    
		    
		    
		    
		    if (nomeDocumentoSelecionado != null) {
		        String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\Iniciais\\" + nomeDocumentoSelecionado;
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
		        
		       
		        System.out.println("Valores preenchidos: " + " V3NCIM3NTOB4S3: " +  vencimentoPiso + "V3NCIM3NT0H0J3" + vencimentoHoje + "TR13N10H0J3" + trienioHoje + "P3RCH0J3" + percentualTrienio +  "D1F3RENP1S0" + String.valueOf(diferencaPisoHoje) +  "TR13N1OB4S3" + trienioPiso +  "T0T4LB4S3" + String.valueOf(totalPisoMaisTrienioPiso) + "D1F3RENT0T4L" + String.valueOf(diferencaTotal) );
		        
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
		    String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\" + nomeDocumento + " - " + nome + " - " + matricula + "_gerado.docx";
		    try (FileOutputStream out = new FileOutputStream(outputPath)) {
		        doc.write(out);
		        exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		    } catch (Exception e) {
		        e.printStackTrace();
		        exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		    }
		}
		
		
		
		
		
		
		
		
		
		
	
	
	                                       //TAB PETIÇÕES INTERCORRENTES
	     //GERAR PETIÇÃO INTERCORRENTE INTERNIVEIS
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
			    String valorNumerico = valorBrutoTexto
			        .replace("R$", "") // Remove "R$"
			        .replace(".", "")   // Remove os pontos de milhar, se houver
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
			int nivel2 = (int)(nivelInicial + 1);
			int nivel3 = (int)(nivel2 + 1);
			int nivel4 = (int)(nivel3 + 1);
			int nivel5 = (int)(nivel4 + 1);
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
				String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\PetInterniveis\\" + nomePeticaoInter +".docx";
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
		    String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\" + nomePeticaoInter + " - " +  nome + " - " + numeroprocesso +"_gerado.docx";
		    try (FileOutputStream out = new FileOutputStream(outputPath)) {
		        doc.write(out);
		        exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
		    } catch (Exception e) {
		        e.printStackTrace();
		        exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
		    }
		}
		
		
        //GERAR PETIÇÃO INTERCORRENTE NE
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
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\PetNE\\" + nomePeticaoNE +".docx";
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
	    String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\" + nomePeticaoNE + " - " +  nome + " - " + numeroprocesso +"_gerado.docx";
	    try (FileOutputStream out = new FileOutputStream(outputPath)) {
	        doc.write(out);
	        exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
	    }
	}
	
		

	//GERAR PETIÇÃO INTERCORRENTE PISO
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
		String valorBrutoTexto = txtValorPrincipal.getText();
		double valorBrutoDouble = 0.0; // Inicializa com zero, caso ocorra um erro na conversão
		try {
		    // Remove o "R$" e substitui a vírgula por ponto
		    String valorNumerico = valorBrutoTexto
		        .replace("R$", "") // Remove "R$"
		        .replace(".", "")   // Remove os pontos de milhar, se houver
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
		int nivel2 = (int)(nivelInicial + 1);
		int nivel3 = (int)(nivel2 + 1);
		int nivel4 = (int)(nivel3 + 1);
		int nivel5 = (int)(nivel4 + 1);
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
		
		
		String nomePeticaoPiso = choiceBoxPetInter.getValue();
		
		if (nomePeticaoPiso != null) {
			String templatePath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\PetPiso\\" + nomePeticaoPiso +".docx";
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
	    String outputPath = "C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\documentosGerados\\" + nomePeticaoPiso + " - " +  nome + " - " + numeroprocesso +"_gerado.docx";
	    try (FileOutputStream out = new FileOutputStream(outputPath)) {
	        doc.write(out);
	        exibirAviso("Documento Gerado", "A Petição foi gerada com sucesso.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        exibirAviso("Erro", "Ocorreu um erro ao gerar a petição.");
	    }
	}
	
	
	
		
	
	
	

}
