import controlador.ControladorAcceso;
import vista.FormAcceso;

public class Main {
    public static void main(String[] args) {
        FormAcceso acceso = new FormAcceso();
        new ControladorAcceso(acceso);
        acceso.setVisible(true);

    }
}
