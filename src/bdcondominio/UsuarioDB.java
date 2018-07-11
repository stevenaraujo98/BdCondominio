/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nathaly Sanchez
 */
public class UsuarioDB {
    
    static String usuario;
    static String pass;
    static Connection connection;
    
    
    public static void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/kenastdb";
        String username = "root";
        String password = "";
        connection = DriverManager.getConnection(url, username, password);
    }
    
    public static boolean consultarUsuario(String usuario, String password) throws SQLException {
        String consulta = "SELECT users, passwords "
                         +"FROM LOGIN"
                         +"WHERE user = " + usuario + " AND password = " + password;
        
        Statement statement = connection.createStatement();
        ResultSet rs  = statement.executeQuery(consulta);
        System.out.println(rs.getString(0)); 
        return true;
    }
    
    public static boolean close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
}
