package guiWAdv;

import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;

public class TarefaListCell extends ListCell<String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            String[] partes = item.split(" \\| "); // Divide a string em partes
            
            // Certifique-se de que o array contém o número esperado de partes
            if (partes.length < 7) {
                setText("Dados incompletos");
                return;
            }

            String dataCriacao = partes[0]; // 14/10/2024
            String status = partes[1]; // pendente
            String descricao = partes[2]; // Teste
            String responsavel = partes[3]; // Responsável
            String cliente = partes[4]; // Cliente
            String processo = partes[5]; // Processo
            String dataLimite = partes[6]; // 15/10/2024

            HBox hBox = new HBox();
            hBox.setSpacing(5); // Espaçamento entre os elementos do HBox
            hBox.setPrefHeight(30); // Aumenta a altura da linha
            hBox.setAlignment(Pos.CENTER_LEFT); // Centraliza horizontalmente os textos

            // Adiciona a data de criação
            hBox.getChildren().add(new Text(dataCriacao + " | ")); 

            // Formatação do status
            Text statusText = new Text(status + " | ");
            if (status.equalsIgnoreCase("pendente")) {
                statusText.setFill(Color.RED); // Cor vermelha para "pendente"
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            } else if (status.equalsIgnoreCase("concluida")) {
                statusText.setFill(Color.GREEN); // Cor verde para "feito"
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            } else if (status.equalsIgnoreCase("em andamento")) {
                statusText.setFill(Color.ORANGE); // Cor laranja para "em andamento"
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            } else {
                statusText.setFill(Color.BLACK); // Cor padrão
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            }
            hBox.getChildren().add(statusText); // Adiciona o status

            // Adiciona a descrição
            hBox.getChildren().add(new Text(descricao + " | "));

            // Formatação do responsável
            Text responsavelText = new Text(responsavel + " | ");
            responsavelText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14)); // Define a fonte em negrito

            if (responsavel.equalsIgnoreCase("Angelo")) {
                responsavelText.setFill(Color.DARKGREEN); // Verde escuro para Angelo
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            } else if (responsavel.equalsIgnoreCase("Liz")) {
                responsavelText.setFill(Color.MAGENTA); // Magenta para Liz
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            } else if (responsavel.equalsIgnoreCase("Thiago")) {
                responsavelText.setFill(Color.BLUE); // Azul para Thiago
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            } else {
                responsavelText.setFill(Color.BLACK); // Cor padrão para outros responsáveis
                statusText.setFont(Font.font("Calibri Light", FontWeight.NORMAL, 14));
            }

            hBox.getChildren().add(responsavelText); // Adiciona o responsável ao HBox
            
            // Adiciona o cliente
            hBox.getChildren().add(new Text(cliente + " | ")); // Adiciona o cliente
            
            // Adiciona o processo
            hBox.getChildren().add(new Text(processo + " | ")); // Adiciona o processo

         
            
            // Adiciona a data limite com o texto "Fazer até:"
            Text dataLimiteText = new Text("Fazer até: " + dataLimite); // Data limite com texto
            hBox.getChildren().add(dataLimiteText); // Adiciona a data limite

            setGraphic(hBox); // Define a célula gráfica
        }
    }
}
