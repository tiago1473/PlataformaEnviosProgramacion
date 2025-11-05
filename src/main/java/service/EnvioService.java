package service;

import models.*;
import models.DTO.EnvioDTO;

import java.time.LocalDateTime;

public class EnvioService {

    private final PlataformaEnvios plataformaEnvios;
    private final UsuarioService usuarioService;

    public EnvioService(){
        this.plataformaEnvios = PlataformaEnvios.getInstancia();
        this.usuarioService = new UsuarioService();
    }

    public Envio crearEnvio(EnvioDTO envioDTO) {

        EnvioBuilder envioBuilder = new EnvioBuilder(
                envioDTO.getOrigen(),
                envioDTO.getDestino(),
                envioDTO.getPeso(),
                envioDTO.getLargo(),
                envioDTO.getAncho(),
                envioDTO.getAlto(),
                envioDTO.getEstado());

        if (envioDTO.isFirma()) {
            envioBuilder.withFirma();
        }
        if (envioDTO.isFragil()) {
            envioBuilder.withFragil();
        }
        if (envioDTO.isPrioridad()) {
            envioBuilder.withPrioridad();
        }
        if (envioDTO.isSeguro()) {
            envioBuilder.withSeguro();
        }
        Envio envio = envioBuilder.build();
        envio.setCosto(calcularCostoEnvio(envio));
        return envio;
    }

    public double calcularCostoEnvio(Envio envio) {
        double distancia = calcularDistancia(envio.getOrigen(), envio.getDestino());
        double costo = distancia * TarifaService.TARIFA_POR_KM;
        double adicion = 0;
        if(envio.isFirma()){
            adicion += costo*(1+TarifaService.EXTRA_FIRMA);
        }
        if(envio.isFragil()){
            adicion += costo*(1+TarifaService.EXTRA_FRAGIL);
        }
        if(envio.isPrioridad())
            adicion += costo*(1+TarifaService.EXTRA_PRIORIDAD);
        if(envio.isSeguro()){
            adicion += costo*(1+TarifaService.EXTRA_SEGURO);
        }
        return (costo + adicion);
    }

    public static double calcularDistancia(Direccion origen, Direccion destino) {
        double radioTierra = 6371; // km
        double dLat = Math.toRadians(destino.getCoordenada().getLatitud() - origen.getCoordenada().getLatitud());
        double dLon = Math.toRadians(destino.getCoordenada().getLongitud() - origen.getCoordenada().getLongitud());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(origen.getCoordenada().getLatitud()))
                * Math.cos(Math.toRadians(destino.getCoordenada().getLatitud()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radioTierra * c;
    }

    public Envio buscarEnvioEntity(String idEnvio) {
        for (Envio envio : plataformaEnvios.getEnvios()) {
            if (envio.getId().equals(idEnvio)) {
                return envio;
            }
        }
        return null;
    }

    public Envio buscarEnvioUsuarioEntity(String usuarioId, String envioId) {
        Usuario usuario = usuarioService.buscarUsuarioEntidad(usuarioId);
        if (usuario != null && usuario.getEnvios() != null) {
            for (Envio envio : usuario.getEnvios()) {
                if (envio != null && envio.getId().equals(envioId)) {
                    return envio;
                }
            }
        }
        return null;
    }

    public boolean agregarEnvioUsuario(String usuarioId, EnvioDTO envioDTO) {
        Usuario usuario = usuarioService.buscarUsuarioEntidad(usuarioId);
        if (usuario == null) {
            return false;
        }
        Envio envio = crearEnvio(envioDTO);
        envio.setFechaCreacion(LocalDateTime.now());
        usuario.getEnvios().add(envio);
        plataformaEnvios.addEnvio(envio);
        return true;
    }

    public boolean modificarEnvioUsuario(String usuarioId, String envioId, EnvioDTO envioDTO) {
        Envio envio = buscarEnvioUsuarioEntity(usuarioId, envioId);
        if (envio == null || envio.getEstado() != EstadoEnvio.SOLICITADO) { //Solo lo puedo modificar si es SOLICITADO
            return false;
        }
        envio.setOrigen(envioDTO.getOrigen());
        envio.setDestino(envioDTO.getDestino());
        envio.setPeso(envioDTO.getPeso());
        envio.setLargo(envioDTO.getLargo());
        envio.setAncho(envioDTO.getAncho());
        envio.setAlto(envioDTO.getAlto());
        envio.setSeguro(envioDTO.isSeguro());
        envio.setFragil(envioDTO.isFragil());
        envio.setFirma(envioDTO.isFirma());
        envio.setPrioridad(envioDTO.isPrioridad());
        envio.setCosto(calcularCostoEnvio(envio));
        return true;
    }

    public boolean cancelarEnvioUsuario(String usuarioId, String envioId) {
        Envio envio = buscarEnvioUsuarioEntity(usuarioId, envioId);
        if (envio == null || envio.getEstado() != EstadoEnvio.SOLICITADO) {
            return false;
        }
        Usuario usuario = usuarioService.buscarUsuarioEntidad(usuarioId);
        if (usuario != null) {
            usuario.getEnvios().remove(envio);
            plataformaEnvios.getEnvios().remove(envio);
            return true;
        }
        return false;
    }

    public double cotizarEnvio(EnvioDTO envioDTO) {
        if (envioDTO.getOrigen() == null || envioDTO.getDestino() == null) {
            return 0.0; //Se debe de seleccionar un origen y destino
        }
        Envio envio = crearEnvio(envioDTO);
        return envio.getCosto();
    }
}
