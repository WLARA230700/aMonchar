package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class act_plan_semanal extends AppCompatActivity {

    LinearLayout contenidoLunes, contenidoMartes, contenidoMiercoles, contenidoJueves, contenidoViernes, contenidoSabado, contenidoDomingo;
    Button btnLunes, btnMartes, btnMiercoles, btnJueves, btnViernes, btnSabado, btnDomingo;

    ImageView btnBack;

    Boolean lunes = false, martes = false, miercoles = false, jueves = false, viernes = false, sabado = false, domingo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_plan_semanal);

        contenidoLunes = findViewById(R.id.planSemanal);
        contenidoMartes = findViewById(R.id.contenidoMartes);
        contenidoMiercoles = findViewById(R.id.contenidoMiercoles);
        contenidoJueves = findViewById(R.id.contenidoJueves);
        contenidoViernes = findViewById(R.id.contenidoViernes);
        contenidoSabado = findViewById(R.id.contenidoSabado);
        contenidoDomingo = findViewById(R.id.contenidoDomingo);

        btnLunes = findViewById(R.id.btnLunes);
        btnMartes = findViewById(R.id.btnMartes);
        btnMiercoles = findViewById(R.id.btnMiercoles);
        btnJueves = findViewById(R.id.btnJueves);
        btnViernes = findViewById(R.id.btnViernes);
        btnSabado = findViewById(R.id.btnSabado);
        btnDomingo = findViewById(R.id.btnDomingo);

        btnBack = findViewById(R.id.btnBack);

        contenidoLunes.setVisibility(View.GONE);
        contenidoMartes.setVisibility(View.GONE);
        contenidoMiercoles.setVisibility(View.GONE);
        contenidoJueves.setVisibility(View.GONE);
        contenidoViernes.setVisibility(View.GONE);
        contenidoSabado.setVisibility(View.GONE);
        contenidoDomingo.setVisibility(View.GONE);


        btnLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunes = cambiarEstado(contenidoLunes, btnLunes, lunes);
            }
        });

        btnMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                martes = cambiarEstado(contenidoMartes, btnMartes, martes);
            }
        });

        btnMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miercoles = cambiarEstado(contenidoMiercoles, btnMiercoles, miercoles);
            }
        });

        btnJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jueves = cambiarEstado(contenidoJueves, btnJueves, jueves);
            }
        });

        btnViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viernes = cambiarEstado(contenidoViernes, btnViernes, viernes);
            }
        });

        btnSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sabado = cambiarEstado(contenidoSabado, btnSabado, sabado);
            }
        });

        btnDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                domingo = cambiarEstado(contenidoDomingo, btnDomingo, domingo);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }//Fin del onCreate

    public boolean cambiarEstado(LinearLayout linearLayout, Button btn, Boolean bool){
        if (bool){
            linearLayout.setVisibility(View.GONE);
            btn.setBackgroundColor(Color.rgb(253, 212, 123));
            btn.setTextColor(Color.rgb(26, 26, 26));
            Drawable drawable = getDrawable(R.drawable.ic_down);
            drawable.setTint(Color.BLACK);
            btn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            bool = false;
            return bool;
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            btn.setBackgroundColor(Color.rgb(249, 179, 52));
            btn.setTextColor(Color.WHITE);
            Drawable drawable = getDrawable(R.drawable.ic_up);
            drawable.setTint(Color.WHITE);
            btn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            bool = true;
            return bool;
        }
    }//Fin de cambiarEstado

}//Fin de la clase