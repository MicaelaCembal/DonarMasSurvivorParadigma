package modelo;

import java.util.ArrayList;
import java.util.List;

public class Deposito implements IAsociable {

    // Máximo de donaciones que este depósito puede manejar
    private static final int CAPACIDAD_MAXIMA = 15;

    private int idDeposito;
    private String ubicacion;
    private List<Donacion> listaDonaciones;
    private List<InventarioItem> inventario; // Requisito del UML

    public Deposito() {
        listaDonaciones = new ArrayList<>();
        inventario = new ArrayList<>(); // Inicialización del inventario
    }



    @Override
    public void agregarDonacion(Donacion donacion) {
        if (!verificarEstadoDisponible()) {
            System.out.println("⚠️ Depósito lleno: El depósito '" + this.ubicacion +
                    "' ha alcanzado su límite de " + CAPACIDAD_MAXIMA +
                    " donaciones. Se debe asignar a otro depósito.");
            return;
        }

        listaDonaciones.add(donacion);
        System.out.println("✅ Donación ID " + donacion.getIdDonacion() +
                " añadida al depósito " + ubicacion + ".");

        // La donación debe impactar el inventario
        actualizarInventario(donacion);
    }

    /**
     * Lógica auxiliar para mantener el inventario sincronizado con las donaciones.
     */
    private void actualizarInventario(Donacion donacion) {
        // En una implementación real se buscaría el InventarioItem y se actualizaría la cantidad.
        boolean encontrado = false;
        for (InventarioItem item : inventario) {
            if (item.getDescripcion().equalsIgnoreCase(donacion.getTipoDonacion())) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            // Si es un nuevo tipo de donación, se añade al inventario.
            inventario.add(new InventarioItem(donacion.getTipoDonacion(), donacion.getCantidad()));
        }
    }


    /**
     * Requisito del UML: verifica si hay espacio disponible en el depósito.
     * @return true si hay espacio, false si está lleno.
     */
    public boolean verificarEstadoDisponible() {
        return listaDonaciones.size() < CAPACIDAD_MAXIMA;
    }

    /**
     * Requisito del UML: Muestra el contenido del inventario.
     */
    public void mostrarInventario() {
        System.out.println("=== Inventario del Depósito: " + ubicacion + " ===");
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

    // Getters y Setters
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