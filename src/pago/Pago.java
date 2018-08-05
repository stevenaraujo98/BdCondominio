/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pago;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * 
 */
public class Pago {
   
    private float monto;
    private LocalDate fecha;
    private Factura factura;
    private int id;
    
    public Pago(float monto, LocalDate fecha, Factura factura) {
        this.monto = monto;
        this.fecha = fecha;
        this.factura = factura;
        this.fecha.format(DateTimeFormatter.ISO_LOCAL_DATE); 
    }
    
    public Pago(int id, float monto, LocalDate fecha, Factura factura) {
        this(monto, fecha, factura);
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
}
