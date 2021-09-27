package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class act_buscar_general extends AppCompatActivity {

    ImageView icInicio, icListaCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_buscar_general);

        icInicio = findViewById(R.id.icInicio);
        icListaCompra = findViewById(R.id.icListaCompra);

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

    }
}