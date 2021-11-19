package com.war.amonchar.Modelo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class RecetaPlanSemanal implements Parcelable {

    private String id;
    private int tiempo_preparacion;
    private String medida_tiempo_preparacion;
    private String tiempo_comida;
    private ArrayList<String> categorias;
    private String nombre_receta;
    private String imagen;
    private ArrayList<String> cantidad_ingredientes;
    private ArrayList<String> ingredientes;
    private ArrayList<String> pasos;
    private String dia;
    private String tiempoComidaPlanSemanal;

    public RecetaPlanSemanal(String id, int tiempo_preparacion, String medida_tiempo_preparacion, String tiempo_comida, ArrayList<String> categorias, String nombre_receta, String imagen, ArrayList<String> cantidad_ingredientes, ArrayList<String> ingredientes, ArrayList<String> pasos) {
        this.id = id;
        this.tiempo_preparacion = tiempo_preparacion;
        this.medida_tiempo_preparacion = medida_tiempo_preparacion;
        this.tiempo_comida = tiempo_comida;
        this.categorias = categorias;
        this.nombre_receta = nombre_receta;
        this.imagen = imagen;
        this.cantidad_ingredientes = cantidad_ingredientes;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
    }

    public RecetaPlanSemanal(String id, int tiempo_preparacion, String medida_tiempo_preparacion, String tiempo_comida, ArrayList<String> categorias, String nombre_receta, String imagen, ArrayList<String> cantidad_ingredientes, ArrayList<String> ingredientes, ArrayList<String> pasos, String dia, String tiempoComidaPlanSemanal) {
        this.id = id;
        this.tiempo_preparacion = tiempo_preparacion;
        this.medida_tiempo_preparacion = medida_tiempo_preparacion;
        this.tiempo_comida = tiempo_comida;
        this.categorias = categorias;
        this.nombre_receta = nombre_receta;
        this.imagen = imagen;
        this.cantidad_ingredientes = cantidad_ingredientes;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.dia = dia;
        this.tiempoComidaPlanSemanal = tiempoComidaPlanSemanal;
    }

    public RecetaPlanSemanal() {
        this.id = "";
        this.tiempo_preparacion = 0;
        this.medida_tiempo_preparacion = "";
        this.tiempo_comida = "";
        this.categorias = null;
        this.nombre_receta = "";
        this.imagen = "";
        this.cantidad_ingredientes = null;
        this.ingredientes = null;
        this.pasos = null;
        this.dia = "";
        this.tiempoComidaPlanSemanal = "";
    }

    protected RecetaPlanSemanal(Parcel in) {
        id = in.readString();
        tiempo_preparacion = in.readInt();
        medida_tiempo_preparacion = in.readString();
        tiempo_comida = in.readString();
        categorias = in.createStringArrayList();
        nombre_receta = in.readString();
        imagen = in.readString();
        cantidad_ingredientes = in.createStringArrayList();
        ingredientes = in.createStringArrayList();
        pasos = in.createStringArrayList();
        dia = in.readString();
        tiempoComidaPlanSemanal = in.readString();
    }

    public static final Creator<RecetaPlanSemanal> CREATOR = new Creator<RecetaPlanSemanal>() {
        @Override
        public RecetaPlanSemanal createFromParcel(Parcel in) {
            return new RecetaPlanSemanal(in);
        }

        @Override
        public RecetaPlanSemanal[] newArray(int size) {
            return new RecetaPlanSemanal[size];
        }
    };

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

    public String getMedida_tiempo_preparacion() {
        return medida_tiempo_preparacion;
    }

    public void setMedida_tiempo_preparacion(String medida_tiempo_preparacion) {
        this.medida_tiempo_preparacion = medida_tiempo_preparacion;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<String> getCantidad_ingredientes() {
        return cantidad_ingredientes;
    }

    public void setCantidad_ingredientes(ArrayList<String> cantidad_ingredientes) {
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTiempoComidaPlanSemanal() {
        return tiempoComidaPlanSemanal;
    }

    public void setTiempoComidaPlanSemanal(String tiempoComidaPlanSemanal) {
        this.tiempoComidaPlanSemanal = tiempoComidaPlanSemanal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeInt(tiempo_preparacion);
        parcel.writeString(medida_tiempo_preparacion);
        parcel.writeString(tiempo_comida);
        parcel.writeStringList(categorias);
        parcel.writeString(nombre_receta);
        parcel.writeString(imagen);
        parcel.writeStringList(cantidad_ingredientes);
        parcel.writeStringList(ingredientes);
        parcel.writeStringList(pasos);
        parcel.writeString(dia);
        parcel.writeString(tiempoComidaPlanSemanal);
    }
}

