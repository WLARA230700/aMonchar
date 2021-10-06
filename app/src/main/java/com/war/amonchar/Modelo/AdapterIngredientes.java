package com.war.amonchar.Modelo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class AdapterIngredientes extends BaseAdapter {

    Context context;
    ArrayList<String> lista;

    public AdapterIngredientes(Context context, ArrayList<String> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        /*
        Code
        */

        return view;
    }
}
