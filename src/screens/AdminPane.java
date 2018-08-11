/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import CalendarControl.CalendarView;
import apartamento.ApartamentoPane;
import bdcondominio.BdCondominio;
import usuarios.Usuario;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.DatePicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menudrawing.ItemView;
import menudrawing.MenuDrawing;
import pago.PagoPane;
import usuarios.EditDeleteUserPane;
import usuarios.RegistrarUsuarioPane;

/**
 *
 * 
 */
public class AdminPane {
    private final BorderPane root;
    private final MenuDrawing menu;
    private CalendarView calendar;
    private StackPane home;
    private StackPane logOut;
    private final Stage stage;
    private final Usuario user;
    
    
    public AdminPane(Stage stage, Usuario user) {
        this.stage = stage;
        this.user = user;
        root = new BorderPane();
        menu = new MenuDrawing(
                new ItemView("Registrar usuario", 
                        new Image(AdminPane.class.getResourceAsStream("newUser.png"), 30, 30, true, true)),
                new ItemView("Consulta de usuarios", 
                        new Image(AdminPane.class.getResourceAsStream("selectUser.png"), 30, 30, true, true)),
                new ItemView("Registros de pagos", 
                        new Image(AdminPane.class.getResourceAsStream("pay.png"), 30, 30, true, true)),
                new ItemView("Crear nueva actividad", 
                        new Image(AdminPane.class.getResourceAsStream("tarea.png"), 30, 30, true, true)),
                new ItemView("Elecciones", 
                        new Image(AdminPane.class.getResourceAsStream("box.png"), 30, 30, true, true)), 
                new ItemView("Registrar Apartamento", new Image(AdminPane.class.getResourceAsStream("box.png"), 30, 30, true, true)));
        
        root.setRight(new VBox(new Rectangle(10, 5, Color.TRANSPARENT), menu)); 
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        actionMenu();
        topPane();
        leftPane();
    }

    private void actionMenu() {
        menu.setOnAction(e-> {
            switch(menu.getItemSelected()) {
                case 0:
                    stage.setTitle("Admin - Registrar"); 
                    root.setCenter(new RegistrarUsuarioPane()); 
                    break;
                case 1:  
                    stage.setTitle("Admin - Consultar"); 
                    root.setCenter(new EditDeleteUserPane()); 
                    break;
                case 2:
                    stage.setTitle("Admin - Pagos"); 
                    root.setCenter(new PagoPane(user, true)); 
                    break;
                case 3:
                    root.setCenter(new VBox(new Text("Cuarto")));
                    System.out.println("Admin - Actividades");
                    stage.setTitle("Admin - Actividades"); 
                    break;
                case 4:
                    root.setCenter(new VBox(new Text("Quinto")));
                    System.out.println("Quinto");
                    stage.setTitle("Admin - Elecciones");
                    break;
                case 5:
                    stage.setTitle("Admin - Apartamentos");
                    root.setCenter(new ApartamentoPane().getContenido());
                    break;
                default:
                    break;
            }
        }); 
    }
    
    public Pane getPane(){
        return root;
    }

    private void topPane() {
        StackPane sp = new StackPane();
        Rectangle rectangle = new Rectangle(Const.MAX_WIDTH, Const.MAX_HEIGHT * 0.175);
        rectangle.setFill(Color.rgb(224, 224, 224));
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10); 
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(-5);
        shadow.setOffsetY(-5);
        shadow.setRadius(20); 
        rectangle.setEffect(shadow); 
        sp.getChildren().add(rectangle);
        root.setTop(sp); 
        home = new StackPane();
        homeButton();
        double size = Const.MAX_HEIGHT * 0.175 * 0.55;
        ImageView iv = new ImageView(new Image(AdminPane.class.getResourceAsStream("user.png"), size, size, true, true));
        HBox box = new HBox(10, iv, home);
        box.setAlignment(Pos.CENTER);
        box.setTranslateX(20); 
        sp.setAlignment(Pos.CENTER_LEFT); 
        logOut = new StackPane();
        buttonLogOut();
        HBox hbox = new HBox(Const.MAX_WIDTH * 0.65, box, logOut); 
        sp.getChildren().add(hbox);           
    }
    
    private void homeButton() {
        Rectangle r = new Rectangle(Const.MAX_WIDTH * 0.18, Const.MAX_HEIGHT * 0.175 * 0.55);
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(-5); 
        shadow.setOffsetY(5);
        shadow.setRadius(5);
        shadow.setColor(Color.rgb(182, 182, 182)); 
        r.setEffect(shadow);  
        r.setFill(Color.rgb(245, 245, 245));
        Text text = new Text("ADMINISTRADOR");
        text.setStyle(Const.FONT_BOLD); 
        Text username = new Text(user.getUser());
        VBox vb = new VBox(5, text, username);
        vb.setAlignment(Pos.CENTER_LEFT); 
        vb.setTranslateX(20); 
        home.setAlignment(Pos.CENTER_LEFT);
        home.getChildren().addAll(r, vb);
        evtHome(r);
    }
    
    private void evtHome(Rectangle r) {
        home.setOnMouseEntered(e-> home.setCursor(Cursor.HAND));
        home.setOnMouseExited(e-> home.setCursor(Cursor.DEFAULT)); 
        home.setOnMousePressed(e-> r.setFill(Color.rgb(230, 230, 230)));
        home.setOnMouseReleased(e-> r.setFill(Color.rgb(245, 245, 245)));
        
        home.setOnMouseClicked(e-> {
            root.setCenter(null); 
            stage.setTitle("Admin - Home"); 
        }); 
    }
    
    private void buttonLogOut() {
        Rectangle r = new Rectangle(Const.MAX_WIDTH * 0.18 * 0.4, Const.MAX_HEIGHT * 0.175 * 0.55 * 0.6);
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(-3); 
        shadow.setOffsetY(3);
        shadow.setRadius(5);
        shadow.setColor(Color.rgb(169, 182, 183)); 
        r.setEffect(shadow);  
        r.setFill(Color.rgb(34, 184, 204));
        Text text = new Text("LogOut");
        text.setFont(Font.font(text.getFont().getFamily(), FontWeight.SEMI_BOLD, 14));
        ImageView iv = new ImageView(new Image(AdminPane.class.getResourceAsStream("logout.png"), 15, 15, true, true));
        HBox hbox = new HBox(2, iv, text);
        hbox.setAlignment(Pos.CENTER); 
        logOut.getChildren().addAll(r, hbox);
        logOut.setAlignment(Pos.CENTER); 
        evtLogOut(r);
    }
    
    private void evtLogOut(Rectangle r) {
        logOut.setOnMouseEntered(e-> logOut.setCursor(Cursor.HAND));
        logOut.setOnMouseExited(e-> logOut.setCursor(Cursor.DEFAULT)); 
        logOut.setOnMousePressed(e-> r.setFill(Color.rgb(22, 172, 192)));
        logOut.setOnMouseReleased(e-> r.setFill(Color.rgb(34, 184, 204)));
        
        logOut.setOnMouseClicked(e-> {
            System.out.println("LogOut");
            stage.close();
            BdCondominio.show();
        }); 
    }
    
    private void leftPane() {
        calendar = new CalendarView(new DatePicker(LocalDate.now()));
        root.setLeft(new HBox(new Rectangle(10, 5, Color.TRANSPARENT), new VBox(new Rectangle(10, 5, Color.TRANSPARENT),calendar))); 
    }

}
