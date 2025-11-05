package modelo.datos;

import modelo.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorDonacion {

    private final ConexionDB conexionDB = new ConexionDB();

    public GestorDonacion() {
        Donacion.inicializarContador(obtenerMaxIdDonacion());
    }

    public int obtenerMaxIdDonacion() {
        String sql = "SELECT MAX(idDonacion) FROM donacion";
        int maxId = 0;
        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener max ID: " + e.getMessage());
        }
        return maxId;
    }

    public void guardarDonacion(Donacion donacion) {
        String sql = """
            INSERT INTO donacion (idDonacion, tipoDonacion, cantidad, fecha, estadoDonacion, campania, deposito)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, donacion.getIdDonacion());
            ps.setString(2, donacion.getTipoDonacion());
            ps.setInt(3, donacion.getCantidad());
            // Guardamos la fecha usando Timestamp para que coincida con DATETIME de MySQL
            ps.setTimestamp(4, Timestamp.valueOf(donacion.getFecha()));
            ps.setString(5, donacion.getEstadoDonacion().toString());
            ps.setString(6, donacion.getCampania() != null ? donacion.getCampania().getNombre() : null);
            ps.setString(7, donacion.getDeposito() != null ? donacion.getDeposito().getUbicacion() : null);
            ps.executeUpdate();
            System.out.println("Donación ID " + donacion.getIdDonacion() + " guardada en DB.");
        } catch (SQLException e) {
            System.err.println("Error al guardar donación: " + e.getMessage());
        }
    }

    public List<Donacion> obtenerDonaciones() {
        List<Donacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM donacion";
        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // --- USO DEL CONSTRUCTOR DE DB (NO LANZA EXCEPCIÓN) ---
                Donacion d = new Donacion(
                        rs.getInt("idDonacion"),
                        rs.getString("tipoDonacion"),
                        rs.getInt("cantidad"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        EstadoDonacion.valueOf(rs.getString("estadoDonacion"))
                );
                // ------------------------------------------------------
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener donaciones: " + e.getMessage());
        }
        return lista;
    }

    // (El resto de métodos para Usuario siguen igual, si los tenías implementados)
}