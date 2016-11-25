package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.Endereco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class EnderecoDAO {

    private SQLiteConnector connector;
    private Context context;


    public EnderecoDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Endereco endereco) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = endereco.getCod();
        ContentValues values = new ContentValues();
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

        return database.delete("endereco", "cod = ?", new String[] { String.valueOf(endereco.getCod()) });
    }

    public List<Endereco> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Endereco> enderecos = new ArrayList<>();

        Cursor cursor = database.query("servidor", null, null, null, null, null, null);
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

        cursor.close();
        return enderecos;
    }
    public Endereco get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("Endereco",null,"cod=?",new String[] { String.valueOf(id) },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Endereco endereco = new Endereco();
        endereco.setNumero((cursor.getString(cursor.getColumnIndex("numero"))));
        endereco.setRua((cursor.getString(cursor.getColumnIndex("rua"))));
        endereco.setBairro((cursor.getString(cursor.getColumnIndex("bairro"))));
        endereco.setCidade((cursor.getString(cursor.getColumnIndex("cidade"))));
        endereco.setComplemento((cursor.getString(cursor.getColumnIndex("comeplento"))));
        endereco.setCep((cursor.getString(cursor.getColumnIndex("cep"))));
        return endereco;
    }
}
