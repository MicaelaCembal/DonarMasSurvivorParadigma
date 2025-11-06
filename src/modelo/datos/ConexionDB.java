package modelo.datos;

import java.sql.*;

public class ConexionDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "donarmas";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "mysql";

    public Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL + DB_NAME, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: no se encontro el driver JDBC.");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }

    public void crearBaseYTablas() {
        Connection conexion = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            stmt = conexion.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            stmt.executeUpdate("USE " + DB_NAME);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS donacion (
                    idDonacion INT PRIMARY KEY,
                    tipoDonacion VARCHAR(50),
                    cantidad INT,
                    fecha DATETIME,
                    estadoDonacion VARCHAR(30),
                    campania VARCHAR(50),
                    deposito VARCHAR(50)
                )
            """);
            System.out.println("Tabla 'donacion' verificada.");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS usuario (
                    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
                    nombre VARCHAR(100),
                    mail VARCHAR(100) UNIQUE,
                    contraseña VARCHAR(50),
                    tipo VARCHAR(30)
                )
            """);
            System.out.println("Tabla 'usuario' verificada.");

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al crear la base o tablas: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* ignorar */ }
            cerrarConexion(conexion);
        }
    }
}