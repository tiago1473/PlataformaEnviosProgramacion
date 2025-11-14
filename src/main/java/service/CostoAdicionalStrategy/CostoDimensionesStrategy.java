package service.CostoAdicionalStrategy;
import models.Envio;
import service.TarifaService;

public class CostoDimensionesStrategy implements CostoAdicionalStrategy {

    @Override
    public double calcularCostoAdicional(double costoBase, Envio envio) {
        double largo = envio.getLargo();
        double ancho = envio.getAncho();
        double alto = envio.getAlto();

        double costoPorVolumen = 0.0;
        double costoPorArea = 0.0;

        double volumen = (largo * ancho * alto) / 1000000; //Las entradas deben ser en cm
        if (volumen > TarifaService.VOLUMEN_UMBRAL) {
            double volumenExcedente = volumen - TarifaService.VOLUMEN_UMBRAL;
            costoPorVolumen = volumenExcedente * TarifaService.COSTO_POR_M3_EXCEDENTE;
        }

        double area = (largo * ancho) / 10000;
        if (area > TarifaService.AREA_UMBRAL) {
            double areaExcedente = area - TarifaService.AREA_UMBRAL;
            costoPorArea = areaExcedente * TarifaService.COSTO_POR_M2_EXCEDENTE;
        }

        return Math.max(costoPorVolumen, costoPorArea); //Retornamos el mayor de los excedentes
    }
}
