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
    private String ultimoMensaje;

    public ControlLibros(ControlBiblioteca sistema) {
        this.sistema = sistema;
        this.ultimoMensaje = "";
    }

    public boolean registrarLibro(String codigo, String isbn, String titulo, String autor,
                                  String genero, int anio, int cantidad) {

        ultimoMensaje = "";

        if (Validaciones.textoVacio(codigo) || Validaciones.textoVacio(isbn)
                || Validaciones.textoVacio(titulo) || Validaciones.textoVacio(autor)
                || Validaciones.textoVacio(genero)) {
            ultimoMensaje = "Todos los campos son obligatorios.";
            return false;
        }

        if (!Validaciones.esIsbnValido(isbn)) {
            ultimoMensaje = "El ISBN debe tener exactamente 10 o 13 dígitos.";
            return false;
        }

        if (cantidad <= 0) {
            ultimoMensaje = "La cantidad debe ser mayor que cero.";
            return false;
        }

        if (anio <= 0) {
            ultimoMensaje = "El año debe ser mayor que cero.";
            return false;
        }

        if (sistema.buscarLibroPorCodigo(codigo) != null) {
            ultimoMensaje = "Ya existe un libro con ese código.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "LIBROS");
            return false;
        }

        if (sistema.buscarLibroPorIsbn(isbn) != null) {
            ultimoMensaje = "Ya existe un libro con ese ISBN.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "LIBROS");
            return false;
        }

        Libro libro = new Libro(codigo, isbn, titulo, autor, genero, anio, cantidad);

        if (!sistema.agregarLibroInterno(libro)) {
            ultimoMensaje = "Se alcanzó el máximo de libros del sistema.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "LIBROS");
            return false;
        }

        sistema.guardarLibros();
        sistema.getBitacora().registrar("CREAR", "admin", "LIBROS");
        ultimoMensaje = "Libro registrado correctamente.";
        return true;
    }

    public boolean modificarLibro(String codigoOriginal, String isbn, String titulo, String autor,
                                  String genero, int anio, int cantidadTotal) {

        ultimoMensaje = "";

        Libro libro = sistema.buscarLibroPorCodigo(codigoOriginal);

        if (libro == null) {
            ultimoMensaje = "No existe un libro activo con ese código.";
            return false;
        }

        if (Validaciones.textoVacio(isbn) || Validaciones.textoVacio(titulo)
                || Validaciones.textoVacio(autor) || Validaciones.textoVacio(genero)) {
            ultimoMensaje = "Todos los campos son obligatorios.";
            return false;
        }

        if (!Validaciones.esIsbnValido(isbn)) {
            ultimoMensaje = "El ISBN debe tener exactamente 10 o 13 dígitos.";
            return false;
        }

        if (anio <= 0) {
            ultimoMensaje = "El año debe ser mayor que cero.";
            return false;
        }

        if (cantidadTotal <= 0) {
            ultimoMensaje = "La cantidad total debe ser mayor que cero.";
            return false;
        }

        for (int i = 0; i < sistema.getTotalLibros(); i++) {
            Libro otro = sistema.getLibros()[i];
            if (otro != null && otro.isActivo()
                    && !otro.getCodigo().equalsIgnoreCase(codigoOriginal)
                    && otro.getIsbn().equalsIgnoreCase(isbn)) {
                ultimoMensaje = "Ya existe otro libro con ese ISBN.";
                return false;
            }
        }

        int prestados = libro.getCantidadPrestada();
        if (cantidadTotal < prestados) {
            ultimoMensaje = "La cantidad total no puede ser menor a los ejemplares prestados actualmente.";
            return false;
        }

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setGenero(genero);
        libro.setAnio(anio);
        libro.setCantidadTotal(cantidadTotal);
        libro.setCantidadDisponible(cantidadTotal - prestados);

        sistema.guardarLibros();
        sistema.getBitacora().registrar("MODIFICAR", "admin", "LIBROS");
        ultimoMensaje = "Libro modificado correctamente.";
        return true;
    }

    public boolean eliminarLibro(String codigo) {
        ultimoMensaje = "";

        Libro libro = sistema.buscarLibroPorCodigo(codigo);

        if (libro == null) {
            ultimoMensaje = "No existe un libro activo con ese código.";
            return false;
        }

        int prestamosActivos = sistema.contarPrestamosActivosDeLibro(libro.getCodigo());

        if (prestamosActivos > 0) {
            ultimoMensaje = "No se puede eliminar. El libro tiene " + prestamosActivos + " préstamo(s) activo(s).";
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "LIBROS");
            return false;
        }

        libro.setActivo(false);
        sistema.guardarLibros();
        sistema.getBitacora().registrar("ELIMINAR", "admin", "LIBROS");
        ultimoMensaje = "Libro eliminado correctamente.";
        return true;
    }

    public Libro buscarPorCodigo(String codigo) {
        return sistema.buscarLibroPorCodigo(codigo);
    }

    public Libro[] buscarPorNombre(String nombre) {
        Libro[] resultados = new Libro[sistema.getTotalLibros()];
        int pos = 0;

        for (int i = 0; i < sistema.getTotalLibros(); i++) {
            Libro l = sistema.getLibros()[i];
            if (l != null && l.isActivo()
                    && l.getTitulo().toLowerCase().contains(nombre.toLowerCase())) {
                resultados[pos] = l;
                pos++;
            }
        }

        return resultados;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}