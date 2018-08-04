/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarControl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * 
 */
public class CalendarView extends BorderPane {
    private final Year[] anios;
    private final Button next;
    private final Button back;
    private int posM, posA;
    private final Text text; 
    
    public CalendarView(DatePicker datePicker) {
        next = new Button(">");
        back = new Button("<");
        text = new Text();
        anios = Year.getYears();
        int anio = datePicker.getValue().getYear();
        posM = datePicker.getValue().getMonth().getValue() - 1;
        while(anio != Year.anios[posA])
            posA++;
        anios[posA].months[posM].selectDay(datePicker.getValue().getDayOfMonth(), "Día actual", Color.rgb(221, 221, 221));
        super.setBackground(new Background(new BackgroundFill(Color.FLORALWHITE, CornerRadii.EMPTY, Insets.EMPTY))); 
        topPane();
        centerPane();
        buttonEvent();
    }

    private void topPane() {
        Rectangle r = new Rectangle(30 * 7, 35);
        r.setFill(Color.rgb(204, 204, 204));
        text.setText(anios[posA].months[posM].getMonth().toString() + " " + anios[posA].year); 
        BorderPane bp = new BorderPane();
        bp.setLeft(new HBox(back));
        ((HBox)bp.getLeft()).setAlignment(Pos.CENTER); 
        bp.setRight(new HBox(next));
        ((HBox)bp.getRight()).setAlignment(Pos.CENTER); 
        bp.setCenter(text); 
        StackPane sp = new StackPane(r, bp);
        sp.setAlignment(Pos.CENTER); 
        super.setTop(sp); 
    }

    private void centerPane() {
        MonthView  month = anios[posA].months[posM];
        Rectangle r = new Rectangle(month.getBoundsInLocal().getWidth(), month.getBoundsInLocal().getHeight());
        r.setFill(Color.FLORALWHITE);
        StackPane sp = new StackPane(r, month);
        sp.setAlignment(Pos.CENTER); 
        super.setCenter(sp);
    }

    private void buttonEvent() {
        next.setOnAction(e-> {
            if(posM < 11){
                posM++;
            }else if(posA < 14) {
                posM = 0;
                posA++;
            }
            
            MonthView  month = anios[posA].months[posM];
            text.setText(month.getMonth().toString() + " " + anios[posA].year); 
            Rectangle r = new Rectangle(month.getBoundsInLocal().getWidth(), month.getBoundsInLocal().getHeight());
            r.setFill(Color.FLORALWHITE);
            StackPane sp = new StackPane(r, month);
            sp.setAlignment(Pos.CENTER); 
            super.setCenter(sp);
        }); 
        
        back.setOnAction(e-> {
            if(posM > 0){
                posM--;
            }else if(posA > 0){
                posM = 11;
                posA--;
            }
            MonthView  month = anios[posA].months[posM];
            text.setText(month.getMonth().toString() + " " + anios[posA].year); 
            Rectangle r = new Rectangle(month.getBoundsInLocal().getWidth(), month.getBoundsInLocal().getHeight());
            r.setFill(Color.FLORALWHITE);
            StackPane sp = new StackPane(r, month);
            sp.setAlignment(Pos.CENTER); 
            super.setCenter(sp);
        }); 
    }
    
    static class Year {
        MonthView[] months;
        int year;
        static int[] anios = {2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023,
                           2024, 2025, 2026, 2028, 2029, 2030};

        public Year(MonthView[] months, int year) {
            this.months = months;
            this.year = year;
        }
        
        static boolean isLeapYear(int year) {
            return LocalDate.of(year, 1, 1).isLeapYear();
        }
        
        static Year[] getYears() {
            int num = 4;
            Year[] years = new Year[15];
            for(int i=0; i<15; i++){
                MonthView[] meses = new MonthView[12];
                for(int j=0; j<12; j++) {
                    Month mes = Month.of(j+1);
                    DayView[] days = new DayView[mes.length(isLeapYear(anios[i]))];
                    for(int d=0; d<days.length; d++)
                        days[d] = new DayView(d + 1);
                    meses[j] = new MonthView(mes, DayOfWeek.of(num), days); 
                    int d = days.length - (7 - (num-1));
                    num = d%7 + 1;
                }
                years[i] = new Year(meses, anios[i]);
            }
            return years;
        }
    }
    
    public void addEvent(LocalDate date, String event, Color color) {
        int a = 0;
        while(Year.anios[a] != date.getYear())
            a++;
        anios[a].months[date.getMonth().getValue() - 1].selectDay(date.getDayOfMonth(), event, color);
    }
}
