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
    private String ultimoMensaje;

    public ControlUsuarios(ControlBiblioteca sistema) {
        this.sistema = sistema;
        this.ultimoMensaje = "";
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
        ultimoMensaje = "";

        if (Validaciones.textoVacio(carnet) || Validaciones.textoVacio(nombre)
                || Validaciones.textoVacio(carrera) || Validaciones.textoVacio(contrasena)) {
            ultimoMensaje = "Todos los campos son obligatorios.";
            return false;
        }

        if (sistema.buscarUsuarioActivo(carnet) != null || "admin".equalsIgnoreCase(carnet)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "REGISTRO ESTUDIANTE");
            ultimoMensaje = "Ya existe un usuario con ese carné.";
            return false;
        }

        Usuario nuevo = new Usuario(Usuario.ROL_ESTUDIANTE, carnet, contrasena, nombre, carrera);

        if (!sistema.agregarUsuarioInterno(nuevo)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "REGISTRO ESTUDIANTE");
            ultimoMensaje = "Se alcanzó el máximo de usuarios.";
            return false;
        }

        sistema.guardarUsuarios();
        sistema.getBitacora().registrar("CREAR", carnet, "REGISTRO ESTUDIANTE");
        ultimoMensaje = "Estudiante registrado correctamente.";
        return true;
    }

    public boolean registrarOperador(String usuario, String nombre, String contrasena) {
        ultimoMensaje = "";

        if (Validaciones.textoVacio(usuario) || Validaciones.textoVacio(nombre) || Validaciones.textoVacio(contrasena)) {
            ultimoMensaje = "Todos los campos son obligatorios.";
            return false;
        }

        if (sistema.buscarUsuarioActivo(usuario) != null || "admin".equalsIgnoreCase(usuario)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "OPERADORES");
            ultimoMensaje = "Ya existe un usuario con ese nombre.";
            return false;
        }

        Usuario nuevo = new Usuario(Usuario.ROL_OPERADOR, usuario, contrasena, nombre, "N/A");

        if (!sistema.agregarUsuarioInterno(nuevo)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "OPERADORES");
            ultimoMensaje = "Se alcanzó el máximo de usuarios.";
            return false;
        }

        sistema.guardarUsuarios();
        sistema.getBitacora().registrar("CREAR", "admin", "OPERADORES");
        ultimoMensaje = "Operador registrado correctamente.";
        return true;
    }

    public boolean modificarUsuario(String usuarioOriginal, String nuevoNombre, String nuevaCarrera,
                                    String nuevaContrasena, Usuario usuarioSesion) {
        ultimoMensaje = "";

        if (usuarioSesion == null || !(usuarioSesion.esAdmin() || usuarioSesion.esOperador())) {
            ultimoMensaje = "Solo admin u operador pueden modificar usuarios.";
            return false;
        }

        Usuario usuario = sistema.buscarUsuarioActivo(usuarioOriginal);

        if (usuario == null) {
            ultimoMensaje = "No existe un usuario activo con ese código.";
            return false;
        }

        if (Validaciones.textoVacio(nuevoNombre) || Validaciones.textoVacio(nuevaContrasena)) {
            ultimoMensaje = "Nombre y contraseña son obligatorios.";
            return false;
        }

        if (usuario.esEstudiante() && Validaciones.textoVacio(nuevaCarrera)) {
            ultimoMensaje = "La carrera del estudiante es obligatoria.";
            return false;
        }

        usuario.setNombreCompleto(nuevoNombre);
        usuario.setContrasena(nuevaContrasena);

        if (usuario.esEstudiante()) {
            usuario.setCarrera(nuevaCarrera);
        }

        sistema.guardarUsuarios();
        sistema.getBitacora().registrar("MODIFICAR", usuarioSesion.getUsuario(), "USUARIOS");
        ultimoMensaje = "Usuario modificado correctamente.";
        return true;
    }

    public boolean eliminarUsuario(String usuario, Usuario usuarioSesion) {
        ultimoMensaje = "";

        if (usuarioSesion == null || !usuarioSesion.esAdmin()) {
            ultimoMensaje = "Solo el administrador puede eliminar usuarios.";
            return false;
        }

        int indice = sistema.indiceUsuarioActivo(usuario);

        if (indice == -1) {
            ultimoMensaje = "No existe un usuario activo con ese código.";
            return false;
        }

        Usuario encontrado = sistema.getUsuarios()[indice];

        if (encontrado.esEstudiante()
                && sistema.getControlPrestamos().contarPrestamosActivos(encontrado.getUsuario()) > 0) {
            ultimoMensaje = "No se puede eliminar un estudiante con préstamos activos.";
            return false;
        }

        encontrado.setActivo(false);
        sistema.guardarUsuarios();
        sistema.getBitacora().registrar("ELIMINAR", usuarioSesion.getUsuario(), "USUARIOS");
        ultimoMensaje = "Usuario eliminado correctamente.";
        return true;
    }

    public Usuario buscarPorCodigo(String codigo) {
        return sistema.buscarUsuarioActivo(codigo);
    }

    public Usuario[] buscarPorNombre(String nombre) {
        Usuario[] resultados = new Usuario[sistema.getTotalUsuarios()];
        int pos = 0;

        for (int i = 0; i < sistema.getTotalUsuarios(); i++) {
            Usuario u = sistema.getUsuarios()[i];
            if (u != null && u.isActivo()
                    && u.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())) {
                resultados[pos] = u;
                pos++;
            }
        }

        return resultados;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}