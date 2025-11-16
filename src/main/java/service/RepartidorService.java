package service;

import models.DTO.EnvioDTO;
import models.DTO.RepartidorDTO;
import models.Envio;
import models.PlataformaEnvios;
import models.Repartidor;
import utils.mappers.EnvioMapper;
import utils.mappers.RepartidorMapper;

import java.util.ArrayList;
import java.util.List;

public class RepartidorService {

    private final PlataformaEnvios plataformaEnvios;
    private final EnvioService envioService;

    public RepartidorService() {
        this.plataformaEnvios = PlataformaEnvios.getInstancia();
        this.envioService = new EnvioService();
    }

    /*Create*/
    public boolean agregarRepartidor(RepartidorDTO repartidorDTO) {
        if (buscarRepartidorEntity(repartidorDTO.getId()) != null) {
            return false;
        }
        Repartidor repartidor = RepartidorMapper.toEntity(repartidorDTO);
        plataformaEnvios.addRepartidor(repartidor);
        return true;
    }

    /*Read*/
    public List<RepartidorDTO> obtenerTodosLosRepartidores() {
        List<RepartidorDTO> repartidoresDTO = new ArrayList<>();
        for (Repartidor repartidor : plataformaEnvios.getRepartidores()) {
            repartidoresDTO.add(RepartidorMapper.toDTO(repartidor));
        }
        return repartidoresDTO;
    }

    public List<EnvioDTO> obtenerTodosLosEnvioRepartidores(String idRepartidor) {
        Repartidor repartidor = buscarRepartidorEntity(idRepartidor);
        List<EnvioDTO> envioRepartidoresDTO = new ArrayList<>();
        for (Envio envio : repartidor.getEnvios()) {
            envioRepartidoresDTO.add(EnvioMapper.toDTOPantallaRepartidor(envio));
        }
        return envioRepartidoresDTO;
    }

    /*Update*/
    public boolean actualizarRepartidor(RepartidorDTO repartidorDTO) {
        Repartidor repartidor = buscarRepartidorEntity(repartidorDTO.getId());
        if (repartidor == null) {
            return false;
        }
        RepartidorMapper.updateEntityFromDTO(repartidor, repartidorDTO);
        for (Envio envio : repartidor.getEnvios()){
            envio.setNombreRepartidor(repartidor.getNombre());
        }
        return true;
    }

    /*Delete*/
    public boolean eliminarRepartidor(String id) {
        Repartidor repartidor = buscarRepartidorEntity(id);
        if (repartidor == null) {
            return false;
        }
        if (plataformaEnvios.getRepartidores().size() <= 1) {
            throw new IllegalStateException("No se puede eliminar al repartidor: no hay otro disponible para reasignar los envíos.");
        }
        Repartidor nuevoRepartidor = null;
        for (Repartidor r : plataformaEnvios.getRepartidores()) {
            if (!r.getId().equals(repartidor.getId())) {
                nuevoRepartidor = r;
                break;
            }
        }
        for (Envio envio : repartidor.getEnvios()){
            envio.setNombreRepartidor(nuevoRepartidor.getNombre());
            nuevoRepartidor.addEnvios(envio);
        }
        plataformaEnvios.getRepartidores().remove(repartidor);
        return true;
    }

    public Repartidor buscarRepartidorEntity(String id) {
        for (Repartidor repartidor : plataformaEnvios.getRepartidores()) {
            if (repartidor.getId().equals(id)) {
                return repartidor;
            }
        }
        return null;
    }

    public boolean eliminarEnvioRepartidor(String idRepartidor,String idEnvio) {
        Repartidor repartidor = buscarRepartidorEntity(idRepartidor);
        Envio envio = envioService.buscarEnvioEntity(idEnvio);
        if (repartidor == null|| envio==null) {
            return false;
        }
        if (plataformaEnvios.getRepartidores().size() <= 1) {
            throw new IllegalStateException("No se puede eliminar el envio: no hay otro repartidor para asignarlo.");
        }
        Repartidor nuevoRepartidor = null;
        for (Repartidor r : plataformaEnvios.getRepartidores()) {
            if (!r.getId().equals(repartidor.getId())) {
                nuevoRepartidor = r;
                break;
            }
        }
        envio.setNombreRepartidor(nuevoRepartidor.getNombre());
        nuevoRepartidor.addEnvios(envio);
        repartidor.getEnvios().remove(envio);
        return true;
    }

    public boolean registrarCambioRepartidor(String idEnvio,String idRepartidor) {
        Repartidor repartidor = buscarRepartidorEntity(idRepartidor);
        Envio envio = envioService.buscarEnvioEntity(idEnvio);
        if (repartidor == null|| envio==null) {
            return false;
        }
        if (envio.getNombreRepartidor()!=null) {
            repartidor.addEnvios(envio);
            envio.setNombreRepartidor(repartidor.getNombre());
        }else {
            repartidor.addEnvios(envio);
            envio.setNombreRepartidor(repartidor.getNombre());
            envio.asignar();
        }

        return true;
    }

}
