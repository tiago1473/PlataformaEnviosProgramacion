package utils.mappers;

import models.DTO.UsuarioDTO;
import models.Direccion;
import models.Envio;
import models.Usuario;

import java.util.ArrayList;

public class UsuarioMapper {

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        if (usuario == null) {return null;}

        return new UsuarioDTO(
                usuario.getPassword(),
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getEnvios(),
                usuario.getDireccion());
    }

    public static Usuario toUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {return null;}

        return new Usuario(
                usuarioDTO.getId(),
                usuarioDTO.getPassword(),
                usuarioDTO.getNombre(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getTelefono());
    }

    public static void actualizarUsuario(UsuarioDTO usuarioDTO, Usuario usuario){
        if(usuarioDTO ==null || usuario==null){return;}
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
    }
}
