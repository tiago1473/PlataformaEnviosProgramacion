package utils.mappers;
import models.DTO.EnvioDTO;
import models.Envio;

public class EnvioMapper {

    public static EnvioDTO toDTO(Envio envio){
        if (envio == null){
            return null;
        }
        return new EnvioDTO(
                envio.getOrigen(),
                envio.getDestino(),
                envio.getPeso(),
                envio.getLargo(),
                envio.getAncho(),
                envio.getAlto(),
                envio.getEstado());
    }
}
