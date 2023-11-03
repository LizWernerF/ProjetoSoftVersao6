package guiWAdv;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
	
	public void inserirCliente(User user) throws SQLException;
    public void atualizaCliente(User user) throws SQLException;
    public void excluiCliente(User user) throws SQLException;
    public User buscaCliente(User user) throws SQLException;
    public List<User> buscaClientes() throws SQLException;

}
	