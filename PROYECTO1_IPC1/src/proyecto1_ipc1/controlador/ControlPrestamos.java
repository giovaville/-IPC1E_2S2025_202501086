/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.controlador;
import proyecto1_ipc1.modelo.Libro;
import proyecto1_ipc1.modelo.Prestamo;
import proyecto1_ipc1.modelo.Usuario;
import proyecto1_ipc1.utilidades.FechaUtil;
/**
 *
 * @author Gio
 */
public class ControlPrestamos {
    
    private final ControlBiblioteca sistema;

    public ControlPrestamos(ControlBiblioteca sistema) {
        this.sistema = sistema;
    }

    public boolean registrarPrestamo(String carnet, String codigoLibro) {
        Usuario estudiante = sistema.buscarUsuarioActivo(carnet);

        if (estudiante == null || !estudiante.esEstudiante()) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        if (contarPrestamosActivos(carnet) >= 3) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        if (tienePrestamosVencidos(carnet)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        Libro libro = sistema.buscarLibroPorCodigo(codigoLibro);
        if (libro == null) {
            libro = sistema.buscarLibroPorIsbn(codigoLibro);
        }

        if (libro == null || !libro.isActivo() || libro.getCantidadDisponible() <= 0) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        String codigoPrestamo = generarCodigoPrestamo();

        Prestamo prestamo = new Prestamo(
                codigoPrestamo,
                carnet,
                libro.getCodigo(),
                FechaUtil.fechaHoyTexto(),
                FechaUtil.sumarDiasTexto(15),
                "",
                Prestamo.ESTADO_ACTIVO
        );

        if (!sistema.agregarPrestamoInterno(prestamo)) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        sistema.agregarPrestamoAHistorial(carnet, prestamo);
        sistema.guardarPrestamos();
        sistema.getBitacora().registrar("CREAR", carnet, "PRESTAMOS");
        return true;
    }

    public boolean registrarDevolucion(String codigoPrestamo) {
        Prestamo prestamo = sistema.buscarPrestamoPorCodigo(codigoPrestamo);

        if (prestamo == null) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "DEVOLUCIONES");
            return false;
        }

        if (!Prestamo.ESTADO_ACTIVO.equalsIgnoreCase(prestamo.getEstado())) {
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "DEVOLUCIONES");
            return false;
        }

        prestamo.setEstado(Prestamo.ESTADO_DEVUELTO);
        prestamo.setFechaDevolucion(FechaUtil.fechaHoyTexto());

        Libro libro = sistema.buscarLibroPorCodigo(prestamo.getCodigoLibro());
        if (libro != null) {
            libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);
        }

        sistema.guardarPrestamos();
        sistema.getBitacora().registrar("MODIFICAR", "admin", "DEVOLUCIONES");
        return true;
    }

    public int contarPrestamosActivos(String carnet) {
        int contador = 0;

        for (int i = 0; i < sistema.getTotalPrestamos(); i++) {
            Prestamo p = sistema.getPrestamos()[i];
            if (p != null &&
                p.getCarnet().equalsIgnoreCase(carnet) &&
                Prestamo.ESTADO_ACTIVO.equalsIgnoreCase(p.getEstado())) {
                contador++;
            }
        }

        return contador;
    }

    public boolean tienePrestamosVencidos(String carnet) {
        for (int i = 0; i < sistema.getTotalPrestamos(); i++) {
            Prestamo p = sistema.getPrestamos()[i];
            if (p != null &&
                p.getCarnet().equalsIgnoreCase(carnet) &&
                FechaUtil.estaVencido(p.getFechaLimite(), p.getEstado())) {
                return true;
            }
        }
        return false;
    }

    private String generarCodigoPrestamo() {
        return "P" + String.format("%04d", sistema.getTotalPrestamos() + 1);
    }
}
