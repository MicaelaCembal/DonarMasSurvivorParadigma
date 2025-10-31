package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormAcceso extends JFrame {
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JPanel pnlPrincipal;
    private JTextField txtNombre;
    private JFormattedTextField pwdClave;
    private JButton btnIngresar;
    private JLabel lblMensaje;

    public FormAcceso(){
        inicializar();

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Verificamos usuario y clave
                String usuario = txtNombre.getText();
                String clave = pwdClave.getText();

                if(usuario.equals("admin") && clave.equals("123")){
                    lblMensaje.setText("Acceso correcto");

                    // Crear y mostrar la siguiente ventana
                    FormUsuarios ventanaUsuarios = new FormUsuarios();
                    ventanaUsuarios.setVisible(true);

                    // Cerrar la ventana actual
                    dispose();

                } else {
                    lblMensaje.setText("Usuario o clave incorrectos");
                }
            }
        });
    }

    private void inicializar(){
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo(null);
        setTitle("Formulario de Acceso");
    }
}
