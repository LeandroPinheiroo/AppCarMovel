package com.example.leandro.appcar.database.models;

import java.io.Serializable;
import java.sql.Timestamp;


public class OrdemServico implements Serializable {

    private int cod;
    private String tipo;
    private Timestamp data;
    private int situacao;
    private Cliente cliente;
    private Carro carro;
    private String descricao;

    public OrdemServico(String tipo, int situacao, Carro carro, Cliente cliente, String descricao) {
        this.tipo = tipo;
        this.situacao = situacao;
        this.cliente = cliente;
        this.carro = carro;
        this.descricao = descricao;
    }

    public OrdemServico() {

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

}
