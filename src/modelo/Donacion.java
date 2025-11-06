package modelo;

import java.time.LocalDateTime;

public class Donacion {

    // Excepción de negocio (Runtime para no obligar a try-catch en interfaces si no se quiere)
    public static class CantidadInvalidaException extends RuntimeException {
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
    private Campania campania;  // Relación con objeto Campaña
    private Deposito deposito;  // Relación con objeto Depósito

    // --- CONSTRUCTOR 1: Para NUEVAS donaciones (con validación) ---
    public Donacion(String tipoDonacion, int cantidad) {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a cero.");
        }
        this.idDonacion = ++contadorDonaciones;
        this.tipoDonacion = tipoDonacion;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
        this.estadoDonacion = EstadoDonacion.PENDIENTE;
    }

    // --- CONSTRUCTOR 2: Para RECONSTRUIR desde la DB ---
    public Donacion(int id, String tipo, int cant, LocalDateTime fecha, EstadoDonacion estado, String nombreCampania, String nombreDeposito) {
        this.idDonacion = id;
        this.tipoDonacion = tipo;
        this.cantidad = cant;
        this.fecha = fecha;
        this.estadoDonacion = estado;

        // Si la DB tenía datos, reconstruimos los objetos básicos en memoria
        if (nombreCampania != null) {
            this.campania = new Campania();
            this.campania.setNombre(nombreCampania);
        }
        if (nombreDeposito != null) {
            this.deposito = new Deposito(nombreDeposito);
        }
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
        if (campania != null) {
            System.out.println("Campaña: " + campania.getNombre());
        }
        if (deposito != null) {
            System.out.println("Lugar de entrega (Depósito): " + deposito.getUbicacion());
        }
        System.out.println("------------------------------");
    }

    public void actualizarEstadoDonacion(EstadoDonacion nuevoEstado) {
        this.estadoDonacion = nuevoEstado;
        System.out.println("Donación ID " + idDonacion + " actualizada a estado: " + nuevoEstado);
    }

    public void asignarCampania(Campania c) {
        this.campania = c;
        // Opcional: mantener consistencia bidireccional en memoria
        // c.agregarDonacion(this);
    }

    public void asignarDeposito(Deposito d) {
        this.deposito = d;
        // Si el depósito es un objeto completo (tiene lista), lo agregamos también
        if (d.getListaDonaciones() != null) {
            d.agregarDonacion(this);
        }
    }

    // --- Getters y Setters ---
    public int getIdDonacion() { return idDonacion; }
    public EstadoDonacion getEstadoDonacion() { return estadoDonacion; }
    public String getTipoDonacion() { return tipoDonacion; }
    public int getCantidad() { return cantidad; }
    public LocalDateTime getFecha() { return fecha; }
    public Campania getCampania() { return campania; }
    public Deposito getDeposito() { return deposito; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setEstadoDonacion(EstadoDonacion estadoDonacion) { this.estadoDonacion = estadoDonacion; }
}