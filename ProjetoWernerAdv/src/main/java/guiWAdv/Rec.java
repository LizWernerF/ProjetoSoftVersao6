package guiWAdv;

import java.io.Serializable;

public class Rec implements Serializable {

	private static final long serialVersionUID = 3789909326487155148L;
	private int Idrec;
	private String numerorecurso;
	private String nomedocliente;
	private String processoorigem;
	private String tiporecurso;
    private String recorridoourecorrente;
    private String status;
    private String julgador;
    private String relator;
    
	
    public Rec() {}
    
    public Rec(String numerorecurso, String nomedocliente, String processoorigem, String tiporecurso, String recorridoourecorrente,
			String status, String julgador, String relator) {
		super();
		
		this.numerorecurso = numerorecurso;
		this.nomedocliente = nomedocliente;
		this.processoorigem = processoorigem;
		this.tiporecurso = tiporecurso;
		this.recorridoourecorrente = recorridoourecorrente;
		this.status = status;
		this.julgador = julgador;
		this.relator = relator;
	
}

	
	public int getIdrec() {
		return Idrec;
	}

	public void setIdrec(int idrec) {
		Idrec = idrec;
	}

	public String getNumerorecurso() {
		return numerorecurso;
	}

	public void setNumerorecurso(String numerorecurso) {
		this.numerorecurso = numerorecurso;
	}

	public String getNomedocliente() {
		return nomedocliente;
	}

	public void setNomedocliente(String nomedocliente) {
		this.nomedocliente = nomedocliente;
	}

	public String getProcessoorigem() {
		return processoorigem;
	}

	public void setProcessoorigem(String processoorigem) {
		this.processoorigem = processoorigem;
	}

	public String getTiporecurso() {
		return tiporecurso;
	}

	public void setTiporecurso(String tiporecurso) {
		this.tiporecurso = tiporecurso;
	}

	public String getRecorridoourecorrente() {
		return recorridoourecorrente;
	}

	public void setRecorridoourecorrente(String recorridoourecorrente) {
		this.recorridoourecorrente = recorridoourecorrente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJulgador() {
		return julgador;
	}

	public void setJulgador(String julgador) {
		this.julgador = julgador;
	}

	public String getRelator() {
		return relator;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}

	
	@Override
	public String toString() {
		return "Rec [Idrec=" + Idrec + ", numerorecurso=" + numerorecurso + ", nomedocliente=" + nomedocliente
				+ ", processoorigem=" + processoorigem + ", tiporecurso=" + tiporecurso + ", recorridoourecorrente="
				+ recorridoourecorrente + ", status=" + status + ", julgador=" + julgador + ", relator=" + relator
				+ "]";
	}
}