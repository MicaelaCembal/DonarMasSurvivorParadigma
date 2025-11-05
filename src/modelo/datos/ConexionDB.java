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
            System.out.println("✅ Conexión exitosa a la base de modelo.datos: " + DB_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: no se encontró el driver JDBC.");
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de modelo.datos: " + e.getMessage());
        }
        return conexion;
    }

    public void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
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

            System.out.println("✅ Base de modelo.datos y tabla 'donacion' creadas correctamente.");

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ Error al crear la base o tabla: " + e.getMessage());
        } finally {
            cerrarConexion(conexion);
        }
    }
}
