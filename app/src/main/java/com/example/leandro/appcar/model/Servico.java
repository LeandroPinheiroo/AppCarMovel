package com.example.leandro.appcar.model;

import java.io.Serializable;
import java.util.List;


public class Servico implements Serializable {

    private int cod;
    private String descricao;
    private double valor;
    private List<Servico_OS> servicosRealizados;

    public Servico(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public Servico() {

    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Servico_OS> getServicosRealizados() {
        return servicosRealizados;
    }

    public void setServicosRealizados(List<Servico_OS> servicosRealizados) {
        this.servicosRealizados = servicosRealizados;
    }


}
