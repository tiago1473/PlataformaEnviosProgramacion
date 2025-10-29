package models.DTO;

import models.Coordenada;

public class DireccionDTO {

    private String id;
    private String alias;
    private String calle;
    private String ciudad;
    private Coordenada coordenada;

    public DireccionDTO() {}

    public DireccionDTO(String alias, String calle, String ciudad) {
        this.alias = alias;
        this.calle = calle;
        this.ciudad = ciudad;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coordenada getCoordenada() {
        return this.coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }
}
