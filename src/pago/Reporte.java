/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pago;

/**
 *
 * @author Municipio de Gye
 */
public class Reporte {
    
    private String nombre;
    private String apellido;
    private Float totalElectricidad;
    private Float totalAgua;
    private Float totalTelefono;
    private Float totalAlucuota;
    private Float totalMulta;
    private Float monto;

    public Reporte(String nombre, String apellido, Float totalElectricidad, 
            Float totalAgua, Float totalTelefono, Float totalAlucuota, 
            Float totalMulta, Float monto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.totalElectricidad = totalElectricidad;
        this.totalAgua = totalAgua;
        this.totalTelefono = totalTelefono;
        this.totalAlucuota = totalAlucuota;
        this.totalMulta = totalMulta;
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Float getTotalElectricidad() {
        return totalElectricidad;
    }

    public void setTotalElectricidad(float totalElectricidad) {
        this.totalElectricidad = totalElectricidad;
    }

    public Float getTotalAgua() {
        return totalAgua;
    }

    public void setTotalAgua(float totalAgua) {
        this.totalAgua = totalAgua;
    }

    public Float getTotalTelefono() {
        return totalTelefono;
    }

    public void setTotalTelefono(float totalTelefono) {
        this.totalTelefono = totalTelefono;
    }

    public Float getTotalAlucuota() {
        return totalAlucuota;
    }

    public void setTotalAlicuota(float totalAlucuota) {
        this.totalAlucuota = totalAlucuota;
    }

    public Float getTotalMulta() {
        return totalMulta;
    }

    public void setTotalMulta(float totalMulta) {
        this.totalMulta = totalMulta;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
       
}
