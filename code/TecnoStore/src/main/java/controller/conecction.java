package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conecction {

    public Connection conectar() {
        Connection c = null;

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore", "root", "Mg0730021-");
            
            System.out.println("Conexion establecida");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;

    }
}
