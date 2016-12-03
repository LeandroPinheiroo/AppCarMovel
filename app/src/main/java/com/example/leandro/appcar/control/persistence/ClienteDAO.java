package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.ClienteJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
import com.example.leandro.appcar.model.Cliente;
import com.example.leandro.appcar.model.Pessoa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        values.put("codigo", cliente.getCodigo());

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
                cliente.setEndereco(pessoa.getEndereco());
                clientes.add(cliente);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return clientes;
    }

    public Cliente get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("cliente", null, "codigo=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Cliente cliente = new Cliente();
        Pessoa pessoa = new PessoaDao(this.context).get(cursor.getInt(cursor.getColumnIndex("codigo")));
        cliente.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
        cliente.setNome(pessoa.getNome());
        cliente.setCpf(pessoa.getCpf());
        cliente.setSexo(pessoa.getSexo());
        cliente.setEndereco(pessoa.getEndereco());
        cliente.setEmail(pessoa.getEmail());
        cliente.setTelefoneM(pessoa.getTelefoneM());
        cliente.setTelefoneF(pessoa.getTelefoneF());
        cliente.setEndereco(pessoa.getEndereco());
        cliente.setRg(pessoa.getRg());
        cursor.close();
        db.close();
        return cliente;
    }

    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            database.delete("cliente", null, null);
            database.close();
        }
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_Cliente_All"))).getJSONObject("return").getJSONArray("cliente");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(ClienteJSON.getClienteJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
