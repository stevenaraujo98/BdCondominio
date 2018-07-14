/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

/**
 *
 * @author Kenny Camba
 */
public class Usuario {
    
    private String user;
    private String password;
    private String name;
    private String lastname;

    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public Usuario(String user, String password, String name, String lastname) {
        this(user, password);
        this.name = name;
        this.lastname = lastname;
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
  
}
