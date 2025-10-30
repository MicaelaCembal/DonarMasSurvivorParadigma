package TrabajoPOO;

public class InventarioItem {
    private String nombre;
    private int cantidad;

    public InventarioItem(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return nombre;
    }
}
