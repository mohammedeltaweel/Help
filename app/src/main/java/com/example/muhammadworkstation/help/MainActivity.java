package com.example.muhammadworkstation.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity  {


    Button others;
    Firebase ref;
    LocationHandler locationHandler;
    Firebase helpChild;
    protected static final String SIGN_OUT_ACTION_KEY="signOutAction";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!PrefUtil.thereIsPref(this,PrefUtil.EMAIL_USER_KEY)){
            Intent i =new Intent(this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        locationHandler=new LocationHandler(this,this);







        ref = new Firebase("https://muhammadhelp.firebaseio.com");
        helpChild = ref.child("child");

        others= (Button) findViewById(R.id.other);
        others.setOnClickListener(otherClick);


    }





    View.OnClickListener otherClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i =new Intent(MainActivity.this,MapsActivity.class);
            startActivity(i);
        }
    };


    private void sendMessage() {
        String message="" ;
        if (!message.equals("")) {
            String author = getAuthor();
            ChatMessage cm = new ChatMessage(author, message);
            ref.push().setValue(cm);
        }
    }

    private String getAuthor() {

        AuthData authData = ref.getAuth();
        if (authData != null) {
            return authData.getUid();
        } else {
            return "no";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id==R.id.action_signOut){
            Intent i =new Intent(MainActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra(SIGN_OUT_ACTION_KEY, true);
            PrefUtil.clearUserPref(MainActivity.this);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*mGoogleApiClient.connect();*/

        locationHandler.locationConnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*mGoogleApiClient.disconnect();*/

        locationHandler.locationDisconnect();
    }


}
