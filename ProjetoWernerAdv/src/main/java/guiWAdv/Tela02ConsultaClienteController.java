package guiWAdv;

import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Tela02ConsultaClienteController {

	private static final Logger logger = Logger.getLogger(com.WernerADV.Software.App.class.getName());
	private UserDao userDao = new UserDao();
	private ProcDao procDao = new ProcDao();
	private RecDao recDao = new RecDao();
	private HistoricoDao HistoricoDao = new HistoricoDao();
	
	
	// CONTROLES DE IDENTIFICAÇÃO TABS
	
		@FXML
	    private TabPane tabPaneClientes; 
	    public void setTabPane(TabPane tabPaneClientes) {
	        this.tabPaneClientes = tabPaneClientes;
	    }
	    
		@FXML
		private Tab tabEditarCliente;
		@FXML
		private Tab tabConsutarCliente;
	
	
	
	// NOMES VARIAVEIS
	//TAB CONSULTA
	@FXML
	private TextField txtBuscaNomeClienteConsulta;
	@FXML
	private Button btnBuscarClienteConsulta;
	@FXML
	private ListView<String> listClientesBuscaConsulta;
	@FXML
	private Label labelNomeCliente;
	@FXML
	private Label labelTelefone;
	@FXML
	private Label labelCPF;
	@FXML
	private Label labelRG;
	@FXML
	private Label labelNacionalidade;
	@FXML
	private Label labelEstadoCivil;
	@FXML
	private Label labelProfissao;
	@FXML
	private Label labelIdFuncional;
	@FXML
	private Label labelDataNascimento;
	@FXML
	private Label labelEndereco;
	@FXML
	private Label labelCidade;
	@FXML
	private Label labelEstado;
	@FXML
	private Label labelCep;
	@FXML
	private Label labelMat1;
	@FXML
	private Label labelRef1;
	@FXML
	private Label labelCargaH1;
	@FXML
	private Label labelDataInicio1;
	@FXML
	private Label labelCargo1;
	@FXML
	private Label labelNivel1;
	@FXML
	private Label labelTrienio1;
	@FXML
	private Label labelDataAposentadoria1;
	@FXML
	private Label labelMat2;
	@FXML
	private Label labelRef2;
	@FXML
	private Label labelCargaH2;
	@FXML
	private Label labelDataInicio2;
	@FXML
	private Label labelCargo2;
	@FXML
	private Label labelNivel2;
	@FXML
	private Label labelTrienio2;
	@FXML
	private Label labelDataAposentadoria2;
	@FXML
	private Button btnLimparTelaConsulta;
	@FXML
	private Button btnEditarClienteTab;
	
	
	//TAB EDITAR CLIENTE
	
	@FXML
	private TextField txtBuscaNomeClienteEdita;
	@FXML
	private Button btnBuscarClienteEdita;
	@FXML
	private ListView<String> listClientesBuscaEdita;
	@FXML
	private Label labelIdCliente;
	@FXML
	private TextField txtNomeClienteEdita;
	@FXML
	private TextField txtTelefone;
	@FXML
	private TextField txtCPF;
	@FXML
	private TextField txtRG;
	@FXML
	private TextField txtNacionaldiade;
	@FXML
	private TextField txtEstadoCivil;
	@FXML
	private TextField txtProfissao;
	@FXML
	private TextField txtIdFuncional;
	@FXML
	private TextField txtDataNascimento;
	@FXML
	private TextField txtEndereco;
	@FXML
	private TextField txtCidade;
	@FXML
	private TextField txtEstado;
	@FXML
	private TextField txtCEP;
	@FXML
	private TextField txtMat1;
	@FXML
	private TextField txtRef1;
	@FXML
	private TextField txtCargah1;
	@FXML
	private TextField txtDataInicio1;
	@FXML
	private TextField txtCargo1;
	@FXML
	private TextField txtNivel1;
	@FXML
	private TextField txtTrienio1;
	@FXML
	private TextField txtDataAposentadoria1;
	@FXML
	private TextField txtMat2;
	@FXML
	private TextField txtRef2;
	@FXML
	private TextField txtCargah2;
	@FXML
	private TextField txtDataInicio2;
	@FXML
	private TextField txtCargo2;
	@FXML
	private TextField txtNivel2;
	@FXML
	private TextField txtTrienio2;
	@FXML
	private TextField txtDataAposentadoria2;
	@FXML
	private Button btnLimparTelaEditar;
	@FXML
	private Button btnVoltardaTabEditar;
	@FXML
	private Button btnConcluirEditarCliente;
	
	
	
	
	// COMANDO INITIALIZE COMBOBOX
    
		public void initializeComboBox() {

		    UserDao userDao = new UserDao();
		   	}
	
	
	// AÇÕES DE LIMPEZA DE TELA
		
		@FXML
		private void btnLimparTelaConsulta() {
		    // Limpe todos os campos de texto e labels
			
			labelNomeCliente.setText("");
			labelTelefone.setText("");
			labelCPF.setText("");
			labelRG.setText("");
			labelNacionalidade.setText("");
			labelEstadoCivil.setText("");
			labelProfissao.setText("");
			labelIdFuncional.setText("");
			labelDataNascimento.setText("");
			labelEndereco.setText("");
			labelCidade.setText("");
			labelEstado.setText("");
			labelCep.setText("");
			labelMat1.setText("");
			labelRef1.setText("");
			labelCargaH1.setText("");
			labelDataInicio1.setText("");
			labelCargo1.setText("");
			labelNivel1.setText("");
			labelTrienio1.setText("");
			labelDataAposentadoria1.setText("");
			labelMat2.setText("");
			labelRef2.setText("");
			labelCargaH2.setText("");
			labelDataInicio2.setText("");
			labelCargo2.setText("");
			labelNivel2.setText("");
			labelTrienio2.setText("");
			labelDataAposentadoria2.setText("");
			txtBuscaNomeClienteConsulta.clear();
			listClientesBuscaConsulta.getSelectionModel().clearSelection();
			listClientesBuscaConsulta.getItems().clear();
		   
		}
	
		@FXML
		private void btnLimparTelaEditar() {
		    // Limpe todos os campos de texto e labels
			
			labelIdCliente.setText("");
			txtBuscaNomeClienteEdita.clear();
			listClientesBuscaEdita.getSelectionModel().clearSelection();
			listClientesBuscaEdita.getItems().clear();
			txtNomeClienteEdita.clear();
			txtTelefone.clear();
			txtCPF.clear();
			txtRG.clear();
			txtNacionaldiade.clear();
			txtEstadoCivil.clear();
			txtProfissao.clear();
			txtIdFuncional.clear();
			txtDataNascimento.clear();
			txtEndereco.clear();
			txtCidade.clear();
			txtEstado.clear();
			txtCEP.clear();
			txtMat1.clear();
			txtRef1.clear();
			txtCargah1.clear();
			txtDataInicio1.clear();
			txtCargo1.clear();
			txtNivel1.clear();
			txtTrienio1.clear();
			txtDataAposentadoria1.clear();
			txtMat2.clear();
			txtRef2.clear();
			txtCargah2.clear();
			txtDataInicio2.clear();
			txtCargo2.clear();
			txtNivel2.clear();
			txtTrienio2.clear();
			txtDataAposentadoria2.clear();
		}
	
	
		// COMANDO INITIALIZE
		
		@FXML
		public void initialize() {
			
			
			
			
			tabPaneClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			        if (newValue == tabEditarCliente) {
			            // Código para a guia "Editar Cliente"
			        } else if (newValue == tabConsutarCliente) {
			            // Código para a guia "Consulta Processo"
			        }
			    });
					
			// ADICIONANDO COMANDO AOS BOTÕES PARA ALTERNAR ENTRE AS TABS
			btnEditarClienteTab.setOnAction(event -> {
				tabPaneClientes.getSelectionModel().select(tabEditarCliente);
					});
				
			btnVoltardaTabEditar.setOnAction(event -> {
				tabPaneClientes.getSelectionModel().select(tabConsutarCliente);
			});
			
			listClientesBuscaConsulta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			    if (newValue != null) {
			        // Quando um cliente for selecionado, chame um método para preencher os campos
			        preencherCamposComDadosDoClienteSelecionado(newValue);
			    }
			});
			
			
			listClientesBuscaEdita.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			    if (newValue != null) {
			        // Quando um cliente for selecionado, chame um método para preencher os campos
			    	preencherCamposComDadosDoClienteSelecionadoEditar(newValue);
			    }
			});
			
			
							
		//ENCERRANDO O MÉTODO INITIALIZE	
		}
	
		@FXML
		private void onbtnBuscarClienteConsulta(ActionEvent event) throws SQLException {
		    
		    UserDao userDao = new UserDao();
		    String letrasIniciais = txtBuscaNomeClienteConsulta.getText();
		    try {
		        List<User> clientes = userDao.buscaClientesPorLetrasIniciaisDoNome(letrasIniciais);

		        if (!clientes.isEmpty()) {
		            List<String> resultados = new ArrayList<>();
		            for (User cliente : clientes) {
		                resultados.add(cliente.getnome());
		            }
		            // Limpe a ListView antes de exibir os novos resultados
		            listClientesBuscaConsulta.getItems().clear();
		            listClientesBuscaConsulta.getItems().addAll(resultados);
		        } else {
		           
		            // Limpe a ListView se nenhum resultado for encontrado
		            listClientesBuscaConsulta.getItems().clear();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	
	
		private void preencherCamposComDadosDoClienteSelecionado(String nomeClienteSelecionado) {
		    // Use o UserDao para buscar os detalhes do cliente com base no nome
		    UserDao userDao = new UserDao();
		    try {
		        User clienteSelecionado = userDao.buscaClientePeloNome(nomeClienteSelecionado);

		        if (clienteSelecionado != null) {
		            // Preencha os campos Label com os detalhes do cliente
		            labelNomeCliente.setText(clienteSelecionado.getnome());
		            labelTelefone.setText(clienteSelecionado.gettelefone());
		            labelCPF.setText(clienteSelecionado.getcpf());
		            labelRG.setText(clienteSelecionado.getrg());
		            labelNacionalidade.setText(clienteSelecionado.getnacionalidade());
		            labelEstadoCivil.setText(clienteSelecionado.getestadocivil());
		            labelProfissao.setText(clienteSelecionado.getprofissao());
		            labelIdFuncional.setText(clienteSelecionado.getidfuncional());
		            labelDataNascimento.setText(clienteSelecionado.getdatanascimento());
		            labelEndereco.setText(clienteSelecionado.getendereco());
		            labelCidade.setText(clienteSelecionado.getcidade());
		            labelEstado.setText(clienteSelecionado.getestado());
		            labelCep.setText(clienteSelecionado.getcep());
		            labelMat1.setText(clienteSelecionado.getmat1());
		            labelRef1.setText(clienteSelecionado.getref1());
		            labelCargaH1.setText(clienteSelecionado.getcargh1());
		            labelDataInicio1.setText(clienteSelecionado.getdatainicio1());
		            labelCargo1.setText(clienteSelecionado.getcargo1());
		            labelNivel1.setText(clienteSelecionado.getnivel1());
		            labelTrienio1.setText(clienteSelecionado.gettrienio1());
		            labelDataAposentadoria1.setText(clienteSelecionado.getdataaposentadoria1());
		            labelMat2.setText(clienteSelecionado.getmat2());
		            labelRef2.setText(clienteSelecionado.getref2());
		            labelCargaH2.setText(clienteSelecionado.getcargh2());
		            labelDataInicio2.setText(clienteSelecionado.getdatainicio2());
		            labelCargo2.setText(clienteSelecionado.getcargo2());
		            labelNivel2.setText(clienteSelecionado.getnivel2());
		            labelTrienio2.setText(clienteSelecionado.gettrienio2());
		            labelDataAposentadoria2.setText(clienteSelecionado.getdataaposentadoria2());
		        } else {
		            // Limpe os campos Label se nenhum cliente for encontrado
		            labelNomeCliente.setText("");
		            labelTelefone.setText("");
		            labelCPF.setText("");
		            labelRG.setText("");
		            labelNacionalidade.setText("");
		            labelEstadoCivil.setText("");
		            labelProfissao.setText("");
		            labelIdFuncional.setText("");
		            labelDataNascimento.setText("");
		            labelEndereco.setText("");
		            labelCidade.setText("");
		            labelEstado.setText("");
		            labelCep.setText("");
		            labelMat1.setText("");
		            labelRef1.setText("");
		            labelCargaH1.setText("");
		            labelDataInicio1.setText("");
		            labelCargo1.setText("");
		            labelNivel1.setText("");
		            labelTrienio1.setText("");
		            labelDataAposentadoria1.setText("");
		            labelMat2.setText("");
		            labelRef2.setText("");
		            labelCargaH2.setText("");
		            labelDataInicio2.setText("");
		            labelCargo2.setText("");
		            labelNivel2.setText("");
		            labelTrienio2.setText("");
		            labelDataAposentadoria2.setText("");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	
	
		@FXML
		private void onbtnBuscarClienteEdita(ActionEvent event) throws SQLException {
		   
		    UserDao userDao = new UserDao();
		    String letrasIniciais = txtBuscaNomeClienteEdita.getText();
		    try {
		        List<User> clientes = userDao.buscaClientesPorLetrasIniciaisDoNome(letrasIniciais);

		        if (!clientes.isEmpty()) {
		            List<String> resultados = new ArrayList<>();
		            for (User cliente : clientes) {
		                resultados.add(cliente.getnome());
		            }
		            // Limpe a ListView antes de exibir os novos resultados
		            listClientesBuscaEdita.getItems().clear();
		            listClientesBuscaEdita.getItems().addAll(resultados);
		        } else {
		           
		            // Limpe a ListView se nenhum resultado for encontrado
		            listClientesBuscaEdita.getItems().clear();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	
		private void preencherCamposComDadosDoClienteSelecionadoEditar(String nomeClienteSelecionado) {
		    // Use o UserDao para buscar os detalhes do cliente com base no nome
		    UserDao userDao = new UserDao();
		    try {
		        User clienteSelecionadoEditar = userDao.buscaClientePeloNomeTabEditar(nomeClienteSelecionado);

		        User clienteSelecionado;
				if (clienteSelecionadoEditar != null) {
		            // Preencha os campos Label com os detalhes do cliente
					labelIdCliente.setText(Integer.toString(clienteSelecionadoEditar.getId()));
					txtNomeClienteEdita.setText(clienteSelecionadoEditar.getnome());
					txtTelefone.setText(clienteSelecionadoEditar.gettelefone());
					txtCPF.setText(clienteSelecionadoEditar.getcpf());
					txtRG.setText(clienteSelecionadoEditar.getrg());
					txtNacionaldiade.setText(clienteSelecionadoEditar.getnacionalidade());
					txtEstadoCivil.setText(clienteSelecionadoEditar.getestadocivil());
					txtProfissao.setText(clienteSelecionadoEditar.getprofissao());
					txtIdFuncional.setText(clienteSelecionadoEditar.getidfuncional());
					txtDataNascimento.setText(clienteSelecionadoEditar.getdatanascimento());
					txtEndereco.setText(clienteSelecionadoEditar.getendereco());
		            txtCidade.setText(clienteSelecionadoEditar.getcidade());
		            txtEstado.setText(clienteSelecionadoEditar.getestado());
		            txtCEP.setText(clienteSelecionadoEditar.getcep());
		            txtMat1.setText(clienteSelecionadoEditar.getmat1());
		            txtRef1.setText(clienteSelecionadoEditar.getref1());
		            txtCargah1.setText(clienteSelecionadoEditar.getcargh1());
		            txtDataInicio1.setText(clienteSelecionadoEditar.getdatainicio1());
		            txtCargo1.setText(clienteSelecionadoEditar.getcargo1());
		            txtNivel1.setText(clienteSelecionadoEditar.getnivel1());
		            txtTrienio1.setText(clienteSelecionadoEditar.gettrienio1());
		            txtDataAposentadoria1.setText(clienteSelecionadoEditar.getdataaposentadoria1());
		            txtMat2.setText(clienteSelecionadoEditar.getmat2());
		            txtRef2.setText(clienteSelecionadoEditar.getref2());
		            txtCargah2.setText(clienteSelecionadoEditar.getcargh2());
		            txtDataInicio2.setText(clienteSelecionadoEditar.getdatainicio2());
		            txtCargo2.setText(clienteSelecionadoEditar.getcargo2());
		            txtNivel2.setText(clienteSelecionadoEditar.getnivel2());
		            txtTrienio2.setText(clienteSelecionadoEditar.gettrienio2());
		            txtDataAposentadoria2.setText(clienteSelecionadoEditar.getdataaposentadoria2());
		        } else {
		            // Limpe os campos Label se nenhum cliente for encontrado
		        	labelIdCliente.setText("");
		        	txtNomeClienteEdita.clear();
		        	txtTelefone.clear();
		        	txtCPF.clear();
		        	txtRG.clear();
		        	txtNacionaldiade.clear();
		        	txtEstadoCivil.clear();
		        	txtProfissao.clear();
		        	txtIdFuncional.clear();
		        	txtDataNascimento.clear();
		        	txtEndereco.clear();
		        	txtCidade.clear();
		        	txtEstado.clear();
		            txtCEP.clear();
		            txtMat1.clear();
		            txtRef1.clear();
		            txtCargah1.clear();
		            txtDataInicio1.clear();
		            txtCargo1.clear();
		            txtNivel1.clear();
		            txtTrienio1.clear();
		            txtDataAposentadoria1.clear();
		            txtMat2.clear();
		            txtRef2.clear();
		            txtCargah2.clear();
		            txtDataInicio2.clear();
		            txtCargo2.clear();
		            txtNivel2.clear();
		            txtTrienio2.clear();
		            txtDataAposentadoria2.clear();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	
		
		
		@FXML
		private void handleEditarMovimentoConcluirEditar() {
		    // Obtenha os valores das TextFields
		   
		    String novonome = txtNomeClienteEdita.getText();
		    String novotelefone = txtTelefone.getText();
		    String novocpf = txtCPF.getText();
		    String novorg = txtRG.getText();
		    String novonacionalidade = txtNacionaldiade.getText();
		    String novoestadoCivil = txtEstadoCivil.getText();
		    String novoprofissao = txtProfissao.getText();
		    String novoidFuncional = txtIdFuncional.getText();
		    String novodataNascimento = txtDataNascimento.getText();
		    String novoendereco = txtEndereco.getText();
		    String novocidade = txtCidade.getText();
		    String novoestado = txtEstado.getText();
		    String novocep = txtCEP.getText();
		    String novomat1 = txtMat1.getText();
		    String novoref1 = txtRef1.getText();
		    String novocargah1 = txtCargah1.getText();
		    String novodataInicio1 = txtDataInicio1.getText();
		    String novocargo1 = txtCargo1.getText();
		    String novonivel1 = txtNivel1.getText();
		    String novotrienio1 = txtTrienio1.getText();
		    String novodataAposentadoria1 = txtDataAposentadoria1.getText();
		    String novomat2 = txtMat2.getText();
		    String novoref2 = txtRef2.getText();
		    String novocargah2 = txtCargah2.getText();
		    String novodataInicio2 = txtDataInicio2.getText();
		    String novocargo2 = txtCargo2.getText();
		    String novonivel2 = txtNivel2.getText();
		    String novotrienio2 = txtTrienio2.getText();
		    String novodataAposentadoria2 = txtDataAposentadoria2.getText();

		    
		    int id = Integer.parseInt(labelIdCliente.getText());
		    
		    
		    // Chame o método de atualização do UserDao
		    	        boolean atualizacaoBemSucedida = userDao.atualizarCliente(id, novonome, novotelefone,novocpf, novorg,novonacionalidade, novoestadoCivil, novoprofissao,novoidFuncional,
		        		novodataNascimento, novoendereco, novocidade, novoestado, novocep, novomat1, novoref1, novocargah1, novodataInicio1, novocargo1, novonivel1, novotrienio1,
		        		novodataAposentadoria1, novomat2, novoref2, novocargah2, novodataInicio2, novocargo2, novonivel2, novotrienio2, novodataAposentadoria2);

		        if (atualizacaoBemSucedida) {
			        // Atualização bem-sucedida, você pode mostrar uma mensagem de sucesso
		        	this.alert("Save", "Successful!", AlertType.INFORMATION);
			        
			    }
		    }


		private void alert(String title, String message, AlertType alertType) {
		    Platform.runLater(() -> {
		        Alert alert = new Alert(alertType);
		        alert.setTitle("Werner Advogados");
		        alert.setHeaderText(title);
		        alert.setContentText(message);


		        alert.showAndWait();
		    });
		}
		 
		
		
		
}
		

