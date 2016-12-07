package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.Util;
import com.example.leandro.appcar.control.rest.LoginJSON;
import com.example.leandro.appcar.control.server.ConnectorSocket;
import com.example.leandro.appcar.model.Login;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginDao {

    private SQLiteConnector connector;
    private Context context;


    public LoginDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Login login) {
        SQLiteDatabase db = connector.getWritableDatabase();
        long i;
        ContentValues values = new ContentValues();
        values.put("cod", login.getCod());
        values.put("senha", login.getSenha());
        values.put("usuario", login.getUsuario());
            i = db.insert("login", null, values);
        db.close();
        return i;
    }


    public void remove(Login login) {
        SQLiteDatabase db = connector.getWritableDatabase();
        db.delete("login", "cod = ?", new String[]{String.valueOf(login.getCod())});
    db.close();
    }


    public List<Login> getAll() {
        SQLiteDatabase db = connector.getReadableDatabase();
        List<Login> logins = new ArrayList<>();
        Cursor cursor = db.query("login", null, null, null, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Login login = new Login();
                        login.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                        login.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                        login.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
                        logins.add(login);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return logins;
    }

    public Login get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();
        Cursor cursor = db.query("login", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Login login = new Login();

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                        login.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                        login.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                        login.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return login;
    }

    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        Cursor cursor = db.query("login", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            db.delete("login", null, null);
        }
        db.close();
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ConnectorSocket().execute(Util.geraJSON("get_Login_All")).get()).getJSONObject("return").getJSONArray("login");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(LoginJSON.getLoginJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
