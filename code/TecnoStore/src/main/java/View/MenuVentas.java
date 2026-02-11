package View;

import Controller.gestionVentas;
import Persistence.CelularCRUD;
import Persistence.ClienteCRUD;
import Model.Celular;
import Model.Cliente;
import Model.Persona;
import Persistence.VentaCRUD;
import Utilities.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuVentas {

    private Scanner scanner;
    private gestionVentas gestorVentas;
    private ClienteCRUD clienteCRUD;
    private CelularCRUD celularCRUD;
    private VentaCRUD ventaCRUD;

    public MenuVentas(Scanner scanner) {
        this.scanner = scanner;
        this.clienteCRUD = new ClienteCRUD();
        this.celularCRUD = new CelularCRUD();
        this.ventaCRUD = new VentaCRUD();
        
        this.gestorVentas = new gestionVentas(
            this.ventaCRUD, 
            this.celularCRUD, 
            this.clienteCRUD
        );
    }

    public void mostrar() {
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.println("│        REGISTRAR NUEVA VENTA         │");
        System.out.println("└──────────────────────────────────────┘\n");

        // Seleccionar cliente
        listarClientes();
        int idCliente = leerEntero("ID del cliente: ");

        Cliente cliente = clienteCRUD.obtenerPorId(idCliente);
        if (cliente == null) {
            System.out.println("❌ Cliente no encontrado");
            pausar();
            return;
        }

        System.out.println("✅ Cliente: " + cliente.getPersona().getNombre() + "\n");

        // Agregar productos
        List<gestionVentas.ItemVenta> items = new ArrayList<>();
        boolean agregarMas = true;

        while (agregarMas) {
            listarCelulares();
            int idCelular = leerEntero("ID del celular: ");

            Celular celular = celularCRUD.obtenerPorId(idCelular);
            if (celular == null) {
                System.out.println("❌ Celular no encontrado");
                continue;
            }

            System.out.println("📦 Stock disponible: " + celular.getStock());
            System.out.println("💵 Precio: $" + celular.getPrecio());
            int cantidad = leerEntero("Cantidad: ");

            if (!Validator.validarCantidad(cantidad)) {
                System.out.println("❌ La cantidad debe ser positiva");
                continue;
            }

            if (cantidad > celular.getStock()) {
                System.out.println("❌ Stock insuficiente");
                continue;
            }

            items.add(new gestionVentas.ItemVenta(idCelular, cantidad));
            
            // Mostrar subtotal parcial
            double subtotal = celular.getPrecio() * cantidad;
            System.out.println("✅ Producto agregado - Subtotal: $" + String.format("%.2f", subtotal) + "\n");

            String continuar = leerTexto("¿Agregar otro producto? (S/N): ");
            agregarMas = continuar.equalsIgnoreCase("S");
        }

        if (items.isEmpty()) {
            System.out.println("❌ No se agregaron productos");
            pausar();
            return;
        }

        // Mostrar resumen antes de confirmar
        mostrarResumenVenta(cliente, items);

        // Confirmar venta
        String confirmacion = leerTexto("\n¿Confirmar venta? (S/N): ");

        if (confirmacion.equalsIgnoreCase("S")) {
            boolean exito = gestorVentas.registrarVenta(idCliente, items);
            
            if (exito) {
                System.out.println("\n✅ ¡VENTA REGISTRADA EXITOSAMENTE!\n");
            } else {
                System.out.println("\n❌ No se pudo completar la venta\n");
            }
        } else {
            System.out.println("\n❌ Venta cancelada\n");
        }
        
        pausar();
    }

    private void mostrarResumenVenta(Cliente cliente, List<gestionVentas.ItemVenta> items) {
        System.out.println("\n┌─────────────────────────────────────────────────┐");
        System.out.println("│              RESUMEN DE LA VENTA                │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ Cliente: " + cliente.getPersona().getNombre());
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│ Productos:                                      │");
        
        double subtotalGeneral = 0;
        
        for (gestionVentas.ItemVenta item : items) {
            Celular celular = celularCRUD.obtenerPorId(item.getIdCelular());
            double subtotal = celular.getPrecio() * item.getCantidad();
            subtotalGeneral += subtotal;
            
            System.out.printf("│   • %s (x%d) - $%.2f\n", 
                celular.getModelo(), 
                item.getCantidad(), 
                subtotal);
        }
        
        double iva = subtotalGeneral * 0.19;
        double total = subtotalGeneral * 1.19;
        
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf("│ Subtotal:           $%.2f\n", subtotalGeneral);
        System.out.printf("│ IVA (19%%):          $%.2f\n", iva);
        System.out.printf("│ TOTAL:              $%.2f\n", total);
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteCRUD.obtenerTodos();

        if (clientes.isEmpty()) {
            System.out.println("⚠️ No hay clientes registrados\n");
            return;
        }

        System.out.println("┌─── CLIENTES DISPONIBLES ───────────────────────┐");
        System.out.printf("│ %-4s │ %-25s │ %-15s │\n", "ID", "NOMBRE", "IDENTIFICACIÓN");
        System.out.println("├──────┼───────────────────────────┼─────────────────┤");
        
        for (Cliente c : clientes) {
            Persona p = c.getPersona();
            System.out.printf("│ %-4d │ %-25s │ %-15s │\n", 
                c.getId(), 
                truncar(p.getNombre(), 25), 
                p.getIdentificacion());
        }
        System.out.println("└──────┴───────────────────────────┴─────────────────┘\n");
    }

    private void listarCelulares() {
        List<Celular> celulares = celularCRUD.obtenerTodos();

        if (celulares.isEmpty()) {
            System.out.println("⚠️ No hay celulares disponibles\n");
            return;
        }

        System.out.println("\n┌─── CELULARES DISPONIBLES ──────────────────────────────────┐");
        System.out.printf("│ %-4s │ %-20s │ %-10s │ %-8s │ %-8s │\n", 
            "ID", "MODELO", "PRECIO", "STOCK", "MARCA");
        System.out.println("├──────┼──────────────────────┼────────────┼──────────┼──────────┤");

        for (Celular c : celulares) {
            System.out.printf("│ %-4d │ %-20s │ $%-9.2f │ %-8d │ %-8s │\n",
                c.getId(),
                truncar(c.getModelo(), 20),
                c.getPrecio(),
                c.getStock(),
                truncar(c.getMarca().getNombre(), 8)
            );
        }
        System.out.println("└──────┴──────────────────────┴────────────┴──────────┴──────────┘");
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
    
    private String truncar(String texto, int maxLength) {
        if (texto.length() <= maxLength) {
            return texto;
        }
        return texto.substring(0, maxLength - 3) + "...";
    }
    
    private void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}