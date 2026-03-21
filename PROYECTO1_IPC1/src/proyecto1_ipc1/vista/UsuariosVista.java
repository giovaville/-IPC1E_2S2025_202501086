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
    private final Usuario usuarioSesion;
    private final String modo;

    private JTextField txtUsuario;
    private JTextField txtNombre;
    private JTextField txtCarrera;
    private JTextField txtContrasena;
    private JTextField txtBuscar;

    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscarCodigo;
    private JButton btnBuscarNombre;
    private JButton btnMostrarTodos;
    private JButton btnLimpiar;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public UsuariosVista(ControlBiblioteca sistema, Usuario usuarioSesion, String modo) {
        this.sistema = sistema;
        this.usuarioSesion = usuarioSesion;
        this.modo = modo;
        inicializarComponentes();
        cargarTablaCompleta();
        configurarModo();
    }

    private void inicializarComponentes() {
        setTitle(modo.equalsIgnoreCase("OPERADORES") ? "Gestión de Operadores" : "Gestión de Estudiantes");
        setSize(1000, 530);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));

        panelFormulario.add(new JLabel("Usuario / Carné:"));
        txtUsuario = new JTextField();
        panelFormulario.add(txtUsuario);

        panelFormulario.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Carrera:"));
        txtCarrera = new JTextField();
        panelFormulario.add(txtCarrera);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtContrasena = new JTextField();
        panelFormulario.add(txtContrasena);

        panelFormulario.add(new JLabel("Buscar (código o nombre):"));
        txtBuscar = new JTextField();
        panelFormulario.add(txtBuscar);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Usuario/Carné");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Carrera");
        modeloTabla.addColumn("Activo");

        tablaUsuarios = new JTable(modeloTabla);
        panelPrincipal.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));

        btnRegistrar = new JButton(modo.equalsIgnoreCase("OPERADORES") ? "Registrar Operador" : "Registrar Estudiante");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscarCodigo = new JButton("Buscar por Código");
        btnBuscarNombre = new JButton("Buscar por Nombre");
        btnMostrarTodos = new JButton("Mostrar Todos");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscarCodigo);
        panelBotones.add(btnBuscarNombre);
        panelBotones.add(btnMostrarTodos);
        panelBotones.add(btnLimpiar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnModificar.addActionListener(e -> modificarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnBuscarCodigo.addActionListener(e -> buscarPorCodigo());
        btnBuscarNombre.addActionListener(e -> buscarPorNombre());
        btnMostrarTodos.addActionListener(e -> cargarTablaCompleta());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila != -1) {
                txtUsuario.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtCarrera.setText(modeloTabla.getValueAt(fila, 3).toString());
            }
        });
    }

    private void configurarModo() {
        if (modo.equalsIgnoreCase("OPERADORES")) {
            txtCarrera.setText("N/A");
            txtCarrera.setEnabled(false);
        }

        if (modo.equalsIgnoreCase("OPERADORES") && !usuarioSesion.esAdmin()) {
            btnRegistrar.setEnabled(false);
        }

        if (!usuarioSesion.esAdmin()) {
            btnEliminar.setEnabled(false);
        }
    }

    private void registrarUsuario() {
        if (modo.equalsIgnoreCase("OPERADORES")) {
            if (!usuarioSesion.esAdmin()) {
                JOptionPane.showMessageDialog(this, "Solo el administrador puede crear operadores.");
                return;
            }

            boolean exito = sistema.registrarOperador(
                    txtUsuario.getText().trim(),
                    txtNombre.getText().trim(),
                    txtContrasena.getText().trim()
            );

            JOptionPane.showMessageDialog(this, sistema.getControlUsuarios().getUltimoMensaje());

            if (exito) {
                limpiarCampos();
                cargarTablaCompleta();
            }
        } else {
            boolean exito = sistema.registrarEstudiante(
                    txtUsuario.getText().trim(),
                    txtNombre.getText().trim(),
                    txtCarrera.getText().trim(),
                    txtContrasena.getText().trim()
            );

            JOptionPane.showMessageDialog(this, sistema.getControlUsuarios().getUltimoMensaje());

            if (exito) {
                limpiarCampos();
                cargarTablaCompleta();
            }
        }
    }

    private void modificarUsuario() {
        String codigo = txtUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String carrera = txtCarrera.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        boolean exito = sistema.modificarUsuario(codigo, nombre, carrera, contrasena, usuarioSesion);
        JOptionPane.showMessageDialog(this, sistema.getControlUsuarios().getUltimoMensaje());

        if (exito) {
            limpiarCampos();
            cargarTablaCompleta();
        }
    }

    private void eliminarUsuario() {
        String codigo = txtUsuario.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione o ingrese el usuario/carné a eliminar.");
            return;
        }

        boolean exito = sistema.eliminarUsuario(codigo, usuarioSesion);
        JOptionPane.showMessageDialog(this, sistema.getControlUsuarios().getUltimoMensaje());

        if (exito) {
            limpiarCampos();
            cargarTablaCompleta();
        }
    }

    private void buscarPorCodigo() {
        String codigo = txtBuscar.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código para buscar.");
            return;
        }

        Usuario u = sistema.getControlUsuarios().buscarPorCodigo(codigo);
        modeloTabla.setRowCount(0);

        if (u != null && coincideConModo(u)) {
            agregarUsuarioATabla(u);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún usuario en este módulo.");
        }
    }

    private void buscarPorNombre() {
        String nombre = txtBuscar.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para buscar.");
            return;
        }

        modeloTabla.setRowCount(0);
        Usuario[] resultados = sistema.getControlUsuarios().buscarPorNombre(nombre);
        boolean encontro = false;

        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i] != null && coincideConModo(resultados[i])) {
                agregarUsuarioATabla(resultados[i]);
                encontro = true;
            }
        }

        if (!encontro) {
            JOptionPane.showMessageDialog(this, "No se encontraron usuarios.");
        }
    }

    private void cargarTablaCompleta() {
        modeloTabla.setRowCount(0);

        for (int i = 0; i < sistema.getTotalUsuarios(); i++) {
            Usuario u = sistema.getUsuarios()[i];
            if (u != null && coincideConModo(u)) {
                agregarUsuarioATabla(u);
            }
        }
    }

    private boolean coincideConModo(Usuario u) {
        if (modo.equalsIgnoreCase("OPERADORES")) {
            return u.esOperador();
        }
        return u.esEstudiante();
    }

    private void agregarUsuarioATabla(Usuario u) {
        modeloTabla.addRow(new Object[]{
            u.getRol(),
            u.getUsuario(),
            u.getNombreCompleto(),
            u.getCarrera(),
            u.isActivo() ? "Sí" : "No"
        });
    }

    private void limpiarCampos() {
        txtUsuario.setText("");
        txtNombre.setText("");
        txtContrasena.setText("");
        txtBuscar.setText("");

        if (modo.equalsIgnoreCase("ESTUDIANTES")) {
            txtCarrera.setText("");
        } else {
            txtCarrera.setText("N/A");
        }

        tablaUsuarios.clearSelection();
    }
}