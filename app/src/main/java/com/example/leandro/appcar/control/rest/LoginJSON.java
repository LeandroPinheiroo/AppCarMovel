package com.example.leandro.appcar.control.rest;

import com.example.leandro.appcar.model.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginJSON {

    public static Login getLoginJSON(JSONObject json) {
        //instancia vetor de logins
        Login login = new Login();
        try {
            //pega do json os registros da tag login
            JSONArray vetor = (JSONArray) json.get("login");
            JSONObject object = (JSONObject) vetor.get(0);
            login.setCod(object.getInt("cod"));
            login.setUsuario(object.getString("usuario"));
            login.setSenha(object.getString("senha"));
        } catch (Exception x) {
        }
        return login;
    }

    public static String geraJSONLogins(ArrayList<Login> logins) {
        ArrayList<JSONObject> tabelaLogins = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Login login : logins) {
            registro = preencheJSON(login);

            //adiciona registro à lista de registros
            tabelaLogins.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("login", (Object) tabelaLogins);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONLogin(Login login) {
        return UtilJSON.limpaJSON(preencheJSON(login));
    }

    public static JSONObject preencheJSON(Login login) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("cod", login.getCod());
            registro.put("usuario", login.getUsuario());
            registro.put("senha", login.getSenha());
            return registro;
        } catch (JSONException k) {
        }
        return null;
    }

}
