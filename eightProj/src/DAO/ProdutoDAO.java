package DAO;

import View.ConexaoMySQL;
import Model.Produto;
import java.sql.*;
import java.sql.PreparedStatement;

public class ProdutoDAO {
    private Connection connection;
    
	String codigoProduto;
	String nomeProduto;
	String enderecoProduto;
	String codigoCategoria = "123456";
	String categoria;
	String precoUnitario;
	String estoqueProduto;
	String classificacaoProduto = "1";
	
    public ProdutoDAO(){ 
        this.connection = new ConexaoMySQL().abrirConexao();
    } 
    public void adiciona(Produto produto){ 
        String sql = "INSERT INTO produtos(codProduto, nomeProduto, descProduto, codCategoria, nomeCategoria, precoProduto, estoqueProduto, classificacaoProduto) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, produto.getCodigoProduto());
            stmt.setString(2, produto.getNomeProduto());
            stmt.setString(3, produto.getDescricaoProduto());
            stmt.setString(4, codigoCategoria);
            stmt.setString(5, produto.getCategoria());
            stmt.setString(6, produto.getPrecoUnitario());
            stmt.setString(7, produto.getEstoqueProduto());
            stmt.setString(8, classificacaoProduto);
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        } 
        
    } 
    
}