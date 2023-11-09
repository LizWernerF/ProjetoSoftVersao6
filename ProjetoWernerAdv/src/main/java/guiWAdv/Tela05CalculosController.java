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
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Selic.SelicRateExtractor;
import infoCalculos.Calculos;
import infoCalculos.CalculosDao;
import infoCalculos.Demonstrativo_interniveis;
import infoCalculos.Demonstrativo_interniveisDao;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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

		listClientesBuscaInterniveis.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					onSelecionarClienteInterniveis(newValue);
				});

		setupListViewClickHandler();

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

			String caminhoDoArquivo = ("C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\calculos\\"
					+ nomeArquivo);

			salvarPlanilhaEAbriLa(caminhoDoArquivo);

			// Crie um alerta informando que o documento foi salvo
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Documento Salvo");
			alert.setHeaderText(null);
			alert.setContentText("A Planilha foi salva com sucesso.");
			alert.showAndWait();
		});

		btnGeraPLanilhaInterniveis.setOnAction(event -> {
			// Primeiro, adicione os cabeçalhos e os dados à planilha
			try {

				String dataDoCalculoText = txtDataDoCalculoInter.getText();
				String nivelInicio = txtNivelInicio.getText();
				String dataNivelInicioText = txtDataNivelInicio.getText();
				int nivelinicio = Integer.parseInt(nivelInicio);
				int nivelFinal = nivelinicio + 1;
				String nivelFim = String.valueOf(nivelFinal);
				Demonstrativo_interniveisDao dao = new Demonstrativo_interniveisDao();
				List<Demonstrativo_interniveis> valores = dao.buscarPorDataENivel(dataNivelInicioText, nivelInicio,
						dataNivelInicioText, nivelFim);

				adicionarCabecalhoCalculoInterniveis();

				adicionarTabelaCalculoInterniveis();

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String nomeProfessor = txtRetornoNomeInterniveis.getText();
			String matricula = txtRetornoMatriculaInterniveis.getText();
			String nomeArquivo = "Calc_Interniveis_" + nomeProfessor + "_" + matricula + ".xlsx";

			// Em seguida, chame o método para salvar a planilha

			String caminhoDoArquivo = ("C:\\Users\\lizwf\\OneDrive\\Área de Trabalho\\Curso de Java\\ProjetoWernerAdv\\src\\main\\java\\guiWAdv\\calculos\\"
					+ nomeArquivo);

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
				// Salve a escola no banco de dados usando o seu UserDao (substitua pelo nome do
				// seu DAO)
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

			// Agora você pode atualizar a exibição das escolas, por exemplo, em uma
			// ListView
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

	// CALCULOS NOVA ESCOLA

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

	// CADASTRO E DATABASE ESCOLAS
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

	// cadastro vinculando nome professor e escola
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

	// CADASTRO CALCULOS
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

		if (!valorCalcNE.isEmpty() && !rioPrevNE.isEmpty() && !valorTotalNE.isEmpty() && !nome.isEmpty()
				&& !tema.isEmpty() && !matricula.isEmpty())

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

	// LIMPAR AÇAO
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

	// calcular juros NE
	@FXML
	private void calcularJurosNE() throws ParseException {

		String dataCalculoStr = txtDataDoCalculoNE.getText();
		LocalDate dataCalculo = LocalDate.parse(dataCalculoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate dataReferencia = LocalDate.of(2007, 2, 6);
		long diferencaDias = ChronoUnit.DAYS.between(dataReferencia, dataCalculo);
		Date dataCalculo1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataDoCalculoNE.getText());
		long diferencaDias1 = (long) diferencaDias * 360 / 365;
		double numeroAnos = (double) diferencaDias1 / 360;
		double numeroMeses = (double) numeroAnos * 12;
		double indiceJuros = (double) numeroMeses * 0.50;

		// Exibir o valor do índice calculado na TextField txtJurosNEAtualizados
		txtJurosNEAtualizados.setText(String.valueOf(indiceJuros + "%"));

	}

	// GERAR PLANILHA
	// GERAR PLANILHA NE
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

		// DETERMINANDO COR DE FUNDO
		CellStyle collorStyle = workbook.createCellStyle();
		collorStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		collorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Linha 1
		Row row1 = sheet.createRow(0);
		row1.createCell(2).setCellValue("NOTA");
		row1.getCell(2).setCellStyle(collorStyle);

		// Substitua 'txtNotaEscola' com o valor apropriado.
		String NotaEscola = txtNotaEscola.getText(); // Exemplo de valor
		int notaEscola = Integer.parseInt(NotaEscola);
		row1.createCell(3).setCellValue(notaEscola);

		// Linha 2
		Row row2 = sheet.createRow(1);
		row2.createCell(0).setCellValue("Nome");
		row2.getCell(0).setCellStyle(collorStyle);

		// Substitua 'txtRetornoNomeNE' com o valor apropriado.
		String RetornoNomeNE = txtRetornoNomeNE.getText(); // Exemplo de valor
		row2.createCell(1).setCellValue(RetornoNomeNE);

		// Linha 3
		Row row3 = sheet.createRow(2);
		row3.createCell(0).setCellValue("Matrícula");
		row3.getCell(0).setCellStyle(collorStyle);

		// Substitua 'txtRetornoMatriculaNE' com o valor apropriado.
		String RetornoMatriculaNE = txtRetornoMatriculaNE.getText(); // Exemplo de valor
		row3.createCell(1).setCellValue(RetornoMatriculaNE);

		row3.createCell(5).setCellValue("SELIC");
		row3.getCell(5).setCellStyle(collorStyle);

		String RetornoSelic = txtValorFinalSelic.getText();
		row3.createCell(6).setCellValue(RetornoSelic);
		row3.getCell(6).setCellStyle(boldStyle);

		// Linha 4
		Row row4 = sheet.createRow(3);
		row4.createCell(0).setCellValue("Data Correção");
		row4.getCell(0).setCellStyle(collorStyle);

		row4.createCell(1).setCellValue("01/01/2003 até ");
		row4.createCell(2).setCellValue(" data do cálculo");

		// Linha 5
		Row row5 = sheet.createRow(4);
		row5.createCell(0).setCellValue("Data do Cálculo");
		row5.getCell(0).setCellStyle(collorStyle);

		// Substitua 'txtDataDoCalculoNE' com o valor apropriado.
		// String txtDataDoCalculoNE = "09/12/2021"; // Exemplo de valor
		String dataEfetivaCalculo = txtDataDoCalculoNE.getText();
		Date dataCalculo = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataDoCalculoNE.getText());
		// Data de referência
		Date dataReferencia = new SimpleDateFormat("dd/MM/yyyy").parse("06/02/2007");
		row5.createCell(1).setCellValue(dataEfetivaCalculo);
		row5.getCell(1).setCellStyle(boldStyle);

		// Linha 6
		Row row6 = sheet.createRow(5);
		row6.createCell(0).setCellValue("Juros");
		row6.getCell(0).setCellStyle(collorStyle);

		row6.createCell(1).setCellValue("06% a.a.");
		row6.getCell(1).setCellStyle(boldStyle);

		// Cálculo da quantidade de dias
		long diferencaDias = (dataCalculo.getTime() - dataReferencia.getTime()) / (1000 * 60 * 60 * 24);
		long difDias = diferencaDias * 360 / 365;
		row6.createCell(2).setCellValue(difDias + " dias");
		row6.getCell(2).setCellStyle(boldStyle);
	}

	public void adicionarTabela() throws ParseException {

		// DETERMINANDO NEGRITO
		CellStyle boldStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldStyle.setFont(boldFont);

		// DETERMINANDO COR DE FUNDO
		CellStyle collorStyle = workbook.createCellStyle();
		collorStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		collorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		

		// Cabecalho
		Row row8 = sheet.createRow(7);
		row8.createCell(0).setCellValue("mês/ano");
		row8.getCell(0).setCellStyle(collorStyle);

		row8.createCell(1).setCellValue("Valor devido");
		row8.getCell(1).setCellStyle(collorStyle);

		row8.createCell(2).setCellValue("IPCA-E");
		row8.getCell(2).setCellStyle(collorStyle);

		row8.createCell(3).setCellValue("Atualizado IPCA-E");
		row8.getCell(3).setCellStyle(collorStyle);

		row8.createCell(4).setCellValue("SELIC");
		row8.getCell(4).setCellStyle(collorStyle);

		row8.createCell(5).setCellValue("Atualizado Selic");
		row8.getCell(5).setCellStyle(collorStyle);

		row8.createCell(6).setCellValue("Data Juros");
		row8.getCell(6).setCellStyle(collorStyle);

		row8.createCell(7).setCellValue("Taxa de Juros");
		row8.getCell(7).setCellStyle(collorStyle);

		row8.createCell(8).setCellValue("Índice de juros");
		row8.getCell(8).setCellStyle(collorStyle);

		row8.createCell(9).setCellValue("Valor dos Juros");
		row8.getCell(9).setCellStyle(collorStyle);

		row8.createCell(10).setCellValue("Subtotal");
		row8.getCell(10).setCellStyle(collorStyle);

		List<String> meses = List.of("jan/02", "fev/02", "mar/02", "abr/02", "mai/02", "jun/02", "jul/02", "ago/02",
				"set/02", "out/02", "nov/02", "dez/02", "13o/2002");

		List<Double> ipcaIndices = List.of(3.0118821, 2.9534047, 2.8901113, 2.8575354, 2.8253267, 2.8015138, 2.795364,
				2.8004047, 2.792864, 2.7770349, 2.7588266, 2.7541446, 2.7541446);

		Double valorTotal = 0d;

		// Obtenha o formato de moeda
		DataFormat dataFormat = workbook.createDataFormat();
		short currencyFormat = dataFormat.getFormat("R$ #,##0.00"); // Formato para moeda brasileira

		// Aplicar o formato de moeda às células
		CellStyle currencyStyle = workbook.createCellStyle();
		currencyStyle.setDataFormat(currencyFormat);

		for (int i = 0; i < 13; i++) {
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
			long diferencaDias1 = (long) diferencaDias * 360 / 365;
			double numeroAnos = (double) diferencaDias1 / 360;
			double numeroMeses = (double) numeroAnos * 12;
			double indiceJuros = (double) numeroMeses * 0.50;
			double valorJuros = valorAtualizado2 * indiceJuros / 100;
			double valorTotalLinha = valorAtualizado2 + valorJuros;
			valorTotal += valorTotalLinha;

			Row row = sheet.createRow(9 + i);

			row.createCell(0).setCellValue(meses.get(i));
			row.getCell(0).setCellStyle(collorStyle);
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
		for (int i = 0; i < 11; i++) {
			if (i == 0) {
				row22.createCell(i).setCellValue("Total");
				row22.getCell(i).setCellStyle(collorStyle);

			} else if (i == 10) {
				row22.createCell(i).setCellValue(valorTotal);
				row22.getCell(i).setCellStyle(currencyStyle);
			} else {
				row22.createCell(i).setCellValue("");
			}
		}
		Row row25 = sheet.createRow(24);
		row25.createCell(0).setCellValue("Resumo");
		row25.getCell(0).setCellStyle(collorStyle);

		Row row26 = sheet.createRow(25);
		row26.createCell(0).setCellValue("Principal Líquido");
		row26.getCell(0).setCellStyle(collorStyle);

		row26.createCell(1).setCellValue(valorTotal);
		row26.getCell(1).setCellStyle(currencyStyle);

		Row row27 = sheet.createRow(26);
		row27.createCell(0).setCellValue("Rioprevidencia");
		row27.getCell(0).setCellStyle(collorStyle);

		double rioprev = valorTotal * 11 / 100;
		row27.createCell(1).setCellValue(rioprev);
		row27.getCell(1).setCellStyle(currencyStyle);

		Row row28 = sheet.createRow(27);
		row28.createCell(0).setCellValue("Principal Líquido");
		row28.getCell(0).setCellStyle(collorStyle);

		double valorAcao = valorTotal - rioprev;
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

	// CALCULOS INTERNIVEIS

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
	private TextField txtDataDoCalculoInter;
	@FXML
	private TextField txtDataFinalSelic1;
	@FXML
	private TextField txtValorFinalSelic1;
	@FXML
	private TextField txtJurosNEAtualizados1;
	@FXML
	private TextField txtPrincipalInterniveis;
	@FXML
	private TextField txtRioPrevInterniveis;
	@FXML
	private TextField txtValorFinalInterniveis;
	@FXML
	private Button btnCalculaSelicInterniveis;
	@FXML
	private Button btnCalculaJurosInterniveis;
	@FXML
	private Button btnCadastraCalculoInterniveis;
	@FXML
	private Button btnGeraPLanilhaInterniveis;

	@FXML
	private void buscarNomesClientesInterniveis() {
		String textoBuscaClienteInterniveis = txtNomeBuscaInterniveis.getText().trim();

		try {
			List<String> nomesClientesInterniveis = userDao
					.buscarNomesClientesPorTextoUser(textoBuscaClienteInterniveis);

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
		listMatriculasInterniveis.getItems().clear();
		try {
			User clienteSelecionado = userDao.buscaClientePeloNomeTabEditar(nomeCliente);

			if (clienteSelecionado != null) {

				txtRetornoNomeInterniveis.setText(clienteSelecionado.getnome());

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
	private void setupListViewClickHandler() {
		listMatriculasInterniveis.setOnMouseClicked(event -> {
			// Verifique se um item da ListView foi clicado
			Object itemSelecionado = listMatriculasInterniveis.getSelectionModel().getSelectedItem();

			if (itemSelecionado != null && itemSelecionado instanceof String) {
				// Converta o item selecionado para uma String
				String linhaSelecionada = (String) itemSelecionado;

				// Divida a linha pelo caractere '-' para extrair a matrícula
				String[] partes = linhaSelecionada.split(":");

				// Verifique se a linha foi dividida corretamente (deve haver pelo menos duas
				// partes)
				if (partes.length >= 2) {
					// Remova espaços em branco em excesso e obtenha a matrícula
					String matricula = partes[1].trim();
					String apresentacao = partes[0].trim();

					// Defina a matrícula na txtMatPetInter
					txtRetornoMatriculaInterniveis.setText(matricula);

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
									txtRefProfessor.setText(user.getref1());
									txtDataInicioProfessor.setText(user.getdatainicio1());
								} else if (matricula.equals(user.getmat2())) {
									// Matrícula corresponde a mat2
									txtRefProfessor.setText(user.getref2());
									txtDataInicioProfessor.setText(user.getdatainicio2());
								}
							}
						} else {
							// Matrícula não existe na base de dados, faça o que for necessário aqui
						}
					} catch (SQLException e) {
						// Trate exceções de SQL aqui
					}
				}
			}
		});
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

	@FXML
	private TextField txtDataNivelInicio;
	@FXML
	private TextField txtNivelInicio;

	// PLANILHA DE CALCULOS INTERNIVEIS
	public void adicionarCabecalhoCalculoInterniveis() throws ParseException {

		// DETERMINANDO NEGRITO
		CellStyle boldStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldStyle.setFont(boldFont);

		// Crie um estilo para alinhar o conteúdo à esquerda
		CellStyle leftAlignedStyle = workbook.createCellStyle();
		leftAlignedStyle.setAlignment(HorizontalAlignment.LEFT);

		// DETERMINANDO COR DE FUNDO
		CellStyle collorStyle = workbook.createCellStyle();
		collorStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		collorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		
		// Linha 1
		Row row1 = sheet.createRow(0);
		// Célula 5
		row1.createCell(3).setCellValue("NOME");
		row1.getCell(3).setCellStyle(collorStyle);
		String nomeProfessor = txtRetornoNomeInterniveis.getText();
		Cell cell4 = row1.createCell(4);
		cell4.setCellValue(nomeProfessor);
		// Crie um objeto CellRangeAddress para mesclar as células da coluna 5 e 6 na
		// primeira linha.
		CellRangeAddress mergedRegion4to5 = new CellRangeAddress(0, 0, 4, 5);
		// Aplique a formatação "merge e center" à região de células desejada (células 5
		// e 6).
		sheet.addMergedRegion(mergedRegion4to5);

		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		// Linha 2
		Row row2 = sheet.createRow(1);
		// Célula 5
		row2.createCell(3).setCellValue("MATRICULA");
		row2.getCell(3).setCellStyle(collorStyle);
		String matricula = txtRetornoMatriculaInterniveis.getText();
		Cell cell4Row2 = row2.createCell(4);
		cell4Row2.setCellValue(matricula);
		CellRangeAddress mergedRegion6to7Row2 = new CellRangeAddress(1, 1, 4, 5);
		sheet.addMergedRegion(mergedRegion6to7Row2);
		// Linha 3
		Row row3 = sheet.createRow(2);
		// Célula 5
		row3.createCell(3).setCellValue("DATA DE INICIO");
		row3.getCell(3).setCellStyle(collorStyle);
		String dataInicio = txtDataInicioProfessor.getText();
		Cell cell4Row3 = row3.createCell(4);
		cell4Row3.setCellValue(dataInicio);
		CellRangeAddress mergedRegion6to7Row3 = new CellRangeAddress(2, 2, 4, 5);
		sheet.addMergedRegion(mergedRegion6to7Row3);
		// Linha 4
		Row row4 = sheet.createRow(3);
		// Célula 5
		row4.createCell(3).setCellValue("REFERÊNCIA");
		row4.getCell(3).setCellStyle(collorStyle);
		String referencia = txtRefProfessor.getText();
		Cell cell4Row4 = row4.createCell(4);
		cell4Row4.setCellValue(referencia);
		// Crie um objeto CellRangeAddress para mesclar as células da coluna 6 e 7 na
		// quarta linha.
		CellRangeAddress mergedRegion6to7Row4 = new CellRangeAddress(3, 3, 4, 5);
		sheet.addMergedRegion(mergedRegion6to7Row4);
		// Linha 5
		Row row5 = sheet.createRow(4);
		// Célula 5
		row5.createCell(3).setCellValue("MUDANÇAS");
		row5.getCell(3).setCellStyle(collorStyle);
		String mudancas = txaDataseNiveisCal.getText();
		Cell cell4Row5 = row5.createCell(4);
		cell4Row5.setCellValue(mudancas);
		// Crie um objeto CellRangeAddress para mesclar as células da coluna 6, 7, 8 e 9
		// na quinta linha.
		CellRangeAddress mergedRegion6to9Row5 = new CellRangeAddress(4, 4, 4, 9);
		sheet.addMergedRegion(mergedRegion6to9Row5);
	}

	public void adicionarTabelaCalculoInterniveis() throws ParseException, SQLException {

		// DETERMINANDO NEGRITO
		CellStyle boldStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setFontHeightInPoints((short)10);
		boldFont.setFontName("Arial");
	    boldFont.setColor(IndexedColors.BLACK.getIndex());
		boldFont.setBold(true);
		boldStyle.setFont(boldFont);

		// DETERMINANDO COR DE FUNDO
		CellStyle collorStyle = workbook.createCellStyle();
		collorStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		collorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		DataFormat dataFormat = workbook.createDataFormat();
		short currencyFormat = dataFormat.getFormat("R$ #,##0.00"); // Formato para moeda brasileira

		// Aplicar o formato de moeda às células
		CellStyle currencyStyle = workbook.createCellStyle();
		currencyStyle.setDataFormat(currencyFormat);

		// Crie um estilo de célula para centralizar o conteúdo
		CellStyle centeredStyle = workbook.createCellStyle();
		centeredStyle.setAlignment(HorizontalAlignment.CENTER); // Configura o alinhamento horizontal para centralizado
		centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Configura o alinhamento vertical para
																		// centralizado
		
		// Cabecalho
		Row row8 = sheet.createRow(7);
		row8.createCell(0).setCellValue("PERÍODO");
		row8.getCell(0).setCellStyle(collorStyle);

		row8.createCell(1).setCellValue("NÍVEL");
		row8.getCell(1).setCellStyle(collorStyle);

		row8.createCell(2).setCellValue("DIFERENÇA");
		row8.getCell(2).setCellStyle(collorStyle);

		row8.createCell(3).setCellValue("TRIÊNIO");
		row8.getCell(3).setCellStyle(collorStyle);

		row8.createCell(4).setCellValue("TOTAL");
		row8.getCell(4).setCellStyle(collorStyle);

		row8.createCell(5).setCellValue("INDICE TR ATÉ 29/06/2009");
		row8.getCell(5).setCellStyle(collorStyle);

		row8.createCell(6).setCellValue("CORRIGIDO TR");
		row8.getCell(6).setCellStyle(collorStyle);

		row8.createCell(7).setCellValue("IPCA-E 30/06/2009-08/12/2021");
		row8.getCell(7).setCellStyle(collorStyle);

		row8.createCell(8).setCellValue("CORRIGIDO IPCA-E");
		row8.getCell(8).setCellStyle(collorStyle);

		row8.createCell(9).setCellValue("SELIC 09/12/2021-" + txtDataDoCalculoInter.getText());
		row8.getCell(9).setCellStyle(collorStyle);

		row8.createCell(10).setCellValue("CORRIGIDO SELIC");
		row8.getCell(10).setCellStyle(collorStyle);

		row8.createCell(11).setCellValue("JUROS");
		row8.getCell(11).setCellStyle(collorStyle);

		row8.createCell(12).setCellValue("VALOR JUROS");
		row8.getCell(12).setCellStyle(collorStyle);

		row8.createCell(13).setCellValue("SUBTOTAL");
		row8.getCell(13).setCellStyle(collorStyle);

		List<String> parcelas = List.of("01/08/1998", "01/09/1998", "01/10/1998", "01/11/1998", "01/12/1998",
				"01/01/1999", "01/02/1999", "01/03/1999", "01/04/1999", "01/05/1999", "01/06/1999", "01/07/1999",
				"01/08/1999", "01/09/1999", "01/10/1999", "01/11/1999", "01/12/1999", "01/01/2000", "01/02/2000",
				"01/03/2000", "01/04/2000", "01/05/2000", "01/06/2000", "01/07/2000", "01/08/2000", "01/09/2000",
				"01/10/2000", "01/11/2000", "01/12/2000", "01/01/2001", "01/02/2001", "01/03/2001", "01/04/2001",
				"01/05/2001", "01/06/2001", "01/07/2001", "01/08/2001", "01/09/2001", "01/10/2001", "01/11/2001",
				"01/12/2001", "01/01/2002", "01/02/2002", "01/03/2002", "01/04/2002", "01/05/2002", "01/06/2002",
				"01/07/2002", "01/08/2002", "01/09/2002", "01/10/2002", "01/11/2002", "01/12/2002", "01/01/2003",
				"01/02/2003", "01/03/2003");

		List<Double> TRIndices = List.of(1.3615996, 1.3614943, 1.3384677, 1.3258983, 1.3211245, 1.3130554, 1.2954665,
				1.2881567, 1.2763768, 1.2703255, 1.2674815, 1.2630813, 1.259652, 1.2557726, 1.2525576, 1.2507597,
				1.2475934, 1.2444181, 1.2405005, 1.2389884, 1.2359885, 1.2346052, 1.2308014, 1.229252, 1.2279335,
				1.225057, 1.2239365, 1.2223653, 1.2203287, 1.2197039, 1.2171224, 1.2172059, 1.2149467, 1.2135899,
				1.2109552, 1.2094876, 1.2069822, 1.2019379, 1.2008207, 1.1968676, 1.1944271, 1.1926268, 1.188617,
				1.1872267, 1.1855685, 1.1827985, 1.1796313, 1.1785455, 1.1751517, 1.1719013, 1.1699436, 1.1660573,
				1.1632272, 1.1592843, 1.1530319, 1.1486381);
		String indiceJurostext = txtJurosNEAtualizados1.getText();
		indiceJurostext = indiceJurostext.replace("%", "");
		double indiceJuros1 = Double.parseDouble(indiceJurostext);

		List<Double> indicesdeJuros = List.of(indiceJuros1, indiceJuros1, indiceJuros1 - 0.5, indiceJuros1 - 1,
				indiceJuros1 - 1.5, indiceJuros1 - 2, indiceJuros1 - 2.5, indiceJuros1 - 3, indiceJuros1 - 3.5,
				indiceJuros1 - 4, indiceJuros1 - 4.5, indiceJuros1 - 5, indiceJuros1 - 5.5, indiceJuros1 - 6,
				indiceJuros1 - 6.5, indiceJuros1 - 7, indiceJuros1 - 7.5, indiceJuros1 - 8, indiceJuros1 - 8.5,
				indiceJuros1 - 9, indiceJuros1 - 9.5, indiceJuros1 - 10, indiceJuros1 - 10.5, indiceJuros1 - 11,
				indiceJuros1 - 11.5, indiceJuros1 - 12, indiceJuros1 - 12.5, indiceJuros1 - 13, indiceJuros1 - 13.5,
				indiceJuros1 - 14, indiceJuros1 - 14.5, indiceJuros1 - 15, indiceJuros1 - 15.5, indiceJuros1 - 16,
				indiceJuros1 - 16.5, indiceJuros1 - 17, indiceJuros1 - 17.5, indiceJuros1 - 18, indiceJuros1 - 18.5,
				indiceJuros1 - 19, indiceJuros1 - 19.5, indiceJuros1 - 20, indiceJuros1 - 20.5, indiceJuros1 - 21,
				indiceJuros1 - 21.5, indiceJuros1 - 22, indiceJuros1 - 22.5, indiceJuros1 - 23, indiceJuros1 - 23.5,
				indiceJuros1 - 24, indiceJuros1 - 24.5, indiceJuros1 - 25, indiceJuros1 - 25.5, indiceJuros1 - 26,
				indiceJuros1 - 26.5, indiceJuros1 - 27);

		String dataDoCalculoText = txtDataDoCalculoInter.getText();
		String nivelInicio = txtNivelInicio.getText();
		String dataNivelInicioText = txtDataNivelInicio.getText();
		int nivelinicio = Integer.parseInt(nivelInicio);
		int nivelFinal = nivelinicio + 1;
		String nivelFim = String.valueOf(nivelFinal);

		Demonstrativo_interniveisDao dao = new Demonstrativo_interniveisDao();
		List<Demonstrativo_interniveis> valores = dao.buscarPorDataENivel(dataNivelInicioText, nivelInicio,
				dataNivelInicioText, nivelFim);

		double diferenca;
		double trienio;
		Double valorTotal = 0d;

		for (int i = 0; i < 56; i++) {
			for (int l = 0; l < valores.size(); l++) {
				if (i * valores.size() + l < 56) {
					Row row = sheet.createRow(8 + i * valores.size() + l);

					Demonstrativo_interniveis valor = valores.get(l);

					row.createCell(0).setCellValue(valor.getDataDif());
					row.createCell(1).setCellValue(valor.getNivel());
					row.createCell(2).setCellValue(valor.getDiferenca());
					row.createCell(3).setCellValue(valor.getTrienio());
					row.createCell(4).setCellValue(valor.getTotal());

					row.getCell(0).setCellStyle(collorStyle);
					row.getCell(1).setCellStyle(centeredStyle);
					row.getCell(2).setCellStyle(currencyStyle);
					row.getCell(3).setCellStyle(currencyStyle);
					row.getCell(4).setCellStyle(currencyStyle);

					double taxaTR = TRIndices.get(l);
					double indicesdeJuros1 = indicesdeJuros.get(l);
					double valorTotalDaCelula = row.getCell(4).getNumericCellValue();
					double valorAtualizado1 = valorTotalDaCelula * taxaTR;
					String selicText = txtValorFinalSelic1.getText();
					selicText = selicText.replace(",", ".");
					double selic = Double.parseDouble(selicText);
					double taxaIPCA = 2.0606492;
					double valorAtualizado2 = valorAtualizado1 * taxaIPCA;
					double valorAtualizado3 = valorAtualizado2 * selic;
					String dataCalculoStr = txtDataDoCalculoInter.getText();
					LocalDate dataCalculo = LocalDate.parse(dataCalculoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					LocalDate dataReferencia = LocalDate.of(1998, 9, 3);
					long diferencaDias = ChronoUnit.DAYS.between(dataReferencia, dataCalculo);
					Date dataCalculo1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataDoCalculoInter.getText());
					long diferencaDias1 = (long) diferencaDias * 360 / 365;
					double numeroAnos = (double) diferencaDias1 / 360;
					double numeroMeses = (double) numeroAnos * 12;
					double indiceJuros = (double) numeroMeses * 0.50;

					double valorJuros = valorAtualizado3 * indicesdeJuros1 / 100;

					row.createCell(5).setCellValue(taxaTR);
					row.createCell(6).setCellValue(valorAtualizado1);
					row.getCell(6).setCellStyle(currencyStyle);
					row.createCell(7).setCellValue(taxaIPCA);
					row.createCell(8).setCellValue(valorAtualizado2);
					row.getCell(8).setCellStyle(currencyStyle);
					row.createCell(9).setCellValue(selic);
					row.createCell(10).setCellValue(valorAtualizado3);
					row.getCell(10).setCellStyle(currencyStyle);
					row.createCell(11).setCellValue(indicesdeJuros1 + "%");
					row.createCell(12).setCellValue(valorJuros);
					row.getCell(12).setCellStyle(currencyStyle);

					double valorTotalLinhaInter = valorAtualizado3 + valorJuros;
					valorTotal += valorTotalLinhaInter;
					row.createCell(13).setCellValue(valorTotalLinhaInter);
					row.getCell(13).setCellStyle(currencyStyle);

				}

			}

			Row row64 = sheet.createRow(64);
			for (int j = 0; j < 13; j++) {
				if (j == 0) {
					row64.createCell(j).setCellValue("Total");
					row64.getCell(j).setCellStyle(collorStyle);
				}
					else if (j == 12) {
					    row64.createCell(j).setCellValue(valorTotal);
					    row64.getCell(j).setCellStyle(currencyStyle);
					}
				else {
					row64.createCell(j).setCellValue("");
				}
			}
			Row row65 = sheet.createRow(65);
			row65.createCell(0).setCellValue("Resumo");
			row65.getCell(0).setCellStyle(collorStyle);

			Row row66 = sheet.createRow(66);
			row66.createCell(0).setCellValue("Total");
			row66.getCell(0).setCellStyle(collorStyle);

			row66.createCell(1).setCellValue(valorTotal);
			row66.getCell(1).setCellStyle(currencyStyle);

			Row row67 = sheet.createRow(67);
			row67.createCell(0).setCellValue("Rioprevidencia");
			row67.getCell(0).setCellStyle(collorStyle);

			double rioprev = valorTotal * 11 / 100;
			row67.createCell(1).setCellValue(rioprev);
			row67.getCell(1).setCellStyle(currencyStyle);

			Row row68 = sheet.createRow(68);
			row68.createCell(0).setCellValue("Principal Líquido");
			row68.getCell(0).setCellStyle(collorStyle);

			double valorAcao = valorTotal - rioprev;
			row68.createCell(1).setCellValue(valorAcao);
			row68.getCell(1).setCellStyle(currencyStyle);

			txtPrincipalInterniveis.setText(String.format("R$ %.2f", valorTotal));
			txtRioPrevInterniveis.setText(String.format("R$ %.2f", rioprev));
			txtValorFinalInterniveis.setText(String.format("R$ %.2f", valorAcao));

			// Crie um estilo para alinhar o conteúdo à esquerda
			CellStyle leftAlignedStyle = workbook.createCellStyle();
			leftAlignedStyle.setAlignment(HorizontalAlignment.LEFT);

			// Linha 70
			Row row70 = sheet.createRow(70);
			Cell cell70 = row70.createCell(0);
			cell70.setCellValue("CORREÇÃO APLICADA");

			cell70.setCellStyle(leftAlignedStyle); // Aplica um estilo que alinha à esquerda

			// Linha 71
			Row row71 = sheet.createRow(71);
			Cell cell71 = row71.createCell(0);
			cell71.setCellValue("TR até 29/06/2009");

			cell71.setCellStyle(leftAlignedStyle);

			// Linha 72
			Row row72 = sheet.createRow(72);
			Cell cell72 = row72.createCell(0);
			cell72.setCellValue("IPCA-E de 07/2009 até 08/12/2021");

			cell72.setCellStyle(leftAlignedStyle);

			// Linha 73
			Row row73 = sheet.createRow(73);
			Cell cell73 = row73.createCell(0);
			cell73.setCellValue("SELIC a partir de 09/12/2021");

			cell73.setCellStyle(leftAlignedStyle);

		}
	}

	public void salvarPlanilhaInterniveisEAbriLa(String caminhoDoArquivo) {
		try (FileOutputStream fileOut = new FileOutputStream(caminhoDoArquivo)) {
			workbook.write(fileOut);

			// Após salvar a planilha, abra-a com o aplicativo padrão
			File file = new File(caminhoDoArquivo);
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void inserirCalculoInterniveisdataBase(ActionEvent event) {
		String valorCalcInterniveis = txtPrincipalInterniveis.getText();
		String rioPrevInterniveis = txtRioPrevInterniveis.getText();
		String valorTotalInterniveis = txtValorFinalInterniveis.getText();
		String nome = txtRetornoNomeInterniveis.getText();
		String tema = txtTemaCalculoInterniveis.getText();
		String matricula = txtRetornoMatriculaInterniveis.getText();

		if (!valorCalcInterniveis.isEmpty() && !rioPrevInterniveis.isEmpty() && !valorTotalInterniveis.isEmpty()
				&& !nome.isEmpty() && !tema.isEmpty() && !matricula.isEmpty())

		{
			Calculos calculos = new Calculos();
			calculos.setNome(nome);
			calculos.setTema(tema);
			calculos.setMatricula(matricula);
			calculos.setValorPrincipal(valorCalcInterniveis);
			calculos.setRioPrev(rioPrevInterniveis);
			calculos.setValorFinal(valorTotalInterniveis);

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

	@FXML
	void calcularSelicInterniveis(ActionEvent event) {
		// Obtenha a data final da SELIC do campo de texto
		String dataFinalSelic = txtDataFinalSelic1.getText();

		// Obtenha a taxa SELIC atualizada usando o SelicRateExtractor
		SelicRateExtractor selicExtractor = new SelicRateExtractor();
		String dataInicial = "09122021"; // Data Inicial fornecida no exemplo
		String taxaSelic = selicExtractor.getTaxaSelicPeriod(dataInicial, dataFinalSelic);

		// Exiba a taxa SELIC atualizada no campo de texto de valor atualizado
		txtValorFinalSelic1.setText(taxaSelic);
	}

	@FXML
	private void calcularJurosInterniveis() throws ParseException {

		String dataCalculoStr = txtDataDoCalculoInter.getText();
		LocalDate dataCalculo = LocalDate.parse(dataCalculoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate dataReferencia = LocalDate.of(1998, 9, 3);
		long diferencaDias = ChronoUnit.DAYS.between(dataReferencia, dataCalculo);
		Date dataCalculo1 = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataDoCalculoInter.getText());
		long diferencaDias1 = (long) diferencaDias * 360 / 365;
		double numeroAnos = (double) diferencaDias1 / 360;
		double numeroMeses = (double) numeroAnos * 12;
		double indiceJuros = (double) numeroMeses * 0.50;

		// Exibir o valor do índice calculado na TextField txtJurosNEAtualizados
		txtJurosNEAtualizados1.setText(String.valueOf(indiceJuros + "%"));

	}

}
