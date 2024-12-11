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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLClienteController implements Initializable {

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    
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
    private void btnRegistar(ActionEvent event) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar Cliente");

        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioCliente.fxml"));
            Parent formularioCliente = loader.load();

            // Obtén el controlador de la nueva vista
            FXMLFormularioClienteController controlador = loader.getController();

            // Pasa el StackPane al nuevo controlador
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

            // Agrega la nueva vista al StackPane (encima de la actual)
            spEscena.getChildren().add(formularioCliente);

        } catch (IOException e) {
            Logger.getLogger(FXMLFormularioClienteController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    @FXML
    private void btnEditar(ActionEvent event) {
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    
    }
    
}


