package com.example.leandro.appcar.server.rest;

import com.example.leandro.appcar.database.models.Endereco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EnderecoJSON {

    public static Endereco getEnderecoJSON(JSONObject json) {
        //instancia vetor de enderecos
        Endereco endereco = new Endereco();
        try {
            //pega do json os registros da tag endereco
            JSONArray vetor = (JSONArray) json.get("endereco");
            JSONObject object = (JSONObject) vetor.get(0);
            endereco.setCod(object.getInt("cod"));
            endereco.setRua(object.getString("rua"));
            endereco.setNumero(object.getString("numero"));
            endereco.setComplemento(object.getString("complemento"));
            endereco.setBairro(object.getString("bairro"));
            endereco.setCidade(object.getString("cidade"));
            endereco.setCep(object.getString("cep"));
        } catch (Exception x) {
        }
        return endereco;
    }

    public static String geraJSONEnderecos(ArrayList<Endereco> enderecos) {
        ArrayList<JSONObject> tabelaEnderecos = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Endereco endereco : enderecos) {
            registro = preencheJSON(endereco);

            //adiciona registro à lista de registros
            tabelaEnderecos.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("endereco", (Object) tabelaEnderecos);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONEndereco(Endereco endereco) {
        return UtilJSON.limpaJSON(preencheJSON(endereco));
    }

    public static JSONObject preencheJSON(Endereco endereco) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("cod", endereco.getCod());
            registro.put("rua", endereco.getRua());
            registro.put("numero", endereco.getNumero());
            registro.put("complemento", endereco.getComplemento());
            registro.put("bairro", endereco.getBairro());
            registro.put("cidade", endereco.getCidade());
            registro.put("cep", endereco.getCep());
        } catch (JSONException k) {
        }
        return null;
    }

}
