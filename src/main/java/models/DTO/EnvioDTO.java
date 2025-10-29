package models.DTO;

import models.Direccion;
import models.EstadoEnvio;

public class EnvioDTO {
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double largo;
    private double ancho;
    private double alto;
    private double costo;
    private EstadoEnvio estado;
    private boolean seguro;
    private boolean fragil;
    private boolean firma;
    private boolean prioridad;

    public EnvioDTO (){}

    public EnvioDTO(Direccion origen, Direccion destino, double peso, double largo,
                    double ancho, double alto, EstadoEnvio estado) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.estado = estado;
    }

    public Direccion getOrigen() {
        return this.origen;
    }

    public void setOrigen(Direccion origen) {
        this.origen = origen;
    }



    public Direccion getDestino() {
        return this.destino;
    }

    public void setDestino(Direccion destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return this.peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getLargo() {
        return this.largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAncho() {
        return this.ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getAlto() {
        return this.alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public EstadoEnvio getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }

    public boolean isSeguro() {
        return this.seguro;
    }

    public void setSeguro(boolean seguro) {
        this.seguro = seguro;
    }

    public boolean isFragil() {
        return this.fragil;
    }

    public void setFragil(boolean fragil) {
        this.fragil = fragil;
    }

    public boolean isFirma() {
        return this.firma;
    }

    public void setFirma(boolean firma) {
        this.firma = firma;
    }

    public boolean isPrioridad() {
        return this.prioridad;
    }

    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }
}