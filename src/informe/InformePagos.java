/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informe;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pago.PagoBD;
import pago.Reporte;


public class InformePagos {
    
    private final RadioButton anual;
    private final RadioButton mensual;
    private final RadioButton rango;
    private final Button cargar;
    private final CheckBox total;
    private final CheckBox electricidad;
    private final CheckBox alicuotas;
    private final CheckBox agua;
    private final CheckBox telefono;
    private final CheckBox multas;
    private final BorderPane root;
    private final DatePicker inicio;
    private final DatePicker fin;
    private final TableView<Reporte> tabla;
    private final TableColumn<Reporte, String> nombre;
    private final TableColumn<Reporte, String> apellido;
    private final TableColumn<Reporte, Float> monto;
    private final TableColumn<Reporte, Float> fe;
    private final TableColumn<Reporte, Float> fal;
    private final TableColumn<Reporte, Float> fa;
    private final TableColumn<Reporte, Float> ft;
    private final TableColumn<Reporte, Float> fm;
    
    public InformePagos() {
        anual = new RadioButton("Anual");
        mensual = new RadioButton("Mensual");
        rango = new RadioButton("Rango");
        cargar = new Button("Cargar");
        total = new CheckBox("Monto total");
        electricidad = new CheckBox("Facturas de electricidad");
        alicuotas = new CheckBox("Facturas de alícuotas");
        agua = new CheckBox("Facturas de agua");
        telefono = new CheckBox("Facturas de telefono");
        multas = new CheckBox("Facturas de multas");
        tabla = new TableView();
        root = new BorderPane();
        inicio = new DatePicker();
        fin = new DatePicker();
        nombre = new TableColumn<>("Nombre");
        apellido = new TableColumn<>("Apellido");
        monto = new TableColumn<>("Total");
        fe = new TableColumn<>("Electricidad");
        fal = new TableColumn<>("Alícuotas");
        fa = new TableColumn<>("Agua");
        fm = new TableColumn<>("Multa");
        ft = new TableColumn<>("Telefono");
        top();
        crearTabla();
        evtCheck();
        evtRadioButtons();
        evtcargar();
    }
    
    private void top() {
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5); 
        ToggleGroup group = new ToggleGroup();
        anual.setToggleGroup(group);
        mensual.setToggleGroup(group);
        rango.setToggleGroup(group);
        rango.setSelected(true);
        grid.addColumn(0, rango, mensual, anual);
        grid.add(inicio, 1, 0);
        grid.add(fin, 2, 0);
        grid.add(cargar, 3, 0);
        grid.addColumn(10, agua, telefono, electricidad);
        grid.addColumn(11, alicuotas, multas, total);
        agua.setSelected(true);
        telefono.setSelected(true);
        electricidad.setSelected(true);
        alicuotas.setSelected(true);
        multas.setSelected(true);
        total.setSelected(true); 
        root.setTop(grid); 
    }
    
    public Pane getRoot() {
        return root;
    }

    private void crearTabla() {
        tabla.getColumns().addAll(nombre, apellido, fe, fa, ft, fal, fm, monto);
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre")); 
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido")); 
        monto.setCellValueFactory(new PropertyValueFactory<>("monto")); 
        fe.setCellValueFactory(new PropertyValueFactory<>("totalElectricidad")); 
        fa.setCellValueFactory(new PropertyValueFactory<>("totalAgua")); 
        ft.setCellValueFactory(new PropertyValueFactory<>("totalTelefono")); 
        fal.setCellValueFactory(new PropertyValueFactory<>("totalAlucuota")); 
        fm.setCellValueFactory(new PropertyValueFactory<>("totalMulta")); 
        root.setCenter(tabla); 
    }

    private void evtCheck() {
        total.setOnAction(e-> {
            if(total.isSelected()) {
                tabla.getColumns().add(monto);
            }else{
                tabla.getColumns().remove(monto);
            }
        }); 
        
        multas.setOnAction(e-> {
            if(multas.isSelected()) {
                tabla.getColumns().add(fm);    
            }else{
                tabla.getColumns().remove(fm);
            }
        }); 
        
        electricidad.setOnAction(e-> {
            if(electricidad.isSelected()) {
                tabla.getColumns().add(fe);    
            }else{
                tabla.getColumns().remove(fe);
            }
        }); 
        
        alicuotas.setOnAction(e-> {
            if(alicuotas.isSelected()) {
                tabla.getColumns().add(fal);
            }else{
                tabla.getColumns().remove(fal);
            }
        }); 
        
        agua.setOnAction(e-> {
            if(agua.isSelected()) {
                tabla.getColumns().add(fa);    
            }else{
                tabla.getColumns().remove(fa);
            }
        }); 
        
        telefono.setOnAction(e-> {
            if(telefono.isSelected()) {
                tabla.getColumns().add(ft);    
            }else{
                tabla.getColumns().remove(ft);
            }
        }); 
    }
    
    private void evtRadioButtons() {
        anual.setOnAction(e -> {
            if(anual.isSelected()) {
                cargar.setDisable(true);
                inicio.setDisable(true);
                fin.setDisable(true);
                tabla.getItems().clear();
                try {
                    List<Reporte> r = PagoBD.hacerReporte(LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 1),
                                    LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31));
                    tabla.getItems().addAll(r);
                    System.out.println(r);
                } catch (SQLException ex) {
                    Logger.getLogger(InformePagos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        mensual.setOnAction(e -> {
            if(mensual.isSelected()) {
                cargar.setDisable(true);
                inicio.setDisable(true);
                fin.setDisable(true);
                tabla.getItems().clear();
                try {
                    List<Reporte> r = PagoBD.hacerReporte(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1),
                                    LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 31));
                    tabla.getItems().addAll(r);
                    System.out.println(r);
                } catch (SQLException ex) {
                    Logger.getLogger(InformePagos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        rango.setOnAction(e -> {
            if(rango.isSelected()) {
                cargar.setDisable(false);
                inicio.setDisable(false);
                fin.setDisable(false);
            }
        }); 
    }
    
    public void evtcargar(){
        cargar.setOnAction(e -> {
            if(inicio.getValue() != null && fin.getValue() != null){
               tabla.getItems().clear();
                try {
                    List<Reporte> r = PagoBD.hacerReporte(inicio.getValue(),fin.getValue());
                    tabla.getItems().addAll(r);
                    System.out.println(r);
                } catch (SQLException ex) {
                    Logger.getLogger(InformePagos.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }    
}
