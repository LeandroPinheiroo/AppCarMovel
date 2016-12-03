package com.example.leandro.appcar.model;

import java.io.Serializable;
import java.util.List;


public class Funcionario extends Pessoa implements Serializable {

    private Login login;
    private List<Servico_OS> servicosRealizados;

    public Funcionario(String nome, String sexo, String email, String telefoneM, String telefoneF, String cpf, String rg, Endereco endereco, Login login) {
        super(nome, cpf, sexo, email, telefoneM, telefoneF, endereco, rg);
        this.login = login;
    }

    public Funcionario() {
        super();
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }


    public List<Servico_OS> getServicosRealizados() {
        return servicosRealizados;
    }

    public void setServicosRealizados(List<Servico_OS> servicosRealizados) {
        this.servicosRealizados = servicosRealizados;
    }


}
