package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.war.amonchar.Client.RetrofitClient;
import com.war.amonchar.Modelo.AdapterBeneficioIngrediente;
import com.war.amonchar.Modelo.BeneficioIngrediente;
import com.war.amonchar.Modelo.BeneficiosRespuesta;
import com.war.amonchar.Service.RetrofitAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_beneficios_ingredientes extends AppCompatActivity {

    ImageView btnBack;
    ListView listaBeneficios;
    AdapterBeneficioIngrediente adapterBeneficioIngrediente;

    private RetrofitAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_beneficios_ingredientes);
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.btnBack);
        listaBeneficios = findViewById(R.id.listaBeneficios);

        iniciarValores();
        getBeneficiosIngredientes();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }// Fin OnCreate

    private void iniciarValores() {
        apiService = RetrofitClient.getApiService();
    }

    private void getBeneficiosIngredientes(){
        apiService.getListaBeneficioIngrediente().enqueue(new Callback<BeneficiosRespuesta>() {
            @Override
            public void onResponse(Call<BeneficiosRespuesta> call, Response<BeneficiosRespuesta> response) {
                BeneficiosRespuesta ingredientes = response.body();

                adapterBeneficioIngrediente = new AdapterBeneficioIngrediente(getApplicationContext(), ingredientes.getIngredientes());
                listaBeneficios.setAdapter(adapterBeneficioIngrediente);
            }

            @Override
            public void onFailure(Call<BeneficiosRespuesta> call, Throwable throwable) {

            }
        });
    }

}//Fin clase act_beneficios_ingredientes