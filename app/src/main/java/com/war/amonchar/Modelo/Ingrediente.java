package com.war.amonchar.Modelo;

public class Ingrediente {

    // VARIABLES
    private int id;
    private String cantidad;
    private String nombre;

    // CONSTRUCTORES
    public Ingrediente(int id, String cantidad, String nombre) {
        this.id = id;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public Ingrediente(String nombre, String cantidad) {
        this.id = 0;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public Ingrediente(int id, String nombre) {
        this.id = id;
        this.cantidad = "";
        this.nombre = nombre;
    }

    public Ingrediente(String nombre) {
        this.id = 0;
        this.cantidad = "";
        this.nombre = nombre;
    }

    public Ingrediente() {
        this.id = 0;
        this.cantidad = "";
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Nombre: " + nombre;
    }
}
