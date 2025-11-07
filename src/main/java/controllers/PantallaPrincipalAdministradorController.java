package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.DTO.UsuarioDTO;
import models.SessionManager;
import utils.PathsFxml;

public class PantallaPrincipalAdministradorController {

    @FXML
    private ComboBox<?> cmbEstadoEnvio;

    @FXML
    private TableColumn<?, ?> colCostoEnvio;

    @FXML
    private TableColumn<?, ?> colEstadoEnvio;

    @FXML
    private TableColumn<?, ?> colEstadoPagoEnvio;

    @FXML
    private TableColumn<?, ?> colFechaCreacionEnvio;

    @FXML
    private TableColumn<?, ?> colFechaEntregaEnvio;

    @FXML
    private TableColumn<?, ?> colIdEnvio;

    @FXML
    private TableColumn<?, ?> colNombreRepartidor;

    @FXML
    private TableColumn<?, ?> colNombreUsuario;

    @FXML
    private ComboBox<?> combRepartidor;

    @FXML
    private ComboBox<?> estadoEnvio;

    @FXML
    private DatePicker fechaCreacionDesde;

    @FXML
    private DatePicker fechaCreacionHasta;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<?> tableEnviosPlataforma;

    @FXML
    private TextField txtIdAdministrador;

    @FXML
    private TextField txtIdEnvio;

    @FXML
    private TextField txtIncidencia;

    private UsuarioDTO usuarioLogueado;
    private SessionManager sessionManager;

    void initialize() {
        sessionManager = SessionManager.getInstancia();
        usuarioLogueado = sessionManager.getUsuarioLogueado();
        mostrarMensajeBienvenida();
    }


    @FXML
    void asignarRepartidorEnvio(ActionEvent event) {

    }

    @FXML
    void cambiarEstadoEnvio(ActionEvent event) {

    }

    @FXML
    void cerrarSesion(ActionEvent event) {

    }

    @FXML
    void limpiarCampos(ActionEvent event) {

    }

    @FXML
    void panelMetricas(ActionEvent event) {

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

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        lblMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }


}