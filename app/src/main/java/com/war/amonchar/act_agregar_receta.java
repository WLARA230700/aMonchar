package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.war.amonchar.Modelo.AdapterDropdownItem;

import java.util.ArrayList;
import java.util.Arrays;

public class act_agregar_receta extends AppCompatActivity {

    ImageView btnBack;

    Spinner preparacionSpinner, tiemposComidaSpinner;
    String[] tiempoPreparacion = {"Minutos", "Horas"};
    String[] tiempoComida = {"Desayuno", "Merienda de mañana", "Almuerzo", "Merienda de tarde", "Cena", "Merienda de noche"};

    AdapterDropdownItem adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_agregar_receta);

        btnBack = findViewById(R.id.btnBack);
        preparacionSpinner = findViewById(R.id.preparacionSpinner);
        tiemposComidaSpinner = findViewById(R.id.tiemposComidaSpinner);

        asignarSpinner();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//Fin onCreate

    public void asignarSpinner(){

        ArrayList<String> listTiemposPreparacion = new ArrayList<>(Arrays.asList(tiempoPreparacion));
        ArrayList<String> listTiemposComida = new ArrayList<>(Arrays.asList(tiempoComida));

        /*ArrayAdapter<String> adapterTiempoPreparacion = new ArrayAdapter<>(act_agregar_receta.this, android.R.layout.simple_list_item_1, listTiemposPreparacion);
        ArrayAdapter<String> adapterTiemposComida = new ArrayAdapter<>(act_agregar_receta.this, android.R.layout.simple_list_item_1, listTiemposComida);*/

        adapter  = new AdapterDropdownItem(getApplicationContext(), listTiemposPreparacion);
        preparacionSpinner.setAdapter(adapter);

        adapter  = new AdapterDropdownItem(getApplicationContext(), listTiemposComida);
        tiemposComidaSpinner.setAdapter(adapter);

    }//Fin asignarSpinner
}