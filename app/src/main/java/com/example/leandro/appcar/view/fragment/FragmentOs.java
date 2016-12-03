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

public class FragmentOs extends Fragment {
    private ListView listView;
    private ArrayList<Servico> lista = new ArrayList<>();
    private ServicoAdapter adapter;

    public FragmentOs() {
        // Required empty public constructor
    }

    public static FragmentOs newInstance() {
        FragmentOs fragmentAction = new FragmentOs();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordem_servico, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) this.getView().findViewById(R.id.listOrcamentos);
        adapter = new ServicoAdapter(this.getContext());
        adapter.setLista((ArrayList<Servico>) new ServicoDao(this.getContext()).getAll());
    }

}
