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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.war.amonchar.Modelo.RecetaPlanSemanal;

import java.util.ArrayList;

public class act_plan_semanal extends AppCompatActivity {

    LinearLayout contenidoLunes, contenidoMartes, contenidoMiercoles, contenidoJueves, contenidoViernes, contenidoSabado, contenidoDomingo;
    Button btnLunes, btnMartes, btnMiercoles, btnJueves, btnViernes, btnSabado, btnDomingo;

    ImageView btnBack;

    GridLayout gridLunes, gridMartes;

    Boolean lunes = false, martes = false, miercoles = false, jueves = false, viernes = false, sabado = false, domingo = false;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecetaPlanSemanal receta;
    //ArrayList<PlanSemanal> planes = new ArrayList<>();

    ArrayList<RecetaPlanSemanal> recetasLunes  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasMartes  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasMiercoles  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasJueves  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasViernes  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasSabado  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasDomingo  = new ArrayList<>();

    AdapterPlanSemanal adapterPlanSemanal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_plan_semanal);
        getSupportActionBar().hide();

        recetasLunes = getIntent().getParcelableArrayListExtra("recetasLunes");
        recetasMartes = getIntent().getParcelableArrayListExtra("recetasMartes");
        recetasMiercoles = getIntent().getParcelableArrayListExtra("recetasMiercoles");
        recetasJueves = getIntent().getParcelableArrayListExtra("recetasJueves");
        recetasViernes = getIntent().getParcelableArrayListExtra("recetasViernes");
        recetasSabado = getIntent().getParcelableArrayListExtra("recetasSabado");
        recetasDomingo = getIntent().getParcelableArrayListExtra("recetasDomingo");

        //inicializarFirebase();
        //cargarPlanSemanal();

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

        insertarReceta();


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

    @Override
    protected void onStart() {
        super.onStart();
        //insertarReceta();
    }

    /*private void inicializarFirebase() {
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

                if(snapshot.exists()){

                    for(DataSnapshot obtSnapshot : snapshot.getChildren()){

                        String dia = obtSnapshot.getValue(PlanSemanal.class).getDia();
                        String idReceta = obtSnapshot.getValue(PlanSemanal.class).getIdReceta();
                        String tiempoComida = obtSnapshot.getValue(PlanSemanal.class).getTiempoComida();

                        cargarReceta(idReceta, dia, tiempoComida);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void cargarReceta(String idReceta, String dia, String tiempoComida){
        databaseReference.child("Receta").child(idReceta).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //recetas.clear();
                if(task.isComplete()){

                    DataSnapshot snapshot = task.getResult();

                    if(snapshot.exists()){

                        switch (dia){
                            case "Lunes":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasLunes.add(receta);
                                break;

                            case "Martes":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasMartes.add(receta);
                                break;

                            case "Miércoles":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasMiercoles.add(receta);
                                break;

                            case "Jueves":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasJueves.add(receta);
                                break;

                            case "Viernes":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasViernes.add(receta);
                                break;

                            case "Sábado":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasSabado.add(receta);
                                break;

                            case "Domingo":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasDomingo.add(receta);
                                break;
                        }

                        //insertarReceta(dia, tiempoComida);
                    }

                }
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        gridLunes.removeAllViews();
        gridMartes.removeAllViews();
        insertarReceta();
    }

    public void insertarReceta(){

        // Cargar recetas Lunes
        gridLunes.removeAllViews();
        adapterPlanSemanal = new AdapterPlanSemanal(getApplicationContext(), recetasLunes);

        for(int i = 0; i < recetasLunes.size(); i++){
            View view = adapterPlanSemanal.getView(i, null, gridLunes);

            int posReceta = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                    intent.putExtra("idReceta", recetasLunes.get(posReceta).getId());
                    intent.putExtra("nombreReceta", recetasLunes.get(posReceta).getNombre_receta());
                    startActivity(intent);
                }
            });

            gridLunes.addView(view);
        }

        // Cargar recetas Martes
        gridMartes.removeAllViews();
        adapterPlanSemanal = new AdapterPlanSemanal(getApplicationContext(), recetasMartes);

        for(int i = 0; i < recetasMartes.size(); i++){
            View view = adapterPlanSemanal.getView(i, null, gridMartes);

            int posReceta = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                    intent.putExtra("idReceta", recetasMartes.get(posReceta).getId());
                    intent.putExtra("nombreReceta", recetasMartes.get(posReceta).getNombre_receta());
                    startActivity(intent);
                }
            });

            gridMartes.addView(view);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        insertarReceta();
    }

}//Fin de la clase