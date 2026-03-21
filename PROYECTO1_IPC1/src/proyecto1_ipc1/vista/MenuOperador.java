/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.vista;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import proyecto1_ipc1.controlador.ControlBiblioteca;
import proyecto1_ipc1.modelo.Usuario;

/**
 *
 * @author Gio
 */
public class MenuOperador extends JFrame {

    private final ControlBiblioteca sistema;
    private final Usuario operador;
    private final JFrame ventanaPadre;

    public MenuOperador(ControlBiblioteca sistema, Usuario operador, JFrame ventanaPadre) {
        this.sistema = sistema;
        this.operador = operador;
        this.ventanaPadre = ventanaPadre;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Menú Operador");
        setSize(420, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Operador: " + operador.getNombreCompleto(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnLibros = new JButton("Gestión de Libros");
        JButton btnEstudiantes = new JButton("Gestión de Estudiantes");
        JButton btnPrestamos = new JButton("Préstamos y Devoluciones");
        JButton btnReportes = new JButton("Reportes");
        JButton btnSalir = new JButton("Cerrar sesión");

        panel.add(lblTitulo);
        panel.add(btnLibros);
        panel.add(btnEstudiantes);
        panel.add(btnPrestamos);
        panel.add(btnReportes);
        panel.add(btnSalir);

        add(panel);

        btnLibros.addActionListener(e -> {
            LibrosVista vista = new LibrosVista(sistema);
            vista.setVisible(true);
        });

        btnEstudiantes.addActionListener(e -> {
            UsuariosVista vista = new UsuariosVista(sistema, operador, "ESTUDIANTES");
            vista.setVisible(true);
        });

        btnPrestamos.addActionListener(e -> {
            PrestamosVista vista = new PrestamosVista(sistema);
            vista.setVisible(true);
        });

        btnReportes.addActionListener(e -> {
            ReportesVista vista = new ReportesVista(sistema);
            vista.setVisible(true);
        });

        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void cerrarSesion() {
        sistema.getBitacora().registrar("CIERRE SESION", operador.getUsuario(), "AUTENTICACION");
        dispose();
        ventanaPadre.setVisible(true);
    }
}