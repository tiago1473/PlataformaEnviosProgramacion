package service;

public class TarifaService {

    // Tarifa base
    public static final double TARIFA_POR_KM = 1000.0;

    // Extras por servicios adicionales
    public static final double EXTRA_SEGURO = 0.15;
    public static final double EXTRA_FRAGIL = 0.10;
    public static final double EXTRA_FIRMA = 0.05;
    public static final double EXTRA_PRIORIDAD = 0.20;

    // Cálculo por peso
    public static final double PESO_UMBRAL = 5;
    public static final double COSTO_POR_KG_EXCEDENTE = 2000;

    // Cálculo por dimensiones
    // Volumen M3
    public static final double VOLUMEN_UMBRAL = 0.1;
    public static final double COSTO_POR_M3_EXCEDENTE = 5000;
    // Área M2
    public static final double AREA_UMBRAL = 0.5;
    public static final double COSTO_POR_M2_EXCEDENTE = 3000.0;
}
