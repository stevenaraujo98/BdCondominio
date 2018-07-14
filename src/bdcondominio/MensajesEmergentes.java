/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author SSAM
 */
public class MensajesEmergentes {
    public static void errorLog(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("El usuario o contraseña ingresados son incorrectos");
        alert.setContentText("Vuelva a intentar, verificando sean los correctos.");

        alert.showAndWait();
    }
    
    public static void accessSuccessful(Usuario usuario) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acceso exitoso"); 
        alert.setHeaderText("Usted se ha ingresado con el usuario: " + usuario.getUser());
        alert.setContentText("Bienbenido(a): " + usuario.getName() + " " + usuario.getLastname()); 
        alert.showAndWait();
    }
}