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
            // AHORA USAMOS EL CAMPO DE TEXTO COMO MAIL
            String mail = vista.getTxtNombre().getText().trim();
            String clave = vista.getPwdClave().getText().trim();

            if (mail.isEmpty() || clave.isEmpty()) {
                vista.getLblMensaje().setText("Por favor, complete mail y clave.");
                return;
            }

            // USAMOS EL NUEVO METODO DE BUSQUEDA POR MAIL
            Usuario usuario = gestor.buscarUsuarioPorMail(mail);

            // Validamos: 1) Existe usuario, 2) Contraseña coincide, 3) Es tipo Donante
            if (usuario != null && usuario.getContraseña().equals(clave)) {

                if (usuario.getClass().getSimpleName().equalsIgnoreCase("Donante")) {
                    vista.getLblMensaje().setText("Acceso correcto. Bienvenido " + usuario.getNombre() + ".");

                    // Abrir la ventana de registro de donaciones
                    FormRegistrarDonacion registrarDonacion = new FormRegistrarDonacion();
                    new ControladorDonacion(registrarDonacion);
                    registrarDonacion.setVisible(true);

                    // Cerrar la ventana de acceso
                    vista.dispose();
                } else {
                    // Si es Admin o Voluntario, por ahora no tiene ventana grafica
                    vista.getLblMensaje().setText("Acceso solo para Donantes en esta version.");
                }

            } else {
                vista.getLblMensaje().setText("Usuario o clave incorrectos.");
            }
        }
    }
}