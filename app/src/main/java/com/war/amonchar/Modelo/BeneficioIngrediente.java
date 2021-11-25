package com.war.amonchar.Modelo;

public class BeneficioIngrediente {

    private String ingrediente;
    private String descripcion;
    private String imagen;

    public BeneficioIngrediente(String ingrediente, String descripcion, String imagen) {
        this.ingrediente = ingrediente;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "BeneficioIngrediente{" +
                "ingrediente='" + ingrediente + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}// Fin clase BeneficioIngrediente
