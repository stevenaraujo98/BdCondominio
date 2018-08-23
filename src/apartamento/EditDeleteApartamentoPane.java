/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartamento;

import bdcondominio.MensajesEmergentes;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import usuarios.Usuario;
import usuarios.UsuarioDB;

/**
 *
 * @author SSAM
 */
public final class EditDeleteApartamentoPane {
    private final GridPane root;
    private final ComboBox<Apartamento> combo;
    private final Button borrar;
    private final Button actualizar;
    private final TextField precio;
    private final TextArea descripcion;
    private final TextField estados;
    private final TextField propietarios;
    private final TextField encargados;
    private final TextField cantmascotas;
    private final TextField cantPersonas;
    private final Text status;
    private final HBox hb;
    private List<Apartamento> lista;
    private boolean disableb;
    
    
    public EditDeleteApartamentoPane(){
        root = new GridPane();
        combo = new ComboBox<>();
        precio = new TextField();
        descripcion = new TextArea();
        estados = new TextField();
        propietarios = new TextField();
        encargados = new TextField();
        cantmascotas = new TextField();
        cantPersonas = new TextField();
        
        borrar = new Button("Borrar");
        borrar.setDisable(true);
        actualizar = new Button("Actualizar");
        actualizar.setDisable(true);
        status = new Text("");
        hb = new HBox(borrar, actualizar);
        formato();
        llenar();
        disableTextos(true); 
        borrarDato();
        actualizarDato();
        
        root.addColumn(0, combo, new Text("Precio"), new Text("Descripción"), new Text("Estados"), new Text("Propetarios"),
                new Text("Encargados"), new Text("Cantidad mascotas"), new Text("Cantidad personas"), status,hb);
        root.addColumn(1, new Label(), precio, descripcion, estados, propietarios, encargados, cantmascotas, cantPersonas);
    }
    
    public void disableTextos(boolean disableb){
        precio.setDisable(disableb);
        descripcion.setDisable(disableb);
        estados.setDisable(disableb);
        propietarios.setDisable(disableb);
        encargados.setDisable(disableb);
        cantmascotas.setDisable(disableb);
        cantPersonas.setDisable(disableb);
    }
    
    public void formato(){
        root.setPadding(new Insets(10, 10, 10, 10));
        
        status.setFill(Color.RED);
        descripcion.setMaxHeight(70);
        descripcion.setMaxWidth(250);
        hb.setSpacing(20);
        hb.setPadding(new Insets(15,15,15,0));
        try{
            lista = ApartamentoDB.getListaApartamentos();
            combo.getItems().addAll(lista);
        }catch(SQLException ex){
            MensajesEmergentes.errorLog();
            Logger.getLogger(EditDeleteApartamentoPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void llenar(){
        combo.setOnAction(e -> {
            Apartamento a = combo.getValue();
            if(a != null){
                precio.setText(String.valueOf(a.getPrecio()));
                descripcion.setText(a.getDescripcion());
                estados.setText(a.getEstado());
                propietarios.setText(String.valueOf(a.getOwner().getId()));
                if(a.getHabitante() != null)encargados.setText(String.valueOf(a.getHabitante().getId()));
                else encargados.setText("No tiene habitante");            
                cantmascotas.setText(String.valueOf(a.getCantMascotas()));
                cantPersonas.setText(String.valueOf(a.getCantPersonas()));
                disableTextos(true);
                actualizar.setText("Actualizar");
                borrar.setDisable(false);
                actualizar.setDisable(false);
            }else{
                precio.setText("");
                descripcion.setText("");
                estados.setText("");
                propietarios.setText("");
                encargados.setText("");
                cantmascotas.setText("");
                cantPersonas.setText("");
                disableTextos(true);
                actualizar.setText("Actualizar");
            }
        });        
    }
    
    public void borrarDato(){
        borrar.setOnAction(e -> {
            status.setText("");
            try {
                Apartamento a = combo.getValue();
                if(a != null){
                    if(MensajesEmergentes.cofirmAccion("Seguro desea borrar este apartamento").get() == ButtonType.OK){
                        ApartamentoDB.eliminarApartamento(a.getId());                        
                        disableTextos(true);
                        
                        combo.getItems().removeAll(lista);
                        lista = ApartamentoDB.getListaApartamentos();
                        combo.getItems().addAll(lista);
                    }                    
                }
                else status.setText("Seleccione un apartamento.");
            } catch (SQLException ex) {
                Logger.getLogger(EditDeleteApartamentoPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private boolean validar(String precio, String descripcion, String estado, Usuario user) {
        return precio.isEmpty() || descripcion.isEmpty() || estado.isEmpty() || user == null;
    }
    
    public void actualizarDato(){
        actualizar.setOnAction(e -> {
            if(actualizar.getText().equals("Actualizar")){
                actualizar.setText("Guardar");
                disableTextos(false);
            }else if(actualizar.getText().equals("Guardar")){
                try {
                    update();
                } catch (SQLException ex) {
                    Logger.getLogger(EditDeleteApartamentoPane.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    private void update() throws SQLException{
        status.setText("");
        Apartamento a = combo.getValue();
        Integer id = a.getId(); 
        Float preciot = Float.parseFloat(precio.getText());
        String descripciont = descripcion.getText();
        String estadosc = estados.getText();
        Usuario propietariosb = UsuarioDB.getById(Integer.parseInt(propietarios.getText()));
        Usuario encargadosb = UsuarioDB.getById(Integer.parseInt(encargados.getText()));
        
        if(validar(String.valueOf(preciot), descripciont, estadosc, propietariosb)) 
            status.setText("Por favor llene todos los campos");
        else if(estadosc.equals("Ocupado") && (encargadosb == null || cantmascotas.getText().equals("") || cantPersonas.getText().equals("")))
            status.setText("Por favor llene todos los campos");
        else {
            Integer cantmt = Integer.parseInt(cantmascotas.getText());
            Integer cantot = Integer.parseInt(cantPersonas.getText());
            
            Apartamento newApart = new Apartamento(id, preciot, descripciont, estadosc, propietariosb, encargadosb, cantmt, cantot);
            if(MensajesEmergentes.cofirmAccion("Desea guardar estos cambios?").get() == ButtonType.OK){
                ApartamentoDB.actualizarApartamento(newApart);
                actualizar.setText("Actualizar");
                disableTextos(true);

                combo.getItems().removeAll(lista);
                lista = ApartamentoDB.getListaApartamentos(); 
                combo.getItems().addAll(lista);
            }
        }
    }
    
    public Pane getContenido() {
       return root; 
    }
}
