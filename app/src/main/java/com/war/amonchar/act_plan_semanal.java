package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class act_plan_semanal extends AppCompatActivity {

    LinearLayout contenidoLunes;
    Button btnLunes;

    ImageView btnBack;

    Boolean lunes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_plan_semanal);

        contenidoLunes = findViewById(R.id.contenidoLunes);
        btnLunes = findViewById(R.id.btnLunes);
        btnBack = findViewById(R.id.btnBack);

        contenidoLunes.setVisibility(View.GONE);


        btnLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lunes){
                    contenidoLunes.setVisibility(View.GONE);
                    lunes = false;
                }else{
                    contenidoLunes.setVisibility(View.VISIBLE);
                    lunes = true;
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }//Fin del onCreate
}//Fin de la clase