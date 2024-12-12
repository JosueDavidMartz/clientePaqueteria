package clientepaqueteria.controladores;

import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.utilidades.Utilidades;
import clientepaqueteria.utilidades.WindowManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMLInicioController implements Initializable {

    private Pane paneSuperior;
    private Colaborador colaborador;
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
    
    private Stage escenarioInicio;

    
    public void setEscenario(Stage escenario) {
    this.escenarioInicio = escenario;
    WindowManager.registrarVentana("inicio", escenario);
}

    public Stage getEscenario() {
        return escenarioInicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagen = new Image(getClass().getResourceAsStream("/clientepaqueteria/recursos/logo.png")); 
        ImageView portada = new ImageView(imagen);
        spEscena.getChildren().add(portada); 
        
        // Registrar el escenario de inicio en WindowManager
    if (escenarioInicio != null) {
        WindowManager.registrarVentana("inicio", escenarioInicio);
    }
    }

    public void obtenerInformacionColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
        if (colaborador != null) {
            cargarInformacionColaborador(colaborador);
        }
    }
    
    private void cargarInformacionColaborador(Colaborador colaborador) {
        lbNombreColaborador.setText(colaborador.getNombre());
        lbRolColaborador.setText(colaborador.getRol());
    }
    
    

    @FXML  
    private void clickFotoColaborador(MouseEvent event) {
       try {
        // Cargar el FXML para la ventana de perfil
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLPerfil.fxml"));
        Parent perfil = loader.load();

        // Obtener el controlador de la ventana cargada
        FXMLPerfilController controladorPerfil = loader.getController();

        // Pasar el objeto Colaborador al controlador de perfil
        controladorPerfil.obtenerInformacionColaborador(colaborador);

        // Crear un nuevo Stage para la ventana modal
        Stage stage = new Stage();
        stage.setScene(new Scene(perfil)); // Establecer el contenido de la ventana
        stage.setTitle("Perfil del Colaborador"); // Título de la ventana

        // Configurar modalidad
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL); // Define que es una ventana modal
        stage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Bloquea la ventana actual

        // Mostrar la ventana de perfil
        stage.showAndWait();
    } catch (IOException ex) {
        Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    @FXML
    private void btnColaborador(ActionEvent event) throws IOException {

         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLColaboradores.fxml"));     
            Parent colaboradores = loader.load();        
            FXMLColaboradoresController controlador = loader.getController();         
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "COLABORADORES");
           
            // Paso 5: Cambiar la vista en el StackPane
            
            spEscena.getChildren().clear();
            spEscena.getChildren().add(colaboradores);
            lbNombreModulo.setText("Colaboradores");
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
       

    }

    @FXML
    private void btnUnidades(ActionEvent event){  //height 786 x width 474 946 X 474
      
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLUnidades.fxml"));     
            Parent unidades = loader.load();        
            FXMLUnidadesController controlador = loader.getController();          
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "UNIDADES");

            // Paso 5: Cambiar la vista en el StackPane
            
            spEscena.getChildren().clear();
            spEscena.getChildren().add(unidades);
            lbNombreModulo.setText("Unidades");
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnClientes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLCliente.fxml"));     
            Parent clientes = loader.load();        
            FXMLClienteController controlador = loader.getController();         
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "CLIENTES");

            // Paso 5: Cambiar la vista en el StackPane
            
            spEscena.getChildren().clear();
            spEscena.getChildren().add(clientes);
            lbNombreModulo.setText("Clientes");
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }

    @FXML
    private void btnEnvios(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLEnvios.fxml"));     
            Parent envios = loader.load();        
            FXMLEnviosController controlador = loader.getController();          
            controlador.recibirConfiguracion(colaborador, hbSuperior, vbMenu, spEscena, lbNombreModulo, "ENVIOS");

            // Paso 5: Cambiar la vista en el StackPane
            
            spEscena.getChildren().clear();
            spEscena.getChildren().add(envios);
            lbNombreModulo.setText("Envios");
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void btnPaquetes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLPaquetes.fxml"));     
            Parent paquetes = loader.load();        
            FXMLPaquetesController controlador = loader.getController();          
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "PAQUETES");

            // Paso 5: Cambiar la vista en el StackPane
            
            spEscena.getChildren().clear();
            spEscena.getChildren().add(paquetes);
            lbNombreModulo.setText("Paquetes");
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void clickLogo(MouseEvent event) {
        Image imagen = new Image(getClass().getResourceAsStream("/clientepaqueteria/recursos/logo.png")); 
        ImageView portada = new ImageView(imagen);
        spEscena.getChildren().clear();
        spEscena.getChildren().add(portada);   
        lbNombreModulo.setText("Inicio");
                 
    }
    
   
    
}
