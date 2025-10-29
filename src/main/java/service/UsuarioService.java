package service;

import models.DTO.DireccionDTO;
import models.DTO.UsuarioDTO;
import models.Direccion;
import models.PlataformaEnvios;
import models.Usuario;
import utils.mappers.DireccionMapper;
import utils.mappers.UsuarioMapper;
import java.util.ArrayList;

public class UsuarioService {
    private final PlataformaEnvios plataformaEnvios;

    public UsuarioService(){
        this.plataformaEnvios = PlataformaEnvios.getInstancia();
    }

    public ArrayList<UsuarioDTO> obtenerTodosLosUsuario (){
        ArrayList<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();
        for(Usuario usuario : this.plataformaEnvios.getUsuarios()){
            listaUsuariosDTO.add(UsuarioMapper.toUsuarioDTO(usuario));
        }
        return listaUsuariosDTO;
    }

    public UsuarioDTO buscarUsuarioId(String id){
        for(Usuario usuario : this.plataformaEnvios.getUsuarios()){
            if(usuario.getId().equals(id)){
                return UsuarioMapper.toUsuarioDTO(usuario);
            }
        }
        return null;
    }

    public UsuarioDTO validarAccesoUsuario(String id, String password){
        UsuarioDTO usuarioHallado =  buscarUsuarioId(id);
        if(usuarioHallado != null &&  usuarioHallado.getPassword().equals(password)){
            return usuarioHallado;
        }
        return null;
    }

    public Usuario buscarUsuarioEntidad(String id){
        for(Usuario usuario : this.plataformaEnvios.getUsuarios()){
            if(usuario.getId().equals(id)){
                return usuario;
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

    public ArrayList<DireccionDTO> obtenerDireccionesUsuario(String id){
        ArrayList<DireccionDTO> direccionesUsuario = new ArrayList<>();
        Usuario usuarioHallado = buscarUsuarioEntidad(id);
        if(usuarioHallado != null){
            for(Direccion direccion : usuarioHallado.getDireccion()){
                direccionesUsuario.add(DireccionMapper.toDireccionDTO(direccion));
            }
            return direccionesUsuario;
        }
        return direccionesUsuario; //Retornaría una lista vacía
    }
}
