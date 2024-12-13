package clientepaqueteria.controladores;

import clientepaqueteria.modelo.dao.PaqueteDAO;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Paquete;
import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class FXMLFormularioPaquetesController implements Initializable {
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
    private ComboBox<?> cbNombreCliente;
    @FXML
    private ComboBox<?> cbConductorEnvio;
    @FXML
    private TextField tfCostoEnvio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scContenedor.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scContenedor.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scContenedor.setFitToWidth(true);
    }

    public void recibirConfiguracion(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    @FXML
    private void btnCancelarPaquete(ActionEvent event) {
        // Remueve la vista actual del StackPane
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Paquetes");
    }

    @FXML
    private void btnAceptarPaquete(ActionEvent event) {
        // 1. Mostrar el formulario de vinculación de envío (paneVincularEnvio)
        paneVincularEnvio.setVisible(true);

        // 2. Capturar los datos del formulario de paquetes
        for (int i = 0; i < vbPaquetes.getChildren().size(); i++) {
            TitledPane titledPane = (TitledPane) vbPaquetes.getChildren().get(i);
            VBox formulario = (VBox) titledPane.getContent();

            // Obtener los controles del formulario
            TextArea txtDescripcion = (TextArea) formulario.lookup("#txtDescripcion");
            TextField txtAlto = (TextField) formulario.lookup("#txtAlto");
            TextField txtAncho = (TextField) formulario.lookup("#txtAncho");
            TextField txtLargo = (TextField) formulario.lookup("#txtLargo");
            TextField txtPeso = (TextField) formulario.lookup("#txtPeso");

            // Verificar que no haya campos vacíos
            String descripcion = txtDescripcion.getText();
            String alto = txtAlto.getText();
            String ancho = txtAncho.getText();
            String largo = txtLargo.getText();
            String peso = txtPeso.getText();

            if (descripcion.isEmpty() || alto.isEmpty() || ancho.isEmpty() || largo.isEmpty() || peso.isEmpty()) {
                // Si hay algún campo vacío, mostrar un mensaje de error y detener el proceso
                Utilidades.mostrarAlertaSimple("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
                return;
            }

            // Crear un nuevo objeto Paquete con los datos del formulario
            Paquete paquete = new Paquete();
            paquete.setDescripcion(descripcion);
            paquete.setDimensionAlto(Float.parseFloat(alto));
            paquete.setDimensionAncho(Float.parseFloat(ancho));
            paquete.setDimensionProfundidad(Float.parseFloat(largo));
            paquete.setPeso(Float.parseFloat(peso));

            // Registrar el paquete usando el DAO
            Mensaje mensaje = PaqueteDAO.registrarPaquete(paquete);

            if (!mensaje.isError()) {
                // Si se guarda correctamente, mostrar un mensaje de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Paquete Guardado");
                alert.setHeaderText(null);
                alert.setContentText("El paquete #" + (i + 1) + " ha sido guardado correctamente.");
                alert.showAndWait();
            } else {
                // Si ocurre un error, mostrar el mensaje de error
                Utilidades.mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }

    /*@FXML
    private void btnAceptarEnvio(ActionEvent event) {
        // 3. Mostrar un mensaje de confirmación antes de guardar la información
        String cliente = (String) cbNombreCliente.getValue();
        String guia = (String) cbNumeroGuia.getValue();
        String conductor = (String) cbConductorEnvio.getValue();
        String costoEnvio = tfCostoEnvio.getText();

        // Verificar que los campos no estén vacíos
        if (cliente == null || guia == null || conductor == null || costoEnvio.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        // Crear el mensaje de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de que deseas guardar esta información?");
        alert.setContentText("Cliente: " + cliente + "\nNúmero de guía: " + guia + "\nConductor: " + conductor + "\nCosto: " + costoEnvio);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // 4. Guardar los datos en la base de datos (Simulando el proceso)
                // Crear el objeto de envío y guardar los datos
                Envio envio = new Envio();
                envio.setCliente(cliente);
                envio.setNumeroGuia(guia);
                envio.setConductor(conductor);
                envio.setCosto(Float.parseFloat(costoEnvio));

                Mensaje mensajeEnvio = PaqueteDAO.registrarEnvio(envio);
                if (!mensajeEnvio.isError()) {
                    // Mostrar mensaje de éxito
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Envío Guardado");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("El envío ha sido guardado correctamente.");
                    successAlert.showAndWait();
                } else {
                    // Si ocurre un error, mostrar mensaje de error
                    Utilidades.mostrarAlertaSimple("Error", mensajeEnvio.getMensaje(), Alert.AlertType.ERROR);
                }

                // 5. Regresar a la vista de paquetes
                btnCancelarEnvio(event);
            }
        });
    }*/

    @FXML
    private void btnCancelarEnvio(ActionEvent event) {
        // Ocultar el formulario de vinculación de envío
        paneVincularEnvio.setVisible(false);
    }

    @FXML
    private void btnAgregar(MouseEvent event) {
        agregarPaquete();
    }

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
        txtDescripcion.setId("txtDescripcion");  // Asignar ID para poder acceder después

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
    }

    @FXML
    private void btnAceptarEnvio(ActionEvent event) {
    }

}