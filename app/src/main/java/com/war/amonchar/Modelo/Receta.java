package com.war.amonchar.Modelo;

import java.net.URI;
import java.util.Arrays;

public class Receta {

    private int id;
    private int tiempo_preparacion;
    private String tiempo_comida;
    private String[] categorias;
    private String nombre_receta;
    private URI imagen;
    private float[] cantidad_ingredientes;
    private String[] ingredientes;
    private String[] pasos;

    public Receta(int id, int tiempo_preparacion, String tiempo_comida, String[] categorias, String nombre_receta, URI imagen, float[] cantidad_ingredientes, String[] ingredientes, String[] pasos) {
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
        this.id = 0;
        this.tiempo_preparacion = 0;
        this.tiempo_comida = "";
        this.categorias = new String[]{};
        this.nombre_receta = "";
        this.imagen = URI.create("");
        this.cantidad_ingredientes = new float[]{};
        this.ingredientes = new String[]{};
        this.pasos = new String[]{};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String[] getCategorias() {
        return categorias;
    }

    public void setCategorias(String[] categorias) {
        this.categorias = categorias;
    }

    public String getNombre_receta() {
        return nombre_receta;
    }

    public void setNombre_receta(String nombre_receta) {
        this.nombre_receta = nombre_receta;
    }

    public URI getImagen() {
        return imagen;
    }

    public void setImagen(URI imagen) {
        this.imagen = imagen;
    }

    public float[] getCantidad_ingredientes() {
        return cantidad_ingredientes;
    }

    public void setCantidad_ingredientes(float[] cantidad_ingredientes) {
        this.cantidad_ingredientes = cantidad_ingredientes;
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String[] getPasos() {
        return pasos;
    }

    public void setPasos(String[] pasos) {
        this.pasos = pasos;
    }

    @Override
    public String toString() {
        return "Receta: " +
                "id=" + id +
                ", tiempo_preparacion=" + tiempo_preparacion +
                ", tiempo_comida='" + tiempo_comida + '\'' +
                ", categorias=" + Arrays.toString(categorias) +
                ", nombre_receta='" + nombre_receta + '\'' +
                ", imagen=" + imagen +
                ", cantidad_ingredientes=" + Arrays.toString(cantidad_ingredientes) +
                ", ingredientes=" + Arrays.toString(ingredientes) +
                ", pasos=" + Arrays.toString(pasos);
    }
}
