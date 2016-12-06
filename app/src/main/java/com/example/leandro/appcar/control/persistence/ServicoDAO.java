package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.Util;
import com.example.leandro.appcar.control.rest.ServicoJSON;
import com.example.leandro.appcar.control.server.ConnectorSocket;
import com.example.leandro.appcar.model.Servico;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ServicoDao {

    private SQLiteConnector connector;
    private Context context;


    public ServicoDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Servico servico) {
        long i;
        SQLiteDatabase db = connector.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cod", servico.getCod());
        values.put("descricao", servico.getDescricao());
        values.put("valor", servico.getValor());
            i = db.insert("servico", null, values);
        db.close();
        return i;
    }


    public void remove(Servico servico) {
        SQLiteDatabase db = connector.getWritableDatabase();
        db.delete("servico", "cod = ?", new String[]{String.valueOf(servico.getCod())});
        db.close();
    }

    public List<Servico> getAll() {
        SQLiteDatabase db = connector.getReadableDatabase();
        List<Servico> servicos = new ArrayList<>();
        Cursor cursor = db.query("servico", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Servico servico = new Servico();
                        servico.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
                        servico.setValor((cursor.getDouble(cursor.getColumnIndex("valor"))));
                        servico.setDescricao((cursor.getString(cursor.getColumnIndex("descricao"))));
                        servicos.add(servico);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return servicos;
    }

    public Servico get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();
        Servico servico = new Servico();
        Cursor cursor = db.query("servico", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                        servico.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
                        servico.setValor((cursor.getDouble(cursor.getColumnIndex("valor"))));
                        servico.setDescricao((cursor.getString(cursor.getColumnIndex("descricao"))));
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return servico;
    }

    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        Cursor cursor = db.query("servico", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            db.delete("servico", null, null);
        }
        db.close();
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ConnectorSocket().execute(Util.geraJSON("get_Servico_All")).get()).getJSONObject("return").getJSONArray("servico");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(ServicoJSON.getServicoJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
