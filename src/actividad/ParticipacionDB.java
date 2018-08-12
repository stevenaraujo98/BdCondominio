/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad;

import bdcondominio.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import usuarios.Usuario;
import usuarios.UsuarioDB;


public class ParticipacionDB {
    
    private ParticipacionDB() {}
    
    public static void crear(Participacion part) throws SQLException {
        int idHab = part.getUsuaio().getId();
        int idActivi = part.getActividad().getId();
        LocalDate fecha = part.getFecha();
        String tarea = part.getTarea();
        String call = "CALL CREARPARTICIPACION (" + idHab + ", " + idActivi + ", " + 
                                                "\'"+fecha.format(DateTimeFormatter.ISO_LOCAL_DATE)+"\'" 
                                                + ", " + "\"" + tarea + "\")";
        DataBase.getStatement().execute(call);
    }
    
    public static List<Participacion> getTareas(Usuario usuario) throws SQLException {
        List<Participacion> lista = new LinkedList<>();
        String call = "call PARTICIPACIONUSUARIO (" + usuario.getId() + ")";
        ResultSet set = DataBase.getStatement().executeQuery(call);
        while(set.next())
            lista.add(new Participacion(
                    new Actividad(set.getString(1), set.getString(2), 
                            LocalDate.parse(set.getString(3), DateTimeFormatter.ISO_LOCAL_DATE)), 
                    usuario, LocalDate.parse(set.getString(5), DateTimeFormatter.ISO_LOCAL_DATE), set.getString(4)));
        return lista;
    }
    
    public static List<Participacion> read(Actividad actividad) throws SQLException {
        List<Participacion> lista = new LinkedList<>();
        String call = "call READPATICIPACION (" + actividad.getId() + ")";
        ResultSet set = DataBase.getStatement().executeQuery(call);
        while(set.next())
            lista.add(new Participacion(set.getInt(1), actividad, UsuarioDB.getById(set.getInt(2)), 
                                        LocalDate.parse(set.getString(4), DateTimeFormatter.ISO_LOCAL_DATE), 
                                        set.getString(5)));
        return lista;
    }
    
    public static void delete(int id) throws SQLException {
        String call = "CALL DELTEPARTICIPACION (" +id+")";
        DataBase.getStatement().execute(call);
    }
    
    public static void update(Participacion p) throws SQLException {
        String call = "CALL EDITPARTICIPACION (" + p.getId() + ", " + p.getUsuaio().getId() + 
                                                ", " + "\'"+p.getFecha().format(DateTimeFormatter.ISO_LOCAL_DATE)+"\'"+
                                                ", " + "\""+p.getTarea()+"\")";
        DataBase.getStatement().execute(call);
    }
}
