package modelo;

import java.util.ArrayList;
import java.util.List;

public class Deposito implements IAsociable {

    public static class DepositoLlenoException extends RuntimeException {
        public DepositoLlenoException(String mensaje) {
            super(mensaje);
        }
    }

    static final int CAPACIDAD_MAXIMA = 15;

    private int idDeposito;  // no fue utilizado pero seria para hacer la tabla de depositos
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
    public void agregarDonacion(Donacion donacion) throws DepositoLlenoException {
        if (!verificarEstadoDisponible()) {
            throw new DepositoLlenoException("El depósito '" + this.ubicacion +
                    "' ha alcanzado su límite de " + CAPACIDAD_MAXIMA + " donaciones.");
        }

        listaDonaciones.add(donacion);
        System.out.println(" Donación ID " + donacion.getIdDonacion() +
                " añadida al depósito " + ubicacion + ".");

        actualizarInventario(donacion);
    }

    private void actualizarInventario(Donacion donacion) {
        boolean encontrado = false;
        for (InventarioItem item : inventario) {
            if (item.getDescripcion().equalsIgnoreCase(donacion.getTipoDonacion())) {
                item.sumarCantidad(donacion.getCantidad());
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
                System.out.println("- " + item.getDescripcion() + ": " + item.getCantidad() + " unidades totales.");
            }
        }
        System.out.println("------------------------------------");
    }

    @Override
    public String getIdentificador() {
        return "Depósito: " + ubicacion;
    }

    public List<Donacion> getListaDonaciones() {
        return listaDonaciones;
    }

    public List<InventarioItem> getInventario() {
        return inventario;
    }

    public String getUbicacion() {
        return ubicacion;
    }


    public int getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(int idDeposito) {
        this.idDeposito = idDeposito;
    }
}