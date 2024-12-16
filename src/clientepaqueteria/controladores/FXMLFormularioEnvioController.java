/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.controladores;

import clientepaqueteria.interfaz.INotificadorOperacion;
import clientepaqueteria.modelo.dao.ClienteDAO;
import clientepaqueteria.modelo.dao.ColaboradorDAO;
import clientepaqueteria.modelo.dao.EnvioDAO;
import clientepaqueteria.modelo.dao.UnidadDAO;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaEnvio;
import clientepaqueteria.pojo.RespuestaUnidad;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
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
    private Map<String, Colaborador> conductorMap = new HashMap<>();
    private INotificadorOperacion observador;
    private ResultadoObtenerEnvio envio;
    private boolean modoEdicion = false;
    
    
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
    private ComboBox<String> cbConductor;
    @FXML
    private ComboBox<String> cbEstado;
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
        obtenerConductores();
        cargarDireccionOrigen();
    }    
    public void recibirConfiguracion(Colaborador colaborador, HBox hbSuperior, VBox vbMenu, StackPane spEscena, Label label, String nombre) {
        this.colaborador = colaborador;
        this.hbSuperior = hbSuperior;
        this.vbMenu = vbMenu;
        this.spEscena = spEscena;
        this.label = label;
        this.nombre = nombre;
    }
    
    void inicializarValores(INotificadorOperacion observador, ResultadoObtenerEnvio envio) {
       
        this.observador = observador;
        this.envio = envio;
        
        if(envio != null){
            modoEdicion = true;
            cargarDatosEdicion();
        }
        /*String infoOrigen = 
        "Calle: " + envio.getCalleOrigen() + "\n" +
        "Número: " + envio.getNumeroOrigen() + "\n" +
        "Colonia: " + envio.getColoniaOrigen() + "\n" +
        "Código Postal: " + envio.getCodigoPostalOrigen() + "\n" +
        "Ciudad: " + envio.getCiudadOrigen() + "\n" +
        "Estado: " + envio.getEstadoOrigen();
        taDireccionOrigen.setText(infoOrigen);*/
    }
    
    private void cargarDireccionOrigen(){
        String infoOrigen = 
        "Calle: " + "Carranza "+"\n" +
        "Número: " + "32" + "\n" +
        "Colonia: " + "Benito Juarez"+ "\n" +
        "Código Postal: " + "20900"+ "\n" +
        "Ciudad: " + "México"+ "\n" +
        "Estado: " + "México";
        taDireccionOrigen.setText(infoOrigen);
        taDireccionOrigen.setDisable(true);
        
        cbConductor.setDisable(true);
            cbEstado.setDisable(true);
            tfCosto.setVisible(false);
    }

    private void cargarDatosEdicion(){
        label.setText("Editar envio");
        cbConductor.setDisable(false);
        cbEstado.setDisable(false);
        tfCosto.setVisible(true);
        cbEstado.getItems().addAll("Pendiente","En tránsito","Detenido","Entregado","Cancelado");
        cbEstado.setValue(this.envio.getNombreSeguimiento());
        cbConductor.setValue(this.envio.getNombreConductor());
        
        tfCalleDestino.setText(this.envio.getCalleDestino());
        tfNumeroDestino.setText(this.envio.getNumeroDestino().toString());
        tfCodiigoPostalDestino.setText(this.envio.getCodigoPostalDestino().toString());
        tfColoniaDestino.setText(this.envio.getColoniaDestino());
        tfEstadoDestino.setText(this.envio.getEstadoDestino());
        tfCiudadDestino.setText(this.envio.getCiudadDestino());
        tfClienteFormularioEnvio.setValue(this.envio.getNombreCliente());
        if (this.envio != null && this.envio.getCostoEnvio() != null) {
            tfCosto.setText(this.envio.getCostoEnvio().toString());
        } else {            
            tfCosto.setText(""); 
        }

        
        
        
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
    
     public void obtenerConductores(){
        
        
        List<Colaborador> listaConductores = new ArrayList<>();
        listaConductores = ColaboradorDAO.obtenerConductores();
        if (listaConductores != null) {
            for (Colaborador colaborador : listaConductores) {
                cbConductor.getItems().add(colaborador.getNombre());
                conductorMap.put(colaborador.getNombre(), colaborador);
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
        boolean respuesta = Utilidades.mostrarConfirmacion("Confirmar", "Si cancelas perderás los cambios no guardados");
        if(respuesta){
        paneNumeroGuia.setVisible(false);
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envios");
        }
    }

    /*@FXML
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
            

            Envio envioNuevo = new Envio();
            envioNuevo.setIdCliente(idCliente);
            envioNuevo.setIdColaborador(colaborador.getIdColaborador());
            
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
            
            
            if(!modoEdicion){
                envioNuevo.setDireccionOrigen(Utilidades.direccionOrigen());
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
            }else{
                int idEnvio = this.envio.getIdEnvio();
                envioNuevo.setIdEnvio(idEnvio);
                envioNuevo.setIdColaborador(this.envio.getIdColaborador());
                envioNuevo.getSeguimiento().setNombre(cbEstado.getValue().toString());
                envioNuevo.getDireccionOrigen().setCalle(envio.getCalleOrigen());
                envioNuevo.getDireccionOrigen().setColonia(envio.getColoniaOrigen());
                envioNuevo.getDireccionOrigen().setNumero(envio.getNumeroOrigen());
                envioNuevo.getDireccionOrigen().setCiudad(envio.getCiudadOrigen());
                envioNuevo.getDireccionOrigen().setEstado(envio.getEstadoOrigen());
                envioNuevo.getDireccionOrigen().setCodigoPostal(envio.getCodigoPostalOrigen());
                
  
                
                RespuestaEnvio msj = EnvioDAO.modificar(envioNuevo);
                if(!msj.isError()){
                    Utilidades.mostrarAlerta("Unidad actualizada", "La informacion ha sido actualizada correctamente", Alert.AlertType.INFORMATION);
                    cerrarVentana();
                    Utilidades.reducirInterfaz(hbSuperior, vbMenu, spEscena, label, nombre);
                    observador.notificarOperacionExitosa("Actualizar", envioNuevo.getComentario());
                }else{
                    Utilidades.mostrarAlerta("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
                }
            } 
            
            
        } else {
            // Muestra un mensaje de advertencia al usuario
            Utilidades.mostrarAlerta("Campos vacios", "Asegurate de llenar todos los campos", Alert.AlertType.INFORMATION);
        }


        
    }*/
        @FXML
    private void btnAceptar(ActionEvent event) {
        if (validarCamposVacios()) {
            Envio envioNuevo = construirEnvioDesdeFormulario();

            if (!modoEdicion) {
                crearEnvio(envioNuevo);
            } else {
                modificarEnvio(envioNuevo);
            }
        } else {
            Utilidades.mostrarAlerta("Campos vacíos", "Asegúrate de llenar todos los campos", Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Construye un objeto Envio basado en los datos ingresados en el formulario.
     */
    private Envio construirEnvioDesdeFormulario() {
        String nombreSeleccionado = tfClienteFormularioEnvio.getValue();
        int idCliente = 0;

        if (nombreSeleccionado != null) {
            Cliente clienteSeleccionado = clienteMap.get(nombreSeleccionado);
            if (clienteSeleccionado != null) {
                idCliente = clienteSeleccionado.getIdCliente();
            }
        }

        Envio envio = new Envio();
        envio.setIdCliente(idCliente);
        envio.setIdColaborador(colaborador.getIdColaborador());

        envio.getDireccionDestino().setCalle(tfCalleDestino.getText());
        envio.getDireccionDestino().setNumero(Integer.parseInt(tfNumeroDestino.getText()));
        envio.getDireccionDestino().setColonia(tfColoniaDestino.getText());
        envio.getDireccionDestino().setEstado(tfEstadoDestino.getText());
        envio.getDireccionDestino().setCiudad(tfCiudadDestino.getText());
        envio.getDireccionDestino().setCodigoPostal(Integer.parseInt(tfCodiigoPostalDestino.getText()));

        envio.getSeguimiento().setIdColaborador(colaborador.getIdColaborador());
        envio.getSeguimiento().setNombre("Pendiente");
        envio.getSeguimiento().setFecha(LocalDate.now().toString());
        envio.getSeguimiento().setHora(LocalTime.now().toString());

        return envio;
    }

  
    private void crearEnvio(Envio envioNuevo) {
        envioNuevo.setDireccionOrigen(Utilidades.direccionOrigen());
        envioNuevo.getDireccionDestino().setTipo("Destino");
        
        RespuestaEnvio resultado = EnvioDAO.crearEnvio(envioNuevo);

        if (!resultado.isError()) {
            Utilidades.mostrarAlerta("Creado", "El envío fue creado con éxito", Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Crear", resultado.getNumeroGuia());
            lbNumeroGuia.setText(resultado.getNumeroGuia());
            paneNumeroGuia.setVisible(true);

            limpiarFormulario();
            
        } else {
            Utilidades.mostrarAlerta("Error al crear envío", resultado.getMensaje(), Alert.AlertType.ERROR);
        }
    }


    private void modificarEnvio(Envio envioNuevo) {
        envioNuevo.setIdEnvio(this.envio.getIdEnvio());
        envioNuevo.setIdColaborador(this.envio.getIdColaborador());
        envioNuevo.getSeguimiento().setNombre(cbEstado.getValue());
        if (tfCosto.getText() == null || tfCosto.getText().trim().isEmpty()) {
             envioNuevo.setCostoEnvio(0f);
        } else {            
            float costo = Float.parseFloat(tfCosto.getText());
            envioNuevo.setCostoEnvio(costo);
          
        }


   
        envioNuevo.setDireccionOrigen(Utilidades.direccionOrigen());
        envioNuevo.getDireccionDestino().setTipo("Destino");
        int idConductor = 0;
        
        String nombreSeleccionado = cbConductor.getValue();
        if (nombreSeleccionado != null) {
            Colaborador colaboradorSeleccionado = conductorMap.get(nombreSeleccionado);
            if (colaboradorSeleccionado != null) {
                idConductor = colaboradorSeleccionado.getIdColaborador();
            }
        }
        
        envioNuevo.setIdConductor(idConductor);
        RespuestaEnvio msj = EnvioDAO.modificar(envioNuevo);
        Mensaje msjSeguimiento = EnvioDAO.asignarSeguimiento(envioNuevo);
        
        if (!msj.isError()) {
            Utilidades.mostrarAlerta("Actualización exitosa", "La información ha sido actualizada correctamente\n" + msjSeguimiento.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Actualizar", envio.getNumeroGuia());
        } else {
            Utilidades.mostrarAlerta("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }


    private void limpiarFormulario() {
        tfCalleDestino.setText("");
        tfNumeroDestino.setText("");
        tfColoniaDestino.setText("");
        tfCodiigoPostalDestino.setText("");
        tfEstadoDestino.setText("");
        tfCiudadDestino.setText("");
        tfClienteFormularioEnvio.setValue(null);
    }


    private void btnAceptarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    private void btnCancelarVinculacion(ActionEvent event) {
        paneNumeroGuia.setVisible(true);
    }

    @FXML
    private void ivCopiarNumeroGuia(MouseEvent event) {
        // Obtener el texto del TextField
        String texto = lbNumeroGuia.getText();

        // Verificar que el texto no esté vacío
        if (texto != null && !texto.isEmpty()) {
            // Obtener el portapapeles del sistema
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(texto);  // Colocar el texto en el portapapeles
            clipboard.setContent(content);  // Establecer el contenido en el portapapeles
            
        }
    }

    @FXML
    private void btnAceptarNumeroGuia(ActionEvent event) {
        paneNumeroGuia.setVisible(false);
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envio");
    }

   
    
    private void cerrarVentana() {
       
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        Utilidades.reducirInterfaz(hbSuperior, vbMenu, stackPane, label, "Envios");
        
    }


   
    
}
