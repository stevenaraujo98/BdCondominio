/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import usuarios.Usuario;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * 
 */
public class AdminScreen {
    
    private AdminPane adminPane;
    private final Usuario user;
    private final Stage stage;
    
    public AdminScreen(Usuario user) {
        this.user = user;
        stage = new Stage();
        start();
    }

   
    private void start() {
        adminPane = new AdminPane(stage, user); 
        Scene scene = new Scene(adminPane.getPane(), Const.MAX_WIDTH, Const.MAX_HEIGHT);
        stage.setScene(scene);
        stage.setMaximized(true); 
        stage.setTitle("Admin - Home"); 
    }
    
    public void show() {
        stage.show();
    }
}
