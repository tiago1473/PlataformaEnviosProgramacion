package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.EstadoRepartidor;
import models.Repartidor;

public class GestionRepartidorController {

    @FXML private TextField txtIdRepartidor;
    @FXML private TextField txtNombreRepartidor;
    @FXML private TextField txtTelefonoRepartidor;
    @FXML private ComboBox<?> cmbEstado;
    @FXML private Label labelLatitud;
    @FXML private Label labelLongitud;

    @FXML private TableView<?> tblEnvios;
    @FXML private TableColumn<?, ?> colEnvio;
    @FXML private TableColumn<?, ?> colOrigen;
    @FXML private TableColumn<?, ?> colDestino;

    @FXML private TableView<Repartidor> tblRepartidores;
    @FXML private TableColumn<Repartidor, String> colIdentificacion;
    @FXML private TableColumn<Repartidor, String> colNombre;
    @FXML private TableColumn<Repartidor, String > colTelefono;
    @FXML private TableColumn<Repartidor, EstadoRepartidor> colEstado;
    @FXML private TableColumn<Repartidor, Integer> colEnviosAsignados;

    @FXML private Label labelMensaje;

    @FXML
    void initialize() {


    }
    @FXML
    void actualizarRepartidor(ActionEvent event) {

    }

    @FXML
    void crearRepartidor(ActionEvent event) {

    }

    @FXML
    void eliminarEnvio(ActionEvent event) {

    }

    @FXML
    void eliminarRepartidor(ActionEvent event) {

    }

    @FXML
    void limpiarCampos(ActionEvent event) {

    }

    @FXML
    void regresarVista(ActionEvent event) {

    }

}
