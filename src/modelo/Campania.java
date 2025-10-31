package modelo;

import java.util.ArrayList;
import java.util.List;

public class Campania implements IAsociable {
    private int idCampania;
    private String nombre;
    private String descripcion;
    private List<Donacion> listaDonaciones;

    public Campania() {
        listaDonaciones = new ArrayList<>();
    }


    public void asociarDonacion(Donacion donacion) {
        listaDonaciones.add(donacion);
    }

    @Override
    public void agregarDonacion(Donacion donacion) {

    }

    @Override
    public String getIdentificador() {
        return "Campaña: " + nombre;
    }

    public List<Donacion> obtenerDonaciones() {
        return listaDonaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


