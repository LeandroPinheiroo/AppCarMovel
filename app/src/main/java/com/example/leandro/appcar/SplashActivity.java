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

        cod_login = getIntent().getIntExtra("cod_login",0);

        new InsertAsync().execute("");
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("cod_login",cod_login);
                startActivity(intent);
                finish();
            }
        }, tempoSplash);

    }

    protected static void populate() {
        new EnderecoDao(ctx).populateSocket();
        new PessoaDao(ctx).populateSocket();
        new ClienteDao(ctx).populateSocket();
        new LoginDao(ctx).populateSocket();
        new FuncionarioDao(ctx).populateSocket();
        new CarroDao(ctx).populateSocket();
        new OrdemServicoDao(ctx).populateSocket();
        new ServicoDao(ctx).populateSocket();
        new Servico_OSDao(ctx).populateSocket();
        System.gc();
    }

    class InsertAsync extends AsyncTask<String, String, String> {
        //método executado antes do método da segunda thread doInBackground
        @Override
        protected void onPreExecute() {
        }

        //método que será executado em outra thread
        @Override
        protected String doInBackground(String... args) {
            SplashActivity.populate();
            return "";
        }

        //método executado depois da thread do doInBackground
        @Override
        protected void onPostExecute(String retorno) {
        }
    }
}


