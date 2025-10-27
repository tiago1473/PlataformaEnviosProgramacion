package models;
import java.util.Random;

public class Coordenada {
    private double latitud;
    private double longitud;
    private static final double LAT_MIN=4.4800;
    private static final double LAT_MAX=4.5800;
    private static final double LON_MIN=-75.7400;
    private static final double LON_MAX=-75.6200;
    private static final Random rand= new Random();

    public Coordenada() {
        this.latitud=LAT_MIN + (LAT_MAX - LAT_MIN) * rand.nextDouble();
        this.longitud=LON_MIN + (LON_MAX - LON_MIN) * rand.nextDouble();
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
