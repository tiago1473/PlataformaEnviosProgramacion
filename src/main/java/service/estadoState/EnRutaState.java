package service.estadoState;
import models.Envio;
import java.time.LocalDateTime;

public class EnRutaState implements EstadoEnvioState{
    @Override
    public void porAsignar(Envio envio) {
        throw new IllegalStateException("Ya está en RUTA");
    }

    @Override
    public void asignar(Envio envio) {
        throw new IllegalStateException("Ya está EN RUTA");
    }

    @Override
    public void enRuta(Envio envio) {
        throw new IllegalStateException("Ya está EN RUTA");
    }

    @Override
    public void entregar(Envio envio) {
        envio.setEstado(new EntregadoState());
        envio.setFechaEntrega(LocalDateTime.now());
    }

    @Override
    public String getNombre() {
        return "EN_RUTA";
    }
}
