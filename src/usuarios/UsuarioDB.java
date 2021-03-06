/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import bdcondominio.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nathaly Sanchez
 */
public class UsuarioDB {
    

    private UsuarioDB() {
    }    
    
    public static Usuario consultarUsuario(String usuario, String password) throws SQLException {
        //Lamada al procedimiento almacenado LOGINAPP
        String call = "CALL LOGINAPP (" + usuario + ", " + password + ")";
        
        Statement statement = DataBase.getStatement();
        ResultSet rs  = statement.executeQuery(call);
        if(rs == null)
            return null; 
        Usuario user = null;
        if(rs.next()){
            System.out.println(rs.getString(1) + "|" + rs.getString(2));
            user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), 
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));       
        }
        return user;
    }
    
    public static Usuario getById(int id) throws SQLException {
        String call = "CALL READUSER (" + id + ")";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        if(rs == null)
            return null;
        Usuario user = null;
        if(rs.next())
            user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), 
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
        return user;
    }
    
    public static int crear(Usuario user) throws SQLException {
        String call = "CALL CREATEUSER ("+"\""+user.getUser()+"\""+","+"\""+user.getPassword()+"\""+","+
                                        "\""+user.getName()+"\""+","+"\""+user.getLastname()+"\""+","+"\""+user.getEmail()+
                                        "\""+","+"\""+user.getTelefono()+"\""+")";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        if(rs.next()){
            user.setId(rs.getInt(1));
            return user.getId();
        }
        return -1;
    }
    
    public static boolean update(Usuario user) throws SQLException {
        String call = "CALL UPDATEUSER ("+user.getId()+","+"\""+user.getUser()+"\""+","+"\""+user.getPassword()+"\""+","+
                                        "\""+user.getName()+"\""+","+"\""+user.getLastname()+"\""+","+"\""+user.getEmail()+
                                        "\""+","+"\""+user.getTelefono()+"\""+","+"\""+user.getTipo()+"\""+")";
        return !DataBase.getStatement().execute(call); 
    }
    
    public static boolean delete(Usuario user) throws SQLException {
        String call = "CALL DELETEUSER ("+user.getId()+")";
        return !DataBase.getStatement().execute(call);
    }
    
    public static Usuario getCurrentAdmin() throws SQLException {
        String call = "CALL CURRENTADMIN()";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        if(rs == null)
            return null;
        Usuario admin = null;
        if(rs.next())
            admin = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), 
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
        return admin;
    }
    
    public static List<Usuario> getListaUsuarios() throws SQLException{
        List<Usuario> usuariolist= new ArrayList<>();
        String call = "SELECT * FROM LISTADOUSUARIOS";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        while(rs.next()) {
            Usuario u = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), 
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            usuariolist.add(u);
        }
        return usuariolist;
    }
    
    public static List<Integer> getListaUsuariosId() throws SQLException{
        List<Integer> usuariolist= new ArrayList<>();
        String call = "SELECT idHabitante FROM LISTADOUSUARIOS";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        while(rs.next()) {
            usuariolist.add(rs.getInt(1));
        }
        return usuariolist;
    }
    
    public static ObservableList<Usuario> getListaUsuariosCombo() throws SQLException{
        ObservableList<Usuario> usuariolist= FXCollections.observableArrayList();
        String call = "SELECT * FROM LISTADOUSUARIOS";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        while(rs.next()) {
            Usuario u = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), 
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            usuariolist.add(u);
        }
        return usuariolist;
    }

}
