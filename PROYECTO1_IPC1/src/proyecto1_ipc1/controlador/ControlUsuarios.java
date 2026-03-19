/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.controlador;
import proyecto1_ipc1.modelo.Usuario;
import proyecto1_ipc1.utilidades.Validaciones;
/**
 *
 * @author Gio
 */
public class ControlUsuarios {

    private final ControlBiblioteca sistema;

    public ControlUsuarios(ControlBiblioteca sistema) {
        this.sistema = sistema;
    }

    public Usuario autenticar(String usuario, String contrasena) {
        if ("admin".equals(usuario) && "admin".equals(contrasena)) {
            sistema.getBitacora().registrar("LOGIN EXITOSO", usuario, "AUTENTICACION");
            return new Usuario(Usuario.ROL_ADMIN, "admin", "admin", "Administrador", "N/A");
        }

        Usuario encontrado = sistema.buscarUsuarioActivo(usuario);

        if (encontrado != null && encontrado.getContrasena().equals(contrasena)) {
            sistema.getBitacora().registrar("LOGIN EXITOSO", usuario, "AUTENTICACION");
            return encontrado;
        }

        sistema.getBitacora().registrar("LOGIN FALLIDO", usuario, "AUTENTICACION");
        return null;
    }

    public boolean registrarEstudiante(String carnet, String nombre, String carrera, String contrasena) {
        if (Validaciones.textoVacio(carnet) || Validaciones.textoVacio(nombre) ||
            Validaciones.textoVacio(carrera) || Validaciones.textoVacio(contrasena)) {
            return false;
        }

        if (sistema.buscarUsuarioActivo(carnet) != null || "admin".equalsIgnoreCase(carnet)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "REGISTRO ESTUDIANTE");
            return false;
        }

        Usuario nuevo = new Usuario(Usuario.ROL_ESTUDIANTE, carnet, contrasena, nombre, carrera);

        if (!sistema.agregarUsuarioInterno(nuevo)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "REGISTRO ESTUDIANTE");
            return false;
        }

        sistema.guardarUsuarios();
        sistema.getBitacora().registrar("CREAR", carnet, "REGISTRO ESTUDIANTE");
        return true;
    }

    public boolean registrarOperador(String usuario, String nombre, String contrasena) {
        if (Validaciones.textoVacio(usuario) || Validaciones.textoVacio(nombre) || Validaciones.textoVacio(contrasena)) {
            return false;
        }

        if (sistema.buscarUsuarioActivo(usuario) != null || "admin".equalsIgnoreCase(usuario)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "OPERADORES");
            return false;
        }

        Usuario nuevo = new Usuario(Usuario.ROL_OPERADOR, usuario, contrasena, nombre, "N/A");

        if (!sistema.agregarUsuarioInterno(nuevo)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "OPERADORES");
            return false;
        }

        sistema.guardarUsuarios();
        sistema.getBitacora().registrar("CREAR", "admin", "OPERADORES");
        return true;
    }

    public boolean eliminarUsuario(String usuario) {
        int indice = sistema.indiceUsuarioActivo(usuario);

        if (indice == -1) {
            return false;
        }

        sistema.getUsuarios()[indice].setActivo(false);
        sistema.guardarUsuarios();
        return true;
    }
}