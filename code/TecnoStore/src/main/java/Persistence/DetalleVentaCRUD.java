package Persistence;

import Controller.ConecctionDB;
import Model.Celular;
import Model.Detalle_venta;
import Model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaCRUD {

    private Connection connection;
    private VentaCRUD ventaCRUD;
    private CelularCRUD celularCRUD;

    public DetalleVentaCRUD() {
        this.connection = ConecctionDB.getInstance().conectar();
        this.ventaCRUD = new VentaCRUD();
        this.celularCRUD = new CelularCRUD();
    }

    // C - insertar detalle de venta
    public boolean insertarDetalleVenta(Detalle_venta detalle) {

        String sql = "insert into detalle_venta (id_venta, id_celular, cantidad, subtotal) values (?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, detalle.getVenta().getId());
            stmt.setInt(2, detalle.getCelular().getId());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setDouble(4, detalle.getSubtotal());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    detalle.setId(rs.getInt(1));
                }
                System.out.println("-- Detalle de venta insertado correctamente --");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    // R - obtener detalle por ID
    public Detalle_venta obtenerPorId(int id) {

        String sql = "select * from detalle_venta where id = ?";
        Detalle_venta detalle = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Venta venta = ventaCRUD.obtenerPorId(rs.getInt("id_venta"));
                Celular celular = celularCRUD.obtenerPorId(rs.getInt("id_celular"));

                if (venta != null && celular != null) {
                    detalle = new Detalle_venta(
                            rs.getInt("id"),
                            rs.getInt("cantidad"),
                            celular,
                            venta,
                            rs.getDouble("subtotal")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return detalle;
    }

    // R - obtener todos los detalles
    public List<Detalle_venta> obtenerTodos() {

        String sql = "select * from detalle_venta";
        List<Detalle_venta> detalles = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venta venta = ventaCRUD.obtenerPorId(rs.getInt("id_venta"));
                Celular celular = celularCRUD.obtenerPorId(rs.getInt("id_celular"));

                if (venta != null && celular != null) {
                    Detalle_venta detalle = new Detalle_venta(
                            rs.getInt("id"),
                            rs.getInt("cantidad"),
                            celular,
                            venta,
                            rs.getDouble("subtotal")
                    );
                    detalles.add(detalle);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return detalles;
    }

    // D - eliminar detalle de venta
    public boolean eliminarDetalleVenta(int id) {

        String sql = "delete from detalle_venta where id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            if (stmt.executeUpdate() > 0) {
                System.out.println("-- Detalle de venta eliminado correctamente --");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }
}
