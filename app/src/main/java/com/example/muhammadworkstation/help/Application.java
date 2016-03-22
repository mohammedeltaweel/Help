package com.example.muhammadworkstation.help;

import com.firebase.client.Firebase;

/**
 * Created by Muhammad Workstation on 18/03/2016.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
