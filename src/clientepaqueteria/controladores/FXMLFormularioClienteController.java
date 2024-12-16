package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.ClienteTabla;
import clientepaqueteria.pojo.Direccion;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private PasswordField tfContraseña;
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

    public void InicializarValores(INotificadorOperacion observador, Cliente clienteEdicion) {
        this.observador = observador;
        this.clienteEdicion = clienteEdicion;
        if (clienteEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion(clienteEdicion);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Cliente");
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
    // Obtener los valores de los campos del formulario
    
    String nombre = tfNombre.getText();
    String apellidoPaterno = tfApellidoPaterno.getText();
    String apellidoMaterno = tfApellidoMaterno.getText();
    String correo = tfCorreo.getText();
    String telefono = tfTelefono.getText();
    
    Cliente cliente = new Cliente();
    cliente.setNombre(nombre);
    cliente.setApellidoPaterno(apellidoPaterno);
    cliente.setApellidoMaterno(apellidoMaterno);
    cliente.setCorreo(correo);
    cliente.setTelefono(telefono);
    
    String calle = tfCalle.getText();
    String colonia = tfColonia.getText();
    
    String tfCodigoPText = tfCodigoP.getText();
    if (tfCodigoPText.isEmpty() || tfCodigoPText.length() != 5 || !tfCodigoPText.matches("\\d+")) {
        Utilidades.mostrarAlerta("Error", "Ingrese un código postal válido.", Alert.AlertType.ERROR);
        return;
    }
    String tfNumeroCasaText = tfNumeroCasa.getText();
    if (tfNumeroCasaText.isEmpty() || Integer.parseInt (tfNumeroCasaText) <= 0) {
        Utilidades.mostrarAlerta("Error", "Ingrese un numero de casa válido, de preferencia que sea valor positivo.", Alert.AlertType.ERROR);
        return;
    }
    try{
        int numeroCasa = Integer.parseInt(tfNumeroCasa.getText());
        int codigoPostal = Integer.parseInt(tfCodigoP.getText());
        
    Direccion direccion = new Direccion();
    direccion.setCalle(calle);
    direccion.setColonia(colonia);
    direccion.setNumero(numeroCasa);
    direccion.setCodigoPostal(codigoPostal);
    List<Direccion> direcciones = new ArrayList<>();
    direcciones.add(direccion);
    
    if (sonCamposValidos(cliente)) {
        if (!modoEdicion) {
            guardarDatosCliente(cliente, direccion); // Si es nuevo, guarda
        } else {
            cliente.setIdCliente(clienteEdicion.getIdCliente()); // Para la edición, usa el ID
            editarDatosCliente(cliente,direcciones); // Llama al método para editar
        }
        
    }
    }catch (NumberFormatException e){
        Utilidades.mostrarAlerta("No es posible", "Ingrese un numero válido.", Alert.AlertType.WARNING);
    }
}



    private boolean sonCamposValidos(Cliente cliente) {
        int longitud = cliente.getCodigoPostal().length();
    // Validar que tfNombre sea válido
    if (!cliente.getNombre().matches("^[a-zA-Z\\s]+$")) {
        Utilidades.mostrarAlerta("Error", "Nombre debe contener solo letras.", Alert.AlertType.ERROR);
        return false;
    }

    // Validar que tfApellidoPaterno sea válido
    if (!cliente.getApellidoPaterno().matches("^[a-zA-Z\\s]+$")) {
        Utilidades.mostrarAlerta("Error", "Apellido Paterno debe contener solo letras.", Alert.AlertType.ERROR);
        return false;
    }

    // Validar que tfApellidoMaterno sea válido
    if (!cliente.getApellidoMaterno().matches("^[a-zA-Z\\s]+$")) {
        Utilidades.mostrarAlerta("Error", "Apellido Materno debe contener solo letras.", Alert.AlertType.ERROR);
        return false;
    }

    // Validar que tfCorreo sea válido
    if (!cliente.getCorreo().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
        Utilidades.mostrarAlerta("Error", cliente.getCorreo(), Alert.AlertType.ERROR);
        return false;
    }
    return true;
}


    private void guardarDatosCliente(Cliente cliente, Direccion direccion) {
        Mensaje respuesta = ClienteDAO.guardarDatosCliente(cliente, direccion);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Cliente registrado correctamente", Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", cliente.getNombre());
            btnCancelar(null);
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarDatosCliente(Cliente cliente, List <Direccion> direcciones) {
        Mensaje respuesta = ClienteDAO.editarDatosCliente(cliente, direcciones);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Cliente editado correctamente", Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Edición", cliente.getNombre());
            btnCancelar(null);
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
}
