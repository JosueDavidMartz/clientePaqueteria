package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.modelo.dao.ConductorDAO;
import clientepaqueteria.modelo.dao.UnidadDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FXMLAsignarUnidadController implements Initializable {

    private Unidad unidad;
    private INotificadorOperacion observador;

    @FXML
    private ComboBox<Colaborador> cbNoPersonal;
    @FXML
    private Label lbNoVehiculo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBoxColaborador();
        cargarDatosColaborador();
    }

    public void InicializarValores(INotificadorOperacion observador, Unidad unidad) {
        this.observador = observador;
        this.unidad = unidad;
        lbNoVehiculo.setText(unidad.getNumeroInterno());
    }

    private void configurarComboBoxColaborador() {
        cbNoPersonal.setCellFactory(lv -> new ListCell<Colaborador>() {
            @Override
            protected void updateItem(Colaborador colaborador, boolean empty) {
                super.updateItem(colaborador, empty);
                if (empty || colaborador == null) {
                    setText(null);
                } else {
                    setText(colaborador.getNumeroPersonal() + " - " + colaborador.getNombre());
                }
            }
        });

        cbNoPersonal.setButtonCell(new ListCell<Colaborador>() {
            @Override
            protected void updateItem(Colaborador colaborador, boolean empty) {
                super.updateItem(colaborador, empty);
                if (empty || colaborador == null) {
                    setText(null);
                } else {
                    setText(colaborador.getNumeroPersonal() + " - " + colaborador.getNombre());
                }
            }
        });
    }

    private void cargarDatosColaborador() {
        List<Colaborador> listaColaboradores = ColaboradorDAO.obtenerConductores();
        if (listaColaboradores != null) {
            // Filtrar los colaboradores cuyo estatus sea 'Activo'
            List<Colaborador> colaboradoresActivos = listaColaboradores.stream()
                    .filter(colaborador -> "Activo".equals(colaborador.getEstatus()))
                    .collect(Collectors.toList()); // Cambiado a Collectors.toList()

            ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList(colaboradoresActivos);
            cbNoPersonal.setItems(colaboradores);
        }
    }

    @FXML
    private void btnAsociar(ActionEvent event) {
        String noPersonal = cbNoPersonal.getValue() != null ? cbNoPersonal.getValue().getNumeroPersonal() : null;
        String numeroUnidad = unidad.getNumeroInterno();
        lbNoVehiculo.setText(numeroUnidad);

        if (noPersonal != null && numeroUnidad != null) {

            Mensaje respuestaAsociado = ConductorDAO.obtenerConductorAsociado(noPersonal);
            
            if (!respuestaAsociado.isError()) {
               
                boolean confirmarAsociacion = Utilidades.mostrarConfirmacion("Cambiar vehículo",
                        "El conductor ya tiene una unidad asociada, ¿Deseas cambiar su unidad por esta otra?");
                if (confirmarAsociacion) {
                    Mensaje respuesta = ConductorDAO.asociarUnidad(noPersonal, numeroUnidad);

                    if (respuesta.isError()) {
                        Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
                    } else {
                        Utilidades.mostrarAlerta("Éxito", "Unidad actualizada exitosamente.", Alert.AlertType.INFORMATION);
                        if (observador != null) {
                            observador.notificarOperacionExitosa("Asociación", numeroUnidad);
                        }
                        cerrarVentana(event);
                        return;
                    }
                } else {
                    return;
                }
            }

            Mensaje respuesta = ConductorDAO.asociarUnidad(noPersonal, numeroUnidad);

            if (respuesta.isError()) {
                Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
            } else {
                Utilidades.mostrarAlerta("Éxito", "Unidad asociada exitosamente.", Alert.AlertType.INFORMATION);
                if (observador != null) {
                    observador.notificarOperacionExitosa("Asociación", numeroUnidad);
                }
                cerrarVentana(event);
            }
        } else {
            Utilidades.mostrarAlerta("Advertencia", "Debe seleccionar un colaborador y una unidad.", Alert.AlertType.WARNING);
        }
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnCancelarAsociacion(ActionEvent event) {
        cerrarVentana(event);
    }
}
