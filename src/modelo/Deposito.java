package modelo;

import java.util.ArrayList;
import java.util.List;

public class Deposito implements IAsociable {

    // --- CORRECCIÓN AQUÍ ---
    // Cambiamos 'Exception' por 'RuntimeException'.
    // Esto evita el error de conflicto con la interfaz IAsociable.
    public static class DepositoLlenoException extends RuntimeException {
        public DepositoLlenoException(String mensaje) {
            super(mensaje);
        }
    }
    // -----------------------

    private static final int CAPACIDAD_MAXIMA = 15;

    private int idDeposito;
    private String ubicacion;
    private List<Donacion> listaDonaciones;
    private List<InventarioItem> inventario;

    public Deposito() {
        listaDonaciones = new ArrayList<>();
        inventario = new ArrayList<>();
    }

    public Deposito(String ubicacion) {
        this.ubicacion = ubicacion;
        this.listaDonaciones = new ArrayList<>();
        this.inventario = new ArrayList<>();
    }

    @Override
    // Al ser RuntimeException, no estamos OBLIGADOS a poner 'throws' aquí,
    // pero es buena práctica dejarlo para saber que puede ocurrir.
    // El compilador ya no dará error porque RuntimeException no rompe contratos de interfaz.
    public void agregarDonacion(Donacion donacion) throws DepositoLlenoException {
        if (!verificarEstadoDisponible()) {
            throw new DepositoLlenoException("El depósito '" + this.ubicacion +
                    "' ha alcanzado su límite de " + CAPACIDAD_MAXIMA + " donaciones.");
        }

        listaDonaciones.add(donacion);
        System.out.println("✅ Donación ID " + donacion.getIdDonacion() +
                " añadida al depósito " + ubicacion + ".");

        actualizarInventario(donacion);
    }

    private void actualizarInventario(Donacion donacion) {
        boolean encontrado = false;
        for (InventarioItem item : inventario) {
            if (item.getDescripcion().equalsIgnoreCase(donacion.getTipoDonacion())) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            inventario.add(new InventarioItem(donacion.getTipoDonacion(), donacion.getCantidad()));
        }
    }

    public boolean verificarEstadoDisponible() {
        return listaDonaciones.size() < CAPACIDAD_MAXIMA;
    }

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

    public int getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(int idDeposito) {
        this.idDeposito = idDeposito;
    }
}