package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DTO.UsuarioDTO;
import models.SessionManager;
import service.facade.UsuarioFacade;
import utils.PathsFxml;


public class RegistroUsuarioController {

    @FXML private TextField txtIdUsuario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPassword;
    @FXML private TextField txtPassword2;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;

    @FXML private Label lblMensaje;

    private UsuarioFacade usuarioFacade;

    public void initialize() {
        usuarioFacade = new UsuarioFacade();
        limpiarCampos();
    }

    @FXML
    void CrearUsuario(ActionEvent event) {
        try {
            if (!validarCampos()) {
                return;
            }
            UsuarioDTO nuevoUsuario = new UsuarioDTO(
                    txtPassword.getText().trim(),
                    txtIdUsuario.getText().trim(),
                    txtNombre.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtTelefono.getText().trim());

            if (usuarioFacade.agregarUsuario(nuevoUsuario)) {
                mostrarMensaje("Usuario creado exitosamente" +nuevoUsuario.getNombre(), false);
            } else {
                mostrarMensaje("Error: Ya existe un Usuario con la cédula " + nuevoUsuario.getId(), true);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al crear el Usuario: " + e.getMessage(), true);
        }
        limpiarCampos();
    }

    @FXML
    void regresarALogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_LOGIN));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarMensaje("Error al volver a login: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void limpiarCampos() {
        txtIdUsuario.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtPassword.clear();
        txtPassword2.clear();
    }

    private boolean validarCampos() {
        if (txtIdUsuario.getText().trim().isEmpty()) {
            mostrarMensaje("La cédula es obligatoria", true);
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre es obligatorio", true);
            return false;
        }
        if (txtPassword.getText().trim().isEmpty()) {
            mostrarMensaje("La contraseña es obligatoria", true);
            return false;
        }
        if (txtPassword!=txtPassword2) {
            mostrarMensaje("La contraseña debe coincidir en los dos campos", true);
        }
        String correo = txtCorreo.getText().trim();
        if (!correo.contains("@") || !correo.contains(".")) {
            mostrarMensaje("El formato del correo no es válido", true);
            return false;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("El telefono es obligatorio", true);
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
