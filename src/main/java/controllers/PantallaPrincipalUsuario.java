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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.DTO.EnvioDTO;
import models.DTO.UsuarioDTO;
import models.Incidencia;
import models.SessionManager;
import models.Usuario;
import service.estadoState.EstadoEnvioState;
import service.estadoState.EstadoEnvioValues;
import service.facade.UsuarioFacade;
import utils.PathsFxml;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PantallaPrincipalUsuario implements Initializable {

    @FXML
    private TableView<EnvioDTO> tableEnviosUsuario;
    @FXML
    private TableColumn<EnvioDTO,String> colIdEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> ColOrigenEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colDestinoEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colCostoEnvio; //Debo castear el costo a String
    @FXML
    private TableColumn<EnvioDTO,String> colFechaCreacionEnvio; //Debo castear la fecha a String
    @FXML
    private TableColumn<EnvioDTO,String> colFechaEntregaEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colFechaEstimadaEntregaEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colEstadoEnvio;
    @FXML
    private TableColumn<EnvioDTO,String> colEstadoPagoEnvio;
    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private Label lblMensaje;
    @FXML
    private TextField costoEnvioParaPago;
    @FXML
    private ComboBox<EstadoEnvioState> estadoEnvio;
    @FXML
    private DatePicker fechaCreacionDesde;
    @FXML
    private DatePicker fechaCreacionHasta;
    @FXML
    private ComboBox<String> metodoDePago;
    @FXML
    private TextField txtIdEnvioParaPago;

    private UsuarioFacade usuarioFacade;
    private ObservableList<EnvioDTO> envios;
    private ObservableList<EnvioDTO> enviosFiltrados;
    private EnvioDTO envioSeleccionado; //Atrapa lo que seleccioné en la tabla
    private SessionManager sessionManager;
    private UsuarioDTO usuarioLogueado;
    private Usuario usuario;
    private NumberFormat formatoPesos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioFacade = new UsuarioFacade();
        sessionManager = SessionManager.getInstancia();
        usuarioLogueado = sessionManager.getUsuarioLogueado();
        usuario = usuarioFacade.buscarUsuarioEntidad(usuarioLogueado.getId());
        envios = FXCollections.observableArrayList();
        enviosFiltrados = FXCollections.observableArrayList();
        formatoPesos = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        mostrarMensajeBienvenida();
        cargarInformacionUsuario();
        txtIdUsuario.setDisable(true);
        txtNombreUsuario.setDisable(true);
        txtIdEnvioParaPago.setDisable(true);
        costoEnvioParaPago.setDisable(true);
        configurarTabla();
        cargarEnvios();
        configurarSeleccionTabla();
        configurarFiltros();
        tableEnviosUsuario.setItems(enviosFiltrados);
        configurarPagosUI();
        System.out.println("PantallaPrincipalUsuarioController inicializada correctamente");
    }

    private void configurarTabla() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        colIdEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        ColOrigenEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getOrigen().getIdDireccion())));
        colDestinoEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getDestino().getIdDireccion())));
        colCostoEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(formatoPesos.format(cellData.getValue().getCosto())));
        colFechaCreacionEnvio.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaCreacion();
            String texto = (fecha != null) ? fecha.format(formatter) : "Sin fecha";
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
        colFechaEstimadaEntregaEnvio.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaEstimadaEntrega();
            String texto = (fecha != null) ? fecha.format(formatter) : "Sin fecha";
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
        colFechaEntregaEnvio.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaEntrega();
            String texto = (fecha != null) ? fecha.format(formatter) : "No entregado";
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
        colEstadoEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getEstado())));
        colEstadoPagoEnvio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getEstadoPago())));
    }

    private void configurarSeleccionTabla() {
        tableEnviosUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                envioSeleccionado = newSelection;
                cargarDatosEnvio(newSelection);
            }
        });
    }

    private void cargarDatosEnvio(EnvioDTO envioDTOSeleccionado) {
        if (envioDTOSeleccionado == null) {
            return;
        }
        if (envioDTOSeleccionado.getEstado().equals("SOLICITADO")) { //Para que se habilite el pago
            txtIdEnvioParaPago.setText(envioDTOSeleccionado.getId());
            costoEnvioParaPago.setText(formatoPesos.format(envioDTOSeleccionado.getCosto()));
        }
    }

    private void cargarInformacionUsuario() {
        txtIdUsuario.setText(usuarioLogueado.getId());
        txtNombreUsuario.setText(usuarioLogueado.getNombre());
    }

    private void cargarEnvios() {
        cargarEnvios(true);
    }
    
    private void cargarEnvios(boolean mostrarMensaje) {
        envios.clear();
        envios.addAll(usuarioFacade.obtenerEnviosUsuario(usuario.getId()));
        enviosFiltrados.setAll(envios); //se los mando todos a la lista de filtrados
        if (mostrarMensaje) {
            mostrarMensaje("Envíos cargados: " + envios.size(), false);
        }
    }

    private void configurarFiltros() {
        estadoEnvio.setItems(FXCollections.observableArrayList(EstadoEnvioValues.values()));
        estadoEnvio.setConverter(new StringConverter<>() {
            @Override
            public String toString(EstadoEnvioState estado) {
                return estado == null ? "" : estado.getNombre();
            }
            @Override
            public EstadoEnvioState fromString(String string) {
                return null;
            }
        });

        //Llama a aplicarFiltros cada que hay un cambio
        fechaCreacionDesde.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        fechaCreacionHasta.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        estadoEnvio.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
    }

    private void configurarPagosUI() {
        metodoDePago.getItems().setAll(
                "Tarjeta",
                "PSE"
        );
    }

    private void aplicarFiltros() {
        LocalDate desde = fechaCreacionDesde.getValue();
        LocalDate hasta = fechaCreacionHasta.getValue();
        EstadoEnvioState estadoSeleccionado = estadoEnvio.getValue();

        enviosFiltrados.clear();

        for (EnvioDTO envio : envios) {
            boolean coincide = true;

            //Filtramos por Fecha
            LocalDate fechaCreacion = (envio.getFechaCreacion() != null) ?
                    envio.getFechaCreacion().toLocalDate() : null;

            if (desde != null && (fechaCreacion == null || fechaCreacion.isBefore(desde))) {
                coincide = false;
            }

            if (hasta != null && (fechaCreacion == null || fechaCreacion.isAfter(hasta))) {
                coincide = false;
            }

            //Filtramos por Estado
            if (estadoSeleccionado != null && !envio.getEstado().equals(estadoSeleccionado.getNombre())) {
                coincide = false;
            }

            if (coincide) {
                enviosFiltrados.add(envio);
            }
        }
    }

    @FXML
    void gestionarCotizarEnvios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_GESTION_ENVIOS));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarMensaje("Error al abrir Gestión Usuario: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    void gestionarDatosYDirecciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_GESTION_USUARIO));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mostrarMensaje("Error al abrir Gestión Usuario: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
        System.out.println("Pantalla Principal Usuario: " + mensaje);
    }

    private void mostrarMensajeBienvenida() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Bienvenido");
        alerta.setHeaderText(null);
        alerta.setContentText("Usuario: " + usuarioLogueado.getNombre()+
                " Cedula: "+usuarioLogueado.getId());
        alerta.show();
    }

    @FXML
    void descargarReporteEnvios(ActionEvent event) {
        try {
            List<String> lineas = enviosFiltrados.stream().map(this::toCsvLineaEnvio).collect(Collectors.toList());
            java.nio.file.Path destino = java.nio.file.Paths.get(System.getProperty("user.home"), "reporte_envios.csv");
            java.nio.file.Files.write(destino, (
                    "ID;Origen;Destino;Costo;FechaCreacion;FechaEntrega;Estado;EstadoPago\n"
                            + String.join("\n", lineas)).getBytes(java.nio.charset.StandardCharsets.UTF_8));
            mostrarMensaje("Reporte de envíos exportado en: " + destino, false);
            File archivo = destino.toFile();
            if (archivo.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            mostrarMensaje("Error exportando reporte de envíos: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private String toCsvLineaEnvio(EnvioDTO e) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fc = e.getFechaCreacion() != null ? e.getFechaCreacion().format(formatter) : "";
        String fe = e.getFechaEntrega() != null ? e.getFechaEntrega().format(formatter) : "";
        String origen = e.getOrigen() != null ? String.valueOf(e.getOrigen().getIdDireccion()) : "";
        String destino = e.getDestino() != null ? String.valueOf(e.getDestino().getIdDireccion()) : "";
        return String.join(";",
                e.getId(),
                origen,
                destino,
                String.valueOf(e.getCosto()),
                fc,
                fe,
                String.valueOf(e.getEstado()),
                String.valueOf(e.getEstadoPago())
        );
    }

    @FXML
    void descargarReportePagos(ActionEvent event) {
        try {
            List<EnvioDTO> pagados = enviosFiltrados.stream()
                    .filter(e -> "Pago".equalsIgnoreCase(String.valueOf(e.getEstadoPago())))
                    .collect(Collectors.toList());
            List<String> lineas = pagados.stream().map(this::toCsvLineaEnvio).collect(Collectors.toList());
            java.nio.file.Path destino = java.nio.file.Paths.get(System.getProperty("user.home"), "reporte_pagos_envios.csv");
            java.nio.file.Files.write(destino, (
                    "ID;Origen;Destino;Costo;FechaCreacion;FechaEntrega;Estado;EstadoPago\n"
                            + String.join("\n", lineas)).getBytes(java.nio.charset.StandardCharsets.UTF_8));
            mostrarMensaje("Reporte de pagos exportado en: " + destino, false);
            File archivo = destino.toFile();
            if (archivo.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            mostrarMensaje("Error exportando reporte de pagos: " + e.getMessage(), true);
            e.printStackTrace();
        }

    }

    @FXML
    void limpiar(ActionEvent event) {
        fechaCreacionDesde.setValue(null);
        fechaCreacionHasta.setValue(null);
        estadoEnvio.setValue(null);
        enviosFiltrados.setAll(envios);
        tableEnviosUsuario.getSelectionModel().clearSelection();
        txtIdEnvioParaPago.clear();
        costoEnvioParaPago.clear();
        metodoDePago.setValue(null);
        mostrarMensaje("Filtros limpiados", false);
    }

    @FXML
    void verIncidenciasEnvio(ActionEvent event) {
        envioSeleccionado = tableEnviosUsuario.getSelectionModel().getSelectedItem();
        if (envioSeleccionado == null) {
            mostrarMensaje("Por favor, seleccione un envío de la tabla", true);
            return;
        }

        EnvioDTO envioCompleto = usuarioFacade.buscarEnvioUsuario(usuario.getId(), envioSeleccionado.getId());
        if (envioCompleto == null) {
            mostrarMensajeIncidencias("No se pudo obtener la información del envío");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (envioCompleto.getIncidencias() != null && !envioCompleto.getIncidencias().isEmpty()) { //Que no sea null y que la lista no esté vacía
            StringBuilder mensajeIncidencias = new StringBuilder();
            mensajeIncidencias.append("Envío: ").append(envioCompleto.getId()).append("\n\n");
            mensajeIncidencias.append("Incidencias registradas:\n\n");

            for (Incidencia incidencia : envioCompleto.getIncidencias()) {
                if (incidencia != null) { //Porque puede que alguna sea null
                    String fecha = (incidencia.getFecha() != null) ? incidencia.getFecha().format(formatter)
                            : "Fecha no disponible";
                    String descripcion = (incidencia.getDescripcion() != null) ? incidencia.getDescripcion()
                            : "Sin descripción";
                    mensajeIncidencias.append("Fecha: ").append(fecha).append("\n");
                    mensajeIncidencias.append("Descripción: ").append(descripcion).append("\n\n");
                }
            }
            mostrarMensajeIncidencias(mensajeIncidencias.toString());
        }else{
            mostrarMensajeIncidencias("El envío " + envioCompleto.getId() + " aún no registra incidencias.");
        }
    }

    private void mostrarMensajeIncidencias(String mensajeIncidencias) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Incidencias de Envío");
        alerta.setHeaderText(null);

        if (mensajeIncidencias == null) {
            mensajeIncidencias = "No hay información de incidencias disponible.";
        }

        alerta.setContentText(mensajeIncidencias);
        alerta.setResizable(true); //Permite que la ventana de la alerta se pueda redimensionar manualmente con el mouse
        alerta.getDialogPane().setPrefWidth(500); //Ajusta el ancho preferido del panel interno del diálogo
        alerta.showAndWait(); //Detiene la ejecución del programa hasta que el usuario la cierre
    }

    @FXML
    void pagar(ActionEvent event) {
        String idEnvio = txtIdEnvioParaPago.getText() != null ? txtIdEnvioParaPago.getText().trim() : "";
        String metodo = metodoDePago.getValue();
        if (idEnvio.isEmpty()) {
            mostrarAlertaInformativa("Ingrese o seleccione un envío para pagar", Alert.AlertType.ERROR);
            mostrarMensaje("Ingrese o seleccione un envío para pagar", true);
            return;
        }
        if (metodo == null || metodo.isEmpty()) {
            mostrarAlertaInformativa("Seleccione un método de pago", Alert.AlertType.ERROR);
            mostrarMensaje("Seleccione un método de pago", true);
            return;
        }

        EnvioDTO envio = usuarioFacade.buscarEnvioUsuario(usuario.getId(), idEnvio);

        if (envio == null) {
            mostrarAlertaInformativa("Envío no encontrado", Alert.AlertType.ERROR);
            mostrarMensaje("Envío no encontrado", true);
            return;
        }
        if (!envio.getEstado().equals("SOLICITADO")) {
            mostrarAlertaInformativa("Solo se pueden pagar envíos en estado SOLICITADO", Alert.AlertType.ERROR);
            mostrarMensaje("Solo se pueden pagar envíos en estado SOLICITADO", true);
            return;
        }

        if (usuarioFacade.procesarPagoEnvio(envio.getId(), envio.getCosto(), metodo)) {
            cargarEnvios(false);
            aplicarFiltros();
            mostrarAlertaInformativa("Pago exitoso con " + metodo + ". Envío ahora POR ASIGNAR.", Alert.AlertType.CONFIRMATION);
            mostrarMensaje("Pago exitoso con " + metodo + ". Envío ahora POR ASIGNAR.", false);
            txtIdEnvioParaPago.clear();
            costoEnvioParaPago.clear();
            metodoDePago.setValue(null);
        } else {
            mostrarAlertaInformativa("Error: No se pudo procesar el pago. Verifique los datos.", Alert.AlertType.ERROR);
            mostrarMensaje("Error: No se pudo procesar el pago. Verifique los datos.", true);
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
}
