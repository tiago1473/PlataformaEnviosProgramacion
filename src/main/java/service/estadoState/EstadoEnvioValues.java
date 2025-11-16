package service.estadoState;

import java.util.List;

public class EstadoEnvioValues {
    public static List<EstadoEnvioState> values() {
        return List.of(
                new SolicitadoState(),
                new PorAsignarState(),
                new AsignadoState(),
                new EnRutaState(),
                new EntregadoState()
        );
    }

}
