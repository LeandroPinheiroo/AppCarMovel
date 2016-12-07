package com.example.leandro.appcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leandro.appcar.control.Util;
import com.example.leandro.appcar.control.persistence.Remember_MeDao;
import com.example.leandro.appcar.control.rest.LoginJSON;
import com.example.leandro.appcar.control.server.ConnectorSocket;
import com.example.leandro.appcar.model.Login;
import com.example.leandro.appcar.model.Remember_Me;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private JSONObject resposta;
    private Remember_MeDao remember_meDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        remember_meDAO = new Remember_MeDao(this.getApplicationContext());

        if (remember_meDAO.getAll().size() > 0) {
            Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
            intent.putExtra("cod_login", remember_meDAO.getAll().get(0).getCod_login());
            startActivity(intent);
            finish();
        }

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);


    }


    public void login(String email, String password) {
        Login login = new Login();
        login.setSenha(mPasswordView.getText().toString());
        login.setUsuario(mEmailView.getText().toString());
        try {
            JSONObject json = new JSONObject();
            json.put("login", LoginJSON.preencheJSON(login));
            String a = new ConnectorSocket().execute(Util.geraJSON("login", json)).get();
            System.out.println(a);
            resposta = new JSONObject(a);

            switch (resposta.getString("return")) {
                case "success":
                    Remember_MeDao remember_meDao = new Remember_MeDao(getApplicationContext());
                    remember_meDao.truncate();
                    remember_meDao.save(new Remember_Me(resposta.getInt("cod_login")));

                    Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                    intent.putExtra("cod_login", resposta.getInt("cod_login"));
                    startActivity(intent);
                    finish();
                    break;
                case "password_incorrect":
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    break;
                case "user_not_found":
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    mEmailView.requestFocus();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            login(email, password);
        }
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


}

