package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.DTO.DireccionDTO;
import models.DTO.UsuarioDTO;
import service.facade.AdminFacade;
import utils.PathsFxml;
import utils.mappers.UsuarioMapper;

import java.util.Optional;

public class GestionUsuarioAdmController {

    @FXML    private TextField txtIdUsuario;
    @FXML    private TextField txtPassword;
    @FXML    private TextField txtNombreUsuario;
    @FXML    private TextField txtTelefonoUsuario;
    @FXML    private TextField txtEmailUsuario;

    @FXML    private TableView<UsuarioDTO> tblUsuarios;
    @FXML    private TableColumn<UsuarioDTO, String> colIdUsuario;
    @FXML    private TableColumn<UsuarioDTO, String> colNombreUsuario;
    @FXML    private TableColumn<UsuarioDTO, String> colTelefono;
    @FXML    private TableColumn<UsuarioDTO, String> colEmail;

    @FXML    private TextField txtIdDireccion;
    @FXML    private TextField txtAlias;
    @FXML    private TextField txtCalle;
    @FXML    private TextField txtCiudad;

    @FXML    private TableView<DireccionDTO> tblDirecciones;
    @FXML    private TableColumn<DireccionDTO, String> colIdDireccion;
    @FXML    private TableColumn<DireccionDTO, String> colAlias;
    @FXML    private TableColumn<DireccionDTO, String> colCalle;
    @FXML    private TableColumn<DireccionDTO, String> colCiudad;

    @FXML    private Label lblMensaje;

    private AdminFacade adminFacade;
    private ObservableList<UsuarioDTO> listaUsuarios;
    private ObservableList<DireccionDTO> listaDirecciones;
    private UsuarioDTO usuarioSeleccionado;
    private DireccionDTO direccionSeleccionada;

    @FXML
    void initialize() {
        adminFacade = new AdminFacade();
        listaUsuarios = FXCollections.observableArrayList();
        listaDirecciones = FXCollections.observableArrayList();
        configurarTablaUsuarios();
        cargarUsuarios();
        configurarTablaDirecciones();
        configurarSeleccionTablaUsuarios();
        configurarSeleccionTablaDirecciones();
    }

    private void configurarTablaUsuarios() {
        colIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tblUsuarios.setItems(listaUsuarios);
    }

