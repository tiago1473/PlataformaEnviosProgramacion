package service;

import models.DTO.EnvioRepartidorDTO;
import models.DTO.RepartidorDTO;
import models.Envio;
import models.PlataformaEnvios;
import models.Repartidor;
import utils.mappers.EnvioRepartidorMapper;
import utils.mappers.RepartidorMapper;

import java.util.ArrayList;
import java.util.List;

public class RepartidorService {

    private final PlataformaEnvios plataformaEnvios;

    public RepartidorService() {
        this.plataformaEnvios = PlataformaEnvios.getInstancia();
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

    public List<EnvioRepartidorDTO> obtenerTodosLosEnvioRepartidores(String idRepartidor) {
        Repartidor repartidor = buscarRepartidorEntity(idRepartidor);
        List<EnvioRepartidorDTO> envioRepartidoresDTO = new ArrayList<>();
        for (Envio envio : repartidor.getEnvios()) {
            envioRepartidoresDTO.add(EnvioRepartidorMapper.toDTO(envio));
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

    public boolean existeRepartidor(String id) {
        return buscarRepartidorEntity(id) != null;
    }

    public Repartidor buscarRepartidorEntity(String id) {
        for (Repartidor repartidor : plataformaEnvios.getRepartidores()) {
            if (repartidor.getId().equals(id)) {
                return repartidor;
            }
        }
        return null;
    }

    public RepartidorDTO buscarRepartidorPorCedula(String id) {
        Repartidor repartidor = buscarRepartidorEntity(id);
        return repartidor != null ? RepartidorMapper.toDTO(repartidor) : null;
    }
}
