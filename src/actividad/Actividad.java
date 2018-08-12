/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad;


import java.time.LocalDate;


public class Actividad {
    
    private int id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;

    public Actividad(int id, String titulo, String descrpcion, LocalDate fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descrpcion;
        this.fechaCreacion = fechaCreacion;
    }

    public Actividad(String titulo, String descrpcion, LocalDate fechaCreacion) {
        this.titulo = titulo;
        this.descripcion = descrpcion;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescrpcion() {
        return descripcion;
    }

    public void setDescrpcion(String descrpcion) {
        this.descripcion = descrpcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    @Override
    public String toString() {
        return "Titulo: " + titulo + ", Fecha de creación: " + fechaCreacion;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Actividad))
            return false;
        Actividad a = (Actividad)o;
        return a.titulo.equals(titulo) && a.descripcion.equals(descripcion) && 
                a.fechaCreacion.equals(fechaCreacion);
    }
    
}
