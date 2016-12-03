package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.Cliente;
import com.example.leandro.appcar.database.models.Funcionario;
import com.example.leandro.appcar.database.models.Pessoa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class FuncionarioDAO {
    private SQLiteConnector connector;
    private Context context;

    public FuncionarioDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }



    public long save(Funcionario funcionario) {
        SQLiteDatabase database = connector.getWritableDatabase();
        long identifier = funcionario.getCodigo();
        ContentValues values = new ContentValues();
        values.put("nome",funcionario.getNome());
        values.put("cpf",funcionario.getCpf());
        values.put("sexo", funcionario.getSexo());
        values.put("email", funcionario.getEmail());
        values.put("TelefoneM", funcionario.getTelefoneM());
        values.put("TelefoneF", funcionario.getTelefoneF());
        values.put("endereco",funcionario.getEndereco().getCod());
        values.put("rg", funcionario.getRg());


        if (identifier != 0) {
            return database.update("funcionario", values, "codigo = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("funcionario", null, values);
        }
    }


    public int remove(Pessoa pessoa) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("funcionario", "codigo = ?", new String[] { String.valueOf(pessoa.getCodigo()) });
    }
    public void truncate(){
        SQLiteDatabase database = connector.getWritableDatabase();
        database.delete("funcionario", null, null);
    }

    public List<Funcionario> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Funcionario> funcionarios = new ArrayList<>();

        Cursor cursor = database.query("funcionario", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Funcionario funcionario = new Funcionario();
                Pessoa pessoa = new PessoaDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("codigo")));
                funcionario.setCodigo(pessoa.getCodigo());
                funcionario.setNome(pessoa.getNome());
                funcionario.setCpf(pessoa.getCpf());
                funcionario.setSexo(pessoa.getSexo());
                funcionario.setEndereco(pessoa.getEndereco());
                funcionario.setEmail(pessoa.getEmail());
                funcionario.setTelefoneM(pessoa.getTelefoneM());
                funcionario.setTelefoneF(pessoa.getTelefoneF());
                funcionario.setRg(pessoa.getRg());
                funcionario.setEndereco(new EnderecoDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("endereco_cod"))));

                funcionarios.add(funcionario);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return funcionarios;
    }

    public Funcionario get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("funcionario",null,"codigo=?",new String[] { String.valueOf(id) },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Funcionario funcionario = new Funcionario();
        Pessoa pessoa = new PessoaDAO(this.context).get(id);
        funcionario.setCodigo(pessoa.getCodigo());
        funcionario.setNome(pessoa.getNome());
        funcionario.setCpf(pessoa.getCpf());
        funcionario.setSexo(pessoa.getSexo());
        funcionario.setEndereco(pessoa.getEndereco());
        funcionario.setEmail(pessoa.getEmail());
        funcionario.setTelefoneM(pessoa.getTelefoneM());
        funcionario.setTelefoneF(pessoa.getTelefoneF());
        funcionario.setRg(pessoa.getRg());
        funcionario.setEndereco(new EnderecoDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("endereco_cod"))));
        return funcionario;
    }
}
