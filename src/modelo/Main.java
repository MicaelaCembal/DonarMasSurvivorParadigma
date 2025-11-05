package modelo;

import java.util.Scanner;
import modelo.datos.ConexionDB;
import modelo.datos.GestorDonacion;

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
        System.out.println("4. Test Rápido: Depósito Lleno (Prueba de Excepción)");
        System.out.print("Elegí una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine(); // Consumir el salto de línea

        // Si elige la opción 4 (Test), no necesitamos pedir datos de usuario
        if (opcion == 4) {
            ejecutarTestDeposito(); // Llamamos a un método auxiliar para mantener el main limpio
            sc.close();
            return; // Salimos después del test
        }

        // Para las otras opciones, pedimos los datos
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Mail: ");
        String mail = sc.nextLine();

        System.out.print("Contraseña: ");
        String contraseña = sc.nextLine();

        if (opcion == 1) {
            Donante donante = new Donante(1, nombre, mail, contraseña);
            donante.registrar();

            System.out.println("\n--- Nueva Donación ---");
            System.out.print("Ingrese el tipo de donación (ej. Ropa, Alimentos): ");
            String tipo = sc.nextLine();

            System.out.print("Ingrese la cantidad: ");
            int cantidad = sc.nextInt();
            sc.nextLine();

            try {
                Donacion d1 = new Donacion(tipo, cantidad);
                donante.realizarDonacion(d1);
                gestor.guardarDonacion(d1);
                System.out.println(" Donación registrada con éxito en la base de datos.");
                donante.verHistorial();

            } catch (Donacion.CantidadInvalidaException e) {
                System.out.println("\n NO SE PUDO REGISTRAR LA DONACIÓN");
                System.out.println("Razón: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(" Ocurrió un error inesperado: " + e.getMessage());
            }

        } else if (opcion == 2) {
            Administrador admin = new Administrador(2, nombre, mail, contraseña);
            admin.registrar();
            System.out.println("\n=== Reporte General de Donaciones ===");
            admin.generarReporteDonaciones(gestor.obtenerDonaciones());

        } else if (opcion == 3) {
            Voluntario voluntario = new Voluntario(3, nombre, mail, contraseña);
            voluntario.registrar();
            System.out.println("\n=== Tareas Pendientes (Donaciones sin entregar) ===");
            voluntario.verTareasAsignadas(gestor.obtenerDonaciones());

        } else {
            System.out.println("Opción inválida");
        }

        sc.close();
    }

    // --- MÉTODO AUXILIAR PARA EL TEST RÁPIDO ---
    private static void ejecutarTestDeposito() {
        System.out.println("\n=== INICIO TEST: DEPÓSITO LLENO ===");
        Deposito depositoTest = new Deposito("Depósito Experimental");
        System.out.println("Se creó: " + depositoTest.getUbicacion() + " (Capacidad máx: 15)");

        try {
            // Intentamos agregar 16 donaciones (1 más del límite)
            for (int i = 1; i <= 16; i++) {
                Donacion d = new Donacion("Item de prueba " + i, 1);
                System.out.print("Intentando agregar donación #" + i + "... ");
                depositoTest.agregarDonacion(d); // Esto lanzará la excepción en el intento 16
            }
        } catch (Deposito.DepositoLlenoException e) {
            System.out.println("\n\n ¡TEST EXITOSO! Se capturó la excepción esperada:");
            System.out.println(" ---> " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n TEST FALLIDO: Ocurrió otra excepción no esperada: " + e.getMessage());
        }
        System.out.println("===================================\n");
    }
}