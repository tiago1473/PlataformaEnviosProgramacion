package utils.mappers;
import models.DTO.DireccionDTO;
import models.DTO.UsuarioDTO;
import models.Direccion;
import models.Usuario;

public class DireccionMapper {

    public static Direccion toDireccion(DireccionDTO direccionDTO){
        if(direccionDTO == null){
            return null;
        }
        return new Direccion(
                direccionDTO.getAlias(),
                direccionDTO.getCalle(),
                direccionDTO.getCiudad());
    }

    public static DireccionDTO toDireccionDTO(Direccion direccion){
        if(direccion == null){
            return null;
        }
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setAlias(direccion.getAlias());
        direccionDTO.setCalle(direccion.getCalle());
        direccionDTO.setCiudad(direccion.getCiudad());
        direccionDTO.setId(direccion.getIdDireccion());
        direccionDTO.setCoordenada(direccion.getCoordenada());
        return direccionDTO;
    }

    public static void actualizarDireccion(DireccionDTO direccionDTO, Direccion direccion){
        if(direccionDTO ==null || direccion==null){return;}
        direccion.setIdDireccion(direccionDTO.getId());
        direccion.setAlias(direccionDTO.getAlias());
        direccion.setCalle(direccionDTO.getCalle());
        direccion.setCiudad(direccionDTO.getCiudad());
    }
}
