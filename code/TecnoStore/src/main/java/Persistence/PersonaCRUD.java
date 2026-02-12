package Persistence;

import Controller.ConecctionDB;
import Model.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonaCRUD {
    
    //Conectarse al la base d edatos
    private Connection connection;
    
    //Constructor
    public PersonaCRUD() {
        this.connection = ConecctionDB.getInstance().conectar();
    }
    
    //C-create-insertar nueva marca
    public boolean insertarPersona(Persona persona)  {
        //La plantilla para saber como se van a inseretar los datos
        String sql = "insert into persona(nombre, identificacion, correo, telefono) values(?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //Se establece el parametro
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getIdentificacion());
            stmt.setString(3, persona.getCorreo());
            stmt.setString(4, persona.getTelefono());

            //Insercion en bd
            int insertar = stmt.executeUpdate();

            if (insertar > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    persona.setId(generatedKeys.getInt(1));

                }
                System.out.println("-- Persona insertada correctamente: " + persona.getNombre()+" --");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    
    
    //R- read(Leer todas las marcas)
    
        //Por ID
    public Persona obtenerPorId(int id) {

        //La plantilla para saber como se van a inseretar los datos
        String sql = "select * from persona where id = ?";
        Persona persona = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            //Se establece el parametro
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            //Insercion en bd
            if (rs.next()) {
                persona = new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return persona;
    }

        //Obtener todas las personasa
    public List<Persona> obtenerTodas()  {
        String sql = "select * from persona";
        List<Persona> personas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Persona persona = new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
                personas.add(persona);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return personas;
    }

    //U-update(actualizar marca)
    
    public boolean actualizarPersona(Persona persona)  {
        String sql = "update persona set nombre = ?, identificacion = ?, correo = ?, telefono = ? where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombre());
            stmt.setInt(2, persona.getId());
            int actualizar = stmt.executeUpdate();
            if (actualizar > 0) {
                System.out.println("-- Persona actualizada correctamente " + persona.getNombre()+" --");
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    //D-delete(eliminar marca)
    
    public boolean eliminarPersona(int id)  {
        String sql = "delete from persona where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int eliminar = stmt.executeUpdate();
            if (eliminar > 0) {
                System.out.println("-- Persona eliminada correctamente --");
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    
    
}
