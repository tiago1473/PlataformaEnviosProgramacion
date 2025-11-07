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
import models.Rol;
import models.SessionManager;
import service.facade.LoginFacade;
import utils.PathsFxml;
import javafx.scene.input.MouseEvent;
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
        inputUsername.clear();
        inputPassword.clear();

    }

    @FXML
    void login(ActionEvent event) {
        try {
            String id = inputUsername.getText().trim();
            String password = inputPassword.getText().trim();

            if (id.isEmpty() || password.isEmpty()) {
                mostrarError("Por favor ingrese su usuario y contraseña");
                return;
            }

            UsuarioDTO usuario = loginFacade.validarCredenciales(id,password);

            if (usuario.getRol()== Rol.USER) {
                SessionManager.getInstancia().iniciarSesion(usuario);
                navegarAPantalla(event, PathsFxml.PATH_PANTALLA_PRINCIPAL_USUARIO);
            }else if(usuario.getRol()==Rol.ADM){
                SessionManager.getInstancia().iniciarSesion(usuario);
                navegarAPantalla(event, PathsFxml.PATH_PANTALLA_PRINCIPAL_ADMINISTRADOR); //CAMBIAR CUANDO TENGA LA PANTALLA
            }else {
                    mostrarError("Credenciales inválidas. Verifique su ID y contraseña.");
                    inputPassword.clear();
                    inputUsername.clear();
            }
        } catch (Exception e) {
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
    }

    public void registrarUsuario(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_REGISTER));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarError("Error al ingresar a Registrar Usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}