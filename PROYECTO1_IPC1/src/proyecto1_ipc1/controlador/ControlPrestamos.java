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
    private String ultimoMensaje;

    public ControlPrestamos(ControlBiblioteca sistema) {
        this.sistema = sistema;
        this.ultimoMensaje = "";
    }

    public boolean registrarPrestamo(String carnet, String codigoLibro) {
        ultimoMensaje = "";

        Usuario estudiante = sistema.buscarUsuarioActivo(carnet);

        if (estudiante == null) {
            ultimoMensaje = "El carné no existe.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        if (!estudiante.esEstudiante()) {
            ultimoMensaje = "El usuario no es estudiante.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        if (contarPrestamosActivos(carnet) >= 3) {
            ultimoMensaje = "El estudiante ya tiene 3 préstamos activos.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        if (tienePrestamosVencidos(carnet)) {
            ultimoMensaje = "El estudiante tiene préstamos vencidos.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        Libro libro = sistema.buscarLibroPorCodigo(codigoLibro);
        if (libro == null) {
            libro = sistema.buscarLibroPorIsbn(codigoLibro);
        }

        if (libro == null) {
            ultimoMensaje = "El libro no existe.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        if (!libro.isActivo()) {
            ultimoMensaje = "El libro está inactivo.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        if (libro.getCantidadDisponible() <= 0) {
            ultimoMensaje = "No hay ejemplares disponibles.";
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
            ultimoMensaje = "Se alcanzó el máximo de préstamos del sistema.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", carnet, "PRESTAMOS");
            return false;
        }

        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        sistema.agregarPrestamoAHistorial(carnet, prestamo);
        sistema.guardarPrestamos();
        sistema.getBitacora().registrar("CREAR", carnet, "PRESTAMOS");

        ultimoMensaje = "Préstamo registrado con código " + codigoPrestamo;
        return true;
    }

    public boolean registrarDevolucion(String codigoPrestamo) {
        ultimoMensaje = "";

        Prestamo prestamo = sistema.buscarPrestamoPorCodigo(codigoPrestamo);

        if (prestamo == null) {
            ultimoMensaje = "El código de préstamo no existe.";
            sistema.getBitacora().registrar("OPERACION ERRONEA", "admin", "DEVOLUCIONES");
            return false;
        }

        if (!Prestamo.ESTADO_ACTIVO.equalsIgnoreCase(prestamo.getEstado())) {
            ultimoMensaje = "Ese préstamo ya fue devuelto.";
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
        ultimoMensaje = "Devolución registrada correctamente.";
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

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}