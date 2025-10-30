package TrabajoPOO;

import java.util.List;

public class Voluntario extends Usuario {

    public Voluntario(int idUsuario, String nombre, String mail, String contraseña) {
        super(idUsuario, nombre, mail, contraseña);
    }

    public void registrarEntrega(Donacion d) {
        d.actualizarEstadoDonacion(EstadoDonacion.ENTREGADO);
        System.out.println("Voluntario " + nombre + " registró la entrega de donación ID " + d.getIdDonacion());
    }

    public void clasificarDonacion(Donacion d) {
        System.out.println("Voluntario " + nombre + " clasificó la donación ID " + d.getIdDonacion());
    }

    public void verTareasAsignadas(List<Donacion> donacionesPendientes) {
        System.out.println("Tareas pendientes de " + nombre + ":");
        for (Donacion d : donacionesPendientes) {
            if (d.getEstadoDonacion() == EstadoDonacion.PENDIENTE)
                System.out.println("- Donación ID " + d.getIdDonacion());
        }
    }
}
