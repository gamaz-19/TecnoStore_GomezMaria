package Utilities;

import Model.Celular;
import Model.Detalle_venta;
import Model.Venta;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportUtils {

    /* Obtener celulares con stock bajo (menor a 5 unidades) Usa Stream API
     */
    public static List<Celular> celularesStockBajo(List<Celular> celulares) {
        return celulares.stream()
                .filter(c -> c.getStock() < 5)
                .sorted(Comparator.comparingInt(Celular::getStock))
                .collect(Collectors.toList());
    }

    /* Obtener Top 3 celulares más vendidos Usa Stream API
     */
    public static List<Map.Entry<String, Integer>> top3MasVendidos(List<Venta> ventas) {

        // Agrupar por modelo y sumar cantidades
        Map<String, Integer> ventasPorModelo = ventas.stream()
                .flatMap(venta -> venta.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        detalle -> detalle.getCelular().getModelo(),
                        Collectors.summingInt(Detalle_venta::getCantidad)
                ));

        // Ordenar y tomar top 3
        return ventasPorModelo.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /* Calcular ventas totales de un mes específico Usa Stream API
     */
    public static double ventasTotalesPorMes(List<Venta> ventas, String mes) {
        // mes debe estar en formato "2025-01" para enero 2025
        return ventas.stream()
                .filter(venta -> venta.getFecha().startsWith(mes))
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    /* Contar total de ventas
     */
    public static int contarVentas(List<Venta> ventas) {
        return (int) ventas.stream().count();
    }

    /* Obtener venta con mayor monto
     */
    public static Venta ventaConMayorMonto(List<Venta> ventas) {
        return ventas.stream()
                .max(Comparator.comparingDouble(Venta::getTotal))
                .orElse(null);
    }

    /* Calcular promedio de ventas
     */
    public static double promedioVentas(List<Venta> ventas) {
        return ventas.stream()
                .mapToDouble(Venta::getTotal)
                .average()
                .orElse(0.0);
    }

    /* Generar resumen de reportes en formato texto
     */
    public static String generarResumenReportes(List<Celular> celulares, List<Venta> ventas) {
        StringBuilder reporte = new StringBuilder();

        reporte.append("╔══════════════════════════════════════════╗\n");
        reporte.append("║      REPORTE GENERAL - TECNOSTORE       ║\n");
        reporte.append("╚══════════════════════════════════════════╝\n\n");

        // Stock bajo
        reporte.append("📊 CELULARES CON STOCK BAJO (< 5 unidades):\n");
        reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        List<Celular> stockBajo = celularesStockBajo(celulares);
        if (stockBajo.isEmpty()) {
            reporte.append("   ✅ Todos los celulares tienen stock suficiente\n");
        } else {
            stockBajo.forEach(c
                    -> reporte.append(String.format("   • %s - Stock: %d unidades\n",
                            c.getModelo(), c.getStock()))
            );
        }
        reporte.append("\n");

        // Top 3 más vendidos
        reporte.append("🏆 TOP 3 CELULARES MÁS VENDIDOS:\n");
        reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        List<Map.Entry<String, Integer>> top3 = top3MasVendidos(ventas);
        int posicion = 1;
        for (Map.Entry<String, Integer> entry : top3) {
            reporte.append(String.format("   %d. %s - %d unidades vendidas\n",
                    posicion++, entry.getKey(), entry.getValue()));
        }
        reporte.append("\n");

        // Estadísticas generales
        reporte.append("📈 ESTADÍSTICAS GENERALES:\n");
        reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        reporte.append(String.format("   Total de ventas: %d\n", contarVentas(ventas)));
        reporte.append(String.format("   Promedio por venta: $%.2f\n", promedioVentas(ventas)));

        Venta ventaMayor = ventaConMayorMonto(ventas);
        if (ventaMayor != null) {
            reporte.append(String.format("   Venta más alta: $%.2f\n", ventaMayor.getTotal()));
        }

        return reporte.toString();
    }

}
