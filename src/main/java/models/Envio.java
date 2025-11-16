package models;

import service.estadoState.EstadoEnvioState;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Envio {
    public static int contador = 1;
    private String id;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double largo;
    private double ancho;
    private double alto;
    private double costo;
    private EstadoEnvioState estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaEntrega;
    private ArrayList<Incidencia> incidencias;
    private boolean seguro;
    private boolean fragil;
    private boolean firma;
    private boolean prioridad;
    private String nombreUsuario;
    private String nombreRepartidor;

    public Envio(EnvioBuilder builder) {
        this.id = "E - 0" + contador;
        contador += 1;
        this.origen = builder.origen;
        this.destino = builder.destino;
        this.peso = builder.peso;
        this.largo = builder.largo;
        this.ancho = builder.ancho;
        this.alto = builder.alto;
        this.estado = builder.estado;
        this.seguro = builder.seguro;
        this.fragil = builder.fragil;
        this.firma = builder.firma;
        this.fechaCreacion = builder.fechaCreacion;
        this.fechaEstimadaEntrega = builder.fechaEstimadaEntrega;
        this.prioridad = builder.prioridad;
        this.incidencias = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return this.estado.getNombre();
    }

    public void setEstado(EstadoEnvioState estado) {
        this.estado = estado;
    }

    public void porAsignar(){
        estado.porAsignar(this);
    }

    public void asignar() {
        estado.asignar(this);
    }

    public void enRuta() {
        estado.enRuta(this);
    }

    public void entregar() {
        estado.entregar(this);
    }

    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEstimadaEntrega() {
        return this.fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public ArrayList<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(ArrayList<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public LocalDateTime getFechaEntrega() {
        return this.fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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

    public void addIncidencia(Incidencia incidencia) {
        this.incidencias.add(incidencia);
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
