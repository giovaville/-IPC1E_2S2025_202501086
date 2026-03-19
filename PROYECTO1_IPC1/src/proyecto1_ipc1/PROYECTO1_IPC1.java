/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1_ipc1;

import proyecto1_ipc1.controlador.ControlBiblioteca;
/**
 *
 * @author Gio
 */
public class PROYECTO1_IPC1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControlBiblioteca sistema = new ControlBiblioteca();

        System.out.println("Sistema base de BiblioSystem inicializado correctamente.");
        System.out.println("Usuarios cargados: " + sistema.getTotalUsuarios());
        System.out.println("Libros en memoria: " + sistema.getTotalLibros());
        System.out.println("Prestamos cargados: " + sistema.getTotalPrestamos());

        // Cuando hagamos la parte grafica, aqui abriremos LoginVista.
        // Ejemplo futuro:
        // new LoginVista(sistema).setVisible(true);
        
    }
    
}
