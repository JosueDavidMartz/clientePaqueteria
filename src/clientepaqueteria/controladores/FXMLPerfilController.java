/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author WIN 10
 */
public class FXMLPerfilController implements Initializable {

    @FXML
    private ImageView ivFotoPerfil;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbNumeroPersonal;
    @FXML
    private Label lbRol;
    @FXML
    private ImageView ivCerrarSesion;
    @FXML
    private Button btnSubirFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ivCerrarSesion(MouseEvent event) {
    }

    @FXML
    private void btnSubirFoto(ActionEvent event) {
    }
    
}
