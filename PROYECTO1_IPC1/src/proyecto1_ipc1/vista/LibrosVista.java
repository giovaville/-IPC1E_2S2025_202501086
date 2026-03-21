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
import proyecto1_ipc1.modelo.Libro;

/**
 *
 * @author Gio
 */
public class LibrosVista extends JFrame {

    private final ControlBiblioteca sistema;

    private JTextField txtCodigo;
    private JTextField txtIsbn;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtGenero;
    private JTextField txtAnio;
    private JTextField txtCantidad;
    private JTextField txtBuscar;

    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscarCodigo;
    private JButton btnBuscarNombre;
    private JButton btnMostrarTodos;
    private JButton btnLimpiar;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public LibrosVista(ControlBiblioteca sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarTablaCompleta();
    }

    private void inicializarComponentes() {
        setTitle("Gestión de Libros");
        setSize(1050, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 10, 10));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panelFormulario.add(txtGenero);

        panelFormulario.add(new JLabel("Año:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        panelFormulario.add(new JLabel("Cantidad Total:"));
        txtCantidad = new JTextField();
        panelFormulario.add(txtCantidad);

        panelFormulario.add(new JLabel("Buscar (código o nombre):"));
        txtBuscar = new JTextField();
        panelFormulario.add(txtBuscar);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("ISBN");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Género");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Disponibles");
        modeloTabla.addColumn("Total");
        modeloTabla.addColumn("Activo");

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaLibros);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));
        btnRegistrar = new JButton("Registrar");
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

        btnRegistrar.addActionListener(e -> registrarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnBuscarCodigo.addActionListener(e -> buscarPorCodigo());
        btnBuscarNombre.addActionListener(e -> buscarPorNombre());
        btnMostrarTodos.addActionListener(e -> cargarTablaCompleta());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaLibros.getSelectedRow();
            if (fila != -1) {
                txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtIsbn.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtTitulo.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtAutor.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtGenero.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtAnio.setText(modeloTabla.getValueAt(fila, 5).toString());
                txtCantidad.setText(modeloTabla.getValueAt(fila, 7).toString());
            }
        });
    }

    private void registrarLibro() {
        try {
            boolean exito = sistema.registrarLibro(
                    txtCodigo.getText().trim(),
                    txtIsbn.getText().trim(),
                    txtTitulo.getText().trim(),
                    txtAutor.getText().trim(),
                    txtGenero.getText().trim(),
                    Integer.parseInt(txtAnio.getText().trim()),
                    Integer.parseInt(txtCantidad.getText().trim())
            );

            JOptionPane.showMessageDialog(this, sistema.getControlLibros().getUltimoMensaje());

            if (exito) {
                limpiarCampos();
                cargarTablaCompleta();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Año y cantidad deben ser números enteros válidos.");
        }
    }

    private void modificarLibro() {
        try {
            boolean exito = sistema.modificarLibro(
                    txtCodigo.getText().trim(),
                    txtIsbn.getText().trim(),
                    txtTitulo.getText().trim(),
                    txtAutor.getText().trim(),
                    txtGenero.getText().trim(),
                    Integer.parseInt(txtAnio.getText().trim()),
                    Integer.parseInt(txtCantidad.getText().trim())
            );

            JOptionPane.showMessageDialog(this, sistema.getControlLibros().getUltimoMensaje());

            if (exito) {
                limpiarCampos();
                cargarTablaCompleta();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Año y cantidad deben ser números enteros válidos.");
        }
    }

    private void eliminarLibro() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione o ingrese el código del libro.");
            return;
        }

        boolean exito = sistema.eliminarLibro(codigo);
        JOptionPane.showMessageDialog(this, sistema.getControlLibros().getUltimoMensaje());

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

        Libro libro = sistema.getControlLibros().buscarPorCodigo(codigo);
        modeloTabla.setRowCount(0);

        if (libro != null) {
            agregarLibroATabla(libro);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún libro con ese código.");
        }
    }

    private void buscarPorNombre() {
        String nombre = txtBuscar.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para buscar.");
            return;
        }

        modeloTabla.setRowCount(0);
        Libro[] resultados = sistema.getControlLibros().buscarPorNombre(nombre);
        boolean encontro = false;

        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i] != null) {
                agregarLibroATabla(resultados[i]);
                encontro = true;
            }
        }

        if (!encontro) {
            JOptionPane.showMessageDialog(this, "No se encontraron libros con ese nombre.");
        }
    }

    private void cargarTablaCompleta() {
        modeloTabla.setRowCount(0);

        for (int i = 0; i < sistema.getTotalLibros(); i++) {
            Libro libro = sistema.getLibros()[i];
            if (libro != null) {
                agregarLibroATabla(libro);
            }
        }
    }

    private void agregarLibroATabla(Libro libro) {
        modeloTabla.addRow(new Object[]{
            libro.getCodigo(),
            libro.getIsbn(),
            libro.getTitulo(),
            libro.getAutor(),
            libro.getGenero(),
            libro.getAnio(),
            libro.getCantidadDisponible(),
            libro.getCantidadTotal(),
            libro.isActivo() ? "Sí" : "No"
        });
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtIsbn.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtGenero.setText("");
        txtAnio.setText("");
        txtCantidad.setText("");
        txtBuscar.setText("");
        tablaLibros.clearSelection();
    }
}