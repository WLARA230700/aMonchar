package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class act_inicio extends AppCompatActivity {

    ImageView icUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_inicio);

        icUsuario = findViewById(R.id.icUsuario);

        icUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_inicio.this, act_perfil_usuario_general.class);
                startActivity(intent);
            }
        });

    }//Fin onCreate
}