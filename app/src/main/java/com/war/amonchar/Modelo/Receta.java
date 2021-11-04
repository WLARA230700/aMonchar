package com.war.amonchar.Modelo;

import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class Receta {

    private String id;
    private int tiempo_preparacion;
    private String tiempo_comida;
    private ArrayList<String> categorias;
    private String nombre_receta;
    private Uri imagen;
    private ArrayList<Float> cantidad_ingredientes;
    private ArrayList<String> ingredientes;
    private ArrayList<String> pasos;

    public Receta(String id, int tiempo_preparacion, String tiempo_comida, ArrayList<String> categorias, String nombre_receta, Uri imagen, ArrayList<Float> cantidad_ingredientes, ArrayList<String> ingredientes, ArrayList<String> pasos) {
        this.id = id;
        this.tiempo_preparacion = tiempo_preparacion;
        this.tiempo_comida = tiempo_comida;
        this.categorias = categorias;
        this.nombre_receta = nombre_receta;
        this.imagen = imagen;
        this.cantidad_ingredientes = cantidad_ingredientes;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
    }

    public Receta() {
        this.id = "";
        this.tiempo_preparacion = 0;
        this.tiempo_comida = "";
        this.categorias = null;
        this.nombre_receta = "";
        this.imagen = Uri.EMPTY;
        this.cantidad_ingredientes = null;
        this.ingredientes = null;
        this.pasos = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTiempo_preparacion() {
        return tiempo_preparacion;
    }

    public void setTiempo_preparacion(int tiempo_preparacion) {
        this.tiempo_preparacion = tiempo_preparacion;
    }

    public String getTiempo_comida() {
        return tiempo_comida;
    }

    public void setTiempo_comida(String tiempo_comida) {
        this.tiempo_comida = tiempo_comida;
    }

    public ArrayList<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<String> categorias) {
        this.categorias = categorias;
    }

    public String getNombre_receta() {
        return nombre_receta;
    }

    public void setNombre_receta(String nombre_receta) {
        this.nombre_receta = nombre_receta;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public ArrayList<Float> getCantidad_ingredientes() {
        return cantidad_ingredientes;
    }

    public void setCantidad_ingredientes(ArrayList<Float> cantidad_ingredientes) {
        this.cantidad_ingredientes = cantidad_ingredientes;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ArrayList<String> getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList<String> pasos) {
        this.pasos = pasos;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id='" + id + '\'' +
                ", tiempo_preparacion=" + tiempo_preparacion +
                ", tiempo_comida='" + tiempo_comida + '\'' +
                ", categorias=" + categorias +
                ", nombre_receta='" + nombre_receta + '\'' +
                ", imagen=" + imagen +
                ", cantidad_ingredientes=" + cantidad_ingredientes +
                ", ingredientes=" + ingredientes +
                ", pasos=" + pasos +
                '}';
    }
}
