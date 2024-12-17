package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Direccion;
import clientepaqueteria.pojo.Mensaje;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

public class ClienteDAO {
    
    public static List<Cliente> obtenerClientes() {
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS + "cliente/obtenerCliente";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
    public static Mensaje guardarDatosCliente(Cliente cliente, Direccion direccion) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "cliente/registroCliente";
        Gson gson = new Gson();
        try {
            JsonObject jsonCliente = gson.toJsonTree(cliente).getAsJsonObject();
            JsonObject jsonDireccion = gson.toJsonTree(direccion).getAsJsonObject();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("nombre", jsonCliente.get("nombre"));
            jsonObject.add("apellidoPaterno", jsonCliente.get("apellidoPaterno"));
            jsonObject.add("apellidoMaterno", jsonCliente.get("apellidoMaterno"));
            jsonObject.add("telefono", jsonCliente.get("telefono"));
            jsonObject.add("correo", jsonCliente.get("correo"));
            jsonObject.add("direccion", jsonDireccion);
            String parametros = gson.toJson(jsonObject);
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
    
    public static Mensaje editarDatosCliente(Cliente cliente, List<Direccion> direcciones) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "cliente/modificarCliente";
        Gson gson = new Gson();
        try {
            JsonObject jsonCliente = gson.toJsonTree(cliente).getAsJsonObject();
            JsonArray jsonDirecciones = new JsonArray();
            for (Direccion direccion : direcciones) {
                jsonDirecciones.add(gson.toJsonTree(direccion).getAsJsonObject());
            }
            jsonCliente.add("direcciones", jsonDirecciones);
            String parametros = gson.toJson(jsonCliente);
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
    
    public static Mensaje eliminarCliente(int idCliente) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "cliente/eliminarCliente"; 
        Gson gson = new Gson();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        try {
            String parametros = gson.toJson(cliente);
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
}
