package Controller;

import Persistence.ClienteCRUD;
import Persistence.CelularCRUD;
import Persistence.VentaCRUD;
import Model.Celular;
import Model.Cliente;
import Model.Detalle_venta;
import Model.Venta;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//POBJETIVOS ESPECIFICOS
/*
1.Validar que precio y stock sean positivos
2.Validar formato del correo y numero de identificacion unico()ya en bd)
 */
public class gestionVentas {

    private VentaCRUD ventaCRUD;
    private CelularCRUD celularCRUD;
    private ClienteCRUD clienteCRUD;

    //Solo se establece una vez y no  es necesario crearr un objeto, puede ser sin instamcia
    private static final double IVA = 0.19; // IVA DE 19%

    public gestionVentas(VentaCRUD ventaCRUD, CelularCRUD celularCRUD, ClienteCRUD clienteCRUD) {
        this.ventaCRUD = ventaCRUD;
        this.celularCRUD = celularCRUD;
        this.clienteCRUD = clienteCRUD;
    }

   
    public boolean registrarVenta(int idCliente, List<ItemVenta> items) {

        // 1. Validar que el cliente exista
        Cliente cliente = clienteCRUD.obtenerPorId(idCliente);
        if (cliente == null) {
            System.err.println("Error: Cliente no encontrado");
            return false;
        }
        //crear venta antes de stock y subtotales para evitar error
        Venta venta = new Venta(
                LocalDate.now().toString(),
                0,
                cliente,
                new ArrayList<>()
        );

        //Validar stock y calcular subtotales
        List<Detalle_venta> detalles = new ArrayList<>();
        double subtotalGeneral = 0;

        for (ItemVenta item : items) {
            // Obtener celular
            Celular celular = celularCRUD.obtenerPorId(item.getIdCelular());

            if (celular == null) {
                System.err.println(" Error: Celular no encontrado (ID: " + item.getIdCelular() + ")");
                return false;
            }

            if (celular.getStock() < item.getCantidad()) {
                System.err.println(" Error: Stock insuficiente para " + celular.getModelo());
                return false;
            }

            double subtotal = celular.getPrecio() * item.getCantidad();
            subtotalGeneral += subtotal;

            Detalle_venta detalle = new Detalle_venta(
                    item.getCantidad(),
                    celular,
                    venta,
                    subtotal
            );

            detalles.add(detalle);
        }

        // Calcular total CON IVA (19%)
        double totalConIVA = subtotalGeneral * (1 + IVA);

        // Crear la venta
        venta.setFecha(LocalDate.now().toString()); // Fecha actual
        venta.setTotal(totalConIVA);
        venta.setDetalles(detalles);

        //Guardar venta en BD
        boolean ventaGuardada = ventaCRUD.insertarVenta(venta);

        if (!ventaGuardada) {
            System.err.println(" Error al guardar la venta");
            return false;
        }

        // Actualizar stock de celulares vendidos
        for (Detalle_venta detalle : detalles) {
            Celular celular = detalle.getCelular();
            int nuevoStock = celular.getStock() - detalle.getCantidad();
            celularCRUD.actualizarStock(celular.getId(), nuevoStock);
        }

        return true;
    }

    // Obtener todas las ventas
    public List<Venta> obtenerTodasLasVentas() {
        return ventaCRUD.obtenerTodos();
    }

    //Clase auxiliar para pasar items de venta
    
    
    public static class ItemVenta {

        private int idCelular;
        private int cantidad;

        public ItemVenta(int idCelular, int cantidad) {
            this.idCelular = idCelular;
            this.cantidad = cantidad;
        }

        public int getIdCelular() {
            return idCelular;
        }

        public int getCantidad() {
            return cantidad;
        }
    }
}
