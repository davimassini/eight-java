package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexaoMySQL {
   
   
     public Connection con = null;
     public Statement stmt = null;
     public ResultSet resultset = null;
     String servidor = "jdbc:mysql://localhost:3306/eightProj";
     String usuario = "root";
     String senha = "";
     String driver = "com.mysql.jdbc.Driver";
   
   
    public Connection abrirConexao() {
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(servidor, usuario, senha);
            stmt = con.createStatement();
            System.out.println("Conexão aberta com sucesso");
        }catch(Exception e){ 
        	System.out.println("erro ao acessar banco de dados");
        	e.printStackTrace();
        }
        return con;
           
    }

    public void fecharConexao() {
        try{
        	con.close();
        	System.out.println("Conexão finalizada com sucesso");
           
        }catch (Exception e ) {
        	System.out.println("Erro ao encerrar conexão" + e.getMessage());
        } 
    }
    
    public ResultSet executaBusca(String sql) {
		try {
			Class.forName(driver).newInstance();
            con = DriverManager.getConnection(servidor, usuario, senha);
    		Statement stm = con.createStatement();
    		ResultSet rs = stm.executeQuery(sql);
    		con.close();
    		return rs;
		} catch (Exception e) {
			e.printStackTrace();
    		return null;
		}
	}
}  