package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.Servico_OSJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
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
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = servico_os.getCod();
        ContentValues values = new ContentValues();
        values.put("cod", servico_os.getCod());
        values.put("servico_cod", servico_os.getServico());
        values.put("ordemservico_cod", servico_os.getOrdemservico());
        values.put("funcionario_codigo", servico_os.getFuncionario());

        if (identifier != 0) {
            return database.update("servico_os", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("servico_os", null, values);
        }
    }


    public int remove(Servico_OS servico_os) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("servico_os", "cod = ?", new String[]{String.valueOf(servico_os.getCod())});
    }


    public List<Servico_OS> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Servico_OS> servicos = new ArrayList<>();

        Cursor cursor = database.query("servico_os", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Servico_OS servico_os = new Servico_OS();
                servico_os.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
                servico_os.setFuncionario(cursor.getInt(cursor.getColumnIndex("funcionario_codigo")));
                servico_os.setOrdemservico(cursor.getInt(cursor.getColumnIndex("ordemservico_cod")));
                servico_os.setServico(cursor.getInt(cursor.getColumnIndex("servico_cod")));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return servicos;
    }

    public Servico_OS get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("servico_os", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Servico_OS servico_os = new Servico_OS();
        servico_os.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
        servico_os.setFuncionario(cursor.getInt(cursor.getColumnIndex("funcionario_codigo")));
        servico_os.setOrdemservico(cursor.getInt(cursor.getColumnIndex("ordemservico_cod")));
        servico_os.setServico(cursor.getInt(cursor.getColumnIndex("servico_cod")));
cursor.close();
        db.close();
        return servico_os;
    }

    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            database.delete("servico_os", null, null);
        }
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_Servico_OS_All"))).getJSONObject("return").getJSONArray("servico_os");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(Servico_OSJSON.getServico_OSJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
