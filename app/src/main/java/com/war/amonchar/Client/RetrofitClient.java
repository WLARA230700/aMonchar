package com.war.amonchar.Client;

import com.war.amonchar.Service.RetrofitAPIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String URL_BASE = "https://www.mockachino.com/eb112b98-1a66-4f/";
    public static Retrofit retrofit;

    public static RetrofitAPIService getApiService(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(RetrofitAPIService.class);

    }// Fin método getApiService

}// Fin clase RetrofitClient
