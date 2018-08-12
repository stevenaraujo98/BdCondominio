/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarControl;

import java.util.HashMap;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * 
 */
public class DayView extends StackPane{
   private final Text day;
   private final Circle select;
   private final double size = 30;
   private final Tooltip tool;
   private boolean selected;
   private final HashMap<String, Color> eventos;
   
    public DayView(int day) {
        this.day = new Text(day+"");
        Rectangle back = new Rectangle(size, size);
        select = new Circle(size/2.8);
        back.setFill(Color.FLORALWHITE);
        select.setFill(Color.FLORALWHITE);
        super.getChildren().addAll(back, select, this.day);
        super.setAlignment(Pos.CENTER); 
        tool = new Tooltip("Sin evento");
        eventos = new HashMap<>();
        Tooltip.install(this, tool);
        setEvent();
    }
    
    private void setEvent() {
        this.setOnMouseEntered(e -> {if(!selected) select.setFill(Color.DARKGRAY);}); 
        this.setOnMouseExited(e -> {if(!selected) select.setFill(Color.FLORALWHITE);}); 
    }
    
    public void select(Color color) {
        selected = true;
        select.setFill(color); 
    }
    
    public void unselect() {
        selected = false;
        select.setFill(Color.TRANSPARENT); 
    }
    
    public void addEvent(String event){
        eventos.put(event, (Color)select.getFill());
        tool.setText(event); 
    }
    
    public HashMap<String, Color> getEvents() {
        return eventos;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public int getDay() {
        return Integer.parseInt(day.getText());
    }
}
