package com.example.leandro.appcar.control.rest;

import com.example.leandro.appcar.model.Pessoa;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PessoaJSON {

    public static Pessoa getPessoaJSON(JSONObject object) {
        //instancia vetor de servico_os
        Pessoa pessoa = new Pessoa();
        try {
            //pega do json os registros da tag servico_os
            pessoa.setCodigo(object.getInt("codigo"));
            pessoa.setNome(object.getString("nome"));
            pessoa.setCpf(object.getString("cpf"));
            pessoa.setEmail(object.getString("email"));
            pessoa.setEndereco(object.getInt("endereco"));
            pessoa.setRg(object.getString("rg"));
            pessoa.setSexo(object.getString("sexo"));
            pessoa.setTelefoneF(object.getString("telefoneF"));
            pessoa.setTelefoneM(object.getString("telefoneM"));
        } catch (Exception x) {
        }
        return pessoa;
    }

    public static String geraJSONPessoas(ArrayList<Pessoa> pessoas) {
        ArrayList<JSONObject> tabelaPessoas = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Pessoa pessoa : pessoas) {
            registro = preencheJSON(pessoa);

            //adiciona registro à lista de registros
            tabelaPessoas.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("servico_os", (Object) tabelaPessoas);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONServico_OS(Pessoa pessoa) {
        return UtilJSON.limpaJSON(preencheJSON(pessoa));
    }

    public static JSONObject preencheJSON(Pessoa pessoa) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("codigo", pessoa.getCodigo());
            registro.put("nome", pessoa.getNome());
            registro.put("cpf", pessoa.getCpf());
            registro.put("rg", pessoa.getRg());
            registro.put("telefoneM", pessoa.getTelefoneM());
            registro.put("telefoneF", pessoa.getTelefoneF());
            registro.put("email", pessoa.getEmail());
            registro.put("sexo", pessoa.getSexo());
            registro.put("endereco", pessoa.getEndereco());
            return registro;
        } catch (JSONException k) {
        }
        return null;
    }

}
