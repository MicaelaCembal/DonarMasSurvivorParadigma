package vista;

import modelo.Campania;
import javax.swing.*;

public class FormRegistrarDonacion extends JFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JTextField txtObjetoDonar;
    private JTextField txtCantidad;
    private JTextField txtLugarEntrega;
    private JButton btnRegistrarDonacion;
    private JLabel lblMensaje;
    private JComboBox<Campania> comboBoxCampania;

    public FormRegistrarDonacion() {
        inicializar();
        cargarCampanias();
    }

    private void inicializar() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setTitle("Registrar Donación");
    }

    private void cargarCampanias() {
        Campania c1 = new Campania();
        c1.setIdCampania(1);
        c1.setNombre("Campaña de Invierno 2025");

        Campania c2 = new Campania();
        c2.setIdCampania(2);
        c2.setNombre("Solidarios por los Niños");

        Campania c3 = new Campania();
        c3.setIdCampania(3);
        c3.setNombre("Doná con Amor");

        comboBoxCampania.addItem(c1);
        comboBoxCampania.addItem(c2);
        comboBoxCampania.addItem(c3);
    }

    public JButton getBtnRegistrarDonacion() { return btnRegistrarDonacion; }
    public JTextField getTxtObjetoDonar() { return txtObjetoDonar; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTextField getTxtLugarEntrega() { return txtLugarEntrega; }
    public JLabel getLblMensaje() { return lblMensaje; }
    public JComboBox<Campania> getComboBoxCampania() { return comboBoxCampania; }

    public void limpiarCampos() {
        txtObjetoDonar.setText("");
        txtCantidad.setText("");
        txtLugarEntrega.setText("");
        comboBoxCampania.setSelectedIndex(-1);
    }
}
