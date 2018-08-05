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
 * 
 */
public class DataBase {
    private static Connection connection;
    
    private DataBase() {
    
    }
    
    public static void conectar() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/kenastdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
    }
    
    public static boolean close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
    
    public static Statement getStatement() throws SQLException {
        return connection.createStatement();
    }
}
