package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.UnidadDAO;
import clientepaqueteria.pojo.RespuestaUnidad;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLUnidadesController implements Initializable, INotificadorOperacion {

    private ObservableList<Unidad> unidades;
    private FilteredList<Unidad> listaUnidades;
    private Unidad unidad;
    private INotificadorOperacion observador;

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    Unidad unidadBaja;

    @FXML
    private Pane paneBajaUnidad;
    @FXML
    private TextArea taMotivo;
    @FXML
    private Label lbErrorMotivo;
    @FXML
    private TableView<Unidad> tvTablaUnidades;
    @FXML
    private TableColumn tcNumeroInterno;
    @FXML
    private TableColumn tcMarca;
    @FXML
    private TableColumn tcModelo;
    @FXML
    private TableColumn tcTipoUnidad;
    @FXML
    private TableColumn tcNoIdentificacionV;
    @FXML
    private TableColumn tcAnio;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn tcConductor;
    @FXML
    private TableColumn tcNoPersonal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        configurarFiltro();
    }

    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    @FXML
    private void btnAñadirUnidad(ActionEvent event) {
        irFormularioUnidades(this, null);
    }

    @FXML
    private void btnModificarUnidad(ActionEvent event) {
        Unidad unidad = tvTablaUnidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            irFormularioUnidades(this, unidad);
        } else {
            Utilidades.mostrarAlerta("Sin selección", "Selecciona una unidad para poder editar", Alert.AlertType.WARNING);
        }
    }

    private void irFormularioUnidades(INotificadorOperacion observador, Unidad unidad) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar unidad");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioUnidad.fxml"));
            Parent formularioUnidad = loader.load();
            FXMLFormularioUnidadController controlador = loader.getController();
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);
            controlador.inicializarValores(observador, unidad);
            spEscena.getChildren().add(formularioUnidad);
        } catch (IOException e) {
            Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    @FXML
    private void btnEliminarUnidad(ActionEvent event) {
        unidadBaja = tvTablaUnidades.getSelectionModel().getSelectedItem();
        if (unidadBaja != null) {
            paneBajaUnidad.setVisible(true);
        } else {
            Utilidades.mostrarAlerta("Sin selección", "Selecciona la unidad que quieres dar de baja", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnAceptarBaja(ActionEvent event) {
        if (taMotivo.getText().trim().isEmpty()) {
            lbErrorMotivo.setText("Se requiere registrar el motivo de la baja");
        } else {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                paneBajaUnidad.setVisible(false);
                unidadBaja.setMotivo(taMotivo.getText());
                taMotivo.setText("");
                lbErrorMotivo.setText("");
                RespuestaUnidad unidadRespuesta = UnidadDAO.darBajaUnidad(unidadBaja);
                if (!unidadRespuesta.isError()) {
                    Utilidades.mostrarAlerta("Eliminado", "Se eliminó la unidad: " + unidadBaja.getNumeroInterno(), Alert.AlertType.INFORMATION);
                } else {
                    Utilidades.mostrarAlerta("Error", "No se pudo eliminar la unidad: " + unidadRespuesta.getMensaje(), Alert.AlertType.ERROR);
                }
            } else {
                Utilidades.mostrarAlerta("Operación cancelada", "La unidad seguirá disponible", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    private void btnCancelarBaja(ActionEvent event) {
        paneBajaUnidad.setVisible(false);
        taMotivo.setText("");
        lbErrorMotivo.setText("");
    }

    private void configurarTabla() {
        tcNumeroInterno.setCellValueFactory(new PropertyValueFactory<>("numeroInterno"));
        tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tcTipoUnidad.setCellValueFactory(new PropertyValueFactory<>("tipoUnidad"));
        tcNoIdentificacionV.setCellValueFactory(new PropertyValueFactory<>("vin"));
        tcAnio.setCellValueFactory(new PropertyValueFactory<>("año"));
    }

    private void cargarInformacionTabla() {
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadDAO.obtenerColaboradores();
        if (listaWS != null) {
            unidades.addAll(listaWS);
            tvTablaUnidades.setItems(unidades);
        } else {
            Utilidades.mostrarAlerta("Datos no disponibles", "Lo sentimos por el momento no se puede cargar la lista de las unidades", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformacionTabla();
    }

    private void configurarFiltro() {
        listaUnidades = new FilteredList<>(unidades, b -> true);
        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            listaUnidades.setPredicate(unidad -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (unidad.getNumeroInterno() != null && unidad.getNumeroInterno().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (unidad.getVin() != null && unidad.getVin().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (unidad.getMarca() != null && unidad.getMarca().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Unidad> sortedData = new SortedList<>(listaUnidades);
        sortedData.comparatorProperty().bind(tvTablaUnidades.comparatorProperty());
        tvTablaUnidades.setItems(sortedData);
    }

    @FXML
    private void btnQuitarVehiculo(ActionEvent event) {
    }

    @FXML
    private void btnAsignarVehiculo(ActionEvent event) {
    }
}
