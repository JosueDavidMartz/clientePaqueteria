package clientepaqueteria.controladores;

import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class FXMLFormularioClienteController implements Initializable {
    
    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private PasswordField tfContraseña;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfNumeroCasa;
    @FXML
    private TextField tfCodigoP;

    
    
    
    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }
    public void setStackPane(StackPane stackPane) {
           this.stackPane = stackPane;
       }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnCancelar(ActionEvent event) {
        // Remueve la vista actual del StackPane (el formulario)
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);

        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Cliente");
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
    }
    
}
