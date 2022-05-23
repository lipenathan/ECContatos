package com.github.lipenathan.eccontatos.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.lipenathan.eccontatos.R;
import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.InterfaceRestContato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RetrofitInstance;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.LoginRest;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.Token;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroContatosActivity extends AppCompatActivity {

    private EditText txNome;
    private EditText txEmail;
    private InterfaceRestContato restContatos;
    private Button btCadastrar;
    private LoginRest loginRest = RetrofitInstance.LOGIN_REST;
    private Token token = new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contatos);
        restContatos = RetrofitInstance.getRestContatos();
        vincularViews();
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato novoContato = new Contato();
                novoContato.setNome(txNome.getText().toString());
                novoContato.setEmail(txEmail.getText().toString());
                cadastrarContato(novoContato);
            }
        });
    }

    private void cadastrarContato(final Contato novoContato) {
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
                        Toast.makeText(CadastroContatosActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        chamadaRestCadastrarContato(novoContato, token);
                    }
                });
    }

    //métodos de apoio privados
    private void vincularViews() {
        txNome = findViewById(R.id.tx_nome);
        txEmail = findViewById(R.id.tx_email);
        btCadastrar = findViewById(R.id.bt_cadastrar);
    }

    private void chamadaRestCadastrarContato(Contato contato, Token token) {
        restContatos.cadastrar(contato, token).enqueue(new Callback<Contato>() {
            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroContatosActivity.this, "Contato salvo com sucesso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CadastroContatosActivity.this, ListaContatosActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        Toast.makeText(CadastroContatosActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                Toast.makeText(CadastroContatosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}