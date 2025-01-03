package clientepaqueteria.pojo;

public class ResultadoObtenerEnvio {
    private Integer idEnvio;  
    private String seguimiento;
    private String numeroGuia;
    private Float costoEnvio;
    
    private Integer idDireccionOrigen;
    private String tipoOrigen;
    private String calleOrigen;
    private Integer numeroOrigen;
    private String coloniaOrigen;
    private Integer codigoPostalOrigen;
    private String ciudadOrigen;
    private String estadoOrigen;    
    
    private Integer idDireccionDestino;
    private String tipoDestino;
    private String calleDestino;
    private Integer numeroDestino;
    private String coloniaDestino;
    private Integer codigoPostalDestino;
    private String ciudadDestino;
    private String estadoDestino;    
       
    private Integer idCliente;
    private String nombreCliente;
    private String estatusCliente;
    
    private Integer idColaborador; 
    private String nombreColaborador;
    private String nombreConductor;
    
    private Integer idSeguimiento;
    private String nombreSeguimiento;
    
    private Integer cantidadPaquetes;
    
    private boolean error;
    private String mensaje;

    public ResultadoObtenerEnvio() {
    }

    public ResultadoObtenerEnvio(Integer idEnvio, String seguimiento, String numeroGuia, Float costoEnvio, Integer idDireccionOrigen, String tipoOrigen, String calleOrigen, Integer numeroOrigen, String coloniaOrigen, Integer codigoPostalOrigen, String ciudadOrigen, String estadoOrigen, Integer idDireccionDestino, String tipoDestino, String calleDestino, Integer numeroDestino, String coloniaDestino, Integer codigoPostalDestino, String ciudadDestino, String estadoDestino, Integer idCliente, String nombreCliente, String estatusCliente, Integer idColaborador, String nombreColaborador, String nombreConductor, Integer idSeguimiento, String nombreSeguimiento, Integer cantidadPaquetes, boolean error, String mensaje) {
        this.idEnvio = idEnvio;
        this.seguimiento = seguimiento;
        this.numeroGuia = numeroGuia;
        this.costoEnvio = costoEnvio;
        this.idDireccionOrigen = idDireccionOrigen;
        this.tipoOrigen = tipoOrigen;
        this.calleOrigen = calleOrigen;
        this.numeroOrigen = numeroOrigen;
        this.coloniaOrigen = coloniaOrigen;
        this.codigoPostalOrigen = codigoPostalOrigen;
        this.ciudadOrigen = ciudadOrigen;
        this.estadoOrigen = estadoOrigen;
        this.idDireccionDestino = idDireccionDestino;
        this.tipoDestino = tipoDestino;
        this.calleDestino = calleDestino;
        this.numeroDestino = numeroDestino;
        this.coloniaDestino = coloniaDestino;
        this.codigoPostalDestino = codigoPostalDestino;
        this.ciudadDestino = ciudadDestino;
        this.estadoDestino = estadoDestino;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.estatusCliente = estatusCliente;
        this.idColaborador = idColaborador;
        this.nombreColaborador = nombreColaborador;
        this.nombreConductor = nombreConductor;
        this.idSeguimiento = idSeguimiento;
        this.nombreSeguimiento = nombreSeguimiento;
        this.cantidadPaquetes = cantidadPaquetes;
        this.error = error;
        this.mensaje = mensaje;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public Float getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Float costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public Integer getIdDireccionOrigen() {
        return idDireccionOrigen;
    }

    public void setIdDireccionOrigen(Integer idDireccionOrigen) {
        this.idDireccionOrigen = idDireccionOrigen;
    }

    public String getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(String tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public String getCalleOrigen() {
        return calleOrigen;
    }

    public void setCalleOrigen(String calleOrigen) {
        this.calleOrigen = calleOrigen;
    }

    public Integer getNumeroOrigen() {
        return numeroOrigen;
    }

    public void setNumeroOrigen(Integer numeroOrigen) {
        this.numeroOrigen = numeroOrigen;
    }

    public String getColoniaOrigen() {
        return coloniaOrigen;
    }

    public void setColoniaOrigen(String coloniaOrigen) {
        this.coloniaOrigen = coloniaOrigen;
    }

    public Integer getCodigoPostalOrigen() {
        return codigoPostalOrigen;
    }

    public void setCodigoPostalOrigen(Integer codigoPostalOrigen) {
        this.codigoPostalOrigen = codigoPostalOrigen;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(String estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public Integer getIdDireccionDestino() {
        return idDireccionDestino;
    }

    public void setIdDireccionDestino(Integer idDireccionDestino) {
        this.idDireccionDestino = idDireccionDestino;
    }

    public String getTipoDestino() {
        return tipoDestino;
    }

    public void setTipoDestino(String tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

    public String getCalleDestino() {
        return calleDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;
    }

    public Integer getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(Integer numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public String getColoniaDestino() {
        return coloniaDestino;
    }

    public void setColoniaDestino(String coloniaDestino) {
        this.coloniaDestino = coloniaDestino;
    }

    public Integer getCodigoPostalDestino() {
        return codigoPostalDestino;
    }

    public void setCodigoPostalDestino(Integer codigoPostalDestino) {
        this.codigoPostalDestino = codigoPostalDestino;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEstatusCliente() {
        return estatusCliente;
    }

    public void setEstatusCliente(String estatusCliente) {
        this.estatusCliente = estatusCliente;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public String getNombreSeguimiento() {
        return nombreSeguimiento;
    }

    public void setNombreSeguimiento(String nombreSeguimiento) {
        this.nombreSeguimiento = nombreSeguimiento;
    }

    public Integer getCantidadPaquetes() {
        return cantidadPaquetes;
    }

    public void setCantidadPaquetes(Integer cantidadPaquetes) {
        this.cantidadPaquetes = cantidadPaquetes;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    
   
    
}
