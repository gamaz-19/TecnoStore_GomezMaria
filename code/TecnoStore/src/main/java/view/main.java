package View;

//Deendencia para la coneccion con bd
import Controller.ConecctionDB;

public class Main {

    public static void main(String[] args) {

//Pueba inicial de funcionamiento y coneccion a base de datos
        System.out.println("Testing...");

        ConecctionDB db = new ConecctionDB();

        db.conectar();
        System.out.println("Hola mundo :3 ");

    }
}
