package com.github.lipenathan.eccontatos.servicos.restadapter.retrofit;

import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.LoginRest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String URL = "http://10.249.6.26:8080/estudo-caso-jwt/api/v1/";
    public static final LoginRest LOGIN_REST = new LoginRest("uniprime", "abc123");

    public static InterfaceRestContato getRestContatos() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(InterfaceRestContato.class);
    }

    private static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
    }
}