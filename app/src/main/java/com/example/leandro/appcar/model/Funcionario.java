package com.example.leandro.appcar.model;

import java.io.Serializable;
import java.util.List;


public class Funcionario extends Pessoa implements Serializable {

    private int codigo;
    private int login;

    public Funcionario() {
       super();
    }

    public Funcionario(String nome, String cpf, String sexo, String email, String telefoneM, String telefoneF, int endereco, String rg, int codigo, int login) {
        super(nome, cpf, sexo, email, telefoneM, telefoneF, endereco, rg);
        this.codigo = codigo;
        this.login = login;
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }
}
