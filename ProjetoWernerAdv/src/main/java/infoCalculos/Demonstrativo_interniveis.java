package infoCalculos;

public class Demonstrativo_interniveis {

	String dataDif;
	String nivel;
	double diferenca;
	double trienio;

	
	public double getTotal() {
	    return diferenca + trienio;
	}
	
	
	public String getDataDif() {
		return dataDif;
	}
	public void setDataDif(String dataDif) {
		this.dataDif = dataDif;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public double getDiferenca() {
		return diferenca;
	}
	public void setDiferenca(double diferenca) {
		this.diferenca = diferenca;
	}
	public double getTrienio() {
		return trienio;
	}
	public void setTrienio(double trienio) {
		this.trienio = trienio;
	}
	
	
}
