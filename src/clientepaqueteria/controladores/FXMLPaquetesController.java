package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.PaqueteDAO;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Paquete;
import clientepaqueteria.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLPaquetesController implements Initializable, INotificadorOperacion {

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    private ScrollPane scrollPane;
    private ObservableList<Paquete> paquetes;
    private FilteredList<Paquete> listaPaquetes;

    @FXML
    private Pane paneContenedorPaquetes;
    @FXML
    private TableView<Paquete> tvPaquetes;
    @FXML
    private TableColumn<Paquete, String> tcDescripcion;
    @FXML
    private TableColumn<Paquete, Double> tcPeso;
    @FXML
    private TableColumn<Paquete, Double> tcAncho;
    @FXML
    private TableColumn<Paquete, Double> tcAlto;
    @FXML
    private TableColumn<Paquete, Double> tcProfundidad;
    @FXML
    private TableColumn<Paquete, String> tcEnvio;
    @FXML
    private TextField tfBuscarPaquete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónTabla();
        configurarFiltro();
    }

    private void configurarTabla() {

        tcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tcPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        tcAncho.setCellValueFactory(new PropertyValueFactory<>("dimensionAncho"));
        tcAlto.setCellValueFactory(new PropertyValueFactory<>("dimensionAlto"));
        tcProfundidad.setCellValueFactory(new PropertyValueFactory<>("dimensionProfundidad"));
        tcEnvio.setCellValueFactory(new PropertyValueFactory<>("numeroGuia"));

    }

    private void cargarInformaciónTabla() {

        paquetes = FXCollections.observableArrayList();

        List<Paquete> listaWS = PaqueteDAO.obtenerPaquetes();
        if (listaWS != null && !listaWS.isEmpty()) {
            paquetes.addAll(listaWS);
            tvPaquetes.setItems(paquetes);
        } else {
           // Utilidades.mostrarAlerta("Sin registros",
             //       "No seencontraron registros de paquetes",
               //     Alert.AlertType.INFORMATION);
        }
    }

    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    @FXML
    private void btnModificarPaquete(ActionEvent event) {

        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();

        if (paquete != null) {
            Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Editar paquetes");
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioPaquetes.fxml"));
                Parent formularioPaquetes = loader.load();

                FXMLFormularioPaquetesController controlador = loader.getController();
                controlador.InicializarValores(this, paquete);

                controlador.setStackPane(spEscena);
                controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

                spEscena.getChildren().add(formularioPaquetes);
            } catch (IOException e) {
                Logger.getLogger(FXMLPaquetesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
            }
        } else {
            Utilidades.mostrarAlerta("Seleccionar Paquete", "Para poder editar debes seleccionar un paquete", Alert.AlertType.WARNING);
        }

    }

    @FXML
    private void btnAgregarPaquete(ActionEvent event) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar paquetes");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioPaquetes.fxml"));
            Parent formularioPaquetes = loader.load();

            FXMLFormularioPaquetesController controlador = loader.getController();
            controlador.InicializarValores(this, null);

            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

            spEscena.getChildren().add(formularioPaquetes);
        } catch (IOException e) {
            Logger.getLogger(FXMLPaquetesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    @FXML
    private void btnEliminarPaquete(ActionEvent event) {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        if (paquete != null) {
            boolean seElimina = Utilidades.mostrarConfirmacion(
                    "Eliminar paquete",
                    "¿Estás seguro de eliminar el paquete seleccionado? "
                    + "Recuerda que una vez eliminado no se puede recuperar."
            );
            if (seElimina) {

                eliminarPaquete(paquete.getIdPaquete());

                if (paquetes != null) {
                    paquetes.remove(paquete);
                }
            }
        } else {
            Utilidades.mostrarAlerta(
                    "Selecciona Paquete",
                    "Para poder eliminar debes seleccionar un paquete de la tabla.",
                    Alert.AlertType.WARNING
            );
        }
    }

    private void eliminarPaquete(int idPaquete) {

        Mensaje mensaje = PaqueteDAO.eliminarPaquete(idPaquete);
        if (!mensaje.isError()) {
            Utilidades.mostrarAlerta("Paquete eliminado", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlerta("Error al eliminar paquete", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }

    }

    private void configurarFiltro() {

        listaPaquetes = new FilteredList<>(paquetes, b -> true);

        tfBuscarPaquete.textProperty().addListener((observable, oldValue, newValue) -> {
            listaPaquetes.setPredicate(paquete -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (paquete.getNumeroGuia() != null && paquete.getNumeroGuia().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Paquete> sortedData = new SortedList<>(listaPaquetes);
        sortedData.comparatorProperty().bind(tvPaquetes.comparatorProperty());
        tvPaquetes.setItems(sortedData);
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformaciónTabla();
        configurarFiltro();
    }
}
