package com.github.lipenathan.eccontatos.views.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lipenathan.eccontatos.R;
import com.github.lipenathan.eccontatos.modelo.Contato;

import java.util.List;

public class ContatosRVAdapter extends RecyclerView.Adapter<ContatosRVAdapter.MyViewHolder> {

    private Context context;
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
        return new ContatosRVAdapter.MyViewHolder(view);
    }

    //método que faz o bind da lista de contatos para os componentes visuais.
    @Override
    public void onBindViewHolder(@NonNull ContatosRVAdapter.MyViewHolder holder, int position) {
        holder.txNome.setText(contatos.get(position).getNome());
        holder.txEmail.setText(contatos.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    //inner class que extende ViewHolder e contém elementos dos que são usados dentro do RecyclerView.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txNome, txEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txNome = itemView.findViewById(R.id.tx_cv_nome);
            txEmail = itemView.findViewById(R.id.tx_cv_email);
        }
    }
}
