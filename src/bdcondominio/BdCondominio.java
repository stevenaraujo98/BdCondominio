/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdcondominio;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 *
 * @author SSAM
 */
public class BdCondominio extends Application {
    
    @Override
    public void init() {
        try{
            UsuarioDB.conectar();
        }catch(SQLException ex){
            System.out.println("Error de conexion");
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(BdCondominio.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox p = new VBox();  

        ImageView iv = new ImageView(new Image(BdCondominio.class.getResourceAsStream("kenast.png"), 100, 70, true, true));
        Scene scene = new Scene(p, 300, 250);
        p.setPadding(new Insets(20, 20, 20, 20));
        stage.setScene(scene); 
        stage.show();
        Label user = new Label("Usuario");
        Label cont = new Label("Contraseña");
        TextField cuadroUsuario = new TextField();
        PasswordField cuadroContra = new PasswordField();
        Button iniciarSesion = new Button("Iniciar Sesión");
        p.setSpacing(20);
        p.setAlignment(Pos.CENTER);
        p.getChildren().addAll(iv, new HBox(30, user, cuadroUsuario), new HBox(10, cont, cuadroContra), iniciarSesion);

        iniciarSesion.setOnAction(e ->{
            try {
                UsuarioDB.consultarUsuario("\""+cuadroUsuario.getText()+"\"", "\""+cuadroContra.getText()+"\"");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(BdCondominio.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       
    }
    
    @Override
    public void stop() {
        UsuarioDB.close();
    }
    
    public static void main(String[] args) { 
        launch();
    }
}
