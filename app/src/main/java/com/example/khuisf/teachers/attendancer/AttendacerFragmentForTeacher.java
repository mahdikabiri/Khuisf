package com.example.khuisf.teachers.attendancer;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;
import com.example.khuisf.teachers.getcourses.CourseAdapterForTeach;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static android.content.Context.MODE_PRIVATE;

public class AttendacerFragmentForTeacher extends Fragment {
    ArrayList<Course> studentItems;
    ArrayList<Course> courseItems;
    WaveSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public AttendacerFragmentForTeacher() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recycler_attendance);
        courseItems = new ArrayList<>();
        adapter = new CourseAdapterForTeach(getContext(), courseItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //geting teachers code from shared preferences
        initSwipeRefreashLayout(view);
        getCourses();
    }

    private void initSwipeRefreashLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.get_course_atendance_for_teacher_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        swipeRefreshLayout.setWaveColor(Color.rgb(57, 73, 171));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), R.string.updating, Toast.LENGTH_SHORT).show();
            update();
        });
    }

    private void getCourses() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        String teacherCode = preferences.getString("code", "");
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getCourseTeacher))
                .addBodyParameter("code", teacherCode)
                .setTag("getCourses")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("sss", response.toString());
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        String cDay = object.getString("day");
                        String cTime = object.getString("time");
                        String cChar = object.getString("charac");
                        // add items from db and save to arraylist
                        courseItems.add(new Course(cName, cDay, cTime, cChar));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(), "ایراد در دریافت برنامه هقتگی", Toast.LENGTH_SHORT).show();
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
        courseItems.clear();
        recyclerView.scheduleLayoutAnimation();
        getCourses();
    }

    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }

    private String getCharFromSharedPrefs() {
        return "2222";
    }

}