package guiWAdv;

public class Historico {

	
	private int idHistorico;
    private int idProcesso;
    private String data;
    private String descricao;
    
    
	public Historico() {
		// TODO Auto-generated constructor stub
	}
	public int getIdHistorico() {
		return idHistorico;
	}
	public void setIdHistorico(int idHistorico) {
		this.idHistorico = idHistorico;
	}
	public int getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(int idProcesso) {
		this.idProcesso = idProcesso;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return "Historico [idHistorico=" + idHistorico + ", idProcesso=" + idProcesso + ", data=" + data
				+ ", descricao=" + descricao + "]";
	}
	
	private int historico;
	 

		public int getHistorico() {
		    return historico;
		}

	
		}

	
