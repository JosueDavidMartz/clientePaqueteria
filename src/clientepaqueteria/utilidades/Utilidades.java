package clientepaqueteria.utilidades;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Utilidades {
    
    public static void expandirInterfaz(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        // Configuraciones que estaban en tu controlador
        vbMenu.setVisible(false);
        hbSuperior.setPrefWidth(946);
        hbSuperior.setLayoutX(0);
        spEscena.setLayoutX(0);
        spEscena.setPrefWidth(946);
        label.setText(nombre);
    }
    
    public static void reducirInterfaz(HBox hbSuperior, VBox vbMenu, StackPane spEscena,Label label, String nombre) {
        // Configuraciones que estaban en tu controlador
        vbMenu.setVisible(true);
        hbSuperior.setPrefWidth(786);
        hbSuperior.setLayoutX(160);
        spEscena.setLayoutX(160);
        spEscena.setPrefWidth(786);
        label.setText(nombre);
    }

    public static void mostrarAlertaSimple(String error, String pro_el_momento_no_se_puede_mostrar_la_pan, Alert.AlertType alertType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
