package clientepaqueteria.pojo;


 
public class RespuestaRegistroColaborador {
 
    private Boolean error;
    private String mensaje;
    private Integer idColaborador;
    
    public RespuestaRegistroColaborador() {
    }

    public RespuestaRegistroColaborador(Boolean error, String mensaje, Integer idColaborador) {
        this.error = error;
        this.mensaje = mensaje;
        this.idColaborador = idColaborador;
       
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

    public boolean isError() {
         return error != null && error; 
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    

}
