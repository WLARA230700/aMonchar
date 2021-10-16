package com.war.amonchar.Modelo;

import android.app.Application;

public class GlobalVariables extends Application {

    private Usuario usuarioLogueado = null;

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

}//Fin clase GlobalVariables
