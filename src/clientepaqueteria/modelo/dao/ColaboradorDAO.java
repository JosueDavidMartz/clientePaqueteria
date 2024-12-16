package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.Rol;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
    String url = Constantes.URL_WS + "colaborador/eliminarColaborador"; 
    Gson gson = new Gson();
    
    Colaborador colaborador = new Colaborador();
    colaborador.setIdColaborador(idColaborador);
    
    try {
        // Convertir el objeto colaborador a JSON
        String parametros = gson.toJson(colaborador);
        RespuestaHTTP respuesta = ConexionWS.peticionDELETEJson(url, parametros); 
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
    
   public static Mensaje subirFoto(int idColaborador, byte[] fotoBlob) {
    Mensaje msj = new Mensaje();
    String url = Constantes.URL_WS + "colaborador/subirFoto/" + idColaborador;
    try {
        RespuestaHTTP respuesta = ConexionWS.peticionPUTBinary(url, fotoBlob);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
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





public static Colaborador obtenerFotoBase64(int idColaborador) {
    Colaborador colaborador= null;
    String url = Constantes.URL_WS + "colaborador/obtenerFoto/" + idColaborador;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            colaborador = gson.fromJson(respuesta.getContenido(), Colaborador.class);
        }
        return colaborador;
    }

public static List<Colaborador> obtenerConductores() {
        List <Colaborador> conductores = null;
        String url = Constantes.URL_WS+"colaborador/obtenerConductores";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        try{
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();
                conductores = gson.fromJson(respuesta.getContenido(),tipoLista);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return conductores;
    }
}




