package modelo;

import java.util.Scanner;
import modelo.datos.ConexionDB;
import modelo.datos.GestorDonacion;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();
        GestorDonacion gestor = new GestorDonacion();

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Bienvenido al Sistema de Donaciones ===");
        System.out.println("1. Donante");
        System.out.println("2. Administrador");
        System.out.println("3. Voluntario");
        System.out.println("4. Test Rapido: Deposito Lleno");
        System.out.print("Elegi una opcion: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        if (opcion == 4) {
            ejecutarTestDeposito();
            sc.close();
            return;
        }

        System.out.print("Ingresa tu mail: ");
        String mail = sc.nextLine();

        Usuario usuarioActual = gestor.buscarUsuarioPorNombre(mail);

        if (usuarioActual != null) {
            System.out.println(">>> Usuario encontrado! Bienvenido de nuevo, " + usuarioActual.getNombre());
        } else {
            System.out.println(">>> Usuario nuevo. Por favor completa tu registro.");
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Contraseña: ");
            String contraseña = sc.nextLine();

            switch (opcion) {
                case 1 -> usuarioActual = new Donante(0, nombre, mail, contraseña);
                case 2 -> usuarioActual = new Administrador(0, nombre, mail, contraseña);
                case 3 -> usuarioActual = new Voluntario(0, nombre, mail, contraseña);
                default -> {
                    System.out.println("Opcion invalida. Saliendo.");
                    sc.close();
                    return;
                }
            }
            gestor.guardarUsuario(usuarioActual);
            usuarioActual = gestor.buscarUsuarioPorNombre(mail);
        }

        if (usuarioActual instanceof Donante donante) {
            System.out.println("\n--- Panel de Donante ---");
            System.out.println("¿Deseas realizar una nueva donacion? (S/N)");
            if (sc.nextLine().equalsIgnoreCase("S")) {
                System.out.print("Tipo de donacion: ");
                String tipo = sc.nextLine();
                System.out.print("Cantidad: ");
                int cantidad = sc.nextInt();
                sc.nextLine();
                System.out.print("Lugar de entrega (depósito): ");
                String deposito = sc.nextLine();

                try {
                    Donacion d = new Donacion(tipo, cantidad);
                    d.setDeposito(deposito);
                    gestor.guardarDonacion(d);
                    donante.realizarDonacion(d);
                    System.out.println("Donacion registrada con exito.");
                } catch (Exception e) {
                    System.out.println("No se pudo registrar: " + e.getMessage());
                }
            }

        } else if (usuarioActual instanceof Administrador admin) {
            System.out.println("\n--- Panel de Administrador ---");
            System.out.println("1. Ver Reporte General");
            System.out.println("2. Eliminar Usuario");
            System.out.print("Elegi una opcion: ");
            int opAdmin = sc.nextInt();
            sc.nextLine();

            if (opAdmin == 1) {
                admin.generarReporteDonaciones(gestor.obtenerDonaciones());
            } else if (opAdmin == 2) {
                System.out.println("\nLista de usuarios:");
                for (Usuario u : gestor.obtenerUsuarios()) {
                    System.out.println("ID: " + u.getIdUsuario() + " | " + u.getNombre() + " (" + u.getClass().getSimpleName() + ") | " + u.getMail());
                }
                System.out.print("ID del usuario a eliminar: ");
                int idEliminar = sc.nextInt();
                sc.nextLine();

                // --- AQUI ESTA EL TRY-CATCH SOLICITADO ---
                try {
                    gestor.eliminarUsuario(idEliminar);
                    // Si llega aqui, el mensaje de exito ya lo imprimio el gestor.
                } catch (GestorDonacion.UsuarioNoEncontradoException e) {
                    System.out.println("ERROR AL ELIMINAR: " + e.getMessage());
                }
                // -----------------------------------------
            }

        } else if (usuarioActual instanceof Voluntario voluntario) {
            System.out.println("\n--- Panel de Voluntario ---");
            System.out.println("Tus tareas pendientes:");
            voluntario.verTareasAsignadas(gestor.obtenerDonaciones());
        }

        sc.close();
    }

    private static void ejecutarTestDeposito() {
        // (El código del test de depósito sigue igual...)
        System.out.println("Ejecutando test de deposito...");
        Deposito depositoTest = new Deposito("Deposito Experimental");
        try {
            for (int i = 1; i <= 16; i++) {
                Donacion d = new Donacion("Item " + i, 1);
                depositoTest.agregarDonacion(d);
            }
        } catch (Exception e) {
            System.out.println("EXITO DEL TEST: " + e.getMessage());
        }
    }
}