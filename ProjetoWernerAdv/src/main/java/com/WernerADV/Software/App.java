package com.WernerADV.Software;

import java.io.IOException;
import java.util.logging.Logger;
import com.WernerADV.Software.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {

	public static final Logger logger = Logger.getLogger(App.class.getName());
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_CONNECTION = "jdbc:mysql://127.0.0.1:3305/clienteswerneradv";
	public static final String DB_USER = "username1";
	public static final String DB_PASSWORD = "LizWerner01-";

	@Override
	public void start(Stage stage) {

	
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/guiWAdv/TelaPrincipal.fxml"));
			FXMLLoader.load(getClass().getResource("/guiWAdv/TelaPrincipal.fxml"));
			Scene scene = new Scene(parent);
			System.out.println("Iniciando");
			
			
			// Adicione a imagem na barra de título
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/guiWAdvImagens/LOGO.png")));
            stage.setTitle("Werner Advogados");
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/guiWAdv/styles.css").toExternalForm());
            
            
			stage.setScene(scene);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}

}
