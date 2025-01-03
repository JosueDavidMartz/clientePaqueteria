/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.pojo;

/**
 *
 * @author WIN 10
 */
public class Unidad {
    
    private Integer idUnidad;
    private String marca;
    private String modelo;
    private Integer año;
    private String vin;
    private String numeroInterno;
    private String situacion;
    private String motivo;
    private Integer idColaborador;
    private String tipoUnidad;
    private String numeroPersonal;
    private String nombreConductor;
    

    public Unidad() {
    }

    public Unidad(Integer idUnidad, String marca, String modelo, Integer año, String vin, String numeroInterno, String situacion, String motivo, Integer idColaborador, String tipoUnidad, String numeroPersonal, String nombreConductor) {
        this.idUnidad = idUnidad;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.vin = vin;
        this.numeroInterno = numeroInterno;
        this.situacion = situacion;
        this.motivo = motivo;
        this.idColaborador = idColaborador;
        this.tipoUnidad = tipoUnidad;
        this.numeroPersonal = numeroPersonal;
        this.nombreConductor = nombreConductor;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    

    
    
}
