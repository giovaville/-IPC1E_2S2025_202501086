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
public class MenuEstudiante extends JFrame {

    private final ControlBiblioteca sistema;
    private final Usuario estudiante;
    private final JFrame ventanaPadre;

    public MenuEstudiante(ControlBiblioteca sistema, Usuario estudiante, JFrame ventanaPadre) {
        this.sistema = sistema;
        this.estudiante = estudiante;
        this.ventanaPadre = ventanaPadre;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Menú Estudiante");
        setSize(420, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Estudiante: " + estudiante.getNombreCompleto(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnPrestamo = new JButton("Solicitar préstamo");
        JButton btnHistorial = new JButton("Ver historial");
        JButton btnDatos = new JButton("Ver mis datos");
        JButton btnLibros = new JButton("Libros disponibles");
        JButton btnSalir = new JButton("Cerrar sesión");

        panel.add(lblTitulo);
        panel.add(btnPrestamo);
        panel.add(btnHistorial);
        panel.add(btnDatos);
        panel.add(btnLibros);
        panel.add(btnSalir);

        add(panel);
        
        btnPrestamo.addActionListener(e -> {
    PrestamosVista vista = new PrestamosVista(sistema);
    vista.setVisible(true);
        });
        btnHistorial.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vista de historial pendiente."));
        btnDatos.addActionListener(e -> mostrarDatos());
        btnLibros.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vista de libros pendiente."));
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void mostrarDatos() {
        String mensaje = "Carné: " + estudiante.getUsuario()
                + "\nNombre: " + estudiante.getNombreCompleto()
                + "\nCarrera: " + estudiante.getCarrera();

        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void cerrarSesion() {
        sistema.getBitacora().registrar("CIERRE SESION", estudiante.getUsuario(), "AUTENTICACION");
        dispose();
        ventanaPadre.setVisible(true);
    }
}
