package clientepaqueteria.utilidades;

import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Utilidades {
    
    public static void expandirInterfaz(HBox hbSuperior, VBox vbMenu, StackPane spEscena) {
        // Configuraciones que estaban en tu controlador
        vbMenu.setVisible(false);
        hbSuperior.setPrefWidth(946);
        hbSuperior.setLayoutX(0);
        spEscena.setLayoutX(0);
        spEscena.setPrefWidth(946);
    }
}
