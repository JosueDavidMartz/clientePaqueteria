package clientepaqueteria.controladores;

import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
        private ComboBox<?> cbNombreClienteVincular;
        @FXML
        private ComboBox<?> cbNumeroGuiaVincular;
        @FXML
        private ComboBox<?> cbConductorVincular;
        @FXML
        private TextField tfCostoVincular;
        @FXML
        private VBox vbPaquetes;

        
        @FXML
        private ScrollPane scContenedor;

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

            // Campo de dimensiones
            Label lblDimensiones = new Label("Dimensiones (cm):");
            HBox hbDimensiones = new HBox(10);
            TextField txtAlto = new TextField();
            txtAlto.setPromptText("Alto");
            TextField txtAncho = new TextField();
            txtAncho.setPromptText("Ancho");
            TextField txtLargo = new TextField();
            txtLargo.setPromptText("Largo");
            hbDimensiones.getChildren().addAll(txtAlto, txtAncho, txtLargo);

            // Campo de peso
            Label lblPeso = new Label("Peso (kg):");
            TextField txtPeso = new TextField();
            txtPeso.setPromptText("Ingrese el peso");

            // Botón para acciones
            Button btnGuardar = new Button("Guardar");
            btnGuardar.setOnAction(e -> {
                System.out.println("Guardando paquete...");
                System.out.println("Descripción: " + txtDescripcion.getText());
                System.out.println("Dimensiones: Alto=" + txtAlto.getText() + ", Ancho=" + txtAncho.getText() + ", Largo=" + txtLargo.getText());
                System.out.println("Peso: " + txtPeso.getText());
            });

            // Agregar elementos al formulario
            formulario.getChildren().addAll(
                lblDescripcion, txtDescripcion,
                lblDimensiones, hbDimensiones,
                lblPeso, txtPeso,
                btnGuardar
            );

            // Crear el TitledPane con el formulario
            TitledPane nuevoPaquete = new TitledPane();
            nuevoPaquete.setText("Paquete #" + (vbPaquetes.getChildren().size() + 1));
            nuevoPaquete.setContent(formulario);

            // Agregar el TitledPane al VBox principal
            vbPaquetes.getChildren().add(nuevoPaquete);


        }

        @FXML
        private void btnAceptarVinculacion(ActionEvent event) {
        }

        @FXML
        private void btnCancelarVinculacion(ActionEvent event) {
        }

    }
