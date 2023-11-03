package Selic;

public enum Rates {

SELIC;
	
	public String getURL() {
		switch(this) {
		case SELIC:
			return "https://www3.bcb.gov.br/CALCIDADAO/publico/exibirFormCorrecaoValores.do?method=exibirFormCorrecaoValores&aba=4";
		default:
			return "Rate not found!";
		}
	}
	
	public String getDataInicialFieldName() {
		switch(this) {
		case SELIC:
			return "dataInicial";
		default:
			return "Rate not found!";
		}
	}
	
	public String getDataFinalFieldName() {
		switch(this) {
		case SELIC:
			return "dataFinal";
		default:
			return "Rate not found!";
		}
	}
	
	public String getValorCorrecaoFieldName() {
		switch(this) {
		case SELIC:
			return "valorCorrecao";
		default:
			return "Rate not found!";
		}
	}
	
	public String getvalor() {
		return "10000";
	}
}
