/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad;

import java.time.LocalDate;
import usuarios.Usuario;


public class Participacion {
    
    private int id;
    private Actividad actividad;
    private Usuario usuaio;
    private LocalDate fecha;
    private String tarea;

    public Participacion(int id, Actividad actividad, Usuario usuaio, LocalDate fecha, String tarea) {
        this.id = id;
        this.actividad = actividad;
        this.usuaio = usuaio;
        this.fecha = fecha;
        this.tarea = tarea;
    }

    public Participacion(Actividad actividad, Usuario usuaio, LocalDate fecha, String tarea) {
        this.actividad = actividad;
        this.usuaio = usuaio;
        this.fecha = fecha;
        this.tarea = tarea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Usuario getUsuaio() {
        return usuaio;
    }

    public void setUsuaio(Usuario usuaio) {
        this.usuaio = usuaio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }
    
    
}
