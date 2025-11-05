package service.facade;
import models.DTO.DireccionDTO;
import models.DTO.EnvioDTO;
import models.DTO.UsuarioDTO;
import models.Direccion;
import models.Envio;
import models.EstadoEnvio;
import models.Usuario;
import service.UsuarioService;
import utils.mappers.DireccionMapper;
import utils.mappers.EnvioMapper;
import utils.mappers.UsuarioMapper;
import java.util.ArrayList;

public class UsuarioFacade {

    private final UsuarioService usuarioService;

    public UsuarioFacade(){
        this.usuarioService = new UsuarioService();
    }

    public ArrayList<UsuarioDTO> obtenerTodosLosUsuario (){
        return usuarioService.obtenerTodosLosUsuario();
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

    public boolean eliminarUsuario(String id){
        return usuarioService.eliminarUsuario(id);
    }

    public boolean actualizarUsuario(UsuarioDTO usuarioDTO){
        return usuarioService.actualizarUsuario(usuarioDTO);
    }

    public boolean agregarDireccionUsuario(String id, DireccionDTO direccionDTO){
        return usuarioService.agregarDireccionUsuario(id, direccionDTO);
    }

    public ArrayList<DireccionDTO> obtenerDireccionesUsuario(String id) {
        return usuarioService.obtenerDireccionesUsuario(id);
    }

    public boolean eliminarDireccionUsuario(String idUsuario, DireccionDTO direccionDTO){
        return usuarioService.eliminarDireccionUsuario(idUsuario, direccionDTO);
    }

    public Direccion buscarDireccionUsuario(Usuario usuario, DireccionDTO direccionDTO){
        return usuarioService.buscarDireccionUsuario(usuario, direccionDTO);
    }

    public boolean actualizarDireccionUsuario(UsuarioDTO usuarioDTO, DireccionDTO direccionDTO){
        return usuarioService.actualizarDireccionUsuario(usuarioDTO, direccionDTO);
    }

    public ArrayList<EnvioDTO> obtenerEnviosUsuario (String id){
        return usuarioService.obtenerEnviosUsuario(id);
    }

    public boolean actualizarEstadoEnvioUsuario(String usuarioId, String envioId, EstadoEnvio nuevoEstado){
        return usuarioService.actualizarEstadoEnvioUsuario(usuarioId, envioId, nuevoEstado);
    }

    public EnvioDTO buscarEnvioUsuario (String idUsuario, String idEnvio){
       return usuarioService.buscarEnvioUsuario(idUsuario, idEnvio);
    }
}
