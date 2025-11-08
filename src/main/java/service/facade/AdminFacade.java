package service.facade;

import models.DTO.*;
import models.EstadoEnvio;
import models.Repartidor;
import models.Usuario;
import service.EnvioService;
import service.RepartidorService;
import service.UsuarioService;

import java.util.List;

public class AdminFacade {

    private final UsuarioService usuarioService;
    private final RepartidorService repartidorService;
    private final EnvioService envioService;

    public AdminFacade() {
        this.usuarioService = new UsuarioService();
        this.repartidorService = new RepartidorService();
        this.envioService = new EnvioService();
    }

    /*Repartidores*/
    public boolean agregarRepartidor(RepartidorDTO repartidorDTO) {
        return repartidorService.agregarRepartidor(repartidorDTO);
    }

    public List<RepartidorDTO> obtenerTodosLosRepartidores() {
        return repartidorService.obtenerTodosLosRepartidores();
    }

    public List <EnvioDTO> obtenerTodosLosEnviosRepartidor(String idRepartidor) {
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

    /*PantallaPrincipaldeEnvios*/

    public List<EnvioDTO> obtenerTodosLosEnvios() {
        return envioService.obtenerTodosLosEnvios();
    }

    public boolean registrarIncidencia(String idEnvio,String incidencia) {
        return envioService.registrarIncidencia(idEnvio,incidencia);
    }

    public boolean registrarCambioEstado(String idEnvio, EstadoEnvio estadoEnvio) {
        return envioService.registrarCambioEstado(idEnvio,estadoEnvio);
    }

    public boolean registrarCambioRepartidor(String idEnvio, String idRepartidor) {
        return repartidorService.registrarCambioRepartidor(idEnvio,idRepartidor);
    }
}
