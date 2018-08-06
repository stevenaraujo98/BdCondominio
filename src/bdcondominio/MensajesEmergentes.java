/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

import java.util.Optional;
import usuarios.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
    
    public static Optional<ButtonType> cofirmAccion(String text) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de acción");
        alert.setHeaderText("Confirmación de acción");
        alert.setContentText(text);
        return alert.showAndWait();
    }
    
    public static void infoSave(int id) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Guardado exitosamente"); 
        alert.setContentText("Id asignado: " + id); 
        alert.showAndWait();
    }
    
    public static void infoSave() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Guardado exitosamente"); 
        alert.showAndWait();
    }
    
    public static void errorAction() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!"); 
        alert.setHeaderText("Error!"); 
        alert.setContentText("Error al realizar la acción"); 
        alert.showAndWait();
    }
}