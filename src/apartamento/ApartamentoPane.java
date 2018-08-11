/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartamento;

import bdcondominio.MensajesEmergentes;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import usuarios.Usuario;
import usuarios.UsuarioDB;

/**
 *
 * 
 */
public class ApartamentoPane {
    
    private final TextField precio;
    private final TextArea descripcion;
    private final ComboBox<String> estados;
    private final ComboBox<Usuario> propietarios;
    private final ComboBox<Usuario> encargados;
    private final TextField cantmascontas;
    private final TextField cantPersonas;
    private final Button save;
    private final GridPane grid;
    
    public ApartamentoPane() {
        precio = new TextField();
        descripcion = new TextArea();
        estados = new ComboBox<>();
        encargados = new ComboBox<>();
        propietarios = new ComboBox<>();
        cantmascontas = new TextField();
        cantPersonas = new TextField();
        save = new Button("Guardar");
        grid = new GridPane();
        estados.getItems().addAll("Libre", "Ocupado");
        crearPane();
    }
    
    private void crearPane() {
        grid.addColumn(0, new Text("Precio"), new Text("Descripcion"), new Text("Estados"), new Text("Propetarios"),
                new Text("Encargados"), new Text("Cantidad mascotas"), new Text("Cantidad personas"));
        grid.addColumn(1, precio, descripcion, estados, encargados, propietarios, cantmascontas, cantPersonas);
        descripcion.setMaxHeight(70);
        descripcion.setMaxWidth(250);
        
        grid.setPadding(new Insets(10, 10, 10, 10)); 
        try{
             encargados.getItems().addAll(UsuarioDB.getListaUsuarios());
        }catch(SQLException ex){
            MensajesEmergentes.errorLog();
        }
        
         try{
             propietarios.getItems().addAll(UsuarioDB.getListaUsuarios());
        }catch(SQLException ex){
            MensajesEmergentes.errorLog();
            ex.printStackTrace();
        }
    } 
    
    public Pane getContenido() {
       return grid; 
    }
    
}
