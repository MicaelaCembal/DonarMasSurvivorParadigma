package TrabajoPOO;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Donante donante = new Donante(1, "Micaela", "mica@correo.com", "1234");
        donante.registrar();

        Donacion donacion1 = new Donacion(101, "Ropa", 20);
        Donacion donacion2 = new Donacion(102, "Comida", 50);

        donante.realizarDonacion(donacion1);
        donante.realizarDonacion(donacion2);

        Campania campania = new Campania();
        campania.setNombre("Ayuda Invernal");

        donacion1.asignarCampania(campania);
        donacion2.asignarCampania(campania);

        Deposito deposito = new Deposito();
        deposito.setUbicacion("Charcas 4145, CABA");

        donacion1.asignarDeposito(deposito);
        donacion2.asignarDeposito(deposito);

        donacion1.actualizarEstadoDonacion(EstadoDonacion.APROBADA);
        donacion2.actualizarEstadoDonacion(EstadoDonacion.ENTREGADO);

        System.out.println("\n--- Mostrando donaciones individuales ---");
        donacion1.mostrarDonacion();
        donacion2.mostrarDonacion();

        System.out.println("\n--- Mostrando historial de donante ---");
        donante.verHistorial();

        System.out.println("\n--- Mostrando estados de donaciones ---");
        donante.consultarEstadoDonacion();
    }
}
