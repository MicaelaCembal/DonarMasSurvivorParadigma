package modelo.datos;

import java.util.List;

public interface IGestorDatos<T> {
    void guardar(T entidad);
    List<T> obtenerTodos();
}
