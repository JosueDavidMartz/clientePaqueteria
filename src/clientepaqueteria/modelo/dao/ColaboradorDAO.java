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
        String url = Constantes.URL_WS+"roles/obtenerRoles";
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
        String url = Constantes.URL_WS+"colaborador/registro";
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
        String url = Constantes.URL_WS + "colaborador/modificarColaborador";
        Gson gson = new Gson ();
        try {
            String parametros = gson.toJson(url);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
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
    
    public static Mensaje eliminarColaborador(String noPersonal) {
    Mensaje msj = new Mensaje();
    String url = Constantes.URL_WS + "colaborador/eliminar"; // URL para eliminar colaborador
    Gson gson = new Gson();
    
    // Crear un nuevo colaborador solo con el noPersonal
    Colaborador colaborador = new Colaborador();
    colaborador.setNumeroPersonal(noPersonal);
    
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

   }


