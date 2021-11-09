package com.war.amonchar.Modelo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.war.amonchar.R;

import java.util.ArrayList;

public class AdapterRecetas extends BaseAdapter {

    Context context;
    ArrayList<Receta> lista;

    public AdapterRecetas(Context context, ArrayList<Receta> lista) {
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
            view = layoutInflater.inflate(R.layout.item_inicio, null);
        }

        TextView lbTituloPS = view.findViewById(R.id.lbTituloPS);
        TextView lbTiempoPreparacion = view.findViewById(R.id.lbTiempoPreparacion);
        ImageView imgReceta = view.findViewById(R.id.imgReceta);
        ImageView bgTimer = view.findViewById(R.id.bgTimer);
        ImageView imgTimer = view.findViewById(R.id.imgTimer);

        lbTituloPS.setText(lista.get(i).getNombre_receta());
        lbTiempoPreparacion.setText(lista.get(i).getTiempo_preparacion()+" min");
        imgReceta.setImageResource(R.drawable.custom_foto_inicio);
        bgTimer.setImageResource(R.drawable.timer_background);
        imgTimer.setImageResource(R.drawable.ic_clock_regular);

        return view;
    }

}
