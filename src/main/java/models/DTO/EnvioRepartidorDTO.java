package models.DTO;

import models.Direccion;

public class EnvioRepartidorDTO {
    private String idEnvio;
    private String origen;
    private String destino;

    public EnvioRepartidorDTO(String idEnvio, String origen, String destino) {
        this.idEnvio=idEnvio;
        this.origen=origen;
        this.destino=destino;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
