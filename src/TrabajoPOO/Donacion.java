package TrabajoPOO;

import java.time.LocalDateTime;

public class Donacion {


    private int idDonacion;
    private EstadoDonacion estadoDonacion;        // Enum
    private String tipoDonacion;
    private int cantidad;
    private LocalDateTime fecha;                  // Fecha y hora de registro


    private Campaña campaña;
    private Deposito deposito;



    public Donacion(int idDonacion, String tipoDonacion, int cantidad) {
        this.idDonacion = idDonacion;
        this.tipoDonacion = tipoDonacion;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
        this.estadoDonacion = EstadoDonacion.PENDIENTE;
    }



    public void mostrarDonacion() {
        System.out.println("=== Donación ID " + idDonacion + " ===");
        System.out.println("Tipo: " + tipoDonacion);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Fecha: " + fecha);
        System.out.println("Estado: " + estadoDonacion);
        if (campaña != null)
            System.out.println("Campaña: " + campaña.getNombre());
        if (deposito != null)
            System.out.println("Depósito: " + deposito.getUbicacion());
        System.out.println("-------------------------");
    }


    public void actualizarEstadoDonacion(EstadoDonacion nuevoEstado) {
        this.estadoDonacion = nuevoEstado;
        System.out.println("Donación ID " + idDonacion + " actualizada a estado: " + nuevoEstado);
    }


    public void asignarCampaña(Campaña c) {
        this.campaña = c;
        c.agregarDonacion(this);
        System.out.println("Donación ID " + idDonacion + " asociada a la campaña " + c.getNombre());
    }


    public void asignarDeposito(Deposito d) {
        this.deposito = d;
        System.out.println("Donación ID " + idDonacion + " enviada al depósito " + d.getUbicacion());
    }


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

    public Campaña getCampaña() {
        return campaña;
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
