/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pago;

import apartamento.Apartamento;
import apartamento.ApartamentoDB;
import bdcondominio.MensajesEmergentes;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import usuarios.Usuario;
import usuarios.UsuarioDB;

/**
 *
 *
 */
public class PagoPane extends BorderPane{
    
    private final boolean adminPermission;
    private final RadioButton registrar;
    private final RadioButton consultar;
    private final ComboBox<Factura> tipos;
    private final ComboBox<Pago> pagos;
    private final ComboBox<Apartamento> apartamentos; 
    private final TextField monto;
    private final TextField id;
    private final TextField name;
    private final DatePicker date;
    private final DatePicker date2;
    private final Button action;
    private final Button edit;
    private final Button search;
    private final Button search2;
    private final Button delete;
    private final Text status;
    private final Usuario usuario;
    
    public PagoPane(Usuario usuario, boolean adminPermission) {
        this.usuario = usuario;
        this.adminPermission = adminPermission;
        registrar = new RadioButton("Registrar");
        consultar = new RadioButton("Consultar");
        tipos = new ComboBox<>();
        pagos = new ComboBox<>();
        apartamentos = new ComboBox<>();
        monto = new TextField();
        id = new TextField();
        name = new TextField();
        date = new DatePicker(LocalDate.now());
        date2 = new DatePicker();
        action = new Button();
        edit = new Button("Editar");
        search = new Button("Buscar");
        search2 = new Button("Buscar");
        delete = new Button("Eliminar");
        status = new Text();
        status.setFill(Color.RED); 
        name.setDisable(true);
        formatter();
        radioGroup();
    }
    
    private void radioGroup() {
        ToggleGroup group = new ToggleGroup();
        registrar.setToggleGroup(group);
        registrar.setSelected(true);
        consultar.setToggleGroup(group);
        VBox groupBox = new VBox(10, registrar, consultar);
        super.setTop(groupBox);
        evtRadioGroup(group);
        super.setCenter(registro()); 
    }
            
