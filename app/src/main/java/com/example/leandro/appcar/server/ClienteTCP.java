package com.example.leandro.appcar.server;

import android.util.Log;
import android.view.View;

import com.example.leandro.appcar.database.models.Login;
import com.example.leandro.appcar.server.rest.LoginJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.*;
import java.io.*;

public class ClienteTCP {

    private Socket s; //socket res
    private static String ipServ = ""; //ip do servidor
    private static int portaServ = 0; //porta do servidor

    //Método construtor criando o socket, estabelecendo conexão com o servidor
    public ClienteTCP() {
        try {
            Log.d("SERVER", "IP e porta do servidor: "+"192.168.0.108" + ", " + 3322);
            s = new Socket("192.168.0.108", 3322);//ip: ip, p: porta
        } catch (SocketException e) {
            e.printStackTrace();
            Log.d("SERVER", "erro conexao: "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SERVER", "erro conexao: "+e.getMessage());
        }
    }

    public String comunica(String msg) {
        String msgRecebida = "";
        try {
            //bufer de leitura
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

            //buffer de escrita
            PrintStream ps = new PrintStream(s.getOutputStream());

            //envia mensagem ao servidor
            ps.println(msg);

            //recebe mensagem resposta do servidor

            String linha;
            Boolean flag = true;
            while (flag!=false){
                linha = br.readLine();
                if(!linha.equals("null")){
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
            Log.d("SERVER", "erro no envio: "+e.getMessage());
            e.printStackTrace();
        }
        return msgRecebida;
    }

    public JSONArray onClickConecta() {
        //para usar um socket é necessário abrir uma nova thread no android
        final String resposta = null;
        try {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    //conecta ao servidor tcp
                    ClienteTCP c = new ClienteTCP();

                    //envia o texto para o servidor e recebe na variavel textoResposta
                    String msgEnvio = "";
                    Log.d("[SERVER]msgEnvio", msgEnvio);

                    final String textoResposta = c.comunica(msgEnvio);

                    resposta.concat(textoResposta);

                    //printa resposta no log do android
                    Log.d("[SERVER]msgResposta", textoResposta);
                }
            });
            t.start();
        } catch (Exception x) {
            x.printStackTrace();
        }

        try {
            return new JSONArray(resposta);
        }catch (Exception e){
            Log.d("Erro", e.toString());
        }
        return null;
    }

    public String login(Login login){
        try{
            return this.comunica(geraJSON("login",new JSONObject().put("login",LoginJSON.preencheJSON(login))));
        }catch (Exception e) {
        }
        return null;
    }

    private String geraJSON(String request, JSONArray array){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request",request);
            jsonObject.put("object",array);
            return jsonObject.toString();
        }catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }

    private String geraJSON(String request){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request",request);
            return jsonObject.toString();
        }catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }

    private String geraJSON(String request, JSONObject json){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request",request);
            jsonObject.put("object",json);
            return jsonObject.toString();
        }catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }
}