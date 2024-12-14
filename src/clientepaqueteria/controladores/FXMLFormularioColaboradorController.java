package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.utilidades.Utilidades;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Rol;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRoles();

        // Inicialmente, ocultar el campo de la licencia
        tfNumLicencia.setVisible(false);

        // Agregar un ChangeListener al ComboBox para verificar el rol seleccionado
        cbRol.valueProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null && "Conductor".equals(newValue.getNombre())) {
        tfNumLicencia.setVisible(true);  // Muestra el campo de licencia si el rol es Conductor
    } else {
        tfNumLicencia.setVisible(false); // Oculta el campo de licencia para otros roles
    }
});

    }
    
     public void InicializarValores(INotificadorOperacion observador, Colaborador colaboradorEdicion){
        this.observador = observador;
        this.colaboradorEdicion = colaboradorEdicion;
        if (colaboradorEdicion != null){
            modoEdicion = true;
            cargarDatosEdicion();
        }
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
    
        // Reducir la interfaz al estado anterior (en este caso "Colaboradores")
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Colaboradores");
    
        // Recargar la página de "Colaboradores"
        recargarPantallaColaboradores();
    }
    
    private void cargarDatosEdicion(){
        tfNumeroPersonal.setText(this.colaboradorEdicion.getNumeroPersonal());
        tfNombre.setText(this.colaboradorEdicion.getNombre());
        tfApellidoPaterno.setText(this.colaboradorEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(this.colaboradorEdicion.getApellidoMaterno());
        tfCorreo.setText(this.colaboradorEdicion.getCorreo());
        tfCurp.setText(this.colaboradorEdicion.getCurp());
        pfContraseña.setText(this.colaboradorEdicion.getContraseña());
        tfNumeroPersonal.setEditable(false); // No editable en edición
        int posicionRol = obtenerPosicionRol(this.colaboradorEdicion.getIdRol());
        cbRol.getSelectionModel().select(posicionRol);
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        // Obtener los valores de los campos del formulario
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
        colaborador.setNumeroLicencia(numLicencia);  // Si el campo es visible, toma el valor
        colaborador.setContraseña(contrasena);
        colaborador.setIdRol(idRol);

        if (sonCamposValidos(colaborador)) {
            if (!modoEdicion) {
                guardarDatosColaborador(colaborador); // Si es nuevo, guarda
            } else {
                colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador()); // Para la edición, usa el ID
                editarDatosColaborador(colaborador); // Llama al método para editar
            }
        } else {
            Utilidades.mostrarAlerta("Error de validación", "Por favor, corrige los errores en el formulario.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnSubirFoto(ActionEvent event) {
        // Lógica para subir la foto del colaborador
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
        // Validar nombre
        if (colaborador.getNombre().trim().isEmpty()) {
            Utilidades.mostrarAlerta("Campo vacío", "El nombre no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }

        // Validar apellido paterno
        if (colaborador.getApellidoPaterno().trim().isEmpty()) {
            Utilidades.mostrarAlerta("Campo vacío", "El apellido paterno no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }

        // Validar correo
        if (colaborador.getCorreo().trim().isEmpty()) {
            Utilidades.mostrarAlerta("Campo vacío", "El correo no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }

        // Validar CURP
        if (colaborador.getCurp().trim().isEmpty()) {
            Utilidades.mostrarAlerta("Campo vacío", "El CURP no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }

        // Validar contraseña
        if (colaborador.getContraseña().trim().isEmpty()) {
            Utilidades.mostrarAlerta("Campo vacío", "La contraseña no puede estar vacía.", Alert.AlertType.WARNING);
            return false;
        }

        // Validar licencia solo si el rol es "Conductor"
        if ("Conductor".equals(colaborador.getRol()) && (colaborador.getNumeroLicencia() == null || colaborador.getNumeroLicencia().trim().isEmpty())) {
            Utilidades.mostrarAlerta("Campo vacío", "El número de licencia es obligatorio para conductores.", Alert.AlertType.WARNING);
            return false;
        }

        // Validar rol
        if (colaborador.getIdRol() == 0) {  // Asumiendo que `idRol` es el identificador numérico del rol
            Utilidades.mostrarAlerta("Campo vacío", "Debe seleccionar un rol.", Alert.AlertType.WARNING);
        return false;
        }

        return true; // Si todos los campos son válidos
    }

    private void guardarDatosColaborador(Colaborador colaborador) {
        Mensaje mensaje = ColaboradorDAO.registrarColaborador(colaborador);
        if (!mensaje.isError()) {
            Utilidades.mostrarAlerta("Colaborador registrado", "La información del colaborador se registró correctamente.", Alert.AlertType.INFORMATION);
            // Cerrar la ventana o volver a la pantalla de colaboradores
            btnVolver(null);
        } else {
            Utilidades.mostrarAlerta("Error al registrar", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void recargarPantallaColaboradores() {
    try {
        // Cargar nuevamente la vista de Colaboradores
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLColaboradores.fxml"));
        Parent colaboradores = loader.load();
        
        // Obtener el controlador de la nueva vista cargada
        FXMLColaboradoresController controlador = loader.getController();
        
        // Configurar la nueva vista (si es necesario)
        controlador.recibirConfiguracion(hbSuperior, vbMenu, stackPane, label, "COLABORADORES");

        // Limpiar el StackPane y agregar la nueva vista
        stackPane.getChildren().clear();
        stackPane.getChildren().add(colaboradores);

        // Actualizar el nombre del módulo si es necesario
        label.setText("Colaboradores");
    } catch (IOException ex) {
        Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
     private void editarDatosColaborador(Colaborador colaborador) {
        Mensaje msj = ColaboradorDAO.modificar(colaborador);
        if (!msj.isError()) {
            Utilidades.mostrarAlerta("Colaborador actualizado", "La información del colaborador " + 
                    colaborador.getNombre() + " ha sido actualizada correctamente.", Alert.AlertType.INFORMATION);
            recargarPantallaColaboradores();
        } else {
            Utilidades.mostrarAlerta("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
     private int obtenerPosicionRol(int idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (idRol == roles.get(i).getIdRol())
                return i;
        }
        return 0;
    }
}
