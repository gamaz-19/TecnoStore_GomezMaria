package Persistence;

import Controller.ConecctionDB;
import Model.Cliente;
import Model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaCRUD {

    //DEpnde de cliente
    private Connection connection;
    private ClienteCRUD clienteCRUD;

    public VentaCRUD() {
        this.connection = ConecctionDB.getInstance().conectar();
        this.clienteCRUD = new ClienteCRUD();
    }

    // C
    public boolean insertarVenta(Venta venta) {

        String sql = "insert into venta (id_cliente, fecha, total) values (?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, venta.getCliente().getId());
            stmt.setString(2, venta.getFecha());
            stmt.setDouble(3, venta.getTotal());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    venta.setId(rs.getInt(1));
                }
                System.out.println("-- Venta insertada correctamente --");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    // R por ID
    public Venta obtenerPorId(int id) {

        String sql = "select * from venta where id = ?";
        Venta venta = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = clienteCRUD.obtenerPorId(rs.getInt("id_cliente"));

                if (cliente != null) {
                    venta = new Venta(
                            rs.getInt("id"),
                            rs.getString("fecha"),
                            rs.getDouble("total"),
                            cliente,
                            new ArrayList<>()
                    );

                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return venta;
    }

    // R todos
    public List<Venta> obtenerTodos() {

        String sql = "select * from venta";
        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = clienteCRUD.obtenerPorId(rs.getInt("id_cliente"));

                if (cliente != null) {
                     Venta venta = new Venta(
                            rs.getInt("id"),
                            rs.getString("fecha"),
                            rs.getDouble("total"),
                            cliente,
                            new ArrayList<>()
                    );
                     ventas.add(venta);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return ventas;
    }

    // D
    public boolean eliminarVenta(int id) {

        String sql = "delete from venta where id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            if (stmt.executeUpdate() > 0) {
                System.out.println("-- Venta eliminada correctamente --");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }
}
