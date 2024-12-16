/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.modelo.dao;

import cientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Paquete;
import clientepaqueteria.pojo.RespuestaEnvio;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
import clientepaqueteria.pojo.Rol;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
           System.out.println("Datos enviados en la petición: " + parametros);
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

    public static RespuestaEnvio registrarCosto(Envio envio) {

        RespuestaEnvio msj = new RespuestaEnvio();
        String url = Constantes.URL_WS + "paquetes/agregarCosto";
        Gson gson = new Gson();
        try{
           String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){
                msj = gson.fromJson(respuesta.getContenido(), RespuestaEnvio.class);//crear el objeto mensaje que viene de respuesta
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

    public static List<String> obtenerNumerosDeGuiaPorCliente(int idCliente) {
        List<ResultadoObtenerEnvio> envios = null; // Cambiamos el tipo a Envio
        List<String> numerosDeGuia = new ArrayList<>(); // Lista para almacenar los números de guía

        // URL del servicio para obtener los envíos
        String url = Constantes.URL_WS + "envios/obtenerEnvios"; // Ajusta la URL según corresponda
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                // Convertir la respuesta en una lista de envíos
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<ResultadoObtenerEnvio>>() {}.getType();
                envios = gson.fromJson(respuesta.getContenido(), tipoLista);

                // Filtrar por idCliente y extraer los números de guía
                if (envios != null) {
                    for (ResultadoObtenerEnvio envio : envios) {
                        if (envio.getIdCliente() == idCliente) { // Verifica si el envío pertenece al cliente
                            numerosDeGuia.add(envio.getNumeroGuia()); // Agrega el número de guía a la lista
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numerosDeGuia; // Devuelve la lista de números de guía
    }
    
    
    
    

    public static ResultadoObtenerEnvio consultarEnvio(String numeroGuia) {
        ResultadoObtenerEnvio resultado = new ResultadoObtenerEnvio();
        String url = Constantes.URL_WS + "envios/consultarEnvio";
        Gson gson = new Gson();

        try {
            // Crear el JSON con el número de guía manualmente
            Map<String, String> parametrosMap = new HashMap<>();
            parametrosMap.put("numeroGuia", numeroGuia);
            String parametros = gson.toJson(parametrosMap);

            // Realizar la solicitud POST con el cuerpo JSON
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);

            // Procesar la respuesta del servicio
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                // Convertir el contenido JSON a un objeto ResultadoObtenerEnvio
                resultado = gson.fromJson(respuesta.getContenido(), ResultadoObtenerEnvio.class);
            } else {
                // Manejo de errores del servicio
                resultado.setError(true);
                resultado.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            // Manejo de errores generales
            resultado.setError(true);
            resultado.setMensaje("Error en la consulta del envío: " + e.getMessage());
        }

        return resultado;
    }


}
