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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class FXMLInicioController implements Initializable {

    private Pane paneSuperior;
    @FXML
    private HBox hbInfoColaborador;
    @FXML
    private VBox vbInfoColaborador;
    @FXML
    private Label lbNombreColaborador;
    @FXML
    private Label lbRolColaborador;
    @FXML
    private ImageView ivFotoColaborador;
    @FXML
    private Label lbNombreModulo;
    @FXML
    private StackPane spEscena;
    @FXML
    private VBox vbMenu;
    @FXML
    private ImageView ivLogo;
    @FXML
    private Button btnColaborador;
    @FXML
    private Button btnUnidades;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnEnvios;
    @FXML
    private Button btnPaquetes;
    @FXML
    private HBox hbSuperior;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagen = new Image(getClass().getResourceAsStream("/clientepaqueteria/recursos/logo.png")); 
        ImageView portada = new ImageView(imagen);
        spEscena.getChildren().add(portada); 

    }    

    @FXML
    
        
    private void clickFotoColaborador(MouseEvent event) {
    }

    @FXML
    private void btnColaborador(ActionEvent event) throws IOException {
       
    }

    @FXML
    private void btnUnidades(ActionEvent event){  //height 786 x width 474 946 X 474
        try {
            Parent unidades = FXMLLoader.load(getClass().getResource("/clientepaqueteria/vistas/FXMLUnidades.fxml"));
            spEscena.getChildren().clear();
            spEscena.getChildren().add(unidades);
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnClientes(ActionEvent event) {
        
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena); 
               
    }

    @FXML
    private void btnEnvios(ActionEvent event) {
        
    }

    @FXML
    private void btnPaquetes(ActionEvent event) {
    }

    @FXML
    private void clickLogo(MouseEvent event) {
        Image imagen = new Image(getClass().getResourceAsStream("/clientepaqueteria/recursos/logo.png")); 
        ImageView portada = new ImageView(imagen);
        spEscena.getChildren().clear();
        spEscena.getChildren().add(portada);   
                 
    }
    
    
}
