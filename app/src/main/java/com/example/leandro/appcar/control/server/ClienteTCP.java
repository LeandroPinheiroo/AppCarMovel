package com.example.leandro.appcar.control.server;

import android.util.Log;

import com.example.leandro.appcar.control.rest.LoginJSON;
import com.example.leandro.appcar.model.Login;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class ClienteTCP {

    private Socket s; //socket res
    private static String ipServ = "172.16.2.155"; //ip do servidor
    private static int portaServ = 3322; //porta do servidor

    //Método construtor criando o socket, estabelecendo conexão com o servidor
    public ClienteTCP() {
        try {
            Log.d("SERVER", "IP e porta do servidor: " + ipServ + ", " + portaServ);
            s = new Socket(ipServ, portaServ);//ip: ip, p: porta
        } catch (SocketException e) {
            e.printStackTrace();
            Log.d("SERVER", "erro conexao: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SERVER", "erro conexao: " + e.getMessage());
        }
    }

    public String socketIO(String msg) {
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
            e.printStackTrace();
        }
        return msgRecebida;
    }

    public String login(Login login) {
        try {
            JSONObject json = new JSONObject();
            json.put("login", LoginJSON.preencheJSON(login));
            String a = this.socketIO(geraJSON("login", json));
            return a;
        } catch (Exception e) {
            System.out.println("DEU ERRO");
            System.out.println(e.toString());
        }
        return null;
    }

    public static String geraJSON(String request, JSONArray array) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request", request);
            jsonObject.put("array", array);
            return jsonObject.toString();
        } catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }

    public static String geraJSON(String request) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request", request);
            return jsonObject.toString();
        } catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }

    public static String geraJSON(String request, JSONObject json) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request", request);
            jsonObject.put("object", json);
            return jsonObject.toString();
        } catch (Exception e) {
            Log.d("Erro JSON", e.toString());
        }
        return null;
    }
}