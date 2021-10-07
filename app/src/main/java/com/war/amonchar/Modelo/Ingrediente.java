package com.war.amonchar.Modelo;

public class Ingrediente {

    // VARIABLES
    private int id;
    private String nombre;
    private boolean comprado = false;

    // CONSTRUCTORES
    public Ingrediente(int id, String nombre, boolean comprado) {
        this.id = id;
        this.nombre = nombre;
        this.comprado = comprado;
    }

    public Ingrediente(String nombre) {
        this.id = 0;
        this.nombre = nombre;
        this.comprado = false;
    }

    public Ingrediente() {
        this.id = 0;
        this.nombre = "";
        this.comprado = false;
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

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Nombre: " + nombre +
                ", Comprado: " + comprado;
    }
}
