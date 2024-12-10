
package cientepaqueteria.modelo;

import clientepaqueteria.pojo.RespuestaHTTP;
import clientepaqueteria.utilidades.Constantes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ConexionWS {
     public static RespuestaHTTP peticionGET(String url){ //no tiene parametros porque recibe los paaremtro en el URL
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlDestino = new URL(url); //creo un URL
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();//manda una llamada a la conexión
            conexionHttp.setRequestMethod("GET"); //configurando el metodo de petición
            int codigoRespuesta = conexionHttp.getResponseCode();//obtiene una respuesta y manda la petición. aqui se guarda el valor del codigo de respuesta
            respuesta.setCodigoRespuesta(codigoRespuesta);//guarda la petición
            System.out.println("Codigo WS: "+codigoRespuesta);
            if(codigoRespuesta == HttpURLConnection.HTTP_OK){//si es 200
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));//obtener el string de entrada
            }else{
                respuesta.setContenido("Código de respuesta HTTP: "+codigoRespuesta); //mandar como contenido el codigo de respuesta
            }
        } catch (MalformedURLException e) {//si no puede crear la URL 
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);//le mando un codigo  para cuando haya un error que tenga que ver con las construcción d ela petición
            respuesta.setContenido("Error el la dirección de conexión.");
        } catch (IOException io){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        } 
        return respuesta;
    }
    
    public static RespuestaHTTP peticionPOST(String url, String parametros){ 
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();//abro la conexión
            conexionHttp.setRequestMethod("POST");//confihuro metodo de petición
            conexionHttp.setRequestProperty("Content-Type", //el encabezado para decirle que los parametrosva a ser en formato application/x-www-form-urlencoded
                    "application/x-www-form-urlencoded");
            conexionHttp.setDoOutput(true); //mi petición a permitir escribir datos en su cuerpo
            OutputStream os = conexionHttp.getOutputStream();// cuerpo de la petición en modo de scritura
            os.write(parametros.getBytes());//convierte la cadena en arreglo de bytes(0010101)
            os.flush();//escribe
            os.close();//cierra el modo escritura
            int codigoRespuesta = conexionHttp.getResponseCode();//invoca la petición
            respuesta.setCodigoRespuesta(codigoRespuesta);//guarda
            if(codigoRespuesta == HttpURLConnection.HTTP_OK){//i es 200
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));//guardo en una cadena
            }else{
                respuesta.setContenido("Código de respuesta HTTP: "+codigoRespuesta);
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error el la dirección de conexión.");
        } catch (IOException io){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        } 
        return respuesta;
    }
    
    public static RespuestaHTTP peticionPUT(String url, String parametros){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("PUT");
            conexionHttp.setRequestProperty("Content-Type", 
                    "application/x-www-form-urlencoded");
            conexionHttp.setDoOutput(true);
            OutputStream os = conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            int codigoRespuesta = conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta == HttpURLConnection.HTTP_OK){
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
            }else{
                respuesta.setContenido("Código de respuesta HTTP: "+codigoRespuesta);
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error el la dirección de conexión.");
        } catch (IOException io){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        } 
        return respuesta;
    }
    
    public static RespuestaHTTP peticionDELETE(String url, String parametros){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("DELETE");
            conexionHttp.setRequestProperty("Content-Type", 
                    "application/x-www-form-urlencoded");
            conexionHttp.setDoOutput(true);
            OutputStream os = conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            int codigoRespuesta = conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta == HttpURLConnection.HTTP_OK){
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
            }else{
                respuesta.setContenido("Código de respuesta HTTP: "+codigoRespuesta);
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error el la dirección de conexión.");
        } catch (IOException io){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        } 
        return respuesta;
    }
    
    public static RespuestaHTTP peticionPOSTJson(String url, String parametros){ 
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();//abro la conexión
            conexionHttp.setRequestMethod("POST");//confihuro metodo de petición
            conexionHttp.setRequestProperty("Content-Type", //el encabezado para decirle que los parametrosva a ser en formato application/x-www-form-urlencoded
                    "application/json");
            conexionHttp.setDoOutput(true); //mi petición a permitir escribir datos en su cuerpo
            OutputStream os = conexionHttp.getOutputStream();// cuerpo de la petición en modo de scritura
            os.write(parametros.getBytes());//convierte la cadena en arreglo de bytes(0010101)
            os.flush();//escribe
            os.close();//cierra el modo escritura
            int codigoRespuesta = conexionHttp.getResponseCode();//invoca la petición
            respuesta.setCodigoRespuesta(codigoRespuesta);//guarda
            if(codigoRespuesta == HttpURLConnection.HTTP_OK){//i es 200
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));//guardo en una cadena
            }else{
                respuesta.setContenido("Código de respuesta HTTP: "+codigoRespuesta);
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error el la dirección de conexión.");
        } catch (IOException io){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        } 
        return respuesta;
    }
    
     public static RespuestaHTTP peticionPUTJson(String url, String parametros){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("PUT");
            conexionHttp.setRequestProperty("Content-Type", 
                    "application/json");
            conexionHttp.setDoOutput(true);
            OutputStream os = conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            int codigoRespuesta = conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta == HttpURLConnection.HTTP_OK){
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
            }else{
                respuesta.setContenido("Código de respuesta HTTP: "+codigoRespuesta);
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error el la dirección de conexión.");
        } catch (IOException io){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        } 
        return respuesta;
    }
    
    //convierte mi respuesta de bytes(010101) a String (cadena)
    private static String obtenerContenidoWS(InputStream inputWS) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(inputWS));
        String inputLine;
        StringBuffer respuestaEntrada = new StringBuffer();
        while( (inputLine = in.readLine()) != null){
            respuestaEntrada.append(inputLine);
        }
        in.close();
        return respuestaEntrada.toString();
    }
}
