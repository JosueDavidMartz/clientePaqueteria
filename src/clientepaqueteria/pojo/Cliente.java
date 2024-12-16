package clientepaqueteria.pojo;

import java.util.ArrayList;
import java.util.List;



/*import clientepaqueteria.pojo.Direccion;

public class Cliente {

    private Integer idCliente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private Direccion direccion;
    private List<Direccion> direcciones; 

    public Cliente() {
    }

    
   
    public Cliente(Integer idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, Direccion direccion, List<Direccion> direcciones) {

        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;

        this.direccion = direccion;
        this.direcciones = direcciones;

    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    

    // Métodos para obtener datos de la primera dirección
    public String getCalle() {
        return direcciones != null && !direcciones.isEmpty() ? direcciones.get(0).getCalle() : "";
    }

    public String getNumero() {
        return direcciones != null && !direcciones.isEmpty() ? String.valueOf(direcciones.get(0).getNumero()) : "";
    }

    public String getColonia() {
        return direcciones != null && !direcciones.isEmpty() ? direcciones.get(0).getColonia() : "";
    }

    public String getCodigoPostal() {
        return direcciones != null && !direcciones.isEmpty() ? String.valueOf(direcciones.get(0).getCodigoPostal()) : "";
    }
}

*/
public class Cliente {
    private Integer idCliente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private Direccion direccion;
    private List<Direccion> direcciones; 

    // Constructor sin direcciones
    public Cliente() {
         this.direcciones = new ArrayList<>();
    }

    public Cliente(Integer idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, Direccion direccion, List<Direccion> direcciones) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.direcciones = direcciones;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    

    // Métodos para obtener datos de la primera dirección
    public String getCalle() {
        return direcciones != null && !direcciones.isEmpty() ? direcciones.get(0).getCalle() : "";
    }

    public String getNumero() {
        return direcciones != null && !direcciones.isEmpty() ? String.valueOf(direcciones.get(0).getNumero()) : "";
    }

    public String getColonia() {
        return direcciones != null && !direcciones.isEmpty() ? direcciones.get(0).getColonia() : "";
    }

    public String getCodigoPostal() {
        return direcciones != null && !direcciones.isEmpty() ? String.valueOf(direcciones.get(0).getCodigoPostal()) : "";
    }
}