package guiWAdv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javafx.scene.control.Alert.AlertType;

public abstract class Documento {
    public void gerarDocumento(String modeloPath, String nomeDocumento, Map<String, String> dataMap) {
        try {
            FileInputStream templateFile = new FileInputStream(modeloPath);
            XWPFDocument document = new XWPFDocument(templateFile);

            // Substitua os marcadores de posição pelos dados do cliente
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                        if (text != null && text.contains(entry.getKey())) {
                            text = text.replace(entry.getKey(), entry.getValue());
                            run.setText(text, 0);
                        }
                    }
                }
            }

            // Salve o documento em um arquivo
            FileOutputStream out = new FileOutputStream("Caminho_de_Saída_Aqui" + nomeDocumento + ".docx");
            document.write(out);
            out.close();
            document.close();
            templateFile.close();

            // Exiba uma mensagem de sucesso após a geração do documento
            alert("Sucesso", nomeDocumento + " gerado com sucesso.", AlertType.INFORMATION);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            alert("Erro", "Arquivo de modelo não encontrado.", AlertType.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            alert("Erro", "Ocorreu um erro durante a geração do documento.", AlertType.ERROR);
        }
    }

    // Implemente o método "alert" ou qualquer outro método de notificação
    protected abstract void alert(String title, String message, AlertType type);
}
