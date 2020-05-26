package com.example.khuisf.tools;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.view.MenuItem;

import com.example.khuisf.LoginActivity;
import com.example.khuisf.MainActivity;

import me.cheshmak.android.sdk.core.Cheshmak;

import static android.content.Context.MODE_PRIVATE;

public class MyTools {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void logout(final  Context context) {
            SessionManager manager = new SessionManager(context);
            SharedPreferences preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
            preferences.edit().putString("code", "null").apply();
            manager.setLogedIn(false);
            Cheshmak.deleteAllTags();
            preferences.edit().clear().commit();

    }
}
