package datos;

import java.sql.*;


public class conexionDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String BBDD = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "negocio";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123";
//    private static final String PASSWORD = "mysql";

    public Connection conexionBBDD() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(BBDD + DB_NAME, USUARIO, PASSWORD);
            System.out.println("conexion ok a " + DB_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("Error en DRIVER\n" + e);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la BBDD\n" + e);
        }
        return conexion;
    }

    /// ////////////////////////////////////////////////
    public void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al cerrar la conexión con la base de datos." + e);
        }
    }

    /// ////////////////////////////////////////////////
    public void crearTablayBD() {
        Connection conexion = null;
        Statement declaracion = null;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(BBDD, USUARIO, PASSWORD);
            declaracion = conexion.createStatement();

            // Crear la base de datos si no existe
            String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            declaracion.executeUpdate(sql);

            // Usar la base de datos
            declaracion.executeUpdate("USE " + DB_NAME);

            // Crear la tabla si no existe
            sql = "CREATE TABLE IF NOT EXISTS cliente (" +
                    "idCliente INT  PRIMARY KEY, " +
                    "nombre VARCHAR(50)  NOT NULL, " +
                    "telefono  VARCHAR(15) NOT NULL)";
            declaracion.executeUpdate(sql);
            System.out.println("Base de datos y tabla creadas correctamente.");

        } catch (ClassNotFoundException e) {
            System.err.println("Error en Driver.\n" + e);
        } catch (SQLException e) {
            System.err.println("Error en la conexión con Base de datos.\n" + e);
        } finally {
            cerrarConexion(conexion);
        }
    }

    /// ////////////////////////////////////////////////
    public void agregarCliente() {
        Connection conexion = conexionBBDD();
        if (conexion != null) {
            try {

                String consultaInsercion = String.format(
                        "INSERT INTO cliente (idCliente, nombre, telefono) " +
                                "VALUES (%d, '%s', '%s');",
                        10,                 // %d para idCliente (debe ser un INT)
                        "Maria",            // %s para nombre
                        "115689777"         // %s para telefono
                );

                System.out.println(consultaInsercion);
                Statement declaracion = conexion.createStatement();
                declaracion.executeUpdate(consultaInsercion);
                System.out.println("Datos insertados correctamente");

            } catch (SQLException e) {
                System.err.println("Se ha producido un error al insertar en la base de datos.\n" + e);
            } finally {
                cerrarConexion(conexion);
            }
        }
    }

    /// ///////////////////////////////////////////////////
    public boolean existeCliente(Cliente cliente)  {
        Connection conexion = conexionBBDD();
        if (conexion != null) {
            try {
                String consultaSQL = "SELECT COUNT(*) AS total FROM cliente WHERE idCliente = ?";
                PreparedStatement ps = conexion.prepareStatement(consultaSQL);
                ps.setInt(1, cliente.getCodigo());
                ResultSet rs = null;
                rs = ps.executeQuery();
                if (rs.next()) {
                    int totalRegistro = rs.getInt("total");
                    if (totalRegistro > 0) {
                        return true;//// existe
                    }
                    else {
                        return false; //no existe
                    }
                }

            } catch (SQLException e) {
                System.err.println("Se ha producido un error al insertar en la base de datos.\n" + e);
            } finally {
                cerrarConexion(conexion);
            }
        } else {
            System.out.println("No se pudo establecer la conexion");
            return false;
        }
        return false;
    }

    /// //////////////////////////////////////////////////
    public void agregarCliente(Cliente cliente) {
        Connection conexion = conexionBBDD();
        if (conexion != null) {
            int codigo = cliente.getCodigo();
            String nombre = cliente.getNombre();
            String telefono = cliente.getTelefono();
            if (!existeCliente(cliente)) {
                try {

                    String consultaInsercion = String.format(
                            "INSERT INTO cliente (idCliente, nombre, telefono) " +
                                    "VALUES (%d, '%s', '%s');", codigo, nombre, telefono
                    );
                    System.out.println(consultaInsercion);
                    Statement declaracion = conexion.createStatement();
                    declaracion.executeUpdate(consultaInsercion);
                    System.out.println("Datos insertados correctamente");

                } catch (SQLException e) {
                    System.err.println("Se ha producido un error al insertar en la base de datos.\n" + e);
                } finally {
                    cerrarConexion(conexion);
                }
            }
            else{
                System.out.println("EXISTE el Cliente. No se puede añadir");
            }
        }
    }
    /// ////////////////////////////////////////////////
    public void modificarCliente(Cliente cliente){
        Connection conexion = conexionBBDD();
        if (conexion != null) {

            try {

                String consultaInsercion = String.format(
                        "UPDATE  cliente  " +
                                " SET  nombre = %s','  telefono = %s"+
                                cliente.getNombre()+","+ cliente.getTelefono()
                );

                System.out.println(consultaInsercion);
                Statement declaracion = conexion.createStatement();
                declaracion.executeUpdate(consultaInsercion);
                System.out.println("Datos insertados correctamente");

            } catch (SQLException e) {
                System.err.println("Se ha producido un error al insertar en la base de datos.\n" + e);
            } finally {
                cerrarConexion(conexion);
            }
        }
    }
    /// //////////////////////////////////////////////////////
    public void mostrarClientes() {
        Connection conexion = conexionBBDD();
        if (conexion != null) {
            try {
                String consultaSeleccion = "SELECT * FROM cliente;";
                System.out.println(consultaSeleccion);
                Statement declaracion = conexion.createStatement();
                if (declaracion.execute(consultaSeleccion)) {
                    ResultSet resultSet = declaracion.getResultSet();
                    while (resultSet.next()) {
                        Cliente cliente = new Cliente(
                                resultSet.getInt("idCliente"),
                                resultSet.getString("nombre"),
                                resultSet.getString("telefono")

                        );
                        System.out.println(cliente.toString());
                    }
                }
                System.out.println("Datos recuperados correctamente");
                declaracion.close();
            } catch (SQLException e) {
                System.err.println("Se ha producido un error al recuperar los datos de la base de datos.\n" + e);
            } finally {
                cerrarConexion(conexion);
            }
        }
    }
    /// //////////////////////////////////////////////////////////////////////
    public void mostrarClientes(Cliente cliente) {
        Connection conexion = conexionBBDD();
        if (conexion != null) {
            if (existeCliente(cliente)) {
                try {
                    String consultaSeleccion = "SELECT * FROM cliente WHERE idCliente = " + cliente.getCodigo();
                    System.out.println(consultaSeleccion);
                    Statement declaracion = conexion.createStatement();
                    if (declaracion.execute(consultaSeleccion)) {
                        ResultSet resultSet = declaracion.getResultSet();
                        while (resultSet.next()) {
                            Cliente elCliente = new Cliente(
                                    resultSet.getInt("idCliente"),
                                    resultSet.getString("nombre"),
                                    resultSet.getString("telefono")

                            );
                            System.out.println(elCliente.toString());
                        }
                    }
                    System.out.println("Datos recuperados correctamente");
                    declaracion.close();
                } catch (SQLException e) {
                    System.err.println("Se ha producido un error al recuperar los datos de la base de datos.\n" + e);
                } finally {
                    cerrarConexion(conexion);
                }
            }
        }
    }

}


