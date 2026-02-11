package Model;

public class Marca {

    private int id;
    private String nombre;

    // Constructor completo para traer de la bd
    public Marca(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    //Constructor sin id para creacion de nuevas marcas
    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Marca{"
                + "id=" + id
                + ", nombre='" + nombre + '\''
                + '}';
    }
//    return"""
//          marca: 
//          id: %s
//          """

}
