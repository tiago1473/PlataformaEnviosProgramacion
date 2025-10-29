package models;

public class Direccion {
    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private Coordenada coordenada;

    public Direccion(String alias, String calle, String ciudad) {
        this.idDireccion = crearId(ciudad, calle);
        this.alias = alias;
        this.calle = calle;
        this.ciudad = ciudad;
        this.coordenada = new Coordenada();
    }

    public String crearId(String ciudad, String calle) {
        String ciudadCode = ciudad.length() >= 3 ? ciudad.substring(0, 3) : ciudad;
        String calleCode = calle.length() >= 3 ? calle.substring(0, 3) : calle;
        return "DIR-" + ciudadCode.toUpperCase() + "-" + calleCode.toUpperCase();
    }

    public String getIdDireccion() {
        return idDireccion;
    }

    public String getAlias() {
        return alias;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }
}
