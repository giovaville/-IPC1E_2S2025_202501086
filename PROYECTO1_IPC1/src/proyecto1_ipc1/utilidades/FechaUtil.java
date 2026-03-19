/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.utilidades;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author Gio
 */
public class FechaUtil {
    
   public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String fechaHoyTexto() {
        return LocalDate.now().format(FORMATO_FECHA);
    }

    public static String sumarDiasTexto(int dias) {
        return LocalDate.now().plusDays(dias).format(FORMATO_FECHA);
    }

    public static boolean estaVencido(String fechaLimite, String estado) {
        if (!"ACTIVO".equalsIgnoreCase(estado)) {
            return false;
        }

        LocalDate limite = LocalDate.parse(fechaLimite, FORMATO_FECHA);
        return LocalDate.now().isAfter(limite);
    }

    public static long diasAtraso(String fechaLimite) {
        LocalDate limite = LocalDate.parse(fechaLimite, FORMATO_FECHA);

        if (!LocalDate.now().isAfter(limite)) {
            return 0;
        }

        return ChronoUnit.DAYS.between(limite, LocalDate.now());
    }

    public static String fechaBitacora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
    }

    public static String horaBitacora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
    }
}
