package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.utilidades.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class FXMLPerfilController implements Initializable {

    private FXMLInicioController inicioController;
    private INotificadorOperacion observador;
    private Colaborador colaborador;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No se necesita inicialización adicional
    }

    public void InicializarValores(INotificadorOperacion observador, Colaborador colaborador) {
        this.observador = observador;
        this.colaborador = colaborador;

        if (colaborador != null) {
            lbNombre.setText(colaborador.getNombre());
            lbNumeroPersonal.setText(String.valueOf(colaborador.getNoPersonal()));
            lbRol.setText(colaborador.getRol());
            cargarFotoPerfil();
        }
    }

    public void setInicioController(FXMLInicioController inicioController) {
        this.inicioController = inicioController;
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

                    if (inicioController != null) {
                        Utilidades.mostrarAlerta("Éxito", "Foto subida correctamente.", Alert.AlertType.INFORMATION);
                        cargarFotoPerfil();
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

    @FXML
    private void ivCerrarSesion(MouseEvent event) {
        // Lógica para cerrar sesión (implementar si es necesario)
    }

   
}
