package guiWAdv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Tela03ConsultaProcessoController {

	private static final Logger logger = Logger.getLogger(com.WernerADV.Software.App.class.getName());
	private UserDao userDao = new UserDao();
	private ProcDao procDao = new ProcDao();
	private RecDao recDao = new RecDao();
	private HistoricoDao HistoricoDao = new HistoricoDao();
	private AndamentoRecDao andamentoRecDao;
	
	
	private void alert(String title, String message, AlertType alertType) {
	    Platform.runLater(() -> {
	        Alert alert = new Alert(alertType);
	        alert.setTitle("Werner Advogados");
	        alert.setHeaderText(title);
	        alert.setContentText(message);

	        alert.showAndWait();
	    });
	}
	
	
	// CONTROLES DE IDENTIFICAÇÃO FXML
	//TABS e TABPANE
	@FXML
    private TabPane tabPaneEdit; 
    public void setTabPane(TabPane tabPaneEdit) {
        this.tabPaneEdit = tabPaneEdit;
    }
    @FXML
	private Tab tabEditarProcesso;
	@FXML
	private Tab tabConsutaProcesso;
	
	
	

    //TAB CONSULTA PROCESSO
    @FXML	
	private ListView<String> listaResultados;

	@FXML
	private Button btnLimparTela;
	@FXML
	private void limparTela() {
	    // Limpe todos os campos de texto e labels
		ResultadoNomedoCliente.setText("");
		ResultadoNumerodoProcesso.setText("");
		ResultadoComarcadoProcesso.setText("");
		ResultadoVaradoProcesso.setText("");
	    ResultadoTemadoProcesso.setText("");
	    ResultadoValordoProcesso.setText("");
	    ResultadoValordosHonorarios.setText("");
	    ResultadoStatus.setText("");
	    ResultadoMatricula.setText("");
	    
	    // Limpe a seleção na ComboBox e na ListView, se aplicável
	    clienteComboBox1.getSelectionModel().clearSelection();
	    listaResultados.getSelectionModel().clearSelection();
	    listaResultados.getItems().clear();
	    ResultadosMovimentos.clear();
	    ResultadosMovimentos.setText(""); 
	}
		
					// BOTOES E AREAS DA TELA - TAB CONSULTA PROCESSO
				@FXML
				private Label NomeCombodoCliente; 
				@FXML
				private Label NomedoCliente;
				@FXML
				private Label ResultadoNomedoCliente;
				@FXML 
				private Label ResultadoMatricula;
				@FXML
				private Label NumerodoProcesso;
				@FXML
				private Label ResultadoNumerodoProcesso;
				@FXML
				private Label ResultadoStatus;
				@FXML
				private Label ComarcadoProcesso;
				@FXML
				private Label ResultadoComarcadoProcesso;
				@FXML
				private Label VaradoProcesso;
				@FXML
				private Label ResultadoVaradoProcesso;
				@FXML
				private Label TemadoProcesso;
				@FXML
				private Label ResultadoTemadoProcesso;
				@FXML
				private Label ValordoProcesso;
				@FXML
				private Label ResultadoValordoProcesso;
				@FXML
				private Label ValordosHonorarios;
				@FXML
				private Label ResultadoValordosHonorarios;
				@FXML
				private Label Movimentos;
				@FXML
				private ComboBox ClienteEmConsulta;
				@FXML
				private ListView ResultadosProcessos;
				@FXML
				private TextArea ResultadosMovimentos;
				@FXML
				private TextField txtDataMovimento;
				@FXML
				private TextField txtDescricaoMovimento;
				@FXML
				private Button btnIserirMovimento;
				@FXML
				private Button btnExibirMovimento;
				@FXML
				private Button btnConsultarRec;
				
				
				
				
				// LAYOUT PARA TELA DE EDIÇÃO - TAB EDIÇÃO DOS DADOS DO PROCESSO - EDITAR PROCESSO
				@FXML 
				private ListView<String> listaResultados2Editar;
				@FXML
				private Label ResultadoNomedoCliente2;
				@FXML
				private Label IdHistorico;
				@FXML 
				private TextField txtNumeroProc;
				@FXML 
				private TextField txtComarcaProc;
				@FXML 
				private TextField txtVaraProc;
				@FXML 
				private TextField txtTemaProc;
				@FXML 
				private TextField txtValorProc;
				@FXML 
				private TextField txtStatus;
				@FXML
				private TextField txtMatricula;
				@FXML 
				private TextField txtHonoProc;
				@FXML
				private ListView<String> ResultadosMovimentosEditar;
				@FXML
				private TextField txtDataMovimentoEditar;
				@FXML
				private TextField txtDescricaoMovimentoEditar;
				@FXML
				private Label labelIdProcEditar;
				@FXML
				private Button btnEditarMovimento;
				@FXML
				private Button btnExibirMovimentoEditar;
				@FXML
				private Button btnLimparTela1;
				
				@FXML
				private void limparTela1() {
				    // Limpe todos os campos de texto e labels
					ResultadoNomedoCliente.setText("");
					txtNumeroProc.clear();
					txtComarcaProc.clear();
					txtVaraProc.clear();
					txtTemaProc.clear();
					txtValorProc.clear();
					txtHonoProc.clear();
					txtStatus.clear();
					txtMatricula.clear();
				    // Limpe a seleção na ComboBox e na ListView, se aplicável
					clienteComboBox2Editar.getSelectionModel().clearSelection();
				    listaResultados2Editar.getSelectionModel().clearSelection();
				    listaResultados2Editar.getItems().clear();
				    ResultadosMovimentosEditar.getSelectionModel().clearSelection();
				    ResultadosMovimentosEditar.getItems().clear();
				}
				
				@FXML
				private Button VoltarEditarProc;
				@FXML
				private Button btnConcluirEdicao;
				@FXML
				private Button btnExcluirProcesso;
	
	
				
				
											// COMBOBOX - COMANDOS E CONTROLE DAS COMBOBOX
	@FXML
	private ComboBox<String> clienteComboBox1;
	@FXML
	private ComboBox<String> clienteComboBox2Editar;

    
                                               // COMANDO INITIALIZE COMBOBOX
    
	public void initializeComboBox() {
	    UserDao userDao = new UserDao();
	   	}
	
	
	
	
	
	
													// COMANDO INITIALIZE
	
	@FXML
	public void initialize() {
		 tabPaneEdit.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        if (newValue == tabEditarProcesso) {
		            // Código para a guia "Editar Processo"
		        } else if (newValue == tabConsutaProcesso) {
		            // Código para a guia "Consulta Processo"
		        }
		    });
		 
		 andamentoRecDao = new AndamentoRecDao();
			 
				
		// ADICIONANDO COMANDO AOS BOTÕES PARA ALTERNAR ENTRE AS TABS
		btnEditarProcesso.setOnAction(event -> {
			tabPaneEdit.getSelectionModel().select(tabEditarProcesso);
				});
			
		VoltarEditarProc.setOnAction(event -> {
			tabPaneEdit.getSelectionModel().select(tabConsutaProcesso);
		});
		
		btnConsultarRec.setOnAction(event -> {
			tabPaneEdit.getSelectionModel().select(tabConsultaRec);
		});
		
		
		btnVoltarPagInicial.setOnAction(event -> {
			tabPaneEdit.getSelectionModel().select(tabConsutaProcesso);
		});
		
		btnEditarRec.setOnAction(event -> {
			tabPaneEdit.getSelectionModel().select(tabEditaRec);
		});
		btnVoltarConsultarRec.setOnAction(event -> {
			tabPaneEdit.getSelectionModel().select(tabConsultaRec);
		});
		
			
		 // Adicione um ouvinte de ação ao botão de limpar
	   //BOTÃO TAB CONSULTA PROCESSO
		btnLimparTela.setOnAction(event -> limparTela());
		//BOTÃO TAB EDITA PROCESSO
	    btnLimparTela1.setOnAction(event -> limparTela1());
	    //BOTÃO TAB CONSULTA RECURSO
	    btnLimpaTelaConsultaRec.setOnAction(event -> limparTela2());
	    //BOTÃO TAB EDITA RECURSO
	    btnLimpaTelaEditaRec.setOnAction(event -> limparTela3());
	    
	    
	    // Adicione um ouvinte de ação ao botão de inserir movimento
	    btnIserirMovimento.setOnAction(event -> inserirMovimento());
    
	    
	    							// Inicialize a ComboBox com os nomes dos clientes
	    
	    //COMBOBOX1 - TAB CONSULTA PROCESSO
        try {
            List<String> nomesClientes = procDao.obterNomesClientes();

            Collections.sort(nomesClientes);
            // Adicione os nomes dos clientes à ComboBox
            for (String nomeCliente : nomesClientes) {
                if (!clienteComboBox1.getItems().contains(nomeCliente)) {
                    clienteComboBox1.getItems().add(nomeCliente);
                  }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            // Adicione um ouvinte de seleção à ComboBox
        clienteComboBox1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Obter os números dos processos para o cliente selecionado
                try {
                    List<Proc> processos = procDao.obterNumerosProcessosPorNomeCliente(newValue);

                    // Limpar a ListView
                    listaResultados.getItems().clear();

                    // Adicionar os números dos processos à ListView
                    for (Proc processo : processos) {
                        listaResultados.getItems().add(processo.getnumeroprocesso());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        // ADICIONA OUVINTE À LISTVIEW DA TAB CONSULTA PROCESSO
        listaResultados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                atualizarLabelsComDadosDoProcesso(newValue);
                
                try {
                    // Obtenha o processo selecionado
                    Proc processoSelecionado = procDao.buscarProcessoPorNumero(newValue);

                    // Se o processo for encontrado, atualize a ListView
                    if (processoSelecionado != null) {
                        ResultadosMovimentos.clear();

                        // Obtenha o histórico para o processo selecionado
                        List<Historico> historico = processoSelecionado.getHistorico();

                        // Adicione todas as datas e descrições ao TextArea
                        for (Historico hist : historico) {
                            String dataEDescricao = hist.getData() + ": " + hist.getDescricao();
                            ResultadosMovimentos.appendText(dataEDescricao + "\n");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
	try {
	    List<String> nomesClientes = procDao.obterNomesClientes();
	  
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	
										//TAB EDITAR PROCESSO
	// Inicialize a ComboBox com os nomes dos clientes tabEditar
    try {
        List<String> nomesClientes2 = procDao.obterNomesClientes2();
        Collections.sort(nomesClientes2);
        // Adicione os nomes dos clientes à ComboBox clienteComboBox2Editar
    for (String nomeCliente : nomesClientes2) {
        if (!clienteComboBox2Editar.getItems().contains(nomeCliente)) {
            clienteComboBox2Editar.getItems().add(nomeCliente);
         
        }  
    }
} catch (SQLException e) {
    e.printStackTrace();
}

     // Adicione um ouvinte de seleção à ComboBox clienteComboBox2Editar
	clienteComboBox2Editar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    if (newValue != null) {
	        // Obter os números dos processos para o cliente selecionado
	        try {
	            List<Proc> processos2 = procDao.obterNumerosProcessosPorNomeCliente2(newValue);

	            // Limpar a ListView listaResultados2Editar
	            listaResultados2Editar.getItems().clear();

	            // Adicionar os números dos processos à ListView listaResultados2Editar
	            for (Proc processo2 : processos2) {
	                listaResultados2Editar.getItems().add(processo2.getnumeroprocesso());
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	});
	
	listaResultados2Editar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            try {
				atualizarCamposComDadosDoCliente(newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            try {
                // Obtenha o processo selecionado
                Proc processoSelecionado = procDao.buscarProcessoPorNumero(newValue);

                // Se o processo for encontrado, atualize a ListView
                if (processoSelecionado != null) {
                	ResultadosMovimentosEditar.getItems().clear();

                    // Obtenha o histórico para o processo selecionado
                    List<Historico> historico = processoSelecionado.getHistorico();

                    // Adicione todas as datas e descrições ao TextArea
                    for (Historico hist : historico) {
                        String dataEDescricao = hist.getData() + ": " + hist.getDescricao();
                        ResultadosMovimentosEditar.getItems().add(dataEDescricao);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    });
try {
    List<String> nomesClientes = procDao.obterNomesClientes();
  
} catch (SQLException e) {
    e.printStackTrace();
}


//Adicione um ouvinte de seleção à ListView
ResultadosMovimentosEditar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	if (newValue != null) {
        System.out.println("Item selecionado na ListView: " + newValue); // Adicione esta linha para verificar se um item é selecionado
        preencherCamposComHistoricoSelecionado(newValue);
    }
});


listClienteRec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        try {
			onSelecionarClienteRec();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
});
listClienteRec1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        try {
			onSelecionarRecurso();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
});



			//TAB EDITAR RECURSO

txtBuscaClienteEditRec.textProperty().addListener((observable, oldValue, newValue) -> {
    buscarNomesClientesEdita();
});

// Ouvinte para atualizar os campos com dados do cliente quando um cliente é selecionado
listClienteEditRec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    try {
        onSelecionarClienteRecEdita();
    } catch (SQLException e) {
        e.printStackTrace();
    }
});

// Ouvinte para atualizar os campos com dados do cliente quando um número de recurso é selecionado
listClienteEditRec1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        try {
            atualizarCamposComDadosDoCliente(newValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
});


listClienteEditRec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        try {
            int idRec = recDao.obterIdRecPorNomeClienteEdit(newValue);

            if (idRec != -1) {
                List<String> andamentos = andamentoRecDao.obterAndamentosPorIdRecEdit(idRec);

                listAndamentoRecEdit.getItems().clear();
                listAndamentoRecEdit.getItems().addAll(andamentos);

                if (!andamentos.isEmpty()) {
                    String primeiroAndamento = andamentos.get(0);
                    String[] partesAndamento = primeiroAndamento.split(" - ");

                    if (partesAndamento.length == 2) {
                        String dataRecurso = partesAndamento[0];
                        String descricaoRecurso = partesAndamento[1];

                        txtDataAndamentoEdit.setText(dataRecurso);
                        txtDescricaoAndamentoEdit.setText(descricaoRecurso);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Lide com exceções SQL, se necessário
        }
    }
});


//Adicione um ouvinte de seleção à ListView
listAndamentoRecEdit.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	
	if (newValue != null) {
		
        // Quando um item for selecionado, chame um método para preencher os campos
		preencherCamposComHistoricoSelecionadoTelaEditRec(newValue);
    }
});









 											//ARQUIVO
btnArquivaRec.setOnAction(e -> {
    if (cboxArquivoRec.isSelected()) {
        String numeroRecurso = labelNumeroRecurso.getText(); // Obtém o número do recurso da label

        RecDao recDao = new RecDao();
        try {
            int idRecParaArquivar = recDao.obterIdRecPorNumeroRecurso(numeroRecurso);

            if (idRecParaArquivar != -1) {
                recDao.moverParaRecArquivo(idRecParaArquivar);
                // Atualize sua interface ou faça qualquer outra ação necessária após arquivar o recurso

                // Exiba uma mensagem de sucesso
                exibirAlerta("Arquivo realizado com sucesso!", AlertType.INFORMATION);
            } else {
                // Trate o caso em que o Idrec não foi encontrado
                exibirAlerta("ID do Recurso não encontrado.", AlertType.WARNING);
            }
        } catch (SQLException exception) {
            // Trate a exceção, se necessário
            exception.printStackTrace();
            exibirAlerta("Erro ao arquivar o recurso.", AlertType.ERROR);
        }
    } else {
        // Exiba uma mensagem indicando que a checkbox não está marcada
        exibirAlerta("Por favor, marque a opção para arquivar o recurso.", AlertType.WARNING);
    }
});



     //FECHA INITIALIZE
	}
	private void exibirAlerta(String mensagem, AlertType tipo) {
	    Alert alerta = new Alert(tipo);
	    alerta.setTitle("Alerta");
	    alerta.setHeaderText(null);
	    alerta.setContentText(mensagem);
	    alerta.showAndWait();
	}
	
	
													//COMANDO PARA ATUALIZAR COMBOBOX
	public void atualizarComboBox(List<String> numerosProcessos) {
	    System.out.println("Chamando atualizarComboBox com " + numerosProcessos.size() + " elementos.");
	    clienteComboBox1.getItems().clear();
	    clienteComboBox1.getItems().addAll(numerosProcessos);
	}

	public void atualizarComboBox2(List<String> numerosProcessos) {
		clienteComboBox2Editar.getItems().clear();
		clienteComboBox2Editar.getItems().addAll(numerosProcessos);
    }
	
	
														// COMANDO PARA PREENCHER INFO ANDAMENTOS(HISTORICO) NA TAB EDITAR PROCESSO
	private void preencherCamposComHistoricoSelecionado(String linhaSelecionada) {
		
	    // Divida a linha selecionada para obter data e descrição
	    String[] partes = linhaSelecionada.split(": ");
	    String data = partes[0];
	    String descricao = partes[1];

	    // Obtenha o historico com base na data e descrição
	    Historico historico = HistoricoDao.obterHistoricoComBaseNaDataDescricao(data, descricao);
	  
	    // Certifique-se de que o historico não seja nulo antes de prosseguir
	    if (historico != null) {
	    
	        // Preencha os campos e rótulos com os valores apropriados
	        txtDataMovimentoEditar.setText(historico.getData());
	        txtDescricaoMovimentoEditar.setText(historico.getDescricao());
	        IdHistorico.setText(Integer.toString(historico.getIdHistorico()));
	        labelIdProcEditar.setText(Integer.toString(historico.getIdProcesso()));
	    } else {
	        // Limpe os campos se o historico não for encontrado
	        txtDataMovimentoEditar.clear();
	        txtDescricaoMovimentoEditar.clear();
	        IdHistorico.setText("");
	        labelIdProcEditar.setText("");
	    }
	}
	
	
											// COMANDO PARA EDIÇÃO DO ANDAMENTO NA TAB EDITAR PROCESSO
	@FXML
	private void handleEditarMovimento() {
	    // Obtenha os novos valores de data e descrição dos campos de entrada
	    String novaData = txtDataMovimentoEditar.getText();
	    String novaDescricao = txtDescricaoMovimentoEditar.getText();

	    // Obtenha o idHistorico do campo IdHistorico
	    int idHistorico = Integer.parseInt(IdHistorico.getText());

	    // Atualize os valores no banco de dados
	    boolean atualizacaoBemSucedida = HistoricoDao.atualizarHistorico(idHistorico, novaData, novaDescricao);

	    if (atualizacaoBemSucedida) {
	        // Atualização bem-sucedida, você pode mostrar uma mensagem de sucesso
	    	this.alert("Save", "Successful!", AlertType.INFORMATION);
	        
	    }
	}
	
	
	
	
	
										// COMANDO PARA EDIÇÃO DE ANDAMENTO NA TELA EDIÇÃO DE RECURSO
	@FXML
	private void handleEditarAndamento() {
	    // Obtenha os novos valores de data e descrição dos campos de entrada
	    String novaDataRec = txtDataAndamentoEdit.getText();
	    String novaDescricaoRec = txtDescricaoAndamentoEdit.getText();

	    // Obtenha o idHistorico do campo IdHistorico
	    int IdAndamento = Integer.parseInt(labelIDAndamento.getText());

	    // Crie uma instância de AndamentoRecDao
	    AndamentoRecDao andamentoRecDao = new AndamentoRecDao();

	    // Chame o método na instância de AndamentoRecDao para atualizar o andamento
	    boolean atualizacaoBemSucedida = andamentoRecDao.atualizarAndamentoRec(IdAndamento, novaDataRec, novaDescricaoRec);

	    if (atualizacaoBemSucedida) {
	        // Atualização bem-sucedida, você pode mostrar uma mensagem de sucesso
	        this.alert("Save", "Successful!", AlertType.INFORMATION);
	    }
	}
	
	
	
	
														
	
						// COMANDO DE INSERÇÃO DE INFORMAÇÕES NA TELA DE CONSULTA DE PROCESSO EM LABELS
    
	private void atualizarLabelsComDadosDoProcesso(String numeroProcessoSelecionado) {
	      try {
	       Proc proc = procDao.buscarProcessoPorNumero(numeroProcessoSelecionado);
	       if (proc != null) {
	        	ResultadoNomedoCliente.setText(proc.getnomecliente());
	        	ResultadoNumerodoProcesso.setText(proc.getnumeroprocesso());
	        	ResultadoComarcadoProcesso.setText(proc.getcomarca());
	        	ResultadoVaradoProcesso.setText(proc.getvara());
	        	ResultadoTemadoProcesso.setText(proc.gettema());
	        	ResultadoValordoProcesso.setText(proc.getvalorcausa());
	        	ResultadoValordosHonorarios.setText(proc.getvalorhono());
	        	ResultadoStatus.setText(proc.getstatus());
	        	ResultadoMatricula.setText(proc.getMatricula());

	        	 // Limpe a TextArea antes de adicionar os novos dados
	            ResultadosMovimentos.clear();

	            // Obter o histórico para o processo selecionado
	            List<Historico> historico = proc.getHistorico();

	            // Adicione todas as datas e descrições ao TextArea
	            for (Historico hist : historico) {
	                String dataEDescricao = hist.getData() + ": " + hist.getDescricao();
	                ResultadosMovimentos.appendText(dataEDescricao + "\n");
	                ResultadosMovimentos.setWrapText(true);
	                
	            }
	        } else {
	        	
	            // Caso o processo não seja encontrado
	        	ResultadoNomedoCliente.setText(" N/A");
	        	ResultadoNumerodoProcesso.setText("Número do Processo: N/A");
	        	ResultadoComarcadoProcesso.setText("N/A");
	        	ResultadoVaradoProcesso.setText("N/A");
	        	ResultadoTemadoProcesso.setText("N/A");
	        	ResultadoValordoProcesso.setText("N/A");
	        	ResultadoValordosHonorarios.setText("N/A");
	        	ResultadoStatus.setText("N/A");
	        	ResultadoMatricula.setText("N/A");
	         	  // Limpe a TextArea
	            ResultadosMovimentos.clear();
	        }
	    // Agora, chame a lógica de exibição de movimentos diretamente aqui
	        if (proc != null) {
	            int idProcesso = proc.getIdproc();
	            HistoricoDao historicoDao = new HistoricoDao();
	            List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(idProcesso);
	            
	            // Limpe o TextArea
	            ResultadosMovimentos.clear();

	            // Exiba os movimentos no TextArea
	            for (String movimento : movimentos) {
	                ResultadosMovimentos.appendText(movimento + "\n");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Método para exibir os movimentos com base no número do processo
	private void exibirMovimentosDoProcesso(String numeroProcessoSelecionado) {
	    try {
	        Proc proc = procDao.buscarProcessoPorNumero(numeroProcessoSelecionado);
	        
	        // Limpe o TextArea antes de adicionar os novos dados
	        ResultadosMovimentos.clear();

	        if (proc != null) {
	            int idProcesso = proc.getIdproc();
	            HistoricoDao historicoDao = new HistoricoDao();
	            List<String> movimentos = historicoDao.getDatasEDescricoesPorIdProcesso(idProcesso);

	            // Exiba os movimentos no TextArea
	            for (String movimento : movimentos) {
	                ResultadosMovimentos.appendText(movimento + "\n");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Método para lidar com a seleção de um processo na lista
	@FXML
	private void onSelecionarProcesso() {
	
	    String numeroProcessoSelecionado = listaResultados.getSelectionModel().getSelectedItem();
	    
	    if (numeroProcessoSelecionado != null) {
	        // Atualize os detalhes do processo
	        atualizarLabelsComDadosDoProcesso(numeroProcessoSelecionado);
	        
	        // Exiba automaticamente os movimentos do processo
	        exibirMovimentosDoProcesso(numeroProcessoSelecionado);
	    }
	}

	// Método para exibir os movimentos quando o botão é clicado
	@FXML
	private void exibirMovimento() {
	    String numeroProcessoSelecionado = listaResultados.getSelectionModel().getSelectedItem();
	    
	    if (numeroProcessoSelecionado != null) {
	        exibirMovimentosDoProcesso(numeroProcessoSelecionado);
	    }
	}

	private Proc getProcessoSelecionado() {
	    // Verifique se algo está selecionado na ListView
	    String numeroProcessoSelecionado = listaResultados.getSelectionModel().getSelectedItem();

	    if (numeroProcessoSelecionado != null) {

	        // Se algo estiver selecionado, obtenha o processo correspondente usando o número do processo
	        try {
	            return procDao.buscarProcessoPorNumero(numeroProcessoSelecionado);
	    
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    // Se nada estiver selecionado ou ocorrer um erro, retorne null
	    return null;
	}
	
	
										//FIM DE COMANDO TAB CONSULTA PROCESSO
	
	
	
	
	
	
	
	
										// TAB EDITAR PROCESSO
	
	
				
				private void preencherCamposComAndamentoSelecionadoTelaEditRec(Object  itemSelecionado) {
					if (itemSelecionado != null && itemSelecionado instanceof String) {
				        String linhaSelecionada = (String) itemSelecionado; // Converta o item em uma String

				        // Divida a linha selecionada para obter data e descrição
				    String[] partes = linhaSelecionada.split(": ");
				    String data = partes[0];
				    String descricao = partes[1];

				    // Obtenha o andamento com base na data e descrição usando a instância historicoDao
				    Historico historico = HistoricoDao. obterHistoricoComBaseNaDataDescricao
	(data, descricao);

				    // Certifique-se de que o historico não seja nulo antes de prosseguir
				    if (historico != null) {
				        // Preencha os campos e rótulos com os valores apropriados
				        txtDataMovimentoEditar.setText(historico.getData());
				        txtDescricaoMovimentoEditar.setText(historico.getDescricao());
				        IdHistorico.setText(Integer.toString(historico.getHistorico()));
				        labelIdProcEditar.setText(Integer.toString(historico.getIdProcesso()));
				    } else {
				        // Limpe os campos se o historico não for encontrado
				        txtDataMovimentoEditar.clear();
				        txtDescricaoMovimentoEditar.clear();
				        IdHistorico.setText("");
				        labelIdProcEditar.setText("");
				    }
				}}

	// Método para lidar com a seleção de um processo na lista
				private boolean movimentosExibidos = false; // Variável de controle
		@FXML
		private void onSelecionarProcessoTabEditar() throws SQLException {
		
		    String numeroProcessoSelecionadoTabEditar = listaResultados2Editar.getSelectionModel().getSelectedItem();
		    
		    if (numeroProcessoSelecionadoTabEditar != null) {
		        // Atualize os detalhes do processo
		        atualizarCamposComDadosDoProcesso(numeroProcessoSelecionadoTabEditar);

		        // Exiba automaticamente os movimentos do processo apenas se ainda não foram exibidos
		        if (!movimentosExibidos) {
		        	exibirMovimentoeditar();
		            movimentosExibidos = true; // Defina a variável para indicar que os movimentos já foram exibidos
		        }
		    }
		}

		

	
    
    // COMANDO DE INSERÇÃO DE INFORMAÇÕES NA TELA DE EDIÇÃO EM TEXTFIELD
    
	private void atualizarCamposComDadosDoProcesso(String numeroProcessoSelecionadoEditar) {
	    // Use a função getProcessoSelecionadoEditarTab para obter o processo selecionado
	    Proc processoSelecionadoEditar = getProcessoSelecionadoEditarTab();
	    
	    HistoricoDao historicoDao = new HistoricoDao();

	    if (processoSelecionadoEditar != null) {
	        // Se o processo for encontrado, atualize os campos TextField
	       	ResultadoNomedoCliente2.setText(processoSelecionadoEditar.getnomecliente());
	        txtNumeroProc.setText(processoSelecionadoEditar.getnumeroprocesso());
	        txtComarcaProc.setText(processoSelecionadoEditar.getcomarca());
	        txtVaraProc.setText(processoSelecionadoEditar.getvara());
	        txtTemaProc.setText(processoSelecionadoEditar.gettema());
	        txtValorProc.setText(processoSelecionadoEditar.getvalorcausa());
	        txtHonoProc.setText(processoSelecionadoEditar.getvalorhono());
	        txtStatus.setText(processoSelecionadoEditar.getstatus());
	        txtMatricula.setText(processoSelecionadoEditar.getMatricula());

	        // Limpe a ListView antes de adicionar os novos dados
	        ResultadosMovimentosEditar.getItems().clear();

	        // Obtenha o histórico para o processo selecionado
	        List<String> historico = new ArrayList<>();
			try {
				historico = historicoDao.getDatasEDescricoesPorIdProcesso(processoSelecionadoEditar.getIdproc());
			} catch (SQLException e) {
				
				e.printStackTrace();
			}// processoSelecionadoEditar.getHistorico();
	        
	       
	        // Adicione todas as datas e descrições ao TextArea
	        for (String hist : historico) {
	            ResultadosMovimentosEditar.getItems().add(hist);
	            
	                   }
	    } else {
	        // Caso nenhum processo esteja selecionado, limpe os campos TextField
	    	ResultadoNomedoCliente2.setText("");
	    	txtNumeroProc.clear();
	        txtComarcaProc.clear();
	        txtVaraProc.clear();
	        txtTemaProc.clear();
	        txtValorProc.clear();
	        txtHonoProc.clear();
	        txtStatus.clear();
	        txtMatricula.clear();

	        // Limpe a TextArea
	        ResultadosMovimentosEditar.getItems().clear();
	    }}
	        
	private Proc getProcessoSelecionadoEditarTab
	() {
				    // Verifique se algo está selecionado na ListView
				    String numeroProcessoSelecionadoTabEditar = listaResultados2Editar.getSelectionModel().getSelectedItem();

				    if (numeroProcessoSelecionadoTabEditar != null) {
			
				        // Se algo estiver selecionado, obtenha o processo correspondente usando o número do processo
				        try {
				            return procDao.buscarProcessoPorNumero(numeroProcessoSelecionadoTabEditar);
				    
				        } catch (SQLException e) {
				            e.printStackTrace();
				        }
				    }
				    
				    // Se nada estiver selecionado ou ocorrer um erro, retorne null
				    return null;
				}

				
	@FXML
	private void exibirMovimentoeditar() {
	    // Verifique se algo está selecionado na ListView
		
	    String numeroProcessoSelecionadoeditar = listaResultados2Editar.getSelectionModel().getSelectedItem();

	    if (numeroProcessoSelecionadoeditar != null) {
	    	
	        // Obtém o processo selecionado
	        Proc processoSelecionadoeditar = getProcessoSelecionadoEditarTab();

	        if (processoSelecionadoeditar != null) {
	            try {
	                // Obtém o ID do processo selecionado
	            	
	                int idProcesso = processoSelecionadoeditar.getIdproc();

	                // Obtém os movimentos do histórico vinculados ao ID do processo
	                
	                HistoricoDao historicoDao = new HistoricoDao();
	                List<Historico> movimentoseditar = historicoDao.getHistoricoPorIdProcesso(idProcesso);

	                // Limpa o ListView
	                ResultadosMovimentosEditar.getItems().clear();

	                // Exibe os movimentos no ListView
	                for (Historico movimento : movimentoseditar) {
	                	
	                    // Adicione cada movimento ao ListView
	                    String dataEDescricao = movimento.getData() + ": " + movimento.getDescricao();
	                    ResultadosMovimentosEditar.getItems().add(dataEDescricao);
	                   
	                }
	                
	             // Agora, chame o método para atualizar os campos do processo
	                atualizarCamposComDadosDoProcesso(numeroProcessoSelecionadoeditar);
	          
	            } catch (SQLException e) {
	                e.printStackTrace();
	                // Lide com erros de SQL, se necessário
	            }
	        }
	    }
	}
	
    
	
	
	// Fim do código TAB EDITAR PROCESSO
	
	
	
	
	
	    
			// INSERÇÃO DE AÇÃO PARA INSERIR MOVIMENTO/ANDAMENTO
	
			@FXML
			private void inserirMovimento() {
			    String descricao = txtDescricaoMovimento.getText();
			    String data = txtDataMovimento.getText(); 
			    
			    if (!descricao.isEmpty() && !data.isEmpty()) {
			        Historico historico = new Historico();
			        historico.setDescricao(descricao);
			        historico.setData(data);

			        // Obtenha o processo selecionado (você precisa implementar esse método)
			        Proc processoSelecionado = getProcessoSelecionado();

			        if (processoSelecionado != null) {
			            try {
			                // Salve o histórico no banco de dados usando o HistoricoDao
			                HistoricoDao historicoDao = new HistoricoDao();
			                historicoDao.saveHistorico(historico, processoSelecionado.getIdproc()); // Passe o ID do processo

			                // Associe o histórico ao processo
			                processoSelecionado.adicionarHistorico(historico);

			                // Limpe os campos após a inserção
			                txtDescricaoMovimento.clear();
			                txtDataMovimento.clear();

			                // Atualize a exibição do histórico
			                atualizarHistorico(processoSelecionado);
			            } catch (SQLException e) {
			                e.printStackTrace();
			                // Lide com erros de SQL, se necessário
			            }
			        }
			    } else {
			    	
			    }
			}
			
			private void atualizarHistorico(Proc processo) {
				ResultadosMovimentos.clear();

			    if (processo != null) {
			        try {
			            // Obtenha o ID do processo selecionado
			            int idProcesso = processo.getIdproc();

			            // Crie uma instância de HistoricoDao
			            HistoricoDao historicoDao = new HistoricoDao();

			            // Use o novo método em HistoricoDao para buscar datas e descrições
			            List<String> datasEDescricoes = historicoDao.getDatasEDescricoesPorIdProcesso(idProcesso);

			            // Exiba as datas e descrições na ResultadosMovimentos
			            for (String dataEDescricao : datasEDescricoes) {
			                ResultadosMovimentos.appendText(dataEDescricao + "\n");
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			    }
			}
			
			
			
			
			
			
			
			
			
						
			
			// Editar ou excluir processo - Nova Tela: EDITARPROCESSO
			
			@FXML
			private Button btnEditarProcesso;
			public void btnEditarProcesso() {
				
			}
			
			@FXML
			private void onConcluirEdicaoButtonClick(ActionEvent event) {
			    // Obtenha os valores das TextFields
				String nomecliente = ResultadoNomedoCliente2.getText();
			    String numeroProc = txtNumeroProc.getText();
			    String comarcaProc = txtComarcaProc.getText();
			    String varaProc = txtVaraProc.getText();
			    String temaProc = txtTemaProc.getText();
			    String valorProc = txtValorProc.getText();
			    String honoProc = txtHonoProc.getText();
			    String statusProc = txtStatus.getText();
			    String matricula = txtMatricula.getText();

			    // Crie um objeto Proc com os valores das TextFields
			    Proc processoAtualizado = new Proc();
			    processoAtualizado.setnomecliente(nomecliente);
			    processoAtualizado.setnumeroprocesso(numeroProc);
			    processoAtualizado.setcomarca(comarcaProc);
			    processoAtualizado.setvara(varaProc);
			    processoAtualizado.settema(temaProc);
			    processoAtualizado.setvalorcausa(valorProc);
			    processoAtualizado.setvalorhono(honoProc);
			    processoAtualizado.setstatus(statusProc);
			    processoAtualizado.setMatricula(matricula);

			    // Chame o método de atualização do banco de dados
			    try {
			        boolean atualizacaoBemSucedida = procDao.EditaProcesso(processoAtualizado);

			        if (atualizacaoBemSucedida) {
			        	this.alert("Save", "Successful!", AlertType.INFORMATION);
			        } else {
			        	this.alert("Error", "Failed!", AlertType.ERROR);
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			        
			    }
			}
			
			
			
			// DOUGLAS
			@FXML
			private void onExcluirProcessoButtonClick(ActionEvent event) {
			    // Obtenha o número do processo selecionado na lista
			    String numeroProcessoSelecionado = listaResultados2Editar.getSelectionModel().getSelectedItem();

			    if (numeroProcessoSelecionado != null) {
			        // Chame o método de exclusão do banco de dados
			        try {
			            boolean exclusaoBemSucedida = procDao.deleteProc(numeroProcessoSelecionado);

			            if (exclusaoBemSucedida) {
			                System.out.println("Exclusão bem sucedida");
			                // Limpe as TextFields e TextArea após a exclusão
			                txtNumeroProc.clear();
			                txtComarcaProc.clear();
			                txtVaraProc.clear();
			                txtTemaProc.clear();
			                txtValorProc.clear();
			                txtHonoProc.clear();
			                txtStatus.clear();
			                txtMatricula.clear();
			                ResultadosMovimentosEditar.getItems().clear();
			            } else {
			            	this.alert("Error", "Failed!", AlertType.ERROR);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Lide com exceções SQL, se necessário
			        }
			    } else {
			      
			    }
			}
						
			
//CONTROLE CONSULTAR E EDITAR RECURSO
			// TAB CONSULTAR
			
			@FXML
			private Tab tabConsultaRec;
			@FXML
			private Tab tabEditaRec;
			
			@FXML
			private TextField txtClienteRec;
			@FXML
			private ListView<String> listClienteRec;
			
			@FXML
			private Label labelProcOrigem;
			@FXML
			private Label labelTipo;
			@FXML
			private Label labelRecorrenteRecorrido;
			@FXML
			private Label labelStatusRec;
			@FXML
			private Label labelJulgador;
			@FXML
			private Label labelRelator;
			@FXML
			private ListView listAndamentosRec;
			@FXML
			private TextField txtDataAndamentoRec;
			@FXML
			private TextField txtDescAndamentoRec;
			@FXML
			private Button btnBuscarNomeClienteRec;
			@FXML
			private Button btnExibirAndamentosRec;
			@FXML
			private Button btnInserirAndamentosRec;
			@FXML
			private Button btnVoltarPagInicial;
			@FXML
			private Button btnLimpaTelaConsultaRec;
			@FXML
			private Button btnEditarRec;
			@FXML
			private Label labelNumeroRecurso;

			
			//TAB EDITAR
			@FXML
			private TextField txtBuscaClienteEditRec;
			@FXML
			private Button btnBuscaClienteEditRec;
			@FXML
			private ListView<String> listClienteEditRec;
			@FXML
			private Label labelNomeClienteEditRec;
			@FXML
			private TextField txtNumeroRecEdit;
			@FXML
			private TextField txtProcOrig;
			@FXML
			private TextField txtTipoRec;
			@FXML
			private TextField txtRecorridoRecorrente;
			@FXML
			private TextField txtStatusRec;
			@FXML
			private TextField txtJulgadorRec;
			@FXML
			private TextField txtRelatorRec;
			@FXML
			private ListView listAndamentoRecEdit;
			@FXML
			private TextField txtDataAndamentoEdit;
			@FXML
			private TextField txtDescricaoAndamentoEdit;
			@FXML
			private Label labelIDAndamento;
			@FXML
			private Label labelIDRec;
			@FXML
			private Button btnLimpaTelaEditaRec;
			@FXML
			private Button btnVoltarConsultarRec;
			@FXML
			private Button btnConcluirEditRec;
			@FXML
			private Button btnEditarAndamentoRec;
			@FXML
			private Button btnexibirAndamentosEdit;
			
			
			@FXML
			private void limparTela2() {
			    // Limpe todos os campos de texto e labels
				labelNumeroRecurso.setText("");
		        labelProcOrigem.setText("");
		        labelTipo.setText("");
		        labelRecorrenteRecorrido.setText("");
		        labelStatusRec.setText("");
		        labelJulgador.setText("");
		        labelRelator.setText("");
				
			    // Limpe a seleção na ComboBox e na ListView, se aplicável
			 
		        listClienteRec.getSelectionModel().clearSelection();
		        listClienteRec.getItems().clear();
		        listAndamentosRec.getSelectionModel().clearSelection();
		        listAndamentosRec.getItems().clear();
			}
			@FXML
			private ListView<String> listClienteRec1;
			@FXML
			private Label labelNomeClienteRec;
			
			@FXML
			private void limparTela3() {
				 // Limpe todos os campos de texto e labels
				
				txtNumeroRecEdit.clear();
				txtProcOrig.clear();
				txtTipoRec.clear();
				txtRecorridoRecorrente.clear();
				txtStatusRec.clear();
				txtJulgadorRec.clear();
				txtRelatorRec.clear();
			    // Limpe a seleção na ComboBox e na ListView, se aplicável
				
				listClienteEditRec.getSelectionModel().clearSelection();
				listClienteEditRec.getItems().clear();
				listAndamentoRecEdit.getSelectionModel().clearSelection();
				listAndamentoRecEdit.getItems().clear();
			}
			
			// MÉTODO PARA APARECEREM OS NOMES NA LISTVIEW APÓS BUSCA PELAS LETRAS INICIAIS
			@FXML
		    private void buscarNomesClientes() {
		        String textoBusca = txtClienteRec.getText().trim();

		         		            	 try {
		     		        RecDao recDao = new RecDao(); // Crie uma instância de RecDao
		     		        List<String> nomesClientesRec = recDao.buscarNomesClientesPorTexto(textoBusca);

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
			            	labelNomeClienteRec.setText(selectedCliente);

			                // Limpe a lista de recursos e adicione os números de recurso do cliente
			                listClienteRec1.getItems().setAll(numerosRecursos);
			            }
			        } catch (SQLException e) {
			            // Lide com exceções de SQL aqui, se necessário
			            e.printStackTrace();
			        }
			    }
			}
			
			@FXML
			private void onSelecionarRecurso() throws SQLException {
			    // Obtém o item selecionado da lista
			    Object selectedValue = listClienteRec1.getSelectionModel().getSelectedItem();

			    // Verifica se o item selecionado é uma String
			    if (selectedValue != null && selectedValue instanceof String) {
			        String newValue = (String) selectedValue;

			        Rec rec = recDao.buscarRecursoPorNumero(newValue);

			       
			        labelNumeroRecurso.setText(rec.getNumerorecurso());
			        labelProcOrigem.setText(rec.getProcessoorigem());
			        labelTipo.setText(rec.getTiporecurso());
			        labelRecorrenteRecorrido.setText(rec.getRecorridoourecorrente());
			        labelStatusRec.setText(rec.getStatus());
			        labelJulgador.setText(rec.getJulgador());
			        labelRelator.setText(rec.getRelator());
			     // Agora, ao selecionar um cliente, exiba automaticamente os andamentos
			        exibirAndamentosRec();
			    } else {
			        // Lidar com o caso em que o valor selecionado não é uma String
			    }
			}
			
			private int idRec = -1; 
			@FXML
			private void inserirAndamentoRec() {
			    String dataRecurso = txtDataAndamentoRec.getText();
			    String descricaoRecurso = txtDescAndamentoRec.getText();

			    if (!dataRecurso.isEmpty() && !descricaoRecurso.isEmpty()) {
			        // Obter o processo selecionado na lista
			        String processoSelecionado = listClienteRec.getSelectionModel().getSelectedItem();

			        if (processoSelecionado != null) {
			            try {
			                // Obter o Idrec com base no processo selecionado
			                int idRec = recDao.obterIdRecPorProcesso(processoSelecionado);

			                // Inserir o andamento no banco de dados
			                boolean insercaoBemSucedida = andamentoRecDao.inserirAndamentoRec(idRec, dataRecurso, descricaoRecurso);

			                if (insercaoBemSucedida) {
			                    // Limpar os campos após a inserção
			                    txtDataAndamentoRec.clear();
			                    txtDescAndamentoRec.clear();

			                    // Atualizar a exibição dos andamentos
			                    exibirAndamentosRec();
			                } else {
			                    // Lide com a falha na inserção, se necessário
			                }
			            } catch (SQLException e) {
			                e.printStackTrace();
			                // Lide com exceções SQL, se necessário
			            }
			        } else {
			            // Trate o caso em que nenhum processo está selecionado na lista
			        }
			    } else {
			        // Trate o caso em que os campos de texto estão vazios
			    }
			}

			
			@FXML
			private void exibirAndamentosRec() throws SQLException {
			    // Verifique se algo está selecionado na ListView listClienteRec
			    String nomeClienteSelecionado = listClienteRec.getSelectionModel().getSelectedItem();

			    if (nomeClienteSelecionado != null) {
			        // Obtenha o idRec com base no nome do cliente selecionado
			        int idRec = recDao.obterIdRecPorNomeCliente(nomeClienteSelecionado);

			        if (idRec != -1) {
			            try {
			                // Chame o método na instância de AndamentoRecDao para obter os andamentos
			                List<String> andamentos = andamentoRecDao.obterAndamentosPorIdRec(idRec);

			                // Limpe a ListView e adicione os andamentos
			                listAndamentosRec.getItems().clear();
			                listAndamentosRec.getItems().addAll(andamentos);
			            } catch (SQLException e) {
			                e.printStackTrace();
			                // Lide com exceções SQL, se necessário
			            }
			        }
			    } else {
			        // Trate o caso em que nenhum nome de cliente foi selecionado
			    }
			}
			// MESMAS AÇÕES DE REC NA TAB EDITAR
			
			@FXML
		    private void buscarNomesClientesEdita() {
		        String textoBusca = txtBuscaClienteEditRec.getText().trim();

		         		            	 try {
		     		        RecDao recDao = new RecDao(); // Crie uma instância de RecDao
		     		        List<String> nomesClientesRec = recDao.buscarNomesClientesPorTexto(textoBusca);

		     		        // Usando um conjunto para garantir nomes únicos e ordená-los
		     		        Set<String> nomesUnicos = new TreeSet<>(nomesClientesRec);

		     		       	                
		     		       listClienteEditRec.getItems().setAll(new ArrayList<>(nomesUnicos));
		   		        // Define os resultados na ListView
		   		    } catch (SQLException e) {
		   		        // Lide com exceções de SQL aqui, se necessário
		   		        e.printStackTrace();
		   		    }
		          		}
			@FXML
			private ListView<String> listClienteEditRec1;
			
			@FXML
			private void onSelecionarClienteRecEdita() throws SQLException {
			    // Obtém o item selecionado da lista
			    String selectedCliente = (String) listClienteEditRec.getSelectionModel().getSelectedItem();

			    if (selectedCliente != null) {
			        try {
			            RecDao recDao = new RecDao();
			            List<String> numerosRecursos = recDao.buscarNumerosRecursoPorNomeCliente(selectedCliente);

			            if (!numerosRecursos.isEmpty()) {
			            	labelNomeEditRec.setText(selectedCliente);

			                // Limpe a lista de recursos e adicione os números de recurso do cliente
			                listClienteEditRec1.getItems().setAll(numerosRecursos);
			            }
			        } catch (SQLException e) {
			            // Lide com exceções de SQL aqui, se necessário
			            e.printStackTrace();
			        }
			    }
			}
			
			private void atualizarCamposComDadosDoCliente(String nomeClienteSelecionado) throws SQLException {
				 // Obtém o item selecionado da lista
			    Object selectedValue = listClienteEditRec1.getSelectionModel().getSelectedItem();

			    // Verifica se o item selecionado é uma String
			    if (selectedValue != null && selectedValue instanceof String) {
			        String newValue = (String) selectedValue;

			        Rec rec = recDao.buscarRecursoPorNumero(newValue);

			            // Preencha as TextFields com os dados do cliente
			        	labelNomeEditRec.setText(rec.getNomedocliente());
			        	txtNumeroRecEdit.setText(rec.getNumerorecurso());
			        	txtProcOrig.setText(rec.getProcessoorigem());
			            txtTipoRec.setText(rec.getTiporecurso());
			            txtRecorridoRecorrente.setText(rec.getRecorridoourecorrente());
			            txtStatusRec.setText(rec.getStatus());
			            txtJulgadorRec.setText(rec.getJulgador());
			            txtRelatorRec.setText(rec.getRelator());
			        }
			    }  
			
			
			@FXML
			private void exibirAndamentosRecEditar() throws SQLException {
			    // Verifique se algo está selecionado na ListView listClienteRec
			    String nomeClienteSelecionadoedit = listClienteEditRec.getSelectionModel().getSelectedItem();

			    if (nomeClienteSelecionadoedit != null) {
			        // Obtenha o idRec com base no nome do cliente selecionado
			        int idRec = recDao.obterIdRecPorNomeClienteEdit(nomeClienteSelecionadoedit);

			        if (idRec != -1) {
			            try {
			                // Chame o método na instância de AndamentoRecDao para obter os andamentos
			                List<String> andamentos = andamentoRecDao.obterAndamentosPorIdRecEdit(idRec);

			                // Limpe a ListView e adicione os andamentos
			                listAndamentoRecEdit.getItems().clear();
			                listAndamentoRecEdit.getItems().addAll(andamentos);
			                if (!andamentos.isEmpty()) {
			                    // Se houver andamentos, defina os valores nas TextFields
			                    String primeiroAndamento = andamentos.get(0); // Supondo que o primeiro andamento tenha o formato "dataRecurso - descricaoRecurso"
			                    String[] partesAndamento = primeiroAndamento.split(" - ");
			                    
			                    if (partesAndamento.length == 2) {
			                        String dataRecurso = partesAndamento[0];
			                        String descricaoRecurso = partesAndamento[1];

			                        // Defina os valores nas TextFields
			                        txtDataAndamentoEdit.setText(dataRecurso);
			                        txtDescricaoAndamentoEdit.setText(descricaoRecurso);
			                    }
			                }
			            } catch (SQLException e) {
			                e.printStackTrace();
			                // Lide com exceções SQL, se necessário
			            }
			        }
			    } else {
			        // Trate o caso em que nenhum nome de cliente foi selecionado
			    }
			}
			
			private void preencherCamposComHistoricoSelecionadoTelaEditRec(Object  itemSelecionado) {
				if (itemSelecionado != null && itemSelecionado instanceof String) {
			        String linhaSelecionada = (String) itemSelecionado; // Converta o item em uma String

			        // Divida a linha selecionada para obter data e descrição
			    String[] partes = linhaSelecionada.split(": ");
			    String dataRecurso = partes[0];
			    String descricaoRecurso = partes[1];

			    // Obtenha o historico com base na data e descrição usando a instância andamentoRecDao
			    AndamentoRec andamentoRec = andamentoRecDao.obterHistoricoComBaseNaDataDescricao(dataRecurso, descricaoRecurso);

			    // Certifique-se de que o historico não seja nulo antes de prosseguir
			    if (andamentoRec != null) {
			        // Preencha os campos e rótulos com os valores apropriados
			        txtDataAndamentoEdit.setText(andamentoRec.getDataRecurso());
			        txtDescricaoAndamentoEdit.setText(andamentoRec.getDescricaoRecurso());
			        labelIDAndamento.setText(Integer.toString(andamentoRec.getIdAndamento()));
			        labelIDRec.setText(Integer.toString(andamentoRec.getIdRecurso()));
			    } else {
			        // Limpe os campos se o historico não for encontrado
			        txtDataAndamentoEdit.clear();
			        txtDescricaoAndamentoEdit.clear();
			        labelIDAndamento.setText("");
			        labelIDRec.setText("");
			    }
			}}
			
			
			@FXML
			private void atualizarRecurso(ActionEvent event) {
			    // Obtenha os valores dos campos de texto
				String nomedocliente = labelNomeEditRec.getText();
			    String numeroRec = txtNumeroRecEdit.getText();
			    String processoOrigem = txtProcOrig.getText();
			    String tipoRec = txtTipoRec.getText();
			    String recorridoRecorrente = txtRecorridoRecorrente.getText();
			    String statusRec = txtStatusRec.getText();
			    String julgadorRec = txtJulgadorRec.getText();
			    String relatorRec = txtRelatorRec.getText();

			    // Crie um objeto Rec com os novos valores
			    Rec recAtualizado = new Rec();
			    recAtualizado.setNomedocliente(nomedocliente);
			    recAtualizado.setNumerorecurso(numeroRec);
			    recAtualizado.setProcessoorigem(processoOrigem);
			    recAtualizado.setTiporecurso(tipoRec);
			    recAtualizado.setRecorridoourecorrente(recorridoRecorrente);
			    recAtualizado.setStatus(statusRec);
			    recAtualizado.setJulgador(julgadorRec);
			    recAtualizado.setRelator(relatorRec);
			    
			    

			    try {
			        boolean atualizacaoBemSucedida = recDao.UpdateRecurso(recAtualizado);
			        if (atualizacaoBemSucedida) {
			        	this.alert("Save", "Successful!", AlertType.INFORMATION);
			        } else {
			        	this.alert("Error", "Failed!", AlertType.ERROR);
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			        // Lide com exceções SQL, se necessário
			    }
			}
			
			
			
			
			@FXML
			private Label labelNomeEditRec;
			
			
										// DATABASE DO ARQUIVO
			@FXML
			private Button btnArquivaRec;
			@FXML
			private CheckBox cboxArquivoRec;
			
			
			
			
			
			
			
			
			
}
