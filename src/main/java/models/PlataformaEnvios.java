package models;

import java.util.ArrayList;

public class PlataformaEnvios {
    ArrayList<Repartidor> repartidores;
    ArrayList<Usuario> usuarios;
    ArrayList<Envio> envios;
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

    public void addEnvio(Envio envio) { envios.add(envio);}

    public ArrayList<Repartidor> getRepartidores() {
        return this.repartidores;
    }

    public void setRepartidores(ArrayList<Repartidor> repartidores) {
        this.repartidores = repartidores;
    }

    public ArrayList<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Envio> getEnvios() {
        return this.envios;
    }

    public void setEnvios(ArrayList<Envio> envios) {
        this.envios = envios;
    }
}
