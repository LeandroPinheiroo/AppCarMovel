package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.FuncionarioJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
import com.example.leandro.appcar.model.Funcionario;
import com.example.leandro.appcar.model.Pessoa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private SQLiteConnector connector;
    private Context context;

    public FuncionarioDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Funcionario funcionario) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = funcionario.getCodigo();
        ContentValues values = new ContentValues();

        System.out.println("ENTROU FUNCIONARIO DE "+funcionario.getCodigo());

        values.put("codigo", funcionario.getCodigo());
        values.put("login_cod", funcionario.getLogin());

        if (identifier != 0) {
            return database.update("funcionario", values, "codigo = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("funcionario", null, values);
        }
    }


    public int remove(Funcionario funcionario) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("funcionario", "codigo = ?", new String[]{String.valueOf(funcionario.getCodigo())});
    }


    public List<Funcionario> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Funcionario> funcionarios = new ArrayList<>();

        Cursor cursor = database.query("funcionario", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Funcionario funcionario = new Funcionario();
                Pessoa pessoa = new PessoaDao(this.context).get(cursor.getInt(cursor.getColumnIndex("codigo")));
                funcionario.setLogin(cursor.getInt(cursor.getColumnIndex("login_cod")));
                funcionario.setCodigo(pessoa.getCodigo());
                funcionario.setNome(pessoa.getNome());
                funcionario.setCpf(pessoa.getCpf());
                funcionario.setSexo(pessoa.getSexo());
                funcionario.setEndereco(pessoa.getEndereco());
                funcionario.setEmail(pessoa.getEmail());
                funcionario.setTelefoneM(pessoa.getTelefoneM());
                funcionario.setTelefoneF(pessoa.getTelefoneF());
                funcionario.setRg(pessoa.getRg());
                funcionario.setEndereco(pessoa.getEndereco());

                funcionarios.add(funcionario);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return funcionarios;
    }

    public Funcionario get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("funcionario", null, "codigo=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Funcionario funcionario = new Funcionario();
            funcionario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            funcionario.setLogin(cursor.getInt(cursor.getColumnIndex("login_cod")));
            cursor.close();
            db.close();

            Pessoa pessoa = new PessoaDao(this.context).get(funcionario.getCodigo());
            funcionario.setCodigo(pessoa.getCodigo());
            funcionario.setNome(pessoa.getNome());
            funcionario.setCpf(pessoa.getCpf());
            funcionario.setSexo(pessoa.getSexo());
            funcionario.setEndereco(pessoa.getEndereco());
            funcionario.setEmail(pessoa.getEmail());
            funcionario.setTelefoneM(pessoa.getTelefoneM());
            funcionario.setTelefoneF(pessoa.getTelefoneF());
            funcionario.setRg(pessoa.getRg());
            funcionario.setEndereco(pessoa.getEndereco());
            return funcionario;
        }
        return null;
    }

    public Funcionario getLogin(int login) {
        SQLiteDatabase db = connector.getReadableDatabase();
        String[] campos =  {"codigo","login_cod"};
        Cursor cursor = db.query("funcionario", campos, "login_cod="+login, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Funcionario funcionario = new Funcionario();

            funcionario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            funcionario.setLogin(cursor.getInt(cursor.getColumnIndex("login_cod")));
            cursor.close();
            db.close();

            Pessoa pessoa = new PessoaDao(this.context).get(funcionario.getCodigo());
            funcionario.setCodigo(pessoa.getCodigo());
            funcionario.setNome(pessoa.getNome());
            funcionario.setCpf(pessoa.getCpf());
            funcionario.setSexo(pessoa.getSexo());
            funcionario.setEndereco(pessoa.getEndereco());
            funcionario.setEmail(pessoa.getEmail());
            funcionario.setTelefoneM(pessoa.getTelefoneM());
            funcionario.setTelefoneF(pessoa.getTelefoneF());
            funcionario.setRg(pessoa.getRg());
            funcionario.setEndereco(pessoa.getEndereco());
            return funcionario;
        }
        return null;
    }


    public void truncate() {
        SQLiteDatabase database = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            database.delete("funcionario", null, null);
            database.close();
        }
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_Funcionario_All"))).getJSONObject("return").getJSONArray("funcionario");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(FuncionarioJSON.getFuncionarioJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
