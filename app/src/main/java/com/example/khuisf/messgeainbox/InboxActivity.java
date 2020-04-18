package com.example.khuisf.messgeainbox;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class InboxActivity extends AppCompatActivity {
    ArrayList<Message> myItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

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
        getInbox(getCodeFromSharedPrefs(), String.valueOf(getRoleFromSharedPrefs()));

    }

    private void getInbox(String studentCode, String studentRole) {
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.host + Urls.getMessageForStudent)
                .addBodyParameter("student_code", studentCode)
                .addBodyParameter("role_code", studentRole)
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
                Log.d("mahdierr", anError.toString());
                Toast.makeText(InboxActivity.this, anError + "s", Toast.LENGTH_SHORT).show();
            }
        });
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
