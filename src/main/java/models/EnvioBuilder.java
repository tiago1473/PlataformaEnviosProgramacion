package models;
import java.time.LocalDateTime;

public class EnvioBuilder {

    Direccion origen;
    Direccion destino;
    double peso;
    double largo;
    double ancho;
    double alto;
    double costo;
    EstadoEnvio estado;
    LocalDateTime fechaCreacion = LocalDateTime.now();
    LocalDateTime fechaEstimadaEntrega = fechaCreacion.plusDays(15);
    boolean seguro;
    boolean fragil;
    boolean firma;
    boolean prioridad;

    public EnvioBuilder(Direccion origen, Direccion destino, double peso, double largo,
                        double ancho, double alto, EstadoEnvio estado) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.estado = estado;
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
