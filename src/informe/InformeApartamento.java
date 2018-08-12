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

/**
 *
 * @author SSAM
 */
public class InformeApartamento {
    private final Pane root;
    private final TableView<Apartamento> tabla;
    private final TableColumn<Apartamento, Integer> id;
    private final TableColumn<Apartamento, Float> precio;
    private final TableColumn<Apartamento, String> descripcion;
    private final TableColumn<Apartamento, String> estado;
    private final TableColumn<Apartamento, Usuario> duenio;
    private final TableColumn<Apartamento, Usuario> habitante;
    private final TableColumn<Apartamento, Usuario> cantMascotas;
    private final TableColumn<Apartamento, Usuario> cantPersonas;
    
    public InformeApartamento(){
        root = new Pane();
        
        tabla = new TableView<>();
        id = new TableColumn<>("id Apartamento");
        precio = new TableColumn<>("Precio");
        descripcion = new TableColumn<>("Descripcion");
        estado = new TableColumn<>("Estado");
        duenio = new TableColumn<>("Dueños");
        habitante = new TableColumn<>("Habitante");
        cantMascotas = new TableColumn<>("C. Mascotas");
        cantPersonas = new TableColumn<>("C. Personas");
        editarTabla();        
        
        try {
            tabla.setItems(ApartamentoDB.getApartamentos());
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
        
        id.setMinWidth(110);
        precio.setMinWidth(65);
        descripcion.setMinWidth(180);
        estado.setMinWidth(90);
        duenio.setMinWidth(140);
        habitante.setMinWidth(120);
        cantMascotas.setMinWidth(90);
        cantPersonas.setMinWidth(90);
        tabla.getColumns().addAll(id, precio, descripcion, estado, duenio, habitante, cantMascotas, cantPersonas);
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        precio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        estado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        duenio.setCellValueFactory(new PropertyValueFactory<>("owner"));
        habitante.setCellValueFactory(new PropertyValueFactory<>("habitante"));
        cantMascotas.setCellValueFactory(new PropertyValueFactory<>("cantMascotas"));
        cantPersonas.setCellValueFactory(new PropertyValueFactory<>("cantPersonas"));
    }
    
    public Pane getContenido() {
       return root; 
    }
}
