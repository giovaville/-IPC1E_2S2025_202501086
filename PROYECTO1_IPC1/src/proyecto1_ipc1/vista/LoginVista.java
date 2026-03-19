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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import proyecto1_ipc1.controlador.ControlBiblioteca;
import proyecto1_ipc1.modelo.Usuario;

public class LoginVista extends JFrame {

    private final ControlBiblioteca sistema;

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JButton btnCrearCuenta;

    public LoginVista(ControlBiblioteca sistema) {
        this.sistema = sistema;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("BiblioSystem - Login");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("BIBLIOSYSTEM", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(3, 2, 10, 10));

        panelCentro.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelCentro.add(txtUsuario);

        panelCentro.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panelCentro.add(txtContrasena);

        btnLogin = new JButton("Iniciar sesión");
        btnCrearCuenta = new JButton("Crear cuenta");

        panelCentro.add(btnLogin);
        panelCentro.add(btnCrearCuenta);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        add(panelPrincipal);

        btnLogin.addActionListener(e -> iniciarSesion());
        btnCrearCuenta.addActionListener(e -> abrirRegistro());
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        Usuario u = sistema.autenticar(usuario, contrasena);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas.");
            return;
        }

        if (u.esAdmin()) {
            MenuAdministrador menu = new MenuAdministrador(sistema, u, this);
            menu.setVisible(true);
            this.setVisible(false);
        } else if (u.esOperador()) {
            MenuOperador menu = new MenuOperador(sistema, u, this);
            menu.setVisible(true);
            this.setVisible(false);
        } else {
            MenuEstudiante menu = new MenuEstudiante(sistema, u, this);
            menu.setVisible(true);
            this.setVisible(false);
        }
    }

    private void abrirRegistro() {
        RegistroEstudianteVista registro = new RegistroEstudianteVista(sistema, this);
        registro.setVisible(true);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
    }
}