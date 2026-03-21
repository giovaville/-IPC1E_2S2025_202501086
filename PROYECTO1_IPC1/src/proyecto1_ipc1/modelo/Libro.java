/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.modelo;

/**
 *
 * @author Gio
 */
public class Libro {

    private String codigo;
    private String isbn;
    private String titulo;
    private String autor;
    private String genero;
    private int anio;
    private int cantidadTotal;
    private int cantidadDisponible;
    private boolean activo;

    public Libro(String codigo, String isbn, String titulo, String autor, String genero, int anio, int cantidadTotal) {
        this.codigo = codigo;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anio = anio;
        this.cantidadTotal = cantidadTotal;
        this.cantidadDisponible = cantidadTotal;
        this.activo = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnio() {
        return anio;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getCantidadPrestada() {
        return cantidadTotal - cantidadDisponible;
    }

    public String toFileLine() {
        return codigo + ";" + isbn + ";" + titulo + ";" + autor + ";" + genero + ";"
                + anio + ";" + cantidadTotal + ";" + cantidadDisponible + ";" + activo;
    }
}