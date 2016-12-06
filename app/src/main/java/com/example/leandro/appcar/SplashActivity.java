package com.example.leandro.appcar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.leandro.appcar.control.persistence.CarroDao;
import com.example.leandro.appcar.control.persistence.ClienteDao;
import com.example.leandro.appcar.control.persistence.EnderecoDao;
import com.example.leandro.appcar.control.persistence.FuncionarioDao;
import com.example.leandro.appcar.control.persistence.LoginDao;
import com.example.leandro.appcar.control.persistence.OrdemServicoDao;
import com.example.leandro.appcar.control.persistence.PessoaDao;
import com.example.leandro.appcar.control.persistence.ServicoDao;
import com.example.leandro.appcar.control.persistence.Servico_OSDao;
import com.example.leandro.appcar.model.Funcionario;

public class SplashActivity extends AppCompatActivity {

    private ImageView splash;
    private AnimationDrawable splashAnimation;
    private static Context ctx;
    private int cod_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        splash = (ImageView) findViewById(R.id.ivAnimacao);
        splash.setBackgroundResource(R.drawable.splash_animation);

        ctx = this.getApplicationContext();

        cod_login = getIntent().getIntExtra("cod_login", 0);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        splashAnimation = (AnimationDrawable) splash.getBackground();
        if (hasFocus) {
            splashAnimation.start();
        } else {
            splashAnimation.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int tempoSplash = 5000;

        this.populate();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("cod_login", cod_login);
                startActivity(intent);
                finish();
            }
        }, tempoSplash);

    }

    public void populate() {
        new EnderecoDao(ctx).populateSocket();
        new PessoaDao(ctx).populateSocket();
        new ClienteDao(ctx).populateSocket();
        new LoginDao(ctx).populateSocket();
        new FuncionarioDao(ctx).populateSocket();
        new CarroDao(ctx).populateSocket();
        new OrdemServicoDao(ctx).populateSocket();
        new ServicoDao(ctx).populateSocket();
        new Servico_OSDao(ctx).populateSocket();
    }

}


