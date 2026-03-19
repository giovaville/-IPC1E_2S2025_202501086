/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1_ipc1;

import javax.swing.SwingUtilities;
import proyecto1_ipc1.controlador.ControlBiblioteca;
import proyecto1_ipc1.vista.LoginVista;
/**
 *
 * @author Gio
 */
public class PROYECTO1_IPC1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ControlBiblioteca sistema = new ControlBiblioteca();
                LoginVista login = new LoginVista(sistema);
                login.setVisible(true);
            }
        });
    }
}
    

