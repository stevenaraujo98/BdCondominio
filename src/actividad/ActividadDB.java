/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad;

import bdcondominio.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;


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
}
