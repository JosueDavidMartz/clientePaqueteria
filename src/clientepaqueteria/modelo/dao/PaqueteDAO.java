/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.modelo.dao;

import cientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Paquete;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.Rol;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author Win10
 */
public class PaqueteDAO {
    public static List<Paquete> obtenerPaquetes(){
        List<Paquete> paquetes = null;
        //traer lo elementos o invocar el servicio
        String url = Constantes.URL_WS + "paquetes/obtenerPaquetes";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        //para trabajar con la respuesta solo me interesa el codigo 200
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){
                Gson gson = new Gson();//crear objeto de tipo Gson
                Type tipoLista = new TypeToken<List<Paquete>>(){}.getType();// las {} es porque algo debes configurar
                paquetes = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }return paquetes;
    }
    
    
    public static Mensaje registrarPaquete(Paquete paquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/registrar";
        Gson gson = new Gson();
        try{
            String parametros = gson.toJson(paquete);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        }catch(Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    public static Mensaje modificar(Paquete paquete) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/modificar";
        Gson gson = new Gson();
        try{
           String parametros = gson.toJson(paquete);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);//crear el objeto mensaje que viene de respuesta
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());//le mnado como mnesjae el contenido
            }
        }catch(Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    public static Mensaje eliminarPaquete(Integer idPaquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/eliminar";
        Gson gson = new Gson();
        try{
            String parametros = String.format("idPaquete=%d", idPaquete);
            RespuestaHTTP respuesta = ConexionWS.peticionDELETE(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);//crear el objeto mensaje que viene de respuesta
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());//le mnado como mnesjae el contenido
            }
        }catch(Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
}
