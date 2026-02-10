package Controller;

import Model.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MarcaCRUD {

    //Conectarse a la base de datos
    private Connection connection;

    // Constructor
    public MarcaCRUD() {
        this.connection = ConecctionDB.getInstance().conectar();
    }

    //C-create-insertar nueva marca
    public boolean insertarMarca(Marca marca) throws SQLException {
        //La plantilla para saber como se van a inseretar los datos
        String sql = "insert into marca(nombre) values(?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //Se establece el parametro
            stmt.setString(1, marca.getNombre());

            //Insercion en bd
            int insertar = stmt.executeUpdate();

            if (insertar > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    marca.setId(generatedKeys.getInt(1));

                }
                System.out.println("Marca insertada correctamente" + marca.getNombre());
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar marca " + e.getMessage());
        }
        return false;
    }
    
    
    //R- read(Leer todas las marcas)
    
        //Por ID
    public Marca obtenerPorId(int id) throws SQLException {

        //La plantilla para saber como se van a inseretar los datos
        String sql = "select * from marca where id = ?";
        Marca marca = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            //Se establece el parametro
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            //Insercion en bd
            if (rs.next()) {
                marca = new Marca(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error, no se pudo obtener marca por ID" + e.getMessage());
        }

        return marca;
    }

        //Obtener todas las marcas
    public List<Marca> obtenerTodas() throws SQLException {
        String sql = "select * from marca";
        List<Marca> marcas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Marca marca = new Marca(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                marcas.add(marca);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las marcas " + e.getMessage());
        }
        return marcas;
    }

    //U-update(actualizar marca)
    
    public boolean actualizarMarca(Marca marca) throws SQLException {
        String sql = "update marca set nombre = ? where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, marca.getNombre());
            stmt.setInt(2, marca.getId());
            int actualizar = stmt.executeUpdate();
            if (actualizar > 0) {
                System.out.println("Marca actualizada correctamente " + marca.getNombre());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar marca " + e.getMessage());
        }
        return false;
    }

    //D-delete(eliminar marca)
    
    public boolean eliminarMarca(int id) throws SQLException {
        String sql = "delete from marca where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int eliminar = stmt.executeUpdate();
            if (eliminar > 0) {
                System.out.println("Marca eliminada correctamente");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar marca " + e.getMessage());
        }
        return false;
    }
}
