package guiWAdv;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Selic.SelicRateExtractor;
import infoCalculos.Calculos;
import infoCalculos.CalculosDao;
import infoCalculos.escolaNE;
import infoCalculos.professorescola;
import infoCalculos.professorescolaDao;
import infoCalculos.escolaNEDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Tela05CalculosController {
	
	private static final Logger logger = Logger.getLogger(com.WernerADV.Software.App.class.getName());
	private UserDao userDao = new UserDao();
	private ProcDao procDao = new ProcDao();
	private RecDao recDao = new RecDao();
	private HistoricoDao HistoricoDao = new HistoricoDao();
	private AndamentoRecDao andamentoRecDao;
	private escolaNEDao escolaNEDao;

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
	
	@FXML
	private void initialize() {
	   
		escolaNEDao = new escolaNEDao();
		  // Adicione um ouvinte para a seleção na ListView
		
		listClientesBuscaNE.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null) {
		        try {
		        	onSelecionarClienteNE();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		});
		
		
		
		 listMatriculasNE.setOnMouseClicked(event -> {
		        // Obtém o item selecionado da ListView
		        String selectedMatricula = listMatriculasNE.getSelectionModel().getSelectedItem();
		        
		        // Atualiza a TextField com a matrícula selecionada
		        txtRetornoMatriculaNE.setText(selectedMatricula);
		    });
		 
		 listClientesBuscaInterniveis.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			 onSelecionarClienteInterniveis(newValue); 
		    });
		 
		 listMatriculasInterniveis.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        onSelecionarMatriculaListInterniveis(newValue); // Chame o método onSelecionarMatriculaListInterniveis com o valor selecionado
		    });
		
		


		 
		 // Adicione um ouvinte de seleção à ListView
		    listEscolaBuscaNE.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        if (newValue != null) {
		            // Quando um item é selecionado, atualize as TextFields com os valores
		            String selectedText = newValue; // O texto selecionado da ListView

		            // Separe o nome da escola e a nota com base na estrutura do texto
		            String[] parts = selectedText.split(
		           
		" - ");

		            if (parts.length == 2) {
		                String nomeEscola = parts[0];
		                String notaEscola = parts[1];
		                
		             // Chame o método para obter idescolasNE
		                int idEscolaNE = escolaNEDao.buscarIdEscolaNE(nomeEscola, notaEscola);


		                // Defina os valores nas TextFields
		                txtRetornoEscolaNE.setText(nomeEscola);
		                txtRetornoNotaNE.setText(notaEscola);
		                
		                labelIDEscola.setText(Integer.toString(idEscolaNE));
		               
		            }
		        }
		    });
		

		            
		    btnGerarCalculoNE.setOnAction(event -> {
		        // Primeiro, adicione os cabeçalhos e os dados à planilha
		        try {
		            adicionarCabecalho();
		           
		            adicionarTabela();
		            
		           
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		        String nomeEscola = txtRetornoNomeNE.getText();
		        String matricula = txtRetornoMatriculaNE.getText();
		        String nomeArquivo = "CalcNE_" + nomeEscola + "_" + matricula + ".xlsx";

		        
		        // Em seguida, chame o método para salvar a planilha
		      
		        String caminhoDoArquivo = ("C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\calculos\\" + nomeArquivo);
		        
		        salvarPlanilhaEAbriLa(caminhoDoArquivo);
		        
		       
		        
		        // Crie um alerta informando que o documento foi salvo
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Documento Salvo");
		        alert.setHeaderText(null);
		        alert.setContentText("A Planilha foi salva com sucesso.");
		        alert.showAndWait();
		    });
		    
		    
		    
		    
		    
		 
		 
		 


	    
	    
	    
	    
	    
	}
	
	
                                                                     	// SELIC
	@FXML
	private TextField txtDataFinalSelic;
	@FXML
	private TextField txtValorFinalSelic;
	@FXML
	private Button btnCalculaSelic;
	
	 // Método para lidar com o evento do botão
    @FXML
    void calcularSelic(ActionEvent event) {
        // Obtenha a data final da SELIC do campo de texto
        String dataFinalSelic = txtDataFinalSelic.getText();

        // Obtenha a taxa SELIC atualizada usando o SelicRateExtractor
        SelicRateExtractor selicExtractor = new SelicRateExtractor();
        String dataInicial = "09122021"; // Data Inicial fornecida no exemplo
        String taxaSelic = selicExtractor.getTaxaSelicPeriod(dataInicial, dataFinalSelic);

        // Exiba a taxa SELIC atualizada no campo de texto de valor atualizado
        txtValorFinalSelic.setText(taxaSelic);
        System.out.println("Valor retornou");
    }
    
    
    
                       // ESTABELECER COMANDO DE BUSCA NOME CLIENTE E MATRÍCULA TEXTFIELD + LISTVIEW
    @FXML
    private void buscarNomesClientesNE() {
        String textoBuscaClienteNE = txtNomeBuscaNE.getText().trim();

        try {
            List<String> nomesClientesNE = userDao.buscarNomesClientesPorTextoUser(textoBuscaClienteNE);

            if (nomesClientesNE.isEmpty()) {
                listClientesBuscaNE.getItems().clear(); 
               
    // Limpa a lista se não houver resultados
            } else {
                listClientesBuscaNE.getItems().setAll(nomesClientesNE); 
               
    // Define os resultados na ListView
            }
        } catch (SQLException e) {
            // Lide com exceções de SQL aqui, se necessário
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onSelecionarClienteNE() throws SQLException {
        // Obtém o item selecionado da lista
        String selectedCliente = (String) listClientesBuscaNE.getSelectionModel().getSelectedItem();

        if (selectedCliente != null) {
            // Agora você pode chamar o método com selectedCliente
            List<String> matriculas = userDao.buscarMatriculasPorNome(selectedCliente);

            txtRetornoNomeNE.setText(selectedCliente);

            // Limpe a lista de matrículas e adicione as matrículas do cliente
            listMatriculasNE.getItems().clear();
            listMatriculasNE.getItems().addAll(matriculas);    
        }
    }
    
    @FXML
    private void onSelecionarMatriculaListNE() {
        // Obtém o item selecionado da lista de matrículas
        String selectedMatricula = listMatriculasNE.getSelectionModel().getSelectedItem();
        
        if (selectedMatricula != null) {
            // Preencha a TextField com a matrícula selecionada
            txtRetornoMatriculaNE.setText(selectedMatricula);
        } else {
            // Lidar com o caso em que nenhuma matrícula foi selecionada
        }
    }

    // Estabelecendo método para salvar dados escola
    
    @FXML
    private void inserirEscolaNE(ActionEvent event) {
        String nomeEscola = txtRetornoEscolaNE.getText();
        String notaEscola = txtRetornoNotaNE.getText();

        if (!nomeEscola.isEmpty() && !notaEscola.isEmpty()) {
            escolaNE escola = new escolaNE();
            escola.setNomeEscola(nomeEscola);
            escola.setNotaEscola(notaEscola);

            try {
                // Salve a escola no banco de dados usando o seu UserDao (substitua pelo nome do seu DAO)
                escolaNEDao escolaNEDao = new escolaNEDao(); // Substitua pelo seu DAO
                escolaNEDao.saveEscolaNE(escola);

                

             // Exibir um alerta de sucesso
                exibirAlerta("Sucesso", "Escola cadastrada com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
                // Exibir um alerta de erro
                exibirAlerta("Erro", "Ocorreu um erro ao cadastrar a escola: " + e.getMessage());
            }
        } else {
            // Lide com o caso em que os campos estão vazios
        }
    }

    private void exibirAlerta(String titulo, String conteudo) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
    
    

    @FXML
    private void buscarEscolasNE() {
        String textoBuscaEscolaNE = txtEscolaBuscaNE.getText().trim();

        try {
            List<escolaNE> escolasNE;

            escolaNEDao dao = new escolaNEDao(); // Crie uma instância do seu DAO

            if (textoBuscaEscolaNE.isEmpty()) {
                // Caso 1: Nada foi digitado na TextField, retornar todos os nomes cadastrados
                escolasNE = dao.getAllEscolasNE();
            } else {
                // Caso 2: Algo foi digitado na TextField, fazer uma busca por nome parcial
                escolasNE = dao.buscarEscolasNEPorNome(textoBuscaEscolaNE);
            }

            // Agora você pode atualizar a exibição das escolas, por exemplo, em uma ListView
            atualizarExibicaoEscolas(escolasNE);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lide com erros de SQL, se necessário
        }
    }


    private void atualizarExibicaoEscolas(List<escolaNE> escolas) {
        listEscolaBuscaNE.getItems().clear();
        
        for (escolaNE escola : escolas) {
            
        
    String textoExibicao = escola.getNomeEscola() + " - " + escola.getNotaEscola();
            listEscolaBuscaNE.getItems().add(textoExibicao);
        }
    }
    @FXML
    private void btnUsaEscolaAction() {
        // Obtenha os valores das TextFields
        String nomeEscola = txtRetornoEscolaNE.getText();
        String notaEscola = txtRetornoNotaNE.getText();

        // Defina os valores nas TextFields txtNomeEscola e txtNotaEscola
        txtNomeEscola.setText(nomeEscola);
        txtNotaEscola.setText(notaEscola);
    }



    
    
    
    //CALCULOS NOVA ESCOLA
    
    @FXML
    private TextField txtNomeBuscaNE;
    @FXML
    private ListView listClientesBuscaNE;
    @FXML
    private ListView<String> listMatriculasNE;
    @FXML
    private TextField txtRetornoNomeNE;
    @FXML
    private TextField txtRetornoMatriculaNE;
    @FXML
    private TextField txtNomeEscola;
    @FXML
    private TextField txtNotaEscola;
    @FXML
    private Button btnBuscaNomeNE;
    @FXML
    private Button btnGerarCalculoNE;
    @FXML
    private TextField txtDataDoCalculoNE;
    @FXML
    private TextField txtJurosNEAtualizados;
    @FXML
    private Button btnCalculaJurosNE;
    
    
   
    
    
    
    
                                   //CADASTRO E DATABASE ESCOLAS
    @FXML
    private TextField txtEscolaBuscaNE;
    @FXML
    private ListView<String> listEscolaBuscaNE;
    @FXML
    private TextField txtRetornoEscolaNE;
    @FXML
    private TextField txtRetornoNotaNE;
    @FXML
    private Button btnBuscaEscolaDB;
    @FXML
    private Button btnCadastraEscola;
    @FXML
    private Button btnUsaEscola;
    @FXML
    private TextField txtPrincipalLiquidoNE;
    @FXML
    private TextField txtDescontoPrevidenciaNE;
    @FXML
    private TextField txtTotalCalculoNE;
    @FXML
    private Button btnCadastrarProfEscNE;
    @FXML
    private Label labelIDEscola;
    
    
    
    											//cadastro vinculando nome professor e escola
    @FXML
    private void inserirProfessorescola(ActionEvent event) {
        String nomeEscola = txtRetornoEscolaNE.getText();
        String notaEscola = txtRetornoNotaNE.getText();
        String nomeProfessor = txtRetornoNomeNE.getText();
        int idescolasNE = Integer.parseInt(labelIDEscola.getText());
        
        if (idescolasNE != -1 && !nomeEscola.isEmpty() && !notaEscola.isEmpty() && !nomeProfessor.isEmpty()) {
            professorescola escola = new professorescola();
            escola.setNomeEscolaNE(nomeEscola);
            escola.setNotaEscolaNE(notaEscola);
            escola.setNome(nomeProfessor);
            escola.setIdescolasNE(idescolasNE);

            try {
                // Salve no banco de dados
                professorescolaDao professorescolaDao = new professorescolaDao();
                professorescolaDao.saveprofessorescola(escola);

               
                // Exibir um alerta de sucesso
                exibirAlerta("Sucesso", "Vinculo realizado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
                // Exibir um alerta de erro
                exibirAlerta("Erro", "Ocorreu um erro realizar o vinculo: " + e.getMessage());
            }
        } else {
            // Lide com o caso em que os campos estão vazios ou o idescolasNE não foi obtido
        }
    }


    
    //CADASTRO CALCULOS
    @FXML
    private TextField txtTemaCalculoNE;
    @FXML
    private Button btnCadastrarCalcNE;
    
    @FXML
    private void inserirCalculoNEdataBase(ActionEvent event) {
        String valorCalcNE = txtPrincipalLiquidoNE.getText();
        String rioPrevNE = txtDescontoPrevidenciaNE.getText();
        String valorTotalNE = txtTotalCalculoNE.getText();
        String nome = txtRetornoNomeNE.getText();
    	String tema = txtTemaCalculoNE.getText();
    	String matricula = txtRetornoMatriculaNE.getText();

        if (!valorCalcNE.isEmpty() && !rioPrevNE.isEmpty() && !valorTotalNE.isEmpty() && !nome.isEmpty() && !tema.isEmpty() && !matricula.isEmpty())
        
        {
        	Calculos calculos = new Calculos();
        	calculos.setNome(nome);
        	calculos.setTema(tema);
        	calculos.setMatricula(matricula);
        	calculos.setValorPrincipal(valorCalcNE);
        	calculos.setRioPrev(rioPrevNE);
        	calculos.setValorFinal(valorTotalNE);

            try {
                // Salve no banco de dados 
            	CalculosDao CalculosDao = new CalculosDao(); 
            	CalculosDao.saveCalculos(calculos);

            	

             // Exibir um alerta de sucesso
                exibirAlerta("Sucesso", "Cálculo cadastrado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
                // Exibir um alerta de erro
                exibirAlerta("Erro", "Ocorreu um erro ao cadastrar o Cálculo: " + e.getMessage());
            }
        } else {
            // Lide com o caso em que os campos estão vazios
        }
    }
    
    
    //LIMPAR AÇAO
    @FXML
    private Button btnLimparTelaCalculoNE;
    
    @FXML
	private void limparTelaCalculoNE(ActionEvent event) {
    	txtRetornoEscolaNE.clear();
        txtRetornoNotaNE.clear();
        txtPrincipalLiquidoNE.clear();
        txtDescontoPrevidenciaNE.clear();
        txtTotalCalculoNE.clear();
        txtRetornoEscolaNE.clear();
        txtRetornoNotaNE.clear();
        txtRetornoNomeNE.clear();
        labelIDEscola.setText("");
        txtRetornoEscolaNE.clear();
        txtRetornoNotaNE.clear();
    }
    
    
    
    
    
    
    
    
    
    //calcular juros NE
    @FXML
    private void calcularJurosNE() throws ParseException {
    
    	String dataCalculoStr = txtDataDoCalculoNE.getText();
    	LocalDate dataCalculo = LocalDate.parse(dataCalculoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    	LocalDate dataReferencia = LocalDate.of(2007, 2, 6);
    	long diferencaDias = ChronoUnit.DAYS.between(dataReferencia, dataCalculo);
    	Date dataCalculo1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataDoCalculoNE.getText());
    	long diferencaDias1 = (long)diferencaDias *360/365;
    	double numeroAnos = (double)diferencaDias1/360;
    	double numeroMeses = (double)numeroAnos*12;    	
    	double indiceJuros = (double)numeroMeses*0.50;
    	
    	  	    
    	        	    // Exibir o valor do índice calculado na TextField txtJurosNEAtualizados
    	txtJurosNEAtualizados.setText(String.valueOf(indiceJuros + "%"));

    	}


    
    
    
    
    
    
                                              //GERAR PLANILHA
                                             //GERAR PLANILHA NE
    private Workbook workbook;
    private Sheet sheet;
    
    
  
    
    public Tela05CalculosController() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("MinhaPlanilha");
        

        // Defina a largura das colunas conforme necessário
        sheet.autoSizeColumn(18);
    }

        
    public void adicionarCabecalho() throws ParseException {
    	
    	// DETERMINANDO NEGRITO
    	CellStyle boldStyle = workbook.createCellStyle();
    	Font boldFont = workbook.createFont();
    	boldFont.setBold(true);
    	boldStyle.setFont(boldFont);
    	
    	//DETERMINANDO COR DE FUNDO
    	CellStyle collorStyle = workbook.createCellStyle();
    	collorStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
    	collorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    	//COMBINANDO ESTILOS DE CELULAS
    	XSSFCellStyle combinedStyle = (XSSFCellStyle) workbook.createCellStyle();
    	combinedStyle.cloneStyleFrom(boldStyle);
    	combinedStyle.cloneStyleFrom(collorStyle);
    	

    	
    	
    	 // Linha 1
        Row row1 = sheet.createRow(0);
        row1.createCell(2).setCellValue("NOTA");
        row1.getCell(2).setCellStyle(combinedStyle);

        // Substitua 'txtNotaEscola' com o valor apropriado.
        String NotaEscola = txtNotaEscola.getText(); // Exemplo de valor
        int notaEscola = Integer.parseInt(NotaEscola);
        row1.createCell(3).setCellValue(notaEscola);

        // Linha 2
        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("Nome");
        row2.getCell(0).setCellStyle(combinedStyle);
       
        // Substitua 'txtRetornoNomeNE' com o valor apropriado.
        String RetornoNomeNE = txtRetornoNomeNE.getText(); // Exemplo de valor
        row2.createCell(1).setCellValue(RetornoNomeNE);

        // Linha 3
        Row row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("Matrícula");
        row3.getCell(0).setCellStyle(combinedStyle);
        
        // Substitua 'txtRetornoMatriculaNE' com o valor apropriado.
        String RetornoMatriculaNE = txtRetornoMatriculaNE.getText(); // Exemplo de valor
        row3.createCell(1).setCellValue(RetornoMatriculaNE);
        
        row3.createCell(5).setCellValue("SELIC");
        row3.getCell(5).setCellStyle(combinedStyle);

        String RetornoSelic = txtValorFinalSelic.getText();
        row3.createCell(6).setCellValue(RetornoSelic);
        row3.getCell(6).setCellStyle(boldStyle);
           
        // Linha 4
        Row row4 = sheet.createRow(3);
        row4.createCell(0).setCellValue("Data Correção");
        row4.getCell(0).setCellStyle(combinedStyle);
        
        row4.createCell(1).setCellValue("01/01/2003 até ");
        row4.createCell(2).setCellValue(" data do cálculo"); 
     

     // Linha 5
        Row row5 = sheet.createRow(4);
        row5.createCell(0).setCellValue("Data do Cálculo");
        row5.getCell(0).setCellStyle(combinedStyle);
        
        // Substitua 'txtDataDoCalculoNE' com o valor apropriado.
        //String txtDataDoCalculoNE = "09/12/2021"; // Exemplo de valor
        String dataEfetivaCalculo = txtDataDoCalculoNE.getText();
        Date dataCalculo = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataDoCalculoNE.getText());
     // Data de referência
        Date dataReferencia = new SimpleDateFormat("dd/MM/yyyy").parse("06/02/2007");
        row5.createCell(1).setCellValue(dataEfetivaCalculo);
        row5.getCell(1).setCellStyle(boldStyle);
        

        // Linha 6
        Row row6 = sheet.createRow(5);
        row6.createCell(0).setCellValue("Juros");
        row6.getCell(0).setCellStyle(combinedStyle);
        
        row6.createCell(1).setCellValue("06% a.a.");
        row6.getCell(1).setCellStyle(boldStyle);

        // Cálculo da quantidade de dias
        long diferencaDias = (dataCalculo.getTime() - dataReferencia.getTime()) / (1000 * 60 * 60 * 24);
        long difDias = diferencaDias *360/365;
        row6.createCell(2).setCellValue(difDias + " dias");
        row6.getCell(2).setCellStyle(boldStyle);
    }
    
    public void adicionarTabela() throws ParseException {
    	
    	// DETERMINANDO NEGRITO
    	CellStyle boldStyle = workbook.createCellStyle();
    	Font boldFont = workbook.createFont();
    	boldFont.setBold(true);
    	boldStyle.setFont(boldFont);
    	
    	//DETERMINANDO COR DE FUNDO
    	CellStyle collorStyle = workbook.createCellStyle();
    	collorStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
    	collorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    	
    	//COMBINANDO ESTILOS DE CELULAS
    	XSSFCellStyle combinedStyle = (XSSFCellStyle) workbook.createCellStyle();
    	combinedStyle.cloneStyleFrom(boldStyle);
    	combinedStyle.cloneStyleFrom(collorStyle);
    	
    	// Cabecalho
    	Row row8 = sheet.createRow(7);
        row8.createCell(0).setCellValue("mês/ano");
        row8.getCell(0).setCellStyle(combinedStyle);
        
        row8.createCell(1).setCellValue("Valor devido");
        row8.getCell(1).setCellStyle(combinedStyle);
       
        row8.createCell(2).setCellValue("IPCA-E");
        row8.getCell(2).setCellStyle(combinedStyle);
       
        row8.createCell(3).setCellValue("Atualizado IPCA-E");
        row8.getCell(3).setCellStyle(combinedStyle);
     
        row8.createCell(4).setCellValue("SELIC");
        row8.getCell(4).setCellStyle(combinedStyle);
    
        row8.createCell(5).setCellValue("Atualizado Selic");
        row8.getCell(5).setCellStyle(combinedStyle);
     
        row8.createCell(6).setCellValue("Data Juros");
        row8.getCell(6).setCellStyle(combinedStyle);
   
        row8.createCell(7).setCellValue("Taxa de Juros");
        row8.getCell(7).setCellStyle(combinedStyle);
       
        row8.createCell(8).setCellValue("Índice de juros");
        row8.getCell(8).setCellStyle(combinedStyle);
       
        row8.createCell(9).setCellValue("Valor dos Juros");
        row8.getCell(9).setCellStyle(combinedStyle);
      
        row8.createCell(10).setCellValue("Subtotal");
        row8.getCell(10).setCellStyle(combinedStyle);
        
        List<String> meses = List.of("jan/02", "fev/02", "mar/02", "abr/02", "mai/02", 
        		"jun/02", "jul/02", "ago/02", "set/02", "out/02", "nov/02", "dez/02", "13o/2002");
        
        List<Double> ipcaIndices = List.of(3.0118821, 2.9534047, 2.8901113, 2.8575354, 2.8253267, 
        		2.8015138, 2.795364, 2.8004047, 2.792864, 2.7770349, 2.7588266, 2.7541446, 2.7541446);
        
        Double valorTotal = 0d;
        
     // Obtenha o formato de moeda
        DataFormat dataFormat = workbook.createDataFormat();
        short currencyFormat = dataFormat.getFormat("R$ #,##0.00"); // Formato para moeda brasileira

        // Aplicar o formato de moeda às células
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(currencyFormat);
        
        
        for(int i=0; i<13; i++) {
        	int diferencaVencimento = 100 * Integer.parseInt(txtNotaEscola.getText());
        	double taxaIPCA = ipcaIndices.get(i);
        	double valorAtualizado1 = diferencaVencimento * taxaIPCA;
        	String selicText = txtValorFinalSelic.getText();
        	selicText = selicText.replace(",", ".");
        	double selic = Double.parseDouble(selicText);
        	double valorAtualizado2 = valorAtualizado1 * selic;
        	String dataCalculoStr = txtDataDoCalculoNE.getText();
        	LocalDate dataCalculo = LocalDate.parse(dataCalculoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        	LocalDate dataReferencia = LocalDate.of(2007, 2, 6);
        	long diferencaDias = ChronoUnit.DAYS.between(dataReferencia, dataCalculo);
        	Date dataCalculo1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataDoCalculoNE.getText());
        	long diferencaDias1 = (long)diferencaDias *360/365;
        	double numeroAnos = (double)diferencaDias1/360;
        	double numeroMeses = (double)numeroAnos*12;    	
        	double indiceJuros = (double)numeroMeses*0.50;
        	double valorJuros = valorAtualizado2 * indiceJuros /100;
        	double valorTotalLinha = valorAtualizado2 + valorJuros;
        	valorTotal += valorTotalLinha;
        	
        	Row row = sheet.createRow(9 + i);
        
        	row.createCell(0).setCellValue(meses.get(i));
        	row.getCell(0).setCellStyle(combinedStyle); 
        	row.createCell(1).setCellValue(diferencaVencimento);
        	row.getCell(1).setCellStyle(currencyStyle);
        	row.createCell(2).setCellValue(taxaIPCA);
        	row.createCell(3).setCellValue(valorAtualizado1);
        	row.getCell(3).setCellStyle(currencyStyle);
        	row.createCell(4).setCellValue(selic);
        	row.createCell(5).setCellValue(valorAtualizado2);
        	row.getCell(5).setCellStyle(currencyStyle);
        	row.createCell(6).setCellValue("06/02/2007 - " + txtDataDoCalculoNE.getText());
        	row.createCell(7).setCellValue("6% a.a.");
        	row.createCell(8).setCellValue(indiceJuros + "%");
        	row.createCell(9).setCellValue(valorJuros);
        	row.getCell(9).setCellStyle(currencyStyle);
        	row.createCell(10).setCellValue(valorTotalLinha);
        	row.getCell(10).setCellStyle(currencyStyle);
        	
        	
       }
      
       Row row22 = sheet.createRow(22);
       for(int i=0; i<11; i++) {
    	   if(i==0) {
    		   row22.createCell(i).setCellValue("Total");
    		   row22.getCell(i).setCellStyle(combinedStyle);
    		   
    	   } else if(i==10) {
    		   row22.createCell(i).setCellValue(valorTotal);
    		   row22.getCell(i).setCellStyle(currencyStyle);
    	   } else  {
    		   row22.createCell(i).setCellValue("");
    	   }
       }
       Row row25 = sheet.createRow(24);
       row25.createCell(0).setCellValue("Resumo");
       row25.getCell(0).setCellStyle(combinedStyle);
     
  	
       Row row26 = sheet.createRow(25);
       row26.createCell(0).setCellValue("Principal Líquido");
       row26.getCell(0).setCellStyle(combinedStyle);
    
       row26.createCell(1).setCellValue(valorTotal);
       row26.getCell(1).setCellStyle(currencyStyle);
       
       
       Row row27 = sheet.createRow(26);
       row27.createCell(0).setCellValue("Rioprevidencia");
       row27.getCell(0).setCellStyle(combinedStyle);
   
       double rioprev = valorTotal*11/100;
       row27.createCell(1).setCellValue(rioprev);
       row27.getCell(1).setCellStyle(currencyStyle);
       
       
       Row row28 = sheet.createRow(27);
       row28.createCell(0).setCellValue("Principal Líquido");
       row28.getCell(0).setCellStyle(combinedStyle);
       
       double valorAcao = valorTotal- rioprev;
       row28.createCell(1).setCellValue(valorAcao);
       row28.getCell(1).setCellStyle(currencyStyle);
       
       
       
       Row row30 = sheet.createRow(29);
       row30.createCell(0).setCellValue("Base Correção");
       row30.getCell(0).setCellStyle(boldStyle);
       
       Row row31 = sheet.createRow(30);
       row31.createCell(0).setCellValue("IPCA-E da data ");
       row31.getCell(0).setCellStyle(boldStyle);
       row31.createCell(1).setCellValue("devida até 08/12/2021");
       row31.getCell(1).setCellStyle(boldStyle);
       
       Row row32 = sheet.createRow(31);
       row32.createCell(0).setCellValue("SELIC a partir");
       row32.getCell(0).setCellStyle(boldStyle);
       row32.createCell(1).setCellValue("de 09/12/2021");
       row32.getCell(1).setCellStyle(boldStyle);
       
       
       txtPrincipalLiquidoNE.setText(String.format("R$ %.2f", valorTotal));
       txtDescontoPrevidenciaNE.setText(String.format("R$ %.2f", rioprev));
       txtTotalCalculoNE.setText(String.format("R$ %.2f", valorAcao));
    }
    
   
        
    

    public void salvarPlanilhaEAbriLa(String caminhoDoArquivo) {
        try (FileOutputStream fileOut = new FileOutputStream(caminhoDoArquivo)) {
            workbook.write(fileOut);
            
            // Após salvar a planilha, abra-a com o aplicativo padrão
            File file = new File(caminhoDoArquivo);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    
   
 


   
    
    
    														//CALCULOS INTERNIVEIS
    
    
    @FXML
    private TextField txtTemaCalculoInterniveis;
    @FXML
    private TextField txtNomeBuscaInterniveis;
    @FXML
    private Button btnBuscaNomeInterniveis;
    @FXML
    private ListView<String> listClientesBuscaInterniveis;
    @FXML
    private TextField txtRetornoNomeInterniveis;
    @FXML
    private ListView<String> listMatriculasInterniveis;
    @FXML
    private TextField txtRetornoMatriculaInterniveis;
    
    @FXML
    private TextField txtRefProfessor;
    @FXML
    private TextField txtDataInicioProfessor;
    @FXML
    private TextArea txaDataseNiveisCal;
    @FXML
    private Button btnCalcDatasNiveis;
    
    @FXML
    private void buscarNomesClientesInterniveis() {
        String textoBuscaClienteInterniveis = txtNomeBuscaInterniveis.getText().trim();

        try {
            List<String> nomesClientesInterniveis = userDao.buscarNomesClientesPorTextoUser(textoBuscaClienteInterniveis);

            if (nomesClientesInterniveis.isEmpty()) {
                listClientesBuscaInterniveis.getItems().clear(); 
               
    // Limpa a lista se não houver resultados
            } else {
                listClientesBuscaInterniveis.getItems().setAll(nomesClientesInterniveis); 
               
    // Define os resultados na ListView
            }
        } catch (SQLException e) {
            // Lide com exceções de SQL aqui, se necessário
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onSelecionarClienteInterniveis(String nomeCliente) {
        UserDao userDao = new UserDao();
        try {
            User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(nomeCliente);

            if (clienteSelecionado != null) {
                // Verifique se mat1 não está vazio
                if (!clienteSelecionado.getmat1().isEmpty()) {
                    listMatriculasInterniveis.getItems().add("Matrícula 1: " + clienteSelecionado.getmat1());
                }

                if (clienteSelecionado.getmat2() != null && !clienteSelecionado.getmat2().isEmpty()) {
                    listMatriculasInterniveis.getItems().add("Matrícula 2: " + clienteSelecionado.getmat2());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSelecionarMatriculaListInterniveis(String selecao) {
        if (selecao != null) {
            // Extrai a parte relevante da linha (por exemplo, "Matrícula 1" ou "Matrícula 2")
            String parteRelevante = selecao.split(":")[0].trim();

            UserDao userDao = new UserDao(); // Crie um objeto UserDao se já não existir

            try {
                User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(txtRetornoNomeInterniveis.getText()); // Obtém o nome do cliente selecionado na ComboBox

                if (clienteSelecionado != null) {
                    if (parteRelevante.equals("Matrícula 1")) {
                        // Preencha as TextFields com os dados correspondentes a mat1
                        txtRetornoMatriculaInterniveis.setText(clienteSelecionado.getmat1());
                        txtRefProfessor.setText(clienteSelecionado.getref1());
                        txtDataInicioProfessor.setText(clienteSelecionado.getdatainicio1());
                    } else if (parteRelevante.equals("Matrícula 2")) {
                        txtRetornoMatriculaInterniveis.setText(clienteSelecionado.getmat2());
                        txtRefProfessor.setText(clienteSelecionado.getref2());
                        txtDataInicioProfessor.setText(clienteSelecionado.getdatainicio2());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Limpe as TextFields se nenhum item for selecionado
            txtRetornoMatriculaInterniveis.clear();
            txtRefProfessor.clear();
            txtDataInicioProfessor.clear();
        }
    }

    



    
    
    
    
    
    
    
    
    
    
    
    
    public void calcularNiveisInterniveis(ActionEvent event) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	    String dataInicioStr = txtDataInicioProfessor.getText();
	    String referencia = txtRefProfessor.getText();
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

	    txaDataseNiveisCal.setText(resultado); // Defina o resultado na TextArea.
	}

	public static Date adicionarAnos(Date data, int anos) {
	    java.util.Calendar cal = java.util.Calendar.getInstance();
	    cal.setTime(data);
	    cal.add(java.util.Calendar.YEAR, anos);
	    return cal.getTime();
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    



        
	
}
