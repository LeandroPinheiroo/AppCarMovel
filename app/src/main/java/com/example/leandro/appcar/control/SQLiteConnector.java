package com.example.leandro.appcar.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteConnector extends SQLiteOpenHelper {

    public static final String DB_NAME = "appcar";
    public static final int DB_VERSION = 1;

    public SQLiteConnector(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        dump(sqLiteDatabase, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*
        if (DB_VERSION > i1) {
            dump(sqLiteDatabase, i);
        }
        */
    }

    private void dump(SQLiteDatabase database, int version) {
        database.execSQL(
                "create table if not exists login(" +
                        " cod integer primary key," +
                        " senha text," +
                        " usuario text" +
                        ");");
        database.execSQL(
                "create table if not exists endereco(" +
                        " cod integer primary key," +
                        " bairro text," +
                        " cep text," +
                        " cidade text," +
                        " complemento text," +
                        " numero text," +
                        " rua text" +
                        ");");
        database.execSQL(
                "create table if not exists servico_os(" +
                        " cod integer primary key," +
                        " funcionario_codigo integer," +
                        " ordemservico_cod integer," +
                        " servico_cod integer" + ");");
        database.execSQL(
                "create table if not exists funcionario(" +
                        " codigo integer," +
                        " login_cod integer" + ");");
        database.execSQL(
                "create table if not exists pessoa(" +
                        " tipo text," +
                        " codigo integer primary key," +
                        " cpf text," +
                        " email text," +
                        " nome text," +
                        " rg text," +
                        " sexo text," +
                        " telefoneF text," +
                        " telefoneM text," +
                        " endereco_cod integer" + ");");
        database.execSQL(
                "create table if not exists cliente(" +
                        " codigo integer" + ");");
        database.execSQL(
                "create table if not exists carro(" +
                        " cod integer primary key," +
                        " ano text," +
                        " chassi text," +
                        " cor text," +
                        " km text," +
                        " marca text," +
                        " modelo text," +
                        " obs text," +
                        " placa text," +
                        " dono_codigo integer" + ");");
        database.execSQL(
                "create table if not exists servico(" +
                        " cod integer primary key," +
                        " descricao text," +
                        " valor double" + ");");
        database.execSQL(
                "create table if not exists ordemservico(" +
                        " cod integer primary key," +
                        " tipo text," +
                        " data text," +
                        " situacao integer," +
                        " cliente_codigo integer," +
                        " carro_cod integer," +
                        " descricao integer" + ");");
        database.execSQL(
                "create table if not exists log(" +
                        " cod integer," +
                        " descricao text," +
                        " data text," +
                        " funcionario_cod integer" + ");"
        );
        database.execSQL(
                "create table if not exists remember_me(" +
                        " cod_login integer )"
        );
        database.close();
        Log.d("DB", "Criado");
    }
}
