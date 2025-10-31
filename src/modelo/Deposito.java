package modelo;

import java.util.ArrayList;
import java.util.List;

public class Deposito implements IAsociable {
    private int idDeposito;
    private String ubicacion;
    private List<Donacion> listaDonaciones;

    public Deposito() {
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
        return "Depósito: " + ubicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Donacion> getListaDonaciones() {
        return listaDonaciones;
    }
}

