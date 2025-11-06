package controlador;

import modelo.Deposito; // <--- IMPORTANTE
import modelo.Donacion;
import modelo.datos.GestorDonacion;
import vista.FormRegistrarDonacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorDonacion implements ActionListener {

    private FormRegistrarDonacion vista;
    private GestorDonacion gestor;

    public ControladorDonacion(FormRegistrarDonacion vista) {
        this.vista = vista;
        this.gestor = new GestorDonacion();
        this.vista.getBtnRegistrarDonacion().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnRegistrarDonacion()) {
            // 1. Obtener datos de la vista
            String objeto = vista.getTxtObjetoDonar().getText().trim();
            String cantidadStr = vista.getTxtCantidad().getText().trim();
            String lugar = vista.getTxtLugarEntrega().getText().trim();

            // 2. Validar que no estén vacíos
            if (objeto.isEmpty() || cantidadStr.isEmpty() || lugar.isEmpty()) {
                vista.getLblMensaje().setText("Por favor, complete todos los campos.");
                return;
            }

            try {
                int cantidad = Integer.parseInt(cantidadStr);

                // 3. Crear la donación (esto valida si cantidad > 0)
                Donacion donacion = new Donacion(objeto, cantidad);

                // 4. --- CORRECCIÓN CLAVE ---
                // Creamos un objeto Deposito con el nombre ingresado y lo asignamos.
                // Ya no usamos .setDeposito(String), sino .asignarDeposito(Deposito)
                donacion.asignarDeposito(new Deposito(lugar));

                // 5. Guardar en la base de datos
                gestor.guardarDonacion(donacion);

                // 6. Feedback al usuario
                vista.getLblMensaje().setText("✅ Donación registrada con éxito.");
                vista.limpiarCampos();

            } catch (NumberFormatException ex) {
                vista.getLblMensaje().setText("❌ La cantidad debe ser un número entero.");
            } catch (Donacion.CantidadInvalidaException ex) {
                // Capturamos nuestra excepción de negocio
                vista.getLblMensaje().setText("⚠️ " + ex.getMessage());
            } catch (Exception ex) {
                System.err.println("Error inesperado: " + ex.getMessage());
                vista.getLblMensaje().setText("❌ Ocurrió un error inesperado.");
            }
        }
    }
}
