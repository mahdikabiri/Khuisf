package com.example.khuisf.students.watchattendance;

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
import com.example.khuisf.entitys.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static android.content.Context.MODE_PRIVATE;

public class WatchAttendanceFragment extends Fragment {
    ArrayList<Course> courseItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    WaveSwipeRefreshLayout swipeRefreshLayout;

    public WatchAttendanceFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getcourse, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recycler_getcourse);
        AndroidNetworking.initialize(getContext());
        courseItems = new ArrayList<>();
        adapter = new CourseAdapterForAttendance(getContext(), courseItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        initSwipeRefreashLayout(view);
        getCourses();
    }

    private void initSwipeRefreashLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.get_course_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        swipeRefreshLayout.setWaveColor(Color.rgb(57, 73, 171));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), R.string.updating, Toast.LENGTH_SHORT).show();
            update();
        });

    }

    private void getCourses() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(Urls.host + Urls.getCourses)
                .addBodyParameter("code", getNameFromSharedRefs())
                .setTag("getCourses")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("sss", response.toString());
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        int cId = object.getInt("id");
                        String cName = object.getString("name");
                        String cDay = object.getString("day");
                        String cTime = object.getString("time");
                        String cChar = object.getString("charac");
                        // add items from db and save to arraylist
                        courseItems.add(new Course(cId, cName, cDay, cTime, cChar));
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
                Log.d("sss", anError.toString());

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
        getCourses();
    }

    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }

    private void readyResponse(String result) {
        String[] elements = result.split(",");
        List<String> fix = Arrays.asList(elements);
        ArrayList<String> listofStrings = new ArrayList<>(fix);
        Toast.makeText(getActivity(), "courses:" + listofStrings, Toast.LENGTH_SHORT).show();

    }
}