package com.war.amonchar.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.war.amonchar.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterIngredientes extends BaseAdapter {

    Context context;
    ArrayList<Ingrediente> lista;
    int contadorIngredientes = 0;

    public AdapterIngredientes(Context context, ArrayList<Ingrediente> lista) {
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
            view = layoutInflater.inflate(R.layout.item_lista_compra, null);
        }

        TextView txtIngrediente = view.findViewById(R.id.txtIngrediente);
        TextView txtNumero = view.findViewById(R.id.txtNumero);

        txtIngrediente.setText(lista.get(i).getNombre());
        contadorIngredientes++;
        txtNumero.setText(contadorIngredientes+". ");

        return view;
    }
}
