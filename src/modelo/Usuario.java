package modelo;

public abstract class Usuario implements IRegistrable {


    protected int idUsuario;
    protected String nombre;
    protected String mail;
    protected String contraseña;


    public Usuario(int idUsuario, String nombre, String mail, String contraseña) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.mail = mail;
        this.contraseña = contraseña;
    }

    //Interfaz
    @Override
    public void registrar() {
        System.out.println(nombre + " fue registrado en el sistema.");
    }

    @Override
    public void eliminar() {
        System.out.println(nombre + " fue eliminado del sistema.");
    }


    public void mostrarDatos() {
        System.out.println("ID: " + idUsuario);
        System.out.println("Nombre: " + nombre);
        System.out.println("Mail: " + mail);
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}

