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

    private JButton btnRegistrar;
    private JButton btnRefrescar;
    private JButton btnLimpiar;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public LibrosVista(ControlBiblioteca sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        setTitle("Gestión de Libros");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(3, 6, 10, 10));

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

        panelFormulario.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelFormulario.add(txtCantidad);

        btnRegistrar = new JButton("Registrar libro");
        btnRefrescar = new JButton("Refrescar tabla");
        btnLimpiar = new JButton("Limpiar campos");

        panelFormulario.add(btnRegistrar);
        panelFormulario.add(btnRefrescar);
        panelFormulario.add(btnLimpiar);

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

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaLibros);

        panelPrincipal.add(scroll, BorderLayout.CENTER);

        add(panelPrincipal);

        btnRegistrar.addActionListener(e -> registrarLibro());
        btnRefrescar.addActionListener(e -> cargarTabla());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void registrarLibro() {
        String codigo = txtCodigo.getText().trim();
        String isbn = txtIsbn.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String genero = txtGenero.getText().trim();

        int anio;
        int cantidad;

        try {
            anio = Integer.parseInt(txtAnio.getText().trim());
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año y cantidad deben ser números enteros.");
            return;
        }

        boolean exito = sistema.registrarLibro(codigo, isbn, titulo, autor, genero, anio, cantidad);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Libro registrado correctamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el libro.");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        Libro[] libros = sistema.getLibros();
        int total = sistema.getTotalLibros();

        for (int i = 0; i < total; i++) {
            Libro libro = libros[i];

            if (libro != null && libro.isActivo()) {
                modeloTabla.addRow(new Object[]{
                    libro.getCodigo(),
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getGenero(),
                    libro.getAnio(),
                    libro.getCantidadDisponible(),
                    libro.getCantidadTotal()
                });
            }
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtIsbn.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtGenero.setText("");
        txtAnio.setText("");
        txtCantidad.setText("");
    }
}
