package clientepaqueteria.controladores;

import clientepaqueteria.modelo.dao.EnvioDAO;
import clientepaqueteria.modelo.dao.UnidadDAO;
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


public class FXMLEnviosController implements Initializable {
    
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    ResultadoObtenerEnvio direccionOrigen;
    List<ResultadoObtenerEnvio> listaEnvios;
    
    @FXML
    private Label lbBuscarEnvio;
    @FXML
    private TextField tfBuscarEnvio;
    @FXML
    private TableView<ResultadoObtenerEnvio> tvEnvios;
    @FXML
    private TableColumn tcCliente;
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
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        obtenerEnvios();
        configurarTabla();
    }    

    void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
         //To change body of generated methods, choose Tools | Templates.
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    @FXML
    private void btnModificar(ActionEvent event) {
    }
    
    public void obtenerEnvios() {
        List<ResultadoObtenerEnvio> listaObtenida = EnvioDAO.obtenerEnvios();
        if (listaObtenida != null && !listaObtenida.isEmpty()) {
            ObservableList<ResultadoObtenerEnvio> observableList = FXCollections.observableArrayList(listaObtenida);
            tvEnvios.setItems(observableList);
        } else {
            Utilidades.mostrarAlerta("Datos no disponibles", "No se encontraron envíos", Alert.AlertType.WARNING);
        }
    }


    @FXML
    private void btnCrearEnvio(ActionEvent event) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar Envío");
        try {
         // Cargar la nueva vista
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioEnvio.fxml"));
         Parent formularioEnvio = loader.load();

         // Obtén el controlador de la nueva vista
         FXMLFormularioEnvioController controlador = loader.getController();

         // Pasa el StackPane al nuevo controlador
         controlador.setStackPane(spEscena);
         controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);
         // Agrega la nueva vista al StackPane (encima de la actual)
         spEscena.getChildren().add(formularioEnvio);
     } catch (IOException e) {
         Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
     }
    }
    
    private void configurarTabla() {
        // Asignar propiedades directamente
        tcCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        // Concatenar las propiedades para tcOrigen
        tcOrigen.setCellValueFactory(cellData -> {
            ResultadoObtenerEnvio direccionOrigen = cellData.getValue();
            String concatenado = direccionOrigen.getCalleOrigen() + " " 
                                + direccionOrigen.getNumeroOrigen() + ", " 
                                + direccionOrigen.getColoniaOrigen() + ", " 
                                + direccionOrigen.getCiudadOrigen();
            return new SimpleStringProperty(concatenado);
        });
        
        tcDestino.setCellValueFactory(cellData -> {
            ResultadoObtenerEnvio direccionDestino = cellData.getValue();
            String concatenado = direccionDestino.getCalleDestino() + " " 
                                + direccionDestino.getNumeroDestino() + ", " 
                                + direccionDestino.getColoniaDestino() + ", " 
                                + direccionDestino.getCodigoPostalDestino()+ ", " 
                                + direccionDestino.getCiudadDestino()+ ", " 
                                + direccionDestino.getEstadoDestino();
            return new SimpleStringProperty(concatenado);
        });
        
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("nombreSeguimiento"));
        tcConductor.setCellValueFactory(new PropertyValueFactory<>("nombreColaborador"));
        tcPaquetes.setCellValueFactory(new PropertyValueFactory<>("cantidadPaquetes"));

        // Configura otras columnas según sea necesario
        

        // Puedes continuar con el resto de las columnas aquí
    }


    /*private void cargarInformacionTabla() {
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadDAO.obtenerColaboradores();
        if(listaWS != null ){//nos interesa diferenciar cuando es null, ya que vacio y con contenido siguen el mismo camino
            unidades.addAll(listaWS);
            tvTablaUnidades.setItems(unidades);
        }else{
            Utilidades.mostrarAlerta("Datos no disponibles", "Lo sentimos por el momento no se puede cargar la lista de las unidades", Alert.AlertType.ERROR);
        }
    }*/

    
    
}
