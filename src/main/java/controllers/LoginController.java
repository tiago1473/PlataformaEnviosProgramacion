package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DTO.UsuarioDTO;
import models.SessionManager;
import service.facade.LoginFacade;
import utils.PathsFxml;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Button btnLogin;
    @FXML private PasswordField inputPassword;
    @FXML private TextField inputUsername;
    @FXML private Label lblMensajeError;
    private LoginFacade loginFacade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginFacade = new LoginFacade();
        if (inputUsername != null) {
            inputUsername.clear();
        }
        if (inputPassword != null) {
            inputPassword.clear();
        }
    }

    @FXML
    void login(ActionEvent event) {
        try {
            String id = inputUsername.getText();
            String password = inputPassword.getText();

            if (id == null || id.trim().isEmpty()) {
                mostrarError("Por favor ingrese su ID de usuario");
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                mostrarError("Por favor ingrese su contraseña");
                return;
            }

            UsuarioDTO usuario = loginFacade.validarCredenciales(id, password);

            if (usuario != null) {
                System.out.println("Login exitoso. Usuario: " + usuario.getNombre()+ ": " + usuario.getId());
                // Guardo el Usuario en Sesión
                SessionManager.getInstancia().iniciarSesion(usuario);
                navegarAPantalla(event, PathsFxml.PATH_PANTALLA_PRINCIPAL_USUARIO); //La del Usuario
            }else if(loginFacade.esAdministrador(id, password)){
                navegarAPantalla(event, PathsFxml.PATH_GESTION_REPARTIDOR); //La del Admin
            }else {
                    mostrarError("Credenciales inválidas. Verifique su ID y contraseña.");
                    inputPassword.clear();
                    inputUsername.clear();
            }
        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al iniciar sesión. Intente nuevamente.");
        }
    }

    private void navegarAPantalla(ActionEvent event, String pathFxml) {
        try {
            // Cargo la Nueva Pantalla
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathFxml));
            Parent root = loader.load();

            // Para obtener el stage actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crear nueva escena
            Scene scene = new Scene(root);

            // Cambiar la escena
            stage.setScene(scene);
            stage.show();

            System.out.println("Navegación exitosa a: " + pathFxml);
        } catch (Exception e) {
            System.err.println("Error al navegar a la pantalla: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al cargar la pantalla. Intente nuevamente.");
        }
    }

    private void mostrarError(String mensaje) {
        if (lblMensajeError != null) {
            lblMensajeError.setText(mensaje);
            lblMensajeError.setVisible(true);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Login");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        System.out.println("Error de login: " + mensaje);
    }
}