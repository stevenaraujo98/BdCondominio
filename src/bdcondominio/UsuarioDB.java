/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nathaly Sanchez
 */
public class UsuarioDB {
    
    private static Connection connection;

    private UsuarioDB() {
    }    
    
    public static void conectar() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/kenastdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
    }
    
    public static boolean consultarUsuario(String usuario, String password) throws SQLException {
        String consulta = "SELECT users, passwords, Nombre, Apellido  "
                         +"FROM LOGIN l, HABITANTE h "
                         +"WHERE users = " + usuario + " AND passwords = " + password + " "
                         +"AND l.idhabitante = h.idhabitante";
        
        Statement statement = connection.createStatement();
        ResultSet rs  = statement.executeQuery(consulta);
        if(rs == null)
            return false;
        if(rs.next()){
            System.out.println(rs.getString(1) + "|" + rs.getString(2));
            MensajesEmergentes.accessSuccessful(
                    new Usuario(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4))); 
        }else
            MensajesEmergentes.errorLog();
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
