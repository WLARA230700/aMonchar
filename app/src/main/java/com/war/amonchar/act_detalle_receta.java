package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.war.amonchar.Modelo.Adapter_Ingredientes_agregar_receta;
import com.war.amonchar.Modelo.Adapter_categoria_detalle_receta;

import java.util.ArrayList;

public class act_detalle_receta extends AppCompatActivity {

    ImageView btnBack;
    LinearLayout planSemanal, contenedorCategorias;
    Button btnPlanSemanal;

    TextView txt_nombre_receta;
    Button btn_categoria1, btn_categoria2;
    ImageView img_fotografia;
    ListView lista_ingredientes, lista_pasos;
    Adapter_categoria_detalle_receta adapter_categoria_detalle_receta;

    String recetaId = "";

    private DatabaseReference mDatabse;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Boolean estadoPlanSemanal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_detalle_receta);
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.btnBack);
        planSemanal = findViewById(R.id.planSemanal);
        btnPlanSemanal = findViewById(R.id.btnPlanSemanal);

        txt_nombre_receta = findViewById(R.id.txt_nombre_receta);
        contenedorCategorias = findViewById(R.id.contenedorCategorias);
        img_fotografia = findViewById(R.id.img_fotografia);
        lista_ingredientes = findViewById(R.id.lista_ingredientes);
        lista_pasos = findViewById(R.id.lista_pasos);

        planSemanal.setVisibility(View.GONE);

        btnPlanSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoPlanSemanal = mostrarPlanSemanal(planSemanal, btnPlanSemanal, estadoPlanSemanal);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Extracción de datos de la BD
        mDatabse = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        recetaId = "64bf9c07-a316-4cd9-bc42-6e9921722b58";

        mDatabse.child("Receta").child(recetaId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                        GenericTypeIndicator<ArrayList<String>> x = new GenericTypeIndicator<ArrayList<String>>(){};
                        ArrayList<String> categorias = dataSnapshot.child("categorias").getValue(x);

                    adapter_categoria_detalle_receta = new Adapter_categoria_detalle_receta(getApplicationContext(), categorias);

                    for(int i = 0; i < adapter_categoria_detalle_receta.getCount(); i++){
                        View view = adapter_categoria_detalle_receta.getView(i, null, contenedorCategorias);
                        contenedorCategorias.addView(view);
                    }

                        String nombreReceta = dataSnapshot.child("nombre_receta").getValue().toString();
                        //String categoria = dataSnapshot.child("categorias").getValue().toString();

                        //String ingredientes = dataSnapshot.child("ingredientes").getValue().toString();
                        //String pasos = dataSnapshot.child("pasos").getValue().toString();


                        txt_nombre_receta.setText(nombreReceta);
                       // btn_categoria1.setText(categoria);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        descargarViaUrl();
    }//Fin onCreate

    //Método para descargar URL de la base de datos
    public void descargarViaUrl(){

        StorageReference imageRef = storageReference.child("Fotografia_Recetas/gelato_64bf9c07-a316-4cd9-bc42-6e9921722b58");

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Cargar una imagen URI usando Glide
                Glide.with(act_detalle_receta.this)
                        .load(uri)
                        .error(R.drawable.ic_launcher_background)
                        .into(img_fotografia);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public boolean mostrarPlanSemanal(LinearLayout opciones, Button planSemanal, Boolean bool){

        if(bool){
            opciones.setVisibility(View.GONE);
            Drawable arrow = getDrawable(R.drawable.ic_yellow_arrow_down);
            planSemanal.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow, null);
            bool = false;
        }else{
            opciones.setVisibility(View.VISIBLE);
            Drawable arrow = getDrawable(R.drawable.ic_yellow_arrow_up);
            planSemanal.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow, null);
            bool = true;
        }

        return bool;

    }//Fin mostrarPlanSemanal

}//Fin clase act_detalle_receta