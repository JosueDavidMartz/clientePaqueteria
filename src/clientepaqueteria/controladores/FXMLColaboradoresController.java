package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLColaboradoresController implements Initializable, INotificadorOperacion{
    private ObservableList<Colaborador> colaboradores;

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        tfBuscarColaborador.textProperty().addListener((observable, oldValue, newValue) -> {
        buscarColaborador(newValue);
    });
        
    }

    

    private void configurarTabla() {
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
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
            boolean seElimina = Utilidades.mostrarAlertaConfirmacion("Eliminar colaborador", "¿Estás seguro de eliminar al colaborador " + colaborador.getNombre() + "?" + "\nRecuerda que una vez eliminado no se podrá recuperar.");
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
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioColaborador.fxml"));
            Parent formularioColaborador = loader.load();

            // Obtén el controlador de la nueva vista
            FXMLFormularioColaboradorController controlador = loader.getController();
            controlador.InicializarValores(observador,colaborador);

            // Pasa el StackPane al nuevo controlador
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

            // Agrega la nueva vista al StackPane (encima de la actual)
            spEscena.getChildren().add(formularioColaborador);

        } catch (IOException e) {
            Logger.getLogger(FXMLColaboradoresController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    private void eliminarColaborador(int idColaborador) {
        Mensaje respuesta = ColaboradorDAO.eliminarColaborador(idColaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Colaborador eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformacionTabla(); // Actualiza la tabla después de eliminar
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }


    

    private void actualizarTablaColaboradores(List<Colaborador> colaboradoresEncontrados) {
        colaboradores.clear();
        colaboradores.addAll(colaboradoresEncontrados);
        tvColaboradores.setItems(colaboradores);
    }
    
    private void buscarColaborador(String noPersonal) {
    // Si el campo está vacío, no hacemos la búsqueda
    if (noPersonal.isEmpty()) {
        // Puedes optar por limpiar la tabla o no hacer nada
        limpiarTablaColaboradores();
        return;
    }

    // Llama al DAO para obtener un colaborador específico
    Mensaje respuesta = ColaboradorDAO.buscarColaborador(noPersonal);

    // Si la respuesta no tiene error, y el colaborador fue encontrado
    if (!respuesta.isError()) {
        // Aquí debes llamar a un método para obtener el colaborador por el número de personal
        Colaborador colaborador = (Colaborador) ColaboradorDAO.obtenerColaboradores(); // Este método debe devolver un colaborador

        if (colaborador != null) {
            // Si se encontró el colaborador, actualiza la tabla con el colaborador encontrado
            ObservableList<Colaborador> listaColaborador = FXCollections.observableArrayList(colaborador);
            actualizarTablaColaboradores(listaColaborador); // Actualiza la tabla con el colaborador encontrado
        } else {
            limpiarTablaColaboradores();  // Si no se encuentra, limpia la tabla
            Utilidades.mostrarAlerta("No encontrado", "No se encontró ningún colaborador con el número de personal: " + noPersonal, Alert.AlertType.INFORMATION);
        }
    } else {
        limpiarTablaColaboradores();  // Si hubo un error, limpia la tabla
        // Si hubo un error o no se encontró el colaborador, muestra el mensaje de error
        Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
    }
}

private void limpiarTablaColaboradores() {
    // Este método puede ser útil para limpiar la tabla si no hay resultados
    ObservableList<Colaborador> listaVacia = FXCollections.observableArrayList();
    actualizarTablaColaboradores(listaVacia);
}

private void actualizarTablaColaboradores(ObservableList<Colaborador> listaColaborador) {
    // Aquí actualizas la tabla con la lista que se pase (ya sea con un solo colaborador o vacía)
    tvColaboradores.setItems(listaColaborador);
}




    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
