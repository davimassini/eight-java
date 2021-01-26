package DAO;

import View.ConexaoMySQL;
import Model.Categoria;
import java.sql.*;
import java.sql.PreparedStatement;

public class CategoriaDAO {
    private Connection connection;
    
	String codigoCategoria;
	String nomeCategoria;
	
    public CategoriaDAO(){}
    
    public void adiciona(Categoria categoria){
    	this.connection = new ConexaoMySQL().abrirConexao();
        String sql = "INSERT INTO categorias(codCategoria, nomeCategoria) VALUES(?, ?)";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, categoria.getCodigoCategoria());
            stmt.setString(2, categoria.getNomeCategoria());
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        } 
        
    } 
    
}