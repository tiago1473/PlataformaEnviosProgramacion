package utils;

import models.EstadoRepartidor;
import models.PlataformaEnvios;
import models.Repartidor;
import models.ZonaCobertura;

public class AppSetup {

    public AppSetup() {
        inicializarUsuarios();
        inicializarRepartidores();
    }

    private void inicializarRepartidores() {
        PlataformaEnvios plataforma=PlataformaEnvios.getInstancia();

        plataforma.addRepartidor(new Repartidor("1097404946","Laura Melisa","3117557643",EstadoRepartidor.ACTIVO,new ZonaCobertura()));
        plataforma.addRepartidor(new Repartidor("1094942977","Juan Sebastian","3203742977",EstadoRepartidor.ACTIVO,new ZonaCobertura()));
        plataforma.addRepartidor(new Repartidor("1010121631","Catalina","3162946447",EstadoRepartidor.ENRUTA,new ZonaCobertura()));
    }
    private void inicializarUsuarios() {

    }
}
