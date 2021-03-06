package com.war.amonchar.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.war.amonchar.R;

import java.util.ArrayList;

public class AdapterPreparacion extends BaseAdapter {

    Context context;
    ArrayList<PasoPreparacion> lista;

    public AdapterPreparacion(Context context, ArrayList<PasoPreparacion> lista) {
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

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_pasos_preparacion, null);
        }

        TextView lblNumeroPaso = view.findViewById(R.id.lblNumeroPaso);
        TextView lblDescripcion = view.findViewById(R.id.lblDescripcion);

        lblNumeroPaso.setText(lista.get(i).getNumero()+"");
        lblDescripcion.setText(lista.get(i).getDescripcion());

        return view;
    }
}
