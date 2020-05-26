package com.example.khuisf.tools;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.LoginActivity;
import com.example.khuisf.MainActivity;
import com.example.khuisf.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
            String mytext = object.getString("TEXT");
            String blockSTATE = object.getString("BLOCKSTATE");
            switch (blockSTATE) {
                case "bu":
                    blockUser(mytext);
                    break;
                case "ubu":
                    unBlockUser(mytext);
                    break;
                case "bp":
                    blockPhone(mytext);
                    break;
                case "ubp":
                    unBlockPhone(mytext);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }


    private void blockUser(String mytext) {
        SessionManager manager = new SessionManager(this);
        if (manager.isLogedIn()) {
            Toast.makeText(this, mytext, Toast.LENGTH_SHORT).show();

            SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
            String username = preferences.getString("username", "null");

            AndroidNetworking.post(getString(R.string.host) + getString(R.string.blockUser_url))
                    .addBodyParameter("username", username)
                    .build().
                    getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("1")) {
                                SweetAlertDialog ss = new SweetAlertDialog(MainActivity.context, SweetAlertDialog.ERROR_TYPE);
                                ss.setTitleText(getString(R.string.account_blocked))
                                        .setContentText(mytext)
                                        .setConfirmClickListener(s -> {
                                            MainActivity.context.startActivity(new Intent(MainActivity.context, LoginActivity.class));
                                            System.exit(1);

                                        }).setCancelable(false);
                                MyTools.logout(MainActivity.context);
                                ss.show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(BlockService.this, anError + "", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void unBlockUser(String mytext) {
        Toast.makeText(this, "user unblocked", Toast.LENGTH_SHORT).show();
    }

    private void blockPhone(String mytext) {
        Toast.makeText(this, "phone blocked", Toast.LENGTH_SHORT).show();
    }

    private void unBlockPhone(String mytext) {
        Toast.makeText(this, "phone unblocked", Toast.LENGTH_SHORT).show();
    }


}
