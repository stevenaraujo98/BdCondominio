/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menudrawing;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Steven Araujo
 * @author Kenny Camba
 * @author Nathaly Sanchez
 */
public class MenuDrawing extends VBox {
    
    private ItemView[] items;
    private double widht;
    private final double SIZE_ITEM;
    private final double space = 20;
    private final ImageView drawButton;
    private ItemView itemSelected;
    private boolean close;
    Rectangle drawContent;
    
    public MenuDrawing(ItemView... items) {
        this.items = items;
        widht = 0;
        double size = items[0].getIcon().getWidth();
        SIZE_ITEM = size;
        close = false;
        drawButton = new ImageView(
                new Image(MenuDrawing.class.getResourceAsStream("drawLine.png"), size, size, true, true));
        adapterMenu();
        adapterDrawing();
    }
    
    private void adapterDrawing() {
        Rectangle r = new Rectangle(drawButton.getImage().getWidth(), drawButton.getImage().getHeight());
        r.setFill(Color.rgb(242, 242, 242));
        StackPane drawing = new StackPane(r, drawButton);
        
        drawContent = new Rectangle(this.widht + space*2 + SIZE_ITEM, SIZE_ITEM);
        drawContent.setFill(Color.rgb(242, 242, 242));
        drawing.setAlignment(Pos.CENTER_RIGHT);
        StackPane sp = new StackPane(drawContent, drawing);
        drawButton.setOnMouseEntered(e-> {
            drawButton.setCursor(Cursor.HAND);
            r.setCursor(Cursor.HAND);
            r.setFill(Color.rgb(145, 220, 230)); 
        }); 
        
        drawButton.setOnMouseExited(e -> {
            drawButton.setCursor(Cursor.DEFAULT);
            r.setFill(Color.rgb(242, 242, 242));
            r.setCursor(Cursor.DEFAULT);
        }); 
        this.getChildren().add(0, sp);
        drawEvent();
    }
    
    private void adapterMenu() {
        double max = Double.MIN_VALUE;
        for(ItemView iv: items){
            this.getChildren().add(iv);
            double w = iv.getItem().getBoundsInLocal().getWidth();
            if(w > max)
                max = w;
        }
        
        for(ItemView iv: items) {
            double w = iv.getItem().getBoundsInLocal().getWidth();
            double spacing = (max - w) + space; 
            iv.setSpacing(spacing);
            iv.adapterObject(max + space*2 + SIZE_ITEM, SIZE_ITEM);
            addEventSelected(iv);
        }
        
        this.widht = max;
    }
    
    public void addItemView(ItemView item) {
        ItemView[] addItems = new ItemView[this.items.length + 1];
        System.arraycopy(items, 0, addItems, 0, items.length);
        items = addItems;
        items[items.length - 1] = item;
        this.getChildren().removeIf(n -> (n instanceof ItemView)); 
        adapterMenu();
    }
    
    private void addEventSelected(ItemView item) {
        item.setOnMouseEntered(e-> {
            item.setCursor(Cursor.HAND);
            item.boxImage.setFill(Color.rgb(145, 220, 230));
            item.getContent().setFill(Color.rgb(145, 220, 230)); 
        }); 
        item.setOnMouseExited(e -> {
            item.setCursor(Cursor.DEFAULT);
            item.boxImage.setFill(Color.rgb(242, 242, 242));
            item.getContent().setFill(Color.rgb(242, 242, 242));
        }); 
        
        item.setOnMousePressed(e-> {
            item.setCursor(Cursor.HAND);
            item.boxImage.setFill(Color.rgb(145, 220, 230));
            item.getContent().setFill(Color.rgb(145, 220, 230)); 
            itemSelected = item;
        }); 
        
        item.setOnMouseReleased(e-> {
            item.setCursor(Cursor.DEFAULT);
            item.boxImage.setFill(Color.rgb(242, 242, 242));
            item.getContent().setFill(Color.rgb(242, 242, 242));
        });
        
    }
    
    private void drawEvent() {
        EventHandler<MouseEvent> handler = (MouseEvent event) -> {
            if(!close)
                close();
            else
                open();
        };
        
        drawButton.setOnMouseClicked(handler); 
    }
    
    public int getItemSelected() {
        int index = this.getChildren().indexOf(itemSelected) - 1;
        itemSelected = null;
        return index;
    }
    
    public void open() {
        close = false;
        openMenu();
    }
    
    public void close() {
        close = true;
        closeMenu();
    }
    
    private void openMenu() {
        drawContent.setVisible(true); 
        for(ItemView iv: items){
            iv.getContent().setVisible(true);
            iv.getItem().setVisible(true);
        }
    }
    
    private void closeMenu() {
        drawContent.setVisible(false);
        for(ItemView iv: items){
            iv.getContent().setVisible(false);
            iv.getItem().setVisible(false);
        }
    }
    
    public void setOnAction(EventHandler<MouseEvent> event){
        this.setOnMouseClicked(event); 
    }
    
    public double getMenuWidth() {
        return this.widht + space*2 + SIZE_ITEM;
    }
    
    public boolean isClose() {
        return close;
    }
    
}
