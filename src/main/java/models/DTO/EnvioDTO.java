package models.DTO;

import models.Direccion;
import models.EstadoEnvio;

import java.time.LocalDateTime;

public class EnvioDTO {
    private String id;
    private Direccion origen;
    private Direccion destino;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntrega;
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
    private String estadoPago;
    private String nombreUsuario;
    private String nombreRepartidor;

    public EnvioDTO (){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCosto() {
        return costo;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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

    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreRepartidor() {
        return this.nombreRepartidor;
    }
    public void setNombreRepartidor(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
    }
}