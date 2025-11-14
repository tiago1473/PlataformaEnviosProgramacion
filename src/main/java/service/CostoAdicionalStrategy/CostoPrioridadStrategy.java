package service.CostoAdicionalStrategy;
import models.Envio;
import service.TarifaService;

public class CostoPrioridadStrategy implements CostoAdicionalStrategy {

    @Override
    public double calcularCostoAdicional(double costoBase, Envio envio) {
        if (envio.isPrioridad()) {
            return costoBase * TarifaService.EXTRA_PRIORIDAD;
        }
        return 0.0;
    }
}
