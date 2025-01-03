package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.UnidadDAO;
import clientepaqueteria.pojo.RespuestaUnidad;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLFormularioUnidadController implements Initializable {

    private Unidad unidad;
    private boolean modoEdicion = false;
    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    private INotificadorOperacion observador;

    @FXML
    private Pane paneContenedorFormularioUnidad;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfNoIdentificacionInterno;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfNoIdentificacionVehicular;
    @FXML
    private ComboBox<String> cbTipoUnidad;
    @FXML
    private TextField tfAnio;
    @FXML
    private Label lbErrorMarca;
    @FXML
    private Label lbErrorVin;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorNumeroInterno;
    @FXML
    private Label lbErrorAño;
    @FXML
    private Label lbErrorTipoUnidad;
    @FXML
    private Label lbNumeroInterno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarCombo();
        tfNoIdentificacionVehicular.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Convierte el texto a mayúsculas y lo asigna al TextField
                tfNoIdentificacionVehicular.setText(newValue.toUpperCase());
            }
        });
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

    void inicializarValores(INotificadorOperacion observador, Unidad unidad) {
        this.observador = observador;
        this.unidad = unidad;
        if (unidad != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        } else {
            tfNoIdentificacionInterno.setVisible(false);
            lbNumeroInterno.setVisible(false);
        }
    }

    private void cargarDatosEdicion() {
        tfMarca.setText(this.unidad.getMarca());
        tfModelo.setText(this.unidad.getModelo());
        tfNoIdentificacionInterno.setText(this.unidad.getNumeroInterno());
        cbTipoUnidad.setValue(this.unidad.getTipoUnidad());
        tfAnio.setText(this.unidad.getAño().toString());
        tfNoIdentificacionVehicular.setText(this.unidad.getVin());
        tfNoIdentificacionVehicular.setDisable(true);
        tfNoIdentificacionInterno.setDisable(true);
    }

    public void inicializarCombo() {
        cbTipoUnidad.getItems().addAll("Gasolina", "Diesel", "Electrica", "Hibrida");

    }

    @FXML
    private void btnGuardarFormularioUnidad(ActionEvent event) {

        Unidad unidad = new Unidad();
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        if (!tfAnio.getText().isEmpty()) {
            int año = Integer.parseInt(tfAnio.getText());
            unidad.setAño(año);
        }

        String vin = tfNoIdentificacionVehicular.getText();
        String numeroInterno = tfNoIdentificacionInterno.getText();
        String situacion = "Activo";
        String tipoUnidad = cbTipoUnidad.getValue();

        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setVin(vin);
        unidad.setNumeroInterno(numeroInterno);
        unidad.setSituacion(situacion);
        unidad.setTipoUnidad(tipoUnidad);

        if (sonCamposValidos(unidad)) {
            if (!modoEdicion) {
                boolean respuesta = Utilidades.mostrarConfirmacion("Guardar", "¿Confirmas registrar la unidad?");
                if (respuesta) {
                    guardarDatosUnidad(unidad);
                }
            } else {
                boolean respuesta = Utilidades.mostrarConfirmacion("Modificar", "¿Confirmas guardar los cambios de la unidad?");
                if (respuesta) {
                    int idUnidad = this.unidad.getIdUnidad();
                    unidad.setIdUnidad(idUnidad);
                    unidad.setIdColaborador(this.unidad.getIdColaborador());
                    editarDatosUnidad(unidad);
                }
            }
        } else {

        }

    }

    @FXML
    private void btnCancelarFormularioUnidad(ActionEvent event) {

        boolean respuesta = Utilidades.mostrarConfirmacion("Confirmar", "Si cancelas perderás los cambios no guardados");
        if (respuesta) {
            stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
            Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Unidades");
        }
    }

    private void guardarDatosUnidad(Unidad unidad) {

        RespuestaUnidad msj = UnidadDAO.registrarUnidad(unidad);
        if (!msj.isError()) {
            Utilidades.mostrarAlerta("Unidad registrada", "La información de la unidad " + unidad.getModelo() + " se registró correctamente, su numero interno es: " + msj.getUnidad().getNumeroInterno() + "\n\nEl numero interno será permanente incluso modificando la información de esta unidad", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Guardar", msj.getUnidad().getNumeroInterno());
        } else {
            Utilidades.mostrarAlerta("Error al guardar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarDatosUnidad(Unidad unidad) {
        RespuestaUnidad msj = UnidadDAO.modificar(unidad);
        if (!msj.isError()) {
            Utilidades.mostrarAlerta("Unidad actualizada", "La informacion ha sido actualizada correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();

            observador.notificarOperacionExitosa("Actualizar", unidad.getNumeroInterno());
        } else {
            Utilidades.mostrarAlerta("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void cerrarVentana() {
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Unidades");
    }

    private boolean sonCamposValidos(Unidad unidad) {

        lbErrorMarca.setText("");
        lbErrorModelo.setText("");
        lbErrorAño.setText("");
        lbErrorVin.setText("");
        lbErrorNumeroInterno.setText("");
        lbErrorTipoUnidad.setText("");

        boolean bandera = true;
        if (unidad.getMarca().isEmpty()) {
            lbErrorMarca.setText("El campo es obligatorio");
            bandera = false;

        }
        if (unidad.getModelo().isEmpty()) {
            lbErrorModelo.setText("El campo es obligatorio");
            bandera = false;
        }

        if (unidad.getAño() == null) {
            lbErrorAño.setText("El campo es obligatorio");
            bandera = false;
        } else if (unidad.getAño() < 1000 || unidad.getAño() > 9999) {
            lbErrorAño.setText("El año debe tener exactamente 4 dígitos (por ejemplo, 2024)");
            bandera = false;
        }
        
        if (unidad.getVin().isEmpty() || unidad.getVin().length() != 17) {
            lbErrorVin.setText("El campo requiere 17 digitos, tiene: "+unidad.getVin().length());
            bandera = false;
        }

        if (unidad.getTipoUnidad() == null) {
            lbErrorTipoUnidad.setText("El campo es obligatorio");
            bandera = false;
        }

        return bandera;

    }

}
