/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.modelo;

/**
 *
 * @author Gio
 */
public class Prestamo {

    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_DEVUELTO = "DEVUELTO";

    private String codigoPrestamo;
    private String carnet;
    private String codigoLibro;
    private String fechaPrestamo;
    private String fechaLimite;
    private String fechaDevolucion;
    private String estado;

    public Prestamo(String codigoPrestamo, String carnet, String codigoLibro,
                    String fechaPrestamo, String fechaLimite,
                    String fechaDevolucion, String estado) {
        this.codigoPrestamo = codigoPrestamo;
        this.carnet = carnet;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    public String getCodigoPrestamo() {
        return codigoPrestamo;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getCodigoLibro() {
        return codigoLibro;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setCodigoPrestamo(String codigoPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public void setCodigoLibro(String codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toFileLine() {
        return codigoPrestamo + ";" + carnet + ";" + codigoLibro + ";" +
               fechaPrestamo + ";" + fechaLimite + ";" +
               fechaDevolucion + ";" + estado;
    }
}