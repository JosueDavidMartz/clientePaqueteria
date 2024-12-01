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

public class FXMLFormularioColaboradorController implements Initializable {
    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbNombreModulo;
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
    public void setStackPane(StackPane stackPane) {
           this.stackPane = stackPane;
       }

   @FXML
    private void btnVolver(ActionEvent event) {
        // Remueve la vista actual del StackPane (el formulario)
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        
        // Vuelve a reducir la interfaz al estado anterior (en este caso "Colaboradores")
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Colaboradores");
    }




}
