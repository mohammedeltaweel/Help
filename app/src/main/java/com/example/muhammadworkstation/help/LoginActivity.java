package com.example.muhammadworkstation.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {


    EditText email,password;
    Button loginButton, signUpButton;
    Firebase ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ref=new Firebase("https://resplendent-heat-981.firebaseio.com/help");

        email = (EditText) findViewById(R.id.loginEmail);
        password= (EditText) findViewById(R.id.loginPassword);
        loginButton= (Button) findViewById(R.id.loginButton);
        signUpButton= (Button) findViewById(R.id.logSignUpButton);

        if (PrefUtil.thereIsPref(this,PrefUtil.EMAIL_USER_KEY)){
            email.setVisibility(View.INVISIBLE);
            password.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            signUpButton.setVisibility(View.INVISIBLE);

            authorize(PrefUtil.getSavedPref(LoginActivity.this,PrefUtil.EMAIL_USER_KEY),PrefUtil.getSavedPref(LoginActivity.this,PrefUtil.PASSWORD_USER_KEY));


        }else {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    authorize(email.getText().toString(),password.getText().toString());
                }
            });

        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });








    }

    private void authorize(String email,String passwrod){

        ref.authWithPassword(email, passwrod, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(LoginActivity.this,"wrong email or password",Toast.LENGTH_LONG).show();
            }
        });
    }




}
