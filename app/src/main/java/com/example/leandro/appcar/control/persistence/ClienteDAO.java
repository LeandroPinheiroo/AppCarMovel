package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.model.Cliente;
import com.example.leandro.appcar.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class ClienteDao {
    private SQLiteConnector connector;
    private Context context;


    public ClienteDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Cliente cliente) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = cliente.getCodigo();
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("cpf", cliente.getCpf());
        values.put("sexo", cliente.getSexo());
        values.put("email", cliente.getEmail());
        values.put("telefoneM", cliente.getTelefoneM());
        values.put("telefoneF", cliente.getTelefoneF());
        values.put("endereco", cliente.getEndereco().getCod());
        values.put("rg", cliente.getRg());

        if (identifier != 0) {
            return database.update("cliente", values, "codigo = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("cliente", null, values);
        }
    }


    public int remove(Pessoa pessoa) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("cliente", "codigo = ?", new String[]{String.valueOf(pessoa.getCodigo())});
    }

    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        database.delete("cliente", null, null);
    }

    public List<Cliente> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Cliente> clientes = new ArrayList<>();

        Cursor cursor = database.query("cliente", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                Cliente cliente = new Cliente();
                Pessoa pessoa = new PessoaDao(this.context).get(cursor.getInt(cursor.getColumnIndex("codigo")));
                cliente.setCodigo(pessoa.getCodigo());
                cliente.setNome(pessoa.getNome());
                cliente.setCpf(pessoa.getCpf());
                cliente.setSexo(pessoa.getSexo());
                cliente.setEndereco(pessoa.getEndereco());
                cliente.setEmail(pessoa.getEmail());
                cliente.setTelefoneM(pessoa.getTelefoneM());
                cliente.setTelefoneF(pessoa.getTelefoneF());
                cliente.setRg(pessoa.getRg());
                cliente.setEndereco(new EnderecoDao(this.context).get(cursor.getInt(cursor.getColumnIndex("endereco_cod"))));
                clientes.add(cliente);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return clientes;
    }

    public Cliente get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("Cliente", null, "codigo=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Cliente cliente = new Cliente();
        Pessoa pessoa = new PessoaDao(this.context).get(id);
        cliente.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
        cliente.setNome(pessoa.getNome());
        cliente.setCpf(pessoa.getCpf());
        cliente.setSexo(pessoa.getSexo());
        cliente.setEndereco(pessoa.getEndereco());
        cliente.setEmail(pessoa.getEmail());
        cliente.setTelefoneM(pessoa.getTelefoneM());
        cliente.setTelefoneF(pessoa.getTelefoneF());
        cliente.setEndereco(new EnderecoDao(this.context).get(cursor.getInt(cursor.getColumnIndex("endereco_cod"))));
        cliente.setRg(pessoa.getRg());
        return cliente;
    }

}
