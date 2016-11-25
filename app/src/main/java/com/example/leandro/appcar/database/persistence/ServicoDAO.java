package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.Servico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class ServicoDAO {

    private SQLiteConnector connector;
    private Context context;


    public ServicoDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Servico servico) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = servico.getCod();
        ContentValues values = new ContentValues();
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

        return database.delete("servico", "cod = ?", new String[] { String.valueOf(servico.getCod()) });
    }

    public List<Servico> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Servico> servicos = new ArrayList<>();

        Cursor cursor = database.query("servidor", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Servico servico = new Servico();
                servico.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
                servico.setValor((cursor.getDouble(cursor.getColumnIndex("valor"))));
                servico.setDescricao((cursor.getString(cursor.getColumnIndex("descricao"))));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return servicos;
    }
    public Servico get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("Servico",null,"cod=?",new String[] { String.valueOf(id) },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Servico servico = new Servico();
        servico.setCod((cursor.getInt(cursor.getColumnIndex("cod"))));
        servico.setValor((cursor.getDouble(cursor.getColumnIndex("valor"))));
        servico.setDescricao((cursor.getString(cursor.getColumnIndex("descricao"))));
        return servico;
    }
}
