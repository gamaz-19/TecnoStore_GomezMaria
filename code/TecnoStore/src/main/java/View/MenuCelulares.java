 package View;

import Persistence.CelularCRUD;
import Persistence.MarcaCRUD;
import Model.Celular;
import Model.Marca;
import Utilities.Validator;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuCelulares {

    private Scanner scanner;
    private CelularCRUD celularCRUD;
    private MarcaCRUD marcaCRUD;

    public MenuCelulares(Scanner scanner) {
        this.scanner = scanner;
        this.celularCRUD = new CelularCRUD();
        this.marcaCRUD = new MarcaCRUD();
    }

    public void mostrar() throws SQLException {
        System.out.println("""
                   *****************************************************
                                  GESTION DE CELULARES
                   *****************************************************
                   1. Listar celulares
                   2. Agregar celular
                   3. Actualizar celular
                   4. Eliminar celular
                   5. Volver
                   *****************************************************
                   """);
        int opcion = leerEntero("Opción: ");

        switch (opcion) {
            case 1:
                listarCelulares();
                break;
            case 2:
                agregarCelular();
                break;
            case 3:
                actualizarCelular();
                break;
            case 4:
                eliminarCelular();
                break;
            case 5:
                break;
            default:
                System.out.println("Opción inválida");
        }
    }

    private void listarCelulares() {
        List<Celular> celulares = celularCRUD.obtenerTodos();

        if (celulares.isEmpty()) {
            System.out.println(" --- No hay celulares registrados ---");
            return;
        }

        System.out.println("""
                   *****************************************************
                                  LISTADO DE CELULARES
                   *****************************************************
                   """);
        System.out.printf("│ %-4s │ %-15s │ %-12s │ %-8s │ %-6s │ %-8s │%n",
                "ID", "MODELO", "MARCA", "PRECIO", "STOCK", "GAMA");
        System.out.println("*****************************************************");

        for (Celular c : celulares) {
            System.out.printf("│ %-4d │ %-15s │ %-12s │ $%-7.2f │ %-6d │ %-8s │%n",
                    c.getId(),
                    c.getModelo(),
                    c.getMarca().getNombre(),
                    c.getPrecio(),
                    c.getStock(),
                    c.getGama()
            );
        }
        System.out.println("*****************************************************");
    }

    private void agregarCelular() {
        System.out.println("--- Agregar Celular ---");

        // Listar marcas
        List<Marca> marcas = marcaCRUD.obtenerTodas();
        System.out.println("--- Marcas disponibles:");
        for (Marca m : marcas) {
            System.out.println("  " + m.getId() + ". " + m.getNombre());
        }

        int idMarca = leerEntero("ID de la marca: ");
        Marca marca = marcaCRUD.obtenerPorId(idMarca);

        if (marca == null) {
            System.out.println("Marca no encontrada");
            return;
        }

        String modelo = leerTexto("Modelo: ");
        double precio = leerDouble("Precio: ");

        if (!Validator.validarPrecio(precio)) {
            System.out.println("El precio debe ser positivo");
            return;
        }

        int stock = leerEntero("Stock: ");

        if (!Validator.validarStock(stock)) {
            System.out.println("El stock debe ser positivo o cero");
            return;
        }

        System.out.println("--- Sistema Operativo:");
        System.out.println("  1. IOS");
        System.out.println("  2. ANDROID");
        int opcionSO = leerEntero("Opción: ");
        Celular.SistemaOperativo so = (opcionSO == 1) ? Celular.SistemaOperativo.IOS : Celular.SistemaOperativo.ANDROID;

        System.out.println("--- Gama:");
        System.out.println("  1. ALTA");
        System.out.println("  2. MEDIA");
        System.out.println("  3. BAJA");
        int opcionGama = leerEntero("Opción: ");
        Celular.Gama gm = (opcionGama == 1) ? Celular.Gama.ALTA
                : (opcionGama == 2) ? Celular.Gama.MEDIA : Celular.Gama.BAJA;

        Celular celular = new Celular(stock, modelo, precio, marca, so, gm);
        celularCRUD.insertarCelular(celular);
    }

    private void actualizarCelular() {
        listarCelulares();
        int id = leerEntero("ID del celular a actualizar: ");

        Celular celular = celularCRUD.obtenerPorId(id);
        if (celular == null) {
            System.out.println("Celular no encontrado");
            return;
        }

        System.out.println("--- Datos actuales: " + celular);
        System.out.println("--- (Presiona ENTER para mantener el valor actual)");

        String nuevoModelo = leerTextoOpcional("Nuevo modelo [" + celular.getModelo() + "]: ");
        if (!nuevoModelo.isEmpty()) {
            celular.setModelo(nuevoModelo);
        }

        String precioStr = leerTextoOpcional("Nuevo precio [" + celular.getPrecio() + "]: ");
        if (!precioStr.isEmpty()) {
            double nuevoPrecio = Double.parseDouble(precioStr);
            if (Validator.validarPrecio(nuevoPrecio)) {
                celular.setPrecio(nuevoPrecio);
            }
        }

        String stockStr = leerTextoOpcional("Nuevo stock [" + celular.getStock() + "]: ");
        if (!stockStr.isEmpty()) {
            int nuevoStock = Integer.parseInt(stockStr);
            if (Validator.validarStock(nuevoStock)) {
                celular.setStock(nuevoStock);
            }
        }

        celularCRUD.actualizarCelular(celular);
    }

    private void eliminarCelular() {
        listarCelulares();
        int id = leerEntero("ID del celular a eliminar: ");

        String confirmacion = leerTexto("¿Esta seguro? (S/N): ");
        if (confirmacion.equalsIgnoreCase("S")) {
            celularCRUD.eliminarCelular(id);
        } else {
            System.out.println("Operacion cancelada");
        }
    }

    // Utilidades
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

    private double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Ingrese un numero valido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}
