package utils.mappers;

import models.DTO.EnvioRepartidorDTO;
import models.Envio;


public class EnvioRepartidorMapper {

    public static EnvioRepartidorDTO toDTO(Envio envio) {
        if (envio == null) return null;
        return new EnvioRepartidorDTO(
                envio.getId(),
                envio.getOrigen().getCiudad(),
                envio.getDestino().getCiudad()
        );
    }
}
