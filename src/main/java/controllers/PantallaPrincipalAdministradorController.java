package controllers;

import javafx.beans.property.SimpleDoubleProperty;
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
import models.DTO.UsuarioDTO;
import models.EstadoEnvio;
import models.SessionManager;
import service.facade.AdminFacade;
import utils.PathsFxml;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PantallaPrincipalAdministradorController {

    @FXML    private TextField txtIdAdministrador;

    @FXML    private DatePicker fechaCreacionDesde;
    @FXML    private DatePicker fechaCreacionHasta;
    @FXML    private ComboBox<EstadoEnvio> cmbEstadoEnvio;

    @FXML    private TableView<EnvioDTO> tblEnvios;
    @FXML    private TableColumn<EnvioDTO, String> colIdEnvio;
    @FXML    private TableColumn<EnvioDTO, String > colFechaCreacionEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colDestino;
    @FXML    private TableColumn<EnvioDTO, Number> colCostoEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colEstadoPagoEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colEstadoEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colNombreUsuario;
    @FXML    private TableColumn<EnvioDTO, String> colNombreRepartidor;

    @FXML    private TextField txtIdEnvio;
    @FXML    private TextField txtIncidencia;
    @FXML    private ComboBox<EstadoEnvio> cmbCambioEstadoEnvio;
    @FXML    private ComboBox<RepartidorDTO> cmbRepartidor;

    @FXML    private Label lblMensaje;

    private AdminFacade adminFacade;
    private ObservableList<EnvioDTO> listaEnvios;
    private ObservableList<EnvioDTO> listaEnviosFiltrados;
    private ObservableList<RepartidorDTO> repartidores;
    private EnvioDTO envioSeleccionado;
    private UsuarioDTO usuarioLogueado;
    private SessionManager sessionManager;

    public void initialize() {
        adminFacade = new AdminFacade();
        listaEnvios = FXCollections.observableArrayList();
        repartidores = FXCollections.observableArrayList();
        listaEnviosFiltrados = FXCollections.observableArrayList();
        sessionManager = SessionManager.getInstancia();
        usuarioLogueado = sessionManager.getUsuarioLogueado();
        mostrarMensajeBienvenida();
        txtIdAdministrador.setText(usuarioLogueado.getId());
        txtIdAdministrador.setDisable(true);
        configurarTablaEnvios();
        cargarEnvios();
        configurarSeleccionTablaEnvios();
        cargarRepartidores();
        configurarFiltros();
        cmbEstadoEnvio.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));
        cmbCambioEstadoEnvio.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));
        cmbRepartidor.setItems(FXCollections.observableArrayList(adminFacade.obtenerTodosLosRepartidores()));
    }

    private void configurarTablaEnvios() {
        colIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colFechaCreacionEnvio.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaCreacion();
            return new SimpleStringProperty((fecha != null) ? fecha.format(formatter) : "Sin fecha");
        });
        colDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestino().getCiudad()));
        colCostoEnvio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCosto()));
        colEstadoPagoEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoPago()));
        colEstadoEnvio.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getEstado().toString()));
        colNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreUsuario()));
        colNombreRepartidor.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue().getNombreRepartidor() != null) ? cellData.getValue().getNombreRepartidor() : "No Asignado"));
        tblEnvios.setItems(listaEnvios);
    }

    private void cargarEnvios() {
        listaEnvios.clear();
        listaEnvios.addAll(adminFacade.obtenerTodosLosEnvios());
        mostrarMensaje("Envios cargados: " + listaEnvios.size(), false);
    }

    private void configurarSeleccionTablaEnvios() {
        tblEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection,seleccion) -> {
            if (seleccion!= null) {
                envioSeleccionado=seleccion;
                txtIdEnvio.setText(envioSeleccionado.getId());
                txtIdEnvio.setDisable(true);
            } else {
                limpiarCampos(null);
            }
        });
    }

    private void cargarRepartidores() {
        repartidores.clear();
        repartidores.addAll(adminFacade.obtenerTodosLosRepartidores());
        if (cmbRepartidor != null) {
            cmbRepartidor.setItems(repartidores);
        }
    }

    private void configurarFiltros() {
        fechaCreacionDesde.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        fechaCreacionHasta.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        cmbEstadoEnvio.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        LocalDate desde = fechaCreacionDesde.getValue();
        LocalDate hasta = fechaCreacionHasta.getValue();
        EstadoEnvio estadoSeleccionado = cmbEstadoEnvio.getValue();

        listaEnviosFiltrados.clear();

        for (EnvioDTO envio : listaEnvios) {
            boolean coincide = true;
            LocalDate fechaCreacion = (envio.getFechaCreacion() != null) ?
                    envio.getFechaCreacion().toLocalDate() : null;

            if (desde != null && (fechaCreacion == null || fechaCreacion.isBefore(desde))) {
                coincide = false;
            }
            if (hasta != null && (fechaCreacion == null || fechaCreacion.isAfter(hasta))) {
                coincide = false;
            }
            if (estadoSeleccionado != null && envio.getEstado() != estadoSeleccionado) {
                coincide = false;
            }
            if (coincide) {
                listaEnviosFiltrados.add(envio);
            }
        }
        tblEnvios.setItems(listaEnviosFiltrados);
    }

    @FXML
    void registrarIncidencia(ActionEvent event) {
        try {
            if (envioSeleccionado == null) {
                mostrarMensaje("Seleccione un Envio de la tabla para registrar la incidencia", true);
                return;
            }
            if (txtIncidencia.getText().trim().isEmpty()) {
                mostrarMensaje("No ha llenado el campo con la incidencia", true);
                return;
            }
            if (adminFacade.registrarIncidencia(envioSeleccionado.getId(),txtIncidencia.getText())) {
                mostrarMensaje("Se registro la incidencia correctamente al envio " +envioSeleccionado.getId(), false);
                txtIncidencia.clear();
            } else {
                mostrarMensaje("Error: No se pudo hacer el registro de la incidencia", true);
            }

        } catch (Exception e) {
            mostrarMensaje("Error al registrar la incidencia: " + e.getMessage(), true);
        }
    }

    @FXML
    void cambiarEstadoEnvio(ActionEvent event) {
        try {
            if (envioSeleccionado == null) {
                mostrarMensaje("Seleccione un Envio de la tabla para cambiar el estado del envio", true);
                return;
            }
            if (cmbCambioEstadoEnvio.getValue()==null) {
                mostrarMensaje("No ha seleccionado una opción para cambio de estado", true);
                return;
            }
            envioSeleccionado.setEstado(cmbCambioEstadoEnvio.getValue());

            if (adminFacade.registrarCambioEstado(envioSeleccionado.getId(),envioSeleccionado.getEstado())) {
                mostrarMensaje("Se registro el cambio de estado al envio " +envioSeleccionado.getId(), false);
                cmbEstadoEnvio.getSelectionModel().clearSelection();
                tblEnvios.refresh();
            } else {
                mostrarMensaje("Error: No se pudo hacer el registro del cambio de estado al envio", true);
            }

        } catch (Exception e) {
            mostrarMensaje("Error al registrar el cambio de estado al envio: " + e.getMessage(), true);
        }
    }

    @FXML
    void asignarRepartidorEnvio(ActionEvent event) {
        try {
            if (envioSeleccionado == null) {
                mostrarMensaje("Seleccione un Envio de la tabla para asignar el repartidor", true);
                return;
            }
            if (cmbRepartidor.getValue()==null) {
                mostrarMensaje("No ha seleccionado un repartidor para asignar", true);
                return;
            }
            envioSeleccionado.setNombreRepartidor(cmbRepartidor.getValue().getNombre());

            if (adminFacade.registrarCambioRepartidor(envioSeleccionado.getId(),cmbRepartidor.getValue().getId())) {
                mostrarMensaje("Se registro la asignación de repartidor al envio " +envioSeleccionado.getId(), false);
                cmbEstadoEnvio.getSelectionModel().clearSelection();
                tblEnvios.refresh();
            } else {
                mostrarMensaje("Error: No se pudo hacer la asignación de repartidor al envio", true);
            }

        } catch (Exception e) {
            mostrarMensaje("Error al asignar el repartidor al envio: " + e.getMessage(), true);
        }
    }

    @FXML
    void panelMetricas(ActionEvent event) {

    }





    @FXML
    void cerrarSesion(ActionEvent event) {
        try {
            SessionManager.getInstancia().cerrarSesion();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_LOGIN));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarMensaje("Error al cerrar sesión: " + e.getMessage(), true);
            e.printStackTrace();
        }

    }

    @FXML
    void gestionarRepartidores(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_GESTION_REPARTIDOR_ADM));
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

    @FXML
    void gestionarUsuarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_GESTION_USUARIO_ADM));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarMensaje("Error al abrir Gestión de Usuarios: " + e.getMessage(), true);
            e.printStackTrace();
        }

    }

    private void mostrarMensajeBienvenida() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Bienvenido");
        alerta.setHeaderText(null);
        alerta.setContentText("Usuario: " + usuarioLogueado.getId());
        alerta.show();
    }

    @FXML
    void limpiarCampos(ActionEvent event) {
        fechaCreacionDesde.setValue(null);
        fechaCreacionHasta.setValue(null);
        txtIdEnvio.clear();
        txtIncidencia.clear();
        cmbEstadoEnvio.getSelectionModel().clearSelection();
        cmbRepartidor.getSelectionModel().clearSelection();
        cmbCambioEstadoEnvio.getSelectionModel().clearSelection();

        envioSeleccionado=null;
        tblEnvios.getSelectionModel().clearSelection();
        tblEnvios.setItems(listaEnvios);

        mostrarMensaje("Formulario limpio", false);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        lblMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }


}