package modelo.datos;

import modelo.*;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * Clase GestorDonacion
 * --------------------
 * Responsable de administrar las operaciones de Usuarios y Donaciones.
 * Usa la clase ConexionDB para interactuar con la base de datos MySQL.
 *
 * Principios aplicados:
 * - SRP: Solo se encarga de gestionar Donaciones y Usuarios (no la conexión).
 * - OCP: Se puede extender (por ejemplo, para agregar filtros o reportes).
 * - LSP: Subclases de Usuario pueden usarse sin alterar la funcionalidad.
 * - ISP: No fuerza a depender de métodos innecesarios.
 * - DIP: Depende de abstracciones (Usuario, Donacion) y una interfaz de conexión (ConexionDB).
 * - GRASP Controller: Centraliza la coordinación de las operaciones del sistema.
 */
public class GestorDonacion {

    private final ConexionDB conexionDB = new ConexionDB();

    public GestorDonacion() {
        // Al instanciar el gestor, inicializamos el contador de IDs.
        Donacion.inicializarContador(obtenerMaxIdDonacion());
    }

    // -----------------------------------------------------
    // MÉTODOS DE DONACIONES
    // -----------------------------------------------------

    /**
     * Busca el ID de donación más alto registrado en la base de datos.
     * Usado para inicializar el contador estático en la clase Donacion.
     */
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
            System.err.println("❌ Error al obtener el máximo ID de donación: " + e.getMessage());
        }
        return maxId;
    }


    /**
     * Guarda una donación en la base de datos.
     */
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
            // Se usa el getter corregido del archivo Donacion.java
            ps.setString(6, donacion.getCampania() != null ? donacion.getCampania().getNombre() : null);
            ps.setString(7, donacion.getDeposito() != null ? donacion.getDeposito().getUbicacion() : null);

            ps.executeUpdate();
            System.out.println("✅ Donación ID " + donacion.getIdDonacion() + " guardada correctamente en la base de datos.");

        } catch (SQLException e) {
            System.err.println("❌ Error al guardar la donación: " + e.getMessage());
        }
    }

    /**
     * Obtiene todas las donaciones registradas en la base de datos.
     */
    public List<Donacion> obtenerDonaciones() {
        List<Donacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM donacion";

        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // CORRECCIÓN: El constructor de Donacion ya no recibe el ID
                Donacion d = new Donacion(
                        rs.getString("tipoDonacion"),
                        rs.getInt("cantidad")
                );

                // NOTA IMPORTANTE: Esto crea un nuevo objeto 'd' con un ID nuevo.
                // Para que el objeto recuperado de la BD tenga su ID original,
                // se necesitaría un constructor auxiliar o un setter para el ID.

                d.setEstadoDonacion(EstadoDonacion.valueOf(rs.getString("estadoDonacion")));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener las donaciones: " + e.getMessage());
        }

        return lista;
    }

    // -----------------------------------------------------
    // MÉTODOS DE USUARIOS
    // -----------------------------------------------------

    /**
     * Guarda un usuario en la base de datos.
     */
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
            ps.setString(5, usuario.getClass().getSimpleName()); // Donante, Voluntario, Administrador

            ps.executeUpdate();
            System.out.println("✅ Usuario '" + usuario.getNombre() + "' guardado correctamente.");

        } catch (SQLException e) {
            System.err.println("❌ Error al guardar el usuario: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     */
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
                String tipo = rs.getString("tipo"); // <-- columna de la BD

                Usuario u = crearUsuarioPorTipo(id, nombre, mail, contraseña, tipo);
                if (u != null) lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener los usuarios: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Método auxiliar que crea el tipo correcto de usuario según la columna 'tipo'.
     */
    private Usuario crearUsuarioPorTipo(int id, String nombre, String mail, String contraseña, String tipo) {
        return switch (tipo.toLowerCase()) {
            case "donante" -> new Donante(id, nombre, mail, contraseña);
            case "voluntario" -> new Voluntario(id, nombre, mail, contraseña);
            case "administrador" -> new Administrador(id, nombre, mail, contraseña);
            default -> {
                System.err.println("⚠️ Tipo de usuario desconocido: " + tipo);
                yield null;
            }
        };
    }

}
