/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import bdcondominio.MensajesEmergentes;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * Clase para crear un nuevo usuario
 */
public class RegistrarUsuarioPane extends VBox {
    
    private final TextField name;
    private final TextField lastname;
    private final TextField phone;
    private final TextField email;
    private final TextField user;
    private final PasswordField password;
    private final Button save;
    private final Text status;
    
    public RegistrarUsuarioPane() {
        name = new TextField();
        lastname = new TextField();
        phone = new TextField();
        email = new TextField();
        user = new TextField();
        password = new PasswordField();
        save = new Button("Guardar");
        status = new Text();
        editPane();
        crearFormulario();
        evtSave();
    }
    
    private void editPane() {
        super.setPadding(new Insets(5, 0, 0, 15));
        super.setSpacing(10);
        status.setFill(Color.RED); 
    }
    
    private void crearFormulario() {
        Text title = new Text("Registro de usuario");
        title.setFont(Font.font(title.getFont().getFamily(), FontWeight.BOLD, 22));  
        super.getChildren().add(title);
        
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10); 
        grid.add(new Text("Datos personales"), 0, 0, 2, 1);
        Text nt = new Text("Nombre");
        nt.setFont(Font.font(14)); 
        grid.add(nt, 0, 1);
        grid.add(name, 1, 1);
        Text lt = new Text("Apellido");
        lt.setFont(Font.font(14));
        grid.add(lt, 0, 2);
        grid.add(lastname, 1, 2);
        Text tt = new Text("Teléfono");
        tt.setFont(Font.font(14));
        grid.add(tt, 0, 3);
        grid.add(phone, 1, 3);
        Text ct = new Text("Correo");
        ct.setFont(Font.font(14));
        grid.add(ct, 0, 4);
        grid.add(email, 1, 4);
        grid.add(new Text("Datos de usuario"), 6, 0, 2, 1);
        Text ut = new Text("Usuario");
        ut.setFont(Font.font(14));
        grid.add(ut, 6, 1);
        grid.add(user, 7, 1);
        Text pt = new Text("Contraseña");
        pt.setFont(Font.font(14));
        grid.add(pt, 6, 2);
        grid.add(password, 7, 2);
        grid.add(status, 0, 5, 6, 1);
        grid.add(save, 7, 4);
        super.getChildren().add(grid);
    }

    private void evtSave() {
        save.setOnAction(e -> {
            status.setText("");
            String nombre = name.getText();
            String apellido = lastname.getText();
            String telefono = phone.getText();
            String correo = email.getText();
            String usuario = user.getText();
            String contra = password.getText();
            if(validar(nombre, apellido, telefono, correo, usuario, contra)) 
                status.setText("Por favor llene todos los campos");
            else {
                try{
                    if(UsuarioDB.consultarUsuario("\""+usuario+"\"", "\""+contra+"\"") == null){
                        Usuario newUser = new Usuario(usuario, contra, nombre, apellido, correo, telefono, "DUEÑO");
                        if(MensajesEmergentes.cofirmSave("Desea guardar este usuario").get() == ButtonType.OK){
                            int id = UsuarioDB.crear(newUser);
                            if(id != -1){
                                MensajesEmergentes.infoSave(id); 
                                clear();
                            }else {
                                System.out.println("Error al guardar");
                            }   
                        }
                    }else {
                        status.setText("El usuario y contraseña ya esta en uso"); 
                    }
                }catch(SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(RegistrarUsuarioPane.class.getName()).log(Level.SEVERE, null, ex); 
                }
            }
        }); 
    }

    private boolean validar(String nombre, String apellido, String telefono, 
            String correo, String usuario, String contra) {
        return nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() ||
                correo.isEmpty() || usuario.isEmpty() || contra.isEmpty();
    }
    
    private void clear() {
        name.setText("");
        lastname.setText("");
        status.setText("");
        phone.setText("");
        email.setText("");
        user.setText("");
        password.setText("");
    }
   
}
