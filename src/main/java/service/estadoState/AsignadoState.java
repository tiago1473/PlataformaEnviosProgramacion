package service.estadoState;

import models.Envio;

public class AsignadoState implements EstadoEnvioState{
    @Override
    public void porAsignar(Envio envio) {
        throw new IllegalStateException("El envio ya fue asignado");
    }

    @Override
    public void asignar(Envio envio) {
        throw new IllegalStateException("El envio ya fue asignado");
    }

    @Override
    public void enRuta(Envio envio) {
        envio.setEstado(new EnRutaState());
    }

    @Override
    public void entregar(Envio envio) {
        throw new IllegalStateException("Debe estar EN RUTA antes de entregar");
    }

    @Override
    public String getNombre() {
        return "ASIGNADO";
    }
}
