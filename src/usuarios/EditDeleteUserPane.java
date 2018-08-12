/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import bdcondominio.MensajesEmergentes;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * Edtar o eliminar un usuario
 */
public class EditDeleteUserPane extends VBox {
    
    private final ComboBox<Integer> id;
    private final TextField name;
    private final TextField lastname;
    private final TextField phone;
    private final TextField email;
    private final TextField user;
    private final PasswordField password;
    private final Button search;
    private final Button edit;
    private final Button delete;
    private final Text status;
    private Usuario usuario;
    
    public EditDeleteUserPane() {
        id = new ComboBox<>();
        name = new TextField();
        lastname = new TextField();
        phone = new TextField();
        email = new TextField();
        user = new TextField();
        password = new PasswordField();
        search = new Button("Buscar");
        edit = new Button("Editar");
        delete = new Button("Eliminar");
        status = new Text();
        formatter();
        disableTextFields(true);
        topPane();
        evtSearch();
        editEvt();
        deleteEvt();
    }
    
    private void formatter() {
        super.setPadding(new Insets(5, 0, 0, 15));
        super.setSpacing(10);
        status.setFill(Color.RED);
        
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getText().matches("[0-9]*"))
                return change;
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        try {
            //id.setTextFormatter(textFormatter);
            id.getItems().addAll(UsuarioDB.getListaUsuariosId());
        } catch (SQLException ex) {
            Logger.getLogger(EditDeleteUserPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void topPane() {
        Text text = new Text("Consultar por id");
        text.setFont(Font.font(14));
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10); 
        grid.add(text, 0, 0);
        grid.add(id, 1, 0);
        grid.add(search, 6, 0);
        grid.add(status, 0, 1, 3, 1);
        super.getChildren().add(grid);
    }

    private void evtSearch() {
        search.setOnAction(e-> {
            status.setText("");
            if(id.getValue() != null){
                try {
                    usuario = UsuarioDB.getById(id.getValue());
                    if(usuario != null){
                        if(super.getChildren().size() == 2) {
                            super.getChildren().remove(1);
                        }
                        crearFormulario();
                        fillTextFields(usuario);
                    }
                    else
                        status.setText("El id ingresado no existe"); 
                } catch (SQLException ex) {
                    Logger.getLogger(EditDeleteUserPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
                status.setText("Por favor seleccione un id"); 
        });
    }
    
    
    private void editEvt() {
        edit.setOnAction(e-> {
            if(edit.getText().equals("Editar")){
                disableTextFields(false);
                id.setDisable(true); 
                edit.setText("Guardar"); 
            }else if(edit.getText().equals("Guardar")){
                if(MensajesEmergentes.cofirmAccion("Desea guardar este usuario").get() == ButtonType.OK){
                    edit.setText("Editar");
                    disableTextFields(true);
                    id.setDisable(false); 
                    saveChanges();
                }
            } 
        });
    }
    
    private void saveChanges() {
        status.setText("");
        String nombre = name.getText();
        String apellido = lastname.getText();
        String telefono = phone.getText();
        String correo = email.getText();
        String usuario1 = user.getText();
        String contra = password.getText();
        if(validar(nombre, apellido, telefono, correo, usuario1, contra)) 
                status.setText("Por favor llene todos los campos");
        else {
            usuario.setName(nombre);
            usuario.setLastname(apellido);
            usuario.setTelefono(telefono);
            usuario.setEmail(correo);
            usuario.setUser(usuario1);
            usuario.setPassword(contra); 
            try {
                UsuarioDB.update(usuario); 
            } catch (SQLException ex) {
                MensajesEmergentes.errorAction();
                Logger.getLogger(EditDeleteUserPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            MensajesEmergentes.infoSave();
        }
         
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
    
    private void disableTextFields(boolean b) {
        name.setDisable(b);
        lastname.setDisable(b);
        email.setDisable(b);
        phone.setDisable(b);
        user.setDisable(b);
        password.setDisable(b);
    }
    
    private void fillTextFields(Usuario usuario) {
        name.setText(usuario.getName());
        lastname.setText(usuario.getLastname()); 
        email.setText(usuario.getEmail());
        phone.setText(usuario.getTelefono()); 
        user.setText(usuario.getUser());
        password.setText(usuario.getPassword()); 
    }
    
    private void crearFormulario() {        
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
        grid.add(edit, 7, 4);
        grid.add(delete, 7, 5);
        super.getChildren().add(grid);
    }

    private void deleteEvt() {
        delete.setOnAction(e-> {
            status.setText(""); 
            try {
                if(MensajesEmergentes.cofirmAccion("Esta seguro que dese eliminar este usuario?").get() == ButtonType.OK){
                    UsuarioDB.delete(usuario);
                    edit.setText("Editar");
                    id.setDisable(false); 
                    clear();
                    disableTextFields(true);
                    super.getChildren().remove(1);
                }
            } catch (SQLException ex) {
                MensajesEmergentes.errorAction();
                Logger.getLogger(EditDeleteUserPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
