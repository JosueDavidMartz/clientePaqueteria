/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientepaqueteria.pojo;

/**
 *
 * @author Win10
 */
public class Seguimiento {
    private Integer idSeguimiento;
    private String nombre;
    private String fecha;
    private String hora;
    private Integer idColaborador;

    public Seguimiento() {
    }

    public Seguimiento(Integer idSeguimiento, String nombre, String fecha, String hora, Integer idColaborador) {
        this.idSeguimiento = idSeguimiento;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.idColaborador = idColaborador;
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }
    
    
}
