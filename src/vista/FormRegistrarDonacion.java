package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        btnRegistrarDonacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String objeto = txtObjetoDonar.getText().trim();
                String cantidad = txtCantidad.getText().trim();
                String lugar = txtLugarEntrega.getText().trim();

                if (objeto.isEmpty() || cantidad.isEmpty() || lugar.isEmpty()) {
                    lblMensaje.setText("Por favor, complete todos los campos.");
                } else {
                    lblMensaje.setText("Donación registrada: " + cantidad + " " + objeto + " en " + lugar + ".");
                    // Acá podrías conectar con tu backend o base de datos, por ejemplo:
                    // GestorDonacion.registrar(new Donacion(objeto, cantidad, lugar));
                    limpiarCampos();
                }
            }
        });
    }

    private void inicializar() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setTitle("Registrar Donación");
    }

    private void limpiarCampos() {
        txtObjetoDonar.setText("");
        txtCantidad.setText("");
        txtLugarEntrega.setText("");
    }
}
