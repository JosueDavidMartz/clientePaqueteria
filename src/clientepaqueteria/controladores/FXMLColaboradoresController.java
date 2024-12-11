package clientepaqueteria.controladores;

import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.utilidades.Utilidades;
import java.io.IOException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLColaboradoresController implements Initializable {
    private ObservableList<Colaborador>colaboradores;
    
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
    private TableColumn<?, ?> colLicencia;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }

    private void configurarTabla(){
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));

    }
    
    private void cargarInformacionTabla(){
        colaboradores = FXCollections.observableArrayList();
        List<Colaborador> listaWS = ColaboradorDAO.obtenerColaboradores();
        if (listaWS != null){
            colaboradores.addAll(listaWS);
            tvColaboradores.setItems(colaboradores);
        }else{
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
    }

    @FXML
    private void btnEditar(ActionEvent event) {
    }

    @FXML
private void btnRegistar(ActionEvent event) {
    Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar colaborador");

        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioColaborador.fxml"));
            Parent formularioColaborador = loader.load();

            // Obtén el controlador de la nueva vista
            FXMLFormularioColaboradorController controlador = loader.getController();

            // Pasa el StackPane al nuevo controlador
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

            // Agrega la nueva vista al StackPane (encima de la actual)
            spEscena.getChildren().add(formularioColaborador);

        } catch (IOException e) {
            Logger.getLogger(FXMLColaboradoresController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }


    
}
