package Utilities;

import Model.Detalle_venta;
import Model.Venta;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UtilsFile {
     /*Generar archivo reporte_ventas.txt con todas las ventas
     * Usa try-with-resources (requisito del proyecto)
     */
    public static boolean generarReporteVentas(List<Venta> ventas, String rutaArchivo) {
        
        // try-with-resources: cierra automáticamente el BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            
            // Encabezado
            writer.write("*****************************************************\n");
            writer.write("*           REPORTE DE VENTAS - TECNOSTORE              *\n");
            writer.write("*****************************************************\n\n");
            
            // Fecha de generación
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            writer.write("Fecha de generación: " + ahora.format(formatter) + "\n");
            writer.write("Total de ventas: " + ventas.size() + "\n\n");
            writer.write("*****************************************************\n\n");
            
            // Detalles de cada venta
            for (Venta venta : ventas) {
                writer.write("*****************************************************\n");
                writer.write(String.format("* VENTA #%d%s*\n", 
                    venta.getId(), 
                    " ".repeat(Math.max(0, 44 - String.valueOf(venta.getId()).length()))));
                writer.write("*****************************************************\n");
                writer.write(String.format("* Cliente: %-42s *\n", 
                    venta.getCliente().getPersona().getNombre()));
                writer.write(String.format("* Fecha: %-44s *\n", venta.getFecha()));
                writer.write("*****************************************************\n");
                writer.write("* PRODUCTOS:                                          *\n");
                
                // Detalles de la venta
                for (Detalle_venta detalle : venta.getDetalles()) {
                    String linea = String.format("│   • %s (x%d) - $%.2f",
                        detalle.getCelular().getModelo(),
                        detalle.getCantidad(),
                        detalle.getSubtotal()
                    );
                    // Rellenar con espacios
                    linea += " ".repeat(Math.max(0, 54 - linea.length())) + "│\n";
                    writer.write(linea);
                }
                
                writer.write("*****************************************************\n");
                writer.write(String.format("* TOTAL: $%-44.2f *\n", venta.getTotal()));
                writer.write("*****************************************************\n\n");
            }
            
            // Resumen final
            double totalGeneral = ventas.stream()
                    .mapToDouble(Venta::getTotal)
                    .sum();
            
            writer.write("*****************************************************n");
            writer.write(String.format("TOTAL GENERAL DE VENTAS: $%.2f\n", totalGeneral));
            writer.write("*****************************************************\n");
            
            System.out.println(rutaArchivo);
            return true;
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
