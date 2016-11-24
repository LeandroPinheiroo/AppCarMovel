package com.example.leandro.appcar.AppBar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leandro.appcar.R;
import com.example.leandro.appcar.database.models.Servico;

import java.util.ArrayList;

public class ServicoAdapter extends BaseAdapter {
    private ArrayList<Servico> lista = new ArrayList<>();
    private Context context;

    public void setLista(ArrayList<Servico> lista){
        this.lista = lista;
    }

    public ServicoAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Servico itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_servicos, parent, false);
        TextView tvCod = (TextView) view.findViewById(R.id.textView);
        TextView tvDesc = (TextView) view.findViewById(R.id.textView);
        TextView tvValor = (TextView) view.findViewById(R.id.textView);
        tvCod.setText(itemLista.getCod());
        tvDesc.setText(itemLista.getDescricao());
        tvValor.setText(Double.toString(itemLista.getValor()));
        return view;
    }
}
