package com.github.lipenathan.eccontatos.servicos;

import static org.junit.Assert.fail;

import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.RestContatos;
import com.github.lipenathan.eccontatos.servicos.restadapter.RetrofitInstance;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestContatosTeste {

    private RestContatos rest;

    @Before
    public void init() {
        rest = RetrofitInstance.getRestContatos();
    }

    @Test
    public void testeListar() {
        Call<List<Contato>> listarContatos = rest.listarContatos();
        listarContatos.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                if (response.isSuccessful()) {
                    List<Contato> resp = response.body();
                    System.out.println(resp);
                } else {
                    fail("Não foi - " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable throwable) {
                fail("Deveria listar com sucesso");
            }
        });
    }
}