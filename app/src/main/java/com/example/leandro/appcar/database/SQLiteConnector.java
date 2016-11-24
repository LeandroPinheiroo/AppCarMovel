package com.example.leandro.appcar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteConnector extends SQLiteOpenHelper {

    public static final String DB_NAME = "database";
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
        Log.d("DB","Criado");
        database.execSQL(
                        "create table login("+
                         "cod integer primary key,"+
                         "senha text,"+
                          "usuario text"+
                            ");"
                        +
                        "create table endereco("+
                         "cod integer primary key," +
                         "bairro text," +
                         "cep text," +
                         "cidade text," +
                         "complemento text," +
                         "numero text," +
                         "rua text"+
                         ");"
                        +
                        "create table servico_os("+
                          "cod integer primary key,"+
                          "funcionario_codigo integer," +
                          "ordemservico_cod integer," +
                          "servico_cod"+");"
                        +
                        "create table funcionario("+
                          "codigo integer," +
                          "login_cod integer"+");"
                        +
                        "create table pessoas("+
                          "tipo text," +
                          "codigo integer primary key," +
                          "cpf text," +
                          "email text," +
                          "nome text," +
                          "rg text," +
                          "sexo text," +
                          "telefoneF text," +
                          "telefonM text," +
                          "endereco_cod integer" +");"
                        +
                        "create table cliente("+
                          "codigo integer"+");"
                        +
                        "create table carro("+
                          "cod integer primary key," +
                          "ano text," +
                          "chassi text," +
                          "cor text," +
                          "cor text," +
                          "km text," +
                          "marca text," +
                          "modelo text," +
                          "obs text," +
                          "placa text," +
                          "dono_codigo integer"+");"
                        +
                        "create table servico("+
                          "cod integer," +
                          "descricao text," +
                          "valor double"+");"


        );
    }
}
