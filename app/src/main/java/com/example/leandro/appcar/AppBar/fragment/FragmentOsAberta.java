package com.example.leandro.appcar.AppBar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.leandro.appcar.AppBar.adapter.OrdemServicoAdapter;
import com.example.leandro.appcar.R;
import com.example.leandro.appcar.database.models.OrdemServico;
import com.example.leandro.appcar.database.persistence.OrdemServicoDAO;

import java.util.ArrayList;


public class FragmentOsAberta extends Fragment {
    private ListView listView;
    private ArrayList<OrdemServico> lista = new ArrayList<>();
    private OrdemServicoAdapter adapter;

    public FragmentOsAberta() {
        // Required empty public constructor
    }

    public static FragmentOsAberta newInstance() {
        FragmentOsAberta fragmentAction = new FragmentOsAberta();
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
        return inflater.inflate(R.layout.fragment_os_aberta, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (OrdemServico os:  new OrdemServicoDAO(this.getContext()).getAll()){
            if (os.getSituacao() == 2){
                lista.add(os);
            }
        }

        listView = (ListView) this.getView().findViewById(R.id.listOrcamentos);
        adapter = new OrdemServicoAdapter(this.getContext());
        adapter.setLista(lista);

    }
}
