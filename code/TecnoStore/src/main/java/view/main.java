package View;

//Deendencia para la coneccion con bd
import Controller.ConecctionDB;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

//Pueba inicial de funcionamiento y coneccion a base de datos
        System.out.println("Testing...");

        ConecctionDB db = ConecctionDB.getInstance();
        Connection estableciendoCC = db.conectar();
       
        System.out.println("Hola mundo :3 ");

    }
}
