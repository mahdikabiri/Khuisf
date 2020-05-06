package com.example.khuisf;

import android.app.Application;

import me.cheshmak.android.sdk.core.Cheshmak;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Cheshmak.with(this);
        Cheshmak.initTracker(getString(R.string.key_cheshmak));
    }
}
