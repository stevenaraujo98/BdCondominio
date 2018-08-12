/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad;

import bdcondominio.DataBase;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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
        System.out.println("query: " + call);
        DataBase.getStatement().execute(call);
    }
    
    
}
