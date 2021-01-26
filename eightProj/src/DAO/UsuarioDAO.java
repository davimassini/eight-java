package DAO;

import View.ConexaoMySQL;
import Model.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {
    private Connection connection;
    ConexaoMySQL banco = new ConexaoMySQL();
    String nome;
    String cpf;
    String email;
    String senha;
    
    public UsuarioDAO(){
    	this.connection = new ConexaoMySQL().abrirConexao();
    }
    
    public void adicionarUsuario(Usuario usuario){
        String sql = "INSERT INTO usuarios(cpf, nome, email, senha) VALUES(?, ?, ?, ?)";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getNomeCompleto());
            stmt.setString(3, usuario.getEnderecoEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        }
    }
    
    public void deletarUsuario(Usuario usuario){
        String sql = "DELETE FROM usuarios WHERE cpf = ?;";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getCpf());
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        }
    }
    
    public void alterarUsuario(Usuario usuario){
        String sql = "UPDATE usuarios SET nome = (?), email= (?), senha= (?) WHERE cpf = (?);";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setString(2, usuario.getEnderecoEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getCpf());
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        }
    }
    
    public Usuario procurarUsuario(String cpf) throws SQLException{
    	Usuario usuario = new Usuario();
    	
    	String sql = "SELECT * FROM usuarios WHERE cpf = " + cpf;
    	
    	ResultSet res = banco.executaBusca(sql);
    	res.next();
    	
    	System.out.println(res.getString("nome"));
    	
    	usuario.setNomeCompleto(res.getString("nome"));
		usuario.setEnderecoEmail(res.getString("senha"));
		usuario.setSenha(res.getString("email"));

        return usuario;
    }
    
    public ArrayList<Usuario> ListarUsuario(String nome) throws SQLException{
	    ArrayList<Usuario> listaUsuario = new ArrayList<>();
	    String sql = "SELECT * FROM usuarios WHERE UPPER(nome) LIKE UPPER('%"+ nome +"%');";
	    ResultSet res = banco.executaBusca(sql);
	    while (res.next()) {
		    Usuario usuario = new Usuario();
		    usuario.setCpf(res.getString("cpf"));
		    usuario.setNomeCompleto(res.getString("nome"));
		    usuario.setSenha(res.getString("senha"));
		    usuario.setEnderecoEmail(res.getString("email"));
		    listaUsuario.add(usuario);
	    }
    return listaUsuario;
    }
}