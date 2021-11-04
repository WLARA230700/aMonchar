package com.war.amonchar.Modelo;

public class Ingrediente {

    // VARIABLES
    private int id;
    private float cantidad;
    private String nombre;

    // CONSTRUCTORES
    public Ingrediente(int id, float cantidad, String nombre) {
        this.id = id;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public Ingrediente(String nombre, float cantidad) {
        this.id = 0;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public Ingrediente(int id, String nombre) {
        this.id = id;
        this.cantidad = 0;
        this.nombre = nombre;
    }

    public Ingrediente(String nombre) {
        this.id = 0;
        this.cantidad = 0;
        this.nombre = nombre;
    }

    public Ingrediente() {
        this.id = 0;
        this.cantidad = 0;
        this.nombre = "";
    }

    // GETTER AND SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Nombre: " + nombre;
    }
}
