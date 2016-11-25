package com.example.leandro.appcar.database.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.database.SQLiteConnector;
import com.example.leandro.appcar.database.models.Carro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alunos on 22/11/16.
 */
public class CarroDAO {

    private SQLiteConnector connector;
    private Context context;


    public CarroDAO(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Carro carro) {
        SQLiteDatabase database = connector.getWritableDatabase();
        Integer identifier = carro.getCod();
        ContentValues values = new ContentValues();
        values.put("marca", carro.getMarca());
        values.put("modelo", carro.getModelo());
        values.put("cor", carro.getCor());
        values.put("ano", carro.getAno());
        values.put("chassi", carro.getChassi());
        values.put("km", carro.getKm());
        values.put("placa", carro.getPlaca());
        values.put("obs", carro.getObs());
        values.put("dono_codigo",carro.getDono().getCodigo());

        if (identifier != 0) {
            return database.update("carro", values, "cod = ?", new String[]{String.valueOf(identifier)});
        } else {
            return database.insert("carro", null, values);
        }
    }


    public int remove(Carro carro) {
        SQLiteDatabase database = connector.getWritableDatabase();

        return database.delete("carro", "cod = ?", new String[] { String.valueOf(carro.getCod()) });
    }

    public List<Carro> getAll() {
        SQLiteDatabase database = connector.getReadableDatabase();

        List<Carro> carros = new ArrayList<>();

        Cursor cursor = database.query("carro", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Carro carro = new Carro();
                carro.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                carro.setMarca((cursor.getString(cursor.getColumnIndex("marca"))));
                carro.setModelo((cursor.getString(cursor.getColumnIndex("modelo"))));
                carro.setCor((cursor.getString(cursor.getColumnIndex("cor"))));
                carro.setAno((cursor.getString(cursor.getColumnIndex("ano"))));
                carro.setChassi((cursor.getString(cursor.getColumnIndex("chassi"))));
                carro.setKm((cursor.getString(cursor.getColumnIndex("km"))));
                carro.setPlaca((cursor.getString(cursor.getColumnIndex("placa"))));
                carro.setObs((cursor.getString(cursor.getColumnIndex("obs"))));
                carro.setDono(new ClienteDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("dono_codigo"))));
                carros.add(carro);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return carros;
    }

    public Carro get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("Carro",null,"cod=?",new String[] { String.valueOf(id) },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Carro carro = new Carro();
        carro.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
        carro.setMarca((cursor.getString(cursor.getColumnIndex("marca"))));
        carro.setModelo((cursor.getString(cursor.getColumnIndex("modelo"))));
        carro.setCor((cursor.getString(cursor.getColumnIndex("cor"))));
        carro.setAno((cursor.getString(cursor.getColumnIndex("ano"))));
        carro.setChassi((cursor.getString(cursor.getColumnIndex("chassi"))));
        carro.setKm((cursor.getString(cursor.getColumnIndex("km"))));
        carro.setPlaca((cursor.getString(cursor.getColumnIndex("placa"))));
        carro.setObs((cursor.getString(cursor.getColumnIndex("obs"))));
        carro.setDono(new ClienteDAO(this.context).get(cursor.getInt(cursor.getColumnIndex("dono_codigo"))));
        return carro;
    }
}
