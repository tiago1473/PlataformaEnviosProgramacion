package service.estadoState;

import models.Envio;

public class SolicitadoState implements EstadoEnvioState {

    @Override
    public void porAsignar(Envio envio) {
        envio.setEstado(new PorAsignarState());
    }

    @Override
    public void asignar(Envio envio) {
        throw new IllegalStateException("No puede pasar a ASIGNAR desde SOLICITADO, debe hacer primero el PAGO");
    }

    @Override
    public void enRuta(Envio envio) {
        throw new IllegalStateException("No puede pasar a EN_RUTA desde SOLICITADO, debe hacer primero el PAGO");
    }

    @Override
    public void entregar(Envio envio) {
        throw new IllegalStateException("No puede entregarse desde SOLICITADO");
    }

    @Override
    public String getNombre() {
        return "SOLICITADO";
    }
}
