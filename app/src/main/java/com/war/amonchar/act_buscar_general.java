package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class act_buscar_general extends AppCompatActivity {

    ImageView icInicio, icListaCompra;
    Button btnBuscarIngredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_buscar_general);

        icInicio = findViewById(R.id.icInicio);
        icListaCompra = findViewById(R.id.icListaCompra);
        btnBuscarIngredientes = findViewById(R.id.btnBuscarIngredientes);
        getSupportActionBar().hide();

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

    }
}