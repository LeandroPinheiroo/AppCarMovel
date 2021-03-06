package com.example.leandro.appcar.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.leandro.appcar.R;
import com.example.leandro.appcar.control.persistence.ServicoDao;
import com.example.leandro.appcar.model.Servico;
import com.example.leandro.appcar.view.adapter.ServicoAdapter;

import java.util.ArrayList;

public class FragmentServicos extends Fragment {
    private ListView listView;
    private ArrayList<Servico> lista = new ArrayList<>();
    private ServicoAdapter adapter;

    public FragmentServicos() {

    }

    public static FragmentServicos newInstance() {
        FragmentServicos fragmentAction = new FragmentServicos();
        Bundle args = new Bundle();
        fragmentAction.setArguments(args);
        return fragmentAction;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicos, container, false);
        adapter = new ServicoAdapter(this.getContext());
        adapter.setLista((ArrayList<Servico>) new ServicoDao(this.getContext()).getAll());
        listView = (ListView) view.findViewById(R.id.listServicos);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
