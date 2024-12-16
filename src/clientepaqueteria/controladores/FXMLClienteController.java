package clientepaqueteria.controladores;
/*
import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Direccion;
import clientepaqueteria.pojo.Mensaje;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLClienteController implements Initializable, INotificadorOperacion {

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    
    private ObservableList<Cliente> clientes;
    private FilteredList<Cliente> listaClientes;
    @FXML
    private TableView<Cliente> tvCliente;
    
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colNumero;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TextField tfBuscarCliente;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        configurarFiltro();
        
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCalle.setCellValueFactory(new PropertyValueFactory<>("calle"));
        colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
    }

    private void cargarInformacionTabla() {
        clientes = FXCollections.observableArrayList();
        List<Cliente> listaWS = ClienteDAO.obtenerClientes();
        if (listaWS != null) {
            clientes.addAll(listaWS);
            tvCliente.setItems(clientes);
        } else {
            Utilidades.mostrarAlerta("Datos no disponibles", "Lo sentimos, por el momento no se puede cargar la información de los clientes", Alert.AlertType.ERROR);
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
    private void btnRegistrar(ActionEvent event) {
        irFormulario(this, null, "Registrar Cliente");
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Cliente cliente = tvCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            irFormulario(this, cliente, "Editar Cliente");
        } else {
            Utilidades.mostrarAlerta("Seleccionar Cliente", "Para poder editar debes seleccionar un cliente", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Cliente cliente = tvCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean seElimina = Utilidades.mostrarConfirmacion("Eliminar Cliente", "¿Estás seguro de eliminar al cliente " + cliente.getNombre() + "?" + "\nRecuerda que una vez eliminado no se podrá recuperar.");
            if (seElimina) {
                eliminarCliente(cliente.getIdCliente());
            }
        } else {
            Utilidades.mostrarAlerta("Seleccionar Cliente", "Para poder eliminar debes seleccionar un cliente", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(INotificadorOperacion observador, Cliente cliente, String pantalla) {
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, pantalla);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioCliente.fxml"));
            Parent formularioCliente = loader.load();

            FXMLFormularioClienteController controlador = loader.getController();
            controlador.InicializarValores(observador, cliente);
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

            spEscena.getChildren().add(formularioCliente);

        } catch (IOException e) {
            Logger.getLogger(FXMLClienteController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    private void eliminarCliente(int idCliente) {
        Mensaje respuesta = ClienteDAO.eliminarCliente(idCliente);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Cliente eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformacionTabla();
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void configurarFiltro() {
        listaClientes = new FilteredList<>(clientes, b -> true);

        tfBuscarCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            listaClientes.setPredicate(cliente -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (cliente.getNombre() != null && cliente.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (cliente.getCorreo() != null && cliente.getCorreo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (cliente.getTelefono() != null && cliente.getTelefono().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Cliente> sortedData = new SortedList<>(listaClientes);
        sortedData.comparatorProperty().bind(tvCliente.comparatorProperty());
        tvCliente.setItems(sortedData);
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformacionTabla();
    }
}
*/
import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Direccion;
import clientepaqueteria.pojo.Mensaje;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXMLClienteController implements Initializable, INotificadorOperacion {

    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;
    
    private ObservableList<Cliente> clientes;
    private FilteredList<Cliente> listaClientes;
    @FXML
    private TableView<Cliente> tvCliente;
    
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colNumero;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TextField tfBuscarCliente;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        configurarFiltro();
        
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCalle.setCellValueFactory(new PropertyValueFactory<>("calle"));
        colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
    }

    private void cargarInformacionTabla() {
        clientes = FXCollections.observableArrayList();
        List<Cliente> listaWS = ClienteDAO.obtenerClientes();
        if (listaWS != null) {
            clientes.addAll(listaWS);
            tvCliente.setItems(clientes);
            
        } else {
            Utilidades.mostrarAlerta("Datos no disponibles", "Lo sentimos, por el momento no se puede cargar la información de los clientes", Alert.AlertType.ERROR);
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
    private void btnRegistrar(ActionEvent event) {
        irFormulario(this, null, "Registrar Cliente");
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Cliente cliente = tvCliente.getSelectionModel().getSelectedItem();
       
        if (cliente != null) {
            irFormulario(this, cliente, "Editar Cliente");
        } else {
            Utilidades.mostrarAlerta("Seleccionar Cliente", "Para poder editar debes seleccionar un cliente", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Cliente cliente = tvCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean seElimina = Utilidades.mostrarConfirmacion("Eliminar Cliente", "¿Estás seguro de eliminar al cliente " + cliente.getNombre() + "?" + "\nRecuerda que una vez eliminado no se podrá recuperar.");
            if (seElimina) {
                eliminarCliente(cliente.getIdCliente());
            }
        } else {
            Utilidades.mostrarAlerta("Seleccionar Cliente", "Para poder eliminar debes seleccionar un cliente", Alert.AlertType.WARNING);
        }
    }
    
    private void irFormulario(INotificadorOperacion observador, Cliente cliente, String pantalla) {
       
        Utilidades.expandirInterfaz(hbSuperior, vbMenu, spEscena, label, pantalla);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLFormularioCliente.fxml"));
            Parent formularioCliente = loader.load();

            FXMLFormularioClienteController controlador = loader.getController();
         
          if (cliente != null) {
                if (cliente.getDirecciones() != null) {
                    controlador.InicializarValores(observador, cliente, cliente.getDirecciones());
                } else {
                    controlador.InicializarValores(observador, cliente, null);
                }
            } else {
            System.out.println("El cliente es nulo");
            // Maneja el caso de cliente nulo aquí
            controlador.InicializarValores(observador, null, null);
        }

           
            controlador.setStackPane(spEscena);
            controlador.recibirConfiguracion(hbSuperior, vbMenu, spEscena, label, nombre);

            spEscena.getChildren().add(formularioCliente);

        } catch (IOException e) {
            Logger.getLogger(FXMLClienteController.class.getName()).log(Level.SEVERE, "Error al cargar la vista", e);
        }
    }

    private void eliminarCliente(int idCliente) {
        Mensaje respuesta = ClienteDAO.eliminarCliente(idCliente);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlerta("Éxito", "Cliente eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformacionTabla();
        } else {
            Utilidades.mostrarAlerta("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void configurarFiltro() {
        listaClientes = new FilteredList<>(clientes, b -> true);

        tfBuscarCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            listaClientes.setPredicate(cliente -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (cliente.getNombre() != null && cliente.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (cliente.getCorreo() != null && cliente.getCorreo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (cliente.getTelefono() != null && cliente.getTelefono().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Cliente> sortedData = new SortedList<>(listaClientes);
        sortedData.comparatorProperty().bind(tvCliente.comparatorProperty());
        tvCliente.setItems(sortedData);
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformacionTabla();
    }
}