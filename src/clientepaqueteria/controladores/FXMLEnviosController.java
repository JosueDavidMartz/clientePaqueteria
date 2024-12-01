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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class FXMLEnviosController implements Initializable {
    
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    @FXML
    private Label lbBuscarEnvio;
    @FXML
    private TextField tfBuscarEnvio;
    @FXML
    private TableView<?> tvEnvios;
    @FXML
    private TableColumn<?, ?> tcCliente;
    @FXML
    private TableColumn<?, ?> tcOrigen;
    @FXML
    private TableColumn<?, ?> tcDestino;
    @FXML
    private TableColumn<?, ?> tcEstado;
    @FXML
    private TableColumn<?, ?> tcConductor;
    @FXML
    private TableColumn<?, ?> tcPaquetes;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
         //To change body of generated methods, choose Tools | Templates.
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    @FXML
    private void btnModificar(ActionEvent event) {
    }

    @FXML
    private void btnCrearEnvio(ActionEvent event) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar Envío");
        try {
         // Cargar la nueva vista
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioEnvio.fxml"));
         Parent formularioEnvio = loader.load();

         // Obtén el controlador de la nueva vista
         FXMLFormularioEnvioController controlador = loader.getController();

         // Pasa el StackPane al nuevo controlador
         controlador.setStackPane(spEscena);
         controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);
         // Agrega la nueva vista al StackPane (encima de la actual)
         spEscena.getChildren().add(formularioEnvio);
     } catch (IOException e) {
         Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
     }
    }
    
    
    
}
