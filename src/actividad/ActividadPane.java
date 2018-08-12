/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad;

import bdcondominio.MensajesEmergentes;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import usuarios.Usuario;
import usuarios.UsuarioDB;


public class ActividadPane extends VBox {
    
    private final TextField titulo;
    private final TextArea descrip;
    private final Button crear;
    private final ScrollPane participaciones;
    private final Button add; 
    private final Text error;
    
    public ActividadPane() {
        titulo = new TextField();
        descrip = new TextArea();
        crear = new Button("Crear y notificar");
        add = new Button("+");
        participaciones = new ScrollPane();
        error = new Text();
        error.setFill(Color.RED); 
        crear();
        participacion();
    }
    
    private void crear() {
        super.setSpacing(15);
        super.setPadding(new Insets(10, 10, 10, 10));
        
        Text text = new Text("Titulo de la actividad");
        text.setFont(Font.font(16));
        HBox box = new HBox(10, text, titulo, new Rectangle(20, 0, Color.TRANSPARENT), crear);
        Text des = new Text("Descripcion");
        des.setFont(Font.font(16));
        descrip.setMaxWidth(450);
        descrip.setMaxHeight(70);
        super.getChildren().addAll(box, new VBox(5, des, descrip), error);
    }
    
    private void participacion() {
        Text t = new Text("Participantes");
        t.setFont(Font.font(16));
        super.getChildren().addAll(t, participaciones);
        participaciones.setHbarPolicy(ScrollBarPolicy.NEVER);
        participaciones.setVbarPolicy(ScrollBarPolicy.AS_NEEDED); 
        VBox content = new VBox(10, add);
        participaciones.setContent(content);
        participaciones.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))); 
        add.setOnAction(e-> {
            error.setText("");
            ComboBox<Usuario> usuarios = new ComboBox<>();
            try {
                usuarios.getItems().addAll(UsuarioDB.getListaUsuarios());
            } catch (SQLException ex) {
                Logger.getLogger(ActividadPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            TextField tarea = new TextField();
            DatePicker date = new DatePicker();
            Button btn = new Button("X");
            HBox part = new HBox(10, new Label("Fecha"), date, new Label("Usuario"), 
                    usuarios, new Label("Tarea"), tarea, btn);
            part.setAlignment(Pos.CENTER); 
            btn.setOnAction(ev -> content.getChildren().remove(part)); 
            content.getChildren().add(content.getChildren().size() - 1, part); 
        }); 
        
        evtCrear(content);
    }

    private void evtCrear(VBox content) {
        crear.setOnAction(e -> {
            error.setText("");
            String titu = titulo.getText();
            String des = descrip.getText();
            if(titu.isEmpty() || des.isEmpty())
                error.setText("Por favor llene todos los campos");
            else {
                Actividad actividad = new Actividad(titu, des);
                List<Participacion> p = new LinkedList<>();
                if(content.getChildren().size() > 1){
                    for(Node node: content.getChildren()) {
                        if(node instanceof HBox) {
                            String tarea = "";
                            LocalDate date = null;
                            Usuario user = null;
                            for(Node n: ((HBox)node).getChildren()) {
                                if(n instanceof TextField)
                                    tarea = ((TextField)n).getText();
                                else if(n instanceof ComboBox)
                                    user = ((ComboBox<Usuario>)n).getValue();
                                else if(n instanceof DatePicker)
                                    date = ((DatePicker)n).getValue();
                            }
                            if(!tarea.isEmpty() && date != null && user != null) 
                                p.add(new Participacion(actividad, user, date, tarea));
                        }
                       
                    }
                    if(content.getChildren().size()-1 != p.size())
                        error.setText("Por favor llene todos los campos de las pariticipaciones");
                    else {
                        if(MensajesEmergentes.cofirmAccion("¿Desea guardar esta acitividad?").get() == ButtonType.OK){
                            try{
                                int id = ActividadDB.crear(actividad);
                                actividad.setId(id);
                                for(Participacion pp: p)
                                    ParticipacionDB.crear(pp); 
                            }catch(SQLException ex) {
                                MensajesEmergentes.errorAction();
                                Logger.getLogger(ActividadPane.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            content.getChildren().removeIf((Node n) -> !(n instanceof Button));
                            clear();
                        }
                    }
                }else {
                    if(MensajesEmergentes.cofirmAccion("¿Desea guardar esta acitividad sin participaciones?").get() 
                            == ButtonType.OK){
                        try{
                            ActividadDB.crear(actividad);     
                        }catch(SQLException ex) {
                            MensajesEmergentes.errorAction();
                            Logger.getLogger(ActividadPane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        clear();
                    }
                }
            }
        }); 
    }
    
    private void clear() {
        titulo.setText("");
        descrip.setText(""); 
    }
}
