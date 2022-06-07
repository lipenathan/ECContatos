package com.github.lipenathan.eccontatos.servicos;

import static com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RetrofitInstance.LOGIN_REST;
import static org.junit.Assert.fail;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.InterfaceRestContato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RetrofitInstance;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.LoginRest;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.Token;
import com.github.lipenathan.eccontatos.views.CadastroContatosActivity;
import com.github.lipenathan.eccontatos.views.ListaContatosActivity;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestContatoTeste {
    private List<Contato> contatos;
    private Token token = new Token();

    @Test
    public void testeListarSinc() {
        Observable<Token> tokenObservable = RetrofitInstance.getRestContatos().getToken(LOGIN_REST);
        tokenObservable
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
                        fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        try {
                            Response<List<Contato>> response = RetrofitInstance.getRestContatos().buscarTodos(token).execute();
                            if (response.isSuccessful()) {
                                System.out.println(response.body());
                            } else {
                                fail(response.errorBody().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Test
    public void testeCadastrar() {
        Contato contato = new Contato("jorge", "jorge@hotmail.com");
        Observable<Token> tokenObservable = RetrofitInstance.getRestContatos().getToken(LOGIN_REST);
        tokenObservable
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
                        fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        try {
                            Response<Contato> response = RetrofitInstance.getRestContatos().cadastrar(contato, token).execute();
                            if (response.isSuccessful()) {
                                System.out.println("Foi cadastrado com sucesso");
                            } else {
                                fail(response.errorBody().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Test
    public void testeDeletarContato() {
        InterfaceRestContato restContatos = RetrofitInstance.getRestContatos();
        int index = 2;
        Observable<Token> tokenObservable = restContatos.getToken(LOGIN_REST);
        tokenObservable
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
                        fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        try {
                            Response<Contato> response = restContatos.deletar(index, token).execute();
                            if (response.isSuccessful()) {
                                System.out.println("Foi deletado com sucesso");
                            } else {
                                fail(response.errorBody().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}