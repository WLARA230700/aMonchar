package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.lang.reflect.Type;
import java.util.ArrayList;

public class act_plan_semanal extends AppCompatActivity {

    LinearLayout contenidoLunes, contenidoMartes, contenidoMiercoles, contenidoJueves, contenidoViernes, contenidoSabado, contenidoDomingo;
    Button btnLunes, btnMartes, btnMiercoles, btnJueves, btnViernes, btnSabado, btnDomingo;

    TextView msgVacio;

    ImageView btnBack;

    GridLayout gridLunes, gridMartes, gridMiercoles, gridJueves, gridViernes, gridSabado, gridDomingo;

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

        inicializarFirebase();
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

        msgVacio = findViewById(R.id.msgVacio);

        gridLunes = findViewById(R.id.gridLunes);
        gridMartes = findViewById(R.id.gridMartes);
        gridMiercoles = findViewById(R.id.gridMiercoles);
        gridJueves = findViewById(R.id.gridJueves);
        gridViernes = findViewById(R.id.gridViernes);
        gridSabado = findViewById(R.id.gridSabado);
        gridDomingo = findViewById(R.id.gridDomingo);

        contenidoLunes.setVisibility(View.GONE);
        contenidoMartes.setVisibility(View.GONE);
        contenidoMiercoles.setVisibility(View.GONE);
        contenidoJueves.setVisibility(View.GONE);
        contenidoViernes.setVisibility(View.GONE);
        contenidoSabado.setVisibility(View.GONE);
        contenidoDomingo.setVisibility(View.GONE);
        msgVacio.setVisibility(View.GONE);

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

    }//Fin del onCreate

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void eliminarDePlanSemanal(String idPlanSemanal){

        databaseReference.child("PlanSemanal").child(idPlanSemanal)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Ha ocurrido un problema al eliminar", Toast.LENGTH_SHORT).show();
            }
        });

    }// Fin método eliminarDePlanSemanal

    /*public void cargarPlanSemanal(){

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
        //gridLunes.removeAllViews();
        //gridMartes.removeAllViews();
        insertarReceta();
    }

    public void insertarReceta(){

        // Cargar recetas Lunes
        if(recetasLunes.size() != 0){
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

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eliminarDePlanSemanal(recetasLunes.get(posReceta).getIdPlanSemanal());
                        gridLunes.removeView(view);
                        recetasLunes.remove(posReceta);

                        if(gridLunes.getChildCount() == 0){
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView txt = new TextView(getApplicationContext());
                            txt.setLayoutParams(params);
                            txt.setText("Nada Registrado");
                            txt.setTextColor(getColor(R.color.amarilloBase));
                            txt.setTypeface(msgVacio.getTypeface());
                            txt.setVisibility(View.VISIBLE);
                            txt.setTextAlignment(msgVacio.getTextAlignment());
                            txt.setId(View.generateViewId());
                            gridLunes.addView(txt);
                        }

                        return true;
                    }
                });

                gridLunes.addView(view, posReceta);
            }
        }

        // Cargar recetas Martes
        if(recetasMartes.size() != 0){
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

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eliminarDePlanSemanal(recetasMartes.get(posReceta).getIdPlanSemanal());
                        gridMartes.removeView(view);
                        recetasMartes.remove(posReceta);

                        if(gridMartes.getChildCount() == 0){
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView txt = new TextView(getApplicationContext());
                            txt.setLayoutParams(params);
                            txt.setText("Nada Registrado");
                            txt.setTextColor(getColor(R.color.amarilloBase));
                            txt.setTypeface(msgVacio.getTypeface());
                            txt.setVisibility(View.VISIBLE);
                            txt.setTextAlignment(msgVacio.getTextAlignment());
                            txt.setId(View.generateViewId());
                            gridMartes.addView(txt);
                        }

                        return true;
                    }
                });

                gridMartes.addView(view, posReceta);
            }
        }

        // Cargar recetas Miércoles
        if(recetasMiercoles.size() != 0){
            gridMiercoles.removeAllViews();
            adapterPlanSemanal = new AdapterPlanSemanal(getApplicationContext(), recetasMiercoles);

            for(int i = 0; i < recetasMiercoles.size(); i++){
                View view = adapterPlanSemanal.getView(i, null, gridMiercoles);

                int posReceta = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                        intent.putExtra("idReceta", recetasMiercoles.get(posReceta).getId());
                        intent.putExtra("nombreReceta", recetasMiercoles.get(posReceta).getNombre_receta());
                        startActivity(intent);
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eliminarDePlanSemanal(recetasMiercoles.get(posReceta).getIdPlanSemanal());
                        gridMiercoles.removeView(view);
                        recetasMiercoles.remove(posReceta);

                        if(gridMiercoles.getChildCount() == 0){
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView txt = new TextView(getApplicationContext());
                            txt.setLayoutParams(params);
                            txt.setText("Nada Registrado");
                            txt.setTextColor(getColor(R.color.amarilloBase));
                            txt.setTypeface(msgVacio.getTypeface());
                            txt.setVisibility(View.VISIBLE);
                            txt.setTextAlignment(msgVacio.getTextAlignment());
                            txt.setId(View.generateViewId());
                            gridMiercoles.addView(txt);
                        }

                        return true;
                    }
                });

                gridMiercoles.addView(view, posReceta);
            }
        }

        // Cargar recetas Jueves
        if(recetasJueves.size() != 0){
            gridJueves.removeAllViews();
            adapterPlanSemanal = new AdapterPlanSemanal(getApplicationContext(), recetasJueves);

            for(int i = 0; i < recetasJueves.size(); i++){
                View view = adapterPlanSemanal.getView(i, null, gridJueves);

                int posReceta = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                        intent.putExtra("idReceta", recetasJueves.get(posReceta).getId());
                        intent.putExtra("nombreReceta", recetasJueves.get(posReceta).getNombre_receta());
                        startActivity(intent);
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eliminarDePlanSemanal(recetasJueves.get(posReceta).getIdPlanSemanal());
                        gridJueves.removeView(view);
                        recetasJueves.remove(posReceta);

                        if(gridJueves.getChildCount() == 0){
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView txt = new TextView(getApplicationContext());
                            txt.setLayoutParams(params);
                            txt.setText("Nada Registrado");
                            txt.setTextColor(getColor(R.color.amarilloBase));
                            txt.setTypeface(msgVacio.getTypeface());
                            txt.setVisibility(View.VISIBLE);
                            txt.setTextAlignment(msgVacio.getTextAlignment());
                            txt.setId(View.generateViewId());
                            gridJueves.addView(txt);
                        }

                        return true;
                    }
                });

                gridJueves.addView(view, posReceta);
            }
        }

        // Cargar recetas Viernes
        if(recetasViernes.size() != 0){
            gridViernes.removeAllViews();
            adapterPlanSemanal = new AdapterPlanSemanal(getApplicationContext(), recetasViernes);

            for(int i = 0; i < recetasViernes.size(); i++){
                View view = adapterPlanSemanal.getView(i, null, gridViernes);

                int posReceta = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                        intent.putExtra("idReceta", recetasViernes.get(posReceta).getId());
                        intent.putExtra("nombreReceta", recetasViernes.get(posReceta).getNombre_receta());
                        startActivity(intent);
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eliminarDePlanSemanal(recetasViernes.get(posReceta).getIdPlanSemanal());
                        gridViernes.removeView(view);
                        recetasViernes.remove(posReceta);

                        if(gridViernes.getChildCount() == 0){
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView txt = new TextView(getApplicationContext());
                            txt.setLayoutParams(params);
                            txt.setText("Nada Registrado");
                            txt.setTextColor(getColor(R.color.amarilloBase));
                            txt.setTypeface(msgVacio.getTypeface());
                            txt.setVisibility(View.VISIBLE);
                            txt.setTextAlignment(msgVacio.getTextAlignment());
                            txt.setId(View.generateViewId());
                            gridViernes.addView(txt);
                        }

                        return true;
                    }
                });

                gridViernes.addView(view, posReceta);
            }
        }

        // Cargar recetas Sábado
        if(recetasSabado.size() != 0){
            gridSabado.removeAllViews();
            adapterPlanSemanal = new AdapterPlanSemanal(getApplicationContext(), recetasSabado);

            for(int i = 0; i < recetasSabado.size(); i++){
                View view = adapterPlanSemanal.getView(i, null, gridSabado);

                int posReceta = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                        intent.putExtra("idReceta", recetasSabado.get(posReceta).getId());
                        intent.putExtra("nombreReceta", recetasSabado.get(posReceta).getNombre_receta());
                        startActivity(intent);
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eliminarDePlanSemanal(recetasSabado.get(posReceta).getIdPlanSemanal());
                        gridSabado.removeView(view);
                        recetasSabado.remove(posReceta);

                        if(gridSabado.getChildCount() == 0){
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView txt = new TextView(getApplicationContext());
                            txt.setLayoutParams(params);
                            txt.setText("Nada Registrado");
                            txt.setTextColor(getColor(R.color.amarilloBase));
                            txt.setTypeface(msgVacio.getTypeface());
                            txt.setVisibility(View.VISIBLE);
                            txt.setTextAlignment(msgVacio.getTextAlignment());
                            txt.setId(View.generateViewId());
                            gridSabado.addView(txt);
                        }

                        return true;
                    }
                });

                gridSabado.addView(view, posReceta);
            }
        }

        // Cargar recetas Domingo
        if(recetasDomingo.size() != 0){
            gridDomingo.removeAllViews();
            adapterPlanSemanal = new AdapterPlanSemanal(getApplicationContext(), recetasDomingo);

            for(int i = 0; i < recetasDomingo.size(); i++){
                View view = adapterPlanSemanal.getView(i, null, gridDomingo);

                int posReceta = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                        intent.putExtra("idReceta", recetasDomingo.get(posReceta).getId());
                        intent.putExtra("nombreReceta", recetasDomingo.get(posReceta).getNombre_receta());
                        startActivity(intent);
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eliminarDePlanSemanal(recetasDomingo.get(posReceta).getIdPlanSemanal());
                        gridDomingo.removeView(view);
                        recetasDomingo.remove(posReceta);

                        if(gridDomingo.getChildCount() == 0){
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView txt = new TextView(getApplicationContext());
                            txt.setLayoutParams(params);
                            txt.setText("Nada Registrado");
                            txt.setTextColor(getColor(R.color.amarilloBase));
                            txt.setTypeface(msgVacio.getTypeface());
                            txt.setVisibility(View.VISIBLE);
                            txt.setTextAlignment(msgVacio.getTextAlignment());
                            txt.setId(View.generateViewId());
                            gridDomingo.addView(txt);
                        }

                        return true;
                    }
                });

                gridDomingo.addView(view, posReceta);
            }
        }

    }// Fin método insertarReceta

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