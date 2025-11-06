package modelo;

public class InventarioItem {
    private String nombre;
    private int cantidad;

    public InventarioItem(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }


    public void sumarCantidad(int cantidadAdicional) {
        this.cantidad += cantidadAdicional;
    }


    public int getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return nombre;
    }
}