package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Serialization.readSerializable(getApplicationContext());
        if(Serialization.serialization != null)
            ProgressBar();

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Serialization.serialization = null;
                ProgressBar();
            }
        });
    }

    private void ProgressBar()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (! (netInfo!= null && netInfo.isConnectedOrConnecting()))
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setMessage("Conexiune la internet inexistenta.").setNegativeButton("Inapoi",null).create().show();
            return;
        }

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Va rugam asteptati.");
        progressDialog.setTitle("Incarcare date");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                if(Serialization.serialization == null)
                    LogIn();
                else
                    Refresh.LogIn(LoginActivity.this,Serialization.serialization.email,Serialization.serialization.password);
                    progressDialog.dismiss();
            }
        }).start();
    }


    private void LogIn() {

        EditText mEmailView = (EditText) findViewById(R.id.email);
        EditText mPasswordView = (EditText) findViewById(R.id.password);
        String mEmail = mEmailView.getText().toString();
        String mPassword = mPasswordView.getText().toString();

        new Serialization(mEmail, mPassword);

        Refresh.LogIn(LoginActivity.this, mEmail, mPassword);
    }
}

