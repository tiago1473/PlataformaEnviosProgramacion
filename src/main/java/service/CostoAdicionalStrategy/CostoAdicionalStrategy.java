package service.CostoAdicionalStrategy;
import models.Envio;

public interface CostoAdicionalStrategy {
    double calcularCostoAdicional(double costoBase, Envio envio);
}
