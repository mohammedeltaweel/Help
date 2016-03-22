package com.example.muhammadworkstation.help;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Muhammad Workstation on 20/03/2016.
 */
public class PrefUtil {


    public static final String EMAIL_USER_KEY = "USERNAME";
    public static final String PASSWORD_USER_KEY = "PASSWORD";


    public static void saveToPref(Context c, String key, String value) {
        SharedPreferences pref = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();

    }


    public static String getSavedPref(Context c, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        if (preferences.contains(key)) {
            return preferences.getString(key, null);
        } else return null;
    }

    public static boolean thereIsPref(Context c, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);

        if (preferences.contains(key)) {
            return true;

        }else {
            return false;
        }
    }
}
