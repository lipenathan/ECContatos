package com.github.lipenathan.eccontatos.views.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lipenathan.eccontatos.R;
import com.github.lipenathan.eccontatos.modelo.Contato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.InterfaceRestContato;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.RetrofitInstance;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.LoginRest;
import com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais.Token;
import com.github.lipenathan.eccontatos.views.CadastroContatosActivity;

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

public class ContatosRVAdapter extends RecyclerView.Adapter<ContatosRVAdapter.MyViewHolder> {

    private static Context context;
    private List<Contato> contatos;

    //construtor que recebe contexto e lista de contatos para serem feitos o bind.
    public ContatosRVAdapter(Context context, List<Contato> contatos) {
        this.context = context;
        this.contatos = contatos;
    }

    //método chamada para criar o adapter com o view holder que contém os itemView do context recebido.
    @NonNull
    @Override
    public ContatosRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_contatos_item, parent, false);
        return new ContatosRVAdapter.MyViewHolder(view, this);
    }

    //método que faz o bind da lista de contatos para os componentes visuais.
    @Override
    public void onBindViewHolder(@NonNull ContatosRVAdapter.MyViewHolder holder, int position) {
        holder.txNome.setText(contatos.get(position).getNome());
        holder.txEmail.setText(contatos.get(position).getEmail());
        holder.idContato = contatos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    //inner class que extende ViewHolder e contém elementos dos que são usados dentro do RecyclerView.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txNome, txEmail;
        int idContato;
        ImageButton btDeletar;
        InterfaceRestContato restContatos;
        ContatosRVAdapter adapter;
        private final LoginRest loginRest = RetrofitInstance.LOGIN_REST;
        private final Token token = new Token();

        public MyViewHolder(@NonNull View itemView, ContatosRVAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            txNome = itemView.findViewById(R.id.tx_cv_nome);
            txEmail = itemView.findViewById(R.id.tx_cv_email);
            btDeletar = itemView.findViewById(R.id.bt_deletar);
            setarDeletar();
        }

        private void setarDeletar() {
            restContatos = RetrofitInstance.getRestContatos();
            btDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAbsoluteAdapterPosition();
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
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onComplete() {
                                    chamadaRestDeletarContato(index, token);
                                }
                            });
                }
            });
        }

        private void chamadaRestDeletarContato(int index, Token token) {
            Call<Contato> chamadaDeletarContato = restContatos.deletar(idContato, token);
            chamadaDeletarContato.enqueue(new Callback<Contato>() {
                @Override
                public void onResponse(Call<Contato> call, Response<Contato> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Contato deletado com sucesso", Toast.LENGTH_LONG).show();
                        adapter.contatos.remove(index);
                        adapter.notifyItemRemoved(index);
                    } else {
                        try {
                            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Contato> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
