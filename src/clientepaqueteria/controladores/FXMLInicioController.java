package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.utilidades.Utilidades;
import clientepaqueteria.utilidades.WindowManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLInicioController implements Initializable {
    
    private Pane paneSuperior;
    private Colaborador colaborador;
    private INotificadorOperacion observador;

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
        cargarFotoPerfil();
    }

    private void cargarFotoPerfil() {
        int idColaborador = colaborador.getIdColaborador();
        Colaborador colaboradorConFoto = ColaboradorDAO.obtenerFotoBase64(idColaborador);

        if (colaboradorConFoto != null && colaboradorConFoto.getFotoBase64() != null && !colaboradorConFoto.getFotoBase64().isEmpty()) {
            byte[] decodeImage = Base64.getDecoder().decode(colaboradorConFoto.getFotoBase64().replaceAll("\\n", ""));
            Image image = new Image(new ByteArrayInputStream(decodeImage));
            ivFotoColaborador.setImage(image);
            Circle clip = new Circle(ivFotoColaborador.getFitWidth() / 2, ivFotoColaborador.getFitHeight() / 2, ivFotoColaborador.getFitWidth() / 2);
            ivFotoColaborador.setClip(clip);
        }
    }

       @FXML
    private void clickFotoColaborador(MouseEvent event) {
        cargarInformacionColaborador(colaborador);
        INotificadorOperacion observador = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLPerfil.fxml"));
            Parent perfil = loader.load();
            FXMLPerfilController controlador = loader.getController();
            controlador.InicializarValores(observador, colaborador);
            controlador.setInicioController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(perfil));
            stage.setTitle("Perfil del Colaborador");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(spEscena.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnColaborador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLColaboradores.fxml"));
            Parent colaboradores = loader.load();
            FXMLColaboradoresController controlador = loader.getController();
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "COLABORADORES");
            spEscena.getChildren().clear();
            spEscena.getChildren().add(colaboradores);
            lbNombreModulo.setText("Colaboradores");
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnUnidades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLUnidades.fxml"));
            Parent unidades = loader.load();
            FXMLUnidadesController controlador = loader.getController();
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "UNIDADES");
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
    
        public void actualizarFotoPerfil(Colaborador colaborador) {
        if (colaborador != null && colaborador.getFotoBlob() != null) {
            byte[] fotoBlob = colaborador.getFotoBlob();
            Image nuevaImagen = new Image(new ByteArrayInputStream(fotoBlob));
            ivFotoColaborador.setImage(nuevaImagen);
        }
    }
}
