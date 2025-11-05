package service.facade;

import models.DTO.EnvioRepartidorDTO;
import models.DTO.RepartidorDTO;
import models.Repartidor;
import models.Usuario;
import service.EnvioService;
import service.RepartidorService;
import service.UsuarioService;

import java.util.List;

public class AdminFacade {

    private final UsuarioService usuarioService;
    private final RepartidorService repartidorService;

    public AdminFacade() {
        this.usuarioService = new UsuarioService();
        this.repartidorService = new RepartidorService();
    }

    /*Repartidores*/
    public boolean agregarRepartidor(RepartidorDTO repartidorDTO) {
        return repartidorService.agregarRepartidor(repartidorDTO);
    }

    public List<RepartidorDTO> obtenerTodosLosRepartidores() {
        return repartidorService.obtenerTodosLosRepartidores();
    }

    public List <EnvioRepartidorDTO> obtenerTodosLosEnvioRepartidores(String idRepartidor) {
        return repartidorService.obtenerTodosLosEnvioRepartidores(idRepartidor);
    }

    public boolean actualizarRepartidor(RepartidorDTO repartidorDTO) {
        return repartidorService.actualizarRepartidor(repartidorDTO);
    }

    public boolean eliminarRepartidor(String id) {
        return repartidorService.eliminarRepartidor(id);
    }

    public Repartidor buscarRepartidorEntity(String id){
        return repartidorService.buscarRepartidorEntity(id);
    }

    public boolean eliminarEnvioRepartidor(String idRepartidor, String idEnvio) {
        return repartidorService.eliminarEnvioRepartidor(idRepartidor, idEnvio);
    }
}
