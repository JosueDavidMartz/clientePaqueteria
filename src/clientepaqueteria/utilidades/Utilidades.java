package clientepaqueteria.utilidades;


import clientepaqueteria.pojo.Direccion;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.util.Optional;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Utilidades {
    
    /*public static void expandirInterfaz(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        // Configuraciones que estaban en tu controlador
        vbMenu.setVisible(false);
        hbSuperior.setPrefWidth(946);
        hbSuperior.setLayoutX(0);
        spEscena.setLayoutX(0);
        spEscena.setPrefWidth(946);
        label.setText(nombre);
    }*/
    public static void expandirInterfaz(HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        // Configuraciones que estaban en tu controlador
        vbMenu.setVisible(false);
        hbSuperior.setPrefWidth(1250);
        hbSuperior.setLayoutX(0);
        spEscena.setLayoutX(0);
        spEscena.setPrefWidth(946);
        label.setText(nombre);
    }
    
    public static void reducirInterfaz(HBox hbSuperior, VBox vbMenu, StackPane spEscena,Label label, String nombre) {
        // Configuraciones que estaban en tu controlador
        vbMenu.setVisible(true);
        hbSuperior.setPrefWidth(1090);
        hbSuperior.setLayoutX(160);
        spEscena.setLayoutX(160);
        //spEscena.setPrefWidth(786);
        spEscena.setPrefWidth(1090);
        label.setText(nombre);
    }


    
    public static void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo){

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);

        alerta.showAndWait();
    }
    

    public static boolean mostrarConfirmacion(String titulo, String contenido){

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        Optional<ButtonType> btnSeleccionado = alerta.showAndWait();

        return (btnSeleccionado.get() == ButtonType.OK);
    }

    public static Direccion direccionOrigen(){
        Direccion origen = new Direccion();
        origen.setTipo("Origen");
        origen.setCalle("Carranza");
        origen.setNumero(32);
        origen.setColonia("Benito Juarez");
        origen.setCodigoPostal(20900);
        origen.setCiudad("Mexico");
        origen.setEstado("Mexico");        
        return origen;
    }
}
