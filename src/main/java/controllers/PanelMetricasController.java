package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import models.DTO.EnvioDTO;
import service.facade.AdminFacade;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PanelMetricasController {

    @FXML
    private VBox container;
    private AdminFacade adminFacade;
    private List<EnvioDTO> envios;

    @FXML
    public void initialize() {
        adminFacade = new AdminFacade();
        envios = adminFacade.obtenerTodosLosEnvios();
        container.getChildren().addAll(
                generarGraficoPromedios(envios),
                diagramaTortaServiciosAdicionales(envios),
                buildIngresosChart(envios)
        );
    }

    public LineChart<String, Number> generarGraficoPromedios(List<EnvioDTO> envios) {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Promedio días de entrega por mes");

        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Promedio días de entrega por mes");

        Map<String, List<Long>> tiemposPorMes = new HashMap<>();
        DateTimeFormatter mesFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault());

        for (EnvioDTO e : envios) {
            if (e.getFechaEntrega() != null && e.getFechaCreacion() != null) {

                String mes = e.getFechaCreacion().format(mesFormatter);
                long dias = Duration.between(e.getFechaCreacion(), e.getFechaEntrega()).toDays();

                tiemposPorMes
                        .computeIfAbsent(mes, k -> new ArrayList<>())
                        .add(dias);
            }
        }

        for (Map.Entry<String, List<Long>> entry : tiemposPorMes.entrySet()) {

            String mes = entry.getKey();
            List<Long> diasList = entry.getValue();

            double promedio = diasList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0);

            String etiquetaEjeX = mes + " (" + String.format("%.1f", promedio) + ")";

            XYChart.Data<String, Number> punto = new XYChart.Data<>(etiquetaEjeX, promedio);
            data.getData().add(punto);
        }

        chart.getData().add(data);
        return chart;
    }

    private PieChart diagramaTortaServiciosAdicionales(List<EnvioDTO> envios) {
        PieChart chart = new PieChart();
        chart.setTitle("Servicios Adicionales Más Usados");

        long countSeguro = envios.stream().filter(EnvioDTO::isSeguro).count();
        long countFragil = envios.stream().filter(EnvioDTO::isFragil).count();
        long countFirma = envios.stream().filter(EnvioDTO::isFirma).count();
        long countPrioridad = envios.stream().filter(EnvioDTO::isPrioridad).count();

        long total = countSeguro + countFragil + countFirma + countPrioridad;

        PieChart.Data d1 = new PieChart.Data("Seguro", countSeguro);
        PieChart.Data d2 = new PieChart.Data("Frágil", countFragil);
        PieChart.Data d3 = new PieChart.Data("Firma", countFirma);
        PieChart.Data d4 = new PieChart.Data("Prioridad", countPrioridad);

        chart.getData().addAll(d1, d2, d3, d4);

        Platform.runLater(() -> {
            if (total == 0) {
                for (PieChart.Data data : chart.getData()) {
                    data.setName(data.getName() + " (0%)");
                }
                return;
            }

            for (PieChart.Data data : chart.getData()) {
                double porcentaje = (data.getPieValue() / (double) total) * 100;
                data.setName(data.getName() + " (" + String.format("%.1f%%", porcentaje) + ")");
            }
        });

        return chart;
    }

    private BarChart<String, Number> buildIngresosChart(List<EnvioDTO> envios) {
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(x, y);
        chart.setTitle("Ingresos Totales por Mes");

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ingresos");

        Map<String, Double> ingresosPorMes = new HashMap<>();
        DateTimeFormatter mesFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault());
        NumberFormat formatoPesos = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        for (EnvioDTO e : envios) {
            if (e.getFechaCreacion() != null) {
                String mes = e.getFechaCreacion().format(mesFormatter);
                ingresosPorMes.merge(mes, e.getCosto(), Double::sum);
            }
        }

        List<String> mesesOrdenados = envios.stream()
                .filter(e -> e.getFechaCreacion() != null)
                .map(e -> e.getFechaCreacion().getMonthValue() + "-" +
                        e.getFechaCreacion().format(mesFormatter))
                .distinct()
                .sorted(Comparator.comparingInt(s -> Integer.parseInt(s.split("-")[0])))
                .map(s -> s.split("-")[1])
                .toList();

        for (String mes : mesesOrdenados) {
            double total = ingresosPorMes.getOrDefault(mes, 0.0);

            String etiqueta = mes + "\n" + formatoPesos.format(total);

            serie.getData().add(new XYChart.Data<>(etiqueta, total));
        }

        chart.getData().add(serie);
        return chart;
    }
}
