package com.github.lipenathan.eccontatos.servicos.restadapter.retrofit.credenciais;

public class LoginRest {
    public String usuario;
    public String senha;

    public LoginRest() {
    }

    public LoginRest(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }
}
