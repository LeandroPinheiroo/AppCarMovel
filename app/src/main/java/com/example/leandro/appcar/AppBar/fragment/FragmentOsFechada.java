package com.example.leandro.appcar.AppBar.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leandro.appcar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOsFechada#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOsFechada extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_os_fechada, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}