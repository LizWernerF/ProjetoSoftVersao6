package guiWAdv;

public class Tarefa {

	    private int idTarefa;
	    private String dataCriacao;
	    private String descricao;
	    private String responsavel;
	    private String cliente;
	    private String processo;
	    private String dataLimite;
	    private String status;
	    private boolean concluida;

	    public Tarefa() {}

	    // Getters e Setters
	    public int getIdTarefa() {
	        return idTarefa;
	    }

	    public void setIdTarefa(int idTarefa) {
	        this.idTarefa = idTarefa;
	    }

	    public String getDataCriacao() {
	        return dataCriacao;
	    }

	    public void setDataCriacao(String dataCriacao) {
	        this.dataCriacao = dataCriacao;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

	    public void setDescricao(String descricao) {
	        this.descricao = descricao;
	    }

	    public String getResponsavel() {
	        return responsavel;
	    }

	    public void setResponsavel(String responsavel) {
	        this.responsavel = responsavel;
	    }

	    public String getCliente() {
	        return cliente;
	    }

	    public void setCliente(String cliente) {
	        this.cliente = cliente;
	    }

	    public String getProcesso() {
	        return processo;
	    }

	    public void setProcesso(String processo) {
	        this.processo = processo;
	    }

	    public String getDataLimite() {
	        return dataLimite;
	    }

	    public void setDataLimite(String dataLimite) {
	        this.dataLimite = dataLimite;
	    }
	    
	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public boolean isConcluida() {
	        return concluida;
	    }

	    public void setConcluida(boolean concluida) {
	        this.concluida = concluida;
	    }
	}
