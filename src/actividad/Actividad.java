/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad;


public class Actividad {
    
    private int id;
    private String titulo;
    private String descrpcion;

    public Actividad(int id, String titulo, String descrpcion) {
        this.id = id;
        this.titulo = titulo;
        this.descrpcion = descrpcion;
    }

    public Actividad(String titulo, String descrpcion) {
        this.titulo = titulo;
        this.descrpcion = descrpcion;
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
        return descrpcion;
    }

    public void setDescrpcion(String descrpcion) {
        this.descrpcion = descrpcion;
    }
    
    
}
