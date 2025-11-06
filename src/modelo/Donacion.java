package modelo;

import java.time.LocalDateTime;

public class Donacion {

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
    private Campania campania;
    private Deposito deposito;

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

    public Donacion(int id, String tipo, int cant, LocalDateTime fecha, EstadoDonacion estado, String nombreCampania, String nombreDeposito) {
        this.idDonacion = id;
        this.tipoDonacion = tipo;
        this.cantidad = cant;
        this.fecha = fecha;
        this.estadoDonacion = estado;


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

    }

    public void asignarDeposito(Deposito d) {
        this.deposito = d;

        if (d.getListaDonaciones() != null) {
            d.agregarDonacion(this);
        }
    }


    public int getIdDonacion() { return idDonacion; }
    public EstadoDonacion getEstadoDonacion() { return estadoDonacion; }
    public String getTipoDonacion() { return tipoDonacion; }
    public int getCantidad() { return cantidad; }
    public LocalDateTime getFecha() { return fecha; }
    public Campania getCampania() { return campania; }
    public Deposito getDeposito() { return deposito; }
}