package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.utilidades.Utilidades;
import clientepaqueteria.utilidades.WindowManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLPerfilController implements Initializable {

    private FXMLInicioController inicioController;
    private INotificadorOperacion observador = null;

    @FXML
    private ImageView ivFotoPerfil;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbNumeroPersonal;
    @FXML
    private Label lbRol;
    @FXML
    private ImageView ivCerrarSesion;
    @FXML
    private ImageView btnSubirFoto;
    @FXML
    private Label lbCerrarSesion;

    private Colaborador colaborador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ivCerrarSesion(MouseEvent event) {

    }

    public void obtenerInformacionColaborador(INotificadorOperacion observador, Colaborador colaborador) {
        this.observador = observador;
        this.colaborador = colaborador;
        lbNombre.setText("");

        if (colaborador != null) {
            cargarInformacionColaborador(colaborador);
            cargarFotoPerfil();
        }
    }

    private void cargarInformacionColaborador(Colaborador colaborador) {

        String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "";
        String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
        String apellidoMaterno = colaborador.getApellidoMaterno() != null ? colaborador.getApellidoMaterno() : "";

        // Concatenar los valores y eliminar espacios 
        String nombreCompleto = (nombre + " " + apellidoPaterno + " " + apellidoMaterno).trim();

        lbNombre.setText(nombreCompleto);
        lbNumeroPersonal.setText(colaborador.getNumeroPersonal());
        lbRol.setText(colaborador.getRol());

    }

    @FXML
    private void ClickedCerarSesion(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                Stage escenarioActual = (Stage) lbCerrarSesion.getScene().getWindow();

                WindowManager.cerrarVentana("inicio");
                WindowManager.cerrarVentana("perfil");

                FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLAutenticacion.fxml"));
                Parent vistaLogin = loaderLogin.load();
                Stage escenarioLogin = new Stage();
                escenarioLogin.setScene(new Scene(vistaLogin));
                escenarioLogin.setTitle("Pantalla Login");

                escenarioLogin.show();

                escenarioActual.close();

            } catch (IOException ex) {
                Utilidades.mostrarAlerta("Error",
                        "Por el momento no se puede mostrar la pantalla de inicio de sesión",
                        Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnSubirFoto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar foto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg"));

        String userPicturesDir = System.getProperty("user.home") + File.separator + "Pictures";
        File picturesDir = new File(userPicturesDir);

        if (picturesDir.exists()) {
            fileChooser.setInitialDirectory(picturesDir);
        }

        File archivoFoto = fileChooser.showOpenDialog(null);

        if (archivoFoto != null) {
            try {
                byte[] fotoBlob = Files.readAllBytes(archivoFoto.toPath());
                Mensaje respuesta = ColaboradorDAO.subirFoto(colaborador.getIdColaborador(), fotoBlob);

                if (!respuesta.isError()) {
                    colaborador.setFotoBlob(fotoBlob);
                    Colaborador colaboradorActualizado = ColaboradorDAO.obtenerFotoBase64(colaborador.getIdColaborador());
                    if (colaboradorActualizado != null) {

                        this.colaborador.setFotoBase64(colaboradorActualizado.getFotoBase64());
                        cargarFotoPerfil();
                        observador.notificarOperacionExitosa("Inicio", "actualizado");
                    }
                    if (inicioController != null) {
                        Utilidades.mostrarAlerta("Éxito", "Foto subida correctamente.", Alert.AlertType.INFORMATION);

                        // metodo para obtener al colaborador de nuevo.
                        inicioController.actualizarFotoPerfil(colaborador);

                    }
                } else {
                    Utilidades.mostrarAlerta("Error", "No se pudo subir la foto.", Alert.AlertType.ERROR);
                }
            } catch (IOException e) {
                Utilidades.mostrarAlerta("Error", "Error al leer el archivo de foto.", Alert.AlertType.ERROR);
            }
        }
    }

    private void cargarFotoPerfil() {

        if (colaborador != null) {
            Colaborador colaboradorConFoto = ColaboradorDAO.obtenerFotoBase64(colaborador.getIdColaborador());

            if (colaboradorConFoto != null && colaboradorConFoto.getFotoBase64() != null && !colaboradorConFoto.getFotoBase64().isEmpty()) {
                byte[] decodeImage = Base64.getDecoder().decode(colaboradorConFoto.getFotoBase64().replaceAll("\\n", ""));
                ivFotoPerfil.setImage(new Image(new ByteArrayInputStream(decodeImage)));
                // Hacer la imagen redonda
                Circle clip = new Circle(ivFotoPerfil.getFitWidth() / 2, ivFotoPerfil.getFitHeight() / 2, ivFotoPerfil.getFitWidth() / 2);
                ivFotoPerfil.setClip(clip);

            }
        }
    }
}
