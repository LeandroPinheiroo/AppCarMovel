package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.Pessoa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class PessoaDAO {

    private SQLiteConnector connector;
    private Context context;


    public PessoaDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }



    public long save(Pessoa pessoa) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = pessoa.getCodigo();
        ContentValues values = new ContentValues();
        values.put("nome",pessoa.getNome());
        values.put("cpf",pessoa.getCpf());
        values.put("sexo", pessoa.getSexo());
        values.put("email", pessoa.getEmail());
        values.put("TelefoneM", pessoa.getTelefoneM());
        values.put("TelefoneF", pessoa.getTelefoneF());
        values.put("rg", pessoa.getRg());
        values.put("endereco",pessoa.getEndereco().getCod());

        if (identifier != 0) {
            return database.update("pessoa", values, "codigo = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("pessoa", null, values);
        }
    }


    public int remove(Pessoa pessoa) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("pessoa", "codigo = ?", new String[] { String.valueOf(pessoa.getCodigo()) });
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
                pessoa.setEndereco(new EnderecoDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("endereco_cod"))));

                pessoas.add(pessoa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return pessoas;
    }
    public Pessoa get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("pessoa",null,"codigo=?",new String[] { String.valueOf(id) },null,null,null,null);
        if (cursor != null)
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
        pessoa.setEndereco(new EnderecoDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("endereco_cod"))));
        return pessoa;
    }

}
