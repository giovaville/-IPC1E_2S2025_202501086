/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.controlador;
import proyecto1_ipc1.modelo.HistorialPrestamos;
import proyecto1_ipc1.modelo.Libro;
import proyecto1_ipc1.modelo.Prestamo;
import proyecto1_ipc1.modelo.Usuario;
import proyecto1_ipc1.reportes.GeneradorReportes;
import proyecto1_ipc1.utilidades.ArchivoManager;
import proyecto1_ipc1.utilidades.Bitacora;
/**
 *
 * @author Gio
 */
public class ControlBiblioteca {

    public static final int MAX_USUARIOS = 100;
    public static final int MAX_LIBROS = 200;
    public static final int MAX_PRESTAMOS = 500;

    private final Usuario[] usuarios;
    private final Libro[] libros;
    private final Prestamo[] prestamos;
    private final HistorialPrestamos[] historiales;

    private int totalUsuarios;
    private int totalLibros;
    private int totalPrestamos;

    private final ArchivoManager archivoManager;
    private final Bitacora bitacora;

    private final ControlUsuarios controlUsuarios;
    private final ControlLibros controlLibros;
    private final ControlPrestamos controlPrestamos;
    private final GeneradorReportes generadorReportes;

    public ControlBiblioteca() {
        usuarios = new Usuario[MAX_USUARIOS];
        libros = new Libro[MAX_LIBROS];
        prestamos = new Prestamo[MAX_PRESTAMOS];
        historiales = new HistorialPrestamos[MAX_USUARIOS];

        for (int i = 0; i < MAX_USUARIOS; i++) {
            historiales[i] = new HistorialPrestamos();
        }

        archivoManager = new ArchivoManager("cuentas.txt", "prestamos.txt");
        bitacora = new Bitacora("bitacora.txt");

        totalUsuarios = archivoManager.cargarUsuarios(usuarios);
        totalPrestamos = archivoManager.cargarPrestamos(prestamos);

        controlUsuarios = new ControlUsuarios(this);
        controlLibros = new ControlLibros(this);
        controlPrestamos = new ControlPrestamos(this);
        generadorReportes = new GeneradorReportes(this);

        inicializarLibrosPrueba();
        reconstruirHistoriales();
        sincronizarDisponiblesConPrestamos();
    }

    private void inicializarLibrosPrueba() {
        if (totalLibros == 0) {
            agregarLibroInterno(new Libro("L001", "9780135166307", "Clean Code", "Robert C. Martin", "Programacion", 2008, 4));
            agregarLibroInterno(new Libro("L002", "9780132350884", "Clean Architecture", "Robert C. Martin", "Arquitectura", 2017, 3));
            agregarLibroInterno(new Libro("L003", "9780134494166", "Effective Java", "Joshua Bloch", "Programacion", 2018, 5));
            agregarLibroInterno(new Libro("L004", "9788497592208", "Cien anios de soledad", "Gabriel Garcia Marquez", "Literatura", 1967, 2));
            agregarLibroInterno(new Libro("L005", "9786070742502", "El principito", "Antoine de Saint-Exupery", "Literatura", 1943, 4));
        }
    }

    private void reconstruirHistoriales() {
        for (int i = 0; i < totalPrestamos; i++) {
            Prestamo p = prestamos[i];
            if (p != null) {
                int indice = indiceUsuarioActivo(p.getCarnet());
                if (indice != -1) {
                    historiales[indice].agregarInicio(p);
                }
            }
        }
    }

    private void sincronizarDisponiblesConPrestamos() {
        for (int i = 0; i < totalLibros; i++) {
            if (libros[i] != null && libros[i].isActivo()) {
                libros[i].setCantidadDisponible(libros[i].getCantidadTotal());
            }
        }

        for (int i = 0; i < totalPrestamos; i++) {
            Prestamo p = prestamos[i];

            if (p != null && Prestamo.ESTADO_ACTIVO.equalsIgnoreCase(p.getEstado())) {
                Libro libro = buscarLibroPorCodigo(p.getCodigoLibro());
                if (libro != null && libro.getCantidadDisponible() > 0) {
                    libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
                }
            }
        }
    }

    public Usuario autenticar(String usuario, String contrasena) {
        return controlUsuarios.autenticar(usuario, contrasena);
    }

