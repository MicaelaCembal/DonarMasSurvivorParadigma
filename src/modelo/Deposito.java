package modelo;

import java.util.ArrayList;
import java.util.List;

public class Deposito implements IAsociable {

    // Máximo de donaciones que este depósito puede manejar
    private static final int CAPACIDAD_MAXIMA = 15;

    private int idDeposito;
    private String ubicacion;
    private List<Donacion> listaDonaciones;
    private List<InventarioItem> inventario;

    public Deposito() {
        listaDonaciones = new ArrayList<>();
        inventario = new ArrayList<>(); // Inicialización del inventario
    }


    @Override
    public void agregarDonacion(Donacion donacion) {
        if (!verificarEstadoDisponible()) {
            System.out.println("Depósito lleno: El depósito '" + this.ubicacion +
                    "' ha alcanzado su límite de " + CAPACIDAD_MAXIMA +
                    " donaciones. Se debe asignar a otro depósito.");
            return;
        }

        listaDonaciones.add(donacion);
        System.out.println("Donación ID " + donacion.getIdDonacion() +
                " añadida al depósito " + ubicacion + ".");

        // Lógica: La donación debe impactar el inventario (simulación)
        actualizarInventario(donacion);
    }


    private void actualizarInventario(Donacion donacion) {
        // En una implementación real, se buscaría el item y se actualizaría la cantidad.
        boolean encontrado = false;
        for (InventarioItem item : inventario) {
            if (item.getDescripcion().equalsIgnoreCase(donacion.getTipoDonacion())) {
                // Como no tenemos un setter en InventarioItem.java (archivo provisto),
                // para simular la actualización, simplemente se marca como encontrado.
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            // Si es un nuevo tipo de donación, se añade al inventario.
            inventario.add(new InventarioItem(donacion.getTipoDonacion(), donacion.getCantidad()));
        }
    }


    public boolean verificarEstadoDisponible() {
        return listaDonaciones.size() < CAPACIDAD_MAXIMA;
    }


    public void mostrarInventario() {
        System.out.println(" Inventario del Depósito: " + ubicacion + " ");
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (InventarioItem item : inventario) {
                System.out.println("- " + item.getDescripcion() + ": " + item.getCantidad());
            }
        }
        System.out.println("------------------------------------");
    }


    @Override
    public String getIdentificador() {
        return "Depósito: " + ubicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Donacion> getListaDonaciones() {
        return listaDonaciones;
    }

    public List<InventarioItem> getInventario() {
        return inventario;
    }
}