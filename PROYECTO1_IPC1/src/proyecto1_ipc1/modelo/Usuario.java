/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1_ipc1.modelo;

/**
 *
 * @author Gio
 */
public class Usuario {

    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_OPERADOR = "OPERADOR";
    public static final String ROL_ESTUDIANTE = "ESTUDIANTE";

    private String rol;
    private String usuario;
    private String contrasena;
    private String nombreCompleto;
    private String carrera;
    private boolean activo;

    public Usuario(String rol, String usuario, String contrasena, String nombreCompleto, String carrera) {
        this.rol = rol;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.carrera = carrera;
        this.activo = true;
    }

    public String getRol() {
        return rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCarrera() {
        return carrera;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean esAdmin() {
        return ROL_ADMIN.equalsIgnoreCase(rol);
    }

    public boolean esOperador() {
        return ROL_OPERADOR.equalsIgnoreCase(rol);
    }

    public boolean esEstudiante() {
        return ROL_ESTUDIANTE.equalsIgnoreCase(rol);
    }

    public String toFileLine() {
        return rol + ";" + usuario + ";" + contrasena + ";" + nombreCompleto + ";" + carrera + ";" + activo;
    }
}