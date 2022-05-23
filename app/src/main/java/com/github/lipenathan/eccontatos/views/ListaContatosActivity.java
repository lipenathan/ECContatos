package com.github.lipenathan.eccontatos.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lipenathan.eccontatos.R;
import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.InterfaceRestContato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RetrofitInstance;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.LoginRest;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.Token;
import com.github.lipenathan.eccontatos.views.recyclerViews.ContatosRVAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaContatosActivity extends AppCompatActivity {

    private InterfaceRestContato interfaceRestContato;
    private List<Contato> contatos = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton fabNovoContato;
    private InterfaceRestContato restContatos;
    private LoginRest loginRest = RetrofitInstance.LOGIN_REST;
    private Token token = new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        interfaceRestContato = RetrofitInstance.getRestContatos();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);
        vincularViews();
        buscarContatos();
    }

    private void vincularViews() {
        recyclerView = findViewById(R.id.rv_contatos);
        fabNovoContato = findViewById(R.id.fab_novoContato);
        fabNovoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaContatosActivity.this, CadastroContatosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setarRecyclerView() {
        ContatosRVAdapter adapter = new ContatosRVAdapter(this, contatos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void buscarContatos() {
        restContatos = RetrofitInstance.getRestContatos();
        Observable<Token> tokenObservable = restContatos.getToken(loginRest);
        tokenObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Token tokenRetornado) {
                        token.token = "Bearer " + tokenRetornado.token;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ListaContatosActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        chamadaRestListarContatos(token);
                    }
                });
    }

    private void chamadaRestListarContatos(Token token) {
        restContatos.buscarTodos(token).enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                if (response.isSuccessful()) {
                    contatos = response.body();
                    setarRecyclerView();
                    Toast.makeText(ListaContatosActivity.this, "Contatos buscados com sucesso", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Toast.makeText(ListaContatosActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {
                Toast.makeText(ListaContatosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}