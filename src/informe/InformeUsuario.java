/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informe;

import apartamento.Apartamento;
import apartamento.ApartamentoDB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import usuarios.Usuario;
import usuarios.UsuarioDB;

/**
 *
 * @author SSAM
 */
public class InformeUsuario {
    private final Pane root;
    
    private final TableView<Usuario> tabla;
    private final TableColumn<Usuario, Integer> id;
    private final TableColumn<Usuario, String> user;
//    private final TableColumn<Usuario, String> password;
    private final TableColumn<Usuario, String> name;
    private final TableColumn<Usuario, String> lastname;
    private final TableColumn<Usuario, String> email;
    private final TableColumn<Usuario, String> telefono;
    private final TableColumn<Usuario, String> tipo;    
    
    public InformeUsuario(){
        root = new Pane();
        
        tabla = new TableView<>();
        id = new TableColumn<>("id Usuario");
        user = new TableColumn<>("Usuario");
        //password = new TableColumn<>("");
        name = new TableColumn<>("Name");
        lastname = new TableColumn<>("Apellido");
        email = new TableColumn<>("Correo");
        telefono = new TableColumn<>("Telefono");
        tipo = new TableColumn<>("Tipo");
        editarTabla();        
        
        try {
            tabla.setItems(UsuarioDB.getListaUsuariosCombo());
        } catch (SQLException ex) {
            Logger.getLogger(Informes.class.getName()).log(Level.SEVERE, null, ex);
        }
        root.getChildren().add(tabla);
    }
    
    public void editarTabla(){
        tabla.setEditable(true);
        tabla.setTranslateX(10);
        tabla.setTranslateY(10);
        tabla.setMaxSize(890, 870);        
        
        id.setMinWidth(90);
        user.setMinWidth(130);
        name.setMinWidth(130);
        lastname.setMinWidth(130);
        email.setMinWidth(180);
        telefono.setMinWidth(120);
        tipo.setMinWidth(90);
        tabla.getColumns().addAll(id, user, name, lastname, email, telefono, tipo);
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
    
    public Pane getContenido() {
       return root; 
    }
}