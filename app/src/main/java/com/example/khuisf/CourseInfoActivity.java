package com.example.khuisf;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.khuisf.entitys.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class CourseInfoActivity extends AppCompatActivity {
    TextView tvCourseName, tvTeacherName, tvActualUnit, tvTheoriUnit, tvCharac, tvDay, tvTime;
    String charac, name, time, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        //init();
        charac = getIntent().getStringExtra(MainActivity.CHARAC);
        name = getIntent().getStringExtra(MainActivity.NAME);
        time = getIntent().getStringExtra(MainActivity.TIME);
        day = getIntent().getStringExtra(MainActivity.DAY);
        AndroidNetworking.initialize(this);


        tvCharac.setText(charac);
        tvDay.setText(day);
        tvTime.setText(time);
        tvCourseName.setText(name);

        getCourseInfo(charac);
    }

    // for more performance  get some data form previus activity
    private void getCourseInfo(String charac) {
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getCourseInfo))
                .addBodyParameter("char", charac)
                .setTag("LOGIN")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String unitPractical = response.getString("unit_practical");
                    String unitTheoretical = response.getString("unit_theoretical");
                    String name = response.getString("name");
                    tvActualUnit.setText(unitPractical);
                    tvTheoriUnit.setText(unitTheoretical);
                    tvTeacherName.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {
                Log.d("sdsd", anError.toString());
            }
        });
    }
/*
    private void init() {
        tvCourseName = findViewById(R.id.courseinfo_tv_coursename);
        tvTeacherName = findViewById(R.id.courseinfo_teachername);
        tvActualUnit = findViewById(R.id.courseinfo_tv_amali);
        tvTheoriUnit = findViewById(R.id.courseinfo_tv_theori);
        tvCharac = findViewById(R.id.courseinfo_course_char);
        tvDay = findViewById(R.id.courseinfo_tv_day);
        tvTime = findViewById(R.id.courseinfo_tv_time);
    }*/

}
