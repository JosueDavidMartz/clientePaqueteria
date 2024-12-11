/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
import clientepaqueteria.pojo.Unidad;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WIN 10
 */
public class EnvioDAO {

    public static List<ResultadoObtenerEnvio> obtenerEnvios() {
        System.out.println("entre al dao");
        List<ResultadoObtenerEnvio> resultado = new ArrayList<>();

        String url = Constantes.URL_WS + "/envios/obtenerEnvios";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                // Convertir el JSON completo en un JsonArray, ya que la respuesta es un arreglo
                JsonArray jsonArrayEnvios = gson.fromJson(respuesta.getContenido(), JsonArray.class);

                // Convertir el JsonArray en una lista de ResultadoObtenerEnvio
                Type tipoLista = new TypeToken<List<ResultadoObtenerEnvio>>(){}.getType();
                resultado = gson.fromJson(jsonArrayEnvios, tipoLista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }


    
}
