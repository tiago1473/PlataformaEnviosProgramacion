package utils.mappers;
import models.DTO.EnvioDTO;
import models.Envio;
import models.EstadoEnvio;

import java.util.ArrayList;

public class EnvioMapper {

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
        envioDTO.setFechaEstimadaEntrega(envio.getFechaEstimadaEntrega());
        envioDTO.setPrioridad(envio.isPrioridad());

         if (envio.getIncidencias() != null) {
             envioDTO.setIncidencias(new ArrayList<>(envio.getIncidencias()));
         } else {
             envioDTO.setIncidencias(new ArrayList<>());
         }

        if (envio.getEstado() == EstadoEnvio.POR_ASIGNAR ||  envio.getEstado() == EstadoEnvio.ENRUTA ||
        envio.getEstado() == EstadoEnvio.ENTREGADO){
            envioDTO.setEstadoPago("Pago");
        }else{
            envioDTO.setEstadoPago("No Pago");
        }
        return envioDTO;
    }

    public static EnvioDTO toDTOPantallaAdministrador(Envio envio){
        if (envio == null){
            return null;
        }
        EnvioDTO envioDTO = new EnvioDTO();
        envioDTO.setId(envio.getId());
        envioDTO.setFechaCreacion(envio.getFechaCreacion());
        envioDTO.setDestino(envio.getDestino());
        envioDTO.setCosto(envio.getCosto());
        envioDTO.setEstado(envio.getEstado());
        envioDTO.setNombreUsuario(envio.getNombreUsuario());

        //Traigo las incidencias del envío
        if (envio.getIncidencias() != null) {
            envioDTO.setIncidencias(new ArrayList<>(envio.getIncidencias()));
        } else {
            envioDTO.setIncidencias(new ArrayList<>());
        }

        if (envio.getEstado() == EstadoEnvio.ASIGNADO){
            envioDTO.setEstadoPago("PAGO");
        }else{
            envioDTO.setEstadoPago("SIN PAGAR");
        }
        return envioDTO;
    }

    public static EnvioDTO toDTOPantallaRepartidor(Envio envio){
         if (envio == null){
             return null;
         }
         EnvioDTO envioDTO = new EnvioDTO();
         envioDTO.setId(envio.getId());
         envioDTO.setOrigen(envio.getOrigen());
         envioDTO.setDestino(envio.getDestino());
        return envioDTO;
    }

}
