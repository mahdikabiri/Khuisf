package com.example.khuisf.students.contact_teacher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Teacher;
import com.example.khuisf.tools.GetDataFromSH;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactToTeacherActivity extends AppCompatActivity {
    ArrayList<Teacher> courseItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_to_teacher);
        recyclerView = findViewById(R.id.activity_contact_to_teacher_recycler);
        AndroidNetworking.initialize(this);
        courseItems = new ArrayList<>();
        adapter = new ContactToTeacherAdapter(this, courseItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        getTeachers(GetDataFromSH.getCodeFromSharedPrefs(this));
    }

    private void getTeachers(String studentCode) {
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getTeachersForMessage))
                .addBodyParameter("student_code", studentCode)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //this loop repeating to count of course list
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                String cTeacgerName = object.getString("teacher_name");
                                String cCourseName = object.getString("course_name");
                                String cTeacherCode = object.getString("teacher_code");
                                // add items from db and save to arraylist
                                courseItems.add(new Teacher(cTeacgerName, cCourseName, cTeacherCode));
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplication(), e.toString() + "", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplication(), "ایراد در دریافت برنامه دروس", Toast.LENGTH_SHORT).show();

                    }
                });
    }
/*
    private String getCodeFromSharedRefs() {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }*/
}
