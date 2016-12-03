package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.model.Remember_Me;

import java.util.ArrayList;
import java.util.List;


public class Remember_MeDao {

    private SQLiteConnector connector;
    private Context context;


    public Remember_MeDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }

    public long save(Remember_Me remember_me) {
        SQLiteDatabase database = connector.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cod_login", remember_me.getCod_login());
        return database.insert("remember_me", null, values);
    }


    public int remove(Remember_Me remember_me) {
        SQLiteDatabase database = connector.getWritableDatabase();
        return database.delete("remember_me", "cod_login = ?", new String[]{String.valueOf(remember_me.getCod_login())});
    }


    public List<Remember_Me> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Remember_Me> remember_me = new ArrayList<>();

        Cursor cursor = database.query("remember_me", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                remember_me.add(new Remember_Me(cursor.getInt(cursor.getColumnIndex("cod_login"))));
            } while (cursor.moveToNext());
        }
database.close();
        cursor.close();
        return remember_me;
    }

    public Remember_Me get(int cod_login) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("remember_me", null, "cod_login=?", new String[]{String.valueOf(cod_login)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Remember_Me remember_me = new Remember_Me();
        remember_me.setCod_login(cursor.getInt(cursor.getColumnIndex("cod_login")));
        cursor.close();
        db.close();
        return remember_me;
    }
    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            database.delete("remember_me", null, null);
        }
    }
}
