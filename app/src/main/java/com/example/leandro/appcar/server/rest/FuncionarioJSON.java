package com.example.leandro.appcar.server.rest;

import com.example.leandro.appcar.database.models.Funcionario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FuncionarioJSON {

    public static Funcionario getFuncionarioJSON(JSONObject json) {
        //instancia vetor de funcionarios
        Funcionario funcionario = new Funcionario();
        try {
            //pega do json os registros da tag funcionario
            JSONArray vetor = (JSONArray) json.get("funcionario");
            JSONObject object = (JSONObject) vetor.get(0);
            funcionario.setCodigo(object.getInt("cod"));
            funcionario.setNome(object.getString("nome"));
            funcionario.setCpf(object.getString("cpf"));
            funcionario.setEmail(object.getString("email"));
            funcionario.setEndereco(EnderecoJSON.getEnderecoJSON(object.getJSONObject("endereco")));
            funcionario.setRg(object.getString("rg"));
            funcionario.setSexo(object.getString("sexo"));
            funcionario.setTelefoneF(object.getString("telefoneF"));
            funcionario.setTelefoneM(object.getString("telefoneM"));
            funcionario.setLogin(LoginJSON.getLoginJSON(object.getJSONObject("login")));
        } catch (Exception x) {
        }
        return funcionario;
    }

    public static String geraJSONFuncionarios(ArrayList<Funcionario> funcionarios) {
        ArrayList<JSONObject> tabelaFuncionarios = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Funcionario funcionario : funcionarios) {
            registro = preencheJSON(funcionario);

            //adiciona registro à lista de registros
            tabelaFuncionarios.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("funcionario", (Object) tabelaFuncionarios);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONFuncionario(Funcionario funcionario) {
        return UtilJSON.limpaJSON(preencheJSON(funcionario));
    }

    public static JSONObject preencheJSON(Funcionario funcionario) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("cod", funcionario.getCodigo());
            registro.put("nome", funcionario.getNome());
            registro.put("cpf", funcionario.getCpf());
            registro.put("rg", funcionario.getRg());
            registro.put("telefoneM", funcionario.getTelefoneM());
            registro.put("telefoneF", funcionario.getTelefoneF());
            registro.put("email", funcionario.getEmail());
            registro.put("sexo", funcionario.getSexo());
            registro.put("endereco", EnderecoJSON.geraJSONEndereco(funcionario.getEndereco()));
            registro.put("login", LoginJSON.geraJSONLogin(funcionario.getLogin()));
        } catch (JSONException k) {
        }
        return null;
    }

}
