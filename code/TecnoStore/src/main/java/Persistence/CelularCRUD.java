package Persistence;

import Controller.ConecctionDB;
import Persistence.MarcaCRUD;
import Model.Celular;
import Model.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//depende de marca
public class CelularCRUD {

    //Coneccion con base de datos
    private Connection connection;
    //Conectando con marca
    private MarcaCRUD marcaCRUD;

    // Constructor
    public CelularCRUD() {
        this.connection = ConecctionDB.getInstance().conectar();
        this.marcaCRUD = new MarcaCRUD();
    }

    // C - create - Insertar nuevo
    public boolean insertarCelular(Celular celular) {
        String sql = "insert into celular (id_marca, modelo, stock, sistemaOperativo, gama, precio) values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, celular.getMarca().getId());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getStock());
            stmt.setString(4, celular.getSistemaOperativo().name()); // Enum a String
            stmt.setString(5, celular.getGama().name()); // Enum a String
            stmt.setDouble(6, celular.getPrecio());

            int insertar = stmt.executeUpdate();

            if (insertar > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    celular.setId(generatedKeys.getInt(1));
                }
                System.out.println("Celular insertado correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    // R - read - leer celulares
    // Por ID
    public Celular obtenerPorId(int id) {
        String sql = "select * from celular where id = ?";
        Celular celular = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Obtener la marca asociada
                Marca marca = marcaCRUD.obtenerPorId(rs.getInt("id_marca"));

                celular = new Celular(
                        rs.getInt("id"),
                        rs.getInt("stock"),
                        rs.getString("modelo"),
                        rs.getDouble("precio"),
                        marca,
                        Celular.SistemaOperativo.valueOf(rs.getString("sistemaOperativo")),
                        Celular.Gama.valueOf(rs.getString("gama"))
                );
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }

        return celular;
    }

    // Obtener todos los celulares
    public List<Celular> obtenerTodos() {
        String sql = "select * from celular order by modelo";
        List<Celular> celulares = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Obtener la marca asociada
                Marca marca = marcaCRUD.obtenerPorId(rs.getInt("id_marca"));

                Celular celular = new Celular(
                        rs.getInt("id"),
                        rs.getInt("stock"),
                        rs.getString("modelo"),
                        rs.getDouble("precio"),
                        marca,
                        Celular.SistemaOperativo.valueOf(rs.getString("sistemaOperativo")),
                        Celular.Gama.valueOf(rs.getString("gama"))
                );
                celulares.add(celular);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return celulares;
    }

    // Obtener celulares por marca
    public List<Celular> obtenerPorMarca(int idMarca) {
        String sql = "select * from celular where id_marca = ? order by modelo";
        List<Celular> celulares = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idMarca);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Marca marca = marcaCRUD.obtenerPorId(rs.getInt("id_marca"));

                Celular celular = new Celular(
                        rs.getInt("id"),
                        rs.getInt("stock"),
                        rs.getString("modelo"),
                        rs.getDouble("precio"),
                        marca,
                        Celular.SistemaOperativo.valueOf(rs.getString("sistemaOperativo")),
                        Celular.Gama.valueOf(rs.getString("gama"))
                );
                celulares.add(celular);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return celulares;
    }

    // U - update - Actualizar celular
    public boolean actualizarCelular(Celular celular) {
        String sql = "update celular set id_marca = ?, modelo = ?, stock = ?, sistemaOperativo = ?, gama = ?, precio = ? where id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, celular.getMarca().getId());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getStock());
            stmt.setString(4, celular.getSistemaOperativo().name());
            stmt.setString(5, celular.getGama().name());
            stmt.setDouble(6, celular.getPrecio());
            stmt.setInt(7, celular.getId());

            int actualizar = stmt.executeUpdate();

            if (actualizar > 0) {
                System.out.println("Celular actualizado correctamente: " + celular.getModelo());
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }

        return false;
    }

    // Actualizar solo el stock (para ventas)
    public boolean actualizarStock(int idCelular, int nuevoStock) {
        String sql = "update celular set stock = ? where id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idCelular);

            int actualizar = stmt.executeUpdate();

            if (actualizar > 0) {
                System.out.println("Stock actualizado correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    //  D - delete - Eliminar celular
    public boolean eliminarCelular(int id) {
        String sql = "delete from celular where id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int eliminar = stmt.executeUpdate();

            if (eliminar > 0) {
                System.out.println("Celular eliminado correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }
}
