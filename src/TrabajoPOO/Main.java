package TrabajoPOO;

import datos.ConexionDB;
import datos.GestorDonacion;

public class Main {
    public static void main(String[] args) {

        ConexionDB conexionDB = new ConexionDB();
        conexionDB.crearBaseYTablas();  // crea la base donarmas y la tabla donacion

        GestorDonacion gestor = new GestorDonacion();

        Donacion d1 = new Donacion(1, "Ropa", 30);
        gestor.guardarDonacion(d1);

        System.out.println("Donaciones guardadas en la base:");
        for (Donacion d : gestor.obtenerDonaciones()) {
            d.mostrarDonacion();
        }
    }
}
