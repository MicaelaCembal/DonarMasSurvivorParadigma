package vista;

import javax.swing.*;

public class FormAcceso extends JFrame {
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JPanel pnlPrincipal;
    private JTextField txtNombre;
    private JFormattedTextField pwdClave;
    private JButton btnIngresar;
    private JLabel lblMensaje;

    public FormAcceso() {
        inicializar();
    }

    private void inicializar() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Formulario de Acceso");
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JFormattedTextField getPwdClave() {
        return pwdClave;
    }

    public JLabel getLblMensaje() {
        return lblMensaje;
    }
}
