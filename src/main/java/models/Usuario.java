package models;
import java.util.ArrayList;

public class Usuario {
    private String id;
    private String password;
    private String nombre;
    private String correo;
    private String telefono;
    private ArrayList<Envio> envios;
    private ArrayList<Direccion> direccion;

    public Usuario(String id, String password, String nombre, String correo, String telefono) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.envios = new ArrayList<>();
        this.direccion = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Envio> getEnvios() {
        return this.envios;
    }

    public void setEnvios(ArrayList<Envio> envios) {
        this.envios = envios;
    }

    public ArrayList<Direccion> getDireccion() {
        return this.direccion;
    }

    public void setDireccion(ArrayList<Direccion> direccion) {
        this.direccion = direccion;
    }

    public void addDireccion(Direccion direccion){
        this.direccion.add(direccion);
    }

    public void addEnvio(Envio envio){
        this.envios.add(envio);
    }

    @Override
    public String toString() {
        return "Usuario: "+
                "\nId: " + this.id+
                "\nPassword: " + this.password +
                "\nNombre: " + this.nombre +
                "\nCorreo: " + this.correo +
                "\nTelefono: " + this.telefono;
    }
}
