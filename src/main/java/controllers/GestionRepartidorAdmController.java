package controllers;

import javafx.beans.property.SimpleIntegerProperty;
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
import models.DTO.EnvioDTO;
import models.DTO.RepartidorDTO;
import models.EstadoRepartidor;
import service.facade.AdminFacade;
import utils.PathsFxml;
import utils.mappers.RepartidorMapper;

import java.util.Optional;

public class GestionRepartidorAdmController {
    @FXML    private TextField txtIdRepartidor;
    @FXML    private TextField txtNombreRepartidor;
    @FXML    private TextField txtTelefonoRepartidor;
    @FXML    private ComboBox<EstadoRepartidor> cmbEstado;
    @FXML    private Label lblLatitud;
    @FXML    private Label lblLongitud;
    @FXML    private Label lblRadio;

    @FXML    private TableView<EnvioDTO> tblEnvios;
    @FXML    private TableColumn<EnvioDTO, String> colEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colOrigen;
    @FXML    private TableColumn<EnvioDTO, String> colDestino;

    @FXML    private TableView<RepartidorDTO> tblRepartidores;
    @FXML    private TableColumn<RepartidorDTO, String> colIdentificacion;
    @FXML    private TableColumn<RepartidorDTO, String> colNombre;
    @FXML    private TableColumn<RepartidorDTO, String> colTelefono;
    @FXML    private TableColumn<RepartidorDTO, String> colEstado;
    @FXML    private TableColumn<RepartidorDTO, Number> colEnviosAsignados;

    @FXML    private Label lblMensaje;

    private AdminFacade adminFacade;
    private ObservableList<RepartidorDTO> listaRepartidores;
    private ObservableList<EnvioDTO> listaEnvios;
    private RepartidorDTO repartidorSeleccionado;
    private EnvioDTO envioSeleccionado;

