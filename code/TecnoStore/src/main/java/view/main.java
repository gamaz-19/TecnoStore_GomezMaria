package View;

//Deendencia para la coneccion con bd
import Controller.ConecctionDB;

//Para prueba de insercion datos marca
import Controller.MarcaCRUD;
import Model.Marca;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

//Pueba inicial de funcionamiento y coneccion a base de datos
        System.out.println("Testing...");

        ConecctionDB db = ConecctionDB.getInstance();
        Connection estableciendoCC = db.conectar();
       
        System.out.println("Hola mundo :3 ");
        
        
    }
}
