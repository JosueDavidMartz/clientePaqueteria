package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.pojo.Rol;
import clientepaqueteria.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLColaboradoresController implements Initializable, INotificadorOperacion {

    private ObservableList<Colaborador> colaboradores;
    private FilteredList<Colaborador> listaColaboradores;
    private Colaborador colaborador;
    private FXMLInicioController inicioController;

    private Unidad unidad;

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    String numeroPersonal;
    String rol;

    private ObservableList<Rol> roles;

    @FXML
    private TableView<Colaborador> tvColaboradores;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colApellidoM;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colRol;
    @FXML
    private Label lbBuscar;
    @FXML
    private TableColumn colLicencia;

    @FXML
    private TextField tfBuscarColaborador;
    @FXML
    private ComboBox<Rol> cbRol;

    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        configurarFiltro();
    }

    private void configurarTabla() {

        colNoPersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
        colLicencia.setCellValueFactory(new PropertyValueFactory("numeroLicencia"));
    }

    private void cargarInformacionTabla() {
        colaboradores = FXCollections.observableArrayList();
        List<Colaborador> listaWS = ColaboradorDAO.obtenerColaboradores();
        if (listaWS != null) {
            colaboradores.addAll(listaWS);
            tvColaboradores.setItems(colaboradores);
        } else {
            Utilidades.mostrarAlerta("Datos no disponibles", "Lo sentimos por el momento no se puede cargar la información de los colaboradores", Alert.AlertType.ERROR);
        }
    }

    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre, Colaborador colaborador) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
        this.colaborador = colaborador;
    }

    @FXML
    private void btnEliminar(ActionEvent event) {

        Colaborador colaboradorSeleccionado = tvColaboradores.getSelectionModel().getSelectedItem();

        if (colaboradorSeleccionado == null) {

            Utilidades.mostrarAlerta("Seleccionar Colaborador",
                    "Para poder eliminar, debes seleccionar al colaborador.",
                    Alert.AlertType.WARNING);
            return;
        }
        
        
        if (this.colaborador.getIdColaborador() == colaboradorSeleccionado.getIdColaborador()) {
            Utilidades.mostrarAlerta("Error",
                    "No puedes eliminar al usuario actualmente logueado. Inicia sesión con otra cuenta con permisos de administrador.",
                    Alert.AlertType.ERROR);
            return;
        }

        String mensajeConfirmacion = "¿Estás seguro de eliminar al colaborador "
                + colaboradorSeleccionado.getNombre() + "?";

        if ("Conductor".equalsIgnoreCase(colaboradorSeleccionado.getRol())) {
            mensajeConfirmacion += "\n\nNo olvides asignar sus envios a otro conductor, en caso de tener un auto asignado, este volverá a estár disponible";
        }

       // mensajeConfirmacion += "\n\nRecuerda que una vez eliminado no se podrá recuperar.";

        boolean seElimina = Utilidades.mostrarConfirmacion("Eliminar colaborador", mensajeConfirmacion);

        if (seElimina) {
            eliminarColaborador(colaboradorSeleccionado.getIdColaborador(), colaboradorSeleccionado.getNumeroPersonal());

        }
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            irFormulario(this, colaborador, "Editar Colaborador");
        } else {
            Utilidades.mostrarAlerta("Seleccionar Colaborador", "Para poder editar debes seleccionar al Colaborador", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnRegistar(ActionEvent event) {
        irFormulario(this, null, "Registrar colaborador");
    }

    private void irFormulario(INotificadorOperacion observador, Colaborador colaborador, String pantalla) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, pantalla);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioColaborador.fxml"));
            Parent formularioColaborador = loader.load();

            FXMLFormularioColaboradorController controlador = loader.getController();
            controlador.InicializarValores(observador, colaborador);
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);
            controlador.setInicioController(inicioController);
            spEscena.getChildren().add(formularioColaborador);

        } catch (IOException e) {
            Logger.getLogger(FXMLColaboradoresController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    private void eliminarColaborador(int idColaborador, String noPersonal) {

        Mensaje respuesta = ColaboradorDAO.eliminarColaborador(idColaborador, noPersonal);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Colaborador eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformacionTabla();
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }

    }

    private void actualizarTablaColaboradores(List<Colaborador> colaboradoresEncontrados) {
        colaboradores.clear();
        colaboradores.addAll(colaboradoresEncontrados);
        tvColaboradores.setItems(colaboradores);
    }

    private void buscarColaborador(String filtro) {

        Map<String, String> parametros = new HashMap<>();
        parametros.put("nombre", filtro);
        parametros.put("numeroPersonal", filtro);
        parametros.put("rol", filtro);

        List<Colaborador> colaboradoresEncontrados = ColaboradorDAO.buscarColaborador(parametros);

        if (colaboradoresEncontrados != null && !colaboradoresEncontrados.isEmpty()) {
            actualizarTablaColaboradores(FXCollections.observableArrayList(colaboradoresEncontrados));
        } else {
            limpiarTablaColaboradores();

        }
    }

    private void limpiarTablaColaboradores() {
        ObservableList<Colaborador> listaVacia = FXCollections.observableArrayList();
        tvColaboradores.setItems(listaVacia);
    }

    private void actualizarTablaColaboradores(ObservableList<Colaborador> listaColaborador) {
        tvColaboradores.setItems(listaColaborador);
    }

    private void configurarFiltro() {

        listaColaboradores = new FilteredList<>(colaboradores, b -> true);

        tfBuscarColaborador.textProperty().addListener((observable, oldValue, newValue) -> {
            listaColaboradores.setPredicate(colaborador -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (colaborador.getNombre() != null && colaborador.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (colaborador.getNumeroPersonal() != null && colaborador.getNumeroPersonal().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (colaborador.getRol() != null && colaborador.getRol().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Colaborador> sortedData = new SortedList<>(listaColaboradores);
        sortedData.comparatorProperty().bind(tvColaboradores.comparatorProperty());
        tvColaboradores.setItems(sortedData);
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformacionTabla();
        configurarFiltro();

    }

    void Inizializar(Colaborador colaborador, FXMLInicioController inicioController) {
        this.colaborador = colaborador;
        this.inicioController = inicioController;
    }
}
