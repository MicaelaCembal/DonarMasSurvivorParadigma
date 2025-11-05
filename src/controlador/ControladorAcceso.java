package controlador;

import vista.FormAcceso;
import vista.FormRegistrarDonacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorAcceso implements ActionListener {

    private FormAcceso vista;

    public ControladorAcceso(FormAcceso vista) {
        this.vista = vista;
        this.vista.getBtnIngresar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnIngresar()) {
            String usuario = vista.getTxtNombre().getText();
            String clave = vista.getPwdClave().getText();

            if (usuario.equals("") || clave.equals("")) {
                vista.getLblMensaje().setText("Por favor, complete usuario y clave.");
                return;
            }

            if (usuario.equals("donante") && clave.equals("123")) {
                vista.getLblMensaje().setText("Acceso correcto.");
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
