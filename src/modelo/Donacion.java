package modelo;

import java.time.LocalDateTime;

public class Donacion {
    private static int contadorId = 0;

    private int idDonacion;
    private String tipoDonacion;
    private int cantidad;
    private String deposito;
    private Campania campania;
    private LocalDateTime fecha;
    private EstadoDonacion estadoDonacion;

    public static class CantidadInvalidaException extends RuntimeException {
        public CantidadInvalidaException(String mensaje) {
            super(mensaje);
        }
    }

    public Donacion(String tipoDonacion, int cantidad) {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a cero.");
        }
        this.idDonacion = ++contadorId;
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

    public static void inicializarContador(int valor) {
        contadorId = valor;
    }

    public void actualizarEstadoDonacion(EstadoDonacion nuevoEstado) {
        this.estadoDonacion = nuevoEstado;
    }
    public void mostrarDonacion() {
        System.out.println("=== Donación ID " + idDonacion + " ===");
        System.out.println("Tipo: " + tipoDonacion);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Lugar de entrega: " + deposito);
        System.out.println("Estado: " + estadoDonacion);
        System.out.println("Fecha: " + fecha);
        if (campania != null) {
            System.out.println("Campaña: " + campania.getNombre());
        }
        System.out.println("------------------------------");
    }

    public int getIdDonacion() { return idDonacion; }
    public void setIdDonacion(int idDonacion) { this.idDonacion = idDonacion; }
    public String getTipoDonacion() { return tipoDonacion; }
    public void setTipoDonacion(String tipoDonacion) { this.tipoDonacion = tipoDonacion; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getDeposito() { return deposito; }
    public void setDeposito(String deposito) { this.deposito = deposito; }
    public Campania getCampania() { return campania; }
    public void asignarCampania(Campania campania) { this.campania = campania; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public EstadoDonacion getEstadoDonacion() { return estadoDonacion; }
    public void setEstadoDonacion(EstadoDonacion estadoDonacion) { this.estadoDonacion = estadoDonacion; }
}
