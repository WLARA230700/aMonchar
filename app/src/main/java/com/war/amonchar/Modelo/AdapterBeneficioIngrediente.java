package com.war.amonchar.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.war.amonchar.R;

import java.util.List;

public class AdapterBeneficioIngrediente extends BaseAdapter {

    private Context context;
    private List<BeneficioIngrediente> lista;

    public AdapterBeneficioIngrediente(Context context, List<BeneficioIngrediente> lista) {
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
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_beneficio_ingrediente, null);
        }

        TextView txtIngrediente = view.findViewById(R.id.txtIngrediente);
        TextView txtDescripcion = view.findViewById(R.id.txtDescripcion);
        ImageView imgIngrediente = view.findViewById(R.id.imgIngrediente);

        txtIngrediente.setText(lista.get(i).getIngrediente());
        txtDescripcion.setText(lista.get(i).getDescripcion());

        Glide.with(context)
                .load(lista.get(i).getImagen())
                .centerCrop()
                .into(imgIngrediente);

        return view;
    }
}
