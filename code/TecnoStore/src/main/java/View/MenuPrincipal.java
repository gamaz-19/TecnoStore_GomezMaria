package View;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner scanner;
    private MenuCelulares menuCelulares;
    private MenuClientes menuClientes;
    private MenuVentas menuVentas;
    private MenuReportes menuReportes;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.menuCelulares = new MenuCelulares(scanner);
        this.menuClientes = new MenuClientes(scanner);
        this.menuVentas = new MenuVentas(scanner);
        this.menuReportes = new MenuReportes(scanner);
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("--- Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        menuCelulares.mostrar();
                        break;
                    case 2:
                        menuClientes.mostrar();
                        break;
                    case 3:
                        menuVentas.mostrar();
                        break;
                    case 4:
                        menuReportes.mostrar();
                        break;
                    case 5:
                        salir = true;
                        System.out.println("--- ¡Gracias por usar TecnoStore! ---");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (SQLException e) {
                System.err.println("Error de base de datos: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("""
                       *****************************************************
                                   TECNOSTORE - MENU PRINCIPAL
                       *****************************************************
                       1. Gestion de Celulares
                       2. Gestion de Clientes
                       3. Registrar Venta
                       4. Ver Reportes
                       5. Salir
                       *****************************************************
                       """);
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
