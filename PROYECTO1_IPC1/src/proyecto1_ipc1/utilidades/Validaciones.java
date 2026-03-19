/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.utilidades;

/**
 *
 * @author Gio
 */
public class Validaciones {

    public static boolean textoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean esIsbnValido(String isbn) {
        if (isbn == null) {
            return false;
        }

        isbn = isbn.trim();

        if (isbn.length() != 10 && isbn.length() != 13) {
            return false;
        }

        for (int i = 0; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean esEnteroPositivo(String valor) {
        try {
            int numero = Integer.parseInt(valor);
            return numero > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
