package com.war.amonchar.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.war.amonchar.R;

import java.util.ArrayList;

public class Adapter_categoria_detalle_receta extends BaseAdapter {

    Context context;
    ArrayList<String> lista;

    public Adapter_categoria_detalle_receta(Context context, ArrayList<String> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        if (lista != null){
            return lista.size();
        }
        return 0;
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

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_categoria_detalle_receta, null);
        }

        Button btnCategoria = view.findViewById(R.id.btn_categoria);

        btnCategoria.setText(lista.get(i));


        return view;
    }
}
