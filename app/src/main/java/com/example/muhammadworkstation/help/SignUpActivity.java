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

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    EditText email,password;
    Button signUpButton;
    Firebase ref;
    LocationHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler=new LocationHandler(this,this);

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
        ref=new Firebase("https://muhammadhelp.firebaseio.com");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().contains("@")||!password.getText().toString().equals("")){
                    ref.createUser(email.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {


                            PrefUtil.saveToPref(SignUpActivity.this,PrefUtil.EMAIL_USER_KEY,email.getText().toString());
                            PrefUtil.saveToPref(SignUpActivity.this,PrefUtil.PASSWORD_USER_KEY,password.getText().toString());
                            Login();

                            if (ref.getAuth()!=null) {

                            }else {
                                Log.i("Auth ","auth is equal null");
                            }

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.locationConnect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.locationDisconnect();
    }

    private void Login() {
        ref.authWithPassword(email.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Map<String, Object> usersInfo = new HashMap<String, Object>();
                if (ref.getAuth()!=null) {

                    String url="user_"+ref.getAuth().getUid();
                    usersInfo.put( "Longitude", String.valueOf(handler.getLong()));
                    usersInfo.put( "Latitude", String.valueOf(handler.getLat()));
                    /*ref.updateChildren(usersInfo);*/
                    ref.child("users").child(url).child("Location").setValue(usersInfo);
                }

                Intent i=new Intent(SignUpActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {

            }
        });
    }


}
