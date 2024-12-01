/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.controladores;

import clientepaqueteria.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author WIN 10
 */
public class FXMLAutenticacionController implements Initializable {

    @FXML
    private Label lbErrorIniciarSesion;
    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private PasswordField tfContrasenia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
         try{
            Stage escenarioBase = (Stage) lbErrorIniciarSesion.getScene().getWindow();
            Parent principal = FXMLLoader.load(getClass().getResource("/clientepaqueteria/vistas/FXMLInicio.fxml"));

            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Inicio");
            escenarioBase.show();
        }catch (IOException ex){
            Utilidades.mostrarAlertaSimple("Error", "Pro el momento no se puede mostrar la pantalla principal", Alert.AlertType.ERROR);

        }
    }
    
}
