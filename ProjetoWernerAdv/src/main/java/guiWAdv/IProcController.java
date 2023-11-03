package guiWAdv;

import java.sql.SQLException;

public interface IProcController {

	 public void inserirProcesso(Proc proc) throws ClassNotFoundException, SQLException;
	    public void atualizarProcesso(Proc proc) throws ClassNotFoundException, SQLException;
	    public void excluirProcesso(Proc proc) throws ClassNotFoundException, SQLException;
	    public void buscarProcesso(Proc proc) throws ClassNotFoundException, SQLException;
	    public void buscarProcessos() throws ClassNotFoundException, SQLException;
	
}
