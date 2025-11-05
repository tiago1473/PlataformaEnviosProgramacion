package service;
import models.DTO.UsuarioDTO;
import models.PlataformaEnvios;
import models.Usuario;
import service.facade.UsuarioFacade;

public class LoginService {
    private final UsuarioFacade usuarioFacade;
    private final String idAdmin = "1010470647";
    private final String passwordAdmin = "proyectoEnvios123";

    public LoginService() {
        this.usuarioFacade = new UsuarioFacade();
    }

    public UsuarioDTO validarCredenciales(String id, String password) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("ID de usuario vacío");
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Contraseña vacía");
            return null;
        }

        UsuarioDTO usuarioValido = validarAccesoUsuario(id.trim(), password);

        if (usuarioValido != null) {
            System.out.println("Usuario autenticado exitosamente: " + usuarioValido.getId());
            return usuarioValido;
        } else {
            System.out.println("Credenciales inválidas, intente ingresar nuevamente");
            return null;
        }
    }

    public UsuarioDTO validarAccesoUsuario(String id, String password){
        UsuarioDTO usuarioHallado = usuarioFacade.buscarUsuarioId(id);
        if(usuarioHallado != null && usuarioHallado.getPassword().equals(password)){
            System.out.println("Contraseña correcta para usuario: " + usuarioHallado.getNombre());
            return usuarioHallado;
        }
        System.out.println("Contraseña Incorrecta para usuario");
        return null;
    }

    public boolean existeUsuario(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        UsuarioDTO usuario = usuarioFacade.buscarUsuarioId(id.trim());
        return usuario != null;
    }

    public boolean esAdministrador(String id, String password) {
        return id.equals(idAdmin) && password.equals(passwordAdmin);
    }
}
