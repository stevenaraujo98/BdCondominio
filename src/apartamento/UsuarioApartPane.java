/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apartamento;

import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import usuarios.Usuario;


public class UsuarioApartPane extends VBox{
 
    private final Usuario user;
    private final TextField precio;
    private final TextArea descrip;
    private final TextField cantp;
    private final TextField cantm;
    private final ComboBox<Apartamento> apartamentos;
    
    public UsuarioApartPane(Usuario user) {
        this.user = user;
        precio = new TextField();
        descrip = new TextArea();
        cantp = new TextField();
        cantm = new TextField();
        apartamentos = new ComboBox<>();
        formato();
        contenido();
    }
    
    private void formato() {
        UnaryOperator<TextFormatter.Change> filter1 = change -> {
            if (change.getText().matches("[0-9]*"))
                return change;
            return null;
        };
        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter1);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter1);
        cantp.setTextFormatter(textFormatter1);
        cantm.setTextFormatter(textFormatter2);
        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (decimalPattern.matcher(change.getControlNewText()).matches())
                return change;
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        precio.setTextFormatter(textFormatter);
        super.setPadding(new Insets(10, 10, 10, 10));
        super.setSpacing(10); 
        descrip.setMaxHeight(70);
        descrip.setMaxWidth(250);
    }
    
    private void contenido() {
        try { 
            apartamentos.getItems().addAll(ApartamentoDB.getUserApartments(user.getId()));
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioApartPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        HBox box = new HBox(15, new Text("Apartamentos"), apartamentos);
        box.setAlignment(Pos.CENTER_LEFT); 
        super.getChildren().add(box);
        evtCombo();
    }
    
    private void evtCombo() {
        apartamentos.setOnAction(e -> {
            super.getChildren().removeIf((Node n)-> n instanceof GridPane);
            GridPane grid = new GridPane();
            grid.setVgap(10);
            grid.setHgap(10); 
            Apartamento a = apartamentos.getValue();
            if(a != null){
                grid.addColumn(0, new Text("Precio"), new Text("Descripcion"), 
                        new Text("Cantidad personas"), new Text("Cantidad mascotas"));
                grid.addColumn(1, precio, descrip, cantp, cantm);
                precio.setText(a.getPrecio() + "");
                descrip.setText(a.getDescripcion());
                cantp.setText(a.getCantPersonas()+"");
                cantm.setText(a.getCantMascotas()+""); 
                grid.setDisable(true); 
                super.getChildren().add(grid);
             }
        }); 
    }
}
