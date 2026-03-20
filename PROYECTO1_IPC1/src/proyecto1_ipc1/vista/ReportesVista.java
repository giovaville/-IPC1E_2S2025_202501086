/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import proyecto1_ipc1.controlador.ControlBiblioteca;
/**
 *
 * @author Gio
 */
public class ReportesVista extends JFrame {

    private final ControlBiblioteca sistema;

    private JButton btnPrestamosVencidos;
    private JButton btnLibrosDisponibles;
    private JButton btnEstudiantesActivos;
    private JTextArea txtResultado;

    public ReportesVista(ControlBiblioteca sistema) {
        this.sistema = sistema;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Reportes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("GENERACIÓN DE REPORTES", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));

        btnPrestamosVencidos = new JButton("Reporte de Préstamos Vencidos");
        btnLibrosDisponibles = new JButton("Reporte de Libros Disponibles");
        btnEstudiantesActivos = new JButton("Reporte de Estudiantes Activos");

        panelBotones.add(btnPrestamosVencidos);
        panelBotones.add(btnLibrosDisponibles);
        panelBotones.add(btnEstudiantesActivos);

        panelPrincipal.add(panelBotones, BorderLayout.WEST);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        txtResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

        panelPrincipal.add(txtResultado, BorderLayout.CENTER);

        add(panelPrincipal);

        btnPrestamosVencidos.addActionListener(e -> generarPrestamosVencidos());
        btnLibrosDisponibles.addActionListener(e -> generarLibrosDisponibles());
        btnEstudiantesActivos.addActionListener(e -> generarEstudiantesActivos());
    }

    private void generarPrestamosVencidos() {
        String archivo = sistema.getGeneradorReportes().generarPrestamosVencidos();
        txtResultado.setText("Reporte generado correctamente:\n\n" + archivo);
        JOptionPane.showMessageDialog(this, "Reporte de préstamos vencidos generado.");
    }

    private void generarLibrosDisponibles() {
        String archivo = sistema.getGeneradorReportes().generarLibrosDisponibles();
        txtResultado.setText("Reporte generado correctamente:\n\n" + archivo);
        JOptionPane.showMessageDialog(this, "Reporte de libros disponibles generado.");
    }

    private void generarEstudiantesActivos() {
        String archivo = sistema.getGeneradorReportes().generarEstudiantesActivos();
        txtResultado.setText("Reporte generado correctamente:\n\n" + archivo);
        JOptionPane.showMessageDialog(this, "Reporte de estudiantes activos generado.");
    }
}
