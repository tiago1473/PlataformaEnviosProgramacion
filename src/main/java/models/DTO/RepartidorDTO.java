package models.DTO;

import models.EstadoRepartidor;

public class RepartidorDTO {
    private String id;
    private String nombre;
    private String telefono;
    private EstadoRepartidor estado;
    private double latitud;
    private double longitud;
    private int radio;
    private int enviosAsignados;

    public RepartidorDTO(String id, String nombre,String telefono, EstadoRepartidor estado, int enviosAsignados) {
        this.id=id;
        this.nombre=nombre;
        this.telefono=telefono;
        this.estado=estado;
        this.enviosAsignados=enviosAsignados;
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

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public int getRadio() {
        return radio;
    }

    public int getEnviosAsignados() {
        return enviosAsignados;
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

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

   public void setRadio(int radio) {
        this.radio = radio;
   }

   public void setEnviosAsignados(int enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
   }

    @Override
    public String toString() {
        return "Repartidor "+ nombre +
                " C.C.='" + getId() + '\'';
    }
}
