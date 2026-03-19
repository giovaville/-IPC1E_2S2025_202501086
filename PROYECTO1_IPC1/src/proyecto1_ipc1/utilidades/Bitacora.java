/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Gio
 */
public class Bitacora {
    
       private final String ruta;

    public Bitacora(String ruta) {
        this.ruta = ruta;
        asegurarArchivo();
    }

    private void asegurarArchivo() {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear bitacora.txt");
        }
    }

    public void registrar(String operacion, String usuario, String modulo) {
        String fecha = FechaUtil.fechaBitacora();
        String hora = FechaUtil.horaBitacora();

        String linea = "[" + operacion + "][" + usuario + "][" + modulo + "][" + fecha + "][" + hora + "]";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true));
            bw.write(linea);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("No se pudo escribir en la bitacora.");
        }
    }
}
