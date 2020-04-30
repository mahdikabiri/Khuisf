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


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        client.onAppTerminated();
    }

}
