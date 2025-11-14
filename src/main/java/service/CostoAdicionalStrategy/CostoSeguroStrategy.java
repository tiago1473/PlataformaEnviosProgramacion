package service.CostoAdicionalStrategy;

import models.Envio;
import service.TarifaService;

public class CostoSeguroStrategy implements CostoAdicionalStrategy {

    @Override
    public double calcularCostoAdicional(double costoBase, Envio envio) {
        if (envio.isSeguro()) {
            return costoBase * TarifaService.EXTRA_SEGURO;
        }
        return 0.0;
    }
}
