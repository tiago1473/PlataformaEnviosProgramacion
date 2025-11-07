package service.facade;

import models.DTO.DireccionDTO;
import models.DTO.EnvioDTO;
import models.EstadoEnvio;
import service.EnvioService;
import service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

public class EnvioFacade {

    private final EnvioService envioService;
    private final UsuarioService usuarioService;

    public EnvioFacade() {
        this.envioService = new EnvioService();
        this.usuarioService = new UsuarioService();
    }

    public boolean crearEnvioUsuario(String usuarioId, EnvioDTO envioDTO) {
        envioDTO.setEstado(EstadoEnvio.SOLICITADO);
        return envioService.agregarEnvioUsuario(usuarioId, envioDTO);
    }

    public boolean modificarEnvioUsuario(String usuarioId, String envioId, EnvioDTO envioDTO) {
        return envioService.modificarEnvioUsuario(usuarioId, envioId, envioDTO);
    }

    public boolean cancelarEnvioUsuario(String usuarioId, String envioId) {
        return envioService.cancelarEnvioUsuario(usuarioId, envioId);
    }

    public double cotizarEnvio(EnvioDTO envioDTO) {
        return envioService.cotizarEnvio(envioDTO);
    }

    public List<DireccionDTO> obtenerDireccionesUsuario(String usuarioId) {
        return usuarioService.obtenerDireccionesUsuario(usuarioId);
    }

    public EnvioDTO buscarEnvioUsuario(String usuarioId, String envioId) {
        return usuarioService.buscarEnvioUsuario(usuarioId, envioId);
    }
}
