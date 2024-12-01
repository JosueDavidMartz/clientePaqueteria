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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class FXMLPaquetesController implements Initializable {

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    private ScrollPane scrollPane;
    
    @FXML
    private Pane paneContenedorPaquetes;
    @FXML
    private TableView<?> tvPaquetes;
    @FXML
    private TableColumn<?, ?> tcDescripcion;
    @FXML
    private TableColumn<?, ?> tcPeso;
    @FXML
    private TableColumn<?, ?> tcAncho;
    @FXML
    private TableColumn<?, ?> tcAlto;
    @FXML
    private TableColumn<?, ?> tcProfundidad;
    @FXML
    private TableColumn<?, ?> tcEnvio;
    @FXML
    private TextField tfBuscarPaquete;

    /**
     * Initializes the controller class.
     */
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
    private void btnModificarPaquete(ActionEvent event) {
    }

    @FXML
    private void btnAgregarPaquete(ActionEvent event) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar paquetes");
        try {
         // Cargar la nueva vista
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioPaquetes.fxml"));
         Parent formularioPaquetes = loader.load();

         // Obtén el controlador de la nueva vista
         FXMLFormularioPaquetesController controlador = loader.getController();

         // Pasa el StackPane al nuevo controlador
         controlador.setStackPane(spEscena);
         controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);
         // Agrega la nueva vista al StackPane (encima de la actual)
         spEscena.getChildren().add(formularioPaquetes);
     } catch (IOException e) {
         Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
     }
    }

    
    
}
