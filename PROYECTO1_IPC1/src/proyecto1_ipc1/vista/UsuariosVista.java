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
import proyecto1_ipc1.modelo.Usuario;

/**
 *
 * @author Gio
 */
public class UsuariosVista extends JFrame {

    private final ControlBiblioteca sistema;

    private JTextField txtUsuario;
    private JTextField txtNombre;
    private JTextField txtContrasena;

    private JButton btnRegistrarOperador;
    private JButton btnRefrescar;
    private JButton btnLimpiar;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public UsuariosVista(ControlBiblioteca sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        setTitle("Gestión de Usuarios");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(2, 3, 10, 10));

        panelFormulario.add(new JLabel("Usuario operador:"));
        txtUsuario = new JTextField();
        panelFormulario.add(txtUsuario);

        panelFormulario.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtContrasena = new JTextField();
        panelFormulario.add(txtContrasena);

        btnRegistrarOperador = new JButton("Registrar operador");
        btnRefrescar = new JButton("Refrescar tabla");
        btnLimpiar = new JButton("Limpiar campos");

        panelFormulario.add(btnRegistrarOperador);
        panelFormulario.add(btnRefrescar);
        panelFormulario.add(btnLimpiar);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Usuario/Carné");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Carrera");
        modeloTabla.addColumn("Activo");

        tablaUsuarios = new JTable(modeloTabla);
        panelPrincipal.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        add(panelPrincipal);

        btnRegistrarOperador.addActionListener(e -> registrarOperador());
        btnRefrescar.addActionListener(e -> cargarTabla());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void registrarOperador() {
        String usuario = txtUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (usuario.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
            return;
        }

        boolean exito = sistema.registrarOperador(usuario, nombre, contrasena);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Operador registrado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el operador.");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        Usuario[] usuarios = sistema.getUsuarios();
        int total = sistema.getTotalUsuarios();

        for (int i = 0; i < total; i++) {
            Usuario u = usuarios[i];

            if (u != null) {
                modeloTabla.addRow(new Object[]{
                    u.getRol(),
                    u.getUsuario(),
                    u.getNombreCompleto(),
                    u.getCarrera(),
                    u.isActivo() ? "Sí" : "No"
                });
            }
        }
    }

    private void limpiarCampos() {
        txtUsuario.setText("");
        txtNombre.setText("");
        txtContrasena.setText("");
    }
}
