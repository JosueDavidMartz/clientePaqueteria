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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author WIN 10
 */
public class FXMLUnidadesController implements Initializable {

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    @FXML
    private Pane paneBajaUnidad;
    @FXML
    private TextArea taMotivo;
    @FXML
    private Label lbErrorMotivo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
        
    }


    @FXML
    private void btnAñadirUnidad(ActionEvent event) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar unidad");
        try {
         // Cargar la nueva vista
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioUnidad.fxml"));
         Parent formularioUnidad = loader.load();

         // Obtén el controlador de la nueva vista
         FXMLFormularioUnidadController controlador = loader.getController();

         // Pasa el StackPane al nuevo controlador
         controlador.setStackPane(spEscena);
         controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);
         // Agrega la nueva vista al StackPane (encima de la actual)
         spEscena.getChildren().add(formularioUnidad);
     } catch (IOException e) {
         Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
     }
    }

    @FXML
    private void btnEliminarUnidad(ActionEvent event) {
         paneBajaUnidad.setVisible(true);
    }

    @FXML
    private void btnModificarUnidad(ActionEvent event) {
    }

    @FXML
    private void btnAceptarBaja(ActionEvent event) {
         paneBajaUnidad.setVisible(false);
    }

    @FXML
    private void btnCancelarBaja(ActionEvent event) {
         paneBajaUnidad.setVisible(false);
    }
}
