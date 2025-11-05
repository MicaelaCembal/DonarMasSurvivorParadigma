package vista;

import javax.swing.*;
import java.awt.*;
import modelo.datos.GestorDonacion;

public class FormRegistrarDonacion extends JFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JTextField txtObjetoDonar;
    private JTextField txtCantidad;
    private JTextField txtLugarEntrega;
    private JButton btnRegistrarDonacion;
    private JLabel lblMensaje;

    public FormRegistrarDonacion() {
        inicializar();
    }

    private void inicializar() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setTitle("Registrar Donación");
    }

    public JButton getBtnRegistrarDonacion() { return btnRegistrarDonacion; }
    public JTextField getTxtObjetoDonar() { return txtObjetoDonar; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTextField getTxtLugarEntrega() { return txtLugarEntrega; }
    public JLabel getLblMensaje() { return lblMensaje; }

    public void limpiarCampos() {
        txtObjetoDonar.setText("");
        txtCantidad.setText("");
        txtLugarEntrega.setText("");
    }
}
