package com.example.leandro.appcar.model;

public class Servico_OS {

    private int cod;
    private int servico;
    private int ordemservico;
    private int funcionario;

    public Servico_OS() {
    }

    public Servico_OS(int cod, int servico, int ordemservico, int funcionario) {
        this.cod = cod;
        this.servico = servico;
        this.ordemservico = ordemservico;
        this.funcionario = funcionario;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getServico() {
        return servico;
    }

    public void setServico(int servico) {
        this.servico = servico;
    }

    public int getOrdemservico() {
        return ordemservico;
    }

    public void setOrdemservico(int ordemservico) {
        this.ordemservico = ordemservico;
    }

    public int getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }
}
