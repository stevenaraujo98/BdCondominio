/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarControl;

import java.time.DayOfWeek;
import java.time.Month;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * 
 */
public class MonthView extends GridPane {
    
    private final DayView[] days;
    private final DayOfWeek startDay;
    private final double size = 30;
    private Month mes;
    
    
    public MonthView(Month mes, DayOfWeek startDay, DayView... days) {
        this.days = days;
        this.startDay = startDay;
        this.mes = mes;
        adapterMonth();
    }

    private void adapterMonth() {
        for(int i=0; i<7; i++){
            String txt = DayOfWeek.of(i + 1).toString();
            String f = txt.toLowerCase().substring(1, 2);
            txt = txt.charAt(0) + f;
            Text ds = new Text(txt);
            Rectangle r = new Rectangle(size, size - 7);
            r.setFill(Color.rgb(238, 238, 238));
            StackPane sp = new StackPane(r, ds);
            sp.setAlignment(Pos.CENTER); 
            super.add(sp, i, 0); 
        }
        
        int ini = startDay.getValue() - 1;
        int index = 0;
        for(int i=ini; i<7; i++) 
            super.add(days[index++], i, 1);  
     
        for(int i=2; i<7; i++){
            int j = 0;
            while(34 - index > 34 - days.length && j < 7){
                super.add(days[index++], j, i);
                j++;
            }
        }
    }
    
    public Month getMonth() {
        return mes;
    }
    
    public void selectDay(int dia, String event, Color color) {
        days[dia-1].select(color);
        days[dia-1].setEvent(event); 
    }
}
