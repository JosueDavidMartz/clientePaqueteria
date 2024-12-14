/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.pojo;

import java.util.List;

public class RespuestaEnvio {
    private Boolean error;
    private String mensaje;
    private Envio envio;
    private List<Envio> envios;
    private String numeroGuia;

    public RespuestaEnvio() {
         this.envio = new Envio();
    }

    public RespuestaEnvio(Boolean error, String mensaje, Envio envio, List<Envio> envios, String numeroGuia) {
        this.error = error;
        this.mensaje = mensaje;
        this.envio = envio;
        this.envios = envios;
        this.numeroGuia = numeroGuia;
    }
    
    
    
    public boolean isError() {
        return error;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }
}