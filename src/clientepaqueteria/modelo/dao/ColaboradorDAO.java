package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.Rol;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import javafx.collections.ObservableList;

public class ColaboradorDAO {
    
    public static List<Colaborador> obtenerColaboradores(){
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS+"colaborador/obtenerColaboradores";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        try{
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(),tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return colaboradores;
    }
    
    public static List<Rol> obtenerRolesColaborador(){
        List <Rol> roles = null;
        String url = Constantes.URL_WS+"colaborador/obtenerRoles";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        try{
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Rol>>(){}.getType();
                roles = gson.fromJson(respuesta.getContenido(),tipoLista);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roles;
    }
    
    public static Mensaje registrarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"colaborador/registroColaboradores";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(colaborador);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            if (respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        }catch (Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    public static Mensaje modificar(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaborador/modificarColaboradores";
        Gson gson = new Gson ();
        try {
            String parametros = gson.toJson(colaborador);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
            if (respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        }catch (Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    public static Mensaje eliminarColaborador(int idColaborador) {
    Mensaje msj = new Mensaje();
    String url = Constantes.URL_WS + "colaborador/eliminarColaborador"; // URL para eliminar colaborador
    Gson gson = new Gson();
    
    // Crear un nuevo colaborador solo con el noPersonal
    Colaborador colaborador = new Colaborador();
    colaborador.setIdColaborador(idColaborador);
    
    try {
        // Convertir el objeto colaborador a JSON
        String parametros = gson.toJson(colaborador);
        RespuestaHTTP respuesta = ConexionWS.peticionDELETEJson(url, parametros); // Asegúrate de que este método esté implementado
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            msj.setError(true);
            msj.setMensaje(respuesta.getContenido());
        }
    } catch (Exception e) {
        msj.setError(true);
        msj.setMensaje(e.getMessage());
    }
    return msj;
}
    
public static Mensaje buscarColaborador(String numeroPersonal) {
    Mensaje msj = new Mensaje();
    String url = Constantes.URL_WS + "colaborador/buscarColaborador"; // URL del endpoint
    Gson gson = new Gson();
    
    try {
        // Enviar la solicitud con los parámetros en formato JSON
        String jsonInputString = "{\"numeroPersonal\":\"" + numeroPersonal + "\"}";
        RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, jsonInputString);
        
        // Validar la respuesta del servidor
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            // Deserializa la respuesta en una lista de colaboradores
            Type listType = new TypeToken<List<Colaborador>>() {}.getType();
            List<Colaborador> colaboradores = gson.fromJson(respuesta.getContenido(), listType);
            
            if (colaboradores != null && !colaboradores.isEmpty()) {
                msj.setError(false);
                msj.setMensaje("Colaboradores encontrados");
            } else {
                msj.setError(true);
                msj.setMensaje("No se encontraron colaboradores.");
            }
        } else {
            msj.setError(true);
            msj.setMensaje("Error en la respuesta del servidor: " + respuesta.getContenido()); // Respuesta detallada en caso de error
        }
    } catch (Exception e) {
        msj.setError(true);
        msj.setMensaje("Error: " + e.getMessage());
    }

    return msj;
}



}


