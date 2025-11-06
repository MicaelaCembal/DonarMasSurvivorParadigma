package modelo;

import java.util.ArrayList;
import java.util.List;

public class Donante extends Usuario {

    private List<Donacion> listaDonaciones;

    public Donante(int idUsuario, String nombre, String mail, String contraseña) {
        super(idUsuario, nombre, mail, contraseña);
        this.listaDonaciones = new ArrayList<>();
    }

    public void realizarDonacion(Donacion d) {
        listaDonaciones.add(d);
        System.out.println(nombre + " realizó una donación de tipo " + d.getTipoDonacion());
    }

    public void verHistorial() {  //Falto la implementacion
        System.out.println("Historial de donaciones de " + nombre + ":");
        for (Donacion d : listaDonaciones)
            d.mostrarDonacion();
    }

    public void consultarEstadoDonacion() { //Falto la implementacion
        for (Donacion d : listaDonaciones)
            System.out.println("Donación ID " + d.getIdDonacion() + ": " + d.getEstadoDonacion());
    }
}

