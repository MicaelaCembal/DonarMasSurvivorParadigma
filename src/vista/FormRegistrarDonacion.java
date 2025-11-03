package vista;

import modelo.*;
import modelo.datos.GestorDonacion;
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
    private GestorDonacion gestor;

    public FormRegistrarDonacion() {
        inicializar();
        gestor = new GestorDonacion();

        btnRegistrarDonacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String objeto = txtObjetoDonar.getText().trim();
                String cantidadStr = txtCantidad.getText().trim();
                String lugar = txtLugarEntrega.getText().trim();

                if (objeto.isEmpty() || cantidadStr.isEmpty() || lugar.isEmpty()) {
                    lblMensaje.setText("Complete todos los campos.");
                    return;
                }

                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    Donacion donacion = new Donacion(objeto, cantidad);
                    gestor.guardarDonacion(donacion);
                    lblMensaje.setText("Donación registrada con éxito.");
                    limpiarCampos();
                } catch (NumberFormatException ex) {
                    lblMensaje.setText("La cantidad debe ser un número válido.");
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
