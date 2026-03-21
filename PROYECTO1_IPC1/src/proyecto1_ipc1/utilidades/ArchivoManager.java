/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import proyecto1_ipc1.modelo.Libro;
import proyecto1_ipc1.modelo.Prestamo;
import proyecto1_ipc1.modelo.Usuario;

/**
 *
 * @author Gio
 */
public class ArchivoManager {

    private final String rutaCuentas;
    private final String rutaLibros;
    private final String rutaPrestamos;

    public ArchivoManager(String rutaCuentas, String rutaLibros, String rutaPrestamos) {
        this.rutaCuentas = rutaCuentas;
        this.rutaLibros = rutaLibros;
        this.rutaPrestamos = rutaPrestamos;

        asegurarArchivo(rutaCuentas);
        asegurarArchivo(rutaLibros);
        asegurarArchivo(rutaPrestamos);
    }

    private void asegurarArchivo(String ruta) {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo: " + ruta);
        }
    }

    public int cargarUsuarios(Usuario[] usuarios) {
        int contador = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaCuentas));
            String linea;

            while ((linea = br.readLine()) != null && contador < usuarios.length) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(";", -1);

                if (partes.length >= 6) {
                    Usuario usuario = new Usuario(partes[0], partes[1], partes[2], partes[3], partes[4]);
                    usuario.setActivo(Boolean.parseBoolean(partes[5]));
                    usuarios[contador] = usuario;
                    contador++;
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios.");
        }

        return contador;
    }

    public int cargarLibros(Libro[] libros) {
        int contador = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaLibros));
            String linea;

            while ((linea = br.readLine()) != null && contador < libros.length) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(";", -1);

                if (partes.length >= 9) {
                    Libro libro = new Libro(
                            partes[0],
                            partes[1],
                            partes[2],
                            partes[3],
                            partes[4],
                            Integer.parseInt(partes[5]),
                            Integer.parseInt(partes[6])
                    );

                    libro.setCantidadDisponible(Integer.parseInt(partes[7]));
                    libro.setActivo(Boolean.parseBoolean(partes[8]));

                    libros[contador] = libro;
                    contador++;
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error al cargar libros.");
        } catch (NumberFormatException e) {
            System.out.println("Error en formato de datos de libros.");
        }

        return contador;
    }

    public int cargarPrestamos(Prestamo[] prestamos) {
        int contador = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaPrestamos));
            String linea;

            while ((linea = br.readLine()) != null && contador < prestamos.length) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(";", -1);

                if (partes.length >= 7) {
                    prestamos[contador] = new Prestamo(
                            partes[0], partes[1], partes[2],
                            partes[3], partes[4], partes[5], partes[6]
                    );
                    contador++;
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error al cargar prestamos.");
        }

        return contador;
    }

    public void guardarUsuarios(Usuario[] usuarios, int totalUsuarios) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaCuentas, false));

            for (int i = 0; i < totalUsuarios; i++) {
                if (usuarios[i] != null) {
                    bw.write(usuarios[i].toFileLine());
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios.");
        }
    }

    public void guardarLibros(Libro[] libros, int totalLibros) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaLibros, false));

            for (int i = 0; i < totalLibros; i++) {
                if (libros[i] != null) {
                    bw.write(libros[i].toFileLine());
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error al guardar libros.");
        }
    }

    public void guardarPrestamos(Prestamo[] prestamos, int totalPrestamos) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaPrestamos, false));

            for (int i = 0; i < totalPrestamos; i++) {
                if (prestamos[i] != null) {
                    bw.write(prestamos[i].toFileLine());
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error al guardar prestamos.");
        }
    }
}