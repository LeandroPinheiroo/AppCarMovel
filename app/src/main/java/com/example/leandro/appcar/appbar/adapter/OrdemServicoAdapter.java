package com.example.leandro.appcar.appbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.leandro.appcar.R;
import com.example.leandro.appcar.database.models.OrdemServico;

import java.util.ArrayList;

public class OrdemServicoAdapter extends BaseAdapter {
    private ArrayList<OrdemServico> lista = new ArrayList<>();
    private Context context;

    public OrdemServicoAdapter(Context context) {
        super();
        this.context = context;
    }

    public ArrayList<OrdemServico> getLista() {
        return lista;
    }

    public void setLista(ArrayList<OrdemServico> lista) {
        this.lista = lista;
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
        com.example.leandro.appcar.database.models.OrdemServico itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_os, parent, false);
        TextView tvCod = (TextView) view.findViewById(R.id.textValorCod);
        TextView tvMarca = (TextView) view.findViewById(R.id.textCarMarca);
        TextView tvModelo = (TextView) view.findViewById(R.id.textCarModelo);
        TextView tvData = (TextView) view.findViewById(R.id.textValorData);
        TextView tvPlca = (TextView) view.findViewById(R.id.textValorPlaca);
        return view;
    }

}
