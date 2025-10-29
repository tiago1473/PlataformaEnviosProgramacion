package utils.mappers;
import models.DTO.DireccionDTO;
import models.Direccion;

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
}
