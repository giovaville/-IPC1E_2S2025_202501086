/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.controlador;
import proyecto1_ipc1.modelo.Libro;
import proyecto1_ipc1.utilidades.Validaciones;
/**
 *
 * @author Gio
 */
public class ControlLibros {

    private final ControlBiblioteca sistema;

    public ControlLibros(ControlBiblioteca sistema) {
        this.sistema = sistema;
    }

    public boolean registrarLibro(String codigo, String isbn, String titulo, String autor,
                                  String genero, int anio, int cantidad) {

        if (Validaciones.textoVacio(codigo) || Validaciones.textoVacio(isbn) ||
            Validaciones.textoVacio(titulo) || Validaciones.textoVacio(autor) ||
            Validaciones.textoVacio(genero)) {
            return false;
        }

        if (!Validaciones.esIsbnValido(isbn)) {
            return false;
        }

        if (cantidad <= 0 || anio <= 0) {
            return false;
        }

        if (sistema.buscarLibroPorCodigo(codigo) != null || sistema.buscarLibroPorIsbn(isbn) != null) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "LIBROS");
            return false;
        }

        Libro libro = new Libro(codigo, isbn, titulo, autor, genero, anio, cantidad);

        if (!sistema.agregarLibroInterno(libro)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "LIBROS");
            return false;
        }

        sistema.getBitacora().registrar("CREAR", "admin", "LIBROS");
        return true;
    }

    public boolean eliminarLibro(String codigo) {
        Libro libro = sistema.buscarLibroPorCodigo(codigo);

        if (libro == null) {
            return false;
        }

        libro.setActivo(false);
        sistema.getBitacora().registrar("ELIMINAR", "admin", "LIBROS");
        return true;
    }
}
