package com.example.khuisf.students.watchattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.MainActivity;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Attendance;
import com.example.khuisf.entitys.Course;
import com.example.khuisf.entitys.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowAttendanceActivity extends AppCompatActivity {
    ArrayList<Attendance> myItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String charac,courseTitle;
    TextView tvStuName,tvCourseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        myItems = new ArrayList<>();
        adapter = new AdapterForAttendance(this, myItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        charac = getIntent().getStringExtra(MainActivity.CHARAC);
        courseTitle = getIntent().getStringExtra(MainActivity.NAME);

        tvStuName.setText(getStuNameFromSharedRefs());
        tvCourseName.setText(courseTitle);

        getCourses();
    }

    private void init() {
        setContentView(R.layout.activity_show_attendance);
        recyclerView = findViewById(R.id.activity_show_attendace_recycler);
        tvStuName=findViewById(R.id.activity_show_attendance_tv_stuname);
        tvCourseName=findViewById(R.id.activity_show_attendance_tv_coursename);
        AndroidNetworking.initialize(this);
    }

    private void getCourses() {
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.host + Urls.getAttendancer)
                .addBodyParameter("studentcode", getCodeFromSharedRefs())
                .addBodyParameter("charac", charac)
                .setTag("getAttendance")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("date");
                        String cDay = object.getString("status");
                        // add items from db and save to arraylist
                        myItems.add(new Attendance(cName, cDay));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplication(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getApplication(), "ایراد در دریافت برنامه هقتگی", Toast.LENGTH_SHORT).show();
                Log.d("sss", anError.toString());

            }
        });
    }

    private String getCodeFromSharedRefs() {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }

    private String getStuNameFromSharedRefs() {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("name", "");
    }

}
