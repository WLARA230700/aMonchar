package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class act_perfil_usuario_general extends AppCompatActivity {

    CardView btnAgregarReceta, btnPlanSemanal, btnBeneficiosIngredientes;
    ImageView btnListaCompra, btnInicio;
    LinearLayout btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_perfil_usuario_general);

        btnAgregarReceta = findViewById(R.id.btnAgregarReceta);
        btnPlanSemanal = findViewById(R.id.btnPlanSemanal);
        btnBeneficiosIngredientes = findViewById(R.id.btnBeneficiosIngredientes);
        btnListaCompra = findViewById(R.id.icListaCompra);
        btnInicio = findViewById(R.id.icInicio);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(act_perfil_usuario_general.this, act_agregar_receta.class);
                startActivity(intento);
            }
        });

        btnPlanSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_plan_semanal.class);
                startActivity(intent);
            }
        });

        btnBeneficiosIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_beneficios_ingredientes.class);
                startActivity(intent);
            }
        });

        btnListaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_lista_compra.class);
                startActivity(intent);
            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_inicio.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_editar_perfil.class);
                startActivity(intent);
            }
        });

    }//Fin onCreate
}