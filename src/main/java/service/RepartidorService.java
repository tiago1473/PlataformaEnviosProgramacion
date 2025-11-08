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
        return true;
    }

    /*Delete*/
    public boolean eliminarRepartidor(String id) {
        Repartidor repartidor = buscarRepartidorEntity(id);
        if (repartidor == null) {
            return false;
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
        repartidor.getEnvios().remove(envio);
        return true;

    }

    public boolean registrarCambioRepartidor(String idEnvio,String idRepartidor) {
        Repartidor repartidor = buscarRepartidorEntity(idRepartidor);
        Envio envio = envioService.buscarEnvioEntity(idEnvio);
        if (repartidor == null|| envio==null) {
            return false;
        }
        repartidor.addEnvios(envio);
        return true;
    }

}
