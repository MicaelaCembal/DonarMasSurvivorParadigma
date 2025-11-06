package controlador;

import modelo.Usuario;
import modelo.datos.GestorDonacion;
import modelo.datos.GestorUsuario;
import vista.FormAcceso;
import vista.FormRegistrarDonacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorAcceso implements ActionListener {

    private FormAcceso vista;
    private GestorDonacion gestorDonacion;
    private GestorUsuario gestorUsuario;

    public ControladorAcceso(FormAcceso vista) {
        this.vista = vista;
        this.gestorDonacion = new GestorDonacion();
        this.gestorUsuario = new GestorUsuario();
        this.vista.getBtnIngresar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnIngresar()) {
            String mail = vista.getTxtNombre().getText().trim();
            String clave = vista.getPwdClave().getText().trim();

            if (mail.isEmpty() || clave.isEmpty()) {
                vista.getLblMensaje().setText("Por favor, complete mail y clave.");
                return;
            }

            Usuario usuario = gestorUsuario.buscarUsuarioPorMail(mail);

            if (usuario != null && usuario.getContraseña().equals(clave)) {
                if (usuario.getClass().getSimpleName().equalsIgnoreCase("Donante")) {
                    vista.getLblMensaje().setText("Acceso correcto. Bienvenido " + usuario.getNombre() + ".");
                    FormRegistrarDonacion registrarDonacion = new FormRegistrarDonacion();
                    new ControladorDonacion(registrarDonacion);
                    registrarDonacion.setVisible(true);
                    vista.dispose();
                } else {
                    vista.getLblMensaje().setText("Acceso solo para Donantes en esta version.");
                }
            } else {
                vista.getLblMensaje().setText("Usuario o clave incorrectos.");
            }
        }
    }
}
