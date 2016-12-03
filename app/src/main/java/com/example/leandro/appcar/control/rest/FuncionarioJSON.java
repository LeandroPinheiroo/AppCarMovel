package com.example.leandro.appcar.control.rest;

import com.example.leandro.appcar.model.Funcionario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FuncionarioJSON {

    public static Funcionario getFuncionarioJSON(JSONObject object) {
        //instancia vetor de funcionarios
        Funcionario funcionario = new Funcionario();
        try {
            //pega do json os registros da tag funcionario
            funcionario.setCodigo(object.getInt("codigo"));
            funcionario.setLogin(object.getInt("login"));
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
            registro.put("codigo", funcionario.getCodigo());
            registro.put("login", funcionario.getLogin());
            return registro;
        } catch (JSONException k) {
        }
        return null;
    }

}
