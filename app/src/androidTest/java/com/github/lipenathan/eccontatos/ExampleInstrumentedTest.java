package com.github.lipenathan.eccontatos;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.RestContatos;
import com.github.lipenathan.eccontatos.servicos.restadapter.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private RestContatos rest;

    @Before
    public void init() {
        rest = RetrofitInstance.getRestContatos();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.github.lipenathan.eccontatos", appContext.getPackageName());
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