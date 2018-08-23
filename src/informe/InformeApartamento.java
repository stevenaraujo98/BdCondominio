/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informe;

import apartamento.Apartamento;
import apartamento.ApartamentoDB;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import usuarios.Usuario;

/**
 *
 * @author SSAM
 */
public class InformeApartamento {
    private final Pane root;
    private final Label texto;
    private final ComboBox<String> filtro;
    private ObservableList<Apartamento> elementos;
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
        filtro = new ComboBox<>();
        texto = new Label("Filtrar por ");
        opciones();
        
        tabla = new TableView<>();
        id = new TableColumn<>("Id");
        precio = new TableColumn<>("Precio");
        descripcion = new TableColumn<>("Descripción");
        estado = new TableColumn<>("Estado");
        duenio = new TableColumn<>("Dueños");
        habitante = new TableColumn<>("Habitante");
        cantMascotas = new TableColumn<>("C. Mascotas");
        cantPersonas = new TableColumn<>("C. Personas");
        editarTabla(); 
        evtComboB();
        
        tabla.setItems(ApartamentoDB.getApartamentos());
        root.getChildren().addAll(new HBox(texto,filtro),tabla);
    }
    
    private void editarTabla(){
        tabla.setEditable(true);
        tabla.setTranslateY(40);
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
    
    private void opciones(){
        texto.setFont(new Font("Arial", 20));
        filtro.getItems().add("Todo");
        filtro.getItems().add("Ocupados");
        filtro.getItems().add("Libres");
        //filtro.getItems().add("Rango de precio");
        //filtro.getItems().add("Por dueños");
        
        filtro.getSelectionModel().selectFirst();
    }
    
    private void evtComboB(){
        filtro.setOnAction(e -> {
            if(filtro.getValue().equals("Todo")){
                tabla.getItems().clear();
                tabla.setItems(ApartamentoDB.getApartamentos());
            }else if(filtro.getValue().equals("Libres")){
                tabla.getItems().clear();
                tabla.setItems(ApartamentoDB.getApartamentosLibres());
            }else if(filtro.getValue().equals("Ocupados")){
                tabla.getItems().clear();
                tabla.getItems().addAll(ApartamentoDB.getApartamentosOcupados());
            }
        });
    }
    
    public Pane getContenido() {
       return root; 
    }
}
