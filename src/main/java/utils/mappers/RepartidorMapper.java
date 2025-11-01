package utils.mappers;

import models.DTO.RepartidorDTO;
import models.Repartidor;

public class RepartidorMapper {

    public static RepartidorDTO toDTO(Repartidor repartidor) {
        if (repartidor == null) return null;
        return new RepartidorDTO(
                repartidor.getId(),
                repartidor.getNombre(),
                repartidor.getTelefono(),
                repartidor.getEstado(),
                repartidor.getEnviosAsignados()
        );
    }

    public static Repartidor toEntity(RepartidorDTO dto) {
        if (dto == null) return null;
        return new Repartidor(
                dto.getId(),
                dto.getNombre(),
                dto.getTelefono(),
                dto.getEstado()
        );
    }

    public static void updateEntityFromDTO(Repartidor entity,RepartidorDTO dto) {
        if (entity == null || dto == null) return;
        entity.setNombre(dto.getNombre());
        entity.setTelefono(dto.getTelefono());
        entity.setEstado(dto.getEstado());
    }

    public static void updateDTOFromEntity(Repartidor entity,RepartidorDTO dto) {
        if (entity == null || dto == null) return;
        dto.setNombre(entity.getNombre());
        dto.setTelefono(entity.getTelefono());
        dto.setEstado(entity.getEstado());
        dto.setLatitud(entity.getZonaCobertura().getCoordenada().getLatitud());
        dto.setLongitud(entity.getZonaCobertura().getCoordenada().getLongitud());
        dto.setRadio(entity.getZonaCobertura().getRadio());
        dto.setEnviosAsignados(entity.getEnviosAsignados());
    }
}
