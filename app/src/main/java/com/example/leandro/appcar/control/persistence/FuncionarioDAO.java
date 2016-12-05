package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.Util;
import com.example.leandro.appcar.control.rest.FuncionarioJSON;
import com.example.leandro.appcar.control.server.ConnectorSocket;
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
        SQLiteDatabase db = connector.getWritableDatabase();
        long i;
        ContentValues values = new ContentValues();
        values.put("codigo", funcionario.getCodigo());
        values.put("login_cod", funcionario.getLogin());
        if (funcionario.getCodigo() != 0) {
            i = db.update("funcionario", values, "codigo = ?", new String[]{String.valueOf(funcionario.getCodigo())});
        } else {
            i = db.insert("funcionario", null, values);
        }
        db.close();
        return i;
    }


    public void remove(Funcionario funcionario) {
        SQLiteDatabase db = connector.getWritableDatabase();
        db.delete("funcionario", "codigo = ?", new String[]{String.valueOf(funcionario.getCodigo())});
        db.close();
    }


    public List<Funcionario> getAll() {
        SQLiteDatabase db = connector.getReadableDatabase();
        List<Funcionario> funcionarios = new ArrayList<>();
        Cursor cursor = db.query("funcionario", null, null, null, null, null, null);
        if (cursor != null) {
            try {
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
            } finally {
                cursor.close();
            }
        }
        db.close();
        return funcionarios;
    }

    public Funcionario get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();
        Cursor cursor = db.query("funcionario", null, "codigo=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Funcionario funcionario = new Funcionario();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        funcionario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        funcionario.setLogin(cursor.getInt(cursor.getColumnIndex("login_cod")));
                        Pessoa pessoa = new PessoaDao(this.context).get(cursor.getInt(cursor.getColumnIndex("codigo")));
                        funcionario.setNome(pessoa.getNome());
                        funcionario.setCpf(pessoa.getCpf());
                        funcionario.setSexo(pessoa.getSexo());
                        funcionario.setEndereco(pessoa.getEndereco());
                        funcionario.setEmail(pessoa.getEmail());
                        funcionario.setTelefoneM(pessoa.getTelefoneM());
                        funcionario.setTelefoneF(pessoa.getTelefoneF());
                        funcionario.setRg(pessoa.getRg());
                        funcionario.setEndereco(pessoa.getEndereco());
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return funcionario;
    }

    public Funcionario getLogin(int login) {
        SQLiteDatabase db = connector.getReadableDatabase();
        Cursor cursor = db.query("funcionario", null, "login_cod=?", new String[]{String.valueOf(login)}, null, null, null, null);
        Funcionario funcionario = new Funcionario();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        funcionario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        funcionario.setLogin(cursor.getInt(cursor.getColumnIndex("login_cod")));
                        Pessoa pessoa = new PessoaDao(this.context).get(cursor.getInt(cursor.getColumnIndex("codigo")));
                        funcionario.setNome(pessoa.getNome());
                        funcionario.setCpf(pessoa.getCpf());
                        funcionario.setSexo(pessoa.getSexo());
                        funcionario.setEndereco(pessoa.getEndereco());
                        funcionario.setEmail(pessoa.getEmail());
                        funcionario.setTelefoneM(pessoa.getTelefoneM());
                        funcionario.setTelefoneF(pessoa.getTelefoneF());
                        funcionario.setRg(pessoa.getRg());
                        funcionario.setEndereco(pessoa.getEndereco());
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return funcionario;
    }


    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            db.delete("funcionario", null, null);
        }
        db.close();
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ConnectorSocket().execute(Util.geraJSON("get_Funcionario_All")).get()).getJSONObject("return").getJSONArray("funcionario");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(FuncionarioJSON.getFuncionarioJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
