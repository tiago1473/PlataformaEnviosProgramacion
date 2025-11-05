package models;
import models.DTO.UsuarioDTO;

public class SessionManager {
    private static SessionManager instancia;
    private UsuarioDTO usuarioLogueado;

    private SessionManager() {
    }

    public static SessionManager getInstancia() {
        if (instancia == null) {
            instancia = new SessionManager();
        }
        return instancia;
    }

    public void iniciarSesion(UsuarioDTO usuario) {
        if (usuario != null) {
            this.usuarioLogueado = usuario;
            System.out.println("Sesión iniciada: " + usuario.getId());
        }
    }

    public void cerrarSesion() {
        if (usuarioLogueado != null) {
            System.out.println("Cerrando sesión para usuario: " + usuarioLogueado.getId());
            this.usuarioLogueado = null;
        }
    }

    public UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }
}
