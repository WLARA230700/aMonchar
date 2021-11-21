package com.war.amonchar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.war.amonchar.Modelo.AdapterRecetas;
import com.war.amonchar.Modelo.Receta;

import java.util.ArrayList;

public class act_lista_recetas_inicio extends AppCompatActivity {

    ImageView btnBack;
    ArrayList<String> idRecetas;
    String buscado = "";
    TextView txtBuscado;



    GridLayout gridRecetas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Receta> recetas  = new ArrayList<>();
    Receta receta;

    AdapterRecetas adapterRecetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_lista_recetas);

        getSupportActionBar().hide();
        btnBack = findViewById(R.id.btnBack);
        gridRecetas = findViewById(R.id.gridRecetas);
        txtBuscado = findViewById(R.id.txtBuscado);


        idRecetas = getIntent().getStringArrayListExtra("idRecetas");
        //Toast.makeText(getApplicationContext(), idRecetas.toString(), Toast.LENGTH_SHORT).show();

        buscado = getIntent().getStringExtra("buscado");

        txtBuscado.setText("\"" + buscado + "\"");

        inicializarFirebase();
        cargarRecetas();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_inicio.class);
                startActivity(intent);
               /* idRecetas.clear();
                gridRecetas.removeAllViews();*/
                finish();
            }
        });

    }// Fin OnCreate

    @Override public void onBackPressed(){ Intent intent = new Intent(this, act_inicio.class); startActivity(intent); }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void cargarRecetas(){
        databaseReference.child("Receta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recetas.clear();
                for(DataSnapshot obtSnapshot : snapshot.getChildren()){

                    for (int i = 0; i < idRecetas.size(); i++) {

                        if(obtSnapshot.child("id").getValue(String.class).equals(idRecetas.get(i))){

                            GenericTypeIndicator<ArrayList<String>> indicator = new GenericTypeIndicator<ArrayList<String>>(){};

                            String id = obtSnapshot.child("id").getValue(String.class);
                            int tiempo_preparacion = obtSnapshot.child("tiempo_preparacion").getValue(int.class);
                            String medida_tiempo_preparacion = obtSnapshot.child("medida_tiempo_preparacion").getValue(String.class);
                            String tiempo_comida = obtSnapshot.child("tiempo_comida").getValue(String.class);
                            ArrayList<String> categorias = obtSnapshot.child("categorias").getValue(indicator);
                            String nombre_receta = obtSnapshot.child("nombre_receta").getValue(String.class);
                            String imagen = obtSnapshot.child("imagen").getValue(String.class);
                            ArrayList<String> cantidad_ingredientes = obtSnapshot.child("cantidad_ingredientes").getValue(indicator);
                            ArrayList<String> ingredientes = obtSnapshot.child("ingredientes").getValue(indicator);
                            ArrayList<String> pasos = obtSnapshot.child("pasos").getValue(indicator);

                            receta = new Receta(id,
                                    tiempo_preparacion,
                                    medida_tiempo_preparacion,
                                    tiempo_comida,
                                    categorias,
                                    nombre_receta,
                                    imagen,
                                    cantidad_ingredientes,
                                    ingredientes,
                                    pasos);

                            recetas.add(receta);
                        }
                    }
                }

                int contador = 0;

                adapterRecetas = new AdapterRecetas(getApplicationContext(), recetas);


                if(gridRecetas.getChildCount() > 0){
                    contador = gridRecetas.getChildCount();
                }

                for(int i = contador; i < recetas.size(); i++){
                    View view = adapterRecetas.getView(i, null, gridRecetas);

                    int posReceta = i;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                            intent.putExtra("idReceta", recetas.get(posReceta).getId());
                            intent.putExtra("nombreReceta", recetas.get(posReceta).getNombre_receta());
                            startActivity(intent);
                        }
                    });
                    gridRecetas.addView(view);
                }
                int children = gridRecetas.getChildCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}