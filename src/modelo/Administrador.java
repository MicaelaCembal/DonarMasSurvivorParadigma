package modelo;

import java.util.List;

public class Administrador extends Usuario {

    public Administrador(int idUsuario, String nombre, String mail, String contraseña) {
        super(idUsuario, nombre, mail, contraseña);
    }

    @Override
    public void eliminar() {
        System.out.println("Administrador " + nombre + " fue eliminado del sistema (registro cerrado).");
    }


    public void generarReporteDonaciones(List<Donacion> donaciones) {
        System.out.println("=== Reporte de Donaciones ===");
        for (Donacion d : donaciones)
            d.mostrarDonacion();
    }

}
