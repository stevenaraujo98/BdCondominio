/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartamento;

import bdcondominio.MensajesEmergentes;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import usuarios.RegistrarUsuarioPane;
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
    private final TextField cantmascotas;
    private final TextField cantPersonas;
    private final Text status;
    private final Button save;
    private final GridPane grid;
    
    public ApartamentoPane() {
        precio = new TextField();
        descripcion = new TextArea();
        estados = new ComboBox<>();
        propietarios = new ComboBox<>();
        encargados = new ComboBox<>();
        cantmascotas = new TextField();
        cantPersonas = new TextField();
        status = new Text();
        save = new Button("Guardar");
        grid = new GridPane();
        estados.getItems().addAll("Libre", "Ocupado");
        crearPane();
        evtDisponibilidad();
        evtSave();
    }
    
    private void crearPane() {
        grid.addColumn(0, new Text("Precio"), new Text("Descripcion"), new Text("Estados"), new Text("Propetarios"),
                new Text("Encargados"), new Text("Cantidad mascotas"), new Text("Cantidad personas"));
        grid.addColumn(1, precio, descripcion, estados, propietarios, encargados, cantmascotas, cantPersonas,status,save);
        descripcion.setMaxHeight(70);
        descripcion.setMaxWidth(250);
        status.setFill(Color.RED);
        estados.setValue("Libre");
        
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
    
    public void evtDisponibilidad(){        
        precio.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().isLetterKey()){
                precio.setText("");
            }
        });
        
        cantmascotas.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().isLetterKey()){
                cantmascotas.setText("");
            }
        });
        
        cantPersonas.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().isLetterKey()){
                cantPersonas.setText("");
            }
        });
    }
    
    private void evtSave() {
        save.setOnAction(e -> {
            status.setText("");
            String preciot = precio.getText();
            String descripciont = descripcion.getText();
            String estadosc = estados.getValue();
            Usuario propietariosb = propietarios.getValue();
            Usuario encargadosb = encargados.getValue();
            String cantmt = cantmascotas.getText();
            String cantot = cantPersonas.getText();
            
            if(validar(preciot, descripciont, estadosc, propietariosb)) 
                status.setText("Por favor llene todos los campos");
            else if(estados.getValue().equals("Ocupado") && (encargadosb == null || cantmt.equals("") || cantot.equals("")))
                status.setText("Por favor llene todos los campos");
            else {
                try{
                    int id = -1;
                    if(estados.getValue().equals("Ocupado")){
                        Apartamento newApart = new Apartamento(Float.parseFloat(preciot), descripciont, estadosc, propietariosb, encargadosb, Integer.parseInt(cantmt), Integer.parseInt(cantot));
                        if(MensajesEmergentes.cofirmAccion("Desea guardar este apartamento").get() == ButtonType.OK){
                            id = ApartamentoDB.crearC(newApart);                            
                        }
                    }else{
                        Apartamento newApart = new Apartamento(Float.parseFloat(preciot), descripciont, estadosc, propietariosb);
                        if(MensajesEmergentes.cofirmAccion("Desea guardar este apartamento").get() == ButtonType.OK){
                            id = ApartamentoDB.crearI(newApart);                            
                        }
                    }
                    if(id != -1){
                        MensajesEmergentes.infoSave(id); 
                        clear();
                    }else {
                        System.out.println("Error al guardar");
                    }
                }catch(SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(RegistrarUsuarioPane.class.getName()).log(Level.SEVERE, null, ex); 
                }
            }
        }); 
    }
    
    private boolean validar(String precio, String descripcion, String estado, Usuario user) {
        return precio.isEmpty() || descripcion.isEmpty() || estado.isEmpty() || user == null;
    }
    
    private void clear() {
        status.setText("");
        precio.setText("");
        descripcion.setText("");
        estados.setValue("Libre");
        propietarios.setValue(null);
        encargados.setValue(null);
        cantmascotas.setText("");
        cantPersonas.setText("");
    }
}