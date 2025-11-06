package modelo;

import java.util.Scanner;
import modelo.datos.ConexionDB;
import modelo.datos.GestorDonacion;
import modelo.datos.GestorUsuario;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();

        GestorDonacion gestorDonacion = new GestorDonacion();
        GestorUsuario gestorUsuario = new GestorUsuario();

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
        Usuario usuarioActual = gestorUsuario.buscarUsuarioPorMail(mail);

        if (usuarioActual == null) {
            System.out.println(">>> Usuario nuevo. Completa tu registro.");
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
            gestorUsuario.guardarUsuario(usuarioActual);
            usuarioActual = gestorUsuario.buscarUsuarioPorMail(mail);
        }
        System.out.println("Hola, " + usuarioActual.getNombre() + " (" + usuarioActual.getClass().getSimpleName() + ")");

        //  Opciones segun el tipo de usuario

        if (usuarioActual instanceof Donante donante) {
            System.out.println("\n--- Panel de Donante ---");
            System.out.print("¿Nueva donacion? (S/N): ");
            if (sc.nextLine().equalsIgnoreCase("S")) {
                System.out.print("Tipo (ej. Ropa): ");
                String tipo = sc.nextLine();
                System.out.print("Cantidad: ");
                int cant = sc.nextInt();
                sc.nextLine();


                System.out.print("Nombre Deposito: ");
                String nomDep = sc.nextLine();
                System.out.print("Nombre Campaña: ");
                String nomCamp = sc.nextLine();

                try {
                    Donacion d = new Donacion(tipo, cant);


                    if (!nomDep.isEmpty()) {
                        d.asignarDeposito(new Deposito(nomDep));
                    }

                    if (!nomCamp.isEmpty()) {
                        Campania c = new Campania();
                        c.setNombre(nomCamp);
                        d.asignarCampania(c);
                    }

                    gestorDonacion.guardarDonacion(d);
                    donante.realizarDonacion(d);
                    System.out.println("¡Gracias! Donacion registrada con exito.");

                    if (d.getDeposito() != null) {
                        d.getDeposito().mostrarInventario();
                    }

                } catch (Exception e) {
                    System.out.println("Error al registrar: " + e.getMessage());
                }
            }

        } else if (usuarioActual instanceof Administrador admin) {
            System.out.println("\n--- Panel de Administrador ---");
            System.out.println("1. Ver Reporte General");
            System.out.println("2. Eliminar Usuario");
            System.out.print("Opcion: ");
            int opAdmin = sc.nextInt();
            if (opAdmin == 1) {
                admin.generarReporteDonaciones(gestorDonacion.obtenerDonaciones());
            } else {
                System.out.println("Lista de usuarios:");
                gestorUsuario.obtenerUsuarios().forEach(u -> System.out.println("#" + u.getIdUsuario() + " " + u.getNombre() + " (" + u.getMail() + ")"));
                System.out.print("ID a eliminar: ");
                try {
                    gestorUsuario.eliminarUsuario(sc.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        } else if (usuarioActual instanceof Voluntario voluntario) {
            System.out.println("\n--- Panel de Voluntario ---");
            List<Donacion> donaciones = gestorDonacion.obtenerDonaciones();
            voluntario.verTareasAsignadas(donaciones);

            System.out.println("\n¿Deseas gestionar el estado de una donacion? (S/N)");
            if (sc.nextLine().equalsIgnoreCase("S")) {
                System.out.print("Ingresa el ID de la donacion a actualizar: ");
                int idDon = sc.nextInt();
                System.out.println("Selecciona el nuevo estado:");
                System.out.println("1. EN_CAMINO");
                System.out.println("2. ENTREGADO");
                System.out.println("3. CANCELADO");
                System.out.println("4. APROBADA");
                System.out.print("Opcion: ");
                int opEstado = sc.nextInt();

                EstadoDonacion nuevoEstado = switch (opEstado) {
                    case 1 -> EstadoDonacion.EN_CAMINO;
                    case 2 -> EstadoDonacion.ENTREGADO;
                    case 3 -> EstadoDonacion.CANCELADO;
                    case 4 -> EstadoDonacion.APROBADA;
                    default -> EstadoDonacion.PENDIENTE;
                };

                if (nuevoEstado != EstadoDonacion.PENDIENTE) {
                    boolean encontrada = false;
                    for (Donacion d : donaciones) {
                        if (d.getIdDonacion() == idDon) {
                            // Actualiza en memoria (usa el metodo de Voluntario)
                            voluntario.registrarActualizacionEstado(d, nuevoEstado);
                            // Actualiza en DB (usa el Gestor)
                            gestorDonacion.actualizarEstadoDonacionDB(idDon, nuevoEstado);
                            encontrada = true;
                            break;
                        }
                    }
                    if (!encontrada) {
                        System.out.println("No se encontro una donacion con ese ID.");
                    }
                } else {
                    System.out.println("Opcion de estado invalida o sin cambios.");
                }
            }
        }

        sc.close();
    }

    private static void ejecutarTestDeposito() {
        System.out.println("\n=== INICIO TEST: DEPOSITO LLENO ===");
        Deposito depositoTest = new Deposito("Deposito Experimental");
        try {
            for (int i = 1; i <= Deposito.CAPACIDAD_MAXIMA + 1; i++) {
                Donacion d = new Donacion("Item " + i, 1);
                depositoTest.agregarDonacion(d);
            }
        } catch (Deposito.DepositoLlenoException e) {
            System.out.println("EXITO DEL TEST: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("FALLO EL TEST: " + e.getMessage());
        }
        System.out.println("===================================\n");
    }
}