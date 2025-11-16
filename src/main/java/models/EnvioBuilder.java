package models;
import service.estadoState.EstadoEnvioState;
import service.estadoState.SolicitadoState;

import java.time.LocalDateTime;

public class EnvioBuilder {

    Direccion origen;
    Direccion destino;
    double peso;
    double largo;
    double ancho;
    double alto;
    double costo;
    EstadoEnvioState estado;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaEstimadaEntrega;
    boolean seguro;
    boolean fragil;
    boolean firma;
    boolean prioridad;

    public EnvioBuilder(Direccion origen, Direccion destino, double peso, double largo,
                        double ancho, double alto) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.estado = new SolicitadoState();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaEstimadaEntrega= fechaCreacion.plusDays(15);
    }

    public EnvioBuilder withSeguro() {
        this.seguro = true;
        return this;
    }

    public EnvioBuilder withFragil() {
        this.fragil = true;
        return this;
    }

    public EnvioBuilder withFirma() {
        this.firma = true;
        return this;
    }

    public EnvioBuilder withPrioridad() {
        this.prioridad = true;
        return this;
    }

    public Envio build() {
        return new Envio(this);
    }

}
