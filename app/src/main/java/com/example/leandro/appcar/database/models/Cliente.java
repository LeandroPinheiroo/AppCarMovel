package com.example.leandro.appcar.database.models;
import java.io.Serializable;
import java.util.List;


public class Cliente extends Pessoa implements Serializable {

    private List<Carro> carros;
    private List<OrdemServico> ordens;

    public Cliente() {
        super();
    }

    public Cliente(String nome, String sexo, String email, String telefoneM, String telefoneF, String cpf, String rg, int tipo, Endereco endereco) {
        super(nome, cpf, sexo, email, telefoneM, telefoneF, endereco, rg);
    }
    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public List<OrdemServico> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }

}
