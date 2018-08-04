/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menudrawing;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Steven Araujo
 * @author Kenny Camba
 * @author Nathaly Sanchez
 */
public class ItemView extends StackPane{
    
    private final Text item;
    private final ImageView icon;
    private final Rectangle content;
    private final HBox box; 
    private final double spacing;
    final Rectangle boxImage;
    
    public ItemView(String item, Image icon) {
        this.item = new Text(item);
        this.icon = new ImageView(icon);
        spacing = 20;
        this.item.setFont(Font.font(14)); 
        content = new Rectangle(
                this.item.getBoundsInLocal().getWidth() + icon.getWidth() + spacing*2, icon.getHeight());
        box = new HBox();
        boxImage = new Rectangle(icon.getWidth(), icon.getHeight());
        view();
    }
    
    private void view() {
        boxImage.setFill(Color.rgb(242, 242, 242));
        StackPane sp = new StackPane(boxImage, icon);
        box.getChildren().addAll(item, sp);
        box.setSpacing(spacing); 
        box.setAlignment(Pos.CENTER_LEFT); 
        this.getChildren().addAll(content, box);
        this.setAlignment(Pos.CENTER_LEFT); 
        content.setFill(Color.rgb(242, 242, 242));
        box.setPadding(new Insets(0, 0, 0, 20)); 
        Tooltip.install(sp, new Tooltip(item.getText()));
    }
    
    void setSpacing(double spacing) {
        box.setSpacing(spacing); 
    }
    
    public void setFill(Color color) {
        this.content.setFill(color); 
    }
    
    public void setIcon(Image image) {
        this.icon.setImage(image); 
    }
    
    public Image getIcon() {
        return this.icon.getImage();
    }
    
    Rectangle getContent() {
        return this.content;
    }
     
    void adapterObject(double width, double height) {
        content.setWidth(width);
        content.setHeight(height);
    }
    
    Text getItem() {
        return item;
    }
}
