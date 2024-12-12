/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.controladores;

import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.modelo.dao.EnvioDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaEnvio;
import clientepaqueteria.utilidades.Utilidades;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author WIN 10
 */
public class FXMLFormularioEnvioController implements Initializable {
    
    private Colaborador colaborador;
    private Map<String, Cliente> clienteMap = new HashMap<>();
    
    
    private StackPane stackPane;
    HBox hbSuperior;
    VBox vbMenu;
    StackPane spEscena;
    Label label;
    String nombre;

    @FXML
    private TextField tfCalleDestino;
    @FXML
    private TextField tfColoniaDestino;
    @FXML
    private TextField tfNumeroDestino;
    @FXML
    private TextField tfCodiigoPostalDestino;
    @FXML
    private TextField tfEstadoDestino;
    @FXML
    private TextField tfCiudadDestino;
    private Pane paneVincularEnvio;
    @FXML
    private Pane paneNumeroGuia;
    @FXML
    private Label lbNumeroGuia;
    @FXML
    private ImageView ivCopiarNumeroGuia;
    @FXML
    private ComboBox<?> cbConductor;
    @FXML
    private ComboBox<?> cbEstado;
    @FXML
    private ComboBox<String> tfClienteFormularioEnvio;
    @FXML
    private TextField tfCosto;
    @FXML
    private TextArea taDireccionOrigen;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        obtenerClientes();
    }    
    void recibirConfiguracion(Colaborador colaborador, HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.colaborador = colaborador;
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }
    
    
    void setStackPane(StackPane spEscena) {
        this.stackPane = spEscena;
    }
    
    public void obtenerClientes(){
        
        
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes = ClienteDAO.obtenerClientes();
        if (listaClientes != null) {
            for (Cliente cliente : listaClientes) {
                tfClienteFormularioEnvio.getItems().add(cliente.getNombre());
                clienteMap.put(cliente.getNombre(), cliente);
            }
        }
    }

    public boolean validarCamposVacios() {
        TextField[] campos = {
            tfCalleDestino, tfColoniaDestino, 
            tfNumeroDestino, tfCodiigoPostalDestino, tfEstadoDestino, tfCiudadDestino
        };

        for (TextField campo : campos) {
            if (campo.getText() == null || campo.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }


    
    @FXML
    private void btnCancelar(ActionEvent event) {
        paneNumeroGuia.setVisible(false);
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envios");
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
  
        if (validarCamposVacios()) {
            String nombreSeleccionado = tfClienteFormularioEnvio.getValue();
            int idCliente = 0;
            if (nombreSeleccionado != null) { 
                Cliente clienteSeleccionado = clienteMap.get(nombreSeleccionado);
             if (clienteSeleccionado != null) { 
                 idCliente = clienteSeleccionado.getIdCliente();
             }            
            }
            
            
            System.out.println("calle: "+tfCalleDestino.getText());
            Envio envioNuevo = new Envio();
            envioNuevo.setIdCliente(idCliente);
            envioNuevo.setIdColaborador(colaborador.getIdColaborador());
            envioNuevo.setDireccionOrigen(Utilidades.direccionOrigen());
            envioNuevo.getDireccionDestino().setCalle(tfCalleDestino.getText());
            envioNuevo.getDireccionDestino().setNumero(Integer.parseInt(tfNumeroDestino.getText()));
            envioNuevo.getDireccionDestino().setColonia(tfColoniaDestino.getText());
            envioNuevo.getDireccionDestino().setEstado(tfEstadoDestino.getText());
            envioNuevo.getDireccionDestino().setCiudad(tfCiudadDestino.getText());
            envioNuevo.getDireccionDestino().setCodigoPostal(Integer.parseInt(tfCodiigoPostalDestino.getText()));
            
            envioNuevo.getSeguimiento().setIdColaborador(colaborador.getIdColaborador());
            envioNuevo.getSeguimiento().setNombre("pendiente");
           // Obtener fecha y hora actuales del sistema 
           LocalDate fechaActual = LocalDate.now(); 
           LocalTime horaActual = LocalTime.now(); 
           envioNuevo.getSeguimiento().setFecha(fechaActual.toString()); 
           envioNuevo.getSeguimiento().setHora(horaActual.toString());
            
            
            RespuestaEnvio resultado = EnvioDAO.crearEnvio(envioNuevo);
            
            if(!resultado.isError()){
                Utilidades.mostrarAlerta("Creado", "El envio fue creado con exito", Alert.AlertType.INFORMATION);
                
                lbNumeroGuia.setText(resultado.getNumeroGuia());
                
                paneNumeroGuia.setVisible(true);
                tfCalleDestino.setText("");
                tfColoniaDestino.setText("");
                tfNumeroDestino.setText("");
                tfCodiigoPostalDestino.setText(""); 
                tfEstadoDestino.setText("");
                tfCiudadDestino.setText("");
                tfClienteFormularioEnvio.setValue(null);
                
            }
        } else {
            // Muestra un mensaje de advertencia al usuario
            Utilidades.mostrarAlerta("Campos vacios", "Asegurate de llenar todos los campos", Alert.AlertType.INFORMATION);
        }


        
    }

    private void btnAceptarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    private void btnCancelarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    @FXML
    private void ivCopiarNumeroGuia(MouseEvent event) {
    }

    @FXML
    private void btnAceptarNumeroGuia(ActionEvent event) {
        paneNumeroGuia.setVisible(false);
    }

    

   
    
}
