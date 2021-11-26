package com.war.amonchar.Modelo;

import java.util.HashMap;
import java.util.Map;

public class PlanSemanal {
    private String id;
    private String dia;
    private String tiempoComida;
    private String correoUsuario;
    private String idReceta;

    public PlanSemanal(String id, String dia, String tiempoComida, String correoUsuario, String idReceta) {
        this.id = id;
        this.dia = dia;
        this.tiempoComida = tiempoComida;
        this.correoUsuario = correoUsuario;
        this.idReceta = idReceta;
    }

    public PlanSemanal() {
        this.id = "";
        this.dia = "";
        this.tiempoComida = "";
        this.correoUsuario = "";
        this.idReceta = "";
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("dia", dia);
        result.put("tiempoComida", tiempoComida);
        result.put("correoUsuario", correoUsuario);
        result.put("idReceta", idReceta);

        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTiempoComida() {
        return tiempoComida;
    }

    public void setTiempoComida(String tiempoComida) {
        this.tiempoComida = tiempoComida;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(String idReceta) {
        this.idReceta = idReceta;
    }

    @Override
    public String toString() {
        return "PlanSemanal{" +
                "id='" + id + '\'' +
                ", dia='" + dia + '\'' +
                ", tiempoComida='" + tiempoComida + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                ", idReceta='" + idReceta + '\'' +
                '}';
    }
}//Fin clase PlanSemanal
