package TrabajoPOO;

import java.util.List;
import java.time.LocalDateTime;

public class Campania {
    private int idCampania;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Donacion> listaDonaciones;

    public void agregarDonacion(Donacion d) {}
    public void eliminarDonacion(Donacion d) {}
    public List<Donacion> obtenerDonaciones() { return listaDonaciones; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
