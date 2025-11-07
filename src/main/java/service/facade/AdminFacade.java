package service.facade;

import models.DTO.DireccionDTO;
import models.DTO.EnvioRepartidorDTO;
import models.DTO.RepartidorDTO;
import models.DTO.UsuarioDTO;
import models.Repartidor;
import models.Usuario;
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

    public List <EnvioRepartidorDTO> obtenerTodosLosEnviosRepartidor(String idRepartidor) {
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

    /*Usuarios*/

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuario();
    }

    public Usuario buscarUsuarioEntity(String id) {
        return usuarioService.buscarUsuarioEntidad(id);
    }

    public List<DireccionDTO> obtenerTodasLasDireccionesUsuario(String id) {
        return usuarioService.obtenerDireccionesUsuario(id);
    }

    public boolean agregarUsuario(UsuarioDTO usuarioDTO) {
        return usuarioService.agregarUsuario(usuarioDTO);
    }

    public boolean actualizarUsuario(UsuarioDTO usuarioSeleccionado) {
        return usuarioService.actualizarUsuario(usuarioSeleccionado);
    }

    public boolean eliminarUsuario(String id) {
        return usuarioService.eliminarUsuario(id);
    }

    public boolean agregarDireccion(String idUsuario,DireccionDTO direccionDTO) {
        return usuarioService.agregarDireccionUsuario(idUsuario, direccionDTO);
    }

    public boolean actualizarDireccion(String idUsuario, DireccionDTO direccionDTO) {
        return usuarioService.actualizarDireccionUsuario(idUsuario,direccionDTO);
    }

    public boolean eliminarDireccion(String idUsuario, DireccionDTO direccionDTO) {
        return usuarioService.eliminarDireccionUsuario(idUsuario,direccionDTO);
    }
}
