package com.github.lipenathan.eccontatos.servicos;

import static org.junit.Assert.fail;

import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RestContatos;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RetrofitInstance;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestContatosTeste {

    private RestContatos restContatos;
    private List<Contato> contatos;

    @Before
    public void init() {
        restContatos = RetrofitInstance.getRestContatos();
    }

    @Test
    public void testeListarSinc() {
        Call<List<Contato>> chamadaRestListarContatos = restContatos.buscarTodos();
        try {
            Response<List<Contato>> response = chamadaRestListarContatos.execute();
            System.out.println(response.body());
        } catch (IOException e) {
            fail("Falha na requisição - " + e.getMessage());
        }
    }

    @Test
    public void testeListarAssinc() {
        Call<List<Contato>> chamadaRestListarContatos = restContatos.buscarTodos();
        chamadaRestListarContatos.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                contatos = response.body();
            }
            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        System.out.println(contatos);
    }

    @Test
    public void testeCadastrar() {
        Contato c = new Contato();
        c.setNome("Augusto");
        c.setEmail("augusto@email.com");
        Call<Response> chamadaRestCadastro = restContatos.cadastrar(c);
        try {
            chamadaRestCadastro.execute();
        } catch (IOException e) {
            fail("Falha no cadastro de contato - " + e.getMessage());
        }
    }

    @Test
    public void testeDeletarContato() {
        Call<Response> chamadaRestDeletar = restContatos.deletar(4);
        try {
            Response response = chamadaRestDeletar.execute();
            System.out.println(response.message() + " " + response.code());
        } catch (IOException e) {
            fail("Falha ao deltar contato - " + e.getMessage());
        }
    }
}