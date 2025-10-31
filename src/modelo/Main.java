package modelo;

import modelo.datos.ConexionDB;
import modelo.datos.GestorDonacion;

public class Main {
    public static void main(String[] args) {

        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();  // crea la base donarmas y la tabla donacion

        GestorDonacion gestor = new GestorDonacion();

        // ----------------------------------------------------------------------
        // CORRECCIÓN: Se elimina el ID manual, ahora lo asigna Donacion.java
        // ----------------------------------------------------------------------
        Donacion d1 = new Donacion("Ropa", 30); // Nuevo constructor: (tipo, cantidad)
        gestor.guardarDonacion(d1);

        System.out.println("Donaciones guardadas en la base:");
        for (Donacion d : gestor.obtenerDonaciones()) {
            d.mostrarDonacion();
        }
    }
}