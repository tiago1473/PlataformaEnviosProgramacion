package service.estadoState;

import models.Envio;

public class PorAsignarState implements EstadoEnvioState {

    @Override
    public void porAsignar(Envio envio) {
        throw new IllegalStateException("El envío ya fue ASIGNADO");
    }

    @Override
    public void asignar(Envio envio) {
        if (envio.getNombreRepartidor() == null || envio.getNombreRepartidor().trim().isEmpty()) {
            throw new IllegalStateException("No se ha asignado un repartidor al envío");
        }
        envio.setEstado(new AsignadoState());
    }

    @Override
    public void enRuta(Envio envio) {
        throw new IllegalStateException("Debe asignarse a un repartidor antes de ponerlo en ruta");
    }

    @Override
    public void entregar(Envio envio) {
        throw new IllegalStateException("No puede entregarse antes de estar en ruta");
    }

    @Override
    public String getNombre() {
        return "POR_ASIGNAR";
    }
}

