package com.github.lipenathan.eccontatos.servicos.restadapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance{

    private static Retrofit retrofit = null;
    private static final String SERVICO_URL = "http://dcs106bapsh201:8080/estudo-caso/api/v1/contatos/";

    public static RestContatos getRestContatos() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVICO_URL)
                    .build();
        }
        return retrofit.create(RestContatos.class);
    }
}