package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.OrdemServicoJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
import com.example.leandro.appcar.model.OrdemServico;

import org.json.JSONArray;
import org.json.JSONObject;

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
        SQLiteDatabase db = connector.getWritableDatabase();
        long identifier = ordemServico.getCod();
        ContentValues values = new ContentValues();
        values.put("cod", ordemServico.getCod());
        values.put("tipo", ordemServico.getTipo());
        values.put("data", ordemServico.getData().toString());
        values.put("situacao", ordemServico.getSituacao());
        values.put("descricao", ordemServico.getDescricao());
        values.put("cliente_codigo", ordemServico.getCliente());
        values.put("carro_cod", ordemServico.getCarro());


        if (identifier != 0) {
            return db.update("ordemservico", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return db.insert("ordemservico", null, values);
        }
    }


    public int remove(OrdemServico ordemServico) {
        SQLiteDatabase db = connector.getWritableDatabase();

        return db.delete("ordemservico", "cod = ?", new String[]{String.valueOf(ordemServico.getCod())});
    }

    public List<OrdemServico> getAll() {
        SQLiteDatabase db = connector.getReadableDatabase();

        List<OrdemServico> ordemServicos = new ArrayList<>();

        Cursor cursor = db.query("ordemservico", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                OrdemServico ordemServico = new OrdemServico();
                ordemServico.setData(null);
                ordemServico.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                ordemServico.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                ordemServico.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                ordemServico.setData(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("data"))));
                ordemServico.setCarro(cursor.getInt(cursor.getColumnIndex("carro_cod")));
                ordemServico.setCliente(cursor.getInt(cursor.getColumnIndex("cliente_codigo")));


                ordemServicos.add(ordemServico);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
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
        ordemServico.setCarro(cursor.getInt(cursor.getColumnIndex("carro_cod")));
        ordemServico.setCliente(cursor.getInt(cursor.getColumnIndex("cliente_codigo")));
        db.close();
        cursor.close();
        return ordemServico;
    }

    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            db.delete("ordemservico", null, null);
            db.close();
        }
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_OrdemServico_All"))).getJSONObject("return").getJSONArray("os");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(OrdemServicoJSON.getOrdemServicoJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
