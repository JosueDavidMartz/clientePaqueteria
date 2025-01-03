package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.modelo.dao.EnvioDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaEnvio;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author WIN 10
 */
public class FXMLFormularioEnvioController implements Initializable {

    private Colaborador colaborador;
    private Map<String, Cliente> clienteMap = new HashMap<>();
    private Map<String, Colaborador> conductorMap = new HashMap<>();
    private INotificadorOperacion observador;
    private ResultadoObtenerEnvio envio;
    private boolean modoEdicion = false;

    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;

    @FXML
    private TextField tfCalleDestino;
    @FXML
    private TextField tfColoniaDestino;
    @FXML
    private TextField tfNumeroDestino;
    @FXML
    private TextField tfCodiigoPostalDestino;
    @FXML
    private TextField tfEstadoDestino;
    @FXML
    private TextField tfCiudadDestino;
    private Pane paneVincularEnvio;
    @FXML
    private Pane paneNumeroGuia;
    @FXML
    private Label lbNumeroGuia;
    @FXML
    private ImageView ivCopiarNumeroGuia;
    @FXML
    private ComboBox<String> cbConductor;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private ComboBox<String> tfClienteFormularioEnvio;
    @FXML
    private TextField tfCosto;
    @FXML
    private Label lbCosto;
    @FXML
    private Label lbCopiado;
    @FXML
    private Label lbErrorCliente;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private Label lbErrorCiudad;
    @FXML
    private Label lbErrorCosto;
    @FXML
    private Label lbErrorEstado;
    @FXML
    private Label lbErrorNumero;
    @FXML
    private Label lbErrorCodigoPostal;
    @FXML
    private Label lbDireccionOrigen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        obtenerClientes();
        obtenerConductores();
        cargarDireccionOrigen();
    }

    public void recibirConfiguracion(Colaborador colaborador, HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.colaborador = colaborador;
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    void inicializarValores(INotificadorOperacion observador, ResultadoObtenerEnvio envio) {

        this.observador = observador;
        this.envio = envio;

        if (envio != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        }

    }

    private void cargarDireccionOrigen() {
        String infoOrigen
                = "Calle: " + "Carranza" + ", "
                + "Número: " + "32" + ", "
                + "Colonia: " + "Benito Juarez" + ", "
                + "Código Postal: " + "20900" + ", "
                + "Ciudad: " + "México" + ", "
                + "Estado: " + "México";
       // taDireccionOrigen.setText(infoOrigen);
        //taDireccionOrigen.setDisable(true);
        lbDireccionOrigen.setText(infoOrigen);

        //cbConductor.setDisable(true);
        cbEstado.setDisable(true);
        tfCosto.setVisible(false);
        lbCosto.setVisible(false);
    }

    private void cargarDatosEdicion() {
        label.setText("Editar envio");
        cbConductor.setDisable(false);
        cbEstado.setDisable(false);
        tfCosto.setVisible(true);
        lbCosto.setVisible(true);
        cbEstado.getItems().addAll("Pendiente", "En tránsito", "Detenido", "Entregado", "Cancelado");
        cbEstado.setValue(this.envio.getNombreSeguimiento());
        cbConductor.setValue(this.envio.getNombreConductor());

        tfCalleDestino.setText(this.envio.getCalleDestino());
        tfNumeroDestino.setText(this.envio.getNumeroDestino().toString());
        tfCodiigoPostalDestino.setText(this.envio.getCodigoPostalDestino().toString());
        tfColoniaDestino.setText(this.envio.getColoniaDestino());
        tfEstadoDestino.setText(this.envio.getEstadoDestino());
        tfCiudadDestino.setText(this.envio.getCiudadDestino());
        tfClienteFormularioEnvio.setValue(this.envio.getNombreCliente());
        if (this.envio != null && this.envio.getCostoEnvio() != null) {
            tfCosto.setText(this.envio.getCostoEnvio().toString());
        } else {
            tfCosto.setText("");
        }

    }

    void setStackPane(StackPane spEscena) {
        this.stackPane = spEscena;
    }

    public void obtenerClientes() {
        List<Cliente> listaClientes = ClienteDAO.obtenerClientes();
        if (listaClientes != null) {
            for (Cliente cliente : listaClientes) {
                // Filtrar clientes con estatus 'Activo'
                if ("Activo".equals(cliente.getEstatus())) {
                    tfClienteFormularioEnvio.getItems().add(cliente.getNombre());
                    clienteMap.put(cliente.getNombre(), cliente);
                }
            }
        }
    }

    public void obtenerConductores() {
        List<Colaborador> listaConductores = ColaboradorDAO.obtenerConductores();
        if (listaConductores != null) {
            for (Colaborador colaborador : listaConductores) {
                // Filtrar conductores con estatus 'Activo'
                if ("Activo".equals(colaborador.getEstatus())) {
                    cbConductor.getItems().add(colaborador.getNombre());
                    conductorMap.put(colaborador.getNombre(), colaborador);
                }
            }
        }
    }

    public boolean validarCampos() {
        boolean valido = true;

        // Validar Ciudad (solo letras y no vacío)
        if (tfCiudadDestino.getText() == null || tfCiudadDestino.getText().trim().isEmpty()) {
            lbErrorCiudad.setText("El campo Ciudad no puede estar vacío.");
            valido = false;
        } else if (!tfCiudadDestino.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            lbErrorCiudad.setText("La ciudad debe contener solo letras.");
            valido = false;
        } else {
            lbErrorCiudad.setText(""); // Limpiar mensaje de error si es válido
        }

        // Validar Estado (solo letras y no vacío)
        if (tfEstadoDestino.getText() == null || tfEstadoDestino.getText().trim().isEmpty()) {
            lbErrorEstado.setText("El campo Estado no puede estar vacío.");
            valido = false;
        } else if (!tfEstadoDestino.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            lbErrorEstado.setText("El estado debe contener solo letras.");
            valido = false;
        } else {
            lbErrorEstado.setText(""); // Limpiar mensaje de error si es válido
        }

        // Validar Colonia (letras, números y no vacío)
        if (tfColoniaDestino.getText() == null || tfColoniaDestino.getText().trim().isEmpty()) {
            lbErrorColonia.setText("El campo Colonia no puede estar vacío.");
            valido = false;
        } else if (!tfColoniaDestino.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s]+")) {
            lbErrorColonia.setText("La colonia debe contener solo letras y números.");
            valido = false;
        } else {
            lbErrorColonia.setText(""); // Limpiar mensaje de error si es válido
        }

        // Validar Calle (letras, números y no vacío)
        if (tfCalleDestino.getText() == null || tfCalleDestino.getText().trim().isEmpty()) {
            lbErrorCalle.setText("El campo Calle no puede estar vacío.");
            valido = false;
        } else if (!tfCalleDestino.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s]+")) {
            lbErrorCalle.setText("La calle debe contener solo letras y números.");
            valido = false;
        } else {
            lbErrorCalle.setText(""); // Limpiar mensaje de error si es válido
        }

        // Validar Número (solo números y no vacío)
        if (tfNumeroDestino.getText() == null || tfNumeroDestino.getText().trim().isEmpty()) {
            lbErrorNumero.setText("El campo Número no puede estar vacío.");
            valido = false;
        } else if (!tfNumeroDestino.getText().matches("\\d+")) {
            lbErrorNumero.setText("El número debe contener solo dígitos.");
            valido = false;
        } else {
            lbErrorNumero.setText(""); // Limpiar mensaje de error si es válido
        }

        // Validar Código Postal (solo números y no vacío)
        if (tfCodiigoPostalDestino.getText() == null || tfCodiigoPostalDestino.getText().trim().isEmpty()) {
            lbErrorCodigoPostal.setText("El campo no puede estar vacío.");
            valido = false;
        } else if (!tfCodiigoPostalDestino.getText().matches("\\d{5}")) {
            lbErrorCodigoPostal.setText("Debe contener solo 5 dígitos.");
            valido = false;
        } else {
            lbErrorCodigoPostal.setText(""); // Limpiar mensaje de error si es válido
        }
        
        if (tfClienteFormularioEnvio.getValue() == null) {
            lbErrorCliente.setText("Debes seleccionar un cliente"); 
            valido = false;
        }else{
            lbErrorCliente.setText("");
        }

        return valido;
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        boolean respuesta = Utilidades.mostrarConfirmacion("Confirmar", "Si cancelas perderás los cambios no guardados");
        if (respuesta) {
            paneNumeroGuia.setVisible(false);
            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envios");
        }
    }

    private boolean validarCamposVacios() {
        // Arreglo de campos de texto a validar
        TextField[] campos = {
            tfCalleDestino, tfColoniaDestino, tfEstadoDestino,
            tfCiudadDestino, tfNumeroDestino, tfCodiigoPostalDestino
        };

        // Validar que los campos de texto no estén vacíos
        for (TextField campo : campos) {
            if (campo.getText() == null || campo.getText().trim().isEmpty()) {
                return false;
            }
        }

        // Validar que el ComboBox tenga un valor seleccionado
        if (tfClienteFormularioEnvio.getValue() == null) {
            return false;
        }

        return true; // Todos los campos son válidos
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        // if (validarCamposVacios()) {//QUITAR LA ALERTA DE CAMPOS VACIOS CUANDO LOS CAMPOS SON INCORRECTOS PERO NO VACIOS
        if (validarCampos()) {
            Envio envioNuevo = construirEnvioDesdeFormulario();

            if (!modoEdicion) {
                crearEnvio(envioNuevo);
            } else {
                modificarEnvio(envioNuevo);
            }
        }

        /*} else {
            Utilidades.mostrarAlerta("Campos vacíos", "Asegúrate de llenar todos los campos", Alert.AlertType.INFORMATION);
        }*/
    }

    private Envio construirEnvioDesdeFormulario() {
        String nombreSeleccionado = tfClienteFormularioEnvio.getValue();
        int idCliente = 0;

        if (nombreSeleccionado != null) {
            Cliente clienteSeleccionado = clienteMap.get(nombreSeleccionado);
            if (clienteSeleccionado != null) {
                idCliente = clienteSeleccionado.getIdCliente();
            }
        }

        Envio envio = new Envio();
        envio.setIdCliente(idCliente);
        envio.setIdColaborador(colaborador.getIdColaborador());

        envio.getDireccionDestino().setCalle(tfCalleDestino.getText());
        envio.getDireccionDestino().setNumero(Integer.parseInt(tfNumeroDestino.getText()));
        envio.getDireccionDestino().setColonia(tfColoniaDestino.getText());
        envio.getDireccionDestino().setEstado(tfEstadoDestino.getText());
        envio.getDireccionDestino().setCiudad(tfCiudadDestino.getText());
        envio.getDireccionDestino().setCodigoPostal(Integer.parseInt(tfCodiigoPostalDestino.getText()));

        envio.getSeguimiento().setIdColaborador(colaborador.getIdColaborador());
        envio.getSeguimiento().setNombre("Pendiente");
        envio.getSeguimiento().setFecha(LocalDate.now().toString());
        envio.getSeguimiento().setHora(LocalTime.now().toString());
        int idConductor = 0;

        String nombreConductorSeleccionado = cbConductor.getValue();
        if (nombreConductorSeleccionado != null) {
            Colaborador colaboradorSeleccionado = conductorMap.get(nombreConductorSeleccionado);
            if (colaboradorSeleccionado != null) {
                idConductor = colaboradorSeleccionado.getIdColaborador();
            }
        }

        envio.setIdConductor(idConductor);

        return envio;
    }

    private void crearEnvio(Envio envioNuevo) {
        envioNuevo.setDireccionOrigen(Utilidades.direccionOrigen());
        envioNuevo.getDireccionDestino().setTipo("Destino");

        RespuestaEnvio resultado = EnvioDAO.crearEnvio(envioNuevo);

        if (!resultado.isError()) {
            Utilidades.mostrarAlerta("Creado", "El envío fue creado con éxito", Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Crear", resultado.getNumeroGuia());
            lbNumeroGuia.setText(resultado.getNumeroGuia());
            paneNumeroGuia.setVisible(true);

            limpiarFormulario();

        } else {
            Utilidades.mostrarAlerta("Error al crear envío", resultado.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void modificarEnvio(Envio envioNuevo) {
        envioNuevo.setIdEnvio(this.envio.getIdEnvio());
        envioNuevo.setIdColaborador(this.envio.getIdColaborador());
        envioNuevo.getSeguimiento().setNombre(cbEstado.getValue());
        if (tfCosto.getText() == null || tfCosto.getText().trim().isEmpty()) {
            envioNuevo.setCostoEnvio(0f);
        } else {
            float costo = Float.parseFloat(tfCosto.getText());
            envioNuevo.setCostoEnvio(costo);

        }

        envioNuevo.setDireccionOrigen(Utilidades.direccionOrigen());
        envioNuevo.getDireccionDestino().setTipo("Destino");
        int idConductor = 0;

        String nombreSeleccionado = cbConductor.getValue();
        if (nombreSeleccionado != null) {
            Colaborador colaboradorSeleccionado = conductorMap.get(nombreSeleccionado);
            if (colaboradorSeleccionado != null) {
                idConductor = colaboradorSeleccionado.getIdColaborador();
            }
        }

        envioNuevo.setIdConductor(idConductor);
        RespuestaEnvio msj = EnvioDAO.modificar(envioNuevo);
        Mensaje msjSeguimiento = EnvioDAO.asignarSeguimiento(envioNuevo);

        if (!msj.isError()) {
            Utilidades.mostrarAlerta("Actualización exitosa", "La información ha sido actualizada correctamente\n" + msjSeguimiento.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Actualizar", envio.getNumeroGuia());
        } else {
            Utilidades.mostrarAlerta("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarFormulario() {
        tfCalleDestino.setText("");
        tfNumeroDestino.setText("");
        tfColoniaDestino.setText("");
        tfCodiigoPostalDestino.setText("");
        tfEstadoDestino.setText("");
        tfCiudadDestino.setText("");
        tfClienteFormularioEnvio.setValue(null);
    }

    private void btnAceptarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    private void btnCancelarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    @FXML
    private void ivCopiarNumeroGuia(MouseEvent event) {

        String texto = lbNumeroGuia.getText();

        if (texto != null && !texto.isEmpty()) {
            // Obtener el portapapeles del sistema
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(texto);  // Colocar el texto en el portapapeles
            clipboard.setContent(content);  // Establecer el contenido en el portapapeles
            lbCopiado.setVisible(true);

        }
    }

    @FXML
    private void btnAceptarNumeroGuia(ActionEvent event) {
        paneNumeroGuia.setVisible(false);
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envio");
    }

    private void cerrarVentana() {

        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envios");

    }

    private void released(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().remove("pressed");
    }

    private void exited(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().remove("hover");
    }

    private void entered(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().add("hover");
    }

    private void pressed(MouseEvent event) {
        Button source = (Button) event.getSource();
        source.getStyleClass().add("pressed");
    }

    @FXML
    private void pressedCopiarGuia(MouseEvent event) {
        // Aplica un efecto visual verde al dar clic
        ivCopiarNumeroGuia.setStyle("-fx-effect: innershadow(gaussian, rgba(0, 255, 0, 0.8), 20, 0.5, 0, 0);");
    }

    @FXML
    private void releasedCopiarGuia(MouseEvent event) {
        ivCopiarNumeroGuia.setStyle("-fx-effect: null;");
    }

    @FXML
    private void enteredCopiarGuia(MouseEvent event) {
        ivCopiarNumeroGuia.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0, 0); -fx-opacity: 1.0;");
    }

    @FXML
    private void exitedCopiarGuia(MouseEvent event) {
        ivCopiarNumeroGuia.setStyle("-fx-effect: null; -fx-opacity: 1.0;");
    }

}
