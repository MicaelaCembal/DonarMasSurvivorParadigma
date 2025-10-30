package TrabajoPOO;

import java.util.List;

public class Administrador extends Usuario {

    public Administrador(int idUsuario, String nombre, String mail, String contraseña) {
        super(idUsuario, nombre, mail, contraseña);
    }

    @Override
    public void eliminar() {
        System.out.println("Administrador " + nombre + " fue eliminado del sistema (registro cerrado).");
    }

    public Usuario buscarUsuario(List<Usuario> usuarios, String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Usuario encontrado: " + u.getNombre());
                return u;
            }
        }
        System.out.println("No se encontró usuario con nombre: " + nombre);
        return null;
    }

    public void eliminarUsuario(List<Usuario> usuarios, int id) {
        usuarios.removeIf(u -> u.getIdUsuario() == id);
        System.out.println("Usuario con ID " + id + " eliminado del sistema.");
    }

    public void generarReporteDonaciones(List<Donacion> donaciones) {
        System.out.println("=== Reporte de Donaciones ===");
        for (Donacion d : donaciones)
            d.mostrarDonacion();
    }
}
