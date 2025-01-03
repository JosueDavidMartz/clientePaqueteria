package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.utilidades.Utilidades;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.RespuestaRegistroColaborador;
import clientepaqueteria.pojo.Rol;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class FXMLFormularioColaboradorController implements Initializable {

    private StackPane stackPane;
    private HBox hbSuperior;
    private VBox vbMenu;
    private StackPane spEscena;
    private Label label;
    private String nombre;
    private ObservableList<Rol> roles;
    private Colaborador colaboradorEdicion;
    private boolean modoEdicion = false;
    private INotificadorOperacion observador;
    private FXMLInicioController inicioController = null;
    private File archivoFoto;

    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfNumLicencia;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private PasswordField pfContraseña;
    @FXML
    private Label lbNumeroLicencia;
    @FXML
    private Label lbErrorNumeroPersonal;
    @FXML
    private Label lbErrorRol;
    @FXML
    private Label lbErrorLicencia;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private Label lbErrorCurp;
    @FXML
    private Label lbErrorContrasenia;
    @FXML
    private Label lbErrorApellidoPaterno;
    @FXML
    private Label lbErrorApellidoMaterno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Limitar a 11 caracteres
        tfCurp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 18) {
                    tfCurp.setText(oldValue);  // Restaurar el valor anterior si se excede
                }
            }
        });
        tfCurp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Convierte el texto a mayúsculas y lo asigna al TextField
                tfCurp.setText(newValue.toUpperCase());
            }
        });

        cargarRoles();

        tfNumLicencia.setVisible(false);
        lbNumeroLicencia.setVisible(false);

        cbRol.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && "Conductor".equals(newValue.getNombre())) {
                tfNumLicencia.setVisible(true);
                lbNumeroLicencia.setVisible(true);
            } else {
                tfNumLicencia.setVisible(false);
                lbNumeroLicencia.setVisible(false);
            }
        });

    }

    public void InicializarValores(INotificadorOperacion observador, Colaborador colaboradorEdicion) {
        this.observador = observador;
        this.colaboradorEdicion = colaboradorEdicion;

        if (colaboradorEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion();
            cargarFotoPerfil();
        }
    }

    public void setInicioController(FXMLInicioController inicioController) {
        this.inicioController = inicioController;
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
        boolean respuesta = Utilidades.mostrarConfirmacion("Confirmar", "Si cancelas perderás los cambios no guardados");
        if (respuesta) {

            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);

            // Reducir la interfaz al estado anterior (en este caso "Colaboradores")
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Colaboradores");

            recargarPantallaColaboradores();
        }
    }

    private void cargarDatosEdicion() {
        tfNumeroPersonal.setText(this.colaboradorEdicion.getNumeroPersonal());
        tfNombre.setText(this.colaboradorEdicion.getNombre());
        tfApellidoPaterno.setText(this.colaboradorEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(this.colaboradorEdicion.getApellidoMaterno());
        tfCorreo.setText(this.colaboradorEdicion.getCorreo());
        tfCurp.setText(this.colaboradorEdicion.getCurp());
        pfContraseña.setText(this.colaboradorEdicion.getContraseña());
        tfNumLicencia.setText(this.colaboradorEdicion.getNumeroLicencia());
        tfNumeroPersonal.setEditable(false);
        int posicionRol = obtenerPosicionRol(this.colaboradorEdicion.getIdRol());
        cbRol.getSelectionModel().select(posicionRol);
        pfContraseña.setText(this.colaboradorEdicion.getContraseña());
        tfNumeroPersonal.setDisable(true);
        cbRol.setDisable(true);

    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        lbErrorApellidoMaterno.setText("");
        lbErrorApellidoPaterno.setText("");
        lbErrorContrasenia.setText("");
        lbErrorCorreo.setText("");
        lbErrorCurp.setText("");
        lbErrorLicencia.setText("");
        lbErrorNombre.setText("");
        lbErrorNumeroPersonal.setText("");
        lbErrorRol.setText("");

        String numeroPersonal = tfNumeroPersonal.getText();
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String correo = tfCorreo.getText();
        String curp = tfCurp.getText();
        String numLicencia = tfNumLicencia.isVisible() ? tfNumLicencia.getText() : null;  // Solo obtiene la licencia si es visible
        String contrasena = pfContraseña.getText();
        int idRol = (cbRol.getSelectionModel().getSelectedItem() != null)
                ? cbRol.getSelectionModel().getSelectedItem().getIdRol() : 0;
        Colaborador colaborador = new Colaborador();
        colaborador.setNumeroPersonal(numeroPersonal);
        colaborador.setNombre(nombre);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setCorreo(correo);
        colaborador.setCurp(curp);
        colaborador.setNumeroLicencia(numLicencia);
        colaborador.setContraseña(contrasena);
        colaborador.setIdRol(idRol);
        if (modoEdicion) {
            colaborador.setRol(colaboradorEdicion.getRol());
            colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());
        }

        if (sonCamposValidos(colaborador)) {
            if (!modoEdicion) {
                boolean respuesta = Utilidades.mostrarConfirmacion("Guardar", "¿Confirmas registrar un nuevo colaborador?");
                if (respuesta) {

                    guardarDatosColaborador(colaborador);
                }

            } else {
                boolean respuesta = Utilidades.mostrarConfirmacion("Modificar", "¿Confirmas guardar los cambios del colaborador?");
                if (respuesta) {
                    if (this.archivoFoto != null) {
                        confirmarActualizarFoto(this.archivoFoto);
                    }
                    editarDatosColaborador(colaborador);
                    if (inicioController != null) {
                        inicioController.actualizarPantalla(colaborador);
                    }
                }
            }
        }
    }

    @FXML
    private void btnSubirFoto(ActionEvent event) {
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
            this.archivoFoto = archivoFoto;  // Guardamos el archivo temporalmente
            cargarFotoTemporal(archivoFoto); // Mostramos la imagen en el ImageView
        }
    }

    private void cargarFotoTemporal(File archivoFoto) {
        try {
            byte[] fotoBlob = Files.readAllBytes(archivoFoto.toPath());
            Image imagen = new Image(new ByteArrayInputStream(fotoBlob));
            ivFoto.setImage(imagen);

            // Hacer la imagen redonda
            Circle clip = new Circle(ivFoto.getFitWidth() / 2, ivFoto.getFitHeight() / 2, ivFoto.getFitWidth() / 2);
            ivFoto.setClip(clip);

        } catch (IOException e) {
            Utilidades.mostrarAlerta("Error", "Error al cargar la foto seleccionada.", Alert.AlertType.ERROR);
        }
    }

    private void confirmarActualizarFoto(File archivoFoto) {
        if (archivoFoto != null) {
            try {
                byte[] fotoBlob = Files.readAllBytes(archivoFoto.toPath());
                Mensaje respuesta = ColaboradorDAO.subirFoto(colaboradorEdicion.getIdColaborador(), fotoBlob);

                if (!respuesta.isError()) {
                    colaboradorEdicion.setFotoBlob(fotoBlob);
                    cargarFotoPerfil();
                    observador.notificarOperacionExitosa("Inicio", "Foto actualizada correctamente.");
                } else {
                    Utilidades.mostrarAlerta("Error", "No se pudo subir la foto.", Alert.AlertType.ERROR);
                }
            } catch (IOException e) {
                Utilidades.mostrarAlerta("Error", "Error al leer el archivo de foto.", Alert.AlertType.ERROR);
            }
        }
    }

    private void cargarFotoPerfil() {

        if (colaboradorEdicion != null) {
            Colaborador colaboradorConFoto = ColaboradorDAO.obtenerFotoBase64(colaboradorEdicion.getIdColaborador());

            if (colaboradorConFoto != null && colaboradorConFoto.getFotoBase64() != null && !colaboradorConFoto.getFotoBase64().isEmpty()) {
                byte[] decodeImage = Base64.getDecoder().decode(colaboradorConFoto.getFotoBase64().replaceAll("\\n", ""));
                ivFoto.setImage(new Image(new ByteArrayInputStream(decodeImage)));
                // Hacer la imagen redonda
                Circle clip = new Circle(ivFoto.getFitWidth() / 2, ivFoto.getFitHeight() / 2, ivFoto.getFitWidth() / 2);
                ivFoto.setClip(clip);

            }
        }
    }

    private void cargarRoles() {
        roles = FXCollections.observableArrayList();
        List<Rol> listaWS = ColaboradorDAO.obtenerRolesColaborador();
        if (listaWS != null) {
            roles.addAll(listaWS);
            cbRol.setItems(roles);
        }
    }

    private boolean sonCamposValidos(Colaborador colaborador) {
        boolean bandera = true;
        // Validaciones en modo edición
        if (modoEdicion) {
            if (tfNombre.getText().trim().isEmpty()
                    || tfCorreo.getText().trim().isEmpty()
                    || tfCurp.getText().trim().isEmpty()
                    || tfApellidoPaterno.getText().trim().isEmpty()
                    || tfApellidoMaterno.getText().trim().isEmpty()
                    || pfContraseña.getText().trim().isEmpty()) {

                Utilidades.mostrarAlerta("Campos vacíos", "Asegúrate de no dejar campos vacíos.", Alert.AlertType.ERROR);
                bandera = false;
            }

            if (colaborador.getRol().equals("Conductor") && tfNumLicencia.getText().trim().isEmpty()) {

                lbErrorLicencia.setText("El número de licencia no puede estar vacío para un conductor.");
                bandera = false;
            }

        } else// Validaciones en modo registro
        if (tfNumeroPersonal.getText().trim().isEmpty()
                || cbRol.getValue() == null
                || tfNombre.getText().trim().isEmpty()
                || tfCorreo.getText().trim().isEmpty()
                || tfCurp.getText().trim().isEmpty()
                || tfApellidoPaterno.getText().trim().isEmpty()
                || tfApellidoMaterno.getText().trim().isEmpty()
                || pfContraseña.getText().trim().isEmpty()) {

            Utilidades.mostrarAlerta("Campos vacíos", "Asegúrate de no dejar campos vacíos.", Alert.AlertType.ERROR);
            bandera = false;
        }

        // Validación para campos de texto (solo letras y espacios)
        if (!tfNombre.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            lbErrorNombre.setText("Únicamente acepta letras y espacios");
            bandera = false;
        }
        if (!tfApellidoPaterno.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            lbErrorApellidoPaterno.setText("Únicamente acepta letras y espacios");
            bandera = false;
        }
        if (!tfApellidoMaterno.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            lbErrorApellidoMaterno.setText("Únicamente acepta letras y espacios");
            bandera = false;
        }

// Validación para número personal (debe tener 5 caracteres)
        if (tfNumeroPersonal.getText().length() != 5) {
            lbErrorNumeroPersonal.setText("Debe tener 5 caracteres");
            bandera = false;
        }

// Validación para correo electrónico (debe ser un formato válido y terminar en dominios específicos)
        String correo = tfCorreo.getText().trim();
// Expresión regular para validar cualquier correo electrónico válido
        if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            lbErrorCorreo.setText("Formato inválido, (usuario@dominio.com)");
            bandera = false;
        }

