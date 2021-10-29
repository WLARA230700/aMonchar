package com.war.amonchar.Modelo;

import android.net.Uri;

public class Usuario {

    // VARIABLES
    private String nombreUsuario;
    private String correo;
    private String contrasenia;
    private String nombre;
    private String apellidos;
    private String biografia;
    private String fotografia;
    private boolean logueado = false;

    // CONSTRUCTORES
    public Usuario(String nombreUsuario, String correo, String contrasenia, String nombre, String apellidos, String biografia, String fotografia) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.biografia = biografia;
        this.fotografia = fotografia;
    }
    public Usuario(String nombreUsuario, String correo, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = "";
        this.apellidos = "";
        this.biografia = "";
        this.fotografia = "";
    }
    public Usuario(String nombreUsuario, String apellidos, String biografia, String fotografia) {
        this.nombreUsuario = nombreUsuario;
        this.correo = "";
        this.contrasenia = "";
        this.nombre = "";
        this.apellidos = apellidos ;
        this.biografia = biografia;
        this.fotografia = fotografia;
    }
    public Usuario() {
        this.nombreUsuario = "";
        this.correo = "";
        this.contrasenia = "";
        this.nombre = "";
        this.apellidos = "";
        this.biografia = "";
        this.fotografia = "";
    }

    // GETTER AND SETTER
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }

    // TO STRING
    @Override
    public String toString() {
        return "nombreUsuario='" + nombreUsuario + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", biografia='" + biografia + '\'' +
                ", fotografia=" + fotografia;
    }
}// Fin de la clase
