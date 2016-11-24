package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.OrdemServico;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class OrdemServicoDAO {

    private SQLiteConnector connector;
    private Context context;


    public OrdemServicoDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(OrdemServico ordemServico) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = ordemServico.getCod();
        ContentValues values = new ContentValues();
        values.put("tipo", ordemServico.getTipo());
        //values.put("data",);
        values.put("situacao", ordemServico.getSituacao());
        values.put("descricao", ordemServico.getDescricao());
        values.put("cliente",ordemServico.getCliente().getCodigo());



        if (identifier != 0) {
            return database.update("ordemservico", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("ordemservico", null, values);
        }
    }


    public int remove(OrdemServico ordemServico) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("ordemservico", "cod = ?", new String[] { String.valueOf(ordemServico.getCod()) });
    }

    public List<OrdemServico> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<OrdemServico> ordemServicos = new ArrayList<>();

        Cursor cursor = database.query("servidor", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                OrdemServico ordemServico = new OrdemServico();
                ordemServico.setData(null);
                ordemServico.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                ordemServico.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                ordemServico.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));




                ordemServicos.add(ordemServico);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ordemServicos;
    }
}
