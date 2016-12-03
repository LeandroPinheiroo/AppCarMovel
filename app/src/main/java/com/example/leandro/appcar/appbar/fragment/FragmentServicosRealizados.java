package com.example.leandro.appcar.appbar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leandro.appcar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentServicosRealizados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentServicosRealizados extends Fragment {


    public FragmentServicosRealizados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment FragmentAction.
     */
    public static FragmentServicosRealizados newInstance() {
        FragmentServicosRealizados fragmentAction = new FragmentServicosRealizados();
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
        return inflater.inflate(R.layout.fragment_servicos_realizados, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
