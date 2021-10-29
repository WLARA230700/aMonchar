package com.war.amonchar.Modelo;

public class Ingrediente {

    // VARIABLES
    private int id;
    private String nombre;

    // CONSTRUCTORES
    public Ingrediente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Ingrediente(String nombre) {
        this.id = 0;
        this.nombre = nombre;
    }

    public Ingrediente() {
        this.id = 0;
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

    // TO STRING
    @Override
    public String toString() {
        return "Nombre: " + nombre;
    }
}
