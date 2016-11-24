package com.example.leandro.appcar.database.models;

import java.io.Serializable;



public class Pessoa implements Serializable {

    private int codigo;
    private String nome;
    private String cpf;
    private String sexo;
    private String email;
    private String telefoneM;
    private String telefoneF;
    private Endereco endereco;
    private String rg;

    public Pessoa() {

    }

    public Pessoa(String nome, String cpf, String sexo, String email, String telefoneM, String telefoneF, Endereco endereco, String rg) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.email = email;
        this.telefoneM = telefoneM;
        this.telefoneF = telefoneF;
        this.endereco = endereco;
        this.rg = rg;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoneM() {
        return telefoneM;
    }

    public void setTelefoneM(String telefoneM) {
        this.telefoneM = telefoneM;
    }

    public String getTelefoneF() {
        return telefoneF;
    }

    public void setTelefoneF(String telefoneF) {
        this.telefoneF = telefoneF;
    }


    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
