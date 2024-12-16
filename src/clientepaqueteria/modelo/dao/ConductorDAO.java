/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author anton
 */
public class ConductorDAO {

    public static Mensaje asociarUnidad(String noPersonal, String numeroUnidad) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "conductores/asignarUnidad/" + noPersonal + "/" + numeroUnidad;

        Gson gson = new Gson();
        
        // Crear un JSON básico como cadena para enviar los parámetros
        String parametros = "{\"idColaborador\": " + noPersonal + ", \"numeroUnidad\": \"" + numeroUnidad + "\"}";
        
        try {
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);

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
    
     public static Mensaje desasociarUnidad(String noPersonal) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "conductores/desasignarUnidad/" + noPersonal;
        Gson gson = new Gson();
         
        Unidad unidad = new Unidad();
        unidad.setNoPersonal(noPersonal);
        try {
            String parametros = gson.toJson(noPersonal);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);  // No se necesita enviar parámetros adicionales

            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                msj = new Gson().fromJson(respuesta.getContenido(), Mensaje.class);
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

