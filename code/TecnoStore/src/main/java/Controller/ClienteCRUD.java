package Controller;

import Model.Cliente;
import Model.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteCRUD {

    //Coneccion con base de datos
    private Connection connection;
    //Conectando con persona
    private PersonaCRUD personaCRUD;

    // Constructor
    public ClienteCRUD() {
        this.connection = ConecctionDB.getInstance().conectar();
        this.personaCRUD = new PersonaCRUD();
    }

    // C - create - Insertar nuevo cliente (primero se inserta como persona)
    public boolean insertarCliente(Cliente cliente) {

        // INsertar/ crear persona primerp
        Persona persona = cliente.getPersona();
        boolean personaInsertada = personaCRUD.insertarPersona(persona);

        if (!personaInsertada) {
            System.err.println("No se pudo insertar la persona");
            return false;
        }

        // Crear el cliente con el id de la persona que se creo antes
        String sql = "insert into cliente (id_persona) values (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, persona.getId());
            int insertar = stmt.executeUpdate();

            if (insertar > 0) {
                cliente.setId(persona.getId());
                System.out.println(" Cliente insertado correctamente ");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar cliente");
        }

        return false;
    }

    // R - read - (leer clientes)
    // Pir ID
    public Cliente obtenerPorId(int id) {
        String sql = "select * from cliente where id_persona = ?";
        Cliente cliente = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Obtener la persona asociada al id creado
                Persona persona = personaCRUD.obtenerPorId(rs.getInt("id_persona"));

                if (persona != null) {
                    cliente = new Cliente(persona.getId(), persona);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: ");

        }

        return cliente;
    }

    // Obtener todos los clientes
    public List<Cliente> obtenerTodos() {
        String sql = "select * from cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Obtener la persona asociada al iD
                Persona persona = personaCRUD.obtenerPorId(rs.getInt("id_persona"));

                if (persona != null) {
                    Cliente cliente = new Cliente(persona.getId(), persona);
                    clientes.add(cliente);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: ");

        }

        return clientes;
    }

    // U - update - Actualizar cliente (actualiza los datos de la persona)
    public boolean actualizarCliente(Cliente cliente) {

        // se actualiza la persona
        Persona persona = cliente.getPersona();

        if (persona == null) {
            System.err.println("El cliente no tiene persona asociada");
            return false;
        }

        boolean actualizado = personaCRUD.actualizarPersona(persona);

        if (actualizado) {
            System.out.println("Cliente actualizado correctamente: ");
        }

        return actualizado;
    }

    // D - delete - Eliminar cliente
    public boolean eliminarCliente(int id) {

        // Se elimina primero de cliente para luego eliminar la persona
        String sql = "delete from cliente where id_persona = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int eliminar = stmt.executeUpdate();

            if (eliminar > 0) {
                // Despues se elimina la persona
                personaCRUD.eliminarPersona(id);
                System.out.println(" Cliente eliminado correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());

        }

        return false;
    }

}
