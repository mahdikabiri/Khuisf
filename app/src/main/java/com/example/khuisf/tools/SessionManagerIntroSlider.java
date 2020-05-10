package com.example.khuisf.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerIntroSlider {
    Context context;
    SharedPreferences preferences;
    String pr_name = "prefs_intro_state";
    String prlforintro = "logedin_intro";


    public SessionManagerIntroSlider(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(pr_name, Context.MODE_PRIVATE);
    }

    public boolean isLogedInforIntro() {
        return preferences.getBoolean(prlforintro, false);
    }


    public void setLogedInforIntro(boolean logedIn) {
        preferences.edit().putBoolean(prlforintro, logedIn).apply();
    }


}
