package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.AdapterRecetas;
import com.war.amonchar.Modelo.Adapter_Ingredientes_agregar_receta;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Receta;

import java.util.ArrayList;

public class act_inicio extends AppCompatActivity {

    ImageView icUsuario, icListaCompra, icBuscar, btnCerrarSesion;
    TextView lbHolaUsuario, txtCatDesayuno, txtCatMeriendas, txtCatAlmuerzo, txtCatCena;
    LinearLayout receta1;
    BD db;
    GridLayout gridRecetas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Receta> recetas  = new ArrayList<>();
    Receta receta;

    AdapterRecetas adapterRecetas;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        //System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_inicio);
        getSupportActionBar().hide();

        db = new BD(getApplicationContext());

        inicializarFirebase();
        //cargarRecetas();

        icUsuario = findViewById(R.id.icUsuario);
        icListaCompra = findViewById(R.id.icListaCompra);
        icBuscar = findViewById(R.id.icBuscar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        gridRecetas = findViewById(R.id.gridRecetas);
        txtCatDesayuno = findViewById(R.id.txtCatDesayuno);
        txtCatMeriendas = findViewById(R.id.txtCatMeriendas);
        txtCatAlmuerzo = findViewById(R.id.txtCatAlmuerzo);
        txtCatCena = findViewById(R.id.txtCatCena);

        lbHolaUsuario = findViewById(R.id.lbHolaUsuario);
        lbHolaUsuario.setText("¡Hola " + ((GlobalVariables) getApplication()).getUsuarioLogueado().getNombreUsuario() + "!");

        icUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_inicio.this, act_perfil_usuario_general.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion(db);
            }
        });

        icListaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_inicio.this, act_lista_compra.class);
                startActivity(intent);
            }
        });

        icBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_buscar_general.class);
                startActivity(intent);
            }
        });

        txtCatDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarRecetasDeayuno();
            }
        });

        txtCatMeriendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txtCatAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txtCatCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }//Fin onCreate

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void cargarRecetas(){

       databaseReference.child("Receta").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recetas.clear();
                for(DataSnapshot obtSnapshot : snapshot.getChildren()){
                    //receta = obtSnapshot.getValue(Receta.class);

                    GenericTypeIndicator<ArrayList<String>> x = new GenericTypeIndicator<ArrayList<String>>(){};


                    String id = obtSnapshot.child("id").getValue(String.class);
                    int tiempo_preparacion = obtSnapshot.child("tiempo_preparacion").getValue(int.class);
                    String tiempo_comida = obtSnapshot.child("tiempo_comida").getValue(String.class);
                    ArrayList<String> categorias = obtSnapshot.child("categorias").getValue(x);
                    String nombre_receta = obtSnapshot.child("nombre_receta").getValue(String.class);
                    String imagen = obtSnapshot.child("imagen").getValue(String.class);
                    ArrayList<String> cantidad_ingredientes = obtSnapshot.child("cantidad_ingredientes").getValue(x);
                    ArrayList<String> ingredientes = obtSnapshot.child("ingredientes").getValue(x);
                    ArrayList<String> pasos = obtSnapshot.child("pasos").getValue(x);

                    receta = new Receta(id, tiempo_preparacion, tiempo_comida, categorias, nombre_receta, imagen, cantidad_ingredientes, ingredientes, pasos);

                    recetas.add(receta);

                    //arrayAdapter = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, personas);
                    //lstPersonas.setAdapter(arrayAdapter);

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

    public void cargarRecetasDeayuno(){

        databaseReference.orderByChild("tiempo_comida").equalTo("Desayuno").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recetas.clear();
                for(DataSnapshot obtSnapshot : snapshot.getChildren()){
                    //receta = obtSnapshot.getValue(Receta.class);

                    GenericTypeIndicator<ArrayList<String>> x = new GenericTypeIndicator<ArrayList<String>>(){};


                    String id = obtSnapshot.child("id").getValue(String.class);
                    int tiempo_preparacion = obtSnapshot.child("tiempo_preparacion").getValue(int.class);
                    String tiempo_comida = obtSnapshot.child("tiempo_comida").getValue(String.class);
                    ArrayList<String> categorias = obtSnapshot.child("categorias").getValue(x);
                    String nombre_receta = obtSnapshot.child("nombre_receta").getValue(String.class);
                    String imagen = obtSnapshot.child("imagen").getValue(String.class);
                    ArrayList<String> cantidad_ingredientes = obtSnapshot.child("cantidad_ingredientes").getValue(x);
                    ArrayList<String> ingredientes = obtSnapshot.child("ingredientes").getValue(x);
                    ArrayList<String> pasos = obtSnapshot.child("pasos").getValue(x);

                    receta = new Receta(id, tiempo_preparacion, tiempo_comida, categorias, nombre_receta, imagen, cantidad_ingredientes, ingredientes, pasos);

                    recetas.add(receta);

                    //arrayAdapter = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, personas);
                    //lstPersonas.setAdapter(arrayAdapter);

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

    private void cerrarSesion(BD db) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("¿Seguro que desea cerrar sesión?");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.validarUsuario(((GlobalVariables) getApplication()).getUsuarioLogueado().getCorreo(),
                        ((GlobalVariables) getApplication()).getUsuarioLogueado().getContrasenia(),
                        false);
                ((GlobalVariables) getApplication()).getUsuarioLogueado().setLogueado(false);
                Toast.makeText(getApplicationContext(), "Sesión Cerrada", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), act_bienvenida.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }
}