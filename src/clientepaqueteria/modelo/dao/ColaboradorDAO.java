/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.modelo.dao;

import cientepaqueteria.modelo.ConexionWS;
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

/**
 *
 * @author Win10
 */
public class ColaboradorDAO {
    public static List<Colaborador> obtenerColaborador(){
        List<Colaborador> colaboradores = null;
        //traer lo elementos o invocar el servicio
        String url = Constantes.URL_WS + "colaboradores/obtenerColaboradores";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        //para trabajar con la respuesta solo me interesa el codigo 200
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){
                Gson gson = new Gson();//crear objeto de tipo Gson
                Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();// las {} es porque algo debes configurar
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }return colaboradores;
    }
    
    public static List<Rol> obtenerRolesColaborador(){
        List<Rol> roles = null; //si viene null o sin contenido manda una lista nula
        String url = Constantes.URL_WS + "roles/obtener-roles";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        try{
             if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){
                 //serializar el elemento
                Gson gson = new Gson();//crear objeto de tipo Gson
                //convertir el json a una lista de roles
                Type tipoLista = new TypeToken<List<Rol>>(){}.getType();// las {} es porque algo debes configurar
                roles = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return roles;
    }
    
    public static Mensaje registrarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaboradores/registro";
        Gson gson = new Gson();
        try{
            String parametros = gson.toJson(colaborador);
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
    
    public static Mensaje modificar(Colaborador colaborador) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaboradores/modificarColaborador";
        Gson gson = new Gson();
        try{
           String parametros = gson.toJson(colaborador);
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
    
    public static Mensaje eliminarColaborador(Integer idColaborador){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaboradores/eliminarColaborador";
        Gson gson = new Gson();
        try{
            String parametros = String.format("idColaborador=%d", idColaborador);
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
