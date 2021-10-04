package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class act_inicio extends AppCompatActivity {

    ImageView icUsuario, icListaCompra, icBuscar;
    LinearLayout receta1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_inicio);

        icUsuario = findViewById(R.id.icUsuario);
        icListaCompra = findViewById(R.id.icListaCompra);
        icBuscar = findViewById(R.id.icBuscar);
        receta1 = findViewById(R.id.receta1);

        icUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_inicio.this, act_perfil_usuario_general.class);
                startActivity(intent);
            }
        });

        icListaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_inicio.this, act_lista_compra.class);
                startActivity(intent);
            }
        });

        icBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_buscar_general.class);
                startActivity(intent);
            }
        });

        receta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                startActivity(intent);
            }
        });

    }//Fin onCreate
}