package Model;

public class Detalle_venta {

    private int id, cantidad;
    private Celular celular;
    private Venta venta;
    private double subtotal;

    //Constructor completo para traerlo desde la BD
    public Detalle_venta(int id, int cantidad, Celular celular, Venta venta, double subtotal) {
        this.id = id;
        this.cantidad = cantidad;
        this.celular = celular;
        this.venta = venta;
        this.subtotal = subtotal;
    }
    //Constructor sin id para la creacion e insercion de nuevo detalle de venta

    public Detalle_venta(int cantidad, Celular celular, Venta venta, double subtotal) {
        this.cantidad = cantidad;
        this.celular = celular;
        this.venta = venta;
        this.subtotal = calcularSubtotal();
    }

    // Calcular el subtotal
    private double calcularSubtotal() {
        return this.celular.getPrecio() * this.cantidad;
    }

    // Recalcular subtotal si cambia la cantidad o el precio
    public void recalcularSubtotal() {
        this.subtotal = calcularSubtotal();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        recalcularSubtotal(); //Para saber si la cantidad cambua
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
        recalcularSubtotal();//Para saber si se cambia el celular
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Detalle_venta{"
                + "id=" + id
                + ", celular=" + celular.getModelo()
                + ", cantidad=" + cantidad
                + ", precioUnitario=" + celular.getPrecio()
                + ", subtotal=" + subtotal
                + '}';
    }

}
