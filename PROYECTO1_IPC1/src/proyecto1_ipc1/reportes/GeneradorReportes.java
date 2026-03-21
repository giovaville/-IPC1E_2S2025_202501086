/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.reportes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import proyecto1_ipc1.controlador.ControlBiblioteca;
import proyecto1_ipc1.modelo.Libro;
import proyecto1_ipc1.modelo.Prestamo;
import proyecto1_ipc1.modelo.Usuario;
import proyecto1_ipc1.utilidades.FechaUtil;

/**
 *
 * @author Gio
 */
public class GeneradorReportes {

    private final ControlBiblioteca sistema;

    public GeneradorReportes(ControlBiblioteca sistema) {
        this.sistema = sistema;
    }

    public String generarPrestamosVencidos() {
        String nombreArchivo = "reporte_prestamos_vencidos_" + FechaUtil.fechaHoyTexto() + ".html";
        StringBuilder html = new StringBuilder();
        int total = 0;

        html.append("<html><head><meta charset='UTF-8'><title>Prestamos Vencidos</title></head><body>");
        html.append("<h1>Reporte de Prestamos Vencidos</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Codigo</th><th>Carnet</th><th>Libro</th><th>Fecha Limite</th><th>Dias Atraso</th></tr>");

        for (int i = 0; i < sistema.getTotalPrestamos(); i++) {
            Prestamo p = sistema.getPrestamos()[i];
            if (p != null && FechaUtil.estaVencido(p.getFechaLimite(), p.getEstado())) {
                html.append("<tr>");
                html.append("<td>").append(p.getCodigoPrestamo()).append("</td>");
                html.append("<td>").append(p.getCarnet()).append("</td>");
                html.append("<td>").append(p.getCodigoLibro()).append("</td>");
                html.append("<td>").append(p.getFechaLimite()).append("</td>");
                html.append("<td>").append(FechaUtil.diasAtraso(p.getFechaLimite())).append("</td>");
                html.append("</tr>");
                total++;
            }
        }

        html.append("</table>");
        html.append("<h3>Total de préstamos vencidos: ").append(total).append("</h3>");
        html.append("</body></html>");
        escribirArchivo(nombreArchivo, html.toString());
        return nombreArchivo;
    }

    public String generarLibrosDisponibles() {
        String nombreArchivo = "reporte_libros_disponibles_" + FechaUtil.fechaHoyTexto() + ".html";
        StringBuilder html = new StringBuilder();
        int total = 0;

        html.append("<html><head><meta charset='UTF-8'><title>Libros Disponibles</title></head><body>");
        html.append("<h1>Reporte de Libros Disponibles</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Codigo</th><th>Titulo</th><th>Autor</th><th>Disponibles</th></tr>");

        for (int i = 0; i < sistema.getTotalLibros(); i++) {
            Libro l = sistema.getLibros()[i];
            if (l != null && l.isActivo() && l.getCantidadDisponible() > 0) {
                html.append("<tr>");
                html.append("<td>").append(l.getCodigo()).append("</td>");
                html.append("<td>").append(l.getTitulo()).append("</td>");
                html.append("<td>").append(l.getAutor()).append("</td>");
                html.append("<td>").append(l.getCantidadDisponible()).append("</td>");
                html.append("</tr>");
                total++;
            }
        }

        html.append("</table>");
        html.append("<h3>Total de libros con disponibilidad: ").append(total).append("</h3>");
        html.append("</body></html>");
        escribirArchivo(nombreArchivo, html.toString());
        return nombreArchivo;
    }

    public String generarCincoLibrosMasPrestados() {
        String nombreArchivo = "reporte_5_libros_mas_prestados_" + FechaUtil.fechaHoyTexto() + ".html";
        StringBuilder html = new StringBuilder();

        Libro[] topLibros = new Libro[5];
        int[] topConteos = new int[5];

        for (int i = 0; i < sistema.getTotalLibros(); i++) {
            Libro libro = sistema.getLibros()[i];
            if (libro == null || !libro.isActivo()) {
                continue;
            }

            int conteo = 0;
            for (int j = 0; j < sistema.getTotalPrestamos(); j++) {
                Prestamo p = sistema.getPrestamos()[j];
                if (p != null && p.getCodigoLibro().equalsIgnoreCase(libro.getCodigo())) {
                    conteo++;
                }
            }

            for (int k = 0; k < 5; k++) {
                if (conteo > topConteos[k]) {
                    for (int m = 4; m > k; m--) {
                        topConteos[m] = topConteos[m - 1];
                        topLibros[m] = topLibros[m - 1];
                    }
                    topConteos[k] = conteo;
                    topLibros[k] = libro;
                    break;
                }
            }
        }

        html.append("<html><head><meta charset='UTF-8'><title>5 Libros Mas Prestados</title></head><body>");
        html.append("<h1>Reporte de los 5 Libros Mas Prestados</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Posicion</th><th>Codigo</th><th>Titulo</th><th>Autor</th><th>Total Prestamos</th></tr>");

        int totalFilas = 0;
        for (int i = 0; i < 5; i++) {
            if (topLibros[i] != null) {
                html.append("<tr>");
                html.append("<td>").append(i + 1).append("</td>");
                html.append("<td>").append(topLibros[i].getCodigo()).append("</td>");
                html.append("<td>").append(topLibros[i].getTitulo()).append("</td>");
                html.append("<td>").append(topLibros[i].getAutor()).append("</td>");
                html.append("<td>").append(topConteos[i]).append("</td>");
                html.append("</tr>");
                totalFilas++;
            }
        }

        html.append("</table>");
        html.append("<h3>Total de libros incluidos en el top: ").append(totalFilas).append("</h3>");
        html.append("</body></html>");
        escribirArchivo(nombreArchivo, html.toString());
        return nombreArchivo;
    }

    public String generarEstudiantesActivos() {
        String nombreArchivo = "reporte_estudiantes_activos_" + FechaUtil.fechaHoyTexto() + ".html";
        StringBuilder html = new StringBuilder();
        int total = 0;

        html.append("<html><head><meta charset='UTF-8'><title>Estudiantes Activos</title></head><body>");
        html.append("<h1>Estudiantes con Prestamos Activos</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Carnet</th><th>Nombre</th><th>Cantidad Activos</th></tr>");

        for (int i = 0; i < sistema.getTotalUsuarios(); i++) {
            Usuario u = sistema.getUsuarios()[i];
            if (u != null && u.isActivo() && u.esEstudiante()) {
                int activos = sistema.getControlPrestamos().contarPrestamosActivos(u.getUsuario());
                if (activos > 0) {
                    html.append("<tr>");
                    html.append("<td>").append(u.getUsuario()).append("</td>");
                    html.append("<td>").append(u.getNombreCompleto()).append("</td>");
                    html.append("<td>").append(activos).append("</td>");
                    html.append("</tr>");
                    total++;
                }
            }
        }

        html.append("</table>");
        html.append("<h3>Total de estudiantes con préstamos activos: ").append(total).append("</h3>");
        html.append("</body></html>");
        escribirArchivo(nombreArchivo, html.toString());
        return nombreArchivo;
    }

    private void escribirArchivo(String nombreArchivo, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write(contenido);
        } catch (IOException e) {
            System.out.println("No se pudo generar el reporte.");
        }
    }
}