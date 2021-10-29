package com.war.amonchar.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.war.amonchar.R;

import java.util.ArrayList;

public class AdapterDropdownItem extends BaseAdapter {

    Context context;
    ArrayList<String> list;

    public AdapterDropdownItem(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_dropdown_opcion, null);
        }

        TextView txt_item = view.findViewById(R.id.txt_item);
        txt_item.setText(list.get(i));

        return view;
    }
}
