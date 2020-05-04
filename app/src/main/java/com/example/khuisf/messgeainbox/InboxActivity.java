package com.example.khuisf.messgeainbox;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Message;
import com.example.khuisf.entitys.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class InboxActivity extends AppCompatActivity {
    ArrayList<Message> myItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    WaveSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        recyclerView = findViewById(R.id.activity_inbox_recycler);

        myItems = new ArrayList<>();
        adapter = new GetMessageAdapter(this, myItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        initSwipeRefreashLayout();
        getInbox();

    }

    private void initSwipeRefreashLayout() {
        swipeRefreshLayout = findViewById(R.id.inbox_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        swipeRefreshLayout.setWaveColor(Color.rgb(57, 73, 171));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(this, R.string.updating, Toast.LENGTH_SHORT).show();
            update();
        });

    }
    private void getInbox( ) {
        String studentCode=getCodeFromSharedPrefs();
        String userRole= String.valueOf(getRoleFromSharedPrefs());
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(getString(R.string.host)+getString(R.string.getMessageForStudent))
                .addBodyParameter("student_code", studentCode)
                .addBodyParameter("role_code", userRole)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("myresponse", response.toString());
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cSender = object.getString("sender");
                        String cText = object.getString("text");
                        // add items from db and save to arraylist
                        myItems.add(new Message(cSender, cText));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplication(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(ANError anError) {
                Toast.makeText(InboxActivity.this, anError + "s", Toast.LENGTH_SHORT).show();
            }
        });
        if (swipeRefreshLayout.isRefreshing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        }
    }


    private void update() {
        myItems.clear();
        recyclerView.scheduleLayoutAnimation();
        getInbox();
    }

    private String getCodeFromSharedPrefs() {
        SharedPreferences preferences = this.getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }

    private int getRoleFromSharedPrefs() {
        SharedPreferences preferences = this.getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getInt("role", 0);
    }

}
