package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class act_lista_recetas extends AppCompatActivity {

    ImageView btnBack;
    ArrayList<String> idRecetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_lista_recetas);

        getSupportActionBar().hide();
        btnBack = findViewById(R.id.btnBack);

        idRecetas = getIntent().getStringArrayListExtra("idRecetas");
        Toast.makeText(getApplicationContext(), idRecetas.toString(), Toast.LENGTH_SHORT).show();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}