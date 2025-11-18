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
import models.DTO.EnvioDTO;
import models.DTO.UsuarioDTO;
import models.Direccion;
import models.SessionManager;
import models.Usuario;
import service.facade.EnvioFacade;
import service.facade.UsuarioFacade;
import utils.PathsFxml;
import utils.mappers.DireccionMapper;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class GestionEnviosController implements Initializable {
    @FXML
    private CheckBox IsSeguro;
    @FXML
    private TextField altoEnvio;
    @FXML
    private TextField anchoEnvio;
    @FXML
    private TableView<EnvioDTO> tableEnviosUsuario;
    @FXML
    private TableColumn<EnvioDTO,String> colCostoEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colDestinoEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colEstadoEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colFechaCreacionEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colIdEnvio;
    @FXML
    private TextField costoEnvioDatos;
    @FXML
    private TextField idEnvio;
    @FXML
    private CheckBox isFirma;
    @FXML
    private CheckBox isFragil;
    @FXML
    private CheckBox isPrioridad;
    @FXML
    private TextField largoEnvio;
    @FXML
    private Label lblMensaje;
    @FXML
    private ComboBox<DireccionDTO> destinoEnvio;
    @FXML
    private ComboBox<DireccionDTO> origenEnvio;
    @FXML
    private TextField pesoEnvio;

    private EnvioFacade envioFacade;
    private UsuarioFacade usuarioFacade;
    private SessionManager sessionManager;
    private UsuarioDTO usuarioLogueado;
    private ObservableList<EnvioDTO> envios;
    private ObservableList<DireccionDTO> direcciones; //Creo la lista de direcciones para los datos del envío
    private EnvioDTO envioSeleccionado;
    private Usuario usuario;
    private NumberFormat formatoPesos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        envioFacade = new EnvioFacade();
        usuarioFacade = new UsuarioFacade();
        sessionManager = SessionManager.getInstancia();
        usuarioLogueado = sessionManager.getUsuarioLogueado();
        usuario = usuarioFacade.buscarUsuarioEntidad(usuarioLogueado.getId());
        formatoPesos = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        envios = FXCollections.observableArrayList();
        direcciones = FXCollections.observableArrayList();
        idEnvio.setDisable(true);
        costoEnvioDatos.setDisable(true);
        cargarDirecciones();
        configurarTabla();
        cargarEnvios();
        configurarSeleccionTabla();
        mostrarMensaje("Pantalla de gestión de envíos cargada", false);
    }

    private void cargarDirecciones() {
        direcciones.clear();
        direcciones.addAll(envioFacade.obtenerDireccionesUsuario(usuarioLogueado.getId()));
        origenEnvio.setItems(direcciones);
        destinoEnvio.setItems(direcciones);

        // Configuro cómo se muestra cada dirección en el ComboBox
        origenEnvio.setCellFactory(param -> new javafx.scene.control.ListCell<DireccionDTO>() {
            @Override
            protected void updateItem(DireccionDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAlias() + " - " + item.getCalle() + ", " + item.getCiudad());
                }
            }
        });
        origenEnvio.setButtonCell(new javafx.scene.control.ListCell<DireccionDTO>() {
            @Override
            protected void updateItem(DireccionDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAlias() + " - " + item.getCalle() + ", " + item.getCiudad());
                }
            }
        });

        origenEnvio.setButtonCell(new javafx.scene.control.ListCell<DireccionDTO>() {
            @Override
            protected void updateItem(DireccionDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAlias() + " - " + item.getCalle() + ", " + item.getCiudad());
                }
            }
        });

        destinoEnvio.setCellFactory(param -> new javafx.scene.control.ListCell<DireccionDTO>() {
            @Override
            protected void updateItem(DireccionDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAlias() + " - " + item.getCalle() + ", " + item.getCiudad());
                }
            }
        });
        destinoEnvio.setButtonCell(new javafx.scene.control.ListCell<DireccionDTO>() {
            @Override
            protected void updateItem(DireccionDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAlias() + " - " + item.getCalle() + ", " + item.getCiudad());
                }
            }
        });
    }

    private void configurarTabla() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        colIdEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        colDestinoEnvio.setCellValueFactory(cellData -> {
            Direccion destino = cellData.getValue().getDestino();
            String texto = (destino != null) ? (destino.getAlias() + " - " + destino.getCalle()) : "Sin destino";
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
        colFechaCreacionEnvio.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaCreacion();
            String texto = (fecha != null) ? fecha.format(formatter) : "Sin fecha";
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
        colCostoEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(formatoPesos.format(cellData.getValue().getCosto())));
        colEstadoEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getEstado())));
    }

    private void cargarEnvios() {
        envios.clear();
        envios.addAll(usuarioFacade.obtenerEnviosUsuario(usuarioLogueado.getId()));
        tableEnviosUsuario.setItems(envios);
    }

    private void configurarSeleccionTabla() {
        tableEnviosUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                envioSeleccionado = newSelection;
                cargarDatosEnvio(newSelection);
            }
        });
    }

    private void cargarDatosEnvio(EnvioDTO envioDTO) {
        if (envioDTO == null) {
            return;
        }
        idEnvio.setText(envioDTO.getId());
        costoEnvioDatos.setText(formatoPesos.format(envioDTO.getCosto()));

        // Convertir Direccion (entity) a DireccionDTO y buscar en la lista
        if (envioDTO.getOrigen() != null) {
            DireccionDTO origenDTO = DireccionMapper.toDireccionDTO(envioDTO.getOrigen());
            if (origenDTO != null) {
                for (DireccionDTO direccion : direcciones) {
                    if (direccion.getId().equals(origenDTO.getId())) {
                        origenEnvio.setValue(direccion);
                        break;
                    }
                }
            }
        }

        // Convertir Direccion (entity) a DireccionDTO y buscar en la lista
        if (envioDTO.getDestino() != null) {
            DireccionDTO destinoDTO = DireccionMapper.toDireccionDTO(envioDTO.getDestino());
            if (destinoDTO != null) {
                for (DireccionDTO dir : direcciones) {
                    if (dir.getId().equals(destinoDTO.getId())) {
                        destinoEnvio.setValue(dir);
                        break;
                    }
                }
            }
        }

        pesoEnvio.setText(String.valueOf(envioDTO.getPeso()));
        largoEnvio.setText(String.valueOf(envioDTO.getLargo()));
        anchoEnvio.setText(String.valueOf(envioDTO.getAncho()));
        altoEnvio.setText(String.valueOf(envioDTO.getAlto()));

        IsSeguro.setSelected(envioDTO.isSeguro());
        isFragil.setSelected(envioDTO.isFragil());
        isFirma.setSelected(envioDTO.isFirma());
        isPrioridad.setSelected(envioDTO.isPrioridad());
    }

    private EnvioDTO obtenerDatosFormulario() {
        EnvioDTO envioDTO = new EnvioDTO();

        DireccionDTO origen = origenEnvio.getValue();
        DireccionDTO destino = destinoEnvio.getValue();

        if (origen == null || destino == null) {
            return null;
        }

        //Busco las direcciones establecidas en los comboBox
        Direccion direccionOrigen = usuarioFacade.buscarDireccionUsuario(usuario, origen);
        Direccion direccionDestino = usuarioFacade.buscarDireccionUsuario(usuario, destino);
        envioDTO.setOrigen(direccionOrigen);
        envioDTO.setDestino(direccionDestino);

        try {
            envioDTO.setPeso(Double.parseDouble(pesoEnvio.getText()));
            envioDTO.setLargo(Double.parseDouble(largoEnvio.getText()));
            envioDTO.setAncho(Double.parseDouble(anchoEnvio.getText()));
            envioDTO.setAlto(Double.parseDouble(altoEnvio.getText()));
        } catch (NumberFormatException e) {
            return null;
        }

        envioDTO.setSeguro(IsSeguro.isSelected());
        envioDTO.setFragil(isFragil.isSelected());
        envioDTO.setFirma(isFirma.isSelected());
        envioDTO.setPrioridad(isPrioridad.isSelected());

        return envioDTO;
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("Gestión Envíos: " + mensaje);
    }

    @FXML
    void cancelar(ActionEvent event) {
        if (envioSeleccionado == null) {
            mostrarAlertaInformativa("Seleccione un envío para cancelar", Alert.AlertType.ERROR);
            mostrarMensaje("Seleccione un envío para cancelar", true);
            return;
        }
        if (!envioSeleccionado.getEstado().equals("SOLICITADO")) {
            mostrarAlertaInformativa("Solo se pueden cancelar envios en estado SOLICITADO", Alert.AlertType.ERROR);
            mostrarMensaje("Solo se pueden cancelar envios en estado SOLICITADO", true);
            return;
        }
        boolean resultadoCancelacion = envioFacade.cancelarEnvioUsuario(usuarioLogueado.getId(), envioSeleccionado.getId());
        if (resultadoCancelacion) {
            mostrarAlertaInformativa("Envío cancelado exitosamente", Alert.AlertType.CONFIRMATION);
            mostrarMensaje("Envío cancelado exitosamente", false);
            cargarEnvios();
            limpiarCampos();
        } else {
            mostrarAlertaInformativa("Error al cancelar el envío, ya se encuentra en ruta o ya fue entregado", Alert.AlertType.ERROR);
            mostrarMensaje("Error al cancelar el envío, ya se encuentra en ruta o ya fue entregado", true);
        }
    }

    @FXML
    void cotizar(ActionEvent event) {
        EnvioDTO envioDTO = obtenerDatosFormulario();
        if (envioDTO == null) {
            mostrarAlertaInformativa("Complete todos los campos correctamente (origen, destino, peso, dimensiones)", Alert.AlertType.ERROR);
            mostrarMensaje("Complete todos los campos correctamente (origen, destino, peso, dimensiones)", true);
            return;
        }
        if(envioDTO.getPeso() <= 0 || envioDTO.getAncho() <= 0 || envioDTO.getLargo() <= 0 || envioDTO.getAlto() <= 0){
            mostrarAlertaInformativa("Verifique Características del Envío. El Peso, Largo, Ancho y Alto no pueden ser valores menores o iguales a cero", Alert.AlertType.ERROR);
            return;
        }
        double costo = envioFacade.cotizarEnvio(envioDTO);
        costoEnvioDatos.setText(formatoPesos.format(costo));
        mostrarMensaje("Cotización realizada: $" + String.format("%.2f", costo), false);
    }

    @FXML
    void crear(ActionEvent event) {
        EnvioDTO envioDTO = obtenerDatosFormulario();
        if (envioDTO == null) {
            mostrarAlertaInformativa("Complete todos los campos correctamente (origen, destino, peso, dimensiones)", Alert.AlertType.ERROR);
            mostrarMensaje("Complete todos los campos correctamente (origen, destino, peso, dimensiones)", true);
            return;
        }
        if(envioDTO.getPeso() <= 0 || envioDTO.getAncho() <= 0 || envioDTO.getLargo() <= 0 || envioDTO.getAlto() <= 0){
            mostrarAlertaInformativa("Verifique Características del Envío. El Peso, Largo, Ancho y Alto no pueden ser valores menores o iguales a cero", Alert.AlertType.ERROR);
            return;
        }
        boolean resultadoCreacion = envioFacade.crearEnvioUsuario(usuarioLogueado.getId(), envioDTO);
        if (resultadoCreacion) {
            mostrarAlertaInformativa("Envío creado exitosamente. Estado: SOLICITADO", Alert.AlertType.CONFIRMATION);
            mostrarMensaje("Envío creado exitosamente. Estado: SOLICITADO", false);
            cargarEnvios();
            limpiarCampos();
        } else {
            mostrarAlertaInformativa("Error al crear el envío", Alert.AlertType.ERROR);
            mostrarMensaje("Error al crear el envío", true);
        }
    }

    @FXML
    void limpiar(ActionEvent event) {
        limpiarCampos();
        mostrarMensaje("Campos limpiados", false);
    }

    private void limpiarCampos() {
        idEnvio.clear();
        origenEnvio.setValue(null);
        destinoEnvio.setValue(null);
        pesoEnvio.clear();
        largoEnvio.clear();
        anchoEnvio.clear();
        altoEnvio.clear();
        costoEnvioDatos.clear();
        IsSeguro.setSelected(false);
        isFragil.setSelected(false);
        isFirma.setSelected(false);
        isPrioridad.setSelected(false);
        envioSeleccionado = null;
        tableEnviosUsuario.getSelectionModel().clearSelection();
    }

    @FXML
    void modificar(ActionEvent event) {

        if (envioSeleccionado == null) {
            mostrarAlertaInformativa("Seleccione un envío de la tabla para modificar", Alert.AlertType.ERROR);
            mostrarMensaje("Seleccione un envío de la tabla para modificar", true);
            return;
        }

        if (!envioSeleccionado.getEstado().equals("SOLICITADO")){
            mostrarAlertaInformativa("Solo se pueden modificar envíos en estado SOLICITADO", Alert.AlertType.ERROR);
            mostrarMensaje("Solo se pueden modificar envíos en estado SOLICITADO", true);
            return;
        }

        EnvioDTO envioDTO = obtenerDatosFormulario();

        if (envioDTO == null) {
            mostrarAlertaInformativa("Complete todos los campos correctamente (origen, destino, peso, dimensiones)", Alert.AlertType.ERROR);
            mostrarMensaje("Complete todos los campos correctamente (origen, destino, peso, dimensiones)", true);
            return;
        }

        if (envioDTO.getPeso() <= 0 || envioDTO.getAncho() <= 0 || envioDTO.getLargo() <= 0 || envioDTO.getAlto() <= 0){
            mostrarAlertaInformativa("Verifique Características del Envío. El Peso, Largo, Ancho y Alto no pueden ser valores menores o iguales a cero", Alert.AlertType.ERROR);
            mostrarMensaje("Verifique Características del Envío. El Peso, Largo, Ancho y Alto no pueden ser valores menores o iguales a cero", true);
            return;
        }

        boolean resultadoActualizacion = envioFacade.modificarEnvioUsuario(usuarioLogueado.getId(), envioSeleccionado.getId(), envioDTO);
        if (resultadoActualizacion) {
            mostrarAlertaInformativa("Envío modificado exitosamente", Alert.AlertType.CONFIRMATION);
            mostrarMensaje("Envío modificado exitosamente", false);
            cargarEnvios();
            // Recargo el envio seleccionado para mostrar su info
            envioSeleccionado = envioFacade.buscarEnvioUsuario(usuarioLogueado.getId(), envioSeleccionado.getId());
            if (envioSeleccionado != null) {
                cargarDatosEnvio(envioSeleccionado);
            }
        } else {
            mostrarMensaje("Error al modificar el envío. Verifique que el envío esté en estado SOLICITADO", true);
            mostrarAlertaInformativa("Error al modificar el envío. Verifique que el envío esté en estado SOLICITADO", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlertaInformativa(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Nuevo Mensaje: ");
        alerta.setHeaderText(null);

        if (mensaje == null) {
            mensaje = "No hay información por mostrar";
        }

        alerta.setContentText(mensaje);
        alerta.setResizable(true); //Permite que la ventana de la alerta se pueda redimensionar manualmente con el mouse
        alerta.getDialogPane().setPrefWidth(500); //Ajusta el ancho preferido del panel interno del diálogo
        alerta.showAndWait(); //Detiene la ejecución del programa hasta que el usuario la cierre
    }

    @FXML
    void regresarPantallaPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_PANTALLA_PRINCIPAL_USUARIO));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarMensaje("Error al regresar a la pantalla principal: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }
}
