/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.modelo;

/**
 *
 * @author Gio
 */
public class NodoPrestamo {

    public Prestamo prestamo;
    public NodoPrestamo siguiente;

    public NodoPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
        this.siguiente = null;
    }
}
