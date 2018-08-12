/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informe;

import apartamento.Apartamento;
import apartamento.ApartamentoDB;
import java.sql.SQLException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import usuarios.Usuario;

/**
 *
 * @author SSAM
 */
public class Informes {
    private final Pane root;
    private final TableView<Apartamento> tabla;
    private final TableColumn<Apartamento, Integer> id;
    private final TableColumn<Apartamento, Float> precio;
    private final TableColumn<Apartamento, String> descripcion;
    private final TableColumn<Apartamento, String> estado;
    private final TableColumn<Apartamento, Usuario> duenio;
    
    
    
    public Informes() throws SQLException{
        root = new Pane();
        tabla = new TableView<>();
        id = new TableColumn<>("id Apartamento");
        precio = new TableColumn<>("Precio");
        descripcion = new TableColumn<>("Descripcion");
        estado = new TableColumn<>("Estado");
        duenio = new TableColumn<>("Dueños");
        editarTabla();        
        
        tabla.setItems(ApartamentoDB.getApartamentos());
        root.getChildren().add(tabla);
    }
    
    public void editarTabla(){
        tabla.setEditable(true);
        tabla.setTranslateX(10);
        tabla.setTranslateY(10);
        
        
        id.setMinWidth(140);
        precio.setMinWidth(140);
        descripcion.setMinWidth(140);
        estado.setMinWidth(140);
        duenio.setMinWidth(140);
        tabla.getColumns().addAll(id, precio, descripcion, estado, duenio);
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        precio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        estado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        duenio.setCellValueFactory(new PropertyValueFactory<>("owner"));
    }
    
    public Pane getContenido() {
       return root; 
    }
}
