package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConecctionDB {

    //Instanc
    private static ConecctionDB conectado;

    private ConecctionDB() {
    }

    public static ConecctionDB getInstance() {
        if (conectado == null) {
            conectado = new ConecctionDB();
        }
        return conectado;
    }

    public Connection conectar() {
        Connection c = null;

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore", "root", "Mg0730021-");

            System.out.println("Conexion con la base de datos se establecio correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;

    }
}
