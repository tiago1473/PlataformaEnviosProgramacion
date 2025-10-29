package service;

import models.DTO.EnvioDTO;
import models.Direccion;
import models.Envio;
import models.EnvioBuilder;

public class EnvioService {

    public EnvioService(){
    }

    public Envio crearEnvio(EnvioDTO envioDTO) {

        EnvioBuilder envioBuilder = new EnvioBuilder(
                envioDTO.getOrigen(),
                envioDTO.getDestino(),
                envioDTO.getPeso(),
                envioDTO.getLargo(),
                envioDTO.getAncho(),
                envioDTO.getAlto(),
                envioDTO.getEstado());

        if (envioDTO.isFirma()) {
            envioBuilder.withFirma();
        }
        if (envioDTO.isFragil()) {
            envioBuilder.withFragil();
        }
        if (envioDTO.isPrioridad()) {
            envioBuilder.withPrioridad();
        }
        if (envioDTO.isSeguro()) {
            envioBuilder.withSeguro();
        }
        Envio envio = envioBuilder.build();
        envio.setCosto(calcularCostoEnvio(envio));
        return envio;
    }

    public double calcularCostoEnvio(Envio envio) {
        double costo = calcularDistancia(envio.getOrigen(), envio.getDestino()) * TarifaService.TARIFA_POR_KM;
        double adicion = 0;
        if(envio.isFirma()){
            adicion += costo*(1+TarifaService.EXTRA_FIRMA);
        }
        if(envio.isFragil()){
            adicion += costo*(1+TarifaService.EXTRA_FRAGIL);
        }
        if(envio.isPrioridad())
            adicion += costo*(1+TarifaService.EXTRA_PRIORIDAD);
        if(envio.isSeguro()){
            adicion += costo*(1+TarifaService.EXTRA_SEGURO);
        }
        return costo + adicion;
    }

    public static double calcularDistancia(Direccion origen, Direccion destino) {
        double radioTierra = 6371; // km
        double dLat = Math.toRadians(destino.getCoordenada().getLatitud() - origen.getCoordenada().getLatitud());
        double dLon = Math.toRadians(destino.getCoordenada().getLongitud() - origen.getCoordenada().getLongitud());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(origen.getCoordenada().getLatitud()))
                * Math.cos(Math.toRadians(destino.getCoordenada().getLatitud()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radioTierra * c;
    }

}
