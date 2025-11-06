package controlador;

import modelo.Usuario;
import modelo.datos.GestorDonacion;
import vista.FormAcceso;
import vista.FormRegistrarDonacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorAcceso implements ActionListener {

    private FormAcceso vista;
    private GestorDonacion gestor;

    public ControladorAcceso(FormAcceso vista) {
        this.vista = vista;
        this.gestor = new GestorDonacion();
        this.vista.getBtnIngresar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnIngresar()) {
            String nombre = vista.getTxtNombre().getText().trim();
            String clave = vista.getPwdClave().getText().trim();

            if (nombre.isEmpty() || clave.isEmpty()) {
                vista.getLblMensaje().setText("Por favor, complete usuario y clave.");
                return;
            }

            Usuario usuario = gestor.buscarUsuarioPorNombre(nombre);

            if (usuario != null && usuario.getContraseña().equals(clave)
                    && usuario.getClass().getSimpleName().equalsIgnoreCase("Donante")) {

                vista.getLblMensaje().setText("Acceso correcto. Bienvenido " + usuario.getNombre() + ".");
                FormRegistrarDonacion registrarDonacion = new FormRegistrarDonacion();
                new ControladorDonacion(registrarDonacion);
                registrarDonacion.setVisible(true);
                vista.dispose();

            } else {
                vista.getLblMensaje().setText("Usuario o clave incorrectos.");
            }
        }
    }
}
