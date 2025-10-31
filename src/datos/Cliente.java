package datos;

public class Cliente {
    private int codigo;
    private String nombre;
    private String telefono;

    public Cliente(int codigo, String nombre, String telefono) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    ////////
    @Override
    public String toString() {
        return "Cliente --> "+  codigo +" - "+ nombre + " - " + telefono ;
    }
}
