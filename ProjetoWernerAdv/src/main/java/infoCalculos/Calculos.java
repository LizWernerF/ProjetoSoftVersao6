package infoCalculos;

public class Calculos {

	int idcalculos;
	String nome;
	String tema;
	String matricula;
	String valorPrincipal;
	String rioPrev;
	String valorFinal;
	
	
	public int getIdcalculos() {
		return idcalculos;
	}
	public void setIdcalculos(int idcalculos) {
		this.idcalculos = idcalculos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getValorPrincipal() {
		return valorPrincipal;
	}
	public void setValorPrincipal(String valorPrincipal) {
		this.valorPrincipal = valorPrincipal;
	}
	public String getRioPrev() {
		return rioPrev;
	}
	public void setRioPrev(String rioPrev) {
		this.rioPrev = rioPrev;
	}
	public String getValorFinal() {
		return valorFinal;
	}
	public void setValorFinal(String valorFinal) {
		this.valorFinal = valorFinal;
	}
	@Override
	public String toString() {
		return "Calculos [idcalculos=" + idcalculos + ", nome=" + nome + ", tema=" + tema + ", matricula=" + matricula
				+ ", valorPrincipal=" + valorPrincipal + ", rioPrev=" + rioPrev + ", valorFinal=" + valorFinal + "]";
	}
	
	
	
	
	
}
