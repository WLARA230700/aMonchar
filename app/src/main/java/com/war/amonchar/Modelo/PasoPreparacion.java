package com.war.amonchar.Modelo;

public class PasoPreparacion {

    // Declaración de variables
    private int numero;
    private String descripcion;

    public PasoPreparacion(int numero, String descripcion) {
        this.numero = numero;
        this.descripcion = descripcion;
    }

    public PasoPreparacion() {
        this.numero = 0;
        this.descripcion = "";
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
