package View;

//Deendencia para la coneccion con bd
import Controller.ConecctionDB;
import Controller.MarcaCRUD;
import Model.Marca;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

//Pueba inicial de funcionamiento y coneccion a base de datos
        System.out.println("Testing...");

        ConecctionDB db = ConecctionDB.getInstance();
        Connection estableciendoCC = db.conectar();
       
        System.out.println("Hola mundo :3 ");
        
        
        
        System.out.println("Prueba insercion marca nueva");
        
        
        MarcaCRUD marcaCRUD = new MarcaCRUD();
        
        // Iserta una nueva marca
        System.out.println("Insertando ....");
        Marca nuevaMarca = new Marca("Huawei");
        marcaCRUD.insertar(nuevaMarca);
        System.out.println("ID generado: " + nuevaMarca.getId() + "\n");
        
        Marca nuevaMarca2 = new Marca("Samsung");
        marcaCRUD.insertar(nuevaMarca2);
        System.out.println("ID generado: " + nuevaMarca.getId() + "\n");

    }
}
