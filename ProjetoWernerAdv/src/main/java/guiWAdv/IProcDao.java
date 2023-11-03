package guiWAdv;

import java.sql.SQLException;
import java.util.List;

public interface IProcDao {
	
	   public void inserirProcesso(Proc proc) throws SQLException;
	    public void atualizaProcesso(Proc proc) throws SQLException;
	    public void excluiProcesso(Proc proc) throws SQLException;
	    public Proc buscaProcesso(Proc proc) throws SQLException;
	    public List<Proc> buscaProcessos() throws SQLException;

}
