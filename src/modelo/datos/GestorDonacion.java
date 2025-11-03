package modelo.datos;

import modelo.*;
import java.sql.*;
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
            System.err.println("Error al obtener el máximo ID de donación: " + e.getMessage());
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
            ps.setString(4, donacion.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setString(5, donacion.getEstadoDonacion().toString());
            ps.setString(6, donacion.getCampania() != null ? donacion.getCampania().getNombre() : null);
            ps.setString(7, donacion.getDeposito() != null ? donacion.getDeposito().getUbicacion() : null);

            ps.executeUpdate();
            System.out.println("Donación ID " + donacion.getIdDonacion() + " guardada correctamente en la base de datos.");

        } catch (SQLException e) {
            System.err.println("Error al guardar la donación: " + e.getMessage());
        }
    }

    public List<Donacion> obtenerDonaciones() {
        List<Donacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM donacion";

        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Donacion d = new Donacion(
                        rs.getString("tipoDonacion"),
                        rs.getInt("cantidad")
                );
                d.setEstadoDonacion(EstadoDonacion.valueOf(rs.getString("estadoDonacion")));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las donaciones: " + e.getMessage());
        }

        return lista;
    }

    public void guardarUsuario(Usuario usuario) {
        String sql = """
            INSERT INTO usuario (idUsuario, nombre, mail, contraseña, tipo)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuario.getIdUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getContraseña());
            ps.setString(5, usuario.getClass().getSimpleName());

            ps.executeUpdate();
            System.out.println("Usuario '" + usuario.getNombre() + "' guardado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String mail = rs.getString("mail");
                String contraseña = rs.getString("contraseña");
                String tipo = rs.getString("tipo");

                Usuario u = crearUsuarioPorTipo(id, nombre, mail, contraseña, tipo);
                if (u != null) lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los usuarios: " + e.getMessage());
        }

        return lista;
    }

    private Usuario crearUsuarioPorTipo(int id, String nombre, String mail, String contraseña, String tipo) {
        return switch (tipo.toLowerCase()) {
            case "donante" -> new Donante(id, nombre, mail, contraseña);
            case "voluntario" -> new Voluntario(id, nombre, mail, contraseña);
            case "administrador" -> new Administrador(id, nombre, mail, contraseña);
            default -> null;
        };
    }
}
