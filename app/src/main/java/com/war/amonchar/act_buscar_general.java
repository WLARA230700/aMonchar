package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Receta;

import java.util.ArrayList;

public class act_buscar_general extends AppCompatActivity {

    ImageView icInicio, icListaCompra;
    Button btnBuscarIngredientes, btnBuscarTexto, btnDesayuno, btnAlmuerzo, btnCena, btnMerienda;
    TextView txtVegetariano, txtMar, txtCarnesRojas, txtCarnesBlancas, txtPostres, txtBebidas, txtUser;
    EditText txtBuscarReceta;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> idRecetas;
    ArrayList<Receta> todasLasResetas;
    Receta receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_buscar_general);

        icInicio = findViewById(R.id.icInicio);
        icListaCompra = findViewById(R.id.icListaCompra);
        btnBuscarIngredientes = findViewById(R.id.btnBuscarIngredientes);

        btnBuscarTexto = findViewById(R.id.btnBuscarTexto);
        txtBuscarReceta = findViewById(R.id.txtBuscarReceta);
        btnDesayuno = findViewById(R.id.btnDesayuno);
        btnAlmuerzo = findViewById(R.id.btnAlmuerzo);
        btnCena = findViewById(R.id.btnCena);
        btnMerienda = findViewById(R.id.btnMerienda);

        txtVegetariano = findViewById(R.id.txtVegetariano);
        txtMar = findViewById(R.id.txtMar);
        txtCarnesRojas = findViewById(R.id.txtCarnesRojas);
        txtCarnesBlancas = findViewById(R.id.txtCarnesBlancas);
        txtPostres = findViewById(R.id.txtPostres);
        txtBebidas = findViewById(R.id.txtBebidas);
        txtUser = findViewById(R.id.txtUser);

        txtUser.setText(((GlobalVariables) getApplication()).getUsuarioLogueado().getNombreUsuario());

        getSupportActionBar().hide();

        idRecetas = new ArrayList<>();
        todasLasResetas  = new ArrayList<>();

        inicializarFirebase();
        cargarRecetas();

        btnBuscarTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtBuscarReceta.getText().toString().isEmpty()){

                    String texto = txtBuscarReceta.getText().toString();

                    idRecetas.clear();

                    if(!txtBuscarReceta.getText().toString().matches("[a-zA-ZÀ-ÿñÑ][a-zA-ZÀ-ÿñÑ]*")){
                        Toast.makeText(getApplicationContext(), "No utilice caracteres especiales", Toast.LENGTH_SHORT).show();
                    }else{
                        rellenarIdRecetas(texto);

                        Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                        intent.putExtra("idRecetas", idRecetas);
                        intent.putExtra("buscado", txtBuscarReceta.getText().toString());
                        startActivity(intent);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Escriba la receta a buscar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        icInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_inicio.class);
                startActivity(intent);
            }
        });

        icListaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_lista_compra.class);
                startActivity(intent);
            }
        });

        btnBuscarIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_buscar_ingredientes.class);
                startActivity(intent);
            }
        });

    //TIEMPOS DE COMIDA
        btnDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    if(todasLasResetas.get(i).getTiempo_comida().equals("Desayuno")){

                        String id = todasLasResetas.get(i).getId().toString();

                        idRecetas.add(id);
                    }
                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Desayuno");
                startActivity(intent);

            }
        });

        btnAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    if(todasLasResetas.get(i).getTiempo_comida().equals("Almuerzo")){

                        String id = todasLasResetas.get(i).getId().toString();

                        idRecetas.add(id);
                    }
                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Almuerzo");
                startActivity(intent);

            }
        });

        btnCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    if(todasLasResetas.get(i).getTiempo_comida().equals("Cena")){

                        String id = todasLasResetas.get(i).getId().toString();

                        idRecetas.add(id);
                    }
                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Cena");
                startActivity(intent);
            }
        });

        btnMerienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    if(todasLasResetas.get(i).getTiempo_comida().matches(".*Merienda.*")){

                        String id = todasLasResetas.get(i).getId().toString();

                        idRecetas.add(id);
                    }
                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Merienda");
                startActivity(intent);

            }
        });

    //TIEMPOS DE CATEGORIAS
        txtVegetariano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    for (int c = 0; c < todasLasResetas.get(i).getCategorias().size(); c++) {

                        if(todasLasResetas.get(i).getCategorias().get(c).equals("Vegetariano")){

                            String id = todasLasResetas.get(i).getId();

                            idRecetas.add(id);
                        }
                    }

                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Vegetariano");
                startActivity(intent);

            }
        });

        txtMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    for (int c = 0; c < todasLasResetas.get(i).getCategorias().size(); c++) {

                        if(todasLasResetas.get(i).getCategorias().get(c).equals("Mar")){

                            String id = todasLasResetas.get(i).getId();

                            idRecetas.add(id);
                        }
                    }

                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Mar");
                startActivity(intent);

            }
        });

        txtCarnesRojas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    for (int c = 0; c < todasLasResetas.get(i).getCategorias().size(); c++) {

                        if(todasLasResetas.get(i).getCategorias().get(c).equals("Carnes Rojas")){

                            String id = todasLasResetas.get(i).getId();

                            idRecetas.add(id);
                        }
                    }

                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Carnes Rojas");
                startActivity(intent);

            }
        });

        txtCarnesBlancas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    for (int c = 0; c < todasLasResetas.get(i).getCategorias().size(); c++) {

                        if(todasLasResetas.get(i).getCategorias().get(c).equals("Carnes Blancas")){

                            String id = todasLasResetas.get(i).getId();

                            idRecetas.add(id);
                        }
                    }

                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Carnes Blancas");
                startActivity(intent);

            }
        });

        txtPostres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    for (int c = 0; c < todasLasResetas.get(i).getCategorias().size(); c++) {

                        if(todasLasResetas.get(i).getCategorias().get(c).equals("Postres")){

                            String id = todasLasResetas.get(i).getId();

                            idRecetas.add(id);
                        }
                    }

                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Postres");
                startActivity(intent);

            }
        });

        txtBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idRecetas.clear();
                for (int i = 0; i < todasLasResetas.size(); i++) {
                    for (int c = 0; c < todasLasResetas.get(i).getCategorias().size(); c++) {

                        if(todasLasResetas.get(i).getCategorias().get(c).equals("Bebidas")){

                            String id = todasLasResetas.get(i).getId();

                            idRecetas.add(id);
                        }
                    }

                }

                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", "Bebidas");
                startActivity(intent);

            }
        });

    }//Fin OnCreate

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void rellenarIdRecetas(String texto){
        for(int i = 0; i < todasLasResetas.size(); i++){
            if(todasLasResetas.get(i).getNombre_receta().toLowerCase().matches(".*" + texto + ".*")){
                idRecetas.add(todasLasResetas.get(i).getId());
            }
        }

        /*databaseReference.child("Receta").orderByChild("nombre_receta").startAt(texto).endAt(texto+"\uf8ff").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                //Toast.makeText(getApplicationContext(), previousChildName, Toast.LENGTH_SHORT).show();

                for(DataSnapshot obtSnapshot : snapshot.getChildren()){

                    if(obtSnapshot.getKey().equals("id")){
                        //Toast.makeText(getApplicationContext(), obtSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                        String id = obtSnapshot.getValue().toString();

                        idRecetas.add(id);
                    }
                }
                //Toast.makeText(getApplicationContext(), "idRecetas: "+idRecetas.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });*/
    }

    public void cargarRecetas(){
        databaseReference.child("Receta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                todasLasResetas.clear();
                for(DataSnapshot obtSnapshot : snapshot.getChildren()){

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

                    todasLasResetas.add(receta);

                   // Toast.makeText(getApplicationContext(), obtSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}//Fin de la clase

