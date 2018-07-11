/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nathaly Sanchez
 */
public class UsuarioDB {
    
    String usuario;
    String password;
    
    public UsuarioDB(String usuario, String password){
        this.usuario = usuario;
        this.password = password;
    }
    
    public void conectar() {
        String url = "jdbc:mysql://localhost:3306/kenastDB";
        String username = "root";
        String password = "contraseña";
        try(Connection connection = DriverManager.getConnection(url, username, password);){
            Statement statement = connection.createStatement();
        }catch(SQLException ex){
        
        }
    }
}
