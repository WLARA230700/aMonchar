package com.war.amonchar.Modelo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.war.amonchar.R;
import com.war.amonchar.act_detalle_receta;

import java.util.ArrayList;

public class AdapterRecetas extends BaseAdapter {

    Context context;
    ArrayList<Receta> lista;

    private DatabaseReference mDatabase;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    public AdapterRecetas(Context context, ArrayList<Receta> lista) {
        this.context = context;
        this.lista = lista;

        //Inicializar la BD
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
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

        StorageReference imageRef = storageReference.child("Fotografia_Recetas/"+lista.get(i).getNombre_receta()+"_"+lista.get(i).getId());

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Cargar una imagen URI usando Glide
                Glide.with(context)
                        .load(uri)
                        .centerCrop()
                        .error(R.drawable.custom_foto_inicio)
                        .into(imgReceta);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        lbTituloPS.setText(lista.get(i).getNombre_receta());
        lbTiempoPreparacion.setText(lista.get(i).getTiempo_preparacion()+" min");
        //imgReceta.setImageResource(R.drawable.custom_foto_inicio);
        bgTimer.setImageResource(R.drawable.timer_background);
        imgTimer.setImageResource(R.drawable.ic_clock_regular);

        return view;
    }

}
