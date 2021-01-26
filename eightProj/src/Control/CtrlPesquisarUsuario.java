package Control;

import java.sql.SQLException;
import java.util.ArrayList;
import DAO.UsuarioDAO;
import Model.Usuario;

public class CtrlPesquisarUsuario {
	public ArrayList<Usuario> PesquisaUsuario(String nome) throws SQLException {
		UsuarioDAO uDAO = new UsuarioDAO();
		ArrayList<Usuario> listaUsuario = uDAO.ListarUsuario(nome);
		return listaUsuario;
	}
}