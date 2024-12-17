package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Rol;
import clientepaqueteria.utilidades.Utilidades;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
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

public class FXMLColaboradoresController implements Initializable, INotificadorOperacion{
   
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    String numeroPersonal;
    String rol;
   
    private ObservableList<Colaborador> colaboradores;
    private ObservableList<Rol> roles;
    private FilteredList<Colaborador> listaColaboradores;
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
    private TextField tfBuscarColaborador;
    @FXML
    private TableColumn colLicencia;

   @Override
public void initialize(URL url, ResourceBundle rb) {
    configurarTabla();
    cargarInformacionTabla();
    configurarFiltro();

}

    private void configurarTabla() {
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
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

    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            boolean seElimina = Utilidades.mostrarConfirmacion("Eliminar colaborador", "¿Estás seguro de eliminar al colaborador " + colaborador.getNombre() + "?" + "\nRecuerda que una vez eliminado no se podrá recuperar.");
            if (seElimina) {
                eliminarColaborador(colaborador.getIdColaborador()); // Asegúrate de que aquí se usa getNoPersonal()
            }
        } else {
            Utilidades.mostrarAlerta("Seleccionar Colaborador", "Para poder eliminar debes seleccionar al Colaborador", Alert.AlertType.WARNING);
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
        irFormulario(this, null, "Pantalla Registro");
    }

    private void irFormulario(INotificadorOperacion observador, Colaborador colaborador, String pantalla) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, pantalla);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioColaborador.fxml"));
            Parent formularioColaborador = loader.load();

            FXMLFormularioColaboradorController controlador = loader.getController();
            controlador.InicializarValores(observador,colaborador);
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

            spEscena.getChildren().add(formularioColaborador);

        } catch (IOException e) {
            Logger.getLogger(FXMLColaboradoresController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    private void eliminarColaborador(int idColaborador) {
        Mensaje respuesta = ColaboradorDAO.eliminarColaborador(idColaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Colaborador eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformacionTabla(); 
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
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
                if (colaborador.getNombre()!= null && colaborador.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (colaborador.getNoPersonal()!= null && colaborador.getNoPersonal().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (colaborador.getRol()!= null && colaborador.getRol().toLowerCase().contains(lowerCaseFilter)) {
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
    }
}
