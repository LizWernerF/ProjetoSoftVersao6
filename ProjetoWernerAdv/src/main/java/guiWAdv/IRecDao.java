package guiWAdv;

import java.sql.SQLException;
import java.util.List;

public interface IRecDao {

	public void inserirRecurso(Rec rec) throws SQLException;
    public void atualizaRecurso(Rec rec) throws SQLException;
    public void excluiRecurso(Rec rec) throws SQLException;
    public Rec buscaRecurso(Rec rec) throws SQLException;
    public List<Rec> buscaRecursos() throws SQLException;
	
	
}
