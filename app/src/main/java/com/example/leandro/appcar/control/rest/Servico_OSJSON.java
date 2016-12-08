package com.example.leandro.appcar.control.rest;

import com.example.leandro.appcar.model.Servico_OS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Servico_OSJSON {

    public static Servico_OS getServico_OSJSON(JSONObject object) {
        //instancia vetor de servico_os
        Servico_OS servico_os = new Servico_OS();
        try {
            //pega do json os registros da tag servico_os
            servico_os.setCod(object.getInt("cod"));
            servico_os.setFuncionario(object.getInt("funcionario_codigo"));
            servico_os.setOrdemservico(object.getInt("ordemservico_codigo"));
            servico_os.setServico(object.getInt("servico_codigo"));
        } catch (Exception x) {
        }
        return servico_os;
    }

    public static String geraJSONServico_OSs(ArrayList<Servico_OS> servico_os) {
        ArrayList<JSONObject> tabelaServico_OSs = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Servico_OS servico : servico_os) {
            registro = preencheJSON(servico);

            //adiciona registro à lista de registros
            tabelaServico_OSs.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("servico_os", (Object) tabelaServico_OSs);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONServico_OS(Servico_OS servico_os) {
        return UtilJSON.limpaJSON(preencheJSON(servico_os));
    }

    public static JSONObject preencheJSON(Servico_OS servico) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("cod", servico.getCod());
            registro.put("funcionario_codigo", servico.getFuncionario());
            registro.put("ordemservico_codigo", servico.getOrdemservico());
            registro.put("servico_codigo", servico.getServico());
            return registro;
        } catch (JSONException k) {
        }
        return null;
    }

}
