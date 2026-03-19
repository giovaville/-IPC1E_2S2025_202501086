/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.vista;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import proyecto1_ipc1.controlador.ControlBiblioteca;

/**
 *
 * @author Gio
 */
public class RegistroEstudianteVista extends JFrame {

    private final ControlBiblioteca sistema;
    private final JFrame ventanaPadre;

    private JTextField txtCarnet;
    private JTextField txtNombre;
    private JTextField txtCarrera;
    private JPasswordField txtContrasena;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public RegistroEstudianteVista(ControlBiblioteca sistema, JFrame ventanaPadre) {
        this.sistema = sistema;
        this.ventanaPadre = ventanaPadre;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de Estudiante");
        setSize(450, 300);
        setLocationRelativeTo(ventanaPadre);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Carné:"));
        txtCarnet = new JTextField();
        panel.add(txtCarnet);

        panel.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Carrera:"));
        txtCarrera = new JTextField();
        panel.add(txtCarrera);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> guardarRegistro());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarRegistro() {
        String carnet = txtCarnet.getText().trim();
        String nombre = txtNombre.getText().trim();
        String carrera = txtCarrera.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        boolean exito = sistema.registrarEstudiante(carnet, nombre, carrera, contrasena);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el estudiante.");
        }
    }
}