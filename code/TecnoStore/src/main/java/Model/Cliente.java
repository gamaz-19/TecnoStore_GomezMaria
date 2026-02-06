package Model;

public class Cliente {

    private int id;
    private Persona persona;

    //Constructor completo para taer desde la BD
    public Cliente(int id, Persona persona) {
        this.id = id;
        this.persona = persona;
    }

    //Contructor sin id para crear nuevo cliente
    public Cliente(Persona persona) {
        this.persona = persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "Cliente{"
                + "id=" + id
                + ", persona=" + persona
                + '}';
    }

}
