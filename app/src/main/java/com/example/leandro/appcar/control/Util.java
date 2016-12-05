package com.example.leandro.appcar.control;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by guilh on 04/12/2016.
 */

public class Util {


    public static String geraJSON(String request, JSONArray array) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request", request);
            jsonObject.put("array", array);
            return jsonObject.toString();
        } catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }

    public static String geraJSON(String request) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request", request);
            return jsonObject.toString();
        } catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }

    public static String geraJSON(String request, JSONObject json) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request", request);
            jsonObject.put("object", json);
            return jsonObject.toString();
        } catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }

}
