package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.ServicoJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
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
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = servico.getCod();
        ContentValues values = new ContentValues();
        values.put("cod", servico.getCod());
        values.put("descricao", servico.getDescricao());
        values.put("valor", servico.getValor());
        if (identifier != 0) {
            return database.update("servico", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("servico", null, values);
        }
    }


    public int remove(Servico servico) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("servico", "cod = ?", new String[]{String.valueOf(servico.getCod())});
    }

    public List<Servico> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Servico> servicos = new ArrayList<>();

        Cursor cursor = database.query("servico", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Servico servico = new Servico();
                servico.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
                servico.setValor((cursor.getDouble(cursor.getColumnIndex("valor"))));
                servico.setDescricao((cursor.getString(cursor.getColumnIndex("descricao"))));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return servicos;
    }

    public Servico get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("servico", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Servico servico = new Servico();
        servico.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
        servico.setValor((cursor.getDouble(cursor.getColumnIndex("valor"))));
        servico.setDescricao((cursor.getString(cursor.getColumnIndex("descricao"))));
        cursor.close();
        db.close();
        return servico;
    }

    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            database.delete("servico", null, null);
        }
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_Servico_All"))).getJSONObject("return").getJSONArray("servico");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(ServicoJSON.getServicoJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
