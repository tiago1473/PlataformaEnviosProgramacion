package utils.mappers;

import models.DTO.UsuarioDTO;
import models.Rol;
import models.Usuario;
import models.UsuarioBase;

public class UsuarioMapper {

    public static UsuarioDTO tousuarioBaseDTO(UsuarioBase usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getPassword());
    }

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(
                usuario.getPassword(),
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono());
    }

    public static Usuario toUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {return null;}

        return new Usuario(
                usuarioDTO.getId(),
                usuarioDTO.getPassword(),
                Rol.USER,
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
