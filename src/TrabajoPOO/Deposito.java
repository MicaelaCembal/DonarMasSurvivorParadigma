package TrabajoPOO;

import java.util.List;

public class Deposito {
    private int idDeposito;
    private String ubicacion;
    private int capacidad;
    private List<Donacion> listaDonaciones;
    private List<InventarioItem> inventario;

    public boolean verificarEstadoDisponible() {
        return false;
    }

    public void listarDonaciones() {}

    public void mostrarInventario() {}
}
