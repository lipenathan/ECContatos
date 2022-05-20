package com.github.lipenathan.eccontatos.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.github.lipenathan.eccontatos.R;
import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RestContatos;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RetrofitInstance;
import com.github.lipenathan.eccontatos.views.recyclerViews.ContatosRVAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaContatosActivity extends AppCompatActivity {

    private RestContatos restContatos;
    private List<Contato> contatos = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        restContatos = RetrofitInstance.getRestContatos();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);
        recyclerView = findViewById(R.id.rv_contatos);
        buscarContatos();
    }

    private void setarRecyclerView() {
        ContatosRVAdapter adapter = new ContatosRVAdapter(this, contatos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void buscarContatos() {
        Call<List<Contato>> chamadaConsultaContatos = restContatos.buscarTodos();
        chamadaConsultaContatos.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                if (response.isSuccessful()) {
                    contatos = response.body();
                    setarRecyclerView();
                } else {
                    Toast.makeText(ListaContatosActivity.this, response.message(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {

            }
        });
    }
}