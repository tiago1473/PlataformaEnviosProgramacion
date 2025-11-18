package models;

import service.pagoFactory.MetodoPago;
import service.pagoFactory.PagoFactory;

import java.time.LocalDateTime;

public class Pago {
    public static int contador = 1;
    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private MetodoPago metodoPago;
    private String tipoMetodoPago;
    private boolean isAprobado;
    private String idEnvio;

    public Pago(double monto, String tipoMetodoPago, String idEnvio) {
        this.idPago = "PAGO-" + String.format("%04d", contador++);
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.tipoMetodoPago = tipoMetodoPago;
        this.idEnvio = idEnvio;
        this.metodoPago = PagoFactory.crearPago(tipoMetodoPago);
        this.isAprobado = this.metodoPago.procesarPago(monto);
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getTipoMetodoPago() {
        return tipoMetodoPago;
    }

    public void setTipoMetodoPago(String tipoMetodoPago) {
        this.tipoMetodoPago = tipoMetodoPago;
    }

    public boolean isAprobado() {
        return isAprobado;
    }

    public void setAprobado(boolean aprobado) {
        isAprobado = aprobado;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago='" + idPago + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", tipoMetodoPago='" + tipoMetodoPago + '\'' +
                ", idEnvio='" + idEnvio + '\'' +
                '}';
    }
}