    private void configurarTablaDirecciones() {
        colIdDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colAlias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlias()));
        colCalle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCalle()));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
        tblDirecciones.setItems(listaDirecciones);
    }

    private void cargarUsuarios() {
        listaUsuarios.clear();
        listaUsuarios.addAll(adminFacade.obtenerTodosLosUsuarios());
        mostrarMensaje("Usuarios cargados: " + listaUsuarios.size(), false);
    }

    private void cargarDirecciones() {
        listaDirecciones.clear();
        listaDirecciones.addAll(adminFacade.obtenerTodasLasDireccionesUsuario(txtIdUsuario.getText()));
        mostrarMensaje("Direcciones cargados: " + listaDirecciones.size(), false);
    }

    private void configurarSeleccionTablaUsuarios() {
        tblUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection,seleccion) -> {
            if (seleccion!= null) {
                usuarioSeleccionado=seleccion;
                UsuarioMapper.updateDTOFromEntity(adminFacade.buscarUsuarioEntity(seleccion.getId()), seleccion);
                cargarDatosEnFormulario(seleccion);
                txtIdDireccion.setDisable(true);
                configurarTablaDirecciones();
                listaDirecciones.clear();
                listaDirecciones.addAll(adminFacade.obtenerTodasLasDireccionesUsuario(seleccion.getId()));
            } else {
                limpiarCampos(null);
            }
        });
    }

    private void configurarSeleccionTablaDirecciones() {
        tblDirecciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection,seleccion) -> {
            if (seleccion!= null) {
                direccionSeleccionada=seleccion;
                cargarDireccionEnFormulario(seleccion);
            } else {
                limpiarCampos(null);
            }
        });
    }

    private void cargarDatosEnFormulario(UsuarioDTO usuario) {
        txtIdUsuario.setText(usuario.getId());
        txtIdUsuario.setDisable(true);
        txtPassword.setText(usuario.getPassword());
        txtPassword.setDisable(true);
        txtNombreUsuario.setText(usuario.getNombre());
        txtTelefonoUsuario.setText(usuario.getTelefono());
        txtEmailUsuario.setText(usuario.getCorreo());
    }

    private void cargarDireccionEnFormulario(DireccionDTO direccion) {
        txtIdDireccion.setText(direccion.getId());
        txtIdDireccion.setDisable(true);
        txtAlias.setText(direccion.getAlias());
        txtCalle.setText(direccion.getCalle());
        txtCiudad.setText(direccion.getCiudad());
    }

    @FXML
    void crearUsuario(ActionEvent event) {
        try {
            if (!validarCamposUsuario()) {
                return;
            }
            UsuarioDTO nuevoUsuario = new UsuarioDTO(
                    txtPassword.getText().trim(),
                    txtIdUsuario.getText().trim(),
                    txtNombreUsuario.getText().trim(),
                    txtEmailUsuario.getText().trim(),
                    txtTelefonoUsuario.getText().trim()
            );
            if (adminFacade.agregarUsuario(nuevoUsuario)) {
                cargarUsuarios();
                limpiarCampos(null);
                mostrarMensaje("Usuario agregado exitosamente", false);
            } else {
                mostrarMensaje("Error: Ya existe un Usuario con la cédula " + nuevoUsuario.getId(), true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al agregar Usuario: " + e.getMessage(), true);
        }
    }

    @FXML
    void actualizarUsuario(ActionEvent event) {
        try {
            if (usuarioSeleccionado == null) {
                mostrarMensaje("Seleccione un Usuario de la tabla para actualizar", true);
                return;
            }
            if (!validarCamposUsuario()) {
                return;
            }
            usuarioSeleccionado.setNombre(txtNombreUsuario.getText().trim());
            usuarioSeleccionado.setTelefono(txtTelefonoUsuario.getText().trim());
            usuarioSeleccionado.setCorreo(txtEmailUsuario.getText().trim());

            if (adminFacade.actualizarUsuario(usuarioSeleccionado)) {
                tblUsuarios.refresh();
                mostrarMensaje("Usuario actualizado exitosamente", false);
                limpiarCampos(null);
            } else {
                mostrarMensaje("Error: No se pudo actualizar el Usuario", true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar el Usuario: " + e.getMessage(), true);
        }
    }

    @FXML
    void EliminarUsuario(ActionEvent event) {
        try {
            if (usuarioSeleccionado == null) {
                mostrarMensaje("Seleccione un Usuario de la tabla para eliminar", true);
                return;
            }
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este Usuario?");
            confirmacion.setContentText("Usuario: " + usuarioSeleccionado.getNombre() +
                    "\nCédula: " + usuarioSeleccionado.getId());

            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (adminFacade.eliminarUsuario(usuarioSeleccionado.getId())) {
                    cargarUsuarios();
                    limpiarCampos(null);
                    mostrarMensaje("Usuario eliminado exitosamente", false);
                } else {
                    mostrarMensaje("Error: No se pudo eliminar el Usuario", true);
                }
            }
        } catch (Exception e) {
            mostrarMensaje("Error al eliminar usuario: " + e.getMessage(), true);
        }
    }

    @FXML
    void crearDireccion(ActionEvent event) {
        try {
            if (!validarCamposDireccion()) {
                return;
            }
            DireccionDTO nuevaDireccion = new DireccionDTO(
                    txtAlias.getText().trim(),
                    txtCalle.getText().trim(),
                    txtCiudad.getText().trim()
            );

            if (adminFacade.agregarDireccion(usuarioSeleccionado.getId(),nuevaDireccion)) {
                cargarDirecciones();
                mostrarMensaje("Dirección agregada exitosamente", false);
            } else {
                mostrarMensaje("Error al agregar dirección", true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al agregar dirección" + e.getMessage(), true);
        }
    }

    @FXML
    void actualizarDireccion(ActionEvent event) {
        try {
            if (direccionSeleccionada == null) {
                mostrarMensaje("Seleccione una Dirección de la tabla para actualizar", true);
                return;
            }
            if (!validarCamposDireccion()) {
                return;
            }
            direccionSeleccionada.setAlias(txtAlias.getText().trim());
            direccionSeleccionada.setCalle(txtCalle.getText().trim());
            direccionSeleccionada.setCiudad(txtCiudad.getText().trim());

            if (adminFacade.actualizarDireccion(usuarioSeleccionado.getId(),direccionSeleccionada)) {
                tblDirecciones.refresh();
                mostrarMensaje("Direccion actualizado exitosamente", false);
            } else {
                mostrarMensaje("Error: No se pudo actualizar la Dirección", true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar la Dirección: " + e.getMessage(), true);
        }

    }

    @FXML
    void EliminarDireccion(ActionEvent event) {
        try {
            if (direccionSeleccionada == null) {
                mostrarMensaje("Seleccione una Dirección de la tabla para eliminar", true);
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar esta Dirección?");
            confirmacion.setContentText("Dirección: " + direccionSeleccionada.getId());

            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                boolean eliminado=adminFacade.eliminarDireccion(usuarioSeleccionado.getId(),direccionSeleccionada);
                if (eliminado) {
                    tblDirecciones.getSelectionModel().clearSelection();
                    tblDirecciones.getItems().remove(direccionSeleccionada);
                    direccionSeleccionada=null;
                    cargarDirecciones();
                    mostrarMensaje("Dirección eliminada del usuario exitosamente", false);
                } else {
                    mostrarMensaje("Error: No se pudo eliminar la dirección del usuario", true);
                }
            }

        } catch (Exception e) {
            mostrarMensaje("Error al eliminar la dirección del usuario: " + e.getMessage(), true);
        }
    }

    @FXML
    void regresarVista(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_PANTALLA_PRINCIPAL_ADMINISTRADOR));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarMensaje("Error al abrir Gestión de Repartidores: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private boolean validarCamposUsuario() {
        if (txtIdUsuario.getText().trim().isEmpty()) {
            mostrarMensaje("La cédula es obligatoria", true);
            return false;
        }
        if (txtNombreUsuario.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre es obligatorio", true);
            return false;
        }
        if (txtPassword.getText().trim().isEmpty()) {
            mostrarMensaje("La contraseña es obligatoria", true);
            return false;
        }
        if (!txtEmailUsuario.getText().contains("@") || !txtEmailUsuario.getText().contains(".")) {
            mostrarMensaje("El formato del correo no es válido", true);
            return false;
        }
        if (txtTelefonoUsuario.getText().trim().isEmpty()) {
            mostrarMensaje("El telefono es obligatorio", true);
            return false;
        }
        return true;
    }

    private boolean validarCamposDireccion() {
        if (txtAlias.getText().trim().isEmpty()) {
            mostrarMensaje("El Alias es obligatorio", true);
            return false;
        }
        if (txtCalle.getText().trim().isEmpty()) {
            mostrarMensaje("La calle es obligatoria", true);
            return false;
        }
        if (txtCiudad.getText().trim().isEmpty()) {
            mostrarMensaje("La ciudad es obligatoria", true);
            return false;
        }
        return true;
    }

    @FXML
    void limpiarCampos(ActionEvent event) {
        txtIdUsuario.clear();
        txtIdUsuario.setDisable(false);
        txtPassword.clear();
        txtPassword.setDisable(false);
        txtNombreUsuario.clear();
        txtTelefonoUsuario.clear();
        txtEmailUsuario.clear();

        txtIdDireccion.clear();
        txtAlias.clear();
        txtCalle.clear();
        txtCiudad.clear();

        usuarioSeleccionado=null;
        direccionSeleccionada=null;
        tblUsuarios.getSelectionModel().clearSelection();
        tblDirecciones.getSelectionModel().clearSelection();

        mostrarMensaje("Formulario limpio - Listo para nuevo Usuario", false);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        lblMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
}