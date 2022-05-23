package com.github.lipenathan.eccontatos.servicos.restadapter.retrofit;


import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.LoginRest;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.Token;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceRestContato {
    @GET("contatos/lista")
    Call<List<Contato>> buscarTodos(@Header("Authorization") Token token);

    @PUT("contatos/novo")
    Call<Contato> cadastrar(@Body Contato contato, @Header("Authorization") Token token);

    @PUT("contatos/deletar/{id}")
    Call<Contato> deletar(@Path("id") int id, @Header("Authorization") Token token);

    @POST("autenticacao")
    Observable<Token> getToken(@Body LoginRest login);
}