    @FXML
    void initialize() {
        adminFacade = new AdminFacade();
        listaRepartidores = FXCollections.observableArrayList();
        listaEnvios = FXCollections.observableArrayList();
        configurarTablaRepartidores();
        cargarRepartidores();
        configurarTablaEnvios();
        configurarSeleccionTabla();
        configurarSeleccionTablaEnvios();
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoRepartidor.values()));
    }

    private void configurarTablaRepartidores() {
        colIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado().toString()));
        colEnviosAsignados.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEnviosAsignados()));
        tblRepartidores.setItems(listaRepartidores);
    }

    private void configurarTablaEnvios() {
        colEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrigen().getCiudad()));
        colDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestino().getCiudad()));
        tblEnvios.setItems(listaEnvios);
    }

    private void cargarRepartidores() {
        listaRepartidores.clear();
        listaRepartidores.addAll(adminFacade.obtenerTodosLosRepartidores());
        mostrarMensaje("Repartidores cargados: " + listaRepartidores.size(), false);
    }

    private void configurarSeleccionTabla() {
        tblRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection,seleccion) -> {
            if (seleccion!= null) {
                repartidorSeleccionado=seleccion;
                RepartidorMapper.updateDTOFromEntity(adminFacade.buscarRepartidorEntity(seleccion.getId()), seleccion);
                cargarDatosEnFormulario(seleccion);
                configurarTablaEnvios();
                listaEnvios.clear();
                listaEnvios.addAll(adminFacade.obtenerTodosLosEnviosRepartidor(seleccion.getId()));
            } else {
                limpiarCampos(null);
            }
        });
    }

    private void configurarSeleccionTablaEnvios() {
        tblEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection,seleccion) -> {
            if (seleccion!= null) {
                envioSeleccionado=seleccion;
            } else {
                limpiarCampos(null);
            }
        });
    }

    private void cargarDatosEnFormulario(RepartidorDTO repartidor) {
        txtIdRepartidor.setText(repartidor.getId());
        txtIdRepartidor.setDisable(true);
        txtNombreRepartidor.setText(repartidor.getNombre());
        txtTelefonoRepartidor.setText(repartidor.getTelefono());
        cmbEstado.setValue(repartidor.getEstado());
        lblLatitud.setText(String.valueOf(repartidor.getLatitud()));
        lblLongitud.setText(String.valueOf(repartidor.getLongitud()));
        lblRadio.setText(String.valueOf(repartidor.getRadio()));
    }

    @FXML
    void crearRepartidor(ActionEvent event) {
        try {
            if (!validarCampos()) {
                return;
            }
            RepartidorDTO nuevoRepartidor = new RepartidorDTO(
                    txtIdRepartidor.getText().trim(),
                    txtNombreRepartidor.getText().trim(),
                    txtTelefonoRepartidor.getText().trim(),
                    cmbEstado.getValue(),
                    0

            );

            if (adminFacade.agregarRepartidor(nuevoRepartidor)) {
                cargarRepartidores();
                limpiarCampos(null);
                mostrarMensaje("Repartidor agregado exitosamente", false);
            } else {
                mostrarMensaje("Error: Ya existe un Repartidor con la cédula " + nuevoRepartidor.getId(), true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al agregar Repartidor: " + e.getMessage(), true);
        }
    }

    @FXML
    void actualizarRepartidor(ActionEvent event) {
        try {
            if (repartidorSeleccionado == null) {
                mostrarMensaje("Seleccione un Repartidor de la tabla para actualizar", true);
                return;
            }

            if (!validarCampos()) {
                return;
            }

            repartidorSeleccionado.setNombre(txtNombreRepartidor.getText().trim());
            repartidorSeleccionado.setTelefono(txtTelefonoRepartidor.getText().trim());
            repartidorSeleccionado.setEstado(cmbEstado.getValue());

            if (adminFacade.actualizarRepartidor(repartidorSeleccionado)) {
                tblRepartidores.refresh();
                mostrarMensaje("Repartidor actualizado exitosamente", false);
                limpiarCampos(null);
            } else {
                mostrarMensaje("Error: No se pudo actualizar el Repartidor", true);
            }

        } catch (Exception e) {
            mostrarMensaje("Error al actualizar repartidor: " + e.getMessage(), true);
        }
    }

    @FXML
    void eliminarRepartidor(ActionEvent event) {
        try {
            if (repartidorSeleccionado == null) {
                mostrarMensaje("Seleccione un Repartidor de la tabla para eliminar", true);
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este Repartidor?");
            confirmacion.setContentText("Repartidor: " + repartidorSeleccionado.getNombre() +
                    "\nCédula: " + repartidorSeleccionado.getId());

            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (adminFacade.eliminarRepartidor(repartidorSeleccionado.getId())) {
                    cargarRepartidores();
                    limpiarCampos(null);
                    mostrarMensaje("Repartidor eliminado exitosamente", false);
                } else {
                    mostrarMensaje("Error: No se pudo eliminar el Repartidor", true);
                }
            }

        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage(), true);
        }
    }

    @FXML
    void eliminarEnvio(ActionEvent event) {
        try {
            if (envioSeleccionado == null) {
                mostrarMensaje("Seleccione un Envio de la tabla para eliminar", true);
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este Envio?");
            confirmacion.setContentText("Envio: " + envioSeleccionado.getId() +
                    "\nDestino: " + envioSeleccionado.getDestino());

            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                boolean eliminado=adminFacade.eliminarEnvioRepartidor(repartidorSeleccionado.getId(),envioSeleccionado.getId());
                if (eliminado) {
                    tblEnvios.getSelectionModel().clearSelection();
                    tblEnvios.getItems().remove(envioSeleccionado);
                    envioSeleccionado=null;
                    configurarTablaEnvios();
                    cargarRepartidores();
                    mostrarMensaje("Envio eliminado del repartidor exitosamente", false);
                } else {
                    mostrarMensaje("Error: No se pudo eliminar el Envio de la lista del Repartidor", true);
                }
            }

        } catch (Exception e) {
            mostrarMensaje("Error al eliminar envio del repartidor: " + e.getMessage(), true);
        }
    }

    @FXML
    void limpiarCampos(ActionEvent event) {
        txtIdRepartidor.clear();
        txtIdRepartidor.setDisable(false);
        txtNombreRepartidor.clear();
        txtTelefonoRepartidor.clear();
        cmbEstado.cancelEdit();

        repartidorSeleccionado=null;
        tblRepartidores.getSelectionModel().clearSelection();

        mostrarMensaje("Formulario limpio - Listo para nuevo repartidor", false);
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

    private boolean validarCampos() {
        if (txtIdRepartidor.getText().trim().isEmpty()) {
            mostrarMensaje("La cédula es obligatoria", true);
            return false;
        }
        if (txtNombreRepartidor.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre es obligatorio", true);
            return false;
        }
        if (txtTelefonoRepartidor.getText().trim().isEmpty()) {
            mostrarMensaje("El teléfono es obligatorio", true);
            return false;
        }
        if (cmbEstado.getValue() == null) {
            mostrarMensaje("Seleccione un estado", true);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        lblMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
}

