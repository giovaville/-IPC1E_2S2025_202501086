/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.vista;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import proyecto1_ipc1.controlador.ControlBiblioteca;
import proyecto1_ipc1.modelo.Prestamo;
import proyecto1_ipc1.utilidades.FechaUtil;

/**
 *
 * @author Gio
 */
public class PrestamosVista extends JFrame {

    private final ControlBiblioteca sistema;

    private JTextField txtCarnet;
    private JTextField txtCodigoLibro;
    private JTextField txtCodigoPrestamo;

    private JButton btnRegistrarPrestamo;
    private JButton btnRegistrarDevolucion;
    private JButton btnRefrescar;
    private JButton btnLimpiar;

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    public PrestamosVista(ControlBiblioteca sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        setTitle("Préstamos y Devoluciones");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 10, 10));

        panelFormulario.add(new JLabel("Carné:"));
        txtCarnet = new JTextField();
        panelFormulario.add(txtCarnet);

        panelFormulario.add(new JLabel("Código o ISBN del libro:"));
        txtCodigoLibro = new JTextField();
        panelFormulario.add(txtCodigoLibro);

        panelFormulario.add(new JLabel("Código de préstamo:"));
        txtCodigoPrestamo = new JTextField();
        panelFormulario.add(txtCodigoPrestamo);

        btnRegistrarPrestamo = new JButton("Registrar préstamo");
        btnRegistrarDevolucion = new JButton("Registrar devolución");
        btnRefrescar = new JButton("Refrescar tabla");
        btnLimpiar = new JButton("Limpiar campos");

        panelFormulario.add(btnRegistrarPrestamo);
        panelFormulario.add(btnRegistrarDevolucion);
        panelFormulario.add(btnRefrescar);
        panelFormulario.add(btnLimpiar);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Carné");
        modeloTabla.addColumn("Libro");
        modeloTabla.addColumn("Fecha préstamo");
        modeloTabla.addColumn("Fecha límite");
        modeloTabla.addColumn("Fecha devolución");
        modeloTabla.addColumn("Estado");

        tablaPrestamos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaPrestamos);

        panelPrincipal.add(scroll, BorderLayout.CENTER);

        add(panelPrincipal);

        btnRegistrarPrestamo.addActionListener(e -> registrarPrestamo());
        btnRegistrarDevolucion.addActionListener(e -> registrarDevolucion());
        btnRefrescar.addActionListener(e -> cargarTabla());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void registrarPrestamo() {
        String carnet = txtCarnet.getText().trim();
        String codigoLibro = txtCodigoLibro.getText().trim();

        if (carnet.isEmpty() || codigoLibro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar carné y código/ISBN del libro.");
            return;
        }

        boolean exito = sistema.registrarPrestamo(carnet, codigoLibro);

        if (exito) {
    JOptionPane.showMessageDialog(this, sistema.getControlPrestamos().getUltimoMensaje());
    limpiarCampos();
    cargarTabla();
        } else {
    JOptionPane.showMessageDialog(this, sistema.getControlPrestamos().getUltimoMensaje());
}
    }

    private void registrarDevolucion() {
        String codigoPrestamo = txtCodigoPrestamo.getText().trim();

        if (codigoPrestamo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el código del préstamo.");
            return;
        }

        boolean exito = sistema.registrarDevolucion(codigoPrestamo);
        if (exito) {
    JOptionPane.showMessageDialog(this, sistema.getControlPrestamos().getUltimoMensaje());
    limpiarCampos();
    cargarTabla();
        } else {
    JOptionPane.showMessageDialog(this, sistema.getControlPrestamos().getUltimoMensaje());
}
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        Prestamo[] prestamos = sistema.getPrestamos();
        int total = sistema.getTotalPrestamos();

        for (int i = 0; i < total; i++) {
            Prestamo p = prestamos[i];

            if (p != null) {
                String estadoMostrar = p.getEstado();

                if (FechaUtil.estaVencido(p.getFechaLimite(), p.getEstado())) {
                    estadoMostrar = "VENCIDO";
                }

                modeloTabla.addRow(new Object[]{
                    p.getCodigoPrestamo(),
                    p.getCarnet(),
                    p.getCodigoLibro(),
                    p.getFechaPrestamo(),
                    p.getFechaLimite(),
                    p.getFechaDevolucion(),
                    estadoMostrar
                });
            }
        }
    }

    private void limpiarCampos() {
        txtCarnet.setText("");
        txtCodigoLibro.setText("");
        txtCodigoPrestamo.setText("");
    }
}
