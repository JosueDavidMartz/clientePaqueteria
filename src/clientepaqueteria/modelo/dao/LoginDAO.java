
package clientepaqueteria.modelo.dao;

import clientepaqueteria.modelo.ConexionWS;
import clientepaqueteria.pojo.Login;
import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.utilidades.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;


public class LoginDAO {
    
    public static Login iniciarsesion(String noPersonal, String contraseña) {
        System.out.println("numeroPersonal: "+noPersonal+" contr: "+contraseña);
        Login respuesta = new Login(); 
        String url = Constantes.URL_WS + "login/colaborador";
        String parametros = String.format("noPersonal=%s&contraseña=%s", noPersonal, contraseña);//llave=valor amperson
        RespuestaHTTP respuestaWS = ConexionWS.peticionPOST(url, parametros); //mandarlo a llamar
        
        //estructura y orden
        if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK ){//si es 200 me da un contenido que se lee
            //converit el contenido en un json
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaWS.getContenido(), Login.class);//metodo declara un objeto
        }else{
            //el servicio no respondio correctamente
            respuesta.setError(true);
            respuesta.setMensaje("Lo sentimos. Por el momento el servciio no esta disponible.");
        }
        return respuesta;
    }
}