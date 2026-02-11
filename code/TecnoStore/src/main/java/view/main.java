package View;

//Deendencia para la coneccion con bd
import Controller.ConecctionDB;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {

//Pueba inicial de funcionamiento y coneccion a base de datos
//        System.out.println("Testing...");
//
//        ConecctionDB db = ConecctionDB.getInstance();
//        Connection estableciendoCC = db.conectar();
//       
//        System.out.println("Hola mundo :3 ");

  
        // Establecer conexión inicial
        ConecctionDB db = ConecctionDB.getInstance();
        Connection conn = db.conectar();
        
        if (conn != null) {
            // Iniciar el menú principal
            MenuPrincipal menu = new MenuPrincipal();
            menu.iniciar();
        } else {
            System.err.println("No se pudo establecer coneccion con la base de datos");
        }
        
        
    }
}
