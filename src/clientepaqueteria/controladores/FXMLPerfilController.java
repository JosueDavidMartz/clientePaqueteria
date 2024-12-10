/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.controladores;

import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.utilidades.Utilidades;
import clientepaqueteria.utilidades.WindowManager;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


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
    private ImageView btnSubirFoto;
    @FXML
    private Label lbCerrarSesion;
    
    private Colaborador colaborador;

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
    
    public void obtenerInformacionColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
        if (colaborador != null) {
            cargarInformacionColaborador(colaborador);
        }
    }
    
    private void cargarInformacionColaborador(Colaborador colaborador) {
         // Verifica que el colaborador no sea nulo y muestra los datos en las etiquetas correspondientes
        String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "";
        String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
        String apellidoMaterno = colaborador.getApellidoMaterno() != null ? colaborador.getApellidoMaterno() : "";

        // Concatenar los valores y eliminar espacios innecesarios
        String nombreCompleto = (nombre + " " + apellidoPaterno + " " + apellidoMaterno).trim();

        // Asignar al Label
        lbNombre.setText(nombreCompleto);
        lbNumeroPersonal.setText(colaborador.getNumeroPersonal());
        lbRol.setText(colaborador.getRol());
        
    
    }


    @FXML
    private void ClickedCerarSesion(MouseEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Cerrar Sesión");
    alert.setHeaderText(null);
    alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            // Cerrar la ventana actual
            Stage escenarioActual = (Stage) lbCerrarSesion.getScene().getWindow();

            // Cerrar las ventanas registradas en WindowManager
            WindowManager.cerrarVentana("inicio");
            WindowManager.cerrarVentana("perfil");

            // Cargar la nueva vista de autenticación
            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLAutenticacion.fxml"));
            Parent vistaLogin = loaderLogin.load();
            Stage escenarioLogin = new Stage();
            escenarioLogin.setScene(new Scene(vistaLogin));
            escenarioLogin.setTitle("Pantalla Login");

            // Mostrar la ventana de inicio de sesión
            escenarioLogin.show();

            // Cerrar la ventana actual
            escenarioActual.close();

        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", 
                "Por el momento no se puede mostrar la pantalla de inicio de sesión", 
                Alert.AlertType.ERROR);
        }
    }
}
}