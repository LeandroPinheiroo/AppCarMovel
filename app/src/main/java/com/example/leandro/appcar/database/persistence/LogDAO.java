package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class LogDAO {

    private SQLiteConnector connector;
    private Context context;


    public LogDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Log log) {
        SQLiteDatabase database = connector.getWritableDatabase();
        Integer identifier = log.getCod();
        ContentValues values = new ContentValues();
        values.put("descricao", log.getDescricao());
        values.put("data",(log.getData().toString()));
        values.put("funcionario_codigo",log.getFuncionario().getCodigo());


        if (identifier != 0) {
            return database.update("log", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("log", null, values);
        }
    }


    public int remove(Log log) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("log", "cod = ?", new String[] { String.valueOf(log.getCod()) });
    }

    public List<Log> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Log> logs = new ArrayList<>();

        Cursor cursor = database.query("log", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log log = new Log(null,null);
                log.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                log.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                log.setData(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("data"))));
                log.setFuncionario(new FuncionarioDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("funcionario_codigo"))));


                logs.add(log);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return logs;
    }
    public Log get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("Log",null,"cod=?",new String[] { String.valueOf(id) },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Log log = new Log(null,null);
        log.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
        log.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        log.setData(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("data"))));
        log.setFuncionario(new FuncionarioDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("funcionario_codigo"))));
        return log;
    }
}
