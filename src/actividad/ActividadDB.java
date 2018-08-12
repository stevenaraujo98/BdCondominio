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


public class ActividadDB {
    
    private ActividadDB() {}
    
    public static int crear(Actividad actividad) throws SQLException {
        String call = "CALL CREARACTIVIDAD(" + "\""+actividad.getTitulo()+"\"" + 
                                            ", " + "\""+actividad.getDescrpcion()+"\")";
        ResultSet set = DataBase.getStatement().executeQuery(call);
        if(set.next()) 
            return set.getInt(1);
        return -1;
    }
    
    public static List<Actividad> listaActividades(LocalDate fi, LocalDate ff) throws SQLException {
        List<Actividad> lista = new LinkedList<>();
        String call = "CALL LISTAACTIVIDADES (" + "\'"+fi.format(DateTimeFormatter.ISO_LOCAL_DATE)+"\'"
                                              +", " + "\'"+ff.format(DateTimeFormatter.ISO_LOCAL_DATE)+"\')";
        ResultSet set = DataBase.getStatement().executeQuery(call);
        while(set.next())
            lista.add(new Actividad(set.getInt(1), set.getString(3), set.getString(2), 
                    LocalDate.parse(set.getString(4), DateTimeFormatter.ISO_LOCAL_DATE)));
        return lista;
    }
    
    public static void update(Actividad a) throws SQLException {
        String call = "CALL EDITACTIVIDAD (" + a.getId() + ", " + "\""+a.getTitulo()+"\"" +
                                            ", " +"\""+a.getDescrpcion()+"\")";
        DataBase.getStatement().execute(call);
    }
}
