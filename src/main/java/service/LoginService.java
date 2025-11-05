package service;
import models.DTO.UsuarioDTO;

public class LoginService {
    private final UsuarioService usuarioService;

    public LoginService() {
        this.usuarioService = new UsuarioService();
    }

    public UsuarioDTO validarCredenciales(String id, String password) {

        UsuarioDTO usuarioValido = validarAccesoUsuario(id, password);

        if (usuarioValido != null) {
            System.out.println("Usuario autenticado exitosamente: " + usuarioValido.getId());
            return usuarioValido;
        } else {
            System.out.println("Credenciales inválidas, intente ingresar nuevamente");
            return null;
        }
    }

    public UsuarioDTO validarAccesoUsuario(String id, String password){

        UsuarioDTO usuarioHallado = usuarioService.buscarUsuarioId(id);
        if(usuarioHallado != null && usuarioHallado.getPassword().equals(password)){
            System.out.println("Contraseña correcta para usuario: " + usuarioHallado.getNombre());
            return usuarioHallado;
        }
        System.out.println("Contraseña Incorrecta para usuario");
        return null;
    }
}
