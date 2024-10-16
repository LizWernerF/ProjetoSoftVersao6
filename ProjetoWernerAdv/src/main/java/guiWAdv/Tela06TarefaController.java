package guiWAdv;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

public class Tela06TarefaController {
	
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
	
	    @FXML
	    private ListView<String> tarefasPendentesList; 
	 
	    @FXML
	    private ListView<String> tarefasFeitasList; 

	    @FXML
	    private Button buscarClienteBtn;
	    @FXML
	    private TextField descricaoFieldTab1; 
	    @FXML
	    private TextField descricaoFieldTab2;

	    @FXML
	    private TextField txtNomeBusca; 

	    @FXML
	    private ListView<String> listClientesBusca; 
	    @FXML
	    private TextField clienteFieldTab1;
	    @FXML
	    private TextField clienteFieldTab2;
	   
	    @FXML
	    private ListView<String> listProcNomes; 

	    @FXML
	    private TextField processoFieldTab1;
	    @FXML
	    private TextField processoFieldTab2;
	  
	    @FXML
	    private DatePicker dataLimiteField; 

	    @FXML
	    private Button adicionarTarefaBtn; 
	    
	    @FXML
	    private Button atualizarTarefasBtn;
	    @FXML
	    private Button atualizarTarefasBtn3;
	    
	    @FXML
	    private Button marcarComoFeitoBtn;
	    
	    @FXML
	    private Button marcarComoPendenteBtn;
	    
	    @FXML
	    private Button editarTarefaBtn;
	    
	    @FXML
	    private Button editarTarefaBtn1;
	    

	    @FXML
	    private Button btnTabGerador;
	    
	    @FXML
	    private TextField responsavelField;

	    @FXML
	    private TextField dataLimiteFieldTab1;

	    
	    @FXML
		private ChoiceBox<String> choiceResponsavel;
	    
	    @FXML
		private ChoiceBox<String> choiceStatus;
	    
	    @FXML
	    private TabPane tabPane;
	    
	    @FXML
	    private Tab tabPendentes;
	    
	    @FXML
	    private Tab tabAdd;
	    
	    @FXML
	    private Tab tabFeitas;
	    
	    @FXML
	    private Button editarTarefaBtn111;
	    
	    @FXML
	    private Button editarTarefaBtn11;
	    
	    @FXML
	    private Button btnMostraTarefas;
	    
	    @FXML
	    private Label labelIDTarefa;
	    
	    @FXML
	    private TextField txtStatusEdit1;
	    
	    public void initializeComboBox() {
			@SuppressWarnings("unused")
			UserDao userDao = new UserDao();
		}
	    
