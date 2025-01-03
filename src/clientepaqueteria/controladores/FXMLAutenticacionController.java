package clientepaqueteria.controladores;

import clientepaqueteria.modelo.dao.LoginDAO;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Login;
import clientepaqueteria.utilidades.Utilidades;
import clientepaqueteria.utilidades.WindowManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLAutenticacionController implements Initializable {

    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private PasswordField tfContrasenia;
    @FXML
    private Label lbErrorContraseña;
    @FXML
    private Label lbErrorNumeropersonal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicIngresar(ActionEvent event) {
        String noPersonal = tfNumeroPersonal.getText();
        String contraseña = tfContrasenia.getText();

        if (validarCampos(noPersonal, contraseña)) {
            verificaCredencialesAcceso(noPersonal, contraseña);
        }
    }

    private boolean validarCampos(String noPersonal, String contraseña) {
        boolean camposValidos = true;
        lbErrorNumeropersonal.setText("");
        lbErrorContraseña.setText("");
        if (noPersonal.isEmpty()) {
            camposValidos = false;
            lbErrorNumeropersonal.setText("Numero Personal obligatorio");
        }
        if (contraseña.isEmpty()) {
            camposValidos = false;
            lbErrorContraseña.setText("Contraseña obligatoria");
        }
        return camposValidos;
    }

    private void verificaCredencialesAcceso(String noPersonal, String contraseña) {
        Login respuestaLogin = LoginDAO.iniciarsesion(noPersonal, contraseña);
        if (!respuestaLogin.isError()) {
            Colaborador colaborador = respuestaLogin.getColaborador();
            
            if(colaborador.getRol().equals("Conductor")){
                Utilidades.mostrarAlerta("Usuario no autorizado",
                    "No tienes permisos para acceder al sistema",
                    Alert.AlertType.ERROR);
                return;
            }
            
            irPantallaPrincipal(colaborador);
        } else {
            Utilidades.mostrarAlerta("Credenciales incorrectas",
                    "Número personal y/o contraseña incorrectos",
                    Alert.AlertType.ERROR);
        }
    }

    private void irPantallaPrincipal(Colaborador colaborador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLInicio.fxml"));
            Parent principal = loader.load();

            FXMLInicioController controlador = loader.getController();

            controlador.obtenerInformacionColaborador(colaborador);
          

            Stage escenarioBase = (Stage) tfNumeroPersonal.getScene().getWindow();

            controlador.setEscenario(escenarioBase); // Registrar este escenario en el controlador
            WindowManager.registrarVentana("inicio", escenarioBase); // Guardar el escenario con un identificador

            // Cambiar la escena a la pantalla principal
            Scene escenaPrincipal = new Scene(principal);
            escenaPrincipal.getStylesheets().add(
                    getClass().getResource("/clientepaqueteria/recursos/estilos.css").toExternalForm()
            );
            escenarioBase.setScene(escenaPrincipal);              
            escenarioBase.setTitle("Pantalla Principal");
            escenarioBase.setX(50);
            escenarioBase.setY(30);
            escenarioBase.show();
        } catch (IOException ex) {
            Utilidades.mostrarAlerta(
                    "Error",
                    "Por el momento no se puede mostrar la pantalla principal",
                    Alert.AlertType.ERROR
            );
            ex.printStackTrace();
        }
    }
}
