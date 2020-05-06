package com.example.khuisf.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    Context context;
    SharedPreferences preferences;
    String pr_name = "prefs";
    boolean logedIn = false;
    String prl = "logedin";
    String prlforintro = "logedin_intro";


    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(pr_name, Context.MODE_PRIVATE);
    }

    public boolean isLogedIn() {
        return preferences.getBoolean(prl, false);
    }

    public boolean isLogedInforIntro() {
        return preferences.getBoolean(prlforintro, false);
    }

    public void setLogedIn(boolean logedIn) {
        preferences.edit().putBoolean(prl, logedIn).apply();
    }

    public void setLogedInforIntro(boolean logedIn) {
        preferences.edit().putBoolean(prlforintro, logedIn).apply();
    }


}
