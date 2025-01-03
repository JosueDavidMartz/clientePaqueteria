package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.EnvioDAO;
import clientepaqueteria.modelo.dao.UnidadDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLEnviosController implements Initializable, INotificadorOperacion {

    private ObservableList<ResultadoObtenerEnvio> envios;
    private FilteredList<ResultadoObtenerEnvio> listaEnvios;

    Colaborador colaborador;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    ResultadoObtenerEnvio direccionOrigen;

    @FXML
    private Label lbBuscarEnvio;
    @FXML
    private TextField tfBuscarEnvio;
    @FXML
    private TableView<ResultadoObtenerEnvio> tvEnvios;
    @FXML
    private TableColumn<ResultadoObtenerEnvio, String> tcCliente;
    @FXML
    private TableColumn<ResultadoObtenerEnvio, String> tcOrigen;
    @FXML
    private TableColumn<ResultadoObtenerEnvio, String> tcDestino;
    @FXML
    private TableColumn tcEstado;
    @FXML
    private TableColumn tcConductor;
    @FXML
    private TableColumn tcPaquetes;
    @FXML
    private TableColumn tcGuia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        configurarTabla();
        obtenerEnvios();
        configurarFiltro();
    }

    void recibirConfiguracion(Colaborador colaborador, HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {

        this.colaborador = colaborador;
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        ResultadoObtenerEnvio envio = tvEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {

            irFormularioEnvio(this, envio);
        } else {
            Utilidades.mostrarAlerta("Sin selección", "Selecciona una unidad para poder editar", Alert.AlertType.WARNING);
        }
    }

    private void irFormularioEnvio(INotificadorOperacion observador, ResultadoObtenerEnvio envio) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar envio");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioEnvio.fxml"));
            Parent formularioUnidad = loader.load();

            FXMLFormularioEnvioController controlador = loader.getController();

            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(colaborador, hbSuperior, vbMenu, spEscena, label, nombre);
            controlador.inicializarValores(observador, envio);

            spEscena.getChildren().add(formularioUnidad);
        } catch (IOException e) {
            Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    public void obtenerEnvios() {
        envios = FXCollections.observableArrayList();
        List<ResultadoObtenerEnvio> listaObtenida = EnvioDAO.obtenerEnvios();
        if (listaObtenida != null && !listaObtenida.isEmpty()) {

            envios.addAll(listaObtenida);
            tvEnvios.setItems(envios);
        } else {
            Utilidades.mostrarAlerta("Datos no disponibles", "No se encontraron envíos", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnCrearEnvio(ActionEvent event) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar Envío");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioEnvio.fxml"));
            Parent formularioEnvio = loader.load();

            FXMLFormularioEnvioController controlador = loader.getController();
            controlador.inicializarValores(this, null);

            controlador.inicializarValores(this, null);

            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(colaborador, hbSuperior, vbMenu, spEscena, label, nombre);

            spEscena.getChildren().add(formularioEnvio);
        } catch (IOException e) {
            Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    private void configurarTabla() {

        // Columna Cliente
        tcCliente.setCellValueFactory(cellData -> {
            ResultadoObtenerEnvio envio = cellData.getValue();
            String nombreCliente = envio.getNombreCliente();
            String estatusCliente = envio.getEstatusCliente();

            if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
                return new SimpleStringProperty(""); // Campo vacío si no hay información
            }

            if (!"Activo".equals(estatusCliente)) {
                nombreCliente += " (cliente eliminado)";
            }

            return new SimpleStringProperty(nombreCliente);
        });

        // Columna Origen
        tcOrigen.setCellValueFactory(cellData -> {
            ResultadoObtenerEnvio direccionOrigen = cellData.getValue();
            String concatenado = (direccionOrigen.getCalleOrigen() == null ? "" : direccionOrigen.getCalleOrigen()) + " "
                    + (direccionOrigen.getNumeroOrigen() == null ? "" : direccionOrigen.getNumeroOrigen()) + ", "
                    + (direccionOrigen.getColoniaOrigen() == null ? "" : direccionOrigen.getColoniaOrigen()) + ", "
                    + (direccionOrigen.getCiudadOrigen() == null ? "" : direccionOrigen.getCiudadOrigen());
            return new SimpleStringProperty(concatenado.trim().replaceAll(", $", "")); // Limpia espacios o comas finales
        });

        // Columna Destino
        tcDestino.setCellValueFactory(cellData -> {
            ResultadoObtenerEnvio direccionDestino = cellData.getValue();
            String concatenado = (direccionDestino.getCalleDestino() == null ? "" : direccionDestino.getCalleDestino()) + " "
                    + (direccionDestino.getNumeroDestino() == null ? "" : direccionDestino.getNumeroDestino()) + ", "
                    + (direccionDestino.getColoniaDestino() == null ? "" : direccionDestino.getColoniaDestino()) + ", "
                    + (direccionDestino.getCodigoPostalDestino() == null ? "" : direccionDestino.getCodigoPostalDestino()) + ", "
                    + (direccionDestino.getCiudadDestino() == null ? "" : direccionDestino.getCiudadDestino()) + ", "
                    + (direccionDestino.getEstadoDestino() == null ? "" : direccionDestino.getEstadoDestino());
            return new SimpleStringProperty(concatenado.trim().replaceAll(", $", "")); // Limpia espacios o comas finales
        });

        // Otras columnas
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("nombreSeguimiento"));
        tcConductor.setCellValueFactory(new PropertyValueFactory<>("nombreConductor"));
        tcPaquetes.setCellValueFactory(new PropertyValueFactory<>("cantidadPaquetes"));
        tcGuia.setCellValueFactory(new PropertyValueFactory<>("numeroGuia"));

        tcEstado.setCellFactory(tc -> {
            return new TableCell<Envio, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);

                        // Aplicar estilos según el estado
                        if ("Pendiente".equals(item)) {
                            setStyle("-fx-text-fill: black");
                        } else if ("En tránsito".equals(item)) {
                            setStyle("-fx-text-fill: blue;");
                        } else if ("Detenido".equals(item)) {
                            setStyle("-fx-text-fill: orange;");
                        } else if ("Entregado".equals(item)) {
                            setStyle("-fx-text-fill: green;");
                        } else if ("Cancelado".equals(item)) {
                            setStyle("-fx-text-fill: red;");
                        } else {
                            setStyle(""); // Restablecer estilo por defecto
                        }
                    }
                }
            };
        });
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        obtenerEnvios();
        configurarFiltro();
    }

    private void configurarFiltro() {

        listaEnvios = new FilteredList<>(envios, b -> true);

        tfBuscarEnvio.textProperty().addListener((observable, oldValue, newValue) -> {
            listaEnvios.setPredicate(envio -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (envio.getNumeroGuia() != null && envio.getNumeroGuia().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<ResultadoObtenerEnvio> sortedData = new SortedList<>(listaEnvios);
        sortedData.comparatorProperty().bind(tvEnvios.comparatorProperty());
        tvEnvios.setItems(sortedData);
    }

}
