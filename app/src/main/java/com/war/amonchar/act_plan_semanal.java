package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.war.amonchar.Modelo.AdapterPlanSemanal;
import com.war.amonchar.Modelo.AdapterRecetas;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.PlanSemanal;
import com.war.amonchar.Modelo.Receta;

import java.util.ArrayList;

public class act_plan_semanal extends AppCompatActivity {

    LinearLayout contenidoLunes, contenidoMartes, contenidoMiercoles, contenidoJueves, contenidoViernes, contenidoSabado, contenidoDomingo;
    Button btnLunes, btnMartes, btnMiercoles, btnJueves, btnViernes, btnSabado, btnDomingo;

    ImageView btnBack;

    GridLayout gridLunes, gridMartes;

    Boolean lunes = false, martes = false, miercoles = false, jueves = false, viernes = false, sabado = false, domingo = false;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Receta> recetas  = new ArrayList<>();
    Receta receta;
    ArrayList<PlanSemanal> planes = new ArrayList<>();

    AdapterPlanSemanal adapterPlanSemanal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_plan_semanal);
        getSupportActionBar().hide();

        inicializarFirebase();
        cargarPlanSemanal();

        contenidoLunes = findViewById(R.id.planSemanal);
        contenidoMartes = findViewById(R.id.contenidoMartes);
        contenidoMiercoles = findViewById(R.id.contenidoMiercoles);
        contenidoJueves = findViewById(R.id.contenidoJueves);
        contenidoViernes = findViewById(R.id.contenidoViernes);
        contenidoSabado = findViewById(R.id.contenidoSabado);
        contenidoDomingo = findViewById(R.id.contenidoDomingo);

        btnLunes = findViewById(R.id.btnLunes);
        btnMartes = findViewById(R.id.btnMartes);
        btnMiercoles = findViewById(R.id.btnMiercoles);
        btnJueves = findViewById(R.id.btnJueves);
        btnViernes = findViewById(R.id.btnViernes);
        btnSabado = findViewById(R.id.btnSabado);
        btnDomingo = findViewById(R.id.btnDomingo);

        btnBack = findViewById(R.id.btnBack);

        gridLunes = findViewById(R.id.gridLunes);
        gridMartes = findViewById(R.id.gridMartes);

        contenidoLunes.setVisibility(View.GONE);
        contenidoMartes.setVisibility(View.GONE);
        contenidoMiercoles.setVisibility(View.GONE);
        contenidoJueves.setVisibility(View.GONE);
        contenidoViernes.setVisibility(View.GONE);
        contenidoSabado.setVisibility(View.GONE);
        contenidoDomingo.setVisibility(View.GONE);


        btnLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunes = cambiarEstado(contenidoLunes, btnLunes, lunes);
            }
        });

        btnMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                martes = cambiarEstado(contenidoMartes, btnMartes, martes);
            }
        });

        btnMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miercoles = cambiarEstado(contenidoMiercoles, btnMiercoles, miercoles);
            }
        });

        btnJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jueves = cambiarEstado(contenidoJueves, btnJueves, jueves);
            }
        });

        btnViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viernes = cambiarEstado(contenidoViernes, btnViernes, viernes);
            }
        });

        btnSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sabado = cambiarEstado(contenidoSabado, btnSabado, sabado);
            }
        });

        btnDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                domingo = cambiarEstado(contenidoDomingo, btnDomingo, domingo);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Toast.makeText(getApplicationContext(), planes.size(), Toast.LENGTH_SHORT).show();

    }//Fin del onCreate

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void cargarPlanSemanal(){

        databaseReference.child("PlanSemanal").orderByChild("correoUsuario")
                .equalTo(((GlobalVariables)getApplication()).getUsuarioLogueado().getCorreo())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Toast.makeText(getApplicationContext(), "gg", Toast.LENGTH_SHORT).show();

                if(snapshot.exists()){

                    for(DataSnapshot obtSnapshot : snapshot.getChildren()){

                        //planes.add(snapshot.getValue(PlanSemanal.class));
                        //Toast.makeText(getApplicationContext(), obtSnapshot.getValue(PlanSemanal.class).getIdReceta(), Toast.LENGTH_SHORT).show();

                        String dia = obtSnapshot.getValue(PlanSemanal.class).getDia();
                        String idReceta = obtSnapshot.getValue(PlanSemanal.class).getIdReceta();

                        //Toast.makeText(getApplicationContext(), idReceta, Toast.LENGTH_SHORT).show();
                        Log.d("idReceta: ", idReceta);

                        cargarReceta(idReceta);
                        //insertarReceta(dia);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void cargarReceta(String idReceta){
        //Toast.makeText(getApplicationContext(), idReceta, Toast.LENGTH_SHORT).show();
        databaseReference.child("Receta").child(idReceta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recetas.clear();
                if(snapshot.exists()){
                    for(DataSnapshot obtSnapshot : snapshot.getChildren()){
                        if(obtSnapshot.hasChildren()){
                            GenericTypeIndicator<ArrayList<String>> indicator = new GenericTypeIndicator<ArrayList<String>>(){};

                            //Toast.makeText(getApplicationContext(), obtSnapshot.child("nombre_receta").getValue(String.class), Toast.LENGTH_SHORT).show();
                            Log.d("Receta", obtSnapshot.child("nombre_receta").getValue(String.class)+" gg");
                        /*String id = obtSnapshot.child("id").getValue(String.class);
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

                        receta = obtSnapshot.getValue(Receta.class);

                        recetas.add(receta);*/
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void insertarReceta(String dia){
        switch (dia){
            case "Lunes":
                gridLunes.removeAllViews();
                gridLunes.setColumnCount(2);
                Log.d(dia + " : ",receta.getNombre_receta());
                break;

            case "Martes":
                gridMartes.removeAllViews();
                gridMartes.setColumnCount(2);
                break;

            case "Miércoles":
                break;

            case "Jueves":
                break;

            case "Viernes":
                break;

            case "Sábado":
                break;

            case "Domingo":
                break;
        }
    }

    public boolean cambiarEstado(LinearLayout linearLayout, Button btn, Boolean bool){
        if (bool){
            linearLayout.setVisibility(View.GONE);
            btn.setBackgroundColor(Color.rgb(253, 212, 123));
            btn.setTextColor(Color.rgb(26, 26, 26));
            Drawable drawable = getDrawable(R.drawable.ic_down);
            drawable.setTint(Color.BLACK);
            btn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            bool = false;
            return bool;
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            btn.setBackgroundColor(Color.rgb(249, 179, 52));
            btn.setTextColor(Color.WHITE);
            Drawable drawable = getDrawable(R.drawable.ic_up);
            drawable.setTint(Color.WHITE);
            btn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            bool = true;
            return bool;
        }
    }//Fin de cambiarEstado

}//Fin de la clase