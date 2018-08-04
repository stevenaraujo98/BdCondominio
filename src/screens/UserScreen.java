/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import bdcondominio.Usuario;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * 
 */
public class UserScreen {
    private UserPane userPane;
    private final Usuario user;
    private final Stage stage;
    
    public UserScreen(Usuario user) {
        this.user = user;
        stage = new Stage();
        start();
    }

   
    private void start() {
        userPane = new UserPane(stage, user); 
        Scene scene = new Scene(userPane.getPane(), Const.MAX_WIDTH, Const.MAX_HEIGHT);
        stage.setScene(scene);
        stage.setMaximized(true); 
        stage.setTitle("User - Home"); 
    }
    
    public void show() {
        stage.show();
    }
}
