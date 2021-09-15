package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class act_perfil_usuario_general extends AppCompatActivity {

    CardView btnAgregarReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_perfil_usuario_general);

        btnAgregarReceta = findViewById(R.id.btnAgregarReceta);

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(act_perfil_usuario_general.this, act_agregar_receta.class);
                startActivity(intento);
            }
        });
    }
}