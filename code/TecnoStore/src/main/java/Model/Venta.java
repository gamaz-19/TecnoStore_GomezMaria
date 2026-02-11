package Model;

import java.util.ArrayList;
import java.util.List;

public class Venta {

    private int id;
    private String fecha;
    private double total;
    private Cliente cliente;
    private List<Detalle_venta> detalles;

    //Constructor completo con id para taer dedsed la BD
    public Venta(int id, String fecha, double total, Cliente cliente, List<Detalle_venta> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
    }

    //Constructor sin id para creacion de nueva venta
    public Venta(String fecha, double total, Cliente cliente, List<Detalle_venta> detalles) {
    this.fecha = fecha;
    this.total = total;
    this.cliente = cliente;
    this.detalles = detalles;
}

    
    
    
    
    // Metodos para manejar los detalles

    public void agregarDetalle(Detalle_venta detalle) {
        this.detalles.add(detalle);
        calcularTotal(); // Recalcular el total cada vez que se agrega un detalle
    }

    public void eliminarDetalle(Detalle_venta detalle) {
        this.detalles.remove(detalle);
        calcularTotal();
    }

    private void calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(Detalle_venta::getSubtotal)
                .sum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Detalle_venta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle_venta> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
}
