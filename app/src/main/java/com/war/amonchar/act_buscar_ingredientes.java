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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class act_buscar_ingredientes extends AppCompatActivity {

    ImageView btnBack;
    Button btnBuscar;
    EditText txtIngrediente1, txtIngrediente2, txtIngrediente3;

    //DatabaseReference db;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> idRecetas, idCampo1, idCampo2, idCampo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_buscar_ingredientes);

        btnBack = findViewById(R.id.btnBack);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtIngrediente1 = findViewById(R.id.txtIngrediente1);
        txtIngrediente2 = findViewById(R.id.txtIngrediente2);
        txtIngrediente3 = findViewById(R.id.txtIngrediente3);

        txtIngrediente2.setEnabled(false);
        txtIngrediente3.setEnabled(false);

        getSupportActionBar().hide();

        inicializarFirebase();

        idRecetas = new ArrayList<>();
        idCampo1 = new ArrayList<>();
        idCampo2 = new ArrayList<>();
        idCampo3 = new ArrayList<>();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamadoDeArrays();
                Intent intent = new Intent(getApplicationContext(), act_lista_recetas.class);
                intent.putExtra("idRecetas", idRecetas);
                if(!txtIngrediente1.getText().toString().isEmpty() && !txtIngrediente2.getText().toString().isEmpty()  && !txtIngrediente3.getText().toString().isEmpty()){
                    intent.putExtra("buscado", txtIngrediente1.getText().toString()+", "+txtIngrediente2.getText().toString()+", "+txtIngrediente3.getText().toString());
                    startActivity(intent);
                }else if(!txtIngrediente1.getText().toString().isEmpty() && !txtIngrediente2.getText().toString().isEmpty()){
                    intent.putExtra("buscado", txtIngrediente1.getText().toString()+", "+txtIngrediente2.getText().toString());
                    startActivity(intent);
                }else if(!txtIngrediente1.getText().toString().isEmpty()){
                    intent.putExtra("buscado", txtIngrediente1.getText().toString());
                    startActivity(intent);
                }

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
                idCampo1.clear();

                if(!txtIngrediente1.getText().toString().isEmpty()){
                    txtIngrediente2.setEnabled(true);
                    if(!txtIngrediente1.getText().toString().matches("[a-zA-ZÀ-ÿñÑ][a-zA-ZÀ-ÿñÑ]*")){

                        Toast.makeText(getApplicationContext(), "No incluya caracteres especiales", Toast.LENGTH_SHORT).show();

                    }else{

                       rellenarIdRecetas(texto.toLowerCase(Locale.ROOT), idCampo1);
                    }
                }else{
                    txtIngrediente2.setEnabled(false);
                }

                //Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            }
        });

        txtIngrediente2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String texto = txtIngrediente2.getText().toString();
                idCampo2.clear();

                if(!txtIngrediente2.getText().toString().isEmpty()){
                    txtIngrediente3.setEnabled(true);
                    if(!txtIngrediente2.getText().toString().matches("[a-zA-ZÀ-ÿñÑ][a-zA-ZÀ-ÿñÑ]*")){

                        Toast.makeText(getApplicationContext(), "No incluya caracteres especiales", Toast.LENGTH_SHORT).show();

                    }else{

                        rellenarIdRecetas(texto.toLowerCase(Locale.ROOT), idCampo2);
                    }
                }else {
                    txtIngrediente3.setEnabled(false);
                }
            }
        });

        txtIngrediente3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String texto = txtIngrediente3.getText().toString();
                idCampo3.clear();

                if(!txtIngrediente3.getText().toString().isEmpty()){
                    if(!txtIngrediente3.getText().toString().matches("[a-zA-ZÀ-ÿñÑ][a-zA-ZÀ-ÿñÑ]*")){

                        Toast.makeText(getApplicationContext(), "No incluya caracteres especiales", Toast.LENGTH_SHORT).show();

                    }else{

                        rellenarIdRecetas(texto.toLowerCase(Locale.ROOT), idCampo3);
                    }
                }
            }
        });

    }//Fin del onCreate

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        //db = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void rellenarIdRecetas(String texto, ArrayList<String> arrayCampo) {
        databaseReference.child("Receta").orderByChild("ingredientes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                GenericTypeIndicator<ArrayList<String>> indicador = new GenericTypeIndicator<ArrayList<String>>(){};
                String id = "";
                boolean guardado = false;

                for (DataSnapshot obtSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(), obtSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                    if(obtSnapshot.getKey().equals("id")){
                        id = obtSnapshot.getValue().toString();
                    }

                    if (obtSnapshot.getKey().equals("ingredientes")) {
                        //Toast.makeText(getApplicationContext(), obtSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                        ArrayList<String> nombreIngredientes = obtSnapshot.getValue(indicador);

                        for(int i = 0; i < nombreIngredientes.size(); i++){

                            if(nombreIngredientes.get(i).toLowerCase(Locale.ROOT).matches(".*"+texto+".*")){
                                for(int e = 0; e < arrayCampo.size(); e++){
                                    if(arrayCampo.get(e).equals(id)){
                                        guardado = true;
                                    }
                                }
                                if(!guardado){
                                    arrayCampo.add(id);
                                }
                                //Toast.makeText(getApplicationContext(), nombreIngredientes.get(i), Toast.LENGTH_SHORT).show();
                            }

                        }

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
        });
    }

    public void llamadoDeArrays(){

        boolean guardado = false;
        idRecetas.clear();

        for(int e = 0; e < idCampo1.size(); e++){
            for(int i = 0; i < idRecetas.size(); i++){
                if(idCampo1.get(e).equals(idRecetas.get(i))){
                    guardado = true;
                    break;
                }
            }
            if(!guardado){
                idRecetas.add(idCampo1.get(e));
            }
        }

        for(int e = 0; e < idCampo2.size(); e++){
            for(int i = 0; i < idRecetas.size(); i++){
                if(idCampo2.get(e).equals(idRecetas.get(i))){
                    guardado = true;
                    break;
                }
            }
            if(!guardado){
                idRecetas.add(idCampo2.get(e));
            }
        }

        for(int e = 0; e < idCampo3.size(); e++){
            for(int i = 0; i < idRecetas.size(); i++){
                if(idCampo3.get(e).equals(idRecetas.get(i))){
                    guardado = true;
                    break;
                }
            }
            if(!guardado){
                idRecetas.add(idCampo3.get(e));
            }
        }
    }
}