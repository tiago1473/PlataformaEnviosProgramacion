package service.costoAdicionalStrategy;

import models.Envio;
import service.TarifaService;

public class CostoFragilStrategy implements CostoAdicionalStrategy {

    @Override
    public double calcularCostoAdicional(double costoBase, Envio envio) {
        if (envio.isFragil()) {
            return costoBase * TarifaService.EXTRA_FRAGIL;
        }
        return 0.0;
    }
}
