package View;

import Persistence.CelularCRUD;
import Persistence.VentaCRUD;
import Model.Celular;
import Model.Venta;
import Utilities.UtilsFile;
import Utilities.ReportUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuReportes {

    private Scanner scanner;
    private CelularCRUD celularCRUD;
    private VentaCRUD ventaCRUD;

    public MenuReportes(Scanner scanner) {
        this.scanner = scanner;
        this.celularCRUD = new CelularCRUD();
        this.ventaCRUD = new VentaCRUD();
    }

    public void mostrar() throws SQLException {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│          REPORTES                  │");
        System.out.println("└────────────────────────────────────┘");
        System.out.println("  1. Stock bajo");
        System.out.println("  2. Top 3 más vendidos");
        System.out.println("  3. Ventas del mes");
        System.out.println("  4. Generar archivo reporte_ventas.txt");
        System.out.println("  5. Resumen general");
        System.out.println("  6. Volver");
        System.out.println("────────────────────────────────────");

        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                reporteStockBajo();
                break;
            case 2:
                reporteTop3();
                break;
            case 3:
                reporteVentasMes();
                break;
            case 4:
                generarArchivoReporte();
                break;
            case 5:
                reporteResumenGeneral();
                break;
            case 6:
                break;
            default:
                System.out.println("❌ Opción inválida");
        }
    }

    private void reporteStockBajo() throws SQLException {
        List<Celular> celulares = celularCRUD.obtenerTodos();
        List<Celular> stockBajo = ReportUtils.celularesStockBajo(celulares);

        System.out.println("\n📊 CELULARES CON STOCK BAJO (< 5 unidades):");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (stockBajo.isEmpty()) {
            System.out.println("✅ Todos los celulares tienen stock suficiente\n");
        } else {
            for (Celular c : stockBajo) {
                System.out.printf("• %s - Stock: %d unidades\n", c.getModelo(), c.getStock());
            }
            System.out.println();
        }
    }

    private void reporteTop3() throws SQLException {
        List<Venta> ventas = ventaCRUD.obtenerTodos();
        var top3 = ReportUtils.top3MasVendidos(ventas);

        System.out.println("\n🏆 TOP 3 CELULARES MÁS VENDIDOS:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int posicion = 1;
        for (var entry : top3) {
            System.out.printf("%d. %s - %d unidades vendidas\n",
                    posicion++, entry.getKey(), entry.getValue());
        }
        System.out.println();
    }

    private void reporteVentasMes() throws SQLException {
        String mes = leerTexto("Ingrese el mes (formato YYYY-MM, ej: 2025-02): ");

        List<Venta> ventas = ventaCRUD.obtenerTodos();
        double totalMes = ReportUtils.ventasTotalesPorMes(ventas, mes);

        System.out.println("\n📈 VENTAS DEL MES " + mes + ":");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("Total: $%.2f\n\n", totalMes);
    }

    private void generarArchivoReporte() throws SQLException {
        List<Venta> ventas = ventaCRUD.obtenerTodos();
        String rutaArchivo = "reporte_ventas.txt";

        System.out.println("\n📄 Generando archivo...");
        boolean generado = UtilsFile.generarReporteVentas(ventas, rutaArchivo);

        if (generado) {
            System.out.println("✅ Archivo generado: " + rutaArchivo + "\n");
        }
    }

    private void reporteResumenGeneral() throws SQLException {
        List<Celular> celulares = celularCRUD.obtenerTodos();
        List<Venta> ventas = ventaCRUD.obtenerTodos();

        String resumen = ReportUtils.generarResumenReportes(celulares, ventas);
        System.out.println("\n" + resumen);
    }

    // Utilidades
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("❌ Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}