package com.example.muhammadworkstation.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Firebase mRef;
    ListView listView;
    EditText editText;
    ImageButton sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i =new Intent(this,LoginActivity.class);
        startActivity(i);

        editText= (EditText) findViewById(R.id.editText);
        listView= (ListView) findViewById(R.id.listView);
        sendButton= (ImageButton) findViewById(R.id.sendButton);



        mRef=new Firebase("https://resplendent-heat-981.firebaseio.com/condition");

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId== EditorInfo.IME_ACTION_SEND){
                    sendMessage();

                }
                return true;
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
                editText.setText("");
            }
        });



    }

    private void sendMessage() {
        String message=editText.getText().toString();
        Random random=new Random();
        if (!message.equals("")){
            String author=getAuthor();
            ChatMessage cm=new ChatMessage(author,message);
            mRef.push().setValue(cm);
        }
    }

    private String getAuthor() {

         AuthData authData= mRef.getAuth();
        if (authData!=null){
            return authData.getUid();
        }
        return "no";
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
        }

        return super.onOptionsItemSelected(item);
    }
}
