package guiWAdv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Proc implements Serializable {

	private static final long serialVersionUID = 3789909326487155148L;
	private int Idproc;
	private User cliente;
    private String numeroprocesso;
    private String comarca;
    private String vara;
    private String tema;
    private String nomecliente;
    private String valorcausa;
    private String valorhono;
    private String status;
    private String matricula;
	
    public Proc() {
    	historico = new ArrayList<>(); // Inicialização da lista de históricos no construtor
    	
    }
    
    
    
    public Proc(String numeroprocesso, String comarca, String vara, String tema, String nomecliente,
			String valorcausa, String valorhono, String status, String matricula) {
		super();
		
		this.numeroprocesso = numeroprocesso;
		this.comarca = comarca;
		this.vara = vara;
		this.tema = tema;
		this.nomecliente = nomecliente;
		this.valorcausa = valorcausa;
		this.valorhono = valorhono;
		this.status = status;
		this.matricula = matricula;
	}
	public int getIdproc() {
		return Idproc;
	}
	public void setIdproc(int Idproc) {
		this.Idproc = Idproc;
	}
	public User getCliente() {
		return cliente;
	}
	public void setCliente(User cliente) {
		this.cliente = cliente;
	}
	public String getnumeroprocesso() {
		return numeroprocesso;
	}
	public void setnumeroprocesso(String numeroprocesso) {
		this.numeroprocesso = numeroprocesso;
	}
	public String getcomarca() {
		return comarca;
	}
	public void setcomarca(String comarca) {
		this.comarca = comarca;
	}
	public String getvara() {
		return vara;
	}
	public void setvara(String vara) {
		this.vara = vara;
	}
	public String gettema() {
		return tema;
	}
	public void settema(String tema) {
		this.tema = tema;
	}
	public String getnomecliente() {
		return nomecliente;
	}
	public void setnomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
	}
	public String getvalorcausa() {
		return valorcausa;
	}
	public void setvalorcausa(String valorcausa) {
		this.valorcausa = valorcausa;
	}
	public String getvalorhono() {
		return valorhono;
	}
	public void setvalorhono(String valorhono) {
		this.valorhono = valorhono;
	}
	public String getstatus() {
		return status;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	@Override
	public String toString() {
		return "Proc [Idproc=" + Idproc + ", numeroprocesso=" + numeroprocesso + ", nomecliente=" + nomecliente + ", comarca=" + comarca + ", vara="
				+ vara + ", tema=" + tema + ", valorcausa=" + valorcausa + ", valorhono="
				+ valorhono + ", status=" + status + ", matricula=" + matricula + "]";
	}
	
	public static Proc fromString(String str) {
	    // Converte a string para um objeto Proc
	    return new Proc(str, str, str, str, str, str, str, str, str);
	}
	
	 private List<Historico> historico;
	 
	 public void adicionarHistorico(Historico historico) {
		    this.historico.add(historico);
		}

		public List<Historico> getHistorico() {
		    return historico;
		}

		public void removerHistorico(Historico historico) {
		    this.historico.remove(historico);
		}
	
		
		
	
}