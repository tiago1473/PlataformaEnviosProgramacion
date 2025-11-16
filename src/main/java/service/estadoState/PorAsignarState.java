package service.estadoState;

import models.Envio;

public class PorAsignarState implements EstadoEnvioState {

    @Override
    public void porAsignar(Envio envio) {
        System.out.println("El envío ya fue ASIGNADO");
    }

    @Override
    public void asignar(Envio envio) {
        try {
            if (envio.getNombreRepartidor() != null) {
                envio.setEstado(new AsignadoState());
            }
        }catch (Exception e) {
            throw new IllegalStateException("No ha asignado un repartidor aun", e);
        }
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

