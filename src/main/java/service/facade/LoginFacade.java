package service.facade;

import models.DTO.UsuarioDTO;
import service.LoginService;

public class LoginFacade {
    private final LoginService loginService;

    public LoginFacade(){
        this.loginService = new LoginService();
    }

    public UsuarioDTO validarCredenciales(String id, String password) {
        return loginService.validarCredenciales(id, password);
    }

    public boolean existeUsuario(String id) {
        return loginService.existeUsuario(id);
    }

    public boolean esAdministrador(String id, String paswword) {
        return loginService.esAdministrador(id, paswword);
    }
}
