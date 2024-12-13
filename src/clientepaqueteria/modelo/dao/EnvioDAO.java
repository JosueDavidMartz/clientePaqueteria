/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Direccion;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaEnvio;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
import clientepaqueteria.pojo.Seguimiento;
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

   /* public static RespuestaEnvio crearEnvio(Envio envioNuevo) {
        System.out.println("idEnvio: "+envioNuevo.getIdEnvio());
        RespuestaEnvio msj = new RespuestaEnvio();
        String url = Constantes.URL_WS+"envios/crearEnvio";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(envioNuevo);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            if (respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), RespuestaEnvio.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        }catch (Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj; //To change body of generated methods, choose Tools | Templates.
    }*/
    
    public static RespuestaEnvio crearEnvio(Envio envioNuevo) {
    System.out.println("Imprimiendo contenido de Envio...");
    
    // Imprimiendo los atributos de Envio
    System.out.print("Envio {");
    System.out.print("idEnvio=" + envioNuevo.getIdEnvio() + ", ");
    System.out.print("numeroGuia='" + envioNuevo.getNumeroGuia() + "', ");
    System.out.print("costoEnvio=" + envioNuevo.getCostoEnvio() + ", ");
    System.out.print("comentario='" + envioNuevo.getComentario() + "', ");
    System.out.print("idColaborador=" + envioNuevo.getIdColaborador() + ", ");
    System.out.print("idCliente=" + envioNuevo.getIdCliente() + ", ");

    // Imprimiendo los atributos de Seguimiento
    if (envioNuevo.getSeguimiento() != null) {
        Seguimiento seguimiento = envioNuevo.getSeguimiento();
        System.out.print("seguimiento={");
        System.out.print("idSeguimiento=" + seguimiento.getIdSeguimiento() + ", ");
        System.out.print("nombre='" + seguimiento.getNombre() + "', ");
        System.out.print("fecha='" + seguimiento.getFecha() + "', ");
        System.out.print("hora='" + seguimiento.getHora() + "', ");
        System.out.print("idColaborador=" + seguimiento.getIdColaborador() + ", ");
        System.out.print("idEnvio=" + seguimiento.getIdEnvio());
        System.out.print("}, ");
    } else {
        System.out.print("seguimiento=null, ");
    }

    // Imprimiendo los atributos de DireccionOrigen
    if (envioNuevo.getDireccionOrigen() != null) {
        Direccion direccionOrigen = envioNuevo.getDireccionOrigen();
        System.out.print("direccionOrigen={");
        System.out.print("idDireccion=" + direccionOrigen.getIdDireccion() + ", ");
        System.out.print("tipo='" + direccionOrigen.getTipo() + "', ");
        System.out.print("calle='" + direccionOrigen.getCalle() + "', ");
        System.out.print("numero=" + direccionOrigen.getNumero() + ", ");
        System.out.print("colonia='" + direccionOrigen.getColonia() + "', ");
        System.out.print("codigoPostal=" + direccionOrigen.getCodigoPostal() + ", ");
        System.out.print("ciudad='" + direccionOrigen.getCiudad() + "', ");
        System.out.print("estado='" + direccionOrigen.getEstado() + "', ");
        System.out.print("idCliente=" + direccionOrigen.getIdCliente() + ", ");
        System.out.print("idEnvio=" + direccionOrigen.getIdEnvio());
        System.out.print("}, ");
    } else {
        System.out.print("direccionOrigen=null, ");
    }

    // Imprimiendo los atributos de DireccionDestino
    if (envioNuevo.getDireccionDestino() != null) {
        Direccion direccionDestino = envioNuevo.getDireccionDestino();
        System.out.print("direccionDestino={");
        System.out.print("idDireccion=" + direccionDestino.getIdDireccion() + ", ");
        System.out.print("tipo='" + direccionDestino.getTipo() + "', ");
        System.out.print("calle='" + direccionDestino.getCalle() + "', ");
        System.out.print("numero=" + direccionDestino.getNumero() + ", ");
        System.out.print("colonia='" + direccionDestino.getColonia() + "', ");
        System.out.print("codigoPostal=" + direccionDestino.getCodigoPostal() + ", ");
        System.out.print("ciudad='" + direccionDestino.getCiudad() + "', ");
        System.out.print("estado='" + direccionDestino.getEstado() + "', ");
        System.out.print("idCliente=" + direccionDestino.getIdCliente() + ", ");
        System.out.print("idEnvio=" + direccionDestino.getIdEnvio());
        System.out.print("}");
        } else {
            System.out.print("direccionDestino=null");
        }
        System.out.println("}");

        RespuestaEnvio msj = new RespuestaEnvio();
        String url = Constantes.URL_WS + "envios/crearEnvio";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(envioNuevo);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                msj = gson.fromJson(respuesta.getContenido(), RespuestaEnvio.class);
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


    public static RespuestaEnvio modificar(Envio envioNuevo) {
    System.out.println("Imprimiendo contenido de Envio...");
    
    // Imprimiendo los atributos de Envio
    System.out.print("Envio {");
    System.out.print("idEnvio=" + envioNuevo.getIdEnvio() + ", ");
    System.out.print("numeroGuia='" + envioNuevo.getNumeroGuia() + "', ");
    System.out.print("costoEnvio=" + envioNuevo.getCostoEnvio() + ", ");
    System.out.print("comentario='" + envioNuevo.getComentario() + "', ");
    System.out.print("idColaborador=" + envioNuevo.getIdColaborador() + ", ");
    System.out.print("idCliente=" + envioNuevo.getIdCliente() + ", ");

    // Imprimiendo los atributos de Seguimiento
    if (envioNuevo.getSeguimiento() != null) {
        Seguimiento seguimiento = envioNuevo.getSeguimiento();
        System.out.print("seguimiento={");
        System.out.print("idSeguimiento=" + seguimiento.getIdSeguimiento() + ", ");
        System.out.print("nombre='" + seguimiento.getNombre() + "', ");
        System.out.print("fecha='" + seguimiento.getFecha() + "', ");
        System.out.print("hora='" + seguimiento.getHora() + "', ");
        System.out.print("idColaborador=" + seguimiento.getIdColaborador() + ", ");
        System.out.print("idEnvio=" + seguimiento.getIdEnvio());
        System.out.print("}, ");
    } else {
        System.out.print("seguimiento=null, ");
    }

    // Imprimiendo los atributos de DireccionOrigen
    if (envioNuevo.getDireccionOrigen() != null) {
        Direccion direccionOrigen = envioNuevo.getDireccionOrigen();
        System.out.print("direccionOrigen={");
        System.out.print("idDireccion=" + direccionOrigen.getIdDireccion() + ", ");
        System.out.print("tipo='" + direccionOrigen.getTipo() + "', ");
        System.out.print("calle='" + direccionOrigen.getCalle() + "', ");
        System.out.print("numero=" + direccionOrigen.getNumero() + ", ");
        System.out.print("colonia='" + direccionOrigen.getColonia() + "', ");
        System.out.print("codigoPostal=" + direccionOrigen.getCodigoPostal() + ", ");
        System.out.print("ciudad='" + direccionOrigen.getCiudad() + "', ");
        System.out.print("estado='" + direccionOrigen.getEstado() + "', ");
        System.out.print("idCliente=" + direccionOrigen.getIdCliente() + ", ");
        System.out.print("idEnvio=" + direccionOrigen.getIdEnvio());
        System.out.print("}, ");
    } else {
        System.out.print("direccionOrigen=null, ");
    }

    // Imprimiendo los atributos de DireccionDestino
    if (envioNuevo.getDireccionDestino() != null) {
        Direccion direccionDestino = envioNuevo.getDireccionDestino();
        System.out.print("direccionDestino={");
        System.out.print("idDireccion=" + direccionDestino.getIdDireccion() + ", ");
        System.out.print("tipo='" + direccionDestino.getTipo() + "', ");
        System.out.print("calle='" + direccionDestino.getCalle() + "', ");
        System.out.print("numero=" + direccionDestino.getNumero() + ", ");
        System.out.print("colonia='" + direccionDestino.getColonia() + "', ");
        System.out.print("codigoPostal=" + direccionDestino.getCodigoPostal() + ", ");
        System.out.print("ciudad='" + direccionDestino.getCiudad() + "', ");
        System.out.print("estado='" + direccionDestino.getEstado() + "', ");
        System.out.print("idCliente=" + direccionDestino.getIdCliente() + ", ");
        System.out.print("idEnvio=" + direccionDestino.getIdEnvio());
        System.out.print("}");
        } else {
            System.out.print("direccionDestino=null");
        }
        System.out.println("}");
        
        RespuestaEnvio msj = new RespuestaEnvio();
        String url = Constantes.URL_WS+"envios/actualizarEnvio";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(envioNuevo);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
            if (respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), RespuestaEnvio.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        }catch (Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj; //To change body of generated methods, choose Tools | Templates.
    }

    public static Mensaje asignarSeguimiento(Envio envioNuevo) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"envios/asignarSeguimiento";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(envioNuevo);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
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


    
}
