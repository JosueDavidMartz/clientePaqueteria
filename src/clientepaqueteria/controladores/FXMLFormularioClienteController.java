package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Direccion;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLFormularioClienteController implements Initializable {

    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;

    private boolean modoEdicion = false;
    private INotificadorOperacion observador;
    private Cliente clienteEdicion;
    private List<Direccion> direccionEdicion;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfNumeroCasa;
    @FXML
    private TextField tfCodigoP;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private Label lbErrorCodigoPostal;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private Label lbErrorTelefono;
    @FXML
    private Label lbErrorApellidoPaterno;
    @FXML
    private Label lbErrorApellidoMaterno;
    @FXML
    private Label lbErrorNumeroCasa;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización
    }

    public void InicializarValores(INotificadorOperacion observador, Cliente clienteEdicion, List<Direccion> direccion) {
        this.observador = observador;
        this.clienteEdicion = clienteEdicion;
        this.direccionEdicion = direccion;
        if (clienteEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion(clienteEdicion);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        boolean respuesta = Utilidades.mostrarConfirmacion("Confirmar", "Al salir perderás la información que no se ha guardado");
        if (respuesta) {
            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Clientes");
        }

    }

    private void cargarDatosEdicion(Cliente cliente) {
        tfNombre.setText(this.clienteEdicion.getNombre());
        tfApellidoPaterno.setText(this.clienteEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(this.clienteEdicion.getApellidoMaterno());
        tfCorreo.setText(this.clienteEdicion.getCorreo());
        tfTelefono.setText(this.clienteEdicion.getTelefono());
        tfCalle.setText(this.clienteEdicion.getCalle());
        tfColonia.setText(this.clienteEdicion.getColonia());
        tfNumeroCasa.setText(String.valueOf(this.clienteEdicion.getNumero()));
        tfCodigoP.setText(String.valueOf(this.clienteEdicion.getCodigoPostal()));
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        lbErrorNombre.setText("");
        lbErrorApellidoPaterno.setText("");
        lbErrorApellidoMaterno.setText("");
        lbErrorCorreo.setText("");
        lbErrorTelefono.setText("");
        lbErrorCalle.setText("");
        lbErrorCodigoPostal.setText("");
        lbErrorColonia.setText("");
        lbErrorNumeroCasa.setText("");

        /*String nombre = tfNombre.getText().trim();
        String apellidoPaterno = tfApellidoPaterno.getText().trim();
        String apellidoMaterno = tfApellidoMaterno.getText().trim();
        String correo = tfCorreo.getText().trim();
        String telefono = tfTelefono.getText().trim();
        String calle = tfCalle.getText().trim();
        String colonia = tfColonia.getText().trim();
        String codigoPostalText = tfCodigoP.getText().trim();
        String numeroCasaText = tfNumeroCasa.getText().trim();

        // Validar los campos básicos
        if (nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || correo.isEmpty() || telefono.isEmpty() || calle.isEmpty() || colonia.isEmpty() || codigoPostalText.isEmpty() || numeroCasaText.isEmpty()) {
            Utilidades.mostrarAlerta("Error", "Todos los campos deben ser completados.", Alert.AlertType.ERROR);

        }

        int numeroCasa = Integer.parseInt(numeroCasaText);
        int codigoPostal = Integer.parseInt(codigoPostalText);

        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setColonia(colonia);
        direccion.setNumero(numeroCasa);
        direccion.setCodigoPostal(codigoPostal);

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellidoPaterno(apellidoPaterno);
        cliente.setApellidoMaterno(apellidoMaterno);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        if (modoEdicion) {
            Integer idDireccion = this.direccionEdicion.get(0).getIdDireccion();
            direccion.setIdDireccion(idDireccion != null ? idDireccion : 0);
        }

        List<Direccion> direcciones = new ArrayList<>();
        direcciones.add(direccion);

        // Verificación de campos antes de guardar*/
        if (sonCamposValidos()) {
            Cliente cliente = new Cliente();
            cliente.setNombre(tfNombre.getText());
            cliente.setApellidoPaterno(tfApellidoPaterno.getText());
            cliente.setApellidoMaterno(tfApellidoMaterno.getText());
            cliente.setCorreo(tfCorreo.getText());
            cliente.setTelefono(tfTelefono.getText());

            Direccion direccion = new Direccion();
            direccion.setCalle(tfCalle.getText());
            direccion.setColonia(tfColonia.getText());
            direccion.setNumero(Integer.parseInt(tfNumeroCasa.getText()));
            direccion.setCodigoPostal(Integer.parseInt(tfCodigoP.getText()));

            if (modoEdicion) {
                Integer idDireccion = this.direccionEdicion.get(0).getIdDireccion();
                direccion.setIdDireccion(idDireccion != null ? idDireccion : 0);
            }

            List<Direccion> direcciones = new ArrayList<>();
            direcciones.add(direccion);

                if (!modoEdicion) {

                    guardarDatosCliente(cliente, direccion); // Si es nuevo, guarda
                } else {
                    cliente.setIdCliente(clienteEdicion.getIdCliente()); // Para la edición, usa el ID

                    editarDatosCliente(cliente, direcciones); // Llama al método para editar
                }
        }

    }

    private boolean sonCamposValidos() {
        boolean bandera = true;
        // Validar que tfNombre sea válido
        if (tfNombre.getText().isEmpty() || tfApellidoPaterno.getText().isEmpty() || tfApellidoMaterno.getText().isEmpty() || tfCorreo.getText().isEmpty() || tfTelefono.getText().isEmpty() || tfCalle.getText().isEmpty() || tfColonia.getText().isEmpty() || tfCodigoP.getText().isEmpty() || tfNumeroCasa.getText().isEmpty()) {
            Utilidades.mostrarAlerta("Error", "Todos los campos deben ser completados.", Alert.AlertType.ERROR);

        }
        if (!tfNombre.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            lbErrorNombre.setText("Debe contener solo letras");
            bandera = false;
        }

        if (!tfApellidoPaterno.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            lbErrorApellidoPaterno.setText("Debe contener solo letras");
            bandera = false;
        }

        if (!tfApellidoMaterno.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            lbErrorApellidoMaterno.setText("Debe contener solo letras");
            bandera = false;
        }

        // Validar que tfCorreo sea válido
        if (!tfCorreo.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            lbErrorCorreo.setText("Asegúrate de seguir el formato nombre@dominio");
            bandera = false;
        }

        // Validar que el teléfono sea numérico y con el formato correcto (esto puede variar según el país)
        if (!tfTelefono.getText().matches("^\\d{10}$")) {
            lbErrorTelefono.setText("El teléfono debe tener 10 dígitos");
            bandera = false;
        }

        // Validar el código postal (5 dígitos numéricos)
        String codigoPostal = String.valueOf(tfCodigoP.getText());
        if (codigoPostal.isEmpty() || !codigoPostal.matches("^\\d{5}$")) {
            lbErrorCodigoPostal.setText("Debe contener exactamente 5 dígitos");
            bandera = false;
        }

        // Validar la calle (letras, números y espacios)
        if (!tfCalle.getText().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$")) {
            lbErrorCalle.setText("Debe contener solo letras y números");
            bandera = false;
        }

        // Validar la colonia (letras, números y espacios)
        if (!tfColonia.getText().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$")) {
            lbErrorColonia.setText("Debe contener solo letras y números");
            bandera = false;
        }

        // Validar el número de casa (positivo y no vacío)
        String numeroCasa = String.valueOf(tfNumeroCasa.getText());
        if (numeroCasa.isEmpty() || !numeroCasa.matches("^\\d+$") || Integer.parseInt(numeroCasa) <= 0) {
            lbErrorNumeroCasa.setText("Debe ser un valor numérico positivo");
            bandera = false;
        }

        return bandera;
    }

    private void guardarDatosCliente(Cliente cliente, Direccion direccion) {
        Mensaje respuesta = ClienteDAO.guardarDatosCliente(cliente, direccion);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Cliente registrado correctamente", Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", cliente.getNombre());
            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Clientes");
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarDatosCliente(Cliente cliente, List<Direccion> direcciones) {
        Direccion direccion = direcciones.get(0);

        Mensaje respuesta = ClienteDAO.editarDatosCliente(cliente, direcciones);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Cliente editado correctamente", Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Edición", cliente.getNombre());
            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Clientes");
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
}
