/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.controladores;

import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FXMLFormularioUnidadController implements Initializable {
    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    
    
    @FXML
    private Label lbTitulo;
    @FXML
    private Pane paneContenedorFormularioUnidad;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfNoIdentificacionInterno;
    @FXML
    private TextField tdAnio;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfNoIdentificacionVehicular;
    @FXML
    private ComboBox<?> cbTipoUnidad;
 

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    private void btnGuardarFormularioUnidad(ActionEvent event) {
        
    }

    @FXML
    private void btnCancelarFormularioUnidad(ActionEvent event) {
        // Remueve la vista actual del StackPane
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Unidades");
    }

   
    
}
