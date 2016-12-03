package com.example.leandro.appcar.server.rest;

import com.example.leandro.appcar.database.models.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClienteJSON {

    public static Cliente getClienteJSON(JSONObject json) {
        //instancia vetor de clientes
        Cliente cliente = new Cliente();
        try {
            //pega do json os registros da tag cliente
            JSONArray vetor = (JSONArray) json.get("cliente");
            JSONObject object = (JSONObject) vetor.get(0);
            cliente.setCodigo(object.getInt("codigo"));
            cliente.setNome(object.getString("nome"));
            cliente.setCpf(object.getString("cpf"));
            cliente.setEmail(object.getString("email"));
            cliente.setEndereco(EnderecoJSON.getEnderecoJSON(object.getJSONObject("endereco")));
            cliente.setRg(object.getString("rg"));
            cliente.setSexo(object.getString("sexo"));
            cliente.setTelefoneF(object.getString("telefoneF"));
            cliente.setTelefoneM(object.getString("telefoneM"));
        } catch (Exception x) {
        }
        return cliente;
    }

    public static String geraJSONClientes(ArrayList<Cliente> clientes) {
        ArrayList<JSONObject> tabelaClientes = new ArrayList<>();
        JSONObject registro;
        //cria um registro primeiro
        for (Cliente cliente : clientes) {
            registro = preencheJSON(cliente);

            //adiciona registro à lista de registros
            tabelaClientes.add(registro);
        }

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("cliente", (Object) tabelaClientes);
        } catch (JSONException u) {
        }
        return UtilJSON.limpaJSON(bd);
    }

    public static String geraJSONCliente(Cliente cliente) {
        return UtilJSON.limpaJSON(preencheJSON(cliente));
    }

    public static JSONObject preencheJSON(Cliente cliente) {
        JSONObject registro = new JSONObject();
        try {
            registro.put("codigo", cliente.getCodigo());
            registro.put("nome", cliente.getNome());
            registro.put("cpf", cliente.getCpf());
            registro.put("rg", cliente.getRg());
            registro.put("telefoneM", cliente.getTelefoneM());
            registro.put("telefoneF", cliente.getTelefoneF());
            registro.put("email", cliente.getEmail());
            registro.put("sexo", cliente.getSexo());
            registro.put("endereco", EnderecoJSON.preencheJSON(cliente.getEndereco()));
        } catch (JSONException k) {
        }
        return null;
    }

}
