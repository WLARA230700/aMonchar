package com.war.amonchar.Service;

import com.war.amonchar.Modelo.BeneficioIngrediente;
import com.war.amonchar.Modelo.BeneficiosRespuesta;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPIService {

    // Retrofit tiene sus propias anotaciones referentes al método a implementar

    @GET("ingredientes")
    Call<BeneficiosRespuesta> getListaBeneficioIngrediente();

}// Fin clase RetrofitAPIService
