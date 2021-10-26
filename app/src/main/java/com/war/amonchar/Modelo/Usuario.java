package com.war.amonchar.Modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

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
    public Usuario(String nombreUsuario, String correo, String contrasenia, String nombre, String apellidos, String biografia, Bitmap fotografia) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.biografia = biografia;
        this.fotografia = bitmapToString(fotografia);
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
    public Usuario(String nombreUsuario, String apellidos, String biografia, Bitmap fotografia) {
        this.nombreUsuario = nombreUsuario;
        this.correo = "";
        this.contrasenia = "";
        this.nombre = "";
        this.apellidos = apellidos ;
        this.biografia = biografia;
        this.fotografia = bitmapToString(fotografia);
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

    public Bitmap getFotografia() {
        return stringToBitmap(this.fotografia);
    }

    public void setFotografia(Bitmap fotografia) {
        this.fotografia = bitmapToString(fotografia);
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }

    // CAMBIAR DE BITMAP A STRING || STRING A BITMAP
    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
