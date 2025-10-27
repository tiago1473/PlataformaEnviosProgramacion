package models;

import java.util.ArrayList;

public class PlataformaEnvios {
    ArrayList<Repartidor> repartidores;
    ArrayList<Usuario> usuarios;
    private static PlataformaEnvios instancia;

    private PlataformaEnvios() {
        repartidores = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public static PlataformaEnvios getInstancia() {
        if (instancia == null) {
            instancia = new PlataformaEnvios();
        }
        return instancia;
    }

    public void addRepartidor(Repartidor repartidor) {
        repartidores.add(repartidor);
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
}
