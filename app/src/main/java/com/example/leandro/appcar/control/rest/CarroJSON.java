package com.example.leandro.appcar.control.rest;

import com.example.leandro.appcar.model.Carro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarroJSON {

    public static Carro getCarroJSON(JSONObject object) {
        //instancia vetor de carros
        Carro carro = new Carro();
        try {
            //pega do json os registros da tag carro
            carro.setCod(object.getInt("cod"));
            carro.setMarca(object.getString("marca"));
            carro.setModelo(object.getString("modelo"));
            carro.setCor(object.getString("cor"));
            carro.setAno(object.getString("ano"));
            carro.setChassi(object.getString("chassi"));
            carro.setKm(object.getString("km"));
            carro.setPlaca(object.getString("placa"));
            carro.setObs(object.getString("obs"));
            carro.setDono(object.getInt("dono_codigo"));
        } catch (Exception x) {
        }
        return carro;
    }

    public static String geraJSONCarros(ArrayList<Carro> carros) {
        ArrayList<JSONObject> tabelaCarros = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Carro carro : carros) {
            tabelaCarros.add(preencheJSON(carro));
        }
        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.put("carro", (Object) tabelaCarros);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONCarro(Carro carro) {
        return UtilJSON.limpaJSON(preencheJSON(carro));
    }

    public static JSONObject preencheJSON(Carro carro) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("cod", carro.getCod());
            registro.put("marca", carro.getMarca());
            registro.put("modelo", carro.getModelo());
            registro.put("cor", carro.getCor());
            registro.put("ano", carro.getAno());
            registro.put("chassi", carro.getChassi());
            registro.put("km", carro.getKm());
            registro.put("placa", carro.getPlaca());
            registro.put("carObs", carro.getObs());
            registro.put("dono_codigo", carro.getDono());
            return registro;
        } catch (JSONException k) {
        }
        return null;
    }

}
