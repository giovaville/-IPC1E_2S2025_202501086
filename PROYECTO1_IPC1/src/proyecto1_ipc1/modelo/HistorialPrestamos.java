/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.modelo;

/**
 *
 * @author Gio
 */
public class HistorialPrestamos {

    private NodoPrestamo cabeza;

    public HistorialPrestamos() {
        cabeza = null;
    }

    public void agregarInicio(Prestamo prestamo) {
        NodoPrestamo nuevo = new NodoPrestamo(prestamo);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
    }

    public NodoPrestamo getCabeza() {
        return cabeza;
    }

    public boolean estaVacio() {
        return cabeza == null;
    }
}