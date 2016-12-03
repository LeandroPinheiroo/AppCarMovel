package com.example.leandro.appcar.appbar.fragment.navigation;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.leandro.appcar.appbar.fragment.FragmentMain;
import com.example.leandro.appcar.appbar.fragment.FragmentOrcamento;
import com.example.leandro.appcar.appbar.fragment.FragmentOs;
import com.example.leandro.appcar.appbar.fragment.FragmentOsAberta;
import com.example.leandro.appcar.appbar.fragment.FragmentOsFechada;
import com.example.leandro.appcar.appbar.fragment.FragmentServicos;
import com.example.leandro.appcar.appbar.fragment.FragmentServicosRealizados;
import com.example.leandro.appcar.BuildConfig;
import com.example.leandro.appcar.MainActivity;
import com.example.leandro.appcar.R;


/**
 * @author msahakyan
 */

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private MainActivity mActivity;

    public static FragmentNavigationManager obtain(MainActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(MainActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragmentOrcamento() {
        showFragment(FragmentOrcamento.newInstance(), false);
    }
    @Override
    public void showFragmentOs() {showFragment(FragmentOs.newInstance(), false);}
    @Override
    public void showFragmentOsAberta() {
        showFragment(FragmentOsAberta.newInstance(), false);
    }
    @Override
    public void showFragmentOsFechada() {
        showFragment(FragmentOsFechada.newInstance(), false);
    }
    @Override
    public void showFragmentServicos() {
        showFragment(FragmentServicos.newInstance(), false);
    }
    @Override
    public void showFragmentServicosRealizados() {
        showFragment(FragmentServicosRealizados.newInstance(), false);
    }
    @Override
    public void showFragmentMain() {
        showFragment(FragmentMain.newInstance(), false);
    }





    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = mFragmentManager;

        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction()
            .replace(R.id.container, fragment);

        ft.addToBackStack(null);

        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }
}
