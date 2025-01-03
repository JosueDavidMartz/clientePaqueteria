package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
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
import javafx.scene.control.Separator;
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

public class FXMLInicioController implements Initializable, INotificadorOperacion {

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
    @FXML
    private HBox hbUnidades;
    @FXML
    private Separator separador1;
    @FXML
    private HBox hbColaboradores;
    @FXML
    private Separator seperador2;
    @FXML
    private HBox hbClientes;
    @FXML
    private HBox hbEnvios;
    @FXML
    private HBox hbPaquetes;

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
            if (colaborador != null && !colaborador.getRol().equals("Administrador")) {
                configurarMenuPorUsuario(false);
            }

        }

    }

    private void cargarInformacionColaborador(Colaborador colaborador) {
        //lbNombreColaborador.setText(colaborador.getNombre());
        //lbRolColaborador.setText(colaborador.getRol());
        actualizarPantalla(colaborador);

        if (colaborador.getFotoBase64() != null) {

            cargarFotoPerfil();
        }

    }

    @FXML
    private void clickFotoColaborador(MouseEvent event) {
        cargarInformacionColaborador(colaborador);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLPerfil.fxml"));
            Parent perfil = loader.load();

            FXMLPerfilController controladorPerfil = loader.getController();

            //controladorPerfil.obtenerInformacionColaborador(this, colaborador);
            controladorPerfil.obtenerInformacionColaborador(this, colaborador);

            Stage stage = new Stage();
            stage.setScene(new Scene(perfil));
            stage.setTitle("Perfil del Colaborador");

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

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
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "COLABORADORES", colaborador);
            controlador.Inizializar(colaborador, this);
            spEscena.getChildren().clear();
            spEscena.getChildren().add(colaboradores);
            lbNombreModulo.setText("Colaboradores");
            btnColaborador.getStyleClass().removeAll("button-default");
            btnColaborador.getStyleClass().add("button-active");

            btnUnidades.getStyleClass().removeAll("button-active");
            btnUnidades.getStyleClass().add("button-default");
            btnEnvios.getStyleClass().removeAll("button-active");
            btnEnvios.getStyleClass().add("button-default");
            btnPaquetes.getStyleClass().removeAll("button-active");
            btnPaquetes.getStyleClass().add("button-default");
            btnClientes.getStyleClass().removeAll("button-active");
            btnClientes.getStyleClass().add("button-default");

        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnUnidades(ActionEvent event) {  //height 786 x width 474 946 X 474

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLUnidades.fxml"));
            Parent unidades = loader.load();
            FXMLUnidadesController controlador = loader.getController();
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "UNIDADES");

            spEscena.getChildren().clear();
            spEscena.getChildren().add(unidades);
            lbNombreModulo.setText("Unidades");

            btnUnidades.getStyleClass().removeAll("button-default");
            btnUnidades.getStyleClass().add("button-active");

            btnColaborador.getStyleClass().removeAll("button-active");
            btnColaborador.getStyleClass().add("button-default");
            btnEnvios.getStyleClass().removeAll("button-active");
            btnEnvios.getStyleClass().add("button-default");
            btnPaquetes.getStyleClass().removeAll("button-active");
            btnPaquetes.getStyleClass().add("button-default");
            btnClientes.getStyleClass().removeAll("button-active");
            btnClientes.getStyleClass().add("button-default");
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
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, lbNombreModulo, "CLIENTES", this.colaborador);

            spEscena.getChildren().clear();
            spEscena.getChildren().add(clientes);
            lbNombreModulo.setText("Clientes");

            btnClientes.getStyleClass().removeAll("button-default");
            btnClientes.getStyleClass().add("button-active");

            btnColaborador.getStyleClass().removeAll("button-active");
            btnColaborador.getStyleClass().add("button-default");
            btnUnidades.getStyleClass().removeAll("button-active");
            btnUnidades.getStyleClass().add("button-default");
            btnEnvios.getStyleClass().removeAll("button-active");
            btnEnvios.getStyleClass().add("button-default");
            btnPaquetes.getStyleClass().removeAll("button-active");
            btnPaquetes.getStyleClass().add("button-default");

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

            btnEnvios.getStyleClass().removeAll("button-default");
            btnEnvios.getStyleClass().add("button-active");

            btnColaborador.getStyleClass().removeAll("button-active");
            btnColaborador.getStyleClass().add("button-default");
            btnUnidades.getStyleClass().removeAll("button-active");
            btnUnidades.getStyleClass().add("button-default");
            btnClientes.getStyleClass().removeAll("button-active");
            btnClientes.getStyleClass().add("button-default");
            btnPaquetes.getStyleClass().removeAll("button-active");
            btnPaquetes.getStyleClass().add("button-default");
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

            btnPaquetes.getStyleClass().removeAll("button-default");
            btnPaquetes.getStyleClass().add("button-active");

            btnColaborador.getStyleClass().removeAll("button-active");
            btnColaborador.getStyleClass().add("button-default");
            btnUnidades.getStyleClass().removeAll("button-active");
            btnUnidades.getStyleClass().add("button-default");
            btnClientes.getStyleClass().removeAll("button-active");
            btnClientes.getStyleClass().add("button-default");
            btnEnvios.getStyleClass().removeAll("button-active");
            btnEnvios.getStyleClass().add("button-default");
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

    private void cargarFotoPerfil() {
        System.out.println("cargando foto perfil....");
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

    public void actualizarFotoPerfil(Colaborador colaborador) {
        if (colaborador != null && colaborador.getFotoBlob() != null) {
            byte[] fotoBlob = colaborador.getFotoBlob();
            Image nuevaImagen = new Image(new ByteArrayInputStream(fotoBlob));
            ivFotoColaborador.setImage(nuevaImagen);
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarFotoPerfil();
    }

    @FXML
    private void released(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().remove("pressed");
    }

    @FXML
    private void exited(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().remove("hover");
    }

    @FXML
    private void entered(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().add("hover");
    }

    @FXML
    private void pressed(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().add("pressed");
    }

    @FXML
    private void enteredFoto(MouseEvent event) {
        ivFotoColaborador.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0, 0); -fx-opacity: 1.0;");
    }

    @FXML
    private void exitedFoto(MouseEvent event) {
        ivFotoColaborador.setStyle("-fx-effect: null; -fx-opacity: 1.0;");
    }

    @FXML
    private void pressedFoto(MouseEvent event) {
        ivFotoColaborador.setStyle("-fx-effect: innershadow(gaussian, rgba(255, 0, 0, 0.8), 20, 0.5, 0, 0);");
    }

    @FXML
    private void releasedFoto(MouseEvent event) {
        ivFotoColaborador.setStyle("-fx-effect: null;");
    }

    public void configurarMenuPorUsuario(boolean tienePermisos) {
        if (!tienePermisos) {
            hbColaboradores.setVisible(false);
            hbColaboradores.setManaged(false);
            hbUnidades.setVisible(false);
            hbUnidades.setManaged(false);
            separador1.setVisible(false);
            separador1.setManaged(false);
            seperador2.setVisible(false);
            seperador2.setManaged(false);
        }

    }

    void actualizarPantalla(Colaborador colaborador) {
       this.colaborador = colaborador;
       lbNombreColaborador.setText(colaborador.getNombre());
       lbRolColaborador.setText(colaborador.getRol());
       cargarFotoPerfil();
    }
}
