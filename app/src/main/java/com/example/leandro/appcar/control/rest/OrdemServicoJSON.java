package com.example.leandro.appcar.control.rest;

import com.example.leandro.appcar.model.OrdemServico;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OrdemServicoJSON {

    public static OrdemServico getOrdemServicoJSON(JSONObject object) {
        //instancia vetor de oss
        OrdemServico os = new OrdemServico();
        try {
            //pega do json os registros da tag os
            os.setCod(object.getInt("cod"));
            os.setCliente(object.getInt("cliente_codigo"));
            os.setCarro(object.getInt("carro_cod"));
            os.setData(Timestamp.valueOf(object.getString("data")));
            os.setSituacao(object.getInt("situacao"));
            os.setTipo(object.getString("tipo"));
            os.setDescricao(object.getString("descricao"));
        } catch (Exception x) {
        }
        return os;
    }

    public static String geraJSONOrdemServicos(ArrayList<OrdemServico> ordens) {
        ArrayList<JSONObject> tabelaOrdemServicos = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (OrdemServico os : ordens) {
            registro = preencheJSON(os);

            //adiciona registro à lista de registros
            tabelaOrdemServicos.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("os", (Object) tabelaOrdemServicos);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONOrdemServico(OrdemServico os) {
        return UtilJSON.limpaJSON(preencheJSON(os));
    }

    public static JSONObject preencheJSON(OrdemServico os) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("cod", os.getCod());
            registro.put("cliente_codigo", os.getCliente());
            registro.put("carro_cod", os.getCarro());
            registro.put("data", os.getData().toString());
            registro.put("descricao", os.getDescricao());
            registro.put("situacao", os.getSituacao());
            registro.put("tipo", os.getTipo());
            return registro;
        } catch (JSONException k) {
        }
        return null;
    }

}
