package View;

import Model.Celular;
import Model.Venta;
import Persistence.CelularCRUD;
import Persistence.VentaCRUD;
import Utilities.ReportUtils;
import Utilities.UtilsFile;
import java.util.List;

public class Tests {

    public static void main(String[] args) {

        System.out.println("=== PROBANDO UTILIDADES ===\n");

        CelularCRUD celularCRUD = new CelularCRUD();
        VentaCRUD ventaCRUD = new VentaCRUD();

        // Obtener datos
        List<Celular> celulares = celularCRUD.obtenerTodos();
        List<Venta> ventas = ventaCRUD.obtenerTodos();

        // 1. Generar resumen de reportes
        System.out.println("--- RESUMEN DE REPORTES ---\n");
        String resumen = ReportUtils.generarResumenReportes(celulares, ventas);
        System.out.println(resumen);

        // 2. Generar archivo reporte_ventas.txt
        System.out.println("\n--- GENERAR ARCHIVO ---");
        String rutaArchivo = "reporte_ventas.txt";
        boolean generado = UtilsFile.generarReporteVentas(ventas, rutaArchivo);

        if (generado) {
            System.out.println("✅ Revisa el archivo: " + rutaArchivo);
        }
    }

}
