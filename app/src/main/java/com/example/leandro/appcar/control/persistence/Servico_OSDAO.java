package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.Util;
import com.example.leandro.appcar.control.rest.Servico_OSJSON;
import com.example.leandro.appcar.control.server.ConnectorSocket;
import com.example.leandro.appcar.model.Servico_OS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Servico_OSDao {
    private SQLiteConnector connector;
    private Context context;


    public Servico_OSDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Servico_OS servico_os) {
        long i;
        SQLiteDatabase db = connector.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cod", servico_os.getCod());
        values.put("servico_cod", servico_os.getServico());
        values.put("ordemservico_cod", servico_os.getOrdemservico());
        values.put("funcionario_codigo", servico_os.getFuncionario());
            i = db.insert("servico_os", null, values);
        db.close();
        return i;
    }


    public void remove(Servico_OS servico_os) {
        SQLiteDatabase db = connector.getWritableDatabase();
 db.delete("servico_os", "cod = ?", new String[]{String.valueOf(servico_os.getCod())});
        db.close();
    }


    public List<Servico_OS> getAll() {
        SQLiteDatabase db = connector.getReadableDatabase();
        List<Servico_OS> servicos = new ArrayList<>();
        Cursor cursor = db.query("servico_os", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Servico_OS servico_os = new Servico_OS();
                        servico_os.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
                        servico_os.setFuncionario(cursor.getInt(cursor.getColumnIndex("funcionario_codigo")));
                        servico_os.setOrdemservico(cursor.getInt(cursor.getColumnIndex("ordemservico_cod")));
                        servico_os.setServico(cursor.getInt(cursor.getColumnIndex("servico_cod")));
                        servicos.add(servico_os);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return servicos;
    }

    public Servico_OS get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();
        Cursor cursor = db.query("servico_os", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Servico_OS servico_os = new Servico_OS();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                        servico_os.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
                        servico_os.setFuncionario(cursor.getInt(cursor.getColumnIndex("funcionario_codigo")));
                        servico_os.setOrdemservico(cursor.getInt(cursor.getColumnIndex("ordemservico_cod")));
                        servico_os.setServico(cursor.getInt(cursor.getColumnIndex("servico_cod")));
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return servico_os;
    }

    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        Cursor cursor = db.query("servico_os", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            db.delete("servico_os", null, null);
        }
        db.close();
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ConnectorSocket().execute(Util.geraJSON("get_Servico_OS_All")).get()).getJSONObject("return").getJSONArray("servico_os");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(Servico_OSJSON.getServico_OSJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
