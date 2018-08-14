/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pago;

import apartamento.ApartamentoDB;
import bdcondominio.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 
 */
public class PagoBD {
    
    private PagoBD() {
    
    }
    
    public static boolean create(Pago pago, int idHabitante, int idAdministrador, int idApartamento) 
            throws SQLException {
        if(pago == null)
            return false;
        String query = "CALL CREATEPAGO (" +pago.getMonto()+","+"\'"+pago.getFecha()+"\'"+"," 
                                        +idApartamento+","+idAdministrador+","+
                                        pago.getFactura().getId()+","+idHabitante+")";
        return DataBase.getStatement().execute(query);
    }
    
    public static List<Pago> read(int idHabitante, LocalDate fecha) throws SQLException {
        ArrayList<Pago> pagos = new ArrayList<>();
        String query = "CALL READPAGO ("+idHabitante + ", " +"\'"+fecha +"\'"+ ")";
        ResultSet rs = DataBase.getStatement().executeQuery(query);
        if(rs == null)
            return null;
        while(rs.next()) {
            Pago pago = new Pago(rs.getInt(1), rs.getFloat(2), 
                    LocalDate.parse(rs.getString(3), DateTimeFormatter.ISO_LOCAL_DATE),
                    Factura.getById(rs.getInt(4)), ApartamentoDB.getById(rs.getInt(5))); 
            pagos.add(pago);
        }
        return pagos;
    }
    
    public static boolean update(int idPago, int idFac, int idApar, int idAdmin, 
            LocalDate datep, float mont, int idHab) throws SQLException {
        String query = "CALL UPDATEPAGO ("+idPago+","+idFac+","+idApar+","+idAdmin+
                                        ","+"\'"+datep+"\'"+","+mont+","+idHab+")";
        return DataBase.getStatement().execute(query); 
    }
    
    public static boolean delete(Pago pago) throws SQLException {
        String query = "CALL DELETEPAGO (" + pago.getId() + ")";
        return DataBase.getStatement().execute(query); 
    }
    
    public static List<Reporte> hacerReporte(LocalDate fechaI, LocalDate fechaF) throws SQLException {
        String call = "CALL LISTAPAGOS (" + "'"+fechaI.format(DateTimeFormatter.ISO_LOCAL_DATE)+"'"+", "+
                                        "'"+fechaI.format(DateTimeFormatter.ISO_LOCAL_DATE)+"')";
        ResultSet set = DataBase.getStatement().executeQuery(call);
        List<Reporte> lista = new LinkedList<>();
        while(set.next()) {
            lista.add(new Reporte(set.getString(1), set.getString(2), set.getFloat(3)));
        }
        return lista;
    }
    
}
