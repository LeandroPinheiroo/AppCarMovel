package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.model.Login;

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
        SQLiteDatabase database = connector.getWritableDatabase();
        Integer identifier = login.getCod();
        ContentValues values = new ContentValues();
        values.put("senha", login.getSenha());
        values.put("usuario", login.getUsuario());
        if (identifier != 0) {
            return database.update("login", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("login", null, values);
        }
    }


    public int remove(Login login) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("login", "cod = ?", new String[]{String.valueOf(login.getCod())});
    }

    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        database.delete("login", null, null);
    }

    public List<Login> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Login> servidors = new ArrayList<>();

        Cursor cursor = database.query("login", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Login login = new Login();
                login.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                login.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                login.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));

                servidors.add(login);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return servidors;
    }

    public Login get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("login", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Login login = new Login();
        login.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
        login.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        login.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
        return login;
    }
}
