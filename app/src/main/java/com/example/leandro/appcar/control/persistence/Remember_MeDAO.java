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
        long i;
        SQLiteDatabase db = connector.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cod_login", remember_me.getCod_login());
        i = db.insert("remember_me", null, values);
        db.close();
        return i;
    }


    public void remove(Remember_Me remember_me) {
        SQLiteDatabase db = connector.getWritableDatabase();
        db.delete("remember_me", "cod_login = ?", new String[]{String.valueOf(remember_me.getCod_login())});
        db.close();
    }


    public List<Remember_Me> getAll() {
        SQLiteDatabase db = connector.getReadableDatabase();
        List<Remember_Me> remember_me = new ArrayList<>();
        Cursor cursor = db.query("remember_me", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        remember_me.add(new Remember_Me(cursor.getInt(cursor.getColumnIndex("cod_login"))));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return remember_me;
    }

    public Remember_Me get(int cod_login) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("remember_me", null, "cod_login=?", new String[]{String.valueOf(cod_login)}, null, null, null, null);
        Remember_Me remember_me = new Remember_Me();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                        remember_me.setCod_login(cursor.getInt(cursor.getColumnIndex("cod_login")));
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return remember_me;
    }
    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            db.delete("remember_me", null, null);
        }
        db.close();
    }
}
