package com.example.leandro.appcar.server.rest;


import org.json.JSONObject;

public class UtilJSON {

    public static String limpaJSON(JSONObject bd) {
        String f = bd.toString();
        f = f.replace("\\", "");
        f = f.replace(":\"[", ":[");
        f = f.replace("]\"}", "]}");
        f = f.replace("]\"", "]");
        f = f.replace(", {", ",{");
        return f;
    }
}
