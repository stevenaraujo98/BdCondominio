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


public class EditDeleteActividadPane extends VBox{
    
    private final ComboBox<Actividad> actividades;
    private final ScrollPane participaciones;
    private final TextField titulo;
    private final TextArea descrip;
    private final Button editar;
    private final Button borrar;
    private final Button buscar;
    private final Text error;
    private final DatePicker fechaInicio;
    private final DatePicker fechaFin;
    private final VBox content;
    private final VBox actividadBox;
    private final VBox content2;
    private final Button add;
    
    public EditDeleteActividadPane() {
        actividades = new ComboBox<>();
        participaciones = new ScrollPane();
        titulo = new TextField();
        descrip = new TextArea();
        editar = new Button("Editar");
        borrar = new Button("Borrar");
        buscar = new Button("Buscar");
        error = new Text();
        error.setFill(Color.RED); 
        fechaInicio = new DatePicker();
        fechaFin = new DatePicker();
        content = new VBox();
        actividadBox = new VBox();
        content2 = new VBox();
        content2.setSpacing(5); 
        add = new Button("+");
        content2.getChildren().add(add);
        contenido();
        evtActividades();
    }
    
    private void contenido() {
        participaciones.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        participaciones.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        participaciones.setContent(content2);
        participaciones.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10); 
        actividadBox.setAlignment(Pos.CENTER_LEFT);
        actividadBox.setSpacing(10); 
        super.setPadding(new Insets(10, 10, 10, 10));
        super.setSpacing(10);
        HBox box = new HBox(10, new Label("Fecha de inicio"), fechaInicio, 
                            new Label("Fecha de fin"), fechaFin);
        box.setAlignment(Pos.CENTER_LEFT); 
        super.getChildren().addAll(box, buscar, content, actividadBox, error);
        evtBuscar();
    }
    
    private void evtBuscar() {
        buscar.setOnAction(e-> {
            error.setText(""); 
            LocalDate inicio = fechaInicio.getValue();
            LocalDate fin = fechaFin.getValue();
            if(inicio == null || fin == null)
                error.setText("Por favor seleccione un rango de fechas"); 
            else if(inicio.compareTo(fin) > 0)
                error.setText("Ingrese un rango de fechas válido");
            else {
                error.setText(""); 
                content.getChildren().clear();
                actividades.getItems().clear();
                try {
                    actividades.getItems().addAll(ActividadDB.listaActividades(inicio, fin));
                    content.getChildren().add(actividades);
                } catch (SQLException ex) {
                    Logger.getLogger(EditDeleteActividadPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
    }
    
    private void evtActividades() {
        actividades.setOnAction(e-> {
            error.setText(""); 
            actividadBox.getChildren().clear();
            disableViews(true);
            Actividad a = actividades.getValue();
            if(a != null){
                titulo.setText(a.getTitulo());
                descrip.setText(a.getDescrpcion()); 
                Text text = new Text("Titulo de la actividad");
                text.setFont(Font.font(16));
                HBox box = new HBox(10, text, titulo, new Rectangle(20, 0, Color.TRANSPARENT), editar, borrar);
                Text des = new Text("Descripcion");
                des.setFont(Font.font(16));
                descrip.setMaxWidth(450);
                descrip.setMaxHeight(70);
                Text t = new Text("Participantes");
                t.setFont(Font.font(16));
                List<Participacion> p = new LinkedList<>();
                try {
                    p = ParticipacionDB.read(a);
                } catch (SQLException ex) {
                    Logger.getLogger(EditDeleteActividadPane.class.getName()).log(Level.SEVERE, null, ex);
                }
                participacion(p);
                actividadBox.getChildren().addAll(box, new VBox(5, des, descrip), t, participaciones);
                evtEditar(a);
            }
        }); 
    }
    
    private void participacion(List<Participacion> parti) {
        content2.getChildren().removeIf((Node n)-> !(n instanceof Button));
        for(Participacion p: parti) {   
            ComboBox<Usuario> usuarios = new ComboBox<>();
            try {
                usuarios.getItems().addAll(UsuarioDB.getListaUsuarios());
            } catch (SQLException ex) {
                Logger.getLogger(ActividadPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            TextField tarea = new TextField();
            DatePicker date = new DatePicker();
            tarea.setText(p.getTarea());
            date.setValue(p.getFecha());
            usuarios.setValue(p.getUsuaio()); 
            Button btn = new Button("X");
            HBox part = new HBox(10, new Text(p.getId()+""), new Label("Fecha"), date, new Label("Usuario"), 
                    usuarios, new Label("Tarea"), tarea, btn);
            part.setAlignment(Pos.CENTER); 
            content2.getChildren().add(content2.getChildren().size() - 1, part); 
            btn.setOnAction(ev -> {
                if(MensajesEmergentes.cofirmAccion("¿Desea eliminar esto?").get() == ButtonType.OK){
                    content2.getChildren().remove(part);
                    try { 
                        ParticipacionDB.delete(p.getId());
                    } catch (SQLException ex) {
                        MensajesEmergentes.errorAction();
                        Logger.getLogger(EditDeleteActividadPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        disableViews(true);
        evtAdd();
    }
    
    private void evtAdd() {
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
            btn.setOnAction(ev -> content2.getChildren().remove(part)); 
            content2.getChildren().add(content2.getChildren().size() - 1, part); 
        }); 
    }
    private void evtEditar(Actividad a) {
        editar.setOnAction(e-> {
            if(editar.getText().equals("Editar")){
                editar.setText("Guardar");
                borrar.setText("Cancelar");
                disableViews(false);
                
            }else if(editar.getText().equals("Guardar")){
                guardar(a);
            }  
        }); 
    }
    
    private void guardar(Actividad a) {
        error.setText("");
        String titu = titulo.getText();
        String des = descrip.getText();
        if(titu.isEmpty() || des.isEmpty())
            error.setText("Por favor llene todos los campos");
        else {
            a.setTitulo(titu);
            a.setDescrpcion(des); 
            List<Participacion> p = new LinkedList<>();
            List<Participacion> nuevos = new LinkedList<>();
            if(content2.getChildren().size() > 1){
                for(Node node: content2.getChildren()) {
                    if(node instanceof HBox) {
                        String tarea = "";
                        LocalDate date = null;
                        Usuario user = null;
                        int id = 0;
                        for(Node n: ((HBox)node).getChildren()) {
                            if(n instanceof TextField)
                                tarea = ((TextField)n).getText();
                            else if(n instanceof ComboBox)
                                user = ((ComboBox<Usuario>)n).getValue();
                            else if(n instanceof DatePicker)
                                date = ((DatePicker)n).getValue();
                            else if(n instanceof Text)
                                id = Integer.parseInt(((Text)n).getText());
                        }
                        if(!tarea.isEmpty() && date != null && user != null && id != 0) 
                            p.add(new Participacion(id, a, user, date, tarea));
                        else if(!tarea.isEmpty() && date != null && user != null) 
                            nuevos.add(new Participacion(a, user, date, tarea));
                    }

                }
                if(content2.getChildren().size()-1 != p.size() + nuevos.size())
                    error.setText("Por favor llene todos los campos de las pariticipaciones");
                else {
                    if(MensajesEmergentes.cofirmAccion("¿Desea guardar los cambios realizados?").get() == ButtonType.OK){
                        try{
                            ActividadDB.update(a); 
                            for(Participacion pp: p){
                                ParticipacionDB.update(pp);
                            } 
                            for(Participacion pa: nuevos)
                                ParticipacionDB.crear(pa); 
                        }catch(SQLException ex) {
                            MensajesEmergentes.errorAction();
                            Logger.getLogger(ActividadPane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        actividadBox.getChildren().clear();
                        editar.setText("Editar");
                        borrar.setText("Borrar");
                        disableViews(true);
                    }
                }
            }else {
                if(MensajesEmergentes.cofirmAccion("¿Desea guardar esta acitividad sin participaciones?").get() 
                        == ButtonType.OK){
                    try{
                        ActividadDB.update(a); 
                    }catch(SQLException ex) {
                        MensajesEmergentes.errorAction();
                        Logger.getLogger(ActividadPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    actividadBox.getChildren().clear();
                    editar.setText("Editar");
                    borrar.setText("Borrar");
                    disableViews(true);
                }
            }
        }
    }
    
    private void disableViews(boolean b) {
        titulo.setDisable(b); 
        descrip.setDisable(b);
        add.setDisable(b); 
        for(Node node: content2.getChildren()) {
            if(node instanceof HBox) {
                for(Node n: ((HBox)node).getChildren()) {
                    if(n instanceof TextField)
                        ((TextField)n).setDisable(b);
                    else if(n instanceof ComboBox)
                        ((ComboBox<Usuario>)n).setDisable(b) ;
                    else if(n instanceof DatePicker)
                        ((DatePicker)n).setDisable(b);
                }
            }
        }
    }
    
}
