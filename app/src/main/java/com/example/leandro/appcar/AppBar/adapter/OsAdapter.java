package com.example.leandro.appcar.AppBar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.leandro.appcar.R;

import java.util.ArrayList;

public class OsAdapter extends BaseAdapter {
    private ArrayList<String> lista = new ArrayList<String>();
    private Context context;

    public OsAdapter(Context context) {
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
        String itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_os, parent, false);

        TextView tv = (TextView) view.findViewById(R.id.textView);
        return view;
    }

}
