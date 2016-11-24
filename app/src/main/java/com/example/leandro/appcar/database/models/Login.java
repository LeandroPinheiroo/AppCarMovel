package com.example.leandro.appcar.database.models;

public class Login {

    private int cod;
    private String usuario;
    private String senha;

    public Login(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public Login() {

    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
