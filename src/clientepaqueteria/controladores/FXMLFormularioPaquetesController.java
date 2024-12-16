package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.modelo.dao.PaqueteDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Direccion;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Paquete;
import clientepaqueteria.pojo.RespuestaEnvio;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class FXMLFormularioPaquetesController implements Initializable {
    private Map<String, Cliente> clienteMap = new HashMap<>();
    private Map<String, Colaborador> conductorMap = new HashMap<>();
    private TitledPane ultimoPaquete = null; // Guarda el último TitledPane agregado
    private INotificadorOperacion observador;
    private Paquete paquete;
    private boolean modoEdicion = false;
    
    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;

    @FXML
    private Pane paneVincularEnvio;
    @FXML
    private Label lbErrorSeleccionarEnvio;
    @FXML
    private VBox vbPaquetes;
    @FXML
    private ScrollPane scContenedor;

    @FXML
    private ComboBox<String> cbNombreCliente;
    
    @FXML
    private TextField tfCostoEnvio;

    private List<Paquete> listaPaquetes = new ArrayList<>(); // Lista para almacenar los paquetes
    private List<VBox> paquetesFormularios = new ArrayList<>();
    List<Paquete> paquetes;
    @FXML
    private ComboBox<String> cbNumeroGuia;
    @FXML
    private ImageView ivAgregarPaquete;
    @FXML
    private Pane paneEditarPaquete;
    @FXML
    private TextField tfAltura;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfLargo;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextArea taDescripcion;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scContenedor.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scContenedor.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scContenedor.setFitToWidth(true);
        agregarPaquete();
        obtenerClientes();
        
        scContenedor.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

    // Personalizar VBox
        vbPaquetes.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 10; -fx-border-color: #cccccc; -fx-padding: 10;");
    }

    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }
    public void InicializarValores(INotificadorOperacion observador, Paquete paquete) {
        this.observador = observador;
        this.paquete = paquete;
        if (this.paquete != null) {
            modoEdicion = true;
            cargarDatosEdicion(this.paquete);
        }else{
            
        }
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }
    public void obtenerClientes(){
        
        
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes = ClienteDAO.obtenerClientes();
        if (listaClientes != null) {
            for (Cliente cliente : listaClientes) {
                cbNombreCliente.getItems().add(cliente.getNombre());
                clienteMap.put(cliente.getNombre(), cliente);
            }
        }
        
        cbNombreCliente.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
                // Llama al método para actualizar los números de guía
                actualizarNumerosDeGuia(newValue);
            }
        });
    }
    
    
    private void cargarDatosEdicion(Paquete paquete) {
        paneEditarPaquete.setVisible(true);
        tfAltura.setText(String.valueOf(paquete.getDimensionAlto()));
        tfAncho.setText(String.valueOf(paquete.getDimensionAncho()));
        tfLargo.setText(String.valueOf(paquete.getDimensionProfundidad()));
        tfPeso.setText(String.valueOf(paquete.getPeso()));
        taDescripcion.setText(paquete.getDescripcion());
        ivAgregarPaquete.setVisible(false);
    }
    
    private void actualizarNumerosDeGuia(String nombreCliente) {
    // Obtiene el objeto Cliente asociado al nombre seleccionado
        Cliente clienteSeleccionado = clienteMap.get(nombreCliente);

        if (clienteSeleccionado != null) {
            int idCliente = clienteSeleccionado.getIdCliente(); // Obtén el ID del cliente
            System.out.println("ID del Cliente seleccionado: " + idCliente);

            // Realiza la consulta para obtener los números de guía asociados
            List<String> numerosDeGuia = PaqueteDAO.obtenerNumerosDeGuiaPorCliente(idCliente);

            // Limpia y actualiza el ComboBox de números de guía
            cbNumeroGuia.getItems().clear();
            if (numerosDeGuia != null) {
                cbNumeroGuia.getItems().addAll(numerosDeGuia);
            }
        }
    
    }
    
    
    
    
    @FXML
    private void btnCancelarPaquete(ActionEvent event) {
        boolean respuesta = Utilidades.mostrarConfirmacion("Confirmar", "Si te sales perderás los cambios sin guardar");
        if(respuesta){
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Paquetes");
        }
    }

    @FXML
    private void btnAceptarPaquete(ActionEvent event) {
        
         if (modoEdicion) {
             
             if(tfAltura.getText().isEmpty() || tfAncho.getText().isEmpty() || tfLargo.getText().isEmpty()
                     || taDescripcion.getText().isEmpty() || tfPeso.getText().isEmpty()){
                 Utilidades.mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
             
         }
            Paquete paqueteEditado = new Paquete();
            paqueteEditado.setDescripcion(taDescripcion.getText());
            paqueteEditado.setDimensionAlto(Float.parseFloat(tfAltura.getText()));
            paqueteEditado.setDimensionAncho(Float.parseFloat(tfAncho.getText()));
            paqueteEditado.setDimensionProfundidad(Float.parseFloat(tfLargo.getText()));
            paqueteEditado.setPeso(Float.parseFloat(tfPeso.getText()));
            paqueteEditado.setIdPaquete(paquete.getIdPaquete());

            Mensaje respuesta = PaqueteDAO.modificar(paqueteEditado);
            String mensaje;
            boolean error = false;
            if (respuesta != null && !respuesta.isError()) {
                     mensaje = respuesta.getMensaje();
                     Utilidades.mostrarAlerta("Editado", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                     observador.notificarOperacionExitosa("Editado", paquete.getDescripcion());
                     stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                     Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Paquetes");
                 } else {
                     error = true;
                     mensaje = respuesta != null ? respuesta.getMensaje() : "Error al editar el paquete.";
                     Utilidades.mostrarAlerta("Editado", respuesta.getMensaje(), Alert.AlertType.ERROR);
                 }
            
         } else {
                    

        //paneVincularEnvio.setVisible(true);
 
        paquetes = new ArrayList<>();

        for (VBox formulario : paquetesFormularios) {
        // Obtener los campos del formulario
            TextArea txtDescripcion = (TextArea) formulario.lookup("#txtDescripcion");
            TextField txtAlto = (TextField) formulario.lookup("#txtAlto");
            TextField txtAncho = (TextField) formulario.lookup("#txtAncho");
            TextField txtLargo = (TextField) formulario.lookup("#txtLargo");
            TextField txtPeso = (TextField) formulario.lookup("#txtPeso");

        // Verificar si los campos están vacíos
        if (txtDescripcion.getText().isEmpty() || 
            txtAlto.getText().isEmpty() || 
            txtAncho.getText().isEmpty() || 
            txtLargo.getText().isEmpty() || 
            txtPeso.getText().isEmpty()) {
            
            // Mostrar alerta si hay campos vacíos
            Utilidades.mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
            return;  // Salir si hay campos vacíos
        }

        try {
            // Convertir los valores a números
            float alto = Float.parseFloat(txtAlto.getText());
            float ancho = Float.parseFloat(txtAncho.getText());
            float largo = Float.parseFloat(txtLargo.getText());
            float peso = Float.parseFloat(txtPeso.getText());

            // Crear el objeto Paquete
            Paquete paquete = new Paquete();
            paquete.setDescripcion(txtDescripcion.getText());
            paquete.setDimensionAlto(alto);
            paquete.setDimensionAncho(ancho);
            paquete.setDimensionProfundidad(largo);
            paquete.setPeso(peso);

            // Añadir el paquete a la lista
            paquetes.add(paquete);

        } catch (NumberFormatException e) {
            // Mostrar alerta si hay error en la conversión
            Utilidades.mostrarAlerta("Error", "Por favor, ingrese valores numéricos válidos para las dimensiones y el peso.", Alert.AlertType.ERROR);
            return;  // Salir si hay error de formato
        }
        }

        // Si se llegó aquí, significa que todos los formularios son válidos
        // Ahora puedes guardar los paquetes en la base de datos o procesarlos
       // guardarPaquetes(paquetes);
        paneVincularEnvio.setVisible(true);
    }
    }

    
    
        private void guardarPaquetes(List<Paquete> paquetes, String costo, String guia) {
         // Mostrar el panel para vincular el envío
         
         boolean error = false;
         String mensaje;
         int idEnvio = 0;

         // Obtener el ID de un envío por el número de guía
         try {
             ResultadoObtenerEnvio envio = PaqueteDAO.consultarEnvio(guia);
             if (envio != null) {
                 idEnvio = envio.getIdEnvio();
             } else {
                 Utilidades.mostrarAlerta("Error", "No se encontró un envío con el número de guía proporcionado.", Alert.AlertType.ERROR);
                 return;
             }
         } catch (Exception e) {
             Utilidades.mostrarAlerta("Error", "Hubo un problema al recuperar el envío: " + e.getMessage(), Alert.AlertType.ERROR);
             return;
         }

         // Asignar el ID del envío a cada paquete antes de registrarlo
         for (Paquete paquete : paquetes) {
             paquete.setIdEnvio(idEnvio); // Asegúrate de que el atributo exista en tu clase Paquete
             try {
                 Mensaje respuesta = PaqueteDAO.registrarPaquete(paquete);
                 if (respuesta != null && !respuesta.isError()) {
                     mensaje = respuesta.getMensaje();
                 } else {
                     error = true;
                     mensaje = respuesta != null ? respuesta.getMensaje() : "Error al registrar el paquete.";
                 }
             } catch (Exception e) {
                 error = true;
                 mensaje = "Excepción al registrar el paquete: " + e.getMessage();
             }
         }

         // Registrar el costo si no hubo errores
         if (!error) {
             try {           
                 
                 Envio envio = new Envio();
                 
                 envio.setNumeroGuia(guia);
                 float costoConvertido = Float.parseFloat(costo);
    
                // Asignar el valor convertido al objeto
                 envio.setCostoEnvio(costoConvertido);
        
                 RespuestaEnvio respuestaCosto = PaqueteDAO.registrarCosto(envio);
                 
                 if (respuestaCosto != null && !respuestaCosto.isError()) {
                     Utilidades.mostrarAlerta("Éxito", "Los paquetes y el costo han sido registrados correctamente.", Alert.AlertType.INFORMATION);
                     observador.notificarOperacionExitosa("Registro", envio.getNumeroGuia());
                     stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                     Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Paquetes");
                 } else {
                     Utilidades.mostrarAlerta("Advertencia", "Los paquetes fueron registrados, pero hubo un problema con el costo.", Alert.AlertType.WARNING);
                 }
             } catch (Exception e) {
                 Utilidades.mostrarAlerta("Error", "Hubo un problema al registrar el costo: " + e.getMessage(), Alert.AlertType.ERROR);
             }
         } else {
             Utilidades.mostrarAlerta("Error", "Hubo problemas al registrar algunos o todos los paquetes.", Alert.AlertType.ERROR);
         }
     }



    @FXML
    private void btnCancelarEnvio(ActionEvent event) {
        paneVincularEnvio.setVisible(false);
        cbNumeroGuia.setValue(null);  // Limpiar el ComboBox de número de guía
        cbNombreCliente.setValue(null);  // Limpiar el ComboBox de nombre de cliente
        tfCostoEnvio.clear();  // Limpiar el TextField de costo de envío
        lbErrorSeleccionarEnvio.setText(""); 
    }

   @FXML
    private void btnAgregar(MouseEvent event) {
        // Retrae el último TitledPane si existe
        if (ultimoPaquete != null) {
            ultimoPaquete.setExpanded(false);
        }

        // Agrega un nuevo formulario
        agregarPaquete();
    }


    /*private void agregarPaquete() {
    // Contenedor del formulario
    VBox formulario = new VBox();
    formulario.setSpacing(10);
    formulario.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-radius: 5;");

    // Campo de descripción
    Label lblDescripcion = new Label("Descripción:");
    TextArea txtDescripcion = new TextArea();
    txtDescripcion.setPromptText("Ingrese la descripción del paquete");
    txtDescripcion.setPrefRowCount(3);
    txtDescripcion.setId("txtDescripcion");

    // Campo de dimensiones
    Label lblDimensiones = new Label("Dimensiones (cm):");
    HBox hbDimensiones = new HBox(10);
    TextField txtAlto = new TextField();
    txtAlto.setPromptText("Alto");
    txtAlto.setId("txtAlto");
    TextField txtAncho = new TextField();
    txtAncho.setPromptText("Ancho");
    txtAncho.setId("txtAncho");
    TextField txtLargo = new TextField();
    txtLargo.setPromptText("Largo");
    txtLargo.setId("txtLargo");
    hbDimensiones.getChildren().addAll(txtAlto, txtAncho, txtLargo);

    // Campo de peso
    Label lblPeso = new Label("Peso (kg):");
    TextField txtPeso = new TextField();
    txtPeso.setPromptText("Ingrese el peso");
    txtPeso.setId("txtPeso");

    // Agregar elementos al formulario
    formulario.getChildren().addAll(
            lblDescripcion, txtDescripcion,
            lblDimensiones, hbDimensiones,
            lblPeso, txtPeso
    );

    // Crear el TitledPane con el formulario
    TitledPane nuevoPaquete = new TitledPane();
    nuevoPaquete.setText("Paquete #" + (vbPaquetes.getChildren().size() + 1));
    nuevoPaquete.setContent(formulario);

    // Agregar el TitledPane al VBox principal
    vbPaquetes.getChildren().add(nuevoPaquete);

    // Guardar la referencia del formulario generado en una lista (para ser procesada luego)
    paquetesFormularios.add(formulario);  // Lista global que contiene todos los formularios
}*/

  private void agregarPaquete() {
    // Contenedor del formulario
    VBox formulario = new VBox();
    formulario.setSpacing(10);
    formulario.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-radius: 5;");

    // Campo de descripción
    Label lblDescripcion = new Label("Descripción:");
    TextArea txtDescripcion = new TextArea();
    txtDescripcion.setPromptText("Ingrese la descripción del paquete");
    txtDescripcion.setPrefRowCount(3);
    txtDescripcion.setId("txtDescripcion"); // Agregar ID

    // Campo de dimensiones
    Label lblDimensiones = new Label("Dimensiones (cm):");
    HBox hbDimensiones = new HBox(10);
    TextField txtAlto = new TextField();
    txtAlto.setPromptText("Alto");
    txtAlto.setId("txtAlto"); // Agregar ID
    TextField txtAncho = new TextField();
    txtAncho.setPromptText("Ancho");
    txtAncho.setId("txtAncho"); // Agregar ID
    TextField txtLargo = new TextField();
    txtLargo.setPromptText("Largo");
    txtLargo.setId("txtLargo"); // Agregar ID
    hbDimensiones.getChildren().addAll(txtAlto, txtAncho, txtLargo);

    // Campo de peso
    Label lblPeso = new Label("Peso (kg):");
    TextField txtPeso = new TextField();
    txtPeso.setPromptText("Ingrese el peso");
    txtPeso.setId("txtPeso"); // Agregar ID

    // Botón para eliminar el formulario
    Button btnEliminar = new Button("Eliminar");
    btnEliminar.setStyle("-fx-background-color: red; -fx-text-fill: white;");

    // Crear el TitledPane con el formulario
    TitledPane nuevoPaquete = new TitledPane();
    nuevoPaquete.setText("Paquete #" + (vbPaquetes.getChildren().size() + 1));
    nuevoPaquete.setContent(formulario);

    // Configurar la acción del botón de eliminar
    btnEliminar.setOnAction(event -> {
        vbPaquetes.getChildren().remove(nuevoPaquete); // Eliminar el TitledPane directamente
        paquetesFormularios.remove(formulario);       // Eliminar el formulario de la lista global
    });

    // Agregar elementos al formulario
    formulario.getChildren().addAll(
            lblDescripcion, txtDescripcion,
            lblDimensiones, hbDimensiones,
            lblPeso, txtPeso,
            btnEliminar // Agregar el botón de eliminar al formulario
    );

    // Agregar el TitledPane al VBox principal
    vbPaquetes.getChildren().add(nuevoPaquete);

    // Guardar el nuevo TitledPane como el último
    ultimoPaquete = nuevoPaquete;

    // Guardar la referencia del formulario generado en una lista (para ser procesada luego)
    paquetesFormularios.add(formulario); // Lista global que contiene todos los formularios
}

    @FXML
    private void btnAceptarEnvio(ActionEvent event) {        
         if (cbNumeroGuia.getValue() != null && 
            cbNombreCliente.getValue() != null && 
            tfCostoEnvio.getText() != null && !tfCostoEnvio.getText().isEmpty()) {               
            String guiaSeleccionada = cbNumeroGuia.getValue();        
            guardarPaquetes(paquetes, tfCostoEnvio.getText(), guiaSeleccionada);             
            cbNumeroGuia.setValue(null);  // Limpiar el ComboBox de número de guía
            cbNombreCliente.setValue(null);  // Limpiar el ComboBox de nombre de cliente
            tfCostoEnvio.clear();  // Limpiar el TextField de costo de envío
            lbErrorSeleccionarEnvio.setText(""); 
        } else {         
            lbErrorSeleccionarEnvio.setText("No dejes ningún campo vacío.");            
        }                         
    }
}
