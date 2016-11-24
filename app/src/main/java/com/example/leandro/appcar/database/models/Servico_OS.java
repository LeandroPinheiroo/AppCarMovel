package com.example.leandro.appcar.database.models;

public class Servico_OS {

    private int cod;
    private Servico servico;
    private OrdemServico ordemservico;
    private Funcionario funcionario;

    public Servico_OS(Servico servico, OrdemServico osCod, Funcionario mecCod) {
        this.servico = servico;
        this.ordemservico = osCod;
        this.funcionario = mecCod;
    }

    public Servico_OS() {

    }


    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }


    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public OrdemServico getOrdemservico() {
        return ordemservico;
    }

    public void setOrdemservico(OrdemServico ordemservico) {
        this.ordemservico = ordemservico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
