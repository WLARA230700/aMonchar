package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class act_buscar_ingredientes extends AppCompatActivity {

    ImageView btnBack;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_buscar_ingredientes);

        btnBack = findViewById(R.id.btnBack);
        btnBuscar = findViewById(R.id.btnBuscar);
        getSupportActionBar().hide();

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
                startActivity(intent);
            }
        });

    }
}