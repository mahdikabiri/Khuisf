package com.example.khuisf.students.deletecourse;

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
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static android.content.Context.MODE_PRIVATE;


public class DeleteCoursesFragment extends Fragment {
    ArrayList<Course> courseItems;
    WaveSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_delete_courses, container, false);
        recyclerView = view.findViewById(R.id.delete_course_recycler);
        AndroidNetworking.initialize(getContext());
        courseItems = new ArrayList<>();
        adapter = new SampleAdapter(getContext(), courseItems);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        //recyclerView.setAdapter(new SampleAdapter(getContext(),recyclerView));
        initSwipeRefreashLayout(view);
        getCourses();
        return view;
    }


    private void initSwipeRefreashLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.delete_score_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        swipeRefreshLayout.setWaveColor(Color.rgb(57, 73, 171));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            update();
            Toast.makeText(getContext(), R.string.updating, Toast.LENGTH_SHORT).show();
        });
    }

    private void getCourses() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getCourses))
                .addBodyParameter("code", getNameFromSharedRefs())
                .setTag("getCourses")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("myjson", response.toString());
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
                Toast.makeText(getActivity(), "ایراد در دریافت لیست دروس", Toast.LENGTH_SHORT).show();
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
        recyclerView.scheduleLayoutAnimation();
        getCourses();
    }

    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }
}
