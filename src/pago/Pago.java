/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pago;

import apartamento.Apartamento;
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
    private Apartamento apartamento;
    
    public Pago(float monto, LocalDate fecha, Factura factura, Apartamento apartamento) {
        this.monto = monto;
        this.fecha = fecha;
        this.factura = factura;
        this.fecha.format(DateTimeFormatter.ISO_LOCAL_DATE); 
        this.apartamento = apartamento;
    }
    
    public Pago(int id, float monto, LocalDate fecha, Factura factura, Apartamento apartamento) {
        this(monto, fecha, factura, apartamento);
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

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }
    
    @Override
    public String toString() {
        return "Tipo: " + factura.getTipo() + ", Fecha: " + fecha + ",  Monto: " + monto;
    }
    
}
