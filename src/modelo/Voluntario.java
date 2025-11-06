package modelo;

import java.util.List;

public class Voluntario extends Usuario {

    public Voluntario(int idUsuario, String nombre, String mail, String contraseña) {
        super(idUsuario, nombre, mail, contraseña);
    }


    public void registrarEntrega(Donacion d, EstadoDonacion nuevoEstado) {
        d.actualizarEstadoDonacion(nuevoEstado);
        System.out.println(" Voluntario " + nombre + " registro el cambio de estado de la donacion ID " + d.getIdDonacion() + " a " + nuevoEstado);
    }


    public void verTareasAsignadas(List<Donacion> donaciones) {
        System.out.println("--- Tareas Pendientes (Donaciones por gestionar) ---");
        boolean hayTareas = false;
        for (Donacion d : donaciones) {
            if (d.getEstadoDonacion() == EstadoDonacion.PENDIENTE || d.getEstadoDonacion() == EstadoDonacion.EN_CAMINO) {
                System.out.println("ID: " + d.getIdDonacion() + " | Tipo: " + d.getTipoDonacion() + " | Cant: " + d.getCantidad() + " | Estado Actual: " + d.getEstadoDonacion());
                hayTareas = true;
            }
        }
        if (!hayTareas) {
            System.out.println("¡No hay tareas pendientes!");
        }
        System.out.println("----------------------------------------------------");
    }
}