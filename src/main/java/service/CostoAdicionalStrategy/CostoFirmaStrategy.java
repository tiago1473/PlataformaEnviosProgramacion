package service.CostoAdicionalStrategy;
import models.Envio;
import service.TarifaService;

public class CostoFirmaStrategy implements CostoAdicionalStrategy {

    @Override
    public double calcularCostoAdicional(double costoBase, Envio envio) {
        if (envio.isFirma()) {
            return costoBase * TarifaService.EXTRA_FIRMA;
        }
        return 0.0;
    }
}