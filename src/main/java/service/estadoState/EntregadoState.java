package service.estadoState;

import models.Envio;

public class EntregadoState implements EstadoEnvioState{
    @Override
    public void porAsignar(Envio envio) {
        throw new IllegalStateException("Ya fue entregado");
    }

    @Override
    public void asignar(Envio envio) {
        throw new IllegalStateException("Ya fue entregado");
    }

    @Override
    public void enRuta(Envio envio) {
        throw new IllegalStateException("Ya fue entregado");
    }

    @Override
    public void entregar(Envio envio) {
        throw new IllegalStateException("Ya está entregado");
    }

    @Override
    public String getNombre() {
        return "ENTREGADO";
    }
}
