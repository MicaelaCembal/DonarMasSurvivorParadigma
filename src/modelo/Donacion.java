package modelo;

import java.time.LocalDateTime;

public class Donacion {

    /**
     * Contador estático para generar IDs únicos en la capa de negocio.
     * Se debe inicializar con el ID más alto de la base de datos al inicio.
     */
    private static int contadorDonaciones = 0;

    private int idDonacion;
    private EstadoDonacion estadoDonacion;        // Enum
    private String tipoDonacion;
    private int cantidad;
    private LocalDateTime fecha;                  // Fecha y hora de registro


    private Campania campania;
    private Deposito deposito;

    /**
     * Constructor que asigna automáticamente el IDDonacion usando el contador estático.
     */
    public Donacion(String tipoDonacion, int cantidad) {
        // Incrementa y asigna el nuevo ID.
        this.idDonacion = ++contadorDonaciones;
        this.tipoDonacion = tipoDonacion;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
        this.estadoDonacion = EstadoDonacion.PENDIENTE;
    }

    private Donacion(int idExistente, String tipoDonacion, int cantidad) {
        this.idDonacion = idExistente;
        this.tipoDonacion = tipoDonacion;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
        this.estadoDonacion = EstadoDonacion.PENDIENTE;

        // NO se incrementa el contador, solo se usa el ID existente
    }

    /**
     * Método auxiliar para inicializar el contador si la BD ya tiene registros.
     * DEBE ser llamado por GestorDonacion al inicio del programa.
     */
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
        if (deposito != null)
            System.out.println("Depósito: " + deposito.getUbicacion());
        System.out.println("-------------------------");
    }


    public void actualizarEstadoDonacion(EstadoDonacion nuevoEstado) {
        this.estadoDonacion = nuevoEstado;
        System.out.println("Donación ID " + idDonacion + " actualizada a estado: " + nuevoEstado);
    }


    public void asignarCampania(Campania c) {
        this.campania = c;
        // Llama al método corregido de la interfaz IAsociable en Campania
        c.agregarDonacion(this);
        System.out.println("Donación ID " + idDonacion + " asociada a la campaña " + c.getNombre());
    }


    public void asignarDeposito(Deposito d) {
        this.deposito = d;
        // Llama al método corregido de la interfaz IAsociable en Deposito
        d.agregarDonacion(this);
        System.out.println("Donación ID " + idDonacion + " enviada al depósito " + d.getUbicacion());
    }


    // Getters y Setters
    public int getIdDonacion() {
        return idDonacion;
    }

    public EstadoDonacion getEstadoDonacion() {
        return estadoDonacion;
    }

    public String getTipoDonacion() {
        return tipoDonacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Campania getCampania() {
        return campania;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setEstadoDonacion(EstadoDonacion estadoDonacion) {
        this.estadoDonacion = estadoDonacion;
    }
}