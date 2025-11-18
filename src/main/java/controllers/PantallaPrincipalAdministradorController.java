package controllers;


import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.DTO.EnvioDTO;
import models.DTO.RepartidorDTO;
import models.DTO.UsuarioDTO;
import models.SessionManager;
import service.estadoState.EstadoEnvioState;
import service.estadoState.EstadoEnvioValues;
import service.facade.AdminFacade;
import utils.PathsFxml;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class PantallaPrincipalAdministradorController {

    @FXML    private TextField txtIdAdministrador;

    @FXML    private DatePicker fechaCreacionDesde;
    @FXML    private DatePicker fechaCreacionHasta;
    @FXML    private ComboBox<EstadoEnvioState> cmbEstadoEnvio;
    @FXML    private Button btnMetricas;
    @FXML    private ComboBox<String> cmbGenerarReportes;

    @FXML    private TableView<EnvioDTO> tblEnvios;
    @FXML    private TableColumn<EnvioDTO, String> colIdEnvio;
    @FXML    private TableColumn<EnvioDTO, String > colFechaCreacionEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colFechaEntregaEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colDestino;
    @FXML    private TableColumn<EnvioDTO, String> colCostoEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colEstadoPagoEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colEstadoEnvio;
    @FXML    private TableColumn<EnvioDTO, String> colNombreUsuario;
    @FXML    private TableColumn<EnvioDTO, String> colNombreRepartidor;

    @FXML    private TextField txtIdEnvio;
    @FXML    private TextField txtIncidencia;
    @FXML    private ComboBox<EstadoEnvioState> cmbCambioEstadoEnvio;
    @FXML    private ComboBox<RepartidorDTO> cmbRepartidor;

    @FXML    private Label lblMensaje;

    private AdminFacade adminFacade;
    private ObservableList<EnvioDTO> listaEnvios;
    private ObservableList<EnvioDTO> listaEnviosFiltrados;
    private ObservableList<RepartidorDTO> repartidores;
    private EnvioDTO envioSeleccionado;
    private UsuarioDTO usuarioLogueado;
    private SessionManager sessionManager;
    private NumberFormat cop;
    private DateTimeFormatter formatter;

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
        cop = NumberFormat.getCurrencyInstance(new java.util.Locale("es", "CO"));
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        cmbEstadoEnvio.setItems(FXCollections.observableArrayList(EstadoEnvioValues.values()));
        cmbEstadoEnvio.setConverter(new StringConverter<>() {
            @Override
            public String toString(EstadoEnvioState estado) {
                return estado == null ? "" : estado.getNombre();
            }
            @Override
            public EstadoEnvioState fromString(String string) {
                return null;
            }
        });
        cmbCambioEstadoEnvio.setItems(FXCollections.observableArrayList(EstadoEnvioValues.values()));
        cmbCambioEstadoEnvio.setConverter(new StringConverter<>() {
            @Override
            public String toString(EstadoEnvioState estado) {
                return estado == null ? "" : estado.getNombre();
            }
            @Override
            public EstadoEnvioState fromString(String string) {
                return null;
            }
        });
        cmbRepartidor.setItems(FXCollections.observableArrayList(adminFacade.obtenerTodosLosRepartidores()));
        configurarComboReportes();
    }

    private void configurarTablaEnvios() {
        colIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colFechaCreacionEnvio.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaCreacion();
            return new SimpleStringProperty((fecha != null) ? fecha.format(formatter) : "Sin fecha");
        });
        colFechaEntregaEnvio.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaEntrega();
            return new SimpleStringProperty((fecha != null) ? fecha.format(formatter) : "Sin fecha");
        });

        colCostoEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cop.format(cellData.getValue().getCosto())));
        colEstadoPagoEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoPago()));
        colEstadoEnvio.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getEstado()));
        colNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreUsuario()));
        colNombreRepartidor.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue().getNombreRepartidor() != null) ? cellData.getValue().getNombreRepartidor() : "No Asignado"));
        colDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestino().getCiudad()));
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
        listaEnviosFiltrados.clear();

        LocalDate desde = fechaCreacionDesde.getValue();
        LocalDate hasta = fechaCreacionHasta.getValue();
        EstadoEnvioState estadoSeleccionado = cmbEstadoEnvio.getValue();


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
            if (estadoSeleccionado != null && !envio.getEstado().equals(estadoSeleccionado.getNombre())) {
                coincide = false;
            }
            if (coincide) {
                listaEnviosFiltrados.add(envio);
            }
        }
        Platform.runLater(() -> {
            tblEnvios.setItems(listaEnviosFiltrados);
            tblEnvios.getSelectionModel().clearSelection();
        });
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

            if (adminFacade.registrarCambioEstado(envioSeleccionado.getId(),cmbCambioEstadoEnvio.getValue().getNombre())) {
                mostrarMensaje("Se registro el cambio de estado al envio " +envioSeleccionado.getId(), false);
                Platform.runLater(() -> {
                    tblEnvios.refresh();
                    if (!cmbEstadoEnvio.getItems().isEmpty()) {
                        cmbEstadoEnvio.getSelectionModel().clearSelection();
                    }
                });
                envioSeleccionado.setEstado(cmbCambioEstadoEnvio.getValue().getNombre());
            } else {
                mostrarMensaje("Error: No se pudo hacer el registro del cambio de estado al envio", true);
            }

        } catch (IllegalArgumentException |IllegalStateException e) {
            mostrarMensaje("Error: " + e.getMessage(), true);

        } catch (Exception e) {
        mostrarMensaje("Ocurrió un error inesperado: " + e.getMessage(), true);
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
            if (envioSeleccionado.getEstado().equals("EN_RUTA")){
                mostrarMensaje("El envio ya se encuentra en RUTA", true);
                return;
            }
            if (envioSeleccionado.getEstado().equals("ENTREGADO")){
                mostrarMensaje("El envio ya se encuentra en ENTREGADO", true);
                return;
            }
            if (envioSeleccionado.getEstado().equals("SOLICITADO")){
                mostrarMensaje("No se puede asignar un envio en estado SOLICITADO, se debe pagar primero", true);
                return;
            }
            if (adminFacade.registrarCambioRepartidor(envioSeleccionado.getId(),cmbRepartidor.getValue().getId())) {
                mostrarMensaje("Se registro la asignación de repartidor al envio " +envioSeleccionado.getId(), false);
                envioSeleccionado.setNombreRepartidor(cmbRepartidor.getValue().getNombre());
                envioSeleccionado.setEstado("ASIGNADO");
                cmbEstadoEnvio.getSelectionModel().clearSelection();
                tblEnvios.refresh();
            } else {
                mostrarMensaje("Error: El envio ya se encuentra en ruta o entregado", true);
            }

        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage(), true);
        }
    }

    @FXML
    void panelMetricas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PathsFxml.PATH_PANEL_METRICAS));
            Parent root = loader.load();

            Stage adminStage = (Stage) btnMetricas.getScene().getWindow();

            Stage metricasStage = new Stage();
            metricasStage.setTitle("Panel de Métricas");
            metricasStage.setScene(new Scene(root,600,440));
            metricasStage.initOwner(adminStage);

            metricasStage.setOnCloseRequest(closeEvent -> {
                try {
                    adminStage.show();
                } catch (Exception e) {
                    System.out.println("Error al mostrar la ventana del administrador: " + e.getMessage());
                }
            });

            adminStage.hide();

            metricasStage.show();

        } catch (IOException e) {
            mostrarMensaje("Error cargando panelMetricas.fxml: " + e.getMessage(),true);
            System.out.println("Error cargando panelMetricas.fxml: " + e.getMessage());
        }
    }

    private void configurarComboReportes(){
        cmbGenerarReportes.getItems().addAll(
                "Reporte de Envios Same-Day",
                "Envios por Repartidor",
                "Reporte Servicios Adicionales"
        );

        cmbGenerarReportes.setOnAction(event -> {
            String opcion = cmbGenerarReportes.getSelectionModel().getSelectedItem();

            if (opcion == null) return;

            switch (opcion) {
                case "Reporte de Envios Same-Day" ->
                        mostrarReporteEntregas();

                case "Envios por Repartidor" ->
                        mostrarReporteEnviosPorRepartidor();

                case "Reporte Servicios Adicionales" ->
                        mostrarReportePorServiciosAdicionales();
            }
        });
    }

    @FXML
    void mostrarReporteEntregas() {
        List<EnvioDTO> fuente = listaEnviosFiltrados.isEmpty()
                ? listaEnvios
                : listaEnviosFiltrados;

        try {
            List<String> lineas = fuente.stream()
                    .map(this::toCsvLineaEnvio)
                    .collect(Collectors.toList());

            int totalEnvios = fuente.size();
            double totalCosto = fuente.stream()
                    .mapToDouble(EnvioDTO::getCosto)
                    .sum();

            String totalCostoCOP = cop.format(totalCosto);

            Path destino = Paths.get(
                    System.getProperty("user.home"),
                    "reporte_envios.csv"
            );

            String contenido =
                    "ID;Origen;Destino;Costo;FechaCreacion;FechaEntrega;Estado;EstadoPago\n"
                            + String.join("\n", lineas)
                            + "\n\n"                     // Línea en blanco
                            + "TOTAL ENVÍOS:;" + totalEnvios + "\n"
                            + "TOTAL COSTO ENVIOS:;" + totalCostoCOP + "\n";

            // BOM para Excel
            String contenidoUTF8 = "\uFEFF" + contenido;

            Files.write(
                    destino,
                    contenidoUTF8.getBytes(java.nio.charset.StandardCharsets.UTF_8)
            );

            mostrarMensaje("Reporte de envíos exportado en: " + destino, false);

            File archivo = destino.toFile();
            if (archivo.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }

        } catch (Exception e) {
            mostrarMensaje("Error exportando reporte de envíos: " + e.getMessage(), true);
        }
    }

    @FXML
    void mostrarReporteEnviosPorRepartidor() {

        List<EnvioDTO> fuente = listaEnviosFiltrados.isEmpty()
                ? listaEnvios
                : listaEnviosFiltrados;

        try{
            Map<String, List<EnvioDTO>> agrupados = fuente.stream()
                    .sorted(Comparator.comparing(
                            e -> e.getNombreRepartidor() == null ? "SIN REPARTIDOR" : e.getNombreRepartidor(),
                            String::compareToIgnoreCase
                    ))
                    .collect(Collectors.groupingBy(
                            e -> e.getNombreRepartidor() == null ? "SIN REPARTIDOR" : e.getNombreRepartidor(),
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            StringBuilder sb = new StringBuilder();
            sb.append("REPORTE DE ENVIOS POR REPARTIDOR\n\n");

            double totalGeneral = 0;

            for (Map.Entry<String, List<EnvioDTO>> entry : agrupados.entrySet()) {

                String nombreRepartidor = entry.getKey();
                List<EnvioDTO> envios = entry.getValue();

                sb.append("REPARTIDOR: ").append(nombreRepartidor).append("\n");
                sb.append("ID;Origen;Destino;Costo;FechaCreacion;FechaEntrega;Estado;EstadoPago\n");

                double totalRepartidor = 0;

                for (EnvioDTO e : envios) {
                    sb.append(toCsvLineaEnvio(e)).append("\n");
                    totalRepartidor += e.getCosto();
                }

                totalGeneral += totalRepartidor;

                sb.append("TOTAL COSTO ").append(nombreRepartidor).append(";")
                        .append(cop.format(totalRepartidor))
                        .append("\n\n");
            }

            sb.append("TOTAL GENERAL DE COSTOS;")
                    .append(cop.format(totalGeneral))
                    .append("\n");

            String contenidoUTF8 = "\uFEFF" + sb.toString();

            Path destino = Paths.get(
                    System.getProperty("user.home"),
                    "reporte_envios_por_repartidor.csv"
            );

            Files.write(destino, contenidoUTF8.getBytes(StandardCharsets.UTF_8));

            mostrarMensaje("Reporte de envíos por repartidor exportado en: " + destino, false);

            File archivo = destino.toFile();
            if (archivo.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }

        } catch (Exception e) {
            mostrarMensaje("Error exportando reporte: " + e.getMessage(), true);
        }
    }

    @FXML
    void mostrarReportePorServiciosAdicionales() {

        List<EnvioDTO> fuente = listaEnviosFiltrados.isEmpty()
                ? listaEnvios
                : listaEnviosFiltrados;
        try {
            Map<String, List<EnvioDTO>> porServicio = new LinkedHashMap<>();
            porServicio.put("ENVIOS CON SEGURO ADICIONAL", new ArrayList<>());
            porServicio.put("ENVIOS FRÁGILES", new ArrayList<>());
            porServicio.put("ENVIOS CON FIRMA", new ArrayList<>());
            porServicio.put("ENVIOS CON PRIORIDAD", new ArrayList<>());

            for (EnvioDTO envio : fuente) {
                if (envio.isSeguro()) {
                    porServicio.get("ENVIOS CON SEGURO ADICIONAL").add(envio);
                }
                if (envio.isFragil()) {
                    porServicio.get("ENVIOS FRÁGILES").add(envio);
                }
                if (envio.isFirma()) {
                    porServicio.get("ENVIOS CON FIRMA").add(envio);
                }
                if (envio.isPrioridad()) {
                    porServicio.get("ENVIOS CON PRIORIDAD").add(envio);
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("REPORTE DE ENVIOS POR SERVICIOS ADICIONALES\n");

            for (Map.Entry<String, List<EnvioDTO>> entry : porServicio.entrySet()) {
                String titulo = entry.getKey();
                List<EnvioDTO> lista = entry.getValue();
                sb.append("\n\n=== ").append(titulo).append(" ===\n");
                sb.append("ID;Origen;Destino;Costo;FechaCreacion;FechaEntrega;Estado;EstadoPago\n");

                for (EnvioDTO e : lista) {
                    sb.append(toCsvLineaEnvio(e)).append("\n");
                }
            }

            String contenidoUTF8 = "\uFEFF" + sb;

            Path destino = Paths.get(
                    System.getProperty("user.home"),
                    "reporte_envios_por_servicio.csv"
            );

            Files.write(
                    destino,
                    contenidoUTF8.getBytes(java.nio.charset.StandardCharsets.UTF_8)
            );

            mostrarMensaje("Reporte por servicios adicionales exportado en: " + destino, false);

            File archivo = destino.toFile();
            if (archivo.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }

        } catch (Exception e) {
            mostrarMensaje("Error exportando reporte de servicios adicionales: " + e.getMessage(), true);
        }
    }

    private String toCsvLineaEnvio(EnvioDTO e) {

        return String.join(";",
                csv(e.getId()),
                csv(e.getOrigen() != null ? e.getOrigen().getCiudad() : ""),
                csv(e.getDestino() != null ? e.getDestino().getCiudad() : ""),
                csv(e.getCosto()),
                csv(e.getFechaCreacion() != null ? e.getFechaCreacion().format(formatter) : ""),
                csv(e.getFechaEntrega() != null ? e.getFechaEntrega().format(formatter) : ""),
                csv(e.getEstado()),
                csv(e.getEstadoPago())
        );
    }

    private String csv(Object o) {
        if (o == null) return "\"\"";
        String v = String.valueOf(o);
        v = v.replace("\"", "\"\"");
        return "\"" + v + "\"";
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
            mostrarMensaje("Error: " + e.getMessage(), true);
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
            mostrarMensaje("Error: " + e.getMessage(), true);
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
        cmbGenerarReportes.getSelectionModel().clearSelection();

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