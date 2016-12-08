package com.example.leandro.appcar.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leandro.appcar.R;
import com.example.leandro.appcar.control.persistence.CarroDao;
import com.example.leandro.appcar.control.persistence.ClienteDao;
import com.example.leandro.appcar.control.persistence.OrdemServicoDao;
import com.example.leandro.appcar.control.persistence.ServicoDao;
import com.example.leandro.appcar.control.persistence.Servico_OSDao;
import com.example.leandro.appcar.model.OrdemServico;
import com.example.leandro.appcar.model.Servico;
import com.example.leandro.appcar.model.Servico_OS;
import com.example.leandro.appcar.view.adapter.ServicoAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentOs extends Fragment {
    private ListView listView;
    private OrdemServico os;
    private ServicoAdapter adapter;
    private int cod;

    public FragmentOs() {
        // Required empty public constructor
    }

    public static FragmentOs newInstance(int i) {
        FragmentOs fragmentAction = new FragmentOs();
        Bundle args = new Bundle();
        args.putInt("cod", i);
        fragmentAction.setArguments(args);
        return fragmentAction;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cod = getArguments().getInt("cod", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordem_servico, container, false);

        OrdemServico os = new OrdemServicoDao(getContext()).get(cod);
        ServicoDao serDao = new ServicoDao(getContext());
        ClienteDao cliDao = new ClienteDao(getContext());
        CarroDao carDao = new CarroDao(getContext());


        listView = (ListView) view.findViewById(R.id.listServicosOS);

        EditText textOsCodigo = (EditText) view.findViewById(R.id.textOsCodigo);
        EditText textOsData = (EditText) view.findViewById(R.id.textData);
        EditText textOsTipo = (EditText) view.findViewById(R.id.textOsTipo);
        EditText textOsDescricao = (EditText) view.findViewById(R.id.textOsDescricao);
        TextView textValor = (TextView) view.findViewById(R.id.textValorEdit);

        textOsCodigo.setText(String.valueOf(os.getCod()));
        textOsTipo.setText(String.valueOf(os.getTipo()));
        textOsDescricao.setText(os.getDescricao());
        textOsData.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(os.getData()).toString());

        EditText textCliCodigo = (EditText) view.findViewById(R.id.textCliCodigo);
        EditText textCliNome = (EditText) view.findViewById(R.id.textCliNome);
        EditText texCliCpf = (EditText) view.findViewById(R.id.texCliCpf);
        EditText textCliTelefone = (EditText) view.findViewById(R.id.textCliTelefone);
        textCliCodigo.setText(String.valueOf(cliDao.get(os.getCliente()).getCodigo()));
        textCliNome.setText(cliDao.get(os.getCliente()).getNome());
        texCliCpf.setText(cliDao.get(os.getCliente()).getCpf());
        textCliTelefone.setText(cliDao.get(os.getCliente()).getTelefoneF());

        EditText textCarCodigo = (EditText) view.findViewById(R.id.textCarCodigo);
        EditText textCarMarca = (EditText) view.findViewById(R.id.textCarMarca);
        EditText textCarModelo = (EditText) view.findViewById(R.id.textModelo);
        EditText textCarPlaca = (EditText) view.findViewById(R.id.textPlaca);
        textCarCodigo.setText(String.valueOf(carDao.get(os.getCarro()).getCod()));
        textCarMarca.setText(carDao.get(os.getCarro()).getMarca());
        textCarModelo.setText(carDao.get(os.getCarro()).getModelo());
        textCarPlaca.setText(carDao.get(os.getCarro()).getPlaca());


        adapter = new ServicoAdapter(this.getContext());
        ArrayList<Servico> servicos = new ArrayList<>();

        for (Servico_OS servico : new Servico_OSDao(this.getContext()).getAll()) {
            if (servico.getOrdemservico() == cod) {
                servicos.add(serDao.get(servico.getServico()));
            }
        }

        double valorTotal = 0;
        for(Servico servico :servicos){
            valorTotal += servico.getValor();
        }

        textValor.setText("R$"+Double.toString(valorTotal));

        adapter.setLista(servicos);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
