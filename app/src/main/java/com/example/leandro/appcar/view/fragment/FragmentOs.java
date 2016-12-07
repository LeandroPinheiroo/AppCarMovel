package com.example.leandro.appcar.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.leandro.appcar.R;
import com.example.leandro.appcar.control.persistence.ServicoDao;
import com.example.leandro.appcar.model.OrdemServico;
import com.example.leandro.appcar.model.Servico;
import com.example.leandro.appcar.view.adapter.ServicoAdapter;

import java.util.ArrayList;

public class FragmentOs extends Fragment {
    private ListView listView;
    private OrdemServico os;
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
        View view = inflater.inflate(R.layout.fragment_ordem_servico, container, false);
        listView = (ListView) this.getView().findViewById(R.id.listOrcamentos);
        adapter = new ServicoAdapter(this.getContext());
        adapter.setLista((ArrayList<Servico>) new ServicoDao(this.getContext()).getAll());
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
