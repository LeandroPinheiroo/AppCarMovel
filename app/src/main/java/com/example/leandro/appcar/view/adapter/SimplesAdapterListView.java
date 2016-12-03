package com.example.leandro.appcar.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.leandro.appcar.R;

import java.util.ArrayList;

public class SimplesAdapterListView extends BaseAdapter {
    private ArrayList<String> lista = new ArrayList<String>();
    private ArrayList<Boolean> listaCheck = new ArrayList<Boolean>();
    private Context context;

    public void setLista(ArrayList<String> lista) {
        this.lista = lista;
    }

    public void setListaCheck(ArrayList<Boolean> listaCheck) {
        this.listaCheck = listaCheck;
    }

    public SimplesAdapterListView(Context context) {
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

    public Object getCheck(int position) {
        return listaCheck.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_simples_list_view, parent, false);
        CheckBox cb = (CheckBox) view.findViewById(R.id.cbItem);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        cb.setChecked(listaCheck.get(position));
        tv.setText(itemLista);
        cb.setTag(position);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int codigo = (int) cb.getTag();
                if (cb.isChecked()) {
                    listaCheck.set(position, true);

                } else {
                    listaCheck.set(position, false);

                }
                //String td ="";
                //for(int i =0;i<listaCheck.size();i++)
                //   td+=", "+listaCheck.get(i);
                // Toast.makeText(context, "Checbox de " + td + " ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
