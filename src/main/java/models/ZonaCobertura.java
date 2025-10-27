package models;


public class ZonaCobertura {
    private Coordenada coordenada;
    private final int radio=3;

    public ZonaCobertura(){
        this.coordenada=new Coordenada();
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public int getRadio() {
        return radio;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }
}
