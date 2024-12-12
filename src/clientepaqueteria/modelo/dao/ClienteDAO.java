package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Cliente;
import clientepaqueteria.pojo.Colaborador;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

public class ClienteDAO {
    
    public static List<Cliente> obtenerClientes(){
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS+"cliente/obtenerClientes";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        try{
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(respuesta.getContenido(),tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return clientes;
    }
}
