package service.costoAdicionalStrategy;
import models.Envio;
import service.EnvioService;

import java.util.ArrayList;
import java.util.List;

public class CalculadoraCostoEnvio {
    private List<CostoAdicionalStrategy> estrategias;

    public CalculadoraCostoEnvio() {
        this.estrategias = new ArrayList<>();
        inicializarEstrategias();
    }

    //Se inicializan las estrategias
    private void inicializarEstrategias() {
        estrategias.add(new CostoPesoStrategy());
        estrategias.add(new CostoDimensionesStrategy());
        estrategias.add(new CostoFirmaStrategy());
        estrategias.add(new CostoFragilStrategy());
        estrategias.add(new CostoSeguroStrategy());
        estrategias.add(new CostoPrioridadStrategy());
    }

    public double calcularCostoTotal(Envio envio) {
        if (envio.getOrigen() == null || envio.getDestino() == null) {
            return 0.0;
        }

        double distancia = EnvioService.calcularDistancia(envio.getOrigen(), envio.getDestino());
        double costoBase = distancia * service.TarifaService.TARIFA_POR_KM;

        //Aplicamos todas las estrategias de costo adicional
        double costoAdicionalTotal = 0.0;
        for (CostoAdicionalStrategy estrategia : this.estrategias) {
            costoAdicionalTotal += estrategia.calcularCostoAdicional(costoBase, envio);
        }
        return costoBase + costoAdicionalTotal;
    }

    public void agregarEstrategia(CostoAdicionalStrategy estrategia) {
        estrategias.add(estrategia);
    }
}
