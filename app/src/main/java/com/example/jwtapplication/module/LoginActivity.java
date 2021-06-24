package com.example.jwtapplication.module;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.example.jwtapplication.R;
import com.example.jwtapplication.dao.LoginDao;
import com.example.jwtapplication.dao.Token;
import com.example.jwtapplication.util.OkHttpUtils;

import java.util.HashMap;


public class LoginActivity extends Activity {

    EditText mAccount;
    EditText mPassword;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    ProgressDialog progressDialog;
    String ResponseString;
    LoginDao loginDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in...");
        mAccount = (EditText) findViewById(R.id.username);

        mPassword = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = String.valueOf(mPassword.getText());
                String account = String.valueOf(mAccount.getText());
                if(account.length()>0&&password.length()>0){
                    new NetWork().execute();
                }else {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("Username/Password cannot be empty!")
                            .show();
                }

            }
        });

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    class NetWork extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String password = String.valueOf(mPassword.getText());
            String account = String.valueOf(mAccount.getText());
            HashMap<String, String> map = new HashMap<>();
            map.put("username", account);
            map.put("password", password);

            try {
                ResponseString = okHttpUtils.PostResult("/login", map);
                Log.d("Test", ResponseString);

            } catch (Exception e) {

            }
            loginDao = JSON.parseObject(ResponseString, LoginDao.class);
            Log.d("Test", String.valueOf(loginDao));

            if (loginDao.success.equals("1")) {
                Token token = new Token();
                token.setUsername(account);
                token.setKey(loginDao.data.jwt);
                //token.setKey(loginDao.data);
                token.setSelectStr("TEST");
                Token.savetOKEN(token);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (loginDao.success.equals("1")) {
                Intent intent = new Intent(LoginActivity.this, Onboarding.class);
                startActivity(intent);
            } else {
                new AlertDialog.Builder(LoginActivity.this).setMessage("Incorrect username or password!")
                        .show();
            }
            progressDialog.dismiss();

        }
    }
}