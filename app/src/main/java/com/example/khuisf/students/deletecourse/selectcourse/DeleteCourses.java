package com.example.khuisf.students.deletecourse.selectcourse;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import static android.content.Context.MODE_PRIVATE;


public class DeleteCourses extends Fragment {
    ArrayList<Course> courseItems;
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
        return inflater.inflate(R.layout.fragment_delete_courses, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.delete_course_recycler);



        AndroidNetworking.initialize(getContext());
        courseItems = new ArrayList<>();
        adapter = new DeleteCourseAdapter(getContext(), courseItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        getCourses();
    }

    private void getCourses1() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(Urls.host + Urls.getSelectedCourses)
                .addBodyParameter("code",getNameFromSharedRefs())
                .setTag("getCourses")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        String cDay = object.getString("day");
                        String cTime = object.getString("time");
                        String cChar = object.getString("charac");
                        // add items from db and save to arraylist
                        courseItems.add(new Course(cName, cDay, cTime,cChar));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(), "ایراد در دریافت برنامه دروس", Toast.LENGTH_SHORT).show();
                Log.d("ersss",anError.toString());

            }
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
                Log.d("sss",response.toString());
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        String cDay = object.getString("day");
                        String cTime = object.getString("time");
                        String cChar = object.getString("charac");
                        // add items from db and save to arraylist
                        courseItems.add(new Course(cName, cDay, cTime,cChar));
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
                Log.d("sss",anError.toString());

            }
        });
    }




    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }
}
