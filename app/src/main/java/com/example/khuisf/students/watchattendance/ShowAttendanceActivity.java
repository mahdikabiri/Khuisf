package com.example.khuisf.students.watchattendance;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.MainActivity;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Attendance;
import com.example.khuisf.tools.GetDataFromSH;
import com.marcoscg.dialogsheet.DialogSheet;
import com.phantomvk.slideback.SlideActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class ShowAttendanceActivity extends SlideActivity {
    ArrayList<Attendance> myItems;
    String charac, courseTitle;
    TextView tvStuName, tvCourseName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

        init();
        myItems = new ArrayList<>();
        adapter = new AdapterForAttendance(this, myItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        charac = getIntent().getStringExtra(MainActivity.CHARAC);
        courseTitle = getIntent().getStringExtra(MainActivity.NAME);

        tvStuName.setText(GetDataFromSH.getStuNameFromSharedRefs(this));
        tvCourseName.setText(courseTitle);
        getCourses();
    }

    private void init() {
        recyclerView = findViewById(R.id.activity_show_attendace_recycler);
        tvStuName = findViewById(R.id.activity_show_attendance_tv_stuname);
        tvCourseName = findViewById(R.id.activity_show_attendance_tv_coursename);
        AndroidNetworking.initialize(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_help_watch_attendancer, menu);
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info_icon_watch_attendancer) {
            new DialogSheet(this)
                    .setTitle(R.string.help_text)
                    .setColoredNavigationBar(true)
                    .setTitleTextSize(20) // In SP
                    .setCancelable(true)
                    .setIconResource(R.drawable.ic_info_blue)
                    .setView(R.layout.layout_dialog_info_watchattendaner)
                    .setPositiveButton(android.R.string.ok, new DialogSheet.OnPositiveClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Your action
                        }
                    })
                    .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                    .show();

        } else if (item.getItemId() == android.R.id.home) {
            finish();
            CustomIntent.customType(this, "right-to-left");

        }
        return super.onOptionsItemSelected(item);
    }

    private void getCourses() {
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getAttendancer))
                .addBodyParameter("studentcode", GetDataFromSH.getCodeFromSharedPrefs(this))
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
/*
    private String getStuNameFromSharedRefs() {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("name", "");
    }*/

}
