package guiWAdv;

import java.sql.SQLException;

public interface IUserController {

	
	public void inserirCliente(User user) throws ClassNotFoundException, SQLException;
    public void atualizarCliente(User user) throws ClassNotFoundException, SQLException;
    public void excluirCliente(User user) throws ClassNotFoundException, SQLException;
    public void buscarCliente(User user) throws ClassNotFoundException, SQLException;
    public void buscarClientes() throws ClassNotFoundException, SQLException;
	
}
