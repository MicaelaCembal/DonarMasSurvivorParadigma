package modelo.datos;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorDonacion {

    // --- EXCEPCIÓN DE NEGOCIO PARA ELIMINACIÓN ---
    public static class UsuarioNoEncontradoException extends Exception {
        public UsuarioNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }
    // --------------------------------------------

    private final ConexionDB conexionDB = new ConexionDB();

    public GestorDonacion() {
        Donacion.inicializarContador(obtenerMaxIdDonacion());
    }

    // --- DONACIONES (Sin cambios) ---
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
            System.err.println("Error al obtener MAX ID donacion: " + e.getMessage());
        }
        return maxId;
    }

    public void guardarDonacion(Donacion donacion) {
        String sql = "INSERT INTO donacion (idDonacion, tipoDonacion, cantidad, fecha, estadoDonacion, campania, deposito) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, donacion.getIdDonacion());
            ps.setString(2, donacion.getTipoDonacion());
            ps.setInt(3, donacion.getCantidad());
            ps.setTimestamp(4, Timestamp.valueOf(donacion.getFecha()));
            ps.setString(5, donacion.getEstadoDonacion().toString());
            ps.setString(6, donacion.getCampania() != null ? donacion.getCampania().getNombre() : null);
            ps.setString(7, donacion.getDeposito() != null ? donacion.getDeposito().getUbicacion() : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar donacion: " + e.getMessage());
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
                        rs.getInt("idDonacion"),
                        rs.getString("tipoDonacion"),
                        rs.getInt("cantidad"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        EstadoDonacion.valueOf(rs.getString("estadoDonacion"))
                );
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener donaciones: " + e.getMessage());
        }
        return lista;
    }

    // --- USUARIOS ---

    public void guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, mail, contraseña, tipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getMail());
            ps.setString(3, usuario.getContraseña());
            ps.setString(4, usuario.getClass().getSimpleName());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Aviso: El mail ya esta registrado en la DB.");
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    public Usuario buscarUsuarioPorMail(String mail) {
        String sql = "SELECT * FROM usuario WHERE mail = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mail);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return crearUsuarioPorTipo(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("mail"),
                        rs.getString("contraseña"),
                        rs.getString("tipo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    // METODO MODIFICADO: Ahora lanza la excepción si no encuentra al usuario
    public void eliminarUsuario(int idUsuario) throws UsuarioNoEncontradoException {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                // Si llegamos aqui, es porque el ID no existia en la DB. Lanzamos la excepcion.
                throw new UsuarioNoEncontradoException("No se encontro ningun usuario con el ID " + idUsuario + " para eliminar.");
            }
            // Si pasa el if, es que sí se eliminó.
            System.out.println("Usuario con ID " + idUsuario + " eliminado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error tecnico al intentar eliminar: " + e.getMessage());
        }
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = crearUsuarioPorTipo(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("mail"),
                        rs.getString("contraseña"),
                        rs.getString("tipo")
                );
                if (u != null) lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }

    private Usuario crearUsuarioPorTipo(int id, String nombre, String mail, String pass, String tipo) {
        return switch (tipo.toLowerCase()) {
            case "donante" -> new Donante(id, nombre, mail, pass);
            case "voluntario" -> new Voluntario(id, nombre, mail, pass);
            case "administrador" -> new Administrador(id, nombre, mail, pass);
            default -> null;
        };
    }
}