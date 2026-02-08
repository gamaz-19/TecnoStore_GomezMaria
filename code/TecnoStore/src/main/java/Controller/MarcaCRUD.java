package Controller;

import Model.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MarcaCRUD {

    //Conectarse a la base de datos
    private Connection connection;

    // Constructor
    public MarcaCRUD() {
        this.connection = ConecctionDB.getInstance().conectar();
    }

    //C-create-insertar nueva marca
    public boolean insertar(Marca marca) throws SQLException {
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

}
