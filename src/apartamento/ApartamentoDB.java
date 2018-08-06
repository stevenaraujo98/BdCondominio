/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartamento;

import bdcondominio.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import usuarios.UsuarioDB;

/**
 *
 * 
 */
public class ApartamentoDB {
   
    private ApartamentoDB() {
    
    }
    
    public static List<Apartamento> getUserApartments(int id) throws SQLException {
        String call = "CALL USERAPARTMENTS (" + id + ")";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        if(rs == null)
            return null;
        LinkedList<Apartamento> lista = new LinkedList<>();
        while(rs.next()) {
            Apartamento a = new Apartamento(rs.getInt(1), rs.getFloat(2), rs.getString(3), rs.getString(4),
                                    UsuarioDB.getById(rs.getInt(5)), UsuarioDB.getById(rs.getInt(6)), 
                                    rs.getInt(7), rs.getInt(8)); 
            lista.add(a);
        }
        return lista;
    }
    
    public static Apartamento getById(int id) throws SQLException {
        String call = "CALL APARTMENTBYID (" + id + ")";
        ResultSet rs = DataBase.getStatement().executeQuery(call);
        if(rs == null)
            return null;
        Apartamento a = null;
        if(rs.next())
            a = new Apartamento(rs.getInt(1), rs.getFloat(2), rs.getString(3), rs.getString(4),
                                UsuarioDB.getById(rs.getInt(5)), UsuarioDB.getById(rs.getInt(6)), 
                                rs.getInt(7), rs.getInt(8));
        return a;
    }
   
    
}
