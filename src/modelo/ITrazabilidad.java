package modelo;

public interface ITrazabilidad {
    void obtenerHistorial();
    void notificarEntrega(Donacion d);
}