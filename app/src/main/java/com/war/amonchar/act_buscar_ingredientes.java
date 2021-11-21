package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class act_buscar_ingredientes extends AppCompatActivity {

    ImageView btnBack;
    Button btnBuscar;
    EditText txtIngrediente1, txtIngrediente2, txtIngrediente3;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> idRecetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_buscar_ingredientes);

        btnBack = findViewById(R.id.btnBack);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtIngrediente1 = findViewById(R.id.txtIngrediente1);
        txtIngrediente2 = findViewById(R.id.txtIngrediente2);
        txtIngrediente3 = findViewById(R.id.txtIngrediente3);

        getSupportActionBar().hide();

        inicializarFirebase();

        idRecetas = new ArrayList<>();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                intent.putExtra("buscado", txtIngrediente1.getText().toString());
                startActivity(intent);
            }
        });

        txtIngrediente1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String texto = txtIngrediente1.getText().toString();
                idRecetas.clear();

                rellenarIdRecetas(texto);
                Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            }
        });

    }//Fin del onCreate

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void rellenarIdRecetas(String texto) {
        databaseReference.child("Receta").orderByChild("ingredientes").startAt(texto).endAt(texto + "\uf8ff").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               //Toast.makeText(getApplicationContext(), previousChildName, Toast.LENGTH_SHORT).show();

                for (DataSnapshot obtSnapshot : snapshot.getChildren()) {

                    if (obtSnapshot.getKey().equals("id")) {
                        //Toast.makeText(getApplicationContext(), obtSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                        String id = obtSnapshot.getValue().toString();

                        idRecetas.add(id);
                    }
                }
               Toast.makeText(getApplicationContext(), "idRecetas: "+idRecetas.toString(), Toast.LENGTH_SHORT).show();
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
        });
    }

}