	    @FXML
		public void initialize() {

	    	// ADICIONANDO COMANDO AOS BOTÕES PARA ALTERNAR ENTRE AS TABS
	    	editarTarefaBtn111.setOnAction(event -> {
	            tabPane.getSelectionModel().select(tabFeitas); // Alterna para a tab "Feitas"
	        });

	        editarTarefaBtn11.setOnAction(event -> {
	            tabPane.getSelectionModel().select(tabAdd); // Alterna para a tab "Adicionar"
	        });
	       	
	    List<String> nomesDocumentos = Arrays.asList("Angelo", "Liz", "Thiago");
		choiceResponsavel.getItems().addAll(nomesDocumentos);
		
		 List<String> statusTarefa = Arrays.asList("pendente", "em andamento", "concluída");
			choiceStatus.getItems().addAll(statusTarefa);
	    
		
		  // Configurar o botão para adicionar uma nova tarefa
        adicionarTarefaBtn.setOnAction(event -> adicionarTarefa());

        
        carregarTarefasPendentes();
        carregarTarefasConcluidas(); 
        
        btnMostraTarefas.setOnAction(event -> carregarTarefasPendentes());
        
        tarefasPendentesList.setCellFactory(param -> new TarefaListCell());
        
        tarefasFeitasList.setCellFactory(listView -> new TarefaListCell());
              
        editarTarefaBtn.setOnAction(event -> editarTarefa());
        
              
        atualizarTarefasBtn.setOnAction(event -> carregarTarefasPendentes());
        
        marcarComoFeitoBtn.setOnAction(event -> marcarComoFeito());
	    
        marcarComoPendenteBtn.setOnAction(event -> marcarComoPendente());
        
        
        editarTarefaBtn1.setOnAction(event -> {
            try {
                // Captura os valores dos campos preenchidos pelo usuário
                String descricao = descricaoFieldTab1.getText();
                String responsavel = responsavelField.getText();
                String cliente = clienteFieldTab1.getText();
                String processo = processoFieldTab1.getText();
                int idTarefa = Integer.parseInt(labelIDTarefa.getText()); // Obtém o ID da tarefa a partir do label
                String status = txtStatusEdit1.getText(); // Obtém o status

                // Obtém a data limite da tarefa com base no ID
                TarefaDao tarefaDao = new TarefaDao();
                String dataLimite = tarefaDao.getDataLimiteById(idTarefa);

                // Preenche o campo de data limite com o valor encontrado
                if (dataLimite != null) {
                    dataLimiteFieldTab1.setText(LocalDate.parse(dataLimite).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                } else {
                    dataLimiteFieldTab1.setText(""); // Se não houver data limite, limpa o campo
                }

                // Cria um objeto Tarefa com os novos dados
                Tarefa tarefaEditada = new Tarefa();
                tarefaEditada.setIdTarefa(idTarefa);
                tarefaEditada.setDescricao(descricao);
                tarefaEditada.setResponsavel(responsavel);
                tarefaEditada.setCliente(cliente);
                tarefaEditada.setProcesso(processo);
                tarefaEditada.setDataLimite(dataLimiteFieldTab1.getText());
                tarefaEditada.setStatus(status); // Adiciona o status

                // Atualiza a tarefa no banco de dados usando TarefaDao
                tarefaDao.atualizarTarefa(tarefaEditada);

                // Exibe uma mensagem de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Tarefa Atualizada");
                alert.setContentText("A tarefa foi atualizada com sucesso.");
                alert.showAndWait();

                // Atualiza a lista de tarefas pendentes (caso necessário)
                carregarTarefasPendentes();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao Atualizar Tarefa");
                alert.setHeaderText("Erro ao atualizar a tarefa");
                alert.setContentText("Ocorreu um erro ao tentar atualizar a tarefa.");
                alert.showAndWait();
            }
        });
        
        
	    //FIM DO INITIALIZE
	    }
	    // Método chamado ao buscar nomes de clientes
	    @FXML
	    private void buscarNomesClientes() {
	        String textoBuscaCliente = txtNomeBusca.getText().trim();

	        try {
	            // Simulação da busca no banco de dados
	            List<String> nomesClientes = userDao.buscarNomesClientesPorTextoUser(textoBuscaCliente);

	            if (nomesClientes.isEmpty()) {
	                listClientesBusca.getItems().clear();  // Limpa a lista se não houver resultados
	            } else {
	                listClientesBusca.getItems().setAll(nomesClientes);  // Adiciona os resultados à ListView
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Método chamado quando um nome de cliente é selecionado na ListView
	    @FXML
	    private void onNomeClienteSelecionado() {
	        String selectedNomeCliente = listClientesBusca.getSelectionModel().getSelectedItem();

	        if (selectedNomeCliente != null) {
	            clienteFieldTab2.setText(selectedNomeCliente);

	            try {
	                // Simulação da busca de processos no banco de dados
	                List<String> numerosProcessos = procDao.buscarNumerosProcPorNomeCliente(selectedNomeCliente);
	                listProcNomes.setItems(FXCollections.observableArrayList(numerosProcessos));
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    // Método chamado quando um processo é selecionado na ListView
	    @FXML
	    private void onProcessoSelecionado() {
	        String selectedProcesso = listProcNomes.getSelectionModel().getSelectedItem();
	        if (selectedProcesso != null) {
	            processoFieldTab2.setText(selectedProcesso);
	        }
	    }

	    public void btnTabGerador() {
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
	      

	    // Método para adicionar uma nova tarefa
	    private void adicionarTarefa() {
	        String descricao = descricaoFieldTab2.getText();
	        String responsavel = choiceResponsavel.getValue();
	        String cliente = clienteFieldTab2.getText();
	        String processo = processoFieldTab2.getText();
	        String dataLimite = (dataLimiteField.getValue() != null) ? dataLimiteField.getValue().toString() : "";
	        String status = choiceStatus.getValue();

	        // Exemplo de validação simples
	        if (descricao.isEmpty() || responsavel == null || dataLimite.isEmpty() || status.isEmpty()) {
	            // Mostrar um alerta se faltar algum campo
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Campos Incompletos");
	            alert.setHeaderText("Todos os campos obrigatórios devem ser preenchidos");
	            alert.setContentText("Preencha a descrição, responsável, data limite e status.");
	            alert.showAndWait();
	        } else {
	            // Criar a tarefa
	            Tarefa tarefa = new Tarefa();
	            tarefa.setDescricao(descricao);
	            tarefa.setResponsavel(responsavel);
	            tarefa.setCliente(cliente.isEmpty() ? null : cliente); // Permitir que cliente possa estar vazio
	            tarefa.setProcesso(processo.isEmpty() ? null : processo); // Permitir que processo possa estar vazio
	            tarefa.setDataCriacao(LocalDate.now().toString()); // Definindo a data de criação como a data atual
	            tarefa.setDataLimite(dataLimite);
	            tarefa.setStatus(status);
	            tarefa.setConcluida(false); // Inicialmente, a tarefa não está concluída

	            try {
	                TarefaDao tarefaDao = new TarefaDao();
	                tarefaDao.saveTarefa(tarefa); // Usando o DAO para salvar a tarefa

	                // Alerta de sucesso
	                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                successAlert.setTitle("Tarefa Adicionada");
	                successAlert.setHeaderText(null);
	                successAlert.setContentText("A tarefa foi adicionada com sucesso!");
	                successAlert.showAndWait();

	                // Opcional: limpar os campos após a adição
	                limparCampos(); // Método que você deve implementar para limpar os campos
	            } catch (SQLException e) {
	                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	                errorAlert.setTitle("Erro ao Adicionar Tarefa");
	                errorAlert.setHeaderText("Erro ao adicionar a tarefa");
	                errorAlert.setContentText("Ocorreu um erro ao tentar salvar a tarefa no banco de dados.");
	                errorAlert.showAndWait();
	                e.printStackTrace();
	            }
	        }
	    }

	    // Método para limpar os campos após a adição
	    private void limparCampos() {
	        descricaoFieldTab2.clear();
	        choiceResponsavel.setValue(null);
	        clienteFieldTab2.clear();
	        processoFieldTab2.clear();
	        dataLimiteField.setValue(null);
	        choiceStatus.setValue(null);
	    }
	    
	    private void carregarTarefasPendentes() {
	        try {
	            TarefaDao tarefaDao = new TarefaDao();
	            List<Tarefa> tarefasPendentes = tarefaDao.getAllTarefas(); // Recupera todas as tarefas

	            tarefasPendentesList.getItems().clear(); // Limpa a lista antes de adicionar

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato da data

	            for (Tarefa tarefa : tarefasPendentes) {
	                if (!tarefa.isConcluida()) { // Apenas tarefas pendentes
	                    // Certifique-se de que a dataLimite está no formato correto
	                    String dataLimiteFormatada = tarefa.getDataLimite() != null 
	                        ? LocalDate.parse(tarefa.getDataLimite()).format(formatter)
	                        : "Sem Data Limite"; // Caso não haja data limite

	                    String tarefaFormatada = String.format("%s | %s | %s | %s | %s | %s | %s",
	                        LocalDate.parse(tarefa.getDataCriacao()).format(formatter), // Formata a data de criação
	                        tarefa.getStatus(),
	                        tarefa.getDescricao(),
	                        tarefa.getResponsavel(),
	                        tarefa.getCliente(),
	                        tarefa.getProcesso(), // Adiciona o processo
	                        dataLimiteFormatada); // Usa a data limite formatada
	                        
	                    tarefasPendentesList.getItems().add(tarefaFormatada); // Adiciona a tarefa formatada na lista
	                }
	            }
	        } catch (SQLException e) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Erro ao Carregar Tarefas");
	            alert.setHeaderText("Erro ao carregar a lista de tarefas");
	            alert.setContentText("Ocorreu um erro ao tentar carregar as tarefas.");
	            alert.showAndWait();
	            e.printStackTrace();
	        }
	    }
	   
	    @FXML
	    private void editarTarefa() {
	        // Obtendo a tarefa selecionada na ListView
	        String tarefaSelecionada = tarefasPendentesList.getSelectionModel().getSelectedItem();

	        if (tarefaSelecionada != null) {
	            // Divide a string da tarefa selecionada em partes separadas pelo delimitador " | "
	            String[] partes = tarefaSelecionada.split(" \\| ");

	            if (partes.length == 7) {
	                // Partes da string: dataCriacao | status | descricao | responsavel | processo | dataLimite
	                String dataCriacao = partes[0].trim(); // Data de criação
	                String descricao = partes[2].trim();   // Descrição da tarefa

	                // Preenchendo os campos da aba de edição com os valores da tarefa
	                descricaoFieldTab1.setText(descricao); // Preenche o campo de descrição
	                responsavelField.setText(partes[3].trim()); // Preenche o campo de responsável
	                clienteFieldTab1.setText(partes[4].trim()); // Preenche o campo de cliente
	                processoFieldTab1.setText(partes[5].trim()); // Preenche o campo de processo
	                txtStatusEdit1.setText(partes[1].trim()); // Preenche o campo de status

	                // Buscar o ID da tarefa no banco de dados com base na data de criação e descrição
	                try {
	                    TarefaDao tarefaDao = new TarefaDao();
	                    int idTarefa = tarefaDao.buscarIdTarefaPorDataDescricao(dataCriacao, descricao);

	                    if (idTarefa != -1) {
	                        labelIDTarefa.setText(String.valueOf(idTarefa)); // Preenche o Label com o ID da tarefa

	                        // Chamar o método para buscar a data limite e preencher o campo
	                        String dataLimite = tarefaDao.getDataLimiteById(idTarefa);
	                        if (dataLimite != null) {
	                            dataLimiteFieldTab1.setText(LocalDate.parse(dataLimite).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	                        } else {
	                            dataLimiteFieldTab1.setText(""); // Limpa o campo se não houver data limite
	                        }
	                    } else {
	                        labelIDTarefa.setText("ID não encontrado"); // Caso o ID não seja encontrado
	                    }

	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Erro ao Buscar ID");
	                    alert.setHeaderText("Erro ao buscar o ID da tarefa");
	                    alert.setContentText("Ocorreu um erro ao tentar buscar o ID da tarefa no banco de dados.");
	                    alert.showAndWait();
	                }
	            }
	        } else {
	            // Se nenhuma tarefa foi selecionada, pode exibir um alerta, se desejado
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Nenhuma Tarefa Selecionada");
	            alert.setHeaderText("Selecione uma tarefa para editar");
	            alert.setContentText("Por favor, selecione uma tarefa da lista antes de tentar editá-la.");
	            alert.showAndWait();
	        }
	    }
	    
	    private void marcarComoFeito() {
	        // Obtendo a tarefa selecionada na ListView
	        String tarefaSelecionada = tarefasPendentesList.getSelectionModel().getSelectedItem();

	        if (tarefaSelecionada != null) {
	            // Divide a string da tarefa selecionada em partes separadas pelo delimitador " | "
	            String[] partes = tarefaSelecionada.split(" \\| ");

	            if (partes.length == 7) {
	                // Partes da string: dataCriacao | status | descricao | responsavel | processo | dataLimite
	                String dataCriacao = partes[0].trim(); // Data de criação
	                String descricao = partes[2].trim();   // Descrição da tarefa

	                // Buscar o ID da tarefa no banco de dados com base na data de criação e descrição
	                try {
	                    TarefaDao tarefaDao = new TarefaDao();
	                    int idTarefa = tarefaDao.buscarIdTarefaPorDataDescricao(dataCriacao, descricao);

	                    // Verifica se o ID da tarefa foi encontrado
	                    if (idTarefa != -1) {
	                        // Chama o método para marcar a tarefa como feita
	                        tarefaDao.marcarComoFeito(idTarefa);
	                        
	                        // Aqui você pode adicionar um código para atualizar a ListView, se necessário
	                        carregarTarefasPendentes(); // Método hipotético para atualizar a ListView
	                    } else {
	                       
	                    }
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                
	            }
	        } else {
	            
	        }
	    }
	   
	    private void carregarTarefasConcluidas() {
	        try {
	            TarefaDao tarefaDao = new TarefaDao();
	            List<Tarefa> tarefasConcluidas = tarefaDao.getTarefasConcluidas(); // Recupera as tarefas concluídas

	            tarefasFeitasList.getItems().clear(); // Limpa a lista antes de adicionar

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato da data

	            for (Tarefa tarefa : tarefasConcluidas) {
	                // Certifique-se de que a dataLimite está no formato correto
	                String dataLimiteFormatada = tarefa.getDataLimite() != null 
	                    ? LocalDate.parse(tarefa.getDataLimite()).format(formatter)
	                    : "Sem Data Limite"; // Caso não haja data limite

	                // Formatação da tarefa para exibir na lista
	                String tarefaFormatada = String.format("%s | %s | %s | %s | %s | %s | %s",
	                    LocalDate.parse(tarefa.getDataCriacao()).format(formatter), // Formata a data de criação
	                    tarefa.getStatus(), // Status da tarefa
	                    tarefa.getDescricao(), // Descrição da tarefa
	                    tarefa.getResponsavel(), // Responsável
	                    tarefa.getCliente(), // Cliente
	                    tarefa.getProcesso(), // Processo
	                    dataLimiteFormatada); // Usa a data limite formatada

	                tarefasFeitasList.getItems().add(tarefaFormatada); // Adiciona a tarefa formatada na lista
	            }
	        } catch (SQLException e) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Erro ao Carregar Tarefas Concluídas");
	            alert.setHeaderText("Erro ao carregar a lista de tarefas concluídas");
	            alert.setContentText("Ocorreu um erro ao tentar carregar as tarefas concluídas.");
	            alert.showAndWait();
	            e.printStackTrace();
	        }
	    }
	    
	    private void marcarComoPendente() {
	        // Obtendo a tarefa selecionada na ListView de tarefas concluídas
	        String tarefaSelecionada = tarefasFeitasList.getSelectionModel().getSelectedItem();

	        if (tarefaSelecionada != null) {
	            // Divide a string da tarefa selecionada em partes separadas pelo delimitador " | "
	            String[] partes = tarefaSelecionada.split(" \\| ");

	            if (partes.length == 7) {
	                // Partes da string: dataCriacao | status | descricao | responsavel | processo | dataLimite
	                String dataCriacao = partes[0].trim(); // Data de criação
	                String descricao = partes[2].trim();   // Descrição da tarefa

	                // Buscar o ID da tarefa no banco de dados com base na data de criação e descrição
	                try {
	                    TarefaDao tarefaDao = new TarefaDao();
	                    int idTarefa = tarefaDao.buscarIdTarefaPorDataDescricao(dataCriacao, descricao);

	                    // Verifica se o ID da tarefa foi encontrado
	                    if (idTarefa != -1) {
	                        // Chama o método para marcar a tarefa como pendente
	                        tarefaDao.marcarComoPendente(idTarefa);

	                        // Atualiza as listas de tarefas pendentes e concluídas
	                        carregarTarefasPendentes(); // Método para atualizar a lista de tarefas pendentes
	                        carregarTarefasConcluidas(); // Método para atualizar a lista de tarefas concluídas
	                    } else {
	                        exibirAlerta("Erro", "Tarefa não encontrada no banco de dados.");
	                    }
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                exibirAlerta("Erro", "A tarefa selecionada está com dados incompletos.");
	            }
	        } else {
	            exibirAlerta("Erro", "Nenhuma tarefa selecionada.");
	        }
	    }

	    private void exibirAlerta(String titulo, String mensagem) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Alerta de informação, pode ser alterado para ERROR se necessário
	        alert.setTitle(titulo); // Define o título do alerta
	        alert.setHeaderText(null); // Cabeçalho do alerta, pode ser removido ou alterado
	        alert.setContentText(mensagem); // Define a mensagem do alerta
	        alert.showAndWait(); // Exibe o alerta e aguarda a ação do usuário
	    }
	   

	    // Método para carregar a lista de tarefas concluídas
	    private void carregarTarefasConcluidasBTN() {
	        try {
	            TarefaDao tarefaDao = new TarefaDao();
	            List<Tarefa> tarefasConcluidas = tarefaDao.getTarefasConcluidas(); // Recupera as tarefas concluídas

	            tarefasFeitasList.getItems().clear(); // Limpa a lista antes de adicionar

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato da data

	            for (Tarefa tarefa : tarefasConcluidas) {
	                // Certifique-se de que a dataLimite está no formato correto
	                String dataLimiteFormatada = tarefa.getDataLimite() != null 
	                    ? LocalDate.parse(tarefa.getDataLimite()).format(formatter)
	                    : "Sem Data Limite"; // Caso não haja data limite

	                String tarefaFormatada = String.format("%s | %s | %s | %s | %s | %s | %s",
	                    LocalDate.parse(tarefa.getDataCriacao()).format(formatter), // Formata a data de criação
	                    tarefa.getStatus(),
	                    tarefa.getDescricao(),
	                    tarefa.getResponsavel(),
	                    tarefa.getCliente(),
	                    tarefa.getProcesso(), // Adiciona o processo
	                    dataLimiteFormatada); // Usa a data limite formatada

	                tarefasFeitasList.getItems().add(tarefaFormatada); // Adiciona a tarefa formatada na lista
	            }
	        } catch (SQLException e) {
	            exibirAlerta("Erro ao Carregar Tarefas", "Erro ao carregar a lista de tarefas concluídas.");
	            e.printStackTrace();
	        }
	    }


	    @FXML
	    private void handleAtualizarTarefasFeitas() {
	    	carregarTarefasConcluidasBTN(); // Atualiza a lista de tarefas concluídas
	    }
	    
	    @FXML
	    private void buscarClientes() {
	        String textoBuscaCliente = txtNomeBusca.getText().trim();

	        try {
	            UserDao userDao = new UserDao();
	            ProcDao procDao = new ProcDao();
	            List<String> nomesClientes;

	            if (textoBuscaCliente.isEmpty()) {
	                // Se a busca estiver vazia, buscar todos os nomes
	                List<String> nomesUser = userDao.buscarTodosNomes(); // Você pode adicionar um método para buscar todos os nomes de usuário
	                List<String> nomesProc = procDao.buscarTodosNomes(); // Você pode adicionar um método para buscar todos os nomes de processos

	                nomesClientes = new ArrayList<>(nomesUser);
	                nomesClientes.addAll(nomesProc);
	            } else {
	                // Se houver texto, buscar clientes por texto
	                List<String> nomesUser = userDao.buscarNomesClientesPorTextoUser(textoBuscaCliente);
	                List<String> nomesProc = procDao.buscarNomesClientesPorTexto(textoBuscaCliente);

	                nomesClientes = new ArrayList<>(nomesUser);
	                nomesClientes.addAll(nomesProc);
	            }

	            // Remover duplicatas e ordenar alfabeticamente
	            Set<String> nomesUnicos = new TreeSet<>(nomesClientes);
	            listClientesBusca.getItems().setAll(new ArrayList<>(nomesUnicos));
	        } catch (SQLException e) {
	            e.printStackTrace(); // Lidar com exceções adequadamente
	        }
	    }
	    
	    @FXML
	    private void onSelecionarCliente() {
	        String clienteSelecionado = listClientesBusca.getSelectionModel().getSelectedItem();

	        if (clienteSelecionado != null) {
	            clienteFieldTab2.setText(clienteSelecionado); // Preencher o TextField com o nome do cliente

	            try {
	                ProcDao procDao = new ProcDao();
	                List<String> numerosProcessos = procDao.buscarNumerosProcPorNomeCliente(clienteSelecionado);

	                if (!numerosProcessos.isEmpty()) {
	                    listProcNomes.getItems().setAll(numerosProcessos); // Adicionar os números dos processos à ListView
	                } else {
	                    listProcNomes.getItems().clear(); // Limpar a ListView se não houver processos
	                }
	            } catch (SQLException e) {
	                e.printStackTrace(); // Lidar com exceções adequadamente
	            }
	        }
	    }
	    
	    @FXML
	    private void onSelecionarProcesso() {
	        String processoSelecionado = listProcNomes.getSelectionModel().getSelectedItem();

	        if (processoSelecionado != null) {
	            processoFieldTab2.setText(processoSelecionado); // Preencher o TextField com o número do processo
	        }
	    }
	    
	    
	}