    private void formatter() {
        super.setPadding(new Insets(5, 0, 0, 15));
        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (decimalPattern.matcher(change.getControlNewText()).matches())
                return change;
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        monto.setTextFormatter(textFormatter); 
        
        StringConverter converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if(object != null)
                    return object.format(DateTimeFormatter.ISO_LOCAL_DATE);
                return "";
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) 
                    return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE);
                return null;
            }
        };
        date.setConverter(converter); 
        
        UnaryOperator<TextFormatter.Change> filter1 = change -> {
            if (change.getText().matches("[0-9]*"))
                return change;
            return null;
        };
        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter1);
        id.setTextFormatter(textFormatter1); 
    }
    
    //RadioButton de registro
    private Pane registro() {
        clear();
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10); 
        grid.addRow(0, formatText("Id del usuario", 14), id);
        grid.add(search, 7, 0);
        grid.add(status, 0, 1, 3, 1);
        GridPane content = new GridPane();
        content.setVgap(10);
        content.setHgap(10); 
        VBox box = new VBox(10, grid, content);
        search.setText("Buscar");
        if(!adminPermission){
            id.setText(usuario.getId()+"");
            id.setDisable(true); 
            search.setDisable(true);
            loadApartments(content);
        }else {
            id.setDisable(false); 
            search.setDisable(false);
            search.setOnAction(e-> {
                status.setText("");
                if(id.getText().isEmpty())
                    status.setText("Por favor llene el campo de búsqueda");
                else{
                    tipos.getItems().clear();
                    apartamentos.getItems().clear();
                    loadApartments(content);
                }
            }); 
        }
        return box;
    }
    
    private void loadApartments(GridPane content) {
        content.getChildren().clear();
        content.addRow(0, formatText("Usuario", 14), name);
        content.addRow(1, formatText("Apartamentos", 14), apartamentos);
            int idu = Integer.parseInt(id.getText());
            try {    
                Usuario user = UsuarioDB.getById(idu);
                name.setText(user.getName() + " " + user.getLastname()); 
                List<Apartamento> apar = ApartamentoDB.getUserApartments(idu); 
                if(apar != null)
                    apartamentos.getItems().addAll(apar);
            } catch (SQLException ex) {
                System.out.println("Error al cargar apartamentos");
                Logger.getLogger(PagoPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTipos();
            content.addRow(2, formatText("Tipo", 14), tipos);
            content.addRow(3, formatText("Fecha", 14), date);
            content.addRow(4, formatText("Valor", 14), monto);
            content.add(action, 6, 4);
            action.setText("Registrar");
            actionEvt(content, idu);
    }
    
    private void loadTipos() {
        tipos.getItems().clear();
        if(!adminPermission)
                tipos.getItems().addAll(Factura.AGUA, Factura.ELECTRICIDAD, Factura.TELEFONO);
        else
            tipos.getItems().addAll(Factura.values());
    }
    
    private void actionEvt(GridPane content, int idu) {
        action.setOnAction(e-> {
            status.setText(""); 
            String var = monto.getText();
            LocalDate ld = date.getValue();
            Factura f = tipos.getValue();
            Apartamento a = apartamentos.getValue();
            if(var.isEmpty() || ld == null || f == null || a == null)
                status.setText("Por favor llene todos los campos"); 
            else {
                if(MensajesEmergentes.cofirmAccion("Confirmar registro de pago").get() == ButtonType.OK){
                    Pago p = new Pago(Float.parseFloat(var), ld, f, a);
                    try {
                        Usuario admin = UsuarioDB.getCurrentAdmin();
                        PagoBD.create(p, idu, admin.getId(), a.getId());
                        MensajesEmergentes.infoSave();
                    } catch (SQLException ex) {
                        MensajesEmergentes.errorAction();
                        Logger.getLogger(PagoPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    clear();
                    content.getChildren().clear();
                }
            }
        }); 
    }
    
    //RadioButton de consulta
    private Pane consulta() {
        clear();
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.addRow(0, formatText("Id del usuario", 14), id); 
        grid.addRow(1, formatText("Fecha", 14), date);
        grid.add(search2, 7, 1);
        grid.add(status, 0, 2);
        GridPane content = new GridPane();
        content.setVgap(10);
        content.setHgap(10); 
        VBox box = new VBox(10, grid, content);
        search2.setText("Buscar");
        if(!adminPermission){
            id.setText(usuario.getId()+"");
            id.setDisable(true); 
        }else {
            id.setDisable(false); 
        }
        searchEvt(content);
        return box;
    }
    
    private void searchEvt(GridPane content) {
        search2.setOnAction(e-> {
            content.getChildren().clear();
            status.setText("");
            pagos.getItems().clear();
            String ids = id.getText();
            LocalDate ld = date.getValue();
            if(ids.isEmpty() || ld == null)
                status.setText("Llene todos los campos de búsqueda"); 
            else {
                content.addRow(0, formatText("Usuario", 14), name);
                content.addRow(1,formatText("Pagos", 14), pagos); 
                int idu = Integer.parseInt(ids);
                try {
                    Usuario user = UsuarioDB.getById(idu);
                    name.setText(user.getName() + " " + user.getLastname());
                    List<Pago> pg = PagoBD.read(idu, ld);
                    if(pg != null){
                        pagos.getItems().addAll(pg);
                        pagosEvt(idu, content);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PagoPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void pagosEvt(int idu, GridPane content) {
        pagos.setOnAction(ev-> {
            Pago p = pagos.getValue();
            if(p != null) {
                try {
                    disableViews(true);
                    loadTipos();
                    tipos.setValue(p.getFactura());
                    monto.setText(p.getMonto()+"");
                    date2.setValue(p.getFecha()); 
                    List<Apartamento> apar = ApartamentoDB.getUserApartments(idu);
                    if(apar != null){
                        apartamentos.getItems().addAll(apar);
                        apartamentos.setValue(p.getApartamento());
                        content.addRow(2, formatText("Apartamentos", 14), apartamentos);
                        content.addRow(3, formatText("Tipo", 14), tipos);
                        content.addRow(4, formatText("Fecha", 14), date2);
                        content.addRow(5, formatText("Valor", 14), monto);
                        if(adminPermission){
                            content.add(edit, 8, 0);
                            content.add(delete, 8, 1);
                            editEvt(p, idu, content);
                            deleteEvt(content);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PagoPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
    }
    
    private void editEvt(Pago p, int idu, GridPane content) {
        edit.setOnAction(e-> {
            status.setText(""); 
            if(edit.getText().equals("Editar")) {
                edit.setText("Guardar");
                delete.setText("Cancelar"); 
                disableViews(false);
            }else if(edit.getText().equals("Guardar")){
                String m = monto.getText();
                Factura f = tipos.getValue();
                Apartamento a = apartamentos.getValue(); 
                LocalDate ld = date2.getValue();
                if(m.isEmpty() || f == null || a == null || ld == null)
                    status.setText("Por favor llene todos los campos");
                else {
                    if(MensajesEmergentes.cofirmAccion("¿Desea guardar los cambios?").get() == ButtonType.OK) {
                        try {
                            Usuario admin = UsuarioDB.getCurrentAdmin();
                            PagoBD.update(p.getId(), f.getId(), a.getId(), admin.getId(), ld, Float.parseFloat(m), idu);
                            MensajesEmergentes.infoSave();
                        } catch (SQLException ex) {
                            MensajesEmergentes.errorAction();
                            Logger.getLogger(PagoPane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        disableViews(true);
                        edit.setText("Editar");
                        delete.setText("Eliminar");
                        content.getChildren().clear();
                    }
                }
            } 
        });
    }
    
    private void deleteEvt(GridPane content) {
        delete.setOnAction(e-> {
            if(delete.getText().equals("Eliminar")) { 
                if(MensajesEmergentes.cofirmAccion("¿Desea eliminar este pago?").get() == ButtonType.OK) {
                    try {
                        PagoBD.delete(pagos.getValue());
                    } catch (SQLException ex) {
                        MensajesEmergentes.errorAction();
                        Logger.getLogger(PagoPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    disableViews(true);
                    edit.setText("Editar");
                    delete.setText("Eliminar");
                    content.getChildren().clear();
                }
            }else if(delete.getText().equals("Cancelar")) {
                edit.setText("Editar");
                delete.setText("Eliminar"); 
                disableViews(true);
            } 
        }); 
    }
    
    private Text formatText(String text, double size) {
        Text t = new Text(text);
        t.setFont(Font.font(size));
        return t;
    }

    private void evtRadioGroup(ToggleGroup group) {
        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle oldValue, Toggle newValue) -> {
                    Toggle toggle = group.getSelectedToggle();
                    if(toggle != null && toggle == registrar){ 
                        initEstatus();
                        super.setCenter(registro()); 
                    }
                    else if(toggle == consultar){
                        initEstatus();
                        super.setCenter(consulta()); 
                    }
                    
        }); 
    }
    
    private void initEstatus() {
        clear();
        apartamentos.getItems().clear();
        tipos.getItems().clear();
        pagos.getItems().clear();
        edit.setText("Editar");
        search.setText("Buscar");
        delete.setText("Eliminar");
    }
    
    private void clear() {
        status.setText("");
        monto.setText(""); 
        id.setText("");
        name.setText("");
    }
    
    private void disableViews(boolean b) {
        monto.setDisable(b);
        tipos.setDisable(b); 
        apartamentos.setDisable(b); 
        date2.setDisable(b); 
    }
}
