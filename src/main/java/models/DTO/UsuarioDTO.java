package models.DTO;
import models.Direccion;
import models.Envio;
import models.Rol;

import java.util.ArrayList;


public class UsuarioDTO {
    private String password;
    private String id;
    private Rol rol;
    private String nombre;
    private String correo;
    private String telefono;
    private ArrayList<Envio> envios;
    private ArrayList<Direccion> direccion;

    public UsuarioDTO() {}

    public UsuarioDTO(String id, String password) {
        this.id = id;
        this.password = password;
        this.rol=Rol.ADM;
    }

    public UsuarioDTO(String password, String id, String nombre, String correo, String telefono) {
        this.password = password;
        this.id = id;
        this.rol = Rol.USER;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(ArrayList<Envio> envios) {
        this.envios = envios;
    }

    public ArrayList<Direccion> getDireccion() {
        return direccion;
    }

    public void setDireccion(ArrayList<Direccion> direccion) {
        this.direccion = direccion;
    }

}
