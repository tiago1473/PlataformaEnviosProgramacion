package models;


public class ZonaCobertura {
    private Coordenada coordenada;
    private static final int RADIO=3;

    public ZonaCobertura(){
        this.coordenada=new Coordenada();
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public int getRadio() {
        return RADIO;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }
}
