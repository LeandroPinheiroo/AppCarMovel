package com.example.leandro.appcar.AppBar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leandro.appcar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOs extends Fragment {


    public FragmentOs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment FragmentAction.
     */
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
