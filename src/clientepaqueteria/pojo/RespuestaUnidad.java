package clientepaqueteria.pojo;

import java.util.List;

public class RespuestaUnidad {
    private Boolean error;
    private String mensaje;
    private Unidad unidad;
    private List<Unidad> unidades;

    // Constructor vacío
    public RespuestaUnidad() {
    }

    // Constructor con parámetros
    public RespuestaUnidad(Boolean error, String mensaje, Unidad unidad, List<Unidad> unidades) {
        this.error = error;
        this.mensaje = mensaje;
        this.unidad = unidad;
        this.unidades = unidades;
    }

    // Getters y setters
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

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    // Método adicional para verificar si hay un error
    public boolean isError() {
        return error != null && error;
    }
}
