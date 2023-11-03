package com.WernerADV.Software;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import guiWAdv.User;
import guiWAdv.UserDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JApps extends Application {

	private static final Logger logger = Logger.getLogger(JApps.class.getName());
	private UserDao userDao = new UserDao();

	

	    public static void Main(String[] args) {
	        launch(args);
	    }
	    @FXML
		private Label labelID;
		@FXML
		private TextField txtID;
		@FXML
		private Label labelNome;
		@FXML
		private TextField txtNome;
		@FXML
		private Label labelTelefone;
		@FXML
		private TextField txtTelefone;
		@FXML
		private Label labelCpf;
		@FXML
		private TextField txtCpf;
		@FXML
		private Label labelRg;
		@FXML
		private TextField txtRg;
		@FXML
		private Label labelNacionalidade;
		@FXML
		private TextField txtNacionalidade;
		@FXML
		private Label labelEstCivil;
		@FXML
		private TextField txtEstCivil;
		@FXML
		private Label labelProfissao;
		@FXML
		private TextField txtProfissao;
		@FXML
		private Label labelIdFuncional;
		@FXML
		private TextField txtIdFuncional;
		@FXML
		private Label labelDatanascimento;
		@FXML
		private TextField txtDatanscimento;
		@FXML
		private Label labelEndereco;
		@FXML
		private TextField txtEndereco;
		@FXML
		private Label labelCidade;
		@FXML
		private TextField txtCidade;
		@FXML
		private Label labelEstado;
		@FXML
		private TextField txtEstado;
		@FXML
		private Label labelCep;
		@FXML
		private TextField txtCep;
		@FXML
		private Label labelMat1;
		@FXML
		private TextField txtMat1;
		@FXML
		private Label labelRef1;
		@FXML
		private TextField BoxRef1;
		@FXML
		private Label labelCargH1;
		@FXML
		private TextField BoxCargH1;
		@FXML
		private Label labelDataInicio1;
		@FXML
		private TextField DataInicio1;
		@FXML
		private Label labelCargo1;
		@FXML
		private TextField txtCargo;
		@FXML
		private Label labelNivelatual1;
		@FXML
		private TextField BoxNivelatual1;
		@FXML
		private Label labelTrienioatual1;
		@FXML
		private TextField txtTrienioatual1;
		@FXML
		private Label labeldataaposentadoria1;
		@FXML
		private TextField txtdataaposentadoria1;
		@FXML
		private Label labelpossui2aMat;
		@FXML
		private Label labelMat2;
		@FXML
		private TextField txtMat2;
		@FXML
		private Label labelRef12;
		@FXML
		private TextField BoxRef2;
		@FXML
		private Label labelCargH2;
		@FXML
		private TextField BoxCargh2;
		@FXML
		private Label labelDataInicio2;
		@FXML
		private TextField datainicio2;
		@FXML
		private Label labelCargo2;
		@FXML
		private TextField txtCargo2;
		@FXML
		private Label labelNivelatual2;
		@FXML
		private TextField BoxNivelatual2;
		@FXML
		private Label labelTrienioatual2;
		@FXML
		private TextField txtTrienioatual2;
		@FXML
		private Label labelaposentado2;
		@FXML
		private Label labelDataaposentadoria2;
		@FXML
		private TextField txtDataaposentadoria2;
		@FXML
		private Label labelWernerADV;
		@FXML
		private Label labelTituloCadCliente;
		@FXML
		private Label labelcadastradocliente;
		@FXML
		private Button btnCadastrarCliente;
		
	    
		@FXML
		public void onBtnCadastrarClienteAction() {
		btnCadastrarCliente.setOnAction(actionEvent -> 
		{
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
			
			
			
			if (!StringPool.BLANK.equals(nome) && !StringPool.BLANK.equals(telefone)
					&& !StringPool.BLANK.equals(cpf) && !StringPool.BLANK.equals(rg) &&
					!StringPool.BLANK.equals(nacionalidade) && !StringPool.BLANK.equals(estadocivil) &&
					!StringPool.BLANK.equals(profissao) && !StringPool.BLANK.equals(idfuncional) &&
					!StringPool.BLANK.equals(datanascimento) && !StringPool.BLANK.equals(endereco) &&
					!StringPool.BLANK.equals(cidade) && !StringPool.BLANK.equals(estado) &&
					!StringPool.BLANK.equals(cep) && !StringPool.BLANK.equals(mat1) &&
					!StringPool.BLANK.equals(ref1) && !StringPool.BLANK.equals(cargh1) &&
					!StringPool.BLANK.equals(datainicio1) && !StringPool.BLANK.equals(cargo1) &&
					!StringPool.BLANK.equals(nivel1) && !StringPool.BLANK.equals(trienio1)) {
			
				try {
					if (!userDao.userExists(nome)) {
						// Se o usuário não existir, faça o seguinte:
						User user = this.createUserObject(nome, telefone, cpf, rg, nacionalidade, estadocivil, profissao, idfuncional, datanascimento, endereco, cidade, estado, cep, mat1, ref1, cargh1, datainicio1, cargo1, nivel1, trienio1, dataaposentadoria1, mat2, ref2, cargh2, DataInicio2, cargo2, nivel2, trienio2, dataaposentadoria2);
						// Cria um objeto User com os valores dos campos de texto, exceto os que podem estar em branco
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

	public void alert(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

	public User createUserObject(
			String nome,
			String telefone,
			String cpf,
			String rg,
			String nacionalidade,
			String estadocivil,
			String profissao,
			String idfuncional,
			String datanascimento,
			String endereco,
			String cidade,
			String estado,
			String cep,
			String mat1,
			String ref1,
			String cargh1,
			String datainicio1,
			String cargo1,
			String nivel1,
			String trienio1,
			String dataaposentadoria1,
			String mat2,
			String ref2,
			String cargh2,
			String dataInicio2,
			String cargo2,
			String nivel2,
			String trienio2,
			String dataaposentadoria2
	) {
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
	
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}