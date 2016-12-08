package com.example.leandro.appcar.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leandro.appcar.MainActivity;
import com.example.leandro.appcar.R;
import com.example.leandro.appcar.control.persistence.CarroDao;
import com.example.leandro.appcar.model.Carro;
import com.example.leandro.appcar.model.OrdemServico;
import com.example.leandro.appcar.view.fragment.navigation.FragmentNavigationManager;
import com.example.leandro.appcar.view.fragment.navigation.NavigationManager;

import java.util.ArrayList;

public class OrdemServicoAdapter extends BaseAdapter {
    private ArrayList<OrdemServico> lista = new ArrayList<>();
    private Context context;
    private NavigationManager mNavigationManager;

    public OrdemServicoAdapter(Context context) {
        super();
        this.context = context;
        mNavigationManager = FragmentNavigationManager.obtain((MainActivity) context);
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
       final OrdemServico itemLista = lista.get(position);
        Carro carro = new CarroDao(this.context).get(itemLista.getCarro());

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_os, parent, false);

        final TextView tvCod = (TextView) view.findViewById(R.id.textValorCod);
        TextView tvData = (TextView) view.findViewById(R.id.textValorData);
        TextView tvMarca = (TextView) view.findViewById(R.id.textCarMarca);
        TextView tvModelo = (TextView) view.findViewById(R.id.textCarModelo);
        TextView tvPlaca = (TextView) view.findViewById(R.id.textValorPlaca);

        tvCod.setText(String.valueOf(itemLista.getCod()));
        tvData.setText(itemLista.getData().toString());
        tvMarca.setText(carro.getMarca());
        tvModelo.setText(carro.getModelo());
        tvPlaca.setText(carro.getPlaca());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationManager.showFragmentOs(itemLista.getCod());
            }
        });

        return view;

    }






}
