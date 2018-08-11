/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

/**
 *
 * @author Kenny Camba
 */
public class Usuario {
    
    private int id;
    private String user;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private String telefono;
    private String tipo;

    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public Usuario(String user, String password, String name, String lastname, 
            String email, String telefono, String tipo) {
        this(user, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }
    
    public Usuario(int id, String user, String password, String name, String lastname, 
            String email, String telefono, String tipo) {
        this(user, password, name, lastname, email, telefono, tipo);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return name + " " +lastname;
    }
  
}
