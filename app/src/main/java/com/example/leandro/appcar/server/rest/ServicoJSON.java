package com.example.leandro.appcar.server.rest;

import com.example.leandro.appcar.database.models.Servico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServicoJSON {

    public static Servico getServicoJSON(JSONObject json) {
        //instancia vetor de servicos
        Servico servico = new Servico();
        try {
            //pega do json os registros da tag servico
            JSONArray vetor = (JSONArray) json.get("servico");
            JSONObject object = (JSONObject) vetor.get(0);
            servico.setCod(object.getInt("cod"));
            servico.setDescricao(object.getString("descricao"));
            servico.setValor(object.getDouble("valor"));
        } catch (Exception x) {
        }
        return servico;
    }

    public static String geraJSONServicos(ArrayList<Servico> servicos) {
        ArrayList<JSONObject> tabelaServicos = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Servico servico : servicos) {
            registro = preencheJSON(servico);

            //adiciona registro à lista de registros
            tabelaServicos.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("servico", (Object) tabelaServicos);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONServico(Servico servico) {
        return UtilJSON.limpaJSON(preencheJSON(servico));
    }

    public static JSONObject preencheJSON(Servico servico) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("cod", servico.getCod());
            registro.put("descricao", servico.getDescricao());
            registro.put("valor", servico.getValor());
        } catch (JSONException k) {
        }
        return null;
    }

}
