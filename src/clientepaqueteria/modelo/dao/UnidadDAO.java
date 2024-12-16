/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.RespuestaUnidad;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author WIN 10
 */
public class UnidadDAO {

    public static List<Unidad> obtenerColaboradores() {    
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS + "/unidades/obtenerUnidades";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                // Convertir el JSON completo en un JsonObject
                JsonObject jsonObject = gson.fromJson(respuesta.getContenido(), JsonObject.class);

                // Extraer el array "unidades" del objeto
                JsonArray jsonArrayUnidades = jsonObject.getAsJsonArray("unidades");

                // Convertir el array "unidades" en una lista de Unidad
                Type tipoLista = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(jsonArrayUnidades, tipoLista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidades;        
    }

    public static RespuestaUnidad registrarUnidad(Unidad unidad) {
         RespuestaUnidad msj = new RespuestaUnidad();
         String url = Constantes.URL_WS+"/unidades/registrarUnidad";
         Gson gson = new Gson();
         try {
            String parametros = gson.toJson(unidad);
            RespuestaHTTP respuesta =  ConexionWS.peticionPOSTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), RespuestaUnidad.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
         return msj;
    }

    public static RespuestaUnidad modificar(Unidad unidad) {
        RespuestaUnidad msj = new RespuestaUnidad();
        String url = Constantes.URL_WS + "/unidades/modificarUnidad";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(unidad);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
                if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                    msj = gson.fromJson(respuesta.getContenido(), RespuestaUnidad.class);
                }else{
                    msj.setError(true);
                    msj.setMensaje(respuesta.getContenido());
                }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }

    public static RespuestaUnidad darBajaUnidad(Unidad unidadBaja) {
        RespuestaUnidad msj = new RespuestaUnidad();
        String url = Constantes.URL_WS + "/unidades/darBajaUnidad";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(unidadBaja);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
                if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                    msj = gson.fromJson(respuesta.getContenido(), RespuestaUnidad.class);
                }else{
                    msj.setError(true);
                    msj.setMensaje("Problema al conectar con el servidor");
                }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }

    
}
