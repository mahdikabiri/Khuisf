package com.example.khuisf.teachers.insertscore;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InsertScoreActivity extends AppCompatActivity {
    String charac, title;
    ArrayList<Student> studentItems;
    TextView tvTitle;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_score);
        charac = getIntent().getStringExtra("CHARACTERISTIC");
        title = getIntent().getStringExtra("title");
        recyclerView = findViewById(R.id.insertscore_recycler);
        tvTitle = findViewById(R.id.insertscore_tv_title);
        tvTitle.setText(title);
        AndroidNetworking.initialize(this);
        studentItems = new ArrayList<>();
        adapter = new InsertScoreAdapter(this, studentItems, charac);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getStudnets();
    }

    private void getStudnets() {
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getStudent))
                .addBodyParameter("char", charac)
                .setTag("getCourse")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        String cCode = object.getString("codestudent");
                        // add items from db and save to arraylist
                        studentItems.add(new Student(cName, cCode));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });

    }
}
