
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


/*    @FXML
    private void btnIniciarSesion(ActionEvent event) {
         try{
            Stage escenarioBase = (Stage) lbErrorIniciarSesion.getScene().getWindow();
            Parent principal = FXMLLoader.load(getClass().getResource("/clientepaqueteria/vistas/FXMLInicio.fxml"));

            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Inicio");
            escenarioBase.show();
        }catch (IOException ex){
            Utilidades.mostrarAlerta("Error", "Pro el momento no se puede mostrar la pantalla principal", Alert.AlertType.ERROR);

*/
     @FXML
    private void clicIngresar(ActionEvent event) {
        //obtener resultados
        String noPersonal = tfNumeroPersonal.getText();
        String contraseña = tfContrasenia.getText();
        
        if(validarCampos(noPersonal, contraseña)){
            verificaCredencialesAcceso(noPersonal, contraseña); //se llama el metodo

        }
    }
    
    private boolean validarCampos(String noPersonal, String contraseña){
        boolean camposValidos = true;
        lbErrorNumeropersonal.setText("");
        lbErrorContraseña.setText("");
        if(noPersonal.isEmpty()){
            camposValidos = false;
            lbErrorNumeropersonal.setText("Numero Personal obligatorio");
        }
        if(contraseña.isEmpty()){
            camposValidos = false;
            lbErrorContraseña.setText("Contraseña obligatoria");
        }
        return camposValidos;
    }
    
    // Verifica que existen
    private void verificaCredencialesAcceso(String noPersonal, String contraseña) {
        Login respuestaLogin = LoginDAO.iniciarsesion(noPersonal, contraseña);
        if (!respuestaLogin.isError()) {
            
            // Obtener el colaborador del Login
            Colaborador colaborador = respuestaLogin.getColaborador();
            
           
            irPantallaPrincipal(colaborador);
        } else {
            Utilidades.mostrarAlerta("Credenciales incorrectas", 
                "Número personal y/o contraseña incorrectos", 
                Alert.AlertType.ERROR);
       }
    }

    private void irPantallaPrincipal(Colaborador colaborador) {
        try {
        // Cargar la vista de la pantalla principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientepaqueteria/vistas/FXMLInicio.fxml"));
        Parent principal = loader.load();
        
        // Obtener el controlador de la nueva pantalla
        FXMLInicioController controlador = loader.getController();
        
        // Pasar el objeto Colaborador al controlador de la pantalla principal
        controlador.obtenerInformacionColaborador(colaborador);

        // Configurar el escenario base (ventana principal)
        Stage escenarioBase = (Stage) tfNumeroPersonal.getScene().getWindow();

        // Registrar el escenario en WindowManager
        controlador.setEscenario(escenarioBase); // Registrar este escenario en el controlador
        WindowManager.registrarVentana("inicio", escenarioBase); // Guardar el escenario con un identificador

        // Cambiar la escena a la pantalla principal
        Scene escenaPrincipal = new Scene(principal);
        escenarioBase.setScene(escenaPrincipal);
        escenarioBase.setTitle("Pantalla Principal");
        escenarioBase.show();
    } catch (IOException ex) {
        Utilidades.mostrarAlerta(
            "Error", 
            "Por el momento no se puede mostrar la pantalla principal", 
            Alert.AlertType.ERROR
        );
        ex.printStackTrace(); // Opcional: para facilitar la depuración
    }
}
}