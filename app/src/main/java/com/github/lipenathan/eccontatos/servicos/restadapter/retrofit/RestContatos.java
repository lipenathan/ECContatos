package com.github.lipenathan.eccontatos.servicos.restadapter.retrofit;

import com.github.lipenathan.eccontatos.modelo.Contato;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestContatos {
    @GET("lista")
    Call<List<Contato>> buscarTodos();

    @PUT("novo")
    Call<Response> cadastrar(@Body Contato contato);

    @PUT("deletar/{id}")
    Call<Response> deletar(@Path("id") int id);
}