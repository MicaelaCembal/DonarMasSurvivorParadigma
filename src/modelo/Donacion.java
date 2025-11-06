package modelo;

import java.time.LocalDateTime;

public class Donacion {

    public static class CantidadInvalidaException extends Exception {
        public CantidadInvalidaException(String mensaje) {
            super(mensaje);
        }
    }

    private static int contadorDonaciones = 0;
    private int idDonacion;
    private EstadoDonacion estadoDonacion;
    private String tipoDonacion;
    private int cantidad;
    private LocalDateTime fecha;
    private Campania campania;

    public Donacion(String tipoDonacion, int cantidad) throws CantidadInvalidaException {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a cero.");
        }
        this.idDonacion = ++contadorDonaciones;
        this.tipoDonacion = tipoDonacion;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
        this.estadoDonacion = EstadoDonacion.PENDIENTE;
    }

    public Donacion(int idDonacion, String tipoDonacion, int cantidad, LocalDateTime fecha, EstadoDonacion estadoDonacion) {
        this.idDonacion = idDonacion;
        this.tipoDonacion = tipoDonacion;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.estadoDonacion = estadoDonacion;
    }

    public static void inicializarContador(int maxIdExistente) {
        if (maxIdExistente > contadorDonaciones) {
            contadorDonaciones = maxIdExistente;
        }
    }

    public void mostrarDonacion() {
        System.out.println("=== Donación ID " + idDonacion + " ===");
        System.out.println("Tipo: " + tipoDonacion);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Fecha: " + fecha);
        System.out.println("Estado: " + estadoDonacion);
        if (campania != null)
            System.out.println("Campaña: " + campania.getNombre());
        System.out.println("-------------------------");
    }

    public void actualizarEstadoDonacion(EstadoDonacion nuevoEstado) {
        this.estadoDonacion = nuevoEstado;
        System.out.println("Donación ID " + idDonacion + " actualizada a estado: " + nuevoEstado);
    }

    public void asignarCampania(Campania c) {
        this.campania = c;
        c.agregarDonacion(this);
        System.out.println("Donación ID " + idDonacion + " asociada a la campaña " + c.getNombre());
    }

    public int getIdDonacion() { return idDonacion; }
    public EstadoDonacion getEstadoDonacion() { return estadoDonacion; }
    public String getTipoDonacion() { return tipoDonacion; }
    public int getCantidad() { return cantidad; }
    public LocalDateTime getFecha() { return fecha; }
    public Campania getCampania() { return campania; }
    public void setEstadoDonacion(EstadoDonacion estadoDonacion) { this.estadoDonacion = estadoDonacion; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
