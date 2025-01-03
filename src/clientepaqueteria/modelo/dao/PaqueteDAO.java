package clientepaqueteria.modelo.dao;

import cientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Envio;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.Paquete;
import clientepaqueteria.pojo.RespuestaEnvio;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.pojo.ResultadoObtenerEnvio;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaqueteDAO {

    public static List<Paquete> obtenerPaquetes() {
        List<Paquete> paquetes = null;

        // URL para obtener la lista de paquetes
        String url = Constantes.URL_WS + "paquetes/obtenerPaquetes";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Paquete>>() {
                }.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoLista);

                // Iterar sobre cada paquete y obtener su número de guía
                for (Paquete paquete : paquetes) {
                    obtenerNumeroGuia(paquete);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paquetes;
    }

    private static void obtenerNumeroGuia(Paquete paquete) {

        String url = Constantes.URL_WS + "paquetes/obtenerGuiasPorPaquete";

        Map<String, Integer> parametros = new HashMap<>();
        parametros.put("idPaquete", paquete.getIdPaquete());

        Gson gson = new Gson();
        String jsonParametros = gson.toJson(parametros);

        RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, jsonParametros);

        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                RespuestaEnvio respuestaEnvio = gson.fromJson(respuesta.getContenido(), RespuestaEnvio.class);

                if (respuestaEnvio != null) {
                    paquete.setNumeroGuia(respuestaEnvio.getNumeroGuia());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Mensaje registrarPaquete(Paquete paquete) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/registrar";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(paquete);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
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

    public static Mensaje modificar(Paquete paquete) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/modificar";
        Gson gson = new Gson();
        try {

            String parametros = gson.toJson(paquete);
            System.out.println("Datos enviados en la petición: " + parametros);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);//crear el objeto mensaje que viene de respuesta
            } else {
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());//le mnado como mnesjae el contenido
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }

    public static Mensaje eliminarPaquete(Integer idPaquete) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/eliminar";
        Gson gson = new Gson();
        try {
            String parametros = String.format("idPaquete=%d", idPaquete);
            RespuestaHTTP respuesta = ConexionWS.peticionDELETE(url, parametros);
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

    public static RespuestaEnvio registrarCosto(Envio envio) {

        RespuestaEnvio msj = new RespuestaEnvio();
        String url = Constantes.URL_WS + "paquetes/agregarCosto";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
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

    /* public static List<String> obtenerNumerosDeGuiaPorCliente(int idCliente) {
        List<ResultadoObtenerEnvio> envios = null;
        List<String> numerosDeGuia = new ArrayList<>();

        // URL del servicio para obtener los envíos
        String url = Constantes.URL_WS + "envios/obtenerEnvios";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {

                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<ResultadoObtenerEnvio>>() {
                }.getType();
                envios = gson.fromJson(respuesta.getContenido(), tipoLista);

                if (envios != null) {
                    for (ResultadoObtenerEnvio envio : envios) {
                        if (envio.getIdCliente() == idCliente) {
                            numerosDeGuia.add(envio.getNumeroGuia());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numerosDeGuia; // Devuelve la lista de números de guía
    }
     */
    public static List<String> obtenerNumerosDeGuiaPorCliente(int idCliente) {
        List<ResultadoObtenerEnvio> envios = null;
        List<String> numerosDeGuia = new ArrayList<>();

        // URL del servicio para obtener los envíos
        String url = Constantes.URL_WS + "envios/obtenerEnvios";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {

                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<ResultadoObtenerEnvio>>() {
                }.getType();
                envios = gson.fromJson(respuesta.getContenido(), tipoLista);

                if (envios != null) {
                    // Filtrar los envíos para que solo queden los de nombreSeguimiento "Pendiente"
                    envios = envios.stream()
                            .filter(envio -> "Pendiente".equals(envio.getNombreSeguimiento()) && envio.getIdCliente() == idCliente)
                            .collect(Collectors.toList());

                    // Obtener los números de guía
                    for (ResultadoObtenerEnvio envio : envios) {
                        numerosDeGuia.add(envio.getNumeroGuia());
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

            Map<String, String> parametrosMap = new HashMap<>();
            parametrosMap.put("numeroGuia", numeroGuia);
            String parametros = gson.toJson(parametrosMap);

            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);

            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {

                resultado = gson.fromJson(respuesta.getContenido(), ResultadoObtenerEnvio.class);
                
            } else {

                resultado.setError(true);
                resultado.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {

            resultado.setError(true);
            resultado.setMensaje("Error en la consulta del envío: " + e.getMessage());
        }
  
        return resultado;
    }

}
