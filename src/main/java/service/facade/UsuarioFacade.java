package service.facade;
import models.DTO.DireccionDTO;
import models.DTO.EnvioDTO;
import models.DTO.UsuarioDTO;
import models.Direccion;
import models.Usuario;
import service.EnvioService;
import service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

public class UsuarioFacade {

    private final UsuarioService usuarioService;
    private final EnvioService envioService;

    public UsuarioFacade(){
        this.usuarioService = new UsuarioService();
        this.envioService = new EnvioService();
    }

    public UsuarioDTO buscarUsuarioId(String id){
        return usuarioService.buscarUsuarioId(id);
    }

    public Usuario buscarUsuarioEntidad(String id){
        return usuarioService.buscarUsuarioEntidad(id);
    }

    public boolean agregarUsuario(UsuarioDTO usuarioDTO){
        return usuarioService.agregarUsuario(usuarioDTO);
    }

    public boolean actualizarUsuario(UsuarioDTO usuarioDTO){
        return usuarioService.actualizarUsuario(usuarioDTO);
    }

    public boolean agregarDireccionUsuario(String id, DireccionDTO direccionDTO){
        return usuarioService.agregarDireccionUsuario(id, direccionDTO);
    }

    public List<DireccionDTO> obtenerDireccionesUsuario(String id) {
        return usuarioService.obtenerDireccionesUsuario(id);
    }

    public boolean eliminarDireccionUsuario(String idUsuario, DireccionDTO direccionDTO){
        return usuarioService.eliminarDireccionUsuario(idUsuario, direccionDTO);
    }

    public Direccion buscarDireccionUsuario(Usuario usuario, DireccionDTO direccionDTO){
        return usuarioService.buscarDireccionUsuario(usuario, direccionDTO);
    }

    public boolean actualizarDireccionUsuario(String idUsuario, DireccionDTO direccionDTO){
        return usuarioService.actualizarDireccionUsuario(idUsuario, direccionDTO);
    }

    public ArrayList<EnvioDTO> obtenerEnviosUsuario (String id){
        return usuarioService.obtenerEnviosUsuario(id);
    }

    public EnvioDTO buscarEnvioUsuario (String idUsuario, String idEnvio){
       return usuarioService.buscarEnvioUsuario(idUsuario, idEnvio);
    }

    public boolean procesarPagoEnvio(String id, double costo, String metodoPago) {
        return envioService.procesarPago(id, costo, metodoPago);
    }
}
