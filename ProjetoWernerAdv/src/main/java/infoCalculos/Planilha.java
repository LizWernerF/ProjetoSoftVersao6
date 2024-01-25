package infoCalculos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import infoCalculos.DbpisoDao.LinhaPlanilha;

public class Planilha {

	public void gerarPlanilha(List<LinhaPlanilha> linhas, String arquivo) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Planilha");

		// Cabeçalho
		Row cabecalho = sheet.createRow(0);
		cabecalho.createCell(0).setCellValue("Data");
		cabecalho.createCell(1).setCellValue("Valor Provento Estado");
		cabecalho.createCell(2).setCellValue("Valor Piso Nacional");
		cabecalho.createCell(3).setCellValue("Diferença Piso - Provento");
		cabecalho.createCell(4).setCellValue("Valor Trienio Estado");
		cabecalho.createCell(5).setCellValue("Valor Trienio Piso Naciona");
		cabecalho.createCell(6).setCellValue("Diferença Trienio");
		cabecalho.createCell(7).setCellValue("Valor Total Devido");

		// Dados
		int rowNum = 1;
		for (LinhaPlanilha l : linhas) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(l.getDataParcelas());
			row.createCell(1).setCellValue(l.getValorProventoEstado());
			row.createCell(2).setCellValue(l.getValorPisoNacional());
			row.createCell(3).setCellValue(l.getDiferenca());
			row.createCell(4).setCellValue(l.getValorProventoEstadoPercentual());
			row.createCell(5).setCellValue(l.getValorPisoNacionalPercentual());
			row.createCell(6).setCellValue(l.getDiferencaPercentual());
			row.createCell(7).setCellValue(l.getTotal());
		}
		
		Row sum = sheet.createRow(linhas.size() + 2);
		for(int i = 0; i < 7; i++) {
			sum.createCell(i).setCellValue("");
		}
		sum.createCell(7).setCellValue(linhas.stream().map(l -> l.getTotal()).reduce(Double::sum).get());

		// Formatação
		DataFormat dataFormat = workbook.createDataFormat();
		short currencyFormat = dataFormat.getFormat("R$ #,##0.00"); // Formato para moeda brasileira

		// Aplicar o formato de moeda às células
		CellStyle currencyStyle = workbook.createCellStyle();
		currencyStyle.setDataFormat(currencyFormat);

		for (int i = 1; i < 8; i++) { 
			sheet.autoSizeColumn(i);
			sheet.setDefaultColumnStyle(i, currencyStyle); 
		}

		

		// Gravar planilha
		try (FileOutputStream outputStream = new FileOutputStream(arquivo)) {
			workbook.write(outputStream);
		}

		workbook.close();
	}
}