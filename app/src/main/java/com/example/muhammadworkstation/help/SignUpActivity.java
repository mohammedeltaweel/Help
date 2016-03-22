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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    EditText email,password;
    Button signUpButton;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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


        email= (EditText) findViewById(R.id.loginEmail);
        password= (EditText) findViewById(R.id.loginPassword);
        signUpButton= (Button) findViewById(R.id.signUpButton);
        ref=new Firebase("https://resplendent-heat-981.firebaseio.com/help");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().contains("@")||!password.getText().toString().equals("")){
                    ref.createUser(email.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {

                            Intent i=new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {

                        }
                    });
                }
            }
        });
    }

}
