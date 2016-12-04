package com.example.leandro.appcar.control.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leandro.appcar.control.SQLiteConnector;
import com.example.leandro.appcar.control.rest.CarroJSON;
import com.example.leandro.appcar.control.server.ClienteTCP;
import com.example.leandro.appcar.model.Carro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarroDao {

    private SQLiteConnector connector;
    private Context context;

    public CarroDao(Context context) {
        this.connector = new SQLiteConnector(context);
        this.context = context;
    }


    public long save(Carro carro) {
        long i;
        SQLiteDatabase db = connector.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cod", carro.getCod());
        values.put("marca", carro.getMarca());
        values.put("modelo", carro.getModelo());
        values.put("cor", carro.getCor());
        values.put("ano", carro.getAno());
        values.put("chassi", carro.getChassi());
        values.put("km", carro.getKm());
        values.put("placa", carro.getPlaca());
        values.put("obs", carro.getObs());
        values.put("dono_codigo", carro.getDono());

        if (carro.getCod() != 0) {
            i = db.update("carro", values, "cod = ?", new String[]{String.valueOf(carro.getCod())});
        } else {
            i = db.insert("carro", null, values);
        }

        db.close();
        return i;
    }


    public void remove(Carro carro) {
        SQLiteDatabase db = connector.getWritableDatabase();
        db.delete("carro", "cod = ?", new String[]{String.valueOf(carro.getCod())});
        db.close();
    }

    public List<Carro> getAll() {
        SQLiteDatabase db = connector.getReadableDatabase();
        List<Carro> carros = new ArrayList<>();
        Cursor cursor = db.query("carro", null, null, null, null, null, null);

        if (cursor != null) {
            try {
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
                        carro.setDono(cursor.getInt(cursor.getColumnIndex("dono_codigo")));
                        carros.add(carro);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return carros;
    }

    public Carro get(int id) {
        SQLiteDatabase db = connector.getReadableDatabase();

        Cursor cursor = db.query("Carro", null, "cod=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Carro carro = new Carro();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    carro.setCod(cursor.getInt(cursor.getColumnIndex("cod")));
                    carro.setMarca((cursor.getString(cursor.getColumnIndex("marca"))));
                    carro.setModelo((cursor.getString(cursor.getColumnIndex("modelo"))));
                    carro.setCor((cursor.getString(cursor.getColumnIndex("cor"))));
                    carro.setAno((cursor.getString(cursor.getColumnIndex("ano"))));
                    carro.setChassi((cursor.getString(cursor.getColumnIndex("chassi"))));
                    carro.setKm((cursor.getString(cursor.getColumnIndex("km"))));
                    carro.setPlaca((cursor.getString(cursor.getColumnIndex("placa"))));
                    carro.setObs((cursor.getString(cursor.getColumnIndex("obs"))));
                    carro.setDono(cursor.getInt(cursor.getColumnIndex("dono_codigo")));
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return carro;

    }

    public void truncate() {
        SQLiteDatabase db = connector.getWritableDatabase();
        if (this.getAll().size() > 0) {
            db.delete("carro", null, null);
        }
        db.close();
    }

    public void populateSocket() {
        this.truncate();
        try {
            JSONArray array = new JSONObject(new ClienteTCP().socketIO(ClienteTCP.geraJSON("get_Carro_All"))).getJSONObject("return").getJSONArray("carro");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i));
                this.save(CarroJSON.getCarroJSON(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

