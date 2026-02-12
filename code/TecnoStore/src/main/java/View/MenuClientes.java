package View;

import Persistence.ClienteCRUD;
import Model.Cliente;
import Model.Persona;
import Utilities.Validator;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuClientes {

    private Scanner scanner;
    private ClienteCRUD clienteCRUD;

    public MenuClientes(Scanner scanner) {
        this.scanner = scanner;
        this.clienteCRUD = new ClienteCRUD();
    }

    public void mostrar() {

        System.out.println("""
                           *****************************************************
                                          GESTION DE CLIENTES
                           *****************************************************
                           1. Listar clientes
                           2. Agregar cliente
                           3. Actualizar cliente
                           4. Eliminar cliente
                           5. Volver
                           *****************************************************
                           """);

        int opcion = leerEntero("Opcion: ");

        switch (opcion) {
            case 1:
                listarClientes();
                break;
            case 2:
                agregarCliente();
                break;
            case 3:
                actualizarCliente();
                break;
            case 4:
                eliminarCliente();
                break;
            case 5:
                break;
            default:
                System.out.println("Opción inválida");
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteCRUD.obtenerTodos();

        if (clientes.isEmpty()) {
            System.out.println("-- No hay clientes registrados --");
            return;
        }

        System.out.println("*****************************************************");
        System.out.println("\n**************** CLINETES ***************************");
        System.out.printf("* %-4s * %-20s * %-15s * %-20s *%n",
                "ID", "NOMBRE", "IDENTIFICACION", "CORREO");
        System.out.println("*****************************************************");

        for (Cliente c : clientes) {
            Persona p = c.getPersona();
            System.out.printf("* %-4d * %-20s * %-15s * %-20s *%n",
                    c.getId(),
                    p.getNombre(),
                    p.getIdentificacion(),
                    p.getCorreo()
            );
        }
        System.out.println("*****************************************************");
    }

    private void agregarCliente() {
        System.out.println("--- Agregar Cliente ---");

        String nombre = leerTexto("Nombre: ");
        if (!Validator.validarNombre(nombre)) {
            System.out.println("El nombre debe tener al menos 3 caracteres");
            return;
        }

        String identificacion = leerTexto("Identificacion: ");
        if (!Validator.validarIdentificacion(identificacion)) {
            System.out.println("La identificacion debe tener entre 6 y 13 caracteres");
            return;
        }

        String correo = leerTexto("Correo: ");
        if (!Validator.validarCorreo(correo)) {
            System.out.println("Correo invalido");
            return;
        }

        String telefono = leerTexto("Telefono: ");
        if (!Validator.validarTelefono(telefono)) {
            System.out.println("El telefono debe tener 10 digitos");
            return;
        }

        Persona persona = new Persona(nombre, identificacion, correo, telefono);
        Cliente cliente = new Cliente(persona);
        clienteCRUD.insertarCliente(cliente);
    }

    private void actualizarCliente() {
        listarClientes();
        int id = leerEntero("ID del cliente a actualizar: ");

        Cliente cliente = clienteCRUD.obtenerPorId(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return;
        }

        Persona persona = cliente.getPersona();
        System.out.println("--- (Presiona ENTER para mantener el valor actual)");

        String nuevoNombre = leerTextoOpcional("Nuevo nombre [" + persona.getNombre() + "]: ");
        if (!nuevoNombre.isEmpty() && Validator.validarNombre(nuevoNombre)) {
            persona.setNombre(nuevoNombre);
        }

        String nuevoCorreo = leerTextoOpcional("Nuevo correo [" + persona.getCorreo() + "]: ");
        if (!nuevoCorreo.isEmpty() && Validator.validarCorreo(nuevoCorreo)) {
            persona.setCorreo(nuevoCorreo);
        }

        String nuevoTelefono = leerTextoOpcional("Nuevo telefono [" + persona.getTelefono() + "]: ");
        if (!nuevoTelefono.isEmpty() && Validator.validarTelefono(nuevoTelefono)) {
            persona.setTelefono(nuevoTelefono);
        }

        clienteCRUD.actualizarCliente(cliente);
    }

    private void eliminarCliente() {
        listarClientes();
        int id = leerEntero("ID del cliente a eliminar: ");

        String confirmacion = leerTexto("¿Esta seguro? (S/N): ");
        if (confirmacion.equalsIgnoreCase("S")) {
            clienteCRUD.eliminarCliente(id);
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

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}
