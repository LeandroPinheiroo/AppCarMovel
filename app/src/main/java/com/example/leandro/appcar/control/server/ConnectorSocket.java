package com.example.leandro.appcar.control.server;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ConnectorSocket extends AsyncTask<String, Void, String> {

    private Socket s; //socket res
    private static String ipServ = "172.16.2.148"; //ip do servidor
    private static int portaServ = 3322; //porta do servidor


    @Override
    protected String doInBackground(String... params) {
        String msgRecebida = "";
            try {
                s = new Socket(ipServ, portaServ);

                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintStream ps = new PrintStream(s.getOutputStream());


                ps.println(params[0]);

                //recebe mensagem resposta do servidor

                String linha;
                Boolean flag = true;
                while (flag != false) {
                    linha = br.readLine();
                    if (linha != null && !linha.equals("null")) {
                        msgRecebida += linha;
                    } else {
                        flag = false;
                    }
                }

                //retorna o que foi feito no servidor
                System.out.println(msgRecebida);
                //fecha conexão
                s.close();
            } catch (Exception e) {
                Log.d("SERVER", "erro no envio: " + e.getMessage());
                Toast.makeText(null,"Erro na conexão com o servidor", Toast.LENGTH_LONG).show();
            }
        return msgRecebida;
    }

}