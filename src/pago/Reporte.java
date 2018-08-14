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
    private float monto;

    public Reporte(String nombre, String apellido, float monto) {
        this.nombre = nombre;
        this.apellido = apellido;
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

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
    
    
    
}
