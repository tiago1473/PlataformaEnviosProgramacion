package service.facade;

import models.DTO.UsuarioDTO;
import service.LoginService;

public class LoginFacade {
    private final LoginService loginService;

    public LoginFacade(){
        this.loginService = new LoginService();
    }

    public UsuarioDTO validarCredenciales(String id, String password) {
        return loginService.validarCredenciales(id,password);
    }

}
