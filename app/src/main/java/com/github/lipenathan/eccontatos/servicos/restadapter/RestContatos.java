package com.github.lipenathan.eccontatos.servicos.restadapter;

import com.github.lipenathan.eccontatos.modelo.Contato;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestContatos {

    @GET("lista")
    Call<List<Contato>> listarContatos();
}