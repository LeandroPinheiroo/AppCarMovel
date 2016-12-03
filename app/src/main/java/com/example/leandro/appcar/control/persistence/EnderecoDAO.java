package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.EnderecoJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
import com.example.leandro.appcar.model.Endereco;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDao {

    private SQLiteConnector connector;
    private Context context;


    public EnderecoDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Endereco endereco) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = endereco.getCod();
        ContentValues values = new ContentValues();
        values.put("cod", endereco.getCod());
        values.put("numero", endereco.getNumero());
        values.put("rua", endereco.getRua());
        values.put("bairro", endereco.getBairro());
        values.put("cidade", endereco.getCidade());
        values.put("complemento", endereco.getComplemento());
        values.put("cep", endereco.getCep());


        if (identifier != 0) {
            return database.update("endereco", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("endereco", null, values);
        }
    }


    public int remove(Endereco endereco) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("endereco", "cod = ?", new String[]{String.valueOf(endereco.getCod())});
    }


    public List<Endereco> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Endereco> enderecos = new ArrayList<>();

        Cursor cursor = database.query("endereco", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Endereco endereco = new Endereco();
                endereco.setNumero((cursor.getString(cursor.getColumnIndex("numero"))));
                endereco.setRua((cursor.getString(cursor.getColumnIndex("rua"))));
                endereco.setBairro((cursor.getString(cursor.getColumnIndex("bairro"))));
                endereco.setCidade((cursor.getString(cursor.getColumnIndex("cidade"))));
                endereco.setComplemento((cursor.getString(cursor.getColumnIndex("comeplento"))));
                endereco.setCep((cursor.getString(cursor.getColumnIndex("cep"))));
                enderecos.add(endereco);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return enderecos;
    }

    public Endereco get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("Endereco", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Endereco endereco = new Endereco();
        endereco.setNumero((cursor.getString(cursor.getColumnIndex("numero"))));
        endereco.setRua((cursor.getString(cursor.getColumnIndex("rua"))));
        endereco.setBairro((cursor.getString(cursor.getColumnIndex("bairro"))));
        endereco.setCidade((cursor.getString(cursor.getColumnIndex("cidade"))));
        endereco.setComplemento((cursor.getString(cursor.getColumnIndex("comeplento"))));
        endereco.setCep((cursor.getString(cursor.getColumnIndex("cep"))));
        cursor.close();
        db.close();
        return endereco;
    }

    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            database.delete("endereco", null, null);
            database.close();
        }
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_Endereco_All"))).getJSONObject("return").getJSONArray("endereco");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(EnderecoJSON.getEnderecoJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
