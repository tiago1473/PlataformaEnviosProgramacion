package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.DTO.DireccionDTO;
import models.DTO.UsuarioDTO;
import models.Direccion;
import models.SessionManager;
import models.Usuario;
import service.facade.UsuarioFacade;
import utils.PathsFxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionUsuarioController implements Initializable {

    @FXML
    private TableView<DireccionDTO> tableDirecciones;
    @FXML
    private TableColumn<DireccionDTO,String> colAlias;
    @FXML
    private TableColumn<DireccionDTO,String> colCalle;
    @FXML
    private TableColumn<DireccionDTO,String> colCiudad;
    @FXML
    private TableColumn<DireccionDTO,String> colId;
    @FXML
    private TableColumn<DireccionDTO, String> colLatitud;
    @FXML
    private TableColumn<DireccionDTO, String> colLongitud;
    @FXML
    private TextField txtAliasDireccion;
    @FXML
    private TextField txtCalleDireccion;
    @FXML
    private TextField txtCiudadDireccion;
    @FXML
    private TextField txtIdDireccion;
    @FXML
    private TextField txtEmailUsuario;
    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtTelefonoUsuario;
    @FXML
    private Label lblMensaje;

    private UsuarioFacade usuarioFacade;
    private ObservableList<DireccionDTO> direcciones;
    private DireccionDTO direccionSeleccionada; //Atrapa lo que seleccioné en la tabla
    private SessionManager sessionManager;
    private UsuarioDTO usuarioLogueado;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioFacade = new UsuarioFacade();
        sessionManager = SessionManager.getInstancia();
        usuarioLogueado = sessionManager.getUsuarioLogueado();
        usuario = usuarioFacade.buscarUsuarioEntidad(usuarioLogueado.getId());
        direcciones = FXCollections.observableArrayList();
        cargarInformacionUsuario();
        txtIdUsuario.setDisable(true);
        txtIdDireccion.setDisable(true);
        configurarTabla();
        cargarDirecciones();
        configurarSeleccionTabla();
        System.out.println("UsuarioController inicializado correctamente");
    }

    private void configurarTabla() {
        colId.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        colAlias.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAlias()));
        colCalle.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCalle()));
        colCiudad.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCiudad()));
        colLatitud.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getCoordenada().getLatitud())));
        colLongitud.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getCoordenada().getLongitud())));
        tableDirecciones.setItems(direcciones);
    }

    private void configurarSeleccionTabla() {
        tableDirecciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                direccionSeleccionada = newSelection;
                cargarDatosEnFormularioDireccion(newSelection);
            }
        });
    }

    private void cargarDatosEnFormularioDireccion(DireccionDTO direccionDTO) {
        txtIdDireccion.setText(direccionDTO.getId());
        txtAliasDireccion.setText(direccionDTO.getAlias());
        txtCalleDireccion.setText(direccionDTO.getCalle());
        txtCiudadDireccion.setText(direccionDTO.getCiudad());
    }

    private void cargarInformacionUsuario() {
        txtEmailUsuario.setText(usuarioLogueado.getCorreo());
        txtIdUsuario.setText(usuarioLogueado.getId());
        txtNombreUsuario.setText(usuarioLogueado.getNombre());
        txtTelefonoUsuario.setText(usuarioLogueado.getTelefono());
        mostrarMensaje("Datos cargados exitosamente", false);
    }

    private void cargarDirecciones() {
        direcciones.clear();
        direcciones.addAll(usuarioFacade.obtenerDireccionesUsuario(usuario.getId()));
        mostrarMensaje("Direcciones cargadas: " + direcciones.size(), false);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("Gestión usuario controller: " + mensaje);
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
            confirmacion.setHeaderText("¿Está seguro de eliminar la dirección?");
            confirmacion.setContentText("Dirección: " + direccionSeleccionada.getId() +
                    "\nAlias: " + direccionSeleccionada.getAlias() + "\nCalle: " + direccionSeleccionada.getCalle() +
                    "\nCiudad: " + direccionSeleccionada.getCiudad());

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (usuarioFacade.eliminarDireccionUsuario(usuario.getId(), direccionSeleccionada)) {
                    obtenerUsuarioActualizado();
                    cargarDirecciones();
                    limpiarFormularioDirecciones(null);
                    mostrarMensaje("Dirección eliminada exitosamente", false);
                    System.out.println("Dirección eliminada a: " + usuario.getId());
                } else {
                mostrarMensaje("Error: No se pudo eliminar la dirección", true);
                }
            }
        } catch (Exception e) {
            mostrarMensaje("Error al eliminar dirección: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void limpiarFormularioDirecciones(ActionEvent event) {
        txtAliasDireccion.clear();
        txtCalleDireccion.clear();
        txtCiudadDireccion.clear();
        txtIdDireccion.setDisable(true); //Cuando está limpio no lo escribo porqque se crea por defecto
        tableDirecciones.getSelectionModel().clearSelection();
        mostrarMensaje("Formulario limpio", false);
    }

    @FXML
    void actualizarDireccion(ActionEvent event) {
        try {
            if (direccionSeleccionada == null) {
                mostrarMensaje("Seleccione una dirección de la tabla para actualizar", true);
                return;
            }
            if (!validarCampos()) {
                return;
            }
            direccionSeleccionada.setAlias(txtAliasDireccion.getText().trim());
            direccionSeleccionada.setCalle(txtCalleDireccion.getText().trim());
            direccionSeleccionada.setCiudad(txtCiudadDireccion.getText().trim());
            if (usuarioFacade.actualizarDireccionUsuario(usuarioLogueado, direccionSeleccionada)) {
                tableDirecciones.refresh(); //es como decir actualizar
                cargarDirecciones();
                mostrarMensaje("Dirección actualizada exitosamente", false);
                System.out.println("Dirección actualizada");
                limpiarFormularioDirecciones(null);
            } else {
                mostrarMensaje("Error: No se pudo actualizar la dirección", true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar dirección: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        if (txtAliasDireccion.getText().trim().isEmpty()) {
            mostrarMensaje("El Alias de la dirección es obligatorio", true);
            return false;
        }
        if (txtCalleDireccion.getText().trim().isEmpty()) {
            mostrarMensaje("La Calle de la dirección es obligatoria", true);
            return false;
        }
        if (txtCiudadDireccion.getText().trim().isEmpty()) {
            mostrarMensaje("La Ciudad de la dirección es obligatoria", true);
            return false;
        }
        return true;
    }

    @FXML
    void agregarDireccion(ActionEvent event) {
        try {
            if (!validarCampos()) {
                return;
            }
            DireccionDTO direccionDTO = new DireccionDTO(
                    txtAliasDireccion.getText().trim(),
                    txtCalleDireccion.getText().trim(),
                    txtCiudadDireccion.getText().trim());
            if (usuarioFacade.agregarDireccionUsuario(usuarioLogueado.getId(), direccionDTO)) {
                tableDirecciones.refresh(); //es como decir actualizar
                cargarDirecciones();
                mostrarMensaje("Dirección creada exitosamente", false);
                System.out.println("Dirección creada");
                limpiarFormularioDirecciones(null);
            } else {
                mostrarMensaje("Error: No se pudo crear la dirección", true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al crear dirección: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void limpiarTabla(ActionEvent event) {
        limpiarFormularioDirecciones(event);
    }

    @FXML
    void actualizarUsuario(ActionEvent event) {
        try{
            UsuarioDTO usuarioDTOActualizado = new UsuarioDTO();
            usuarioDTOActualizado.setId(usuarioLogueado.getId());
            usuarioDTOActualizado.setNombre(txtNombreUsuario.getText().trim());
            usuarioDTOActualizado.setTelefono(txtTelefonoUsuario.getText().trim());
            usuarioDTOActualizado.setCorreo(txtEmailUsuario.getText().trim());
            if(usuarioFacade.actualizarUsuario(usuarioDTOActualizado)){
                obtenerUsuarioActualizado();
                cargarInformacionUsuario();
                mostrarMensaje("Usuario actualizado exitosamente", false);
                System.out.println("Usuario actualizado");
            }else {
                mostrarMensaje("Error: No se pudo actualizar el usuario", true);
            }
        }catch (Exception e) {
            mostrarMensaje("Error al actualizar usuario: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private void obtenerUsuarioActualizado(){
        usuario = usuarioFacade.buscarUsuarioEntidad(usuarioLogueado.getId());
        usuarioLogueado = usuarioFacade.buscarUsuarioId(usuarioLogueado.getId());
        sessionManager.iniciarSesion(usuarioLogueado);
    }

    @FXML
    void regresarGestionUsuario(ActionEvent event) {
        try {
            // Cargo la Nueva Pantalla
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_PANTALLA_PRINCIPAL_USUARIO));
            Parent root = loader.load();

            // Para obtener el stage actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crear nueva escena
            Scene scene = new Scene(root);

            // Cambiar la escena
            stage.setScene(scene);
            stage.show();

            System.out.println("Navegación exitosa a: " + PathsFxml.PATH_PANTALLA_PRINCIPAL_USUARIO);
        } catch (Exception e) {
            System.err.println("Error al navegar a la pantalla: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al cargar la pantalla. Intente nuevamente.");
        }
    }

    private void mostrarError(String mensaje) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
            lblMensaje.setVisible(true);
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Login");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        System.out.println("Error de login: " + mensaje);
    }
}

