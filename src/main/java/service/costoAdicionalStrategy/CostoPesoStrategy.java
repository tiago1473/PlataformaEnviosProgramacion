package service.costoAdicionalStrategy;

import models.Envio;
import service.TarifaService;

public class CostoPesoStrategy implements CostoAdicionalStrategy {

    @Override
    public double calcularCostoAdicional(double costoBase, Envio envio) {
        double peso = envio.getPeso();
        if (peso > TarifaService.PESO_UMBRAL) {
            double pesoExcedente = peso - TarifaService.PESO_UMBRAL;
            return pesoExcedente * TarifaService.COSTO_POR_KG_EXCEDENTE;
        }
        return 0.0;
    }
}
