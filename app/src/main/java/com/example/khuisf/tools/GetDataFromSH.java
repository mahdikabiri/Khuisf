package com.example.khuisf.tools;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class GetDataFromSH {

    public static String getNationalCodeFromSharedPrefs(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("national_code", "");
    }


    public static String getCodeFromSharedPrefs(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }

    public static String getStuNameFromSharedRefs(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("name", "");
    }


    public static int getRoleFromSharedPrefs(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getInt("role", 0);
    }

}
