package com.example.leandro.appcar.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.leandro.appcar.R;
import com.example.leandro.appcar.control.persistence.OrdemServicoDao;
import com.example.leandro.appcar.model.OrdemServico;
import com.example.leandro.appcar.view.adapter.OrdemServicoAdapter;

import java.util.ArrayList;

public class FragmentOsFechada extends Fragment {
    private ListView listView;
    private ArrayList<OrdemServico> lista = new ArrayList<>();
    private OrdemServicoAdapter adapter;


    public FragmentOsFechada() {
        // Required empty public constructor
    }


    public static FragmentOsFechada newInstance() {
        FragmentOsFechada fragmentAction = new FragmentOsFechada();
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
        View view = inflater.inflate(R.layout.fragment_os_fechada, container, false);
        listView = (ListView) view.findViewById(R.id.listOsFechada);
        for (OrdemServico os : new OrdemServicoDao(this.getContext()).getAll()) {
            if (os.getSituacao() == 3 || os.getSituacao() == 0 ) {
                lista.add(os);
            }
        }
        adapter = new OrdemServicoAdapter(this.getContext());
        adapter.setLista(lista);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
