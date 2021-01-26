package Control;

import java.sql.SQLException;

import DAO.UsuarioDAO;
import Model.Usuario;
import View.IUCadastrarUsuario;

public class CtrlCadUsuario {
	public CtrlCadUsuario() {}
	
	public void inserirUsuario(String cpf, String nomeCompleto, String senha, String enderecoEmail) {
		Usuario u = new Usuario();
		u.setCpf(cpf);
		u.setNomeCompleto(nomeCompleto);
		u.setSenha(senha);
		u.setEnderecoEmail(enderecoEmail);
	}
	
	public void deletarUsuario(String cpf) {
		Usuario u = new Usuario();
		u.setCpf(cpf);
	}
	
	public void alterarUsuario(String cpf, String nomeCompleto, String senha, String enderecoEmail) {
		Usuario u = new Usuario();
		u.setCpf(cpf);
		u.setNomeCompleto(nomeCompleto);
		u.setSenha(senha);
		u.setEnderecoEmail(enderecoEmail);
	}
	
	public Usuario procurarUsuario(String cpf) throws SQLException {
		UsuarioDAO uDAO = new UsuarioDAO();
		Usuario u = uDAO.procurarUsuario(cpf);
		return u;
	}
}
