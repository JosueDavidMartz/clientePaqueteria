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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author WIN 10
 */
public class FXMLFormularioEnvioController implements Initializable {
    
    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;

    @FXML
    private TextField tfNombreFormularioEnvio;
    @FXML
    private TextField tfNombreCalleOrigen;
    @FXML
    private TextField tfCodigoPostalOrigen;
    @FXML
    private TextField tfColoniaOrigen;
    @FXML
    private TextField tfNumeroOrigen;
    @FXML
    private TextField tfEstadoOrigen;
    @FXML
    private TextField tfCiudadOrigen;
    @FXML
    private TextField tfCalleDestino;
    @FXML
    private TextField tfColoniaDestino;
    @FXML
    private TextField tfNumeroDestino;
    @FXML
    private TextField tfCodiigoPostalDestino;
    @FXML
    private TextField tfEstadoDestino;
    @FXML
    private TextField tfCiudadDestino;
    private Pane paneVincularEnvio;
    @FXML
    private Pane paneNumeroGuia;
    @FXML
    private Label lbNumeroGuia;
    @FXML
    private ImageView ivCopiarNumeroGuia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }
    
    void setStackPane(StackPane spEscena) {
        this.stackPane = spEscena;
    }
    @FXML
    private void btnCancelar(ActionEvent event) {
        paneNumeroGuia.setVisible(false);
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envios");
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    private void btnAceptarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    private void btnCancelarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    @FXML
    private void ivCopiarNumeroGuia(MouseEvent event) {
    }

    @FXML
    private void btnAceptarNumeroGuia(ActionEvent event) {
        paneNumeroGuia.setVisible(false);
    }

    

   
    
}
