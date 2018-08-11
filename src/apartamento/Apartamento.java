/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartamento;

import usuarios.Usuario;

/**
 *
 * 
 */
public class Apartamento {
   
    private int id;
    private float precio;
    private String descripcion;
    private String estado;
    private Usuario owner;
    private Usuario habitante;
    private int cantMascotas;
    private int cantPersonas;

    public Apartamento(int id, float precio, String descripcion, String estado, Usuario owner, Usuario habitante, int cantMascotas, int cantPersonas) {
        this(precio, descripcion, estado, owner, habitante, cantMascotas, cantPersonas);
        this.id = id;
    }

    public Apartamento(float precio, String descripcion, String estado, Usuario owner, Usuario habitante, int cantMascotas, int cantPersonas) {
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.owner = owner;
        this.habitante = habitante;
        this.cantMascotas = cantMascotas;
        this.cantPersonas = cantPersonas;
    }

    public Apartamento(float precio, String descripcion, String estado, Usuario owner) {
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.owner = owner;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getOwner() {
        return owner;
    }

    public void setOwner(Usuario owner) {
        this.owner = owner;
    }

    public Usuario getHabitante() {
        return habitante;
    }

    public void setHabitante(Usuario habitante) {
        this.habitante = habitante;
    }

    public int getCantMascotas() {
        return cantMascotas;
    }

    public void setCantMascotas(int cantMascotas) {
        this.cantMascotas = cantMascotas;
    }

    public int getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }
    
    @Override
    public String toString() {
        return "id: " + id;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Apartamento))
            return false;
        return id == ((Apartamento)o).id;
    }
}
