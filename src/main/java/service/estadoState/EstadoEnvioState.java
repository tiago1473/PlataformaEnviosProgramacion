package service.estadoState;

import models.Envio;

public interface EstadoEnvioState {
    void porAsignar(Envio envio);

    void asignar(Envio envio);

    void enRuta(Envio envio);

    void entregar(Envio envio);

    String getNombre();
}
