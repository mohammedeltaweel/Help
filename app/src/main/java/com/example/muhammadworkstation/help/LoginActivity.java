package com.example.muhammadworkstation.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {


    EditText email,password;
    Button loginButton, signUpButton;
    Firebase ref;
    private boolean signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ref=new Firebase("https://muhammadhelp.firebaseio.com");

        Intent intent=getIntent();
        signOut= intent.getBooleanExtra(MainActivity.SIGN_OUT_ACTION_KEY,false);

        email = (EditText) findViewById(R.id.loginEmail);
        password= (EditText) findViewById(R.id.loginPassword);
        loginButton= (Button) findViewById(R.id.loginButton);
        signUpButton= (Button) findViewById(R.id.logSignUpButton);

        if (!signOut){
            authorize();
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefUtil.saveToPref(LoginActivity.this,PrefUtil.EMAIL_USER_KEY,email.getText().toString());
                PrefUtil.saveToPref(LoginActivity.this,PrefUtil.PASSWORD_USER_KEY,password.getText().toString());

                ref.authWithPassword(email.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                        Log.i("auth", "Authenticated and auth id is " + authData.getUid());
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {

                    }
                });

            }
        });



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });













    }

   private void authorize(){
       if (PrefUtil.thereIsPref(this,PrefUtil.EMAIL_USER_KEY)){
           email.setVisibility(View.INVISIBLE);
           password.setVisibility(View.INVISIBLE);
           loginButton.setVisibility(View.INVISIBLE);
           signUpButton.setVisibility(View.INVISIBLE);


           ref.authWithPassword(PrefUtil.getSavedPref(this, PrefUtil.EMAIL_USER_KEY), PrefUtil.getSavedPref(this, PrefUtil.PASSWORD_USER_KEY), new Firebase.AuthResultHandler() {
               @Override
               public void onAuthenticated(AuthData authData) {
                   Intent i = new Intent(LoginActivity.this, MainActivity.class);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(i);
               }

               @Override
               public void onAuthenticationError(FirebaseError firebaseError) {

               }
           });



       }


   }




}
