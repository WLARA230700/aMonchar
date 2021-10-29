package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Usuario;

public class act_bienvenida extends AppCompatActivity {

    //Variables
    Button btnUsarCorreo;
    TextView txtIniciarSesion;
    Usuario usuarioLog;

    private BD db;

    @Override
    protected void onRestart() {
        super.onRestart();
        restart();
    }

    protected void restart() {
        super.onRestart();
        db = new BD(getApplicationContext());
        usuarioLog = db.getUsuarioLogueado(1);

        if (usuarioLog != null){
            ((GlobalVariables) getApplication()).setUsuarioLogueado(usuarioLog);
            Intent intent = new Intent(getApplicationContext(), act_inicio.class);
            startActivity(intent);
        }else{
            setContentView(R.layout.lyt_bienvenida);
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

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        restart();


    }//Fin del onCreate
}//Fin de la clase