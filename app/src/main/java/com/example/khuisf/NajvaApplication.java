package com.example.khuisf;

import android.app.Application;
import android.widget.Toast;

import com.najva.sdk.NajvaClient;
import com.najva.sdk.NajvaConfiguration;
import com.najva.sdk.UserSubscriptionListener;

public class NajvaApplication extends Application {
    NajvaClient client;
    @Override
    public void onCreate() {
        super.onCreate();
        client = new NajvaClient(this);
        registerActivityLifecycleCallbacks(client);

/*
        NajvaConfiguration configuration = new NajvaConfiguration();
        configuration.setUserSubscriptionListener(new UserSubscriptionListener() {
            @Override
            public void onUserSubscribed(String token) {
                //TODO handle token here.

                Toast.makeText(NajvaApplication.this, token+"", Toast.LENGTH_SHORT).show();
            }
        });
        client = new NajvaClient(this, configuration);
        registerActivityLifecycleCallbacks(client);*/
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        client.onAppTerminated();
    }

}
