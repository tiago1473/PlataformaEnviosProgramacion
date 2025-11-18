package models;

import java.util.ArrayList;

public class Repartidor {
    private String id;
    private String nombre;
    private String telefono;
    private EstadoRepartidor estado;
    private ArrayList<Envio> envios;
    private ZonaCobertura zonaCobertura;

    public Repartidor(String id, String nombre,String telefono, EstadoRepartidor estado) {
        this.id=id;
        this.nombre=nombre;
        this.telefono=telefono;
        this.estado=estado;
        this.zonaCobertura=new ZonaCobertura();
        this.envios=new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public EstadoRepartidor getEstado() {
        return estado;
    }

    public ArrayList<Envio> getEnvios() {
        return envios;
    }

    public ZonaCobertura getZonaCobertura() {
        return zonaCobertura;
    }

    public int getEnviosAsignados() {
        return envios.size();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEstado(EstadoRepartidor estado) {
        this.estado = estado;
    }

    public void addEnvios(Envio envio) {
        this.envios.add(envio);
    }

    public void setZonaCobertura(ZonaCobertura zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }
}
