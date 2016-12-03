package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.model.OrdemServico;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDao {

    private SQLiteConnector connector;
    private Context context;


    public OrdemServicoDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(OrdemServico ordemServico) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = ordemServico.getCod();
        ContentValues values = new ContentValues();
        values.put("tipo", ordemServico.getTipo());
        values.put("data", ordemServico.getData().toString());
        values.put("situacao", ordemServico.getSituacao());
        values.put("descricao", ordemServico.getDescricao());
        values.put("cliente", ordemServico.getCliente().getCodigo());
        values.put("carro", ordemServico.getCarro().getCod());


        if (identifier != 0) {
            return database.update("ordemservico", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("ordemservico", null, values);
        }
    }


    public int remove(OrdemServico ordemServico) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("ordemservico", "cod = ?", new String[]{String.valueOf(ordemServico.getCod())});
    }

    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        database.delete("ordemservico", null, null);
    }

    public List<OrdemServico> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<OrdemServico> ordemServicos = new ArrayList<>();

        Cursor cursor = database.query("ordemservico", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                OrdemServico ordemServico = new OrdemServico();
                ordemServico.setData(null);
                ordemServico.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                ordemServico.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                ordemServico.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                ordemServico.setData(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("data"))));
                ordemServico.setCarro(new CarroDao(this.context).get(cursor.getInt(cursor.getColumnIndex("carro_cod"))));
                ordemServico.setCliente(new ClienteDao(this.context).get(cursor.getInt(cursor.getColumnIndex("cliente_codigo"))));


                ordemServicos.add(ordemServico);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ordemServicos;
    }

    public OrdemServico get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("ordemservico", null, "codigo=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setData(null);
        ordemServico.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
        ordemServico.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        ordemServico.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
        ordemServico.setData(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("data"))));
        ordemServico.setCarro(new CarroDao(this.context).get(cursor.getInt(cursor.getColumnIndex("carro_cod"))));
        ordemServico.setCliente(new ClienteDao(this.context).get(cursor.getInt(cursor.getColumnIndex("cliente_codigo"))));
        return ordemServico;
    }
}
