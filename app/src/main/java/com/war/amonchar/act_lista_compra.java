package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.AdapterIngredientes;
import com.war.amonchar.Modelo.Ingrediente;

import java.io.Console;
import java.util.ArrayList;

public class act_lista_compra extends AppCompatActivity {

    ImageView btnBack;

    ArrayList<Ingrediente> ingredientes, ingredientesPorComprar, ingredientesComprados;
    AdapterIngredientes adapterIngredientesPorComprar, adapterIngredientesComprados;

    ListView listPorComprar, listComprado;

    CardView btnAgregarIngrediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_lista_compra);

        btnBack = findViewById(R.id.btnBack);
        listPorComprar = findViewById(R.id.listPorComprar);
        listComprado = findViewById(R.id.listComprado);
        btnAgregarIngrediente = findViewById(R.id.btnAgregarIngrediente);

        ingredientesPorComprar = new ArrayList<>();
        ingredientesComprados = new ArrayList<>();

        final BD db = new BD(getApplicationContext());

        ingredientes = db.getIngredientes();

        llenarAdapters();

        Toast.makeText(getApplicationContext(), "Cantidad de ingredientes: " + db.getIngredientes().size(), Toast.LENGTH_SHORT).show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingrediente ingrediente = new Ingrediente();
                ingredientesPorComprar.add(ingrediente);
                //db.agregarIngrediente(ingrediente);
                adapterIngredientesPorComprar = new AdapterIngredientes(getApplicationContext(), ingredientesPorComprar);
                listPorComprar.setAdapter(adapterIngredientesPorComprar);
                Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_SHORT).show();
            }
        });

    }// Fin del onCreate

    public void llenarAdapters(){

        for (int i = 0; i < ingredientes.size(); i++){
            if(!ingredientes.get(i).isComprado()){
                ingredientesPorComprar.add(ingredientes.get(i));
            }else{
                ingredientesComprados.add(ingredientes.get(i));
            }
        }

        adapterIngredientesPorComprar = new AdapterIngredientes(getApplicationContext(), ingredientesPorComprar);
        listPorComprar.setAdapter(adapterIngredientesPorComprar);
        adapterIngredientesComprados = new AdapterIngredientes(getApplicationContext(), ingredientesComprados);
        listComprado.setAdapter(adapterIngredientesComprados);

    }// Fin de llenarAdapters

}// Fin de la clase