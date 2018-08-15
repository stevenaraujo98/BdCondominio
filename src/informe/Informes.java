/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informe;

import java.util.LinkedList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author SSAM
 */
public class Informes {
    private final ComboBox<String> combo;
    private final GridPane root;    
    private Pane apartamento;
    private Pane usuario;
    
    public Informes(){
        root = new GridPane();
        combo = new ComboBox<>();
        LinkedList<String> lista = new LinkedList<>();
        lista.add("Usuario");
        lista.add("Apartamento");
        lista.add("Pagos");
        combo.getItems().setAll(lista);
        
        usuario = new InformeUsuario().getContenido();
        
        root.add(combo,0,0);
        root.setPadding(new Insets(10,10,10,10));
        accionar();
    }
    
    public void accionar(){
        combo.setOnAction(e -> {
            if(combo.getValue().equals("Apartamento")){
                root.getChildren().removeIf((Node n)-> !(n instanceof ComboBox));
                root.add(new InformeApartamento().getContenido(),0,1);
            }
            else if(combo.getValue().equals("Usuario")){
                root.getChildren().removeIf((Node n)-> !(n instanceof ComboBox));
                root.add(new InformeUsuario().getContenido(),0,1);
            }
            else if(combo.getValue().equals("Pagos")){
                root.getChildren().removeIf((Node n)-> !(n instanceof ComboBox));
                root.add(new InformePagos().getRoot(), 0, 1);
            }
        });
    }
    
    public Pane getContenido() {
       return root; 
    }
}
