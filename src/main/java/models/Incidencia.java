package models;

import java.time.LocalDateTime;

public class Incidencia {
    private LocalDateTime fecha;
    private String descripcion;

    public Incidencia(String descripcion) {
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
