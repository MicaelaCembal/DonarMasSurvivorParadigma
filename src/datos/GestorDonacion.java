package datos;

import TrabajoPOO.Donacion;
import TrabajoPOO.EstadoDonacion;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorDonacion {

    private ConexionDB conexionDB = new ConexionDB();

    // -----------------------------------------------------
    // MÉTODO PARA GUARDAR UNA DONACIÓN EN LA BASE DE DATOS
    // -----------------------------------------------------
    public void guardarDonacion(Donacion donacion) {
        String sql = "INSERT INTO donacion (idDonacion, tipoDonacion, cantidad, fecha, estadoDonacion, campania, deposito) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, donacion.getIdDonacion());
            ps.setString(2, donacion.getTipoDonacion());
            ps.setInt(3, donacion.getCantidad());
            ps.setString(4, donacion.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setString(5, donacion.getEstadoDonacion().toString());
            ps.setString(6, donacion.getCampaña() != null ? donacion.getCampaña().getNombre() : null);
            ps.setString(7, donacion.getDeposito() != null ? donacion.getDeposito().getUbicacion() : null);

            ps.executeUpdate();
            System.out.println("✅ Donación guardada correctamente en la base de datos.");

        } catch (SQLException e) {
            System.err.println("❌ Error al guardar la donación: " + e.getMessage());
        }
    }

    // -----------------------------------------------------
    // MÉTODO PARA OBTENER TODAS LAS DONACIONES
    // -----------------------------------------------------
    public List<Donacion> obtenerDonaciones() {
        List<Donacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM donacion";

        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Donacion d = new Donacion(
                        rs.getInt("idDonacion"),
                        rs.getString("tipoDonacion"),
                        rs.getInt("cantidad")
                );
                d.setEstadoDonacion(EstadoDonacion.valueOf(rs.getString("estadoDonacion")));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener las donaciones: " + e.getMessage());
        }

        return lista;
    }
}
