package com.example.khuisf.tools;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class BlockService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("CHESHMAK_POPP", "SERVICE STARTED" + intent.getStringExtra("me.cheshmak.data"));
        JSONObject object = null;
        try {
            object = new JSONObject(intent.getStringExtra("me.cheshmak.data"));
            String myOption = object.getString("TEXT");
            String blockSTATE = object.getString("BLOCKSTATE");
            switch (blockSTATE) {
                case "bu":
                    blockUser();
                    break;
                case "ubu":
                    unBlockUser();
                    break;
                case "bp":
                    blockPhone();
                    break;
                case "ubp":
                    unBlockPhone();
                    break;
            }

            blockUser();
            Toast.makeText(this, "" + myOption, Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }


    private void blockUser() {
        SessionManager manager = new SessionManager(this);
        if (manager.isLogedIn()) {
            SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
            String s=null;
            s=preferences.getString("username","null");
            Toast.makeText(this, s+"  blocked", Toast.LENGTH_SHORT).show();




            /*
            ُ getedUsernam=preferences.getString("username", "");
            Toast.makeText(this, getedUsernam+"  blocked", Toast.LENGTH_SHORT).show();
        */}
    }

    private void unBlockUser() {
        Toast.makeText(this, "user unblocked", Toast.LENGTH_SHORT).show();
    }

    private void blockPhone() {
        Toast.makeText(this, "phone blocked", Toast.LENGTH_SHORT).show();
    }

    private void unBlockPhone() {
        Toast.makeText(this, "phone unblocked", Toast.LENGTH_SHORT).show();
    }


}
