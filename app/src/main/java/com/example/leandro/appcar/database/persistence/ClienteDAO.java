package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.Cliente;
import com.example.leandro.appcar.database.models.Pessoa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class ClienteDAO {
    private SQLiteConnector connector;
    private Context context;


    public ClienteDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }



    public long save(Cliente cliente) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = cliente.getCodigo();
        ContentValues values = new ContentValues();
        values.put("nome",cliente.getNome());
        values.put("cpf",cliente.getCpf());
        values.put("sexo", cliente.getSexo());
        values.put("email", cliente.getEmail());
        values.put("TelefoneM", cliente.getTelefoneM());
        values.put("TelefoneF", cliente.getTelefoneF());
        values.put("endereco",cliente.getEndereco().getCod());
        values.put("rg", cliente.getRg());

        if (identifier != 0) {
            return database.update("cliente", values, "codigo = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("cliente", null, values);
        }
    }


    public int remove(Pessoa pessoa) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("cliente", "codigo = ?", new String[] { String.valueOf(pessoa.getCodigo()) });
    }

    public List<Cliente> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Cliente> clientes = new ArrayList<>();

        Cursor cursor = database.query("cliente", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                cliente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                cliente.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
                cliente.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
                cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                cliente.setTelefoneM(cursor.getString(cursor.getColumnIndex("telefoneM")));
                cliente.setTelefoneF(cursor.getString(cursor.getColumnIndex("telefoneF")));
                cliente.setRg(cursor.getString(cursor.getColumnIndex("rg")));

                clientes.add(cliente);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return clientes;
    }

}