    public boolean registrarEstudiante(String carnet, String nombre, String carrera, String contrasena) {
        return controlUsuarios.registrarEstudiante(carnet, nombre, carrera, contrasena);
    }

    public boolean registrarOperador(String usuario, String nombre, String contrasena) {
        return controlUsuarios.registrarOperador(usuario, nombre, contrasena);
    }

    public boolean registrarLibro(String codigo, String isbn, String titulo, String autor,
                                  String genero, int anio, int cantidad) {
        return controlLibros.registrarLibro(codigo, isbn, titulo, autor, genero, anio, cantidad);
    }

    public boolean registrarPrestamo(String carnet, String codigoLibro) {
        return controlPrestamos.registrarPrestamo(carnet, codigoLibro);
    }

    public boolean registrarDevolucion(String codigoPrestamo) {
        return controlPrestamos.registrarDevolucion(codigoPrestamo);
    }

    public int indiceUsuarioActivo(String usuario) {
        for (int i = 0; i < totalUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u != null && u.isActivo() && u.getUsuario().equalsIgnoreCase(usuario)) {
                return i;
            }
        }
        return -1;
    }

    public Usuario buscarUsuarioActivo(String usuario) {
        int indice = indiceUsuarioActivo(usuario);
        if (indice == -1) {
            return null;
        }
        return usuarios[indice];
    }

    public Libro buscarLibroPorCodigo(String codigo) {
        for (int i = 0; i < totalLibros; i++) {
            Libro l = libros[i];
            if (l != null && l.isActivo() && l.getCodigo().equalsIgnoreCase(codigo)) {
                return l;
            }
        }
        return null;
    }

    public Libro buscarLibroPorIsbn(String isbn) {
        for (int i = 0; i < totalLibros; i++) {
            Libro l = libros[i];
            if (l != null && l.isActivo() && l.getIsbn().equalsIgnoreCase(isbn)) {
                return l;
            }
        }
        return null;
    }

    public Prestamo buscarPrestamoPorCodigo(String codigoPrestamo) {
        for (int i = 0; i < totalPrestamos; i++) {
            Prestamo p = prestamos[i];
            if (p != null && p.getCodigoPrestamo().equalsIgnoreCase(codigoPrestamo)) {
                return p;
            }
        }
        return null;
    }

    public boolean agregarUsuarioInterno(Usuario usuario) {
        if (totalUsuarios >= MAX_USUARIOS) {
            return false;
        }
        usuarios[totalUsuarios] = usuario;
        totalUsuarios++;
        return true;
    }

    public boolean agregarLibroInterno(Libro libro) {
        if (totalLibros >= MAX_LIBROS) {
            return false;
        }
        libros[totalLibros] = libro;
        totalLibros++;
        return true;
    }

    public boolean agregarPrestamoInterno(Prestamo prestamo) {
        if (totalPrestamos >= MAX_PRESTAMOS) {
            return false;
        }
        prestamos[totalPrestamos] = prestamo;
        totalPrestamos++;
        return true;
    }

    public void guardarUsuarios() {
        archivoManager.guardarUsuarios(usuarios, totalUsuarios);
    }

    public void guardarPrestamos() {
        archivoManager.guardarPrestamos(prestamos, totalPrestamos);
    }

    public void agregarPrestamoAHistorial(String carnet, Prestamo prestamo) {
        int indice = indiceUsuarioActivo(carnet);
        if (indice != -1) {
            historiales[indice].agregarInicio(prestamo);
        }
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public Libro[] getLibros() {
        return libros;
    }

    public Prestamo[] getPrestamos() {
        return prestamos;
    }

    public HistorialPrestamos[] getHistoriales() {
        return historiales;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public int getTotalLibros() {
        return totalLibros;
    }

    public int getTotalPrestamos() {
        return totalPrestamos;
    }

    public Bitacora getBitacora() {
        return bitacora;
    }

    public ControlUsuarios getControlUsuarios() {
        return controlUsuarios;
    }

    public ControlLibros getControlLibros() {
        return controlLibros;
    }

    public ControlPrestamos getControlPrestamos() {
        return controlPrestamos;
    }

    public GeneradorReportes getGeneradorReportes() {
        return generadorReportes;
    }
}
