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

        System.out.println("Bienvenido al sistema de donaciones");
        System.out.println("1. Donante");
        System.out.println("2. Administrador");
        System.out.println("3. Voluntario");
        System.out.print("Elegí una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Mail: ");
        String mail = sc.nextLine();

        System.out.print("Contraseña: ");
        String contraseña = sc.nextLine();

        if (opcion == 1) {
            Donante donante = new Donante(1, nombre, mail, contraseña);
            donante.registrar();

            Donacion d1 = new Donacion("Ropa", 10);
            donante.realizarDonacion(d1);
            gestor.guardarDonacion(d1);

            donante.verHistorial();
        }
        else if (opcion == 2) {
            Administrador admin = new Administrador(2, nombre, mail, contraseña);
            admin.registrar();

            System.out.println("Lista de donaciones registradas:");
            admin.generarReporteDonaciones(gestor.obtenerDonaciones());
        }
        else if (opcion == 3) {
            Voluntario voluntario = new Voluntario(3, nombre, mail, contraseña);
            voluntario.registrar();

            System.out.println("Donaciones pendientes:");
            voluntario.verTareasAsignadas(gestor.obtenerDonaciones());
        }
        else {
            System.out.println("Opción inválida");
        }

        sc.close();
    }
}
