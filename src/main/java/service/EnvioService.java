package service;
import javafx.scene.control.Alert;
import models.*;
import models.DTO.EnvioDTO;
import service.costoAdicionalStrategy.CalculadoraCostoEnvio;
import service.estadoState.EstadoEnvioState;
import utils.mappers.EnvioMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnvioService {
    private final PlataformaEnvios plataformaEnvios;
    private final UsuarioService usuarioService;
    private final CalculadoraCostoEnvio calculadoraCostoEnvio;

    public EnvioService(){
        this.plataformaEnvios = PlataformaEnvios.getInstancia();
        this.usuarioService = new UsuarioService();
        this.calculadoraCostoEnvio = new CalculadoraCostoEnvio();
    }

    public Envio crearEnvio(EnvioDTO envioDTO) {

        EnvioBuilder envioBuilder = new EnvioBuilder(
                envioDTO.getOrigen(),
                envioDTO.getDestino(),
                envioDTO.getPeso(),
                envioDTO.getLargo(),
                envioDTO.getAncho(),
                envioDTO.getAlto());

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
        if (envio == null || envio.getOrigen() == null || envio.getDestino() == null) {
            return 0.0;
        }
        return calculadoraCostoEnvio.calcularCostoTotal(envio);
    }

    public static double calcularDistancia(Direccion origen, Direccion destino) {
        if (origen == null || destino == null ||
                origen.getCoordenada() == null || destino.getCoordenada() == null) {
            return 0.0;
        }
        double radioTierra = 6371; //En km
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
        envio.setNombreUsuario(usuario.getNombre());
        plataformaEnvios.addEnvio(envio);
        return true;
    }

    public boolean modificarEnvioUsuario(String usuarioId, String envioId, EnvioDTO envioDTO) {
        Envio envio = buscarEnvioUsuarioEntity(usuarioId, envioId);
        if (envio == null) { //Solo lo puedo modificar si es SOLICITADO
            return false;
        }

        if (envio.getEstado().equals("SOLICITADO")){
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
        return false;
    }

    public boolean cancelarEnvioUsuario(String usuarioId, String envioId) {
        Envio envio = buscarEnvioUsuarioEntity(usuarioId, envioId);
        if (envio == null) {
            return false;
        }
        Usuario usuario = usuarioService.buscarUsuarioEntidad(usuarioId);
        if (usuario != null && envio.getEstado().equals("SOLICITADO")) {
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

    public List<EnvioDTO> obtenerTodosLosEnvios() {
        List<EnvioDTO> enviosDTO = new ArrayList<>();
        for (Envio envio : plataformaEnvios.getEnvios()) {
            enviosDTO.add(EnvioMapper.toDTOPantallaAdministrador(envio));
        }
        return enviosDTO;
    }

    public boolean registrarIncidencia(String idEnvio, String incidencia) {
        Envio envio=buscarEnvioEntity(idEnvio);
        if (envio == null) {
            return false;
        }
        envio.addIncidencia(new Incidencia(incidencia));
        return true;
    }

    public boolean registrarCambioEstado(String idEnvio, String estadoEnvio) {
        Envio envio = buscarEnvioEntity(idEnvio);
        if (envio == null) {
              throw new IllegalArgumentException("No existe el envío con id: " + idEnvio);
        }

        switch (estadoEnvio) {
            case "ASIGNADO":
                envio.asignar();
                return true;
            case "EN_RUTA":
                envio.enRuta();
                return true;
            case "ENTREGADO":
                envio.entregar();
                return true;
            default:
                throw new IllegalArgumentException("Estado inválido: " + estadoEnvio);
        }
    }

    public boolean procesarPago(String idEnvio, double monto, String tipoMetodoPago) {
        Envio envio = buscarEnvioEntity(idEnvio);
        if (envio == null) {
            return false;
        }
        if (!envio.getEstado().equals("SOLICITADO")) {
            return false;
        }
        Pago pago = new Pago(monto,tipoMetodoPago,idEnvio);

        if (pago.isAprobado()) {
            plataformaEnvios.addPago(pago);
            envio.porAsignar();
            return true;
        }

        return false;
    }
}
