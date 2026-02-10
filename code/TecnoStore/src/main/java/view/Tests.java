package View;

import Controller.MarcaCRUD;
import Model.Marca;
import java.sql.SQLException;
import java.util.List;

public class Tests {

    public static void main(String[] args) throws SQLException {

        MarcaCRUD marcaCRUD = new MarcaCRUD();

        // 1. Insertar una nueva marca
        System.out.println("INSERTARA MARCA");
        Marca nuevaMarca = new Marca("LG");
        marcaCRUD.insertarMarca(nuevaMarca);
        System.out.println("ID generado: " + nuevaMarca.getId() + "\n");

        // 2. Obtener marca por ID
        System.out.println("MARCA POR ID");
        Marca marca = marcaCRUD.obtenerPorId(4);
        System.out.println(marca + "\n");

        // 3. Obtener todas las marcas
        System.out.println("LISTAR MARCAS");
        List<Marca> todasLasMarcas = marcaCRUD.obtenerTodas();
        for (Marca m : todasLasMarcas) {
            System.out.println(m);
        }

        // 4. Actualizar una marca
        System.out.println("ACTUALIZAR MARCA");
        Marca marcaActualizar = marcaCRUD.obtenerPorId(3);
        if (marcaActualizar != null) {
            marcaActualizar.setNombre("poyito ");
            marcaCRUD.actualizarMarca(marcaActualizar);
            System.out.println("Marca actualizada: " + marcaCRUD.obtenerPorId(3) + "\n");
        }

        // 5. Eliminar una marca
        System.out.println("ACTUALIZAR MARCA");
        boolean eliminado = marcaCRUD.eliminarMarca(2);
        if (eliminado) {
            System.out.println("Marca con ID 2 eliminada\n");
        }
    }
}
