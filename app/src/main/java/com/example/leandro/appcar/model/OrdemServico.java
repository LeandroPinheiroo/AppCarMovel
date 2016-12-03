package com.example.leandro.appcar.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class OrdemServico implements Serializable {

    private int cod;
    private String tipo;
    private Timestamp data;
    private int situacao;
    private int cliente;
    private int carro;
    private String descricao;

    public OrdemServico() {
    }

    public OrdemServico(int cod, String tipo, Timestamp data, int situacao, int cliente, int carro, String descricao) {
        this.cod = cod;
        this.tipo = tipo;
        this.data = data;
        this.situacao = situacao;
        this.cliente = cliente;
        this.carro = carro;
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

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getCarro() {
        return carro;
    }

    public void setCarro(int carro) {
        this.carro = carro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
