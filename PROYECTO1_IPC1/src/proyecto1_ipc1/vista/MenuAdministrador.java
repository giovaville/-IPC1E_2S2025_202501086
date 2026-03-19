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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import proyecto1_ipc1.controlador.ControlBiblioteca;
import proyecto1_ipc1.modelo.Usuario;

/**
 *
 * @author Gio
 */
public class MenuAdministrador extends JFrame {

    private final ControlBiblioteca sistema;
    private final Usuario admin;
    private final JFrame ventanaPadre;

    public MenuAdministrador(ControlBiblioteca sistema, Usuario admin, JFrame ventanaPadre) {
        this.sistema = sistema;
        this.admin = admin;
        this.ventanaPadre = ventanaPadre;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Menú Administrador");
        setSize(420, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Administrador: " + admin.getNombreCompleto(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnOperadores = new JButton("Gestión de Operadores");
        JButton btnLibros = new JButton("Gestión de Libros");
        JButton btnEstudiantes = new JButton("Gestión de Estudiantes");
        JButton btnPrestamos = new JButton("Préstamos y Devoluciones");
        JButton btnReportes = new JButton("Reportes");
        JButton btnSalir = new JButton("Cerrar sesión");

        panel.add(lblTitulo);
        panel.add(btnOperadores);
        panel.add(btnLibros);
        panel.add(btnEstudiantes);
        panel.add(btnPrestamos);
        panel.add(btnReportes);
        panel.add(btnSalir);

        add(panel);

        btnOperadores.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vista de operadores pendiente."));
        btnLibros.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vista de libros pendiente."));
        btnEstudiantes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vista de estudiantes pendiente."));
        btnPrestamos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vista de préstamos pendiente."));
        btnReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vista de reportes pendiente."));
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void cerrarSesion() {
        sistema.getBitacora().registrar("CIERRE SESION", admin.getUsuario(), "AUTENTICACION");
        dispose();
        ventanaPadre.setVisible(true);
    }
}