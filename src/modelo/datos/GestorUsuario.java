package modelo.datos;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuario {

    public static class UsuarioNoEncontradoException extends Exception {
        public UsuarioNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }

    private final ConexionDB conexionDB = new ConexionDB();

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
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearUsuarioPorTipo(
                            rs.getInt("idUsuario"),
                            rs.getString("nombre"),
                            rs.getString("mail"),
                            rs.getString("contraseña"),
                            rs.getString("tipo")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    public void eliminarUsuario(int idUsuario) throws UsuarioNoEncontradoException {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        try (Connection conn = conexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new UsuarioNoEncontradoException("No se encontró ningun usuario con ID " + idUsuario);
            }
            System.out.println("Usuario eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
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
        if (tipo == null) return null;
        return switch (tipo.toLowerCase()) {
            case "donante" -> new Donante(id, nombre, mail, pass);
            case "voluntario" -> new Voluntario(id, nombre, mail, pass);
            case "administrador" -> new Administrador(id, nombre, mail, pass);
            default -> null;
        };
    }
}
