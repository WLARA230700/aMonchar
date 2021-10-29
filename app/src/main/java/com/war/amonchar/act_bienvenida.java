package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class act_bienvenida extends AppCompatActivity {

    //Variables
    Button btnUsarCorreo;
    TextView txtIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_bienvenida);
        getSupportActionBar().hide();

        this.btnUsarCorreo = findViewById(R.id.btnUsarCorreo);
        this.txtIniciarSesion = findViewById(R.id.txtIniciarSesion);

//--------------------------------------------------------------------------------------------------
        btnUsarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_registro_usuario.class);
                startActivity(intent);
            }
        });
//--------------------------------------------------------------------------------------------------
        txtIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_inicio_sesion.class);
                startActivity(intent);
            }
        });

    }//Fin del onCreate
}//Fin de la clase