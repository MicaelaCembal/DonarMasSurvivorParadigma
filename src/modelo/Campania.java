package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Campania implements IAsociable {
    private int idCampania;
    private String nombre;
    private String descripcion;
    private List<Donacion> listaDonaciones;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Campania() {
        listaDonaciones = new ArrayList<>();
    }


    @Override
    public void agregarDonacion(Donacion donacion) {
        listaDonaciones.add(donacion);
    }

    @Override
    public String getIdentificador() {
        return "Campaña: " + nombre;
    }

    public List<Donacion> obtenerDonaciones() {
        return listaDonaciones;
    }

    // Métodos de gestión de donaciones (vistos en el UML)
    public void eliminarDonacion(Donacion d) {
        listaDonaciones.remove(d);
        System.out.println("Donación ID " + d.getIdDonacion() + " eliminada de la campaña " + nombre);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCampania() {
        return idCampania;
    }

    public void setIdCampania(int idCampania) {
        this.idCampania = idCampania;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}


