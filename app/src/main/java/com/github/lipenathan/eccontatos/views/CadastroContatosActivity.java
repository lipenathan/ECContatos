package com.github.lipenathan.eccontatos.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.lipenathan.eccontatos.R;

public class CadastroContatosActivity extends AppCompatActivity {

    private TextView txNome;
    private TextView txEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contatos);
        vincularCampos();
    }

    private void vincularCampos() {
        txNome = findViewById(R.id.tx_nome);
        txEmail =findViewById(R.id.tx_email);
    }
}