package utils.mappers;
import models.DTO.EnvioDTO;
import models.Envio;
import models.EstadoEnvio;

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

    public static EnvioDTO toDTOPantallaUsuario(Envio envio){
        if (envio == null){
            return null;
        }
        EnvioDTO envioDTO = new EnvioDTO();
        envioDTO.setId(envio.getId());
        envioDTO.setOrigen(envio.getOrigen());
        envioDTO.setDestino(envio.getDestino());
        envioDTO.setCosto(envio.getCosto());
        envioDTO.setFechaCreacion(envio.getFechaCreacion());
        envioDTO.setFechaEntrega(envio.getFechaEntrega());
        envioDTO.setEstado(envio.getEstado());
        envioDTO.setPeso(envio.getPeso());
        envioDTO.setLargo(envio.getLargo());
        envioDTO.setAncho(envio.getAncho());
        envioDTO.setAlto(envio.getAlto());
        envioDTO.setSeguro(envio.isSeguro());
        envioDTO.setFragil(envio.isFragil());
        envioDTO.setFirma(envio.isFirma());
        envioDTO.setPrioridad(envio.isPrioridad());
        if (envio.getEstado() == EstadoEnvio.ASIGNADO){
            envioDTO.setEstadoPago("Pago");
        }else{
            envioDTO.setEstadoPago("No Pago");
        }
        return envioDTO;
    }
}
