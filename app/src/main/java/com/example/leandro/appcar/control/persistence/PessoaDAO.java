package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.PessoaJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
import com.example.leandro.appcar.model.Pessoa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PessoaDao {

    private SQLiteConnector connector;
    private Context context;


    public PessoaDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Pessoa pessoa) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = pessoa.getCodigo();
        ContentValues values = new ContentValues();
        values.put("codigo", pessoa.getCodigo());
        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("sexo", pessoa.getSexo());
        values.put("email", pessoa.getEmail());
        values.put("telefoneM", pessoa.getTelefoneM());
        values.put("telefoneF", pessoa.getTelefoneF());
        values.put("rg", pessoa.getRg());
        values.put("endereco_cod", pessoa.getEndereco());

        if (identifier != 0) {
            return database.update("pessoa", values, "codigo = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("pessoa", null, values);
        }
    }


    public int remove(Pessoa pessoa) {
        SQLiteDatabase database = connector.getWritableDatabase();
        return database.delete("pessoa", "codigo = ?", new String[]{String.valueOf(pessoa.getCodigo())});
    }

    public List<Pessoa> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Pessoa> pessoas = new ArrayList<>();

        Cursor cursor = database.query("pessoa", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Pessoa pessoa = new Pessoa();
                pessoa.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                pessoa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                pessoa.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
                pessoa.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
                pessoa.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                pessoa.setTelefoneM(cursor.getString(cursor.getColumnIndex("telefoneM")));
                pessoa.setTelefoneF(cursor.getString(cursor.getColumnIndex("telefoneF")));
                pessoa.setRg(cursor.getString(cursor.getColumnIndex("rg")));
                pessoa.setEndereco(cursor.getInt(cursor.getColumnIndex("endereco_cod")));
                pessoas.add(pessoa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return pessoas;
    }

    public Pessoa get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();
        Cursor cursor = db.query("pessoa", null, "codigo=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Pessoa pessoa = new Pessoa();
            pessoa.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            pessoa.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
            pessoa.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            pessoa.setTelefoneM(cursor.getString(cursor.getColumnIndex("telefoneM")));
            pessoa.setTelefoneF(cursor.getString(cursor.getColumnIndex("telefoneF")));
            pessoa.setRg(cursor.getString(cursor.getColumnIndex("rg")));
            pessoa.setEndereco(cursor.getInt(cursor.getColumnIndex("endereco_cod")));
            cursor.close();
            db.close();
            return pessoa;
        }
           return null;
    }

    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            db.delete("pessoa", null, null);
            db.close();
        }
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_Pessoa_All"))).getJSONObject("return").getJSONArray("pessoa");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(PessoaJSON.getPessoaJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
