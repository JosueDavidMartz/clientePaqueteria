/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ConductorDAO;
import clientepaqueteria.modelo.dao.UnidadDAO;
import clientepaqueteria.pojo.Mensaje;
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

/**
 * FXML Controller class
 *
 * @author WIN 10
 */
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
        if(unidad != null){
            
            irFormularioUnidades(this, unidad);
        }else{
            Utilidades.mostrarAlerta("Sin selección", "Selecciona una unidad para poder editar", Alert.AlertType.WARNING);
        }
    }

    private void irFormularioUnidades(INotificadorOperacion observador, Unidad unidad){
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, "Registrar unidad");
        try {
         // Cargar la nueva vista
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioUnidad.fxml"));
         Parent formularioUnidad = loader.load();

         // Obtén el controlador de la nueva vista
         FXMLFormularioUnidadController controlador = loader.getController();

         // Pasa el StackPane al nuevo controlador
         controlador.setStackPane(spEscena);
         controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);
         controlador.inicializarValores(observador, unidad);
         // Agrega la nueva vista al StackPane (encima de la actual)
         spEscena.getChildren().add(formularioUnidad);
     } catch (IOException e) {
         Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
     }
    }

    @FXML
    private void btnEliminarUnidad(ActionEvent event) {
         
        unidadBaja = tvTablaUnidades.getSelectionModel().getSelectedItem();
        if(unidadBaja != null){
            paneBajaUnidad.setVisible(true);            
        }else{
            Utilidades.mostrarAlerta("Sin selección", "Selecciona la unidad que quieres dar de baja", Alert.AlertType.WARNING);
        }
        
    }

   
    /*@FXML
    private void btnAceptarBaja(ActionEvent event) {
        
        if (taMotivo.getText().trim().isEmpty()) {
            lbErrorMotivo.setText("Se requiere registrar el motivo de la baja");            
        }else{
            paneBajaUnidad.setVisible(false);
            unidadBaja.setMotivo(taMotivo.getText());
            RespuestaUnidad unidadRespuesta = UnidadDAO.darBajaUnidad(unidadBaja);
            if(!unidadRespuesta.isError()){
                Utilidades.mostrarAlerta("Eliminado", "Se eliminó la unidad: "+unidadBaja.getNumeroInterno(), Alert.AlertType.INFORMATION);                
            }
        }
    }*/
    @FXML
    private void btnAceptarBaja(ActionEvent event) {
        if (taMotivo.getText().trim().isEmpty()) {
            lbErrorMotivo.setText("Se requiere registrar el motivo de la baja");
        } else {
            // Crear un cuadro de diálogo de confirmación
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            // Mostrar el cuadro de diálogo y esperar la respuesta del usuario
            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // El usuario confirmó, proceder con la eliminación
                paneBajaUnidad.setVisible(false);
                unidadBaja.setMotivo(taMotivo.getText());
                taMotivo.setText("");
                lbErrorMotivo.setText("");
                RespuestaUnidad unidadRespuesta = UnidadDAO.darBajaUnidad(unidadBaja);

                if (!unidadRespuesta.isError()) {
                    Utilidades.mostrarAlerta(
                        "Eliminado",
                        "Se eliminó la unidad: " + unidadBaja.getNumeroInterno(),
                        Alert.AlertType.INFORMATION
                    );
                } else {
                    Utilidades.mostrarAlerta(
                        "Error",
                        "No se pudo eliminar la unidad: " + unidadRespuesta.getMensaje(),
                        Alert.AlertType.ERROR
                    );
                }
            } else {
                // El usuario canceló la operación
                Utilidades.mostrarAlerta(
                    "Operación cancelada",
                    "La unidad seguirá disponible",
                    Alert.AlertType.INFORMATION
                );
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
    tcConductor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    tcNoPersonal.setCellValueFactory(new PropertyValueFactory<>("noPersonal"));

    // Configurar el estilo de la celda basada en el estado
    tcConductor.setCellFactory(tc -> {
        return new TableCell<Unidad, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("Disponible".equals(item)) {
                        setStyle("-fx-text-fill: green;"); // Verde
                    } else {
                        setStyle("");
                    }
                }
            }
        };
    });
}


    private void cargarInformacionTabla() {
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadDAO.obtenerColaboradores();
        if(listaWS != null ){//nos interesa diferenciar cuando es null, ya que vacio y con contenido siguen el mismo camino
            unidades.addAll(listaWS);
            tvTablaUnidades.setItems(unidades);
        }else{
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
                if (unidad.getNumeroInterno()!= null && unidad.getNumeroInterno().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (unidad.getVin()!= null && unidad.getVin().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (unidad.getMarca()!= null && unidad.getMarca().toLowerCase().contains(lowerCaseFilter)) {
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
        unidad = tvTablaUnidades.getSelectionModel().getSelectedItem();
    if (unidad != null) {
        String noPersonal = unidad.getNoPersonal(); // Asegúrate que noPersonal no sea null
        System.out.println("Numero Personal: " + noPersonal);
         if (noPersonal != null && !"No Asignado".equals(noPersonal)) {
             System.out.println("Numero Personal ESTO RECIBE: " + noPersonal);// Uso del método equals() para comparar correctamente
            quitarVehiculo(noPersonal);
            cargarInformacionTabla();
        } else {
            Utilidades.mostrarAlerta("Error", "Número de personal no encontrado.", Alert.AlertType.ERROR);
        }
    } else {
        // Manejar el caso en que no se selecciona ninguna unidad
        Utilidades.mostrarAlerta("Advertencia", "Debe seleccionar una unidad", Alert.AlertType.WARNING);
    }
    }
    
    private void quitarVehiculo(String noPersonal){
     Mensaje respuesta = ConductorDAO.desasociarUnidad(noPersonal);
    if (!respuesta.isError()) {
        Utilidades.mostrarAlerta("Éxito", "Unidad Desvinculada", Alert.AlertType.INFORMATION);
    } else {
        Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
    }
    }

    @FXML
    private void btnAsignarVehiculo(ActionEvent event) {
        Unidad unidadSeleccionada = tvTablaUnidades.getSelectionModel().getSelectedItem();
        if (unidadSeleccionada != null) {
            irAsignacionVehiculo(this, unidadSeleccionada);
        } else {
            Utilidades.mostrarAlerta(
                "Sin selección",
                "Por favor, selecciona un vehículo antes de asignarlo.",
                Alert.AlertType.WARNING
            );
        }
    }


    private void irAsignacionVehiculo(INotificadorOperacion observador, Unidad unidad){
        try {
        // Cargar la nueva vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLAsignarUnidad.fxml"));
        Parent formularioUnidad = loader.load();

        // Configurar el controlador de la nueva vista
        FXMLAsignarUnidadController controlador = loader.getController();
        controlador.InicializarValores(observador, unidad);

        // Crear un nuevo Stage
        Stage nuevaVentana = new Stage();
        nuevaVentana.setTitle("Asociar Unidad");
        nuevaVentana.setScene(new Scene(formularioUnidad));
        nuevaVentana.initModality(Modality.NONE); // Bloquea la ventana principal hasta que esta se cierre, si es necesario
        nuevaVentana.show();

    } catch (IOException e) {
        Logger.getLogger(FXMLUnidadesController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
    }
        
    }
}