// Validación para CURP (debe tener 18 caracteres)
        if (tfCurp.getText().length() != 18) {
            int longitudActual = tfCurp.getText().length();
            String mensaje = "El CURP debe tener exactamente 18 caracteres, pero actualmente tiene " + longitudActual + ".";
            lbErrorCurp.setText(mensaje);
            bandera = false;
        }

// Validación para contraseña (mínimo 8 caracteres y al menos un número)
        String contraseña = pfContraseña.getText();
        if (!contraseña.matches("^(?=.*[0-9]).{8,}$")) {
            lbErrorContrasenia.setText("Requiere al menos 8 caracteres y un número");
            bandera = false;
        }

        if (cbRol.getValue() == null) {
            lbErrorRol.setText("Requieres seleccionar un rol");
            bandera = false;
        }

// Validación adicional para conductores en modo registro
        if (cbRol.getValue() != null
                && cbRol.getValue().getNombre().equals("Conductor")) {
            String numLicencia = tfNumLicencia.getText().trim();

            // Validación para verificar que el campo no esté vacío y que tenga exactamente 11 caracteres alfanuméricos
            if (numLicencia.isEmpty() || !numLicencia.matches("[A-Za-z0-9]{11}")) {
                lbErrorLicencia.setText("Requiere 11 caracteres (alfanuméricos)");
                bandera = false;
            }
        }

        return bandera;
    }

    private void guardarDatosColaborador(Colaborador colaborador) {
        byte[] fotoBlob = null;
        try {
            fotoBlob = Files.readAllBytes(archivoFoto.toPath());
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RespuestaRegistroColaborador mensaje = ColaboradorDAO.registrarColaborador(colaborador, fotoBlob);
        if (!mensaje.isError()) {
            Utilidades.mostrarAlerta("Colaborador registrado", "La información del colaborador se registró correctamente.", Alert.AlertType.INFORMATION);
            // Cerrar la ventana o volver a la pantalla de colaboradores
            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);

            // Reducir la interfaz al estado anterior (en este caso "Colaboradores")
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Colaboradores");
            observador.notificarOperacionExitosa("creado", "creado nuevo");
            //recargarPantallaColaboradores();
        } else {
            Utilidades.mostrarAlerta("Error al registrar", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void recargarPantallaColaboradores() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLColaboradores.fxml"));
            Parent colaboradores = loader.load();

            FXMLColaboradoresController controlador = loader.getController();

            controlador.recibirConfiguracion(hbSuperior, vbMenu, stackPane, label, "COLABORADORES", null);

            stackPane.getChildren().clear();
            stackPane.getChildren().add(colaboradores);

            label.setText("Colaboradores");
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editarDatosColaborador(Colaborador colaborador) {
        Mensaje msj = ColaboradorDAO.modificar(colaborador);
        if (!msj.isError()) {
            Utilidades.mostrarAlerta("Colaborador actualizado", "La información del colaborador "
                    + colaborador.getNombre() + " ha sido actualizada correctamente.", Alert.AlertType.INFORMATION);
            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);

            // Reducir la interfaz al estado anterior (en este caso "Colaboradores")
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Colaboradores");
            observador.notificarOperacionExitosa("editado", colaboradorEdicion.getNombre());
            // Recargar la página de "Colaboradores"
            //recargarPantallaColaboradores();
        } else {

            Utilidades.mostrarAlerta("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private int obtenerPosicionRol(int idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (idRol == roles.get(i).getIdRol()) {
                return i;
            }
        }
        return 0;
    }

}
