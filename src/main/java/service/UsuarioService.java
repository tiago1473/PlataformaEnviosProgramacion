package service;
import models.*;
import models.DTO.DireccionDTO;
import models.DTO.EnvioDTO;
import models.DTO.UsuarioDTO;
import utils.mappers.DireccionMapper;
import utils.mappers.EnvioMapper;
import utils.mappers.UsuarioMapper;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final PlataformaEnvios plataformaEnvios;

    public UsuarioService(){
        this.plataformaEnvios = PlataformaEnvios.getInstancia();
    }

    public ArrayList<UsuarioDTO> obtenerTodosLosUsuario (){
        ArrayList<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();
        for(UsuarioBase usuario : this.plataformaEnvios.getUsuarios()){
            if(usuario instanceof Usuario){
                listaUsuariosDTO.add(UsuarioMapper.toUsuarioDTO((Usuario) usuario));
            }

        }
        return listaUsuariosDTO;
    }

    public UsuarioDTO buscarUsuarioId(String id) {
        for (UsuarioBase usuario : this.plataformaEnvios.getUsuarios()) {
            if (usuario instanceof Usuario && usuario.getId().equals(id)) {
                return UsuarioMapper.toUsuarioDTO((Usuario) usuario);
            } else if (usuario.getId().equals(id)) {
                return UsuarioMapper.tousuarioBaseDTO(usuario);
            }
            System.out.println("No se encontró usuario con ID: " + id);
        }
        return null;
    }

    public Usuario buscarUsuarioEntidad(String id){
        for(UsuarioBase usuario : this.plataformaEnvios.getUsuarios()){
            if(usuario.getId().equals(id) && usuario instanceof Usuario){
                return (Usuario) usuario;
            }
        }
        return null;
    }

    public boolean agregarUsuario(UsuarioDTO usuarioDTO){
        if(buscarUsuarioEntidad(usuarioDTO.getId()) == null) {
            this.plataformaEnvios.addUsuario(UsuarioMapper.toUsuario(usuarioDTO));
            return true;
        }
        return false;
    }

    public boolean eliminarUsuario(String id){
        Usuario usuarioHallado = buscarUsuarioEntidad(id);
        if(usuarioHallado != null){
            this.plataformaEnvios.getUsuarios().remove(usuarioHallado);
            return true;
        }
        return false;
    }

    public boolean actualizarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuarioHallado = buscarUsuarioEntidad(usuarioDTO.getId());
        if(usuarioHallado != null){
            UsuarioMapper.actualizarUsuario(usuarioDTO, usuarioHallado);
            for (Envio envio : usuarioHallado.getEnvios()){
                envio.setNombreUsuario(usuarioHallado.getNombre());
            }
            return true;
        }
        return false;
    }

    public boolean agregarDireccionUsuario(String id, DireccionDTO direccionDTO){
        Usuario usuarioHallado = buscarUsuarioEntidad(id);
        if (usuarioHallado != null && direccionDTO != null){
            usuarioHallado.getDireccion().add(DireccionMapper.toDireccion(direccionDTO));
            return true;
        }
        return true;
    }

    public List<DireccionDTO> obtenerDireccionesUsuario(String id){
        List<DireccionDTO> direccionesUsuario = new ArrayList<>();
        Usuario usuarioHallado = buscarUsuarioEntidad(id);
        if(usuarioHallado != null){
            for(Direccion direccion : usuarioHallado.getDireccion()){
                direccionesUsuario.add(DireccionMapper.toDireccionDTO(direccion));
            }
            return direccionesUsuario;
        }
        return direccionesUsuario;
    }

    public Direccion buscarDireccionUsuario(Usuario usuario, DireccionDTO direccionDTO){
        for(Direccion direccion : usuario.getDireccion()){
            if(direccion.getIdDireccion().equals(direccionDTO.getId())){
                return direccion;
            }
        }
        return null;
    }

    public boolean actualizarDireccionUsuario(String idUsuario, DireccionDTO direccionDTO){
        Usuario usuarioHallado = buscarUsuarioEntidad(idUsuario);
        Direccion direccion = buscarDireccionUsuario(usuarioHallado, direccionDTO);
        if(usuarioHallado != null && direccion != null){
            DireccionMapper.actualizarDireccion(direccionDTO, direccion);
            return true;
        }
        return false;
    }

    public ArrayList<EnvioDTO> obtenerEnviosUsuario (String id){
        ArrayList<EnvioDTO> listaEnviosDTOUsuario = new ArrayList<>();
        Usuario usuarioHallado = buscarUsuarioEntidad(id);
        for(Envio envio : usuarioHallado.getEnvios()){
            listaEnviosDTOUsuario.add(EnvioMapper.toDTOPantallaUsuario(envio));
        }
        return listaEnviosDTOUsuario;
    }

    public boolean actualizarEstadoEnvioUsuario(String usuarioId, String envioId){
        Usuario usuarioHallado = buscarUsuarioEntidad(usuarioId);
        if (usuarioHallado == null || usuarioHallado.getEnvios() == null) {
            return false;
        }
        for (Envio envio : usuarioHallado.getEnvios()){
            if (envio != null && envio.getId().equals(envioId)){
                envio.porAsignar();
                return true;
            }
        }
        return false;
    }

    public EnvioDTO buscarEnvioUsuario (String idUsuario, String idEnvio){
        Usuario usuarioHallado = buscarUsuarioEntidad(idUsuario);
        for(Envio envio : usuarioHallado.getEnvios()){
            if(envio.getId().equals(idEnvio)){
                EnvioDTO envioHallado = EnvioMapper.toDTOPantallaUsuario(envio);
                return envioHallado;
            }
        }
        return null;
    }

    public boolean eliminarDireccionUsuario(String idUsuario, DireccionDTO direccionDTO){
        Usuario usuarioHallado = buscarUsuarioEntidad(idUsuario);
        Direccion direccion = buscarDireccionUsuario(usuarioHallado, direccionDTO);
        if(usuarioHallado != null && direccion != null){
            usuarioHallado.getDireccion().remove(direccion);
            return true;
        }
        return false;
    }
}
