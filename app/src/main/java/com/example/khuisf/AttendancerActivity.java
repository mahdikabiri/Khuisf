package com.example.khuisf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttendancerActivity extends AppCompatActivity {
    ArrayList<Student> studentItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String charac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendancer);

        charac=getIntent().getStringExtra("CHARACTRISTIC");
        recyclerView = findViewById(R.id.recycler_attendance1);
        AndroidNetworking.initialize(this);
        studentItems = new ArrayList<>();
        adapter = new attendancerAdapter(this, studentItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getStudnets();
    }



    private void getStudnets() {
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.host+ Urls.getStudent)
                .addBodyParameter("char",charac)
                .setTag("getCourse")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ddf",response.toString());
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        // add items from db and save to arraylist
                        studentItems.add(new Student(cName));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }            }

            @Override
            public void onError(ANError anError) {
                Log.d("salam",anError.toString());

            }
        });
    }
}
