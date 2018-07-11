/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 *
 * @author SSAM
 */
public class BdCondominio extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       VBox p = new VBox();  
       
//       Image i = new Image("kenast.png", 100, 70, true, true);
//       ImageView iv = new ImageView(i);
       Scene scene = new Scene(p, 300, 250);
       p.setPadding(new Insets(20, 20, 20, 20));
       stage.setScene(scene); 
       stage.show();
       Label user = new Label("Usuario");
       Label cont = new Label("Contraseña");
       TextField cuadroUsuario = new TextField();
       TextField cuadroContra = new TextField();
       Button iniciarSesion = new Button("Iniciar Sesión");
       p.setSpacing(20);
       p.getChildren().addAll(new HBox(10, user, cuadroUsuario), new HBox(10, cont, cuadroContra), iniciarSesion);
    }
    
    public static void main(String[] args) { 
        launch();
    